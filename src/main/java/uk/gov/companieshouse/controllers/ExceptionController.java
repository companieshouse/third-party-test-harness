package uk.gov.companieshouse.controllers;


import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public String handleIOException(){
        return "error";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleHttpClientError(HttpClientErrorException e){
        ModelAndView modelAndView = new ModelAndView("httpClientError");
        modelAndView.addObject("reason", e.getStatusText());
        LOGGER.error("HttpClientException", e);
        modelAndView.setStatus(e.getStatusCode());
        return modelAndView;
    }
}