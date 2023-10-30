package com.example.angular_jwt.Config.Auth;

import com.example.angular_jwt.Config.JwtService;
import com.example.angular_jwt.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user=authService.RegisterUser(request);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user=authService.findUser(request.getEmail())
                .orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        var userObj=user;
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(userObj)
                .build();
    }
}
