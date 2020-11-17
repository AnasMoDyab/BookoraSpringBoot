package com.tietoevry.bookorabackend.exception;

public class UserExistedException extends Exception {
    public UserExistedException(String s) {
        super(s);
    }
}