package uk.gov.companieshouse.model;

public class UserTokenStore {

    private String email;
    private String accessToken;
    private String refreshToken;
    private long expires;

    public UserTokenStore(String email, String accessToken, String refreshToken, long expires) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expires = expires;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
