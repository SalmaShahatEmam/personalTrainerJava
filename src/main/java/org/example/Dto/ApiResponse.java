package org.example.Dto;

import java.util.List;

public class ApiResponse {

    public Object data;
    public Integer status;
    public String message;


    public ApiResponse(Object data , Integer Status  , String message) {
        this.data = data;
        this.message = message;
        this.status = Status;
    }


    
}
