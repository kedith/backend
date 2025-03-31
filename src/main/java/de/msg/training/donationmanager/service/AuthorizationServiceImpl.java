package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.config.JwtUtils;
import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static de.msg.training.donationmanager.exception.BusinessExceptionCode.USER_NOT_AUTHORIZED;

@Service
public class AuthorizationServiceImpl {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    public void checkUserAuthorization(String jwtToken, ERight right) throws BusinessException {
        User user = UserFromJwt(jwtToken);
        Set<Role> roles = user.getRoles();
        Set<Right> userRights = new HashSet<>();
        for (Role i : roles) {
            userRights.addAll(i.getRights());
        }
        boolean t = false;
        for(Right i:userRights)
        {
            if(i.getName() == right) {
                t = true;
                break;
            }
        }
        if(!t){
            throw new BusinessException(USER_NOT_AUTHORIZED);
        }
    }
    public boolean checkRepRestriction(String jwtToken){
        User user = UserFromJwt(jwtToken);
        Set<Role> roles = user.getRoles();
        Set<Right> userRights = new HashSet<>();
        boolean t = false;
        for (Role i : roles) {
            if(i.getName() == ERole.REP){
                t = true;
                break;
            }
            userRights.addAll(i.getRights());
        }
        if(t) {
            for (Right i : userRights) {
                if (i.getName() == ERight.CAMP_REPORT_RESTRICTED) {
                    return true;
                }
            }
        }
        return t;
    }
    public User UserFromJwt(String jwtToken){
        return userDetailsService.getUserByUsername(jwtUtils.getUserNameFromJwtToken(jwtToken));
    }
}
