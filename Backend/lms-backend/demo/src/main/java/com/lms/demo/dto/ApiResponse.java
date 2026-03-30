package com.lms.demo.dto;

import lombok.Data;

@Data
public class ApiResponse<T>
{
    private boolean success;
    private T data;
    private String error;
}
