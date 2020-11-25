package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when employee is not found.
 */
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String s) {
        super(s);
    }
}