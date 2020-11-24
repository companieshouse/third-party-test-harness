package uk.gov.companieshouse.controllers;


import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.exception.NoUserOfIdException;
import uk.gov.companieshouse.exception.RateExceededException;
import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.model.UserTokenStore;
import uk.gov.companieshouse.service.IUserService;

@ControllerAdvice
public class ExceptionController {

    @Autowired
    private IUserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyController.class);

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RateExceededException.class)
    public String handleRateExceeded(RateExceededException e, Model model){
        User user;
        try {
            UserTokenStore userTokenStore = userService.findByEmail(e.getUser().getEmail());
            user = userService.getUserDetails(userTokenStore.getAccessToken());
        }catch (NoUserOfIdException noUserEx){
            user = new User();
        }
        model.addAttribute("user", user);
        return "rateExceeded";
    }

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