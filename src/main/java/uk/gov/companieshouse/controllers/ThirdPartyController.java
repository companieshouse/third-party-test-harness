package uk.gov.companieshouse.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.companieshouse.model.Query;
import uk.gov.companieshouse.model.Scope;
import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.service.UserAuthService;

@Controller
public class ThirdPartyController {
    private static final String SCOPE = "scope";
    private static final String USER_SCOPE = "https://identity.company-information.service.gov.uk/user/profile.read";

    private final UserAuthService userAuthService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyController.class);

    @Value("${client-id}")
    private String clientId;
    @Value("${redirect-uri}")
    private String redirectUri;
    @Value("${authorise-uri}")
    private String authoriseUri;

    @Autowired
    public ThirdPartyController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/attemptLogin")
    public String attemptLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("response_type", "code");
        redirectAttributes.addAttribute("client_id", clientId);
        redirectAttributes.addAttribute("redirect_uri", redirectUri);
        redirectAttributes.addAttribute(SCOPE, USER_SCOPE);
        return "redirect:" + authoriseUri;
    }

    @GetMapping(value = "/loginViaRequestedScope")
    public String loginViaCompanyNumber() {
        return "loginRequestedScope";
    }

    @GetMapping(value = "/loginRequestedScope")
    public String attemptLoginCompanyNumber(
            @Valid @ModelAttribute("requestedScope") Scope scope,
            BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpServletRequest httpServletRequest) {
        if (result.hasErrors()) {
            List<FieldError> errorsList = result.getFieldErrors();
            model.addAttribute("errors", errorsList);
            return "loginRequestedScope";
        }
        redirectAttributes.addAttribute(SCOPE, scope.getRequestedScope());
        redirectAttributes.addAttribute("response_type", "code");
        redirectAttributes.addAttribute("client_id", clientId);
        redirectAttributes.addAttribute("redirect_uri", redirectUri);

        final HttpSession session = httpServletRequest.getSession();

        LOGGER.debug("[------SESSION------]", session);

        return "redirect:" + authoriseUri;
    }

    @GetMapping(value = "/redirect")
    public String handleRedirect(@RequestParam("code") String code, Model model)
            throws IOException {
        String accessToken = userAuthService.getAccessToken(code);
        LOGGER.debug("[---ACCESS TOKEN VALUE----]", accessToken);

        User user = userAuthService.getUserDetails(accessToken);
        userAuthService.storeUserDetails(user.getEmail(), accessToken);
        model.addAttribute("user", user);
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("query", new Query());
        return "loginResult";
    }
}