package uk.gov.companieshouse.model;

public class Query {
    private String email;
    private String companyNumber;

    public Query() {
    }

    public Query(String email, String companyNumber) {
        this.email = email;
        this.companyNumber = companyNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }
}
