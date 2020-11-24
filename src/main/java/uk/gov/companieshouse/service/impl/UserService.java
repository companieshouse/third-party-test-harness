package uk.gov.companieshouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.exception.NoUserOfIdException;
import uk.gov.companieshouse.exception.RestTemplateResponseErrorHandler;
import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.model.UserTokenStore;
import uk.gov.companieshouse.service.IUserService;

@Service
public class UserService implements IUserService {

    @Value("${user-uri}")
    private String userUri;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_HEADER = "Bearer ";

    private Map<String, UserTokenStore> userTokenStoreMap;

    private final RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplateBuilder restTemplateBuilder) {
        this.userTokenStoreMap = new HashMap<>();
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Override
    public User getUserDetails(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER, BEARER_HEADER + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User> responseEntity = restTemplate.exchange(userUri, HttpMethod.GET, entity, User.class);
        return responseEntity.getBody();
    }

    @Override
    public void storeUserDetails(String email, String accessToken, String refreshToken, long expiresIn) {
            UserTokenStore userToken = new UserTokenStore(email, accessToken, refreshToken, (System.currentTimeMillis() / 1000L) + 3600);
            userTokenStoreMap.put(email, userToken);
    }

    @Override
    public List<UserTokenStore> findAllUsers() {
        List<UserTokenStore> allUsers = new ArrayList<>();
        for (Map.Entry<String, UserTokenStore> entry : userTokenStoreMap.entrySet()) {
            allUsers.add(entry.getValue());
        }
        return allUsers;
    }

    @Override
    public UserTokenStore findByEmail(String email) throws NoUserOfIdException {
        if(userTokenStoreMap.containsKey(email)){
            return userTokenStoreMap.get(email);
        }

        throw new NoUserOfIdException();
    }

    @Override
    public void saveUserTokenStore(UserTokenStore userTokenStore) {
        userTokenStoreMap.put(userTokenStore.getEmail(), userTokenStore);
    }

    @Override
    public UserTokenStore findByAccessToken(String accessToken) throws NoUserOfIdException{
        for (Map.Entry<String, UserTokenStore> entry : userTokenStoreMap.entrySet()) {
            if(entry.getValue().getAccessToken().equals(accessToken)){
                return entry.getValue();
            }
        }

        throw new NoUserOfIdException();
    }
}
