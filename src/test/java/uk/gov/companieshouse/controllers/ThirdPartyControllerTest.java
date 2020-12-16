package uk.gov.companieshouse.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.companieshouse.service.UserAuthService;
import uk.gov.companieshouse.service.impl.UserAuthServiceImpl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.verifyNoInteractions;
import org.springframework.test.context.web.WebAppConfiguration;

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
////@RunWith(SpringJUnitClassRunner.class)
//@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
//@WebAppConfiguration
@AutoConfigureMockMvc
public class ThirdPartyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

//    @BeforeEach
//    public void test(TestInfo info) throws Exception {
//        //Mockito.reset(service);
//        mockMvc = MockMvcBuilders.standaloneSetup(new ThirdPartyController(service)).build();
//        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    @Test
    @DisplayName("Test login page can be reached")
    void testSuccessfulGetHomePage() throws Exception{
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Test that the user sign in page can be reached and redirect")
    void testGetSuccessfulAttemptLogin() throws Exception {
        mockMvc.perform(get("/attemptLogin"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test that the requested sign in scope page can be reached and redirect")
    void testGetSuccessfulLoginRequestedScope() {

    }

    @Test
    @DisplayName("Test that the redirect page is reached after interaction with account")
    void testGetSuccessfulRedirectPage() {

    }

}
