package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when zone is not found.
 */
public class ZoneNotFoundException extends Exception {
    public ZoneNotFoundException(String s) {
        super(s);
    }
}