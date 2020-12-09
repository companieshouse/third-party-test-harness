package uk.gov.companieshouse.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.model.UserTokenStore;

@ExtendWith(MockitoExtension.class)
public class UserAuthServiceImplTest {
    @Mock
    private RestTemplate restTemplate;
    
    private UserAuthServiceImpl service;
    
    @BeforeEach
    void setup() {
        RestTemplateBuilder restTemplateBuilder = Mockito.mock(RestTemplateBuilder.class);
        
        when(restTemplateBuilder.errorHandler(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        
        service = new UserAuthServiceImpl(restTemplateBuilder);
    
        service.userUri = "USER_URI";
        service.tokenUri = "TOKEN_URI";
        service.redirectUri = "REDIRECT_URI";
        service.clientId = "CLIENT";
        service.clientSecret = "SECRET";
        service.init();
    }

    @Test
    void getAccessToken() throws IOException {
        final String expectedAccessToken = "ACCESS_TOKEN";
        final String expectedEncodedCredentials = new String(Base64.getEncoder().encode((service.clientId + ":" + service.clientSecret).getBytes()));
        final String authCode = "code";

        String accessTokenUrl = service.tokenUri + "?code=" + authCode + "&grant_type=authorization_code&redirect_uri="
                + service.redirectUri;
        ResponseEntity<String> responseEntity = Mockito.mock(ResponseEntity.class);

        when(restTemplate.exchange(eq(accessTokenUrl), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(responseEntity);

        String returnTokenString = "{\"access_token\":\"" + expectedAccessToken + "\"}";
        when(responseEntity.getBody()).thenReturn(returnTokenString);

        String accessToken = service.getAccessToken(authCode);
        assertEquals(expectedAccessToken, accessToken);

        ArgumentCaptor<HttpEntity<String>> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).exchange(eq(accessTokenUrl), eq(HttpMethod.POST), entityCaptor.capture(), eq(String.class));

        HttpEntity<String> entity = entityCaptor.getValue();
        assertTrue(entity.getHeaders().containsKey("Authorization"));
        assertEquals(1, entity.getHeaders().get("Authorization").size());
        assertEquals("Basic " + expectedEncodedCredentials, entity.getHeaders().get("Authorization").get(0));

        assertTrue(entity.getHeaders().containsKey("Accept"));
        assertEquals(1, entity.getHeaders().get("Accept").size());
        assertEquals("application/json", entity.getHeaders().get("Accept").get(0));
    }

    @Test
    void getUserDetails() {
        User returnedUser = new User();
        final String accessToken = "TOKEN";
        
        ResponseEntity<User> responseEntity = Mockito.mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(returnedUser);
        
        when(restTemplate.exchange(eq(service.userUri), eq(HttpMethod.GET), any(), eq(User.class)))
            .thenReturn(responseEntity);
        
        User user = service.getUserDetails(accessToken);
        
        assertEquals(returnedUser, user);
        
        ArgumentCaptor<HttpEntity<String>> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).exchange(eq(service.userUri), eq(HttpMethod.GET), entityCaptor.capture(), eq(User.class));
        
        HttpEntity<String> entity = entityCaptor.getValue();
        assertEquals(1, entity.getHeaders().size());
        assertTrue(entity.getHeaders().containsKey("Authorization"));
        assertEquals(1, entity.getHeaders().get("Authorization").size());
        assertEquals("Bearer " + accessToken, entity.getHeaders().get("Authorization").get(0));
    }
    

    @Test
    void storeUserDetails() {
        String accessToken = "PPAK";
        String email = "email@domain.com";
        service.storeUserDetails(email, accessToken);
    
        assertTrue(service.userTokenStoreMap.containsKey(email));
        UserTokenStore userToken = service.userTokenStoreMap.get(email);
        assertEquals(email, userToken.getEmail());
        assertEquals(accessToken, userToken.getAccessToken());
    }
}
