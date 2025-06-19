package com.mini_project.employeemanagementsystem.common.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
