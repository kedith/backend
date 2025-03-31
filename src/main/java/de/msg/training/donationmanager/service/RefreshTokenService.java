package de.msg.training.donationmanager.service;


import de.msg.training.donationmanager.config.JwtUtils;
import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.RefreshToken;
import de.msg.training.donationmanager.repository.RefreshTokenRepository;
import de.msg.training.donationmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public void deleteRefreshTokenForUser(Long userId) {
        refreshTokenRepository.deleteRefreshTokenFromUser(userId);
    }


    public void createRefreshToken(String uuid, Long userId) {
        RefreshToken rt = new RefreshToken();
        rt.setRefreshToken(uuid);
        rt.setExpiryDate(Instant.now().plusSeconds(84000));
        rt.setUser(userRepository.findById(userId).get());
        refreshTokenRepository.save(rt);
    }


    public String exchangeRefreshToken(String refreshToken) throws BusinessException {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(refreshToken);
        if(!refreshTokenOptional.isPresent()) {
            throw new RuntimeException("Refresh token is not valid");
        }
        RefreshToken rt = refreshTokenOptional.get();
        if(rt.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(rt);
            throw new BusinessException(BusinessExceptionCode.EXPIRED_REFRESHTOKEN);
        }
        return jwtUtils.generateJwtToken(userDetailsService.loadUserByUsername(rt.getUser().getUsername()));
    }

}
