package uk.gov.companieshouse.service.impl;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.exception.RateExceededException;
import uk.gov.companieshouse.exception.RestTemplateResponseErrorHandler;
import uk.gov.companieshouse.exception.UserNotAuthorisedException;
import uk.gov.companieshouse.model.UserTokenStore;
import uk.gov.companieshouse.model.restmodels.Company;
import uk.gov.companieshouse.service.ICompanyService;
import uk.gov.companieshouse.service.IOAuthService;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Value("${protected-uri}")
    private String protectedUri;

    private final IOAuthService oAuthService;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_HEADER = "Bearer ";

    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            CompanyServiceImpl.class);

    @Autowired
    public CompanyServiceImpl(IOAuthService oAuthService,  RestTemplateBuilder restTemplateBuilder) {
        this.oAuthService = oAuthService;
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }
    
    @Override
    public Company findCompany(UserTokenStore userTokenStore, String companyNumber, boolean retry)throws IOException {
        // Use the access token for retrieving protected info
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER, BEARER_HEADER + userTokenStore.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Company> responseEntity = restTemplate
                .exchange(protectedUri + companyNumber, HttpMethod.GET, entity, Company.class);

        if(responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        }else if(responseEntity.getStatusCodeValue() == 429){
            throw new RateExceededException(userTokenStore);
        }else if(responseEntity.getStatusCodeValue() == 401){
            LOGGER.warn("{} access token expired, attempting refresh", userTokenStore.getEmail());
            String newToken = oAuthService.refreshAccessToken(userTokenStore);
            LOGGER.info("New token: {}", newToken);

            if(retry) {
                return findCompany(userTokenStore, companyNumber, false);
            }else{
                throw new UserNotAuthorisedException();
            }
        }else{
            throw new IOException();
        }

    }

    @Override
    public Company userNotPresentFindCompany(UserTokenStore user, boolean retry) throws IOException {
        ResponseEntity<Company> responseEntity;
        // Use the access token for authentication
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER, BEARER_HEADER + user.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        responseEntity = restTemplate.exchange(protectedUri + "00006400", HttpMethod.GET, entity, Company.class);
        if(responseEntity.getStatusCodeValue() == 200) {
            if (responseEntity.getBody() != null) {
                LOGGER.info("{} authenticated, Response: {} {}",
                        user.getEmail(),
                        responseEntity.getBody().getCompanyName(),
                        responseEntity.getBody().getCompanyNumber());
            }
            return responseEntity.getBody();
        }else if(responseEntity.getStatusCodeValue() == 401){
            LOGGER.warn("{} access token expired, attempting refresh", user.getEmail());
            String newToken = oAuthService.refreshAccessToken(user);
            LOGGER.info("New token: {}", newToken);

            if(retry) {
                return userNotPresentFindCompany(user, false);
            }else{
                throw new UserNotAuthorisedException();
            }
        }else if(responseEntity.getStatusCodeValue() == 429){
            throw new RateExceededException(user);
        }
        else{
            throw new IOException();
        }

    }
}
