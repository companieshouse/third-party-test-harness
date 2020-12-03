package uk.gov.companieshouse.service;

import java.io.IOException;
import java.util.Map;
import uk.gov.companieshouse.model.User;

public interface UserAuthService {

    Map<String, String> getAccessTokenAndRefreshToken(String authCode) throws IOException;

    User getUserDetails(String accessToken);

    void storeUserDetails(String email, String accessToken, String refreshToken, long expiresIn);

}
