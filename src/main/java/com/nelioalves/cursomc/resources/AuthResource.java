package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.security.JWTUtil;
import com.nelioalves.cursomc.security.UserSpringSecurity;
import com.nelioalves.cursomc.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final JWTUtil jwtUtil;

    public AuthResource(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(HttpServletResponse response) {
        UserSpringSecurity user = UserServices.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
