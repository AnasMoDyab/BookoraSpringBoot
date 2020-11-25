package com.tietoevry.bookorabackend.exception;

import com.tietoevry.bookorabackend.api.v1.model.BookingIdDTO;
import com.tietoevry.bookorabackend.api.v1.model.JwtDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.thymeleaf.exceptions.TemplateInputException;

/**
 * This class gives instructions to controller and rest controller when specific exception is thrown.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Handles TemplateInputException.
     *
     * @param exception A exception
     * @return A http status of bad request and a error message as response body
     */
    @ExceptionHandler(TemplateInputException.class)
    public ResponseEntity<MessageDTO> handleErrorInTemplate(Exception exception) {

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserExistedException.
     *
     * @param exception A exception
     * @return A http status of conflict and a error message as response body
     */
    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<MessageDTO> handleUserExistedException(Exception exception) {

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
    }

    /**
     * Handles InvalidDomainException, RoleNotFoundException, ZoneNotFoundException and EmployeeNotFoundException.
     *
     * @param exception A exception
     * @return A http status of not found and a error message as response body
     */
    @ExceptionHandler({InvalidDomainException.class, RoleNotFoundException.class, ZoneNotFoundException.class,
            EmployeeNotFoundException.class})
    public ResponseEntity<MessageDTO> handleNotFound(Exception exception) {

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ResourceAccessException and MethodArgumentNotValidException.
     *
     * @param exception A exception
     * @return A http status of bad request and a error message as response body
     */
    @ExceptionHandler({ResourceAccessException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<MessageDTO> handleInvalidInput(Exception exception) {

        MessageDTO messageDTO = new MessageDTO("Invalid input");

        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles EmployeeNotActivatedException, InvalidInputException and InvalidActionException.
     *
     * @param exception A exception
     * @return A http status of bad request and a JwtDTO which contains the error message
     */
    @ExceptionHandler({EmployeeNotActivatedException.class, InvalidInputException.class, InvalidActionException.class})
    public ResponseEntity<JwtDTO> handleEmployeeNotActivatedException(Exception exception) {

        JwtDTO jwtDTO = new JwtDTO(exception.getMessage());

        return new ResponseEntity<>(jwtDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BookingFailException.
     *
     * @param exception A exception
     * @return A http status of bad request and a BookingIdDTO which contains the error message
     */
    @ExceptionHandler(BookingFailException.class)
    public ResponseEntity<BookingIdDTO> handleBookingFailException(Exception exception) {

        BookingIdDTO bookingIdDTO = new BookingIdDTO(exception.getMessage(), null);

        return new ResponseEntity<>(bookingIdDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BadCredentialsException.
     *
     * @param exception A exception
     * @return A http status of unauthorized and a error message as response body
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageDTO> handleBadCredentialsException(Exception exception) {

        MessageDTO messageDTO = new MessageDTO("Incorrect email or password");

        return new ResponseEntity<>(messageDTO, HttpStatus.UNAUTHORIZED);
    }


}