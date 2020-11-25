package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when user already exist.
 */
public class UserExistedException extends Exception {
    public UserExistedException(String s) {
        super(s);
    }
}