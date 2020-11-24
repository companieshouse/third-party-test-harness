package uk.gov.companieshouse.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.companieshouse.model.UserTokenStore;
import uk.gov.companieshouse.service.IOAuthService;
import uk.gov.companieshouse.service.IUserService;

@Service
public class OAuthServiceImpl implements IOAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            OAuthServiceImpl.class);

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Value("${redirect-uri}")
    private String redirectUri;

    @Value("${token-uri}")
    private String tokenUri;

    private final IUserService userService;

    private UriComponentsBuilder tokenUriTemplate;
    private UriComponentsBuilder refreshTokenUriTemplate;
    private static final String AUTH_HEADER = "Authorization";

    @Autowired
    public OAuthServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void init(){
        this.tokenUriTemplate = UriComponentsBuilder.fromUriString(tokenUri)
                .query("code={code}")
                .query("grant_type={grantType}")
                .query("redirect_uri={redirectUri}");
        this.refreshTokenUriTemplate = UriComponentsBuilder.fromUriString(tokenUri)
                .query("refresh_token={refreshToken}")
                .query("grant_type={grantType}");
    }

    @Override
    public Map<String, String> getAccessTokenAndRefreshToken(String authCode) throws IOException{
        ResponseEntity<String> response;
        LOGGER.info("Authorization Code------{}", authCode);

        RestTemplate restTemplate = new RestTemplate();

        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        //Add secret and id to headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(AUTH_HEADER, "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<>(headers);

        //Auth server token uri, with access code from logon
        String accessTokenUrl = this.tokenUriTemplate.
                buildAndExpand(authCode, "authorization_code", redirectUri).toUriString();

        response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, request, String.class);

        String accessAndRefreshToken = response.getBody();
        LOGGER.info("Access Token Response ---------{}", accessAndRefreshToken);

        // Get the Access Token From the recieved JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(accessAndRefreshToken);
        String token = node.path("access_token").asText();
        String refresh = node.path("refresh_token").asText();
        String expiresIn = node.path("expires_in").asText();
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("access_token", token);
        returnMap.put("refresh_token", refresh);
        returnMap.put("expires_in", expiresIn);
        return returnMap;
    }

    @Override
    public String refreshAccessToken(UserTokenStore userTokenStore) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response;
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        //Add secret and id to headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(AUTH_HEADER, "Basic " + encodedCredentials);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String accessTokenUrl = refreshTokenUriTemplate.buildAndExpand(userTokenStore.getRefreshToken(), "refresh_token").toUriString();

        response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.getBody());
        String token = node.path("access_token").asText();

        userTokenStore.setAccessToken(token);
        userService.saveUserTokenStore(userTokenStore);
        return token;
    }
}
