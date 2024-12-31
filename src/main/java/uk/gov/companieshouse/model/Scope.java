package uk.gov.companieshouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class Scope {

    @JsonProperty("requestedScope")
    @NotBlank(message = "Requested Scope Cannot Be Empty")
    private String requestedScope;

    public String getRequestedScope() {
        return requestedScope;
    }

    public void setRequestedScope(String requestedScope) {
        this.requestedScope = requestedScope;
    }
}