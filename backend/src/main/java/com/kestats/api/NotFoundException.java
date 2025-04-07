package com.kestats.api;

public class NotFoundException extends RuntimeException {

    public NotFoundException(){
        super("Not Found");
    }
}
