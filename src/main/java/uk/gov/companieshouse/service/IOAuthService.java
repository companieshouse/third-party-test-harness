package uk.gov.companieshouse.service;

import java.io.IOException;
import java.util.Map;
import uk.gov.companieshouse.model.UserTokenStore;

public interface IOAuthService {
    Map<String, String> getAccessTokenAndRefreshToken(String authCode)throws IOException;
}
