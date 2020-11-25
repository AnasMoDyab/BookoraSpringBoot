package com.tietoevry.bookorabackend.exception;

/**
 * A exception class that is thrown when employee is not activated.
 */
public class EmployeeNotActivatedException extends Exception {
    public EmployeeNotActivatedException(String s) {
        super(s);
    }
}