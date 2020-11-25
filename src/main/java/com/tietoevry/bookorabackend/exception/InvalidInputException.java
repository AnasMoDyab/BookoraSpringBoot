package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when input is not valid.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String s) {
        super(s);
    }
}