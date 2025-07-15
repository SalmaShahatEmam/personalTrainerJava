package org.example.controller;

import org.example.Dto.ApiResponse;
import org.example.Dto.AuthResponse;
import org.example.Dto.LoginRequest;
import org.example.Enums.StatusEnum;
import org.example.model.User;
import org.example.services.JwtService;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;

@RestController
public class AuthController {

    public final UserRepository userRepository;
    public final JwtService jwtService;

    @Autowired
    public AuthController(UserRepository userRepository , JwtService jwtService ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("Register")
    public ResponseEntity<ApiResponse> Register(@RequestBody User user) {
            User new_user = new User();
            String email = user.getEmail();
            new_user.setEmail(user.getEmail());
            new_user.setRole(StatusEnum.STUDENT);
            new_user.setPassword(user.getPassword());
            new_user.setFirstName(user.getFirstName());
            new_user.setLastName(user.getLastName());
            userRepository.save(new_user);
            String token = jwtService.generateToken(email);
        AuthResponse authResponse = new AuthResponse(new_user , token);

        ApiResponse apiResponse = new ApiResponse(authResponse , 200 , "register successfully");
            return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("Login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest)
    {

    }
}
