package ua.denys.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(code = NOT_FOUND)
public class ChatNotFoundException extends AppException{

    public ChatNotFoundException(String message) {
        super(message);
    }
}
