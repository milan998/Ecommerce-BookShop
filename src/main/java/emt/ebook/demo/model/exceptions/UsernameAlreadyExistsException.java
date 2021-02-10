package emt.ebook.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String name) {}
}
