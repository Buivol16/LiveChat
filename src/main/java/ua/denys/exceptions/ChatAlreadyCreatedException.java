package ua.denys.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(code = CONFLICT)
public class ChatAlreadyCreatedException extends AppException{
    public ChatAlreadyCreatedException(String message) {
        super(message);
    }
}
