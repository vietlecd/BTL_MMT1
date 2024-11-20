package com.project.btl_mmt1.helpers;


import com.project.btl_mmt1.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    public User getUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    public String getUsername(Authentication authentication) {
        User user = getUser(authentication);
        return user != null ? user.getUsername() : null;
    }
}
