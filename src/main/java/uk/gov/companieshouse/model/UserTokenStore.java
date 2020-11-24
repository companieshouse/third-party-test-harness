package uk.gov.companieshouse.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserTokenStore {

    private String email;
    private String accessToken;
    private String refreshToken;
    private long expires;

    public UserTokenStore() {
    }

    public UserTokenStore(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getExpires() {
        return Instant.ofEpochSecond(expires).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
}
