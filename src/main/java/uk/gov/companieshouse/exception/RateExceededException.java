package uk.gov.companieshouse.exception;

import java.io.IOException;
import uk.gov.companieshouse.model.UserTokenStore;

public class RateExceededException extends IOException {
    private final UserTokenStore user;

    public RateExceededException(UserTokenStore user) {
        this.user = user;
    }

    public UserTokenStore getUser() {
        return user;
    }
}
