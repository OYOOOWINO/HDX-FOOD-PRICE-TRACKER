package com.kestats.api;


public class UserExistsException extends RuntimeException {

    public UserExistsException(){
        super("User Exists");
    }
}
