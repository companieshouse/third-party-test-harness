
package uk.gov.companieshouse.model.restmodels;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self",
    "filing_history",
    "officers"
})
public class Links {

    @JsonProperty("self")
    private String self;
    @JsonProperty("filing_history")
    private String filingHistory;
    @JsonProperty("officers")
    private String officers;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getFilingHistory() {
        return filingHistory;
    }

    public void setFilingHistory(String filingHistory) {
        this.filingHistory = filingHistory;
    }

    public String getOfficers() {
        return officers;
    }

    public void setOfficers(String officers) {
        this.officers = officers;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
