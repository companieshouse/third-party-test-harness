
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
    "period_end_on",
    "due_on",
    "period_start_on",
    "overdue"
})
public class NextAccounts {

    @JsonProperty("period_end_on")
    private String periodEndOn;
    @JsonProperty("due_on")
    private String dueOn;
    @JsonProperty("period_start_on")
    private String periodStartOn;
    @JsonProperty("overdue")
    private Boolean overdue;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getPeriodEndOn() {
        return periodEndOn;
    }

    public void setPeriodEndOn(String periodEndOn) {
        this.periodEndOn = periodEndOn;
    }

    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    public String getPeriodStartOn() {
        return periodStartOn;
    }

    public void setPeriodStartOn(String periodStartOn) {
        this.periodStartOn = periodStartOn;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
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
