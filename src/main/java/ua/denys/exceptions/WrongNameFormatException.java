package ua.denys.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(code = BAD_REQUEST)
public class WrongNameFormatException extends AppException{

    public WrongNameFormatException(String message) {
        super(message);
    }
}
