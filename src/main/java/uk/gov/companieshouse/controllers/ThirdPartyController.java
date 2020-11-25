package uk.gov.companieshouse.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class ThirdPartyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ThirdPartyController.class);

    @Value("${client-id}")
    private String clientId;

    @Value("${redirect-uri}")
    private String redirectUri;

    @Value("${authorise-uri}")
    private String authoriseUri;

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
        redirectAttributes.addAttribute("scope", "https://identity.company-information.service.gov.uk/user/profile.read");
        return "redirect:" + authoriseUri;
    }

    @GetMapping(value = "/loginViaCompanyNumber")
    public String loginViaCompanyNumber(){
        return "loginCompanyNumber";
    }

//    @GetMapping(value = "/error")
//    public String errorHandling(){
//        return "login";
//    }

    @GetMapping(value = "/loginCompanyNumber")
    //public String attemptLoginCompanyNumber(@Size(max=8,min=8) @Valid @RequestParam("companyNumber") String companyNumber, RedirectAttributes redirectAttributes) {
    public String attemptLoginCompanyNumber(@Valid @ModelAttribute("companyNumber") CompanyNumber companyNumber,
                                            BindingResult result, RedirectAttributes redirectAttributes) {
        String scope = "https://identity.company-information.service.gov.uk/user/profile.read https://api.company-information.service.gov.uk/company/" + companyNumber + "/registered-office-address.update";
        redirectAttributes.addAttribute("scope", scope);
        redirectAttributes.addAttribute("response_type", "code");
        redirectAttributes.addAttribute("client_id", clientId);
        redirectAttributes.addAttribute("redirect_uri", redirectUri);
        return "redirect:" + authoriseUri;
    }

    @GetMapping(value = "/redirect")
    public String handleRedirect(@RequestParam("code") String code, Model model) throws IOException {
        Map<String, String> userTokens = oAuthService.getAccessTokenAndRefreshToken(code);

        User user = userService.getUserDetails(userTokens.get("access_token"));
        userService.storeUserDetails(user.getEmail(), userTokens.get("access_token"), userTokens.get("refresh_token"), Long.parseLong(userTokens.get("expires_in")));
        model.addAttribute("user", user);
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