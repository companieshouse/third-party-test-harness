package uk.gov.companieshouse.controllers;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.model.Scope;
import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.service.UserAuthService;

@SpringBootTest
@AutoConfigureMockMvc
public class ThirdPartyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthService service;

    private Scope scope;
    private User user;

    private static final String REQUESTED_SCOPE = "https://identity.company-information.service.gov.uk/user/profile.read https://api.company-information.service.gov.uk/company/00000640/registered-office-address.update";
    private static final String CODE = "CODE";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String USER_EMAIL = "USER_EMAIL";

    @Test
    @DisplayName("Test login page can be reached")
    void testSuccessfulGetHomePage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Test that the user sign in page can be reached and redirect")
    void testGetSuccessfulAttemptLogin() throws Exception {
        mockMvc.perform(get("/attemptLogin"))
                .andExpect(status().is3xxRedirection());
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Test that the requested sign in scope page can be reached and redirect")
    void testGetSuccessfulLoginRequestedScope() throws Exception {
        scope = new Scope();
        scope.setRequestedScope(REQUESTED_SCOPE);
        mockMvc.perform(get("/loginRequestedScope").flashAttr("requestedScope", scope))
                .andExpect(status().is3xxRedirection());
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Test that the requested sign in scope page can't be reached as the scope is empty")
    void testGetUnsuccessfulLoginEmptyScope() throws Exception {
        scope = new Scope();
        mockMvc.perform(get("/loginRequestedScope").flashAttr("requestedScope", scope))
                .andExpect(status().isOk())
                .andExpect(view().name("loginRequestedScope"));
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Test that the redirect page is reached after interaction with account")
    void testGetSuccessfulRedirectPage() throws Exception {
        user = new User();
        user.setEmail(USER_EMAIL);
        when(service.getAccessToken(CODE)).thenReturn(ACCESS_TOKEN);
        when(service.getUserDetails(ACCESS_TOKEN)).thenReturn(user);
        mockMvc.perform(get("/redirect").param("code", CODE))
                .andExpect(status().isOk())
                .andExpect(view().name("loginResult"));
    }

}
