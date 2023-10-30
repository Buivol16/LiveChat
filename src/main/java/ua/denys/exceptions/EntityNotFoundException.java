package ua.denys.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(code = BAD_REQUEST)
public class EntityNotFoundException extends AppException{

    public EntityNotFoundException(String message) {
        super(message);
    }
}
