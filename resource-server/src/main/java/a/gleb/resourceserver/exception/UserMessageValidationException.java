package a.gleb.resourceserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserMessageValidationException extends RuntimeException {

    public UserMessageValidationException(String message) {
        super(message);
    }
}
