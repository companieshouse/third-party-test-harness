package uk.gov.companieshouse.service;

import java.util.List;
import uk.gov.companieshouse.exception.NoUserOfIdException;
import uk.gov.companieshouse.model.User;
import uk.gov.companieshouse.model.UserTokenStore;

public interface IUserService {
    User getUserDetails(String accessToken);
    void storeUserDetails(String email, String accessToken, String refreshToken, long expiresIn);
}
