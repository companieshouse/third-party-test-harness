
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
    "type",
    "period_start_on",
    "period_end_on",
    "made_up_to"
})
public class LastAccounts {

    @JsonProperty("type")
    private String type;
    @JsonProperty("period_start_on")
    private String periodStartOn;
    @JsonProperty("period_end_on")
    private String periodEndOn;
    @JsonProperty("made_up_to")
    private String madeUpTo;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeriodStartOn() {
        return periodStartOn;
    }

    public void setPeriodStartOn(String periodStartOn) {
        this.periodStartOn = periodStartOn;
    }

    public String getPeriodEndOn() {
        return periodEndOn;
    }

    public void setPeriodEndOn(String periodEndOn) {
        this.periodEndOn = periodEndOn;
    }

    public String getMadeUpTo() {
        return madeUpTo;
    }

    public void setMadeUpTo(String madeUpTo) {
        this.madeUpTo = madeUpTo;
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
