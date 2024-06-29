package com.mykhailotiutiun.authservice.service;

import com.mykhailotiutiun.authservice.client.UserClient;
import com.mykhailotiutiun.authservice.dto.LoginRequest;
import com.mykhailotiutiun.authservice.dto.RegisterRequest;
import com.mykhailotiutiun.authservice.dto.RegisterResponse;
import com.mykhailotiutiun.authservice.dto.TokenResponse;
import com.mykhailotiutiun.authservice.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserClient userClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserClient userClient, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userClient = userClient;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public TokenResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()){
            return TokenResponse.builder()
                    .token(jwtUtil.generateToken(userClient.findUser(loginRequest.getEmail())))
                    .build();
        } else throw new UsernameNotFoundException(loginRequest.getEmail());
    }

    public RegisterResponse register(RegisterRequest registerRequest){
        registerRequest.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        return userClient.register(registerRequest);
    }
}
