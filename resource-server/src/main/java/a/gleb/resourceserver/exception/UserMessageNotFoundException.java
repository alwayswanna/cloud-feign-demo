package a.gleb.resourceserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserMessageNotFoundException extends RuntimeException{

    public UserMessageNotFoundException(String message) {
        super(message);
    }
}
