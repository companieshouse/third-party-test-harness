package uk.gov.companieshouse.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.companieshouse.exception.RateExceededException;
import uk.gov.companieshouse.model.Query;
import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.model.UserTokenStore;
import uk.gov.companieshouse.model.restmodels.CompanyNumber;
import uk.gov.companieshouse.service.ICompanyService;
import uk.gov.companieshouse.service.IOAuthService;
import uk.gov.companieshouse.service.IUserService;

@Controller
public class ThirdPartyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ThirdPartyController.class);

    private static final String companyNumberPlaceHolder = "{{ COMPANY_NUMBER }}";

    @Value("${client-id}")
    private String clientId;

    @Value("${redirect-uri}")
    private String redirectUri;

    @Value("${authorise-uri}")
    private String authoriseUri;

    @Value("${requested-scope}")
    private String requestedScope;

    @Value("${user-requested-scope}")
    private String userRequestedScope;

    private final IOAuthService oAuthService;
    private final IUserService userService;
    private final ICompanyService companyService;

    @Autowired
    public ThirdPartyController(IOAuthService oAuthService, IUserService userService, ICompanyService companyService) {
        this.oAuthService = oAuthService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/attemptLogin")
    public String attemptLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("response_type", "code");
        redirectAttributes.addAttribute("client_id", clientId);
        redirectAttributes.addAttribute("redirect_uri", redirectUri);
        redirectAttributes.addAttribute("scope", userRequestedScope);
        return "redirect:" + authoriseUri;
    }

    @GetMapping(value = "/loginViaCompanyNumber")
    public String loginViaCompanyNumber(){
        return "loginCompanyNumber";
    }

    @GetMapping(value = "/loginCompanyNumber")
    public String attemptLoginCompanyNumber(@Valid @ModelAttribute("companyNumber") CompanyNumber companyNumber,
                                            BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()){
            List<FieldError> errorsList = result.getFieldErrors();
            model.addAttribute("errors", errorsList);
            return "loginCompanyNumber";
        }

        List<String> scopeList = new ArrayList<>();
        scopeList.add(userRequestedScope);
        scopeList.add(requestedScope.replace(companyNumberPlaceHolder,companyNumber.getCompanyNumber()));
        String scope = userRequestedScope + " " + requestedScope.replace(companyNumberPlaceHolder,companyNumber.getCompanyNumber());
        LOGGER.warn("***SCOPE: "+scopeList.toString());
//        model.addAttribute("scope", scopeList);
        redirectAttributes.addAttribute("scope", scopeList);
        redirectAttributes.addAttribute("response_type", "code");
        redirectAttributes.addAttribute("client_id", clientId);
        redirectAttributes.addAttribute("redirect_uri", redirectUri);
        return "redirect:" + authoriseUri;
    }

    @GetMapping(value = "/redirect")
    public String handleRedirect(@RequestParam("code") String code, Model model, CompanyNumber companyNumber) throws IOException {
        Map<String, String> userTokens = oAuthService.getAccessTokenAndRefreshToken(code);

        List<String> scopeList = new ArrayList<>();
        scopeList.add(userRequestedScope);
        scopeList.add(requestedScope.replace(companyNumberPlaceHolder,companyNumber.getCompanyNumber()));
        User user = userService.getUserDetails(userTokens.get("access_token"));
        userService.storeUserDetails(user.getEmail(), userTokens.get("access_token"), userTokens.get("refresh_token"), Long.parseLong(userTokens.get("expires_in")));
        model.addAttribute("user", user);
        model.addAttribute("scope", scopeList);
        model.addAttribute("accessToken", userTokens.get("access_token"));
        model.addAttribute("query", new Query());
        return "loginResult";
    }

    @PostMapping(value = "/getResult")
    public String getResult(@ModelAttribute @Valid Query queryObject, Model model)throws IOException {
        UserTokenStore userTokenStore = userService.findByEmail(queryObject.getEmail());
        User user = userService.getUserDetails(userTokenStore.getAccessToken());
        model.addAttribute("user", user);
        try {
            model.addAttribute("companyResult", companyService.findCompany(userTokenStore, queryObject.getCompanyNumber(), true));

            return "companyResultPage";
        }catch(RateExceededException e){
            LOGGER.warn(e.getMessage());
            return "rateExceeded";
        }catch(IOException e){
            LOGGER.warn(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorType", e.getCause());
            return "errorRetrievingDetails";
        }
    }

    @GetMapping(value = "/notPresent")
    public String notPresentHome(Model model){
        List<UserTokenStore> allUsers = userService.findAllUsers();
        if(!allUsers.isEmpty()) {
            model.addAttribute("users", allUsers);
            model.addAttribute("query", new Query());
            return "notPresentHome";
        }else{
            return "notPresentNoUsers";
        }
    }

    @PostMapping(value = "/notPresentResult")
    public String notPresentResult(@ModelAttribute @Valid Query queryObject, Model model) throws IOException {
        UserTokenStore userTokenStore = userService.findByEmail(queryObject.getEmail());

        try {
            model.addAttribute("companyResult", companyService.findCompany(userTokenStore, queryObject.getCompanyNumber(), true));

            userTokenStore = userService.findByEmail(queryObject.getEmail());
            User user = userService.getUserDetails(userTokenStore.getAccessToken());
            model.addAttribute("user", user);
            return "notPresentCompanyResultPage";
        }catch(RateExceededException e){
            LOGGER.warn(e.getMessage());
            return "rateExceeded";
        }catch(IOException e){
            LOGGER.warn(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorType", e.getCause());
            return "errorRetrievingDetails";
        }


    }
}