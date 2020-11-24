
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
    "name",
    "effective_from",
    "ceased_on"
})
public class PreviousCompanyName {

    @JsonProperty("name")
    private String name;
    @JsonProperty("effective_from")
    private String effectiveFrom;
    @JsonProperty("ceased_on")
    private String ceasedOn;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getCeasedOn() {
        return ceasedOn;
    }

    public void setCeasedOn(String ceasedOn) {
        this.ceasedOn = ceasedOn;
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
