package emt.ebook.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidBookIdException extends RuntimeException{
    public InvalidBookIdException(){}
}
