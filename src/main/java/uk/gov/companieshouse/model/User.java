package uk.gov.companieshouse.model;

public class User {
    private String id;
    private String email;
    private String locale;
    private String scope;
    private String forename;
    private String surname;

    public User(String id, String email, String locale, String scope, String forename, String surname) {
        this.id = id;
        this.email = email;
        this.locale = locale;
        this.scope = scope;
        this.forename = forename;
        this.surname = surname;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
