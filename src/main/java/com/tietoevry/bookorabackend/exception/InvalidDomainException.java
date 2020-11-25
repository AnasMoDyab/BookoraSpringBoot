package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when email domain is not valid.
 */
public class InvalidDomainException extends Exception {
    public InvalidDomainException(String s) {
        super(s);
    }
}