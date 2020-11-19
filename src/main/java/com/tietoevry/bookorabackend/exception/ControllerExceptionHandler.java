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

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(TemplateInputException.class)
    public ResponseEntity<MessageDTO> handleErrorInTemplate(Exception exception) {

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<MessageDTO> handleUserExistedException(Exception exception) {

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidDomainException.class, RoleNotFoundException.class, ZoneNotFoundException.class,
            EmployeeNotFoundException.class})
    public ResponseEntity<MessageDTO> handleNotFound(Exception exception) {

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceAccessException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<MessageDTO> handleInvalidInput(Exception exception) {

        MessageDTO messageDTO = new MessageDTO("Invalid input");

        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmployeeNotActivatedException.class, InvalidInputException.class, InvalidActionException.class})
    public ResponseEntity<JwtDTO> handleEmployeeNotActivatedException(Exception exception) {

        JwtDTO jwtDTO = new JwtDTO(exception.getMessage());

        return new ResponseEntity<>(jwtDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingFailException.class)
    public ResponseEntity<BookingIdDTO> handleBookingFailException(Exception exception) {

        BookingIdDTO bookingIdDTO = new BookingIdDTO(exception.getMessage(), null);

        return new ResponseEntity<>(bookingIdDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageDTO> handleBadCredentialsException(Exception exception) {

        MessageDTO messageDTO = new MessageDTO("Incorrect email or password");

        return new ResponseEntity<>(messageDTO, HttpStatus.UNAUTHORIZED);
    }


}