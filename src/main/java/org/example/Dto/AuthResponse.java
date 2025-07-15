package org.example.Dto;

public class AuthResponse {

    public Object user;
    public String token;

    public AuthResponse(Object user ,String token)
    {
        this.user = user;
        this.token = token;
    }
}
