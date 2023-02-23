package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.dto.EmailDTO;
import com.nelioalves.cursomc.security.JWTUtil;
import com.nelioalves.cursomc.security.UserSpringSecurity;
import com.nelioalves.cursomc.services.AuthService;
import com.nelioalves.cursomc.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final JWTUtil jwtUtil;
    private final AuthService authService;

    public AuthResource(JWTUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(HttpServletResponse response) {
        UserSpringSecurity user = UserServices.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity forgot(@Valid @RequestBody EmailDTO emailDTO) {
        UserSpringSecurity user = UserServices.authenticated();
        authService.sendNewPassWord(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
