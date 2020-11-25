package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when booking fails.
 */
public class BookingFailException extends Exception {
    public BookingFailException(String s) {
        super(s);
    }
}