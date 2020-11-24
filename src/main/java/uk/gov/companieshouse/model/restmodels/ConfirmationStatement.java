
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
    "next_due",
    "overdue",
    "next_made_up_to"
})
public class ConfirmationStatement {

    @JsonProperty("next_due")
    private String nextDue;
    @JsonProperty("overdue")
    private Boolean overdue;
    @JsonProperty("next_made_up_to")
    private String nextMadeUpTo;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getNextDue() {
        return nextDue;
    }

    public void setNextDue(String nextDue) {
        this.nextDue = nextDue;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public String getNextMadeUpTo() {
        return nextMadeUpTo;
    }

    public void setNextMadeUpTo(String nextMadeUpTo) {
        this.nextMadeUpTo = nextMadeUpTo;
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
