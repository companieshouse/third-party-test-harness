package uk.gov.companieshouse.model.restmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CompanyNumber {

    @JsonProperty("companyNumber")
    @NotBlank(message = "field cannot be empty")
    @Size(max=8,min=8)
//    @Valid
    private String companyNumber;

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }
}