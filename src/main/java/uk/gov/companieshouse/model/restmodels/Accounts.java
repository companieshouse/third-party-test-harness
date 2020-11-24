
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
    "accounting_reference_date",
    "next_due",
    "overdue",
    "last_accounts",
    "next_made_up_to",
    "next_accounts"
})
public class Accounts {

    @JsonProperty("accounting_reference_date")
    private AccountingReferenceDate accountingReferenceDate;
    @JsonProperty("next_due")
    private String nextDue;
    @JsonProperty("overdue")
    private Boolean overdue;
    @JsonProperty("last_accounts")
    private LastAccounts lastAccounts;
    @JsonProperty("next_made_up_to")
    private String nextMadeUpTo;
    @JsonProperty("next_accounts")
    private NextAccounts nextAccounts;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public AccountingReferenceDate getAccountingReferenceDate() {
        return accountingReferenceDate;
    }

    public void setAccountingReferenceDate(AccountingReferenceDate accountingReferenceDate) {
        this.accountingReferenceDate = accountingReferenceDate;
    }

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

    public LastAccounts getLastAccounts() {
        return lastAccounts;
    }

    public void setLastAccounts(LastAccounts lastAccounts) {
        this.lastAccounts = lastAccounts;
    }

    public String getNextMadeUpTo() {
        return nextMadeUpTo;
    }

    public void setNextMadeUpTo(String nextMadeUpTo) {
        this.nextMadeUpTo = nextMadeUpTo;
    }

    public NextAccounts getNextAccounts() {
        return nextAccounts;
    }

    public void setNextAccounts(NextAccounts nextAccounts) {
        this.nextAccounts = nextAccounts;
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
