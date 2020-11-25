package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when there is a invalid action.
 */
public class InvalidActionException extends Exception {
    public InvalidActionException(String s) {
        super(s);
    }
}