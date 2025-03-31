package de.msg.training.donationmanager.controller.auth;


import de.msg.training.donationmanager.config.JwtUtils;
import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Notifications;
import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.net.websocket.service.WebsocketService;
import de.msg.training.donationmanager.repository.UserRepository;
import de.msg.training.donationmanager.service.NotificationService;
import de.msg.training.donationmanager.service.RefreshTokenService;
import de.msg.training.donationmanager.service.UserDetailsImpl;
import de.msg.training.donationmanager.service.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static de.msg.training.donationmanager.model.ENotifications.USER_DEACTIVATED;


@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final String key = "1234567890123456";

    private static final String REFRESHTOKEN_COOKIE_NAME = "RefreshTokenCookie";

    @Autowired
    private WebsocketService websocketService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws BusinessException {

        User user = userDetailsService.getUserByUsername(loginRequest.getUsername());
        try {

            String decryptedPassword = decrypt(loginRequest.getPassword());

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), decryptedPassword));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String jwt = jwtUtils.generateJwtToken(userDetails);

            String refreshToken = UUID.randomUUID().toString();

            if (userDetails.isActive()) {
                refreshTokenService.deleteRefreshTokenForUser(userDetails.getId());
                refreshTokenService.createRefreshToken(refreshToken, userDetails.getId());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, createCookie(refreshToken).toString());
            SignInResponse response = new SignInResponse(jwt, "", userDetails.getId(),
                    userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.isActive(), userDetails.isFirstLogin());

            updateLoginAttempts(user, true);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (AuthenticationException e) {
            updateLoginAttempts(user, false);
            throw new BusinessException(BusinessExceptionCode.INVALID_CREDENTIALS);
        }
    }

    public void updateLoginAttempts(User user, boolean t) throws BusinessException {
        if(user != null) {
            if (t) {
                user.setAttempts((short) 0);
                userDetailsService.updateUser(user);
            } else {
                user.setAttempts((short) (user.getAttempts() + 1));
                userDetailsService.updateUser(user);
                if (user.getAttempts() == 5) {
                    user.setActive(false);
                    userDetailsService.updateUser(user);
                    websocketService.sendMessagesForUserDeactivated(user.getUsername());
                    System.out.println("ceva");
                    notificationService.saveNotification(new Notifications(userDetailsService.getUserByUsername("admin"),user,USER_DEACTIVATED,"", LocalDateTime.now()));
                    throw new BusinessException(BusinessExceptionCode.USER_DEACTIVATED);
                }
                if(user.getAttempts()>5){
                    throw new BusinessException(BusinessExceptionCode.DEACTIVATED_USER);
                }
            }
        }
        else{
            throw new BusinessException(BusinessExceptionCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> checkCookie(HttpServletRequest request) throws BusinessException {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(REFRESHTOKEN_COOKIE_NAME)).findFirst();
        if (cookie.isPresent()) {
            return ResponseEntity.ok(new RefreshTokenResponse(refreshTokenService.exchangeRefreshToken(cookie.get().getValue())));
        }
        throw new BusinessException(BusinessExceptionCode.MISSING_COOKIE);
    }

    private ResponseCookie createCookie(String token) {
        return ResponseCookie.from(REFRESHTOKEN_COOKIE_NAME, token)
                .httpOnly(true)
                .maxAge(Duration.ofDays(1))
                .sameSite("None")
                .path("/auth/refreshToken")
                .build();
    }

    public String decrypt(String toDecrypt) {
        try {
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(toDecrypt));
            return new String(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
