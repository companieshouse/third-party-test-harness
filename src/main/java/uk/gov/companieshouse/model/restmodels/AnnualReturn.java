
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
    "overdue",
    "last_made_up_to"
})
public class AnnualReturn {

    @JsonProperty("overdue")
    private Boolean overdue;
    @JsonProperty("last_made_up_to")
    private String lastMadeUpTo;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public String getLastMadeUpTo() {
        return lastMadeUpTo;
    }

    public void setLastMadeUpTo(String lastMadeUpTo) {
        this.lastMadeUpTo = lastMadeUpTo;
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
