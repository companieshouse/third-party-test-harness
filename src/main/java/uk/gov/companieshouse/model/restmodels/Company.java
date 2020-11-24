
package uk.gov.companieshouse.model.restmodels;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "company_name",
    "registered_office_address",
    "accounts",
    "type",
    "jurisdiction",
    "undeliverable_registered_office_address",
    "has_been_liquidated",
    "last_full_members_list_date",
    "company_number",
    "date_of_creation",
    "sic_codes",
    "etag",
    "has_insolvency_history",
    "company_status",
    "has_charges",
    "previous_company_names",
    "confirmation_statement",
    "links",
    "registered_office_is_in_dispute",
    "annual_return",
    "can_file"
})
public class Company {

    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("registered_office_address")
    private RegisteredOfficeAddress registeredOfficeAddress;
    @JsonProperty("accounts")
    private Accounts accounts;
    @JsonProperty("type")
    private String type;
    @JsonProperty("jurisdiction")
    private String jurisdiction;
    @JsonProperty("undeliverable_registered_office_address")
    private Boolean undeliverableRegisteredOfficeAddress;
    @JsonProperty("has_been_liquidated")
    private Boolean hasBeenLiquidated;
    @JsonProperty("last_full_members_list_date")
    private String lastFullMembersListDate;
    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("date_of_creation")
    private String dateOfCreation;
    @JsonProperty("sic_codes")
    private List<String> sicCodes = null;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("has_insolvency_history")
    private Boolean hasInsolvencyHistory;
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("has_charges")
    private Boolean hasCharges;
    @JsonProperty("previous_company_names")
    private List<PreviousCompanyName> previousCompanyNames = null;
    @JsonProperty("confirmation_statement")
    private ConfirmationStatement confirmationStatement;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("registered_office_is_in_dispute")
    private Boolean registeredOfficeIsInDispute;
    @JsonProperty("annual_return")
    private AnnualReturn annualReturn;
    @JsonProperty("can_file")
    private Boolean canFile;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public RegisteredOfficeAddress getRegisteredOfficeAddress() {
        return registeredOfficeAddress;
    }

    public void setRegisteredOfficeAddress(RegisteredOfficeAddress registeredOfficeAddress) {
        this.registeredOfficeAddress = registeredOfficeAddress;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Boolean getUndeliverableRegisteredOfficeAddress() {
        return undeliverableRegisteredOfficeAddress;
    }

    public void setUndeliverableRegisteredOfficeAddress(Boolean undeliverableRegisteredOfficeAddress) {
        this.undeliverableRegisteredOfficeAddress = undeliverableRegisteredOfficeAddress;
    }

    public Boolean getHasBeenLiquidated() {
        return hasBeenLiquidated;
    }

    public void setHasBeenLiquidated(Boolean hasBeenLiquidated) {
        this.hasBeenLiquidated = hasBeenLiquidated;
    }

    public String getLastFullMembersListDate() {
        return lastFullMembersListDate;
    }

    public void setLastFullMembersListDate(String lastFullMembersListDate) {
        this.lastFullMembersListDate = lastFullMembersListDate;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<String> getSicCodes() {
        return sicCodes;
    }

    public void setSicCodes(List<String> sicCodes) {
        this.sicCodes = sicCodes;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Boolean getHasInsolvencyHistory() {
        return hasInsolvencyHistory;
    }

    public void setHasInsolvencyHistory(Boolean hasInsolvencyHistory) {
        this.hasInsolvencyHistory = hasInsolvencyHistory;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public Boolean getHasCharges() {
        return hasCharges;
    }

    public void setHasCharges(Boolean hasCharges) {
        this.hasCharges = hasCharges;
    }

    public List<PreviousCompanyName> getPreviousCompanyNames() {
        return previousCompanyNames;
    }

    public void setPreviousCompanyNames(List<PreviousCompanyName> previousCompanyNames) {
        this.previousCompanyNames = previousCompanyNames;
    }

    public ConfirmationStatement getConfirmationStatement() {
        return confirmationStatement;
    }

    public void setConfirmationStatement(ConfirmationStatement confirmationStatement) {
        this.confirmationStatement = confirmationStatement;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Boolean getRegisteredOfficeIsInDispute() {
        return registeredOfficeIsInDispute;
    }

    public void setRegisteredOfficeIsInDispute(Boolean registeredOfficeIsInDispute) {
        this.registeredOfficeIsInDispute = registeredOfficeIsInDispute;
    }

    public AnnualReturn getAnnualReturn() {
        return annualReturn;
    }

    public void setAnnualReturn(AnnualReturn annualReturn) {
        this.annualReturn = annualReturn;
    }

    public Boolean getCanFile() {
        return canFile;
    }

    public void setCanFile(Boolean canFile) {
        this.canFile = canFile;
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
