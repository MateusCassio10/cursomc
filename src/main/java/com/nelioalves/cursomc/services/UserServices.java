package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.security.UserSpringSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserServices {

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}
