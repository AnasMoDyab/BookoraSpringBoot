package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when role is not found.
 */
public class RoleNotFoundException extends Exception {
    public RoleNotFoundException(String s) {
        super(s);
    }
}