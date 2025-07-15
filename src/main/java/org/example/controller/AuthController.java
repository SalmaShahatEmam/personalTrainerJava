package org.example.controller;

import org.example.Dto.ApiResponse;
import org.example.Dto.AuthResponse;
import org.example.Dto.LoginRequest;
import org.example.Enums.StatusEnum;
import org.example.model.User;
import org.example.services.JwtService;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Optional;

@RestController
public class AuthController {

    public final UserRepository userRepository;
    public final JwtService jwtService;

    @Autowired
    public AuthController(UserRepository userRepository , JwtService jwtService ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String passwordHash(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }

    @PostMapping("Register")
    public ResponseEntity<ApiResponse> Register(@RequestBody User user) {
            User new_user = new User();
            String email = user.getEmail();

            Optional<User> userCheck = userRepository.findByEmail(email);

            if(userCheck.isPresent())
            {
                ApiResponse apiResponse = new ApiResponse(null , 409 , "email already taken");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
            }

            new_user.setEmail(user.getEmail());
            new_user.setRole(StatusEnum.STUDENT);

            //Encoding
            String passwordHashed = this.passwordHash(user.getPassword());
            new_user.setPassword(passwordHashed);


            new_user.setFirstName(user.getFirstName());
            new_user.setLastName(user.getLastName());
            userRepository.save(new_user);
            String token = jwtService.generateToken(email);
        AuthResponse authResponse = new AuthResponse(new_user , token);

        ApiResponse apiResponse = new ApiResponse(authResponse , 200 , "register successfully");
            return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null , 404 , "email not found"));

        }
        User user = optionalUser.get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(password ,user.getPassword() ))
        {
            String token = jwtService.generateToken(email);
            AuthResponse authResponse = new AuthResponse(user , token);

            ApiResponse apiResponse = new ApiResponse(authResponse , 200 , "register successfully");
            return ResponseEntity.ok(apiResponse);
        }
        return ResponseEntity.ok("can not loged in password incorrect");

    }
}
