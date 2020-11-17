package com.tietoevry.bookorabackend.exception;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TemplateInputException.class)
    public ResponseEntity<MessageDTO> handleErrorInTemplate(Exception exception){

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return  new ResponseEntity<>(messageDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<MessageDTO> handleUserExistedException(Exception exception){

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDomainException.class)
    public ResponseEntity<MessageDTO> handleInvalidDomainException(Exception exception){

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<MessageDTO> handleRoleNotFoundException(Exception exception){

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ZoneNotFoundException.class)
    public ResponseEntity<MessageDTO> handleZoneNotFoundException(Exception exception){

        MessageDTO messageDTO = new MessageDTO(exception.getMessage());

        return new ResponseEntity<>(messageDTO,HttpStatus.NOT_FOUND);
    }


}