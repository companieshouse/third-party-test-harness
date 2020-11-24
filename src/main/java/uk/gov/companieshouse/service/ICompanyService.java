package uk.gov.companieshouse.service;

import uk.gov.companieshouse.model.UserTokenStore;
import uk.gov.companieshouse.model.restmodels.Company;
import java.io.IOException;

public interface ICompanyService {
    Company findCompany(UserTokenStore userTokenStore, String companyNumber, boolean retry) throws IOException;
    Company userNotPresentFindCompany(UserTokenStore user, boolean retry)throws IOException;
}
