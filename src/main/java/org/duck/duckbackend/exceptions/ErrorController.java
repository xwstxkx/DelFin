package org.duck.duckbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Объект с такими параметрами не существует")
    public ResponseError handle(ObjectNotFound exception) {
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParametersNotSpecified.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Недостаточно параметров в запросе")
    public ResponseError handle(ParametersNotSpecified exception) {
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentials.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Некорректный логин или пароль")
    public ResponseError handle(BadCredentials exception) {
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDoNotMatch.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Пароли не совпадают")
    public ResponseError handle(PasswordDoNotMatch exception) {
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsAlreadyExists.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Пользователь с указанным именем уже существует")
    public ResponseError handle(UserIsAlreadyExists exception) {
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
