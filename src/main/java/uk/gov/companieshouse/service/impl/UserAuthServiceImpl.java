package uk.gov.companieshouse.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.companieshouse.exception.RestTemplateResponseErrorHandler;
import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.model.UserTokenStore;
import uk.gov.companieshouse.service.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Value("${redirect-uri}")
    private String redirectUri;

    @Value("${token-uri}")
    private String tokenUri;

    @Value("${user-uri}")
    private String userUri;

    private Map<String, UserTokenStore> userTokenStoreMap;
    private final RestTemplate restTemplate;
    private UriComponentsBuilder tokenUriTemplate;
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_HEADER = "Bearer ";

    @PostConstruct
    private void init() {
        this.tokenUriTemplate = UriComponentsBuilder.fromUriString(tokenUri)
                .query("code={code}")
                .query("grant_type={grantType}")
                .query("redirect_uri={redirectUri}");
    }

    @Autowired
    public UserAuthServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.userTokenStoreMap = new HashMap<>();
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    @Override
    public String getAccessToken(String authCode) throws IOException {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

        //Add secret and id to headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(AUTH_HEADER, "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<>(headers);

        //Auth server token uri, with access code from logon
        String accessTokenUrl = this.tokenUriTemplate.
                buildAndExpand(authCode, "authorization_code", redirectUri).toUriString();

        ResponseEntity<String> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, request, String.class);

        // Get the Access Token From the recieved JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.getBody());
        return node.path("access_token").asText();
    }

    @Override
    public User getUserDetails(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER, BEARER_HEADER + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User> responseEntity = restTemplate
                .exchange(userUri, HttpMethod.GET, entity, User.class);
        return responseEntity.getBody();
    }

    @Override
    public void storeUserDetails(String email, String accessToken) {
        UserTokenStore userToken = new UserTokenStore(email, accessToken);
        userTokenStoreMap.put(email, userToken);
    }
}
