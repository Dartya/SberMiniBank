package ru.sber.alex.minibank.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * Контроллер возникающих ошибок.
 */
@ControllerAdvice
public class ErrorController {

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    public String handleIOException(final IOException ioException, final Model model){
        logger.error("IOException", ioException);
        String errorMessage = (ioException != null ? ioException.getMessage() : "Unknown error");
        model.addAttribute("userError", false);
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("userError", false);
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}