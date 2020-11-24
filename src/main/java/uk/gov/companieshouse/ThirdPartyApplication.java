package uk.gov.companieshouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThirdPartyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyApplication.class, args);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyApplication.class);

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Value(("${redirect-uri}"))
    private String redirectUri;

    @Override
    public void run(String... args) throws Exception {
        boolean startUpError = false;

        if(clientId == null || clientId.isEmpty()){
            LOGGER.warn("No client id set in application.properties, please create one");
            startUpError = true;
        }

        if(clientSecret == null || clientSecret.isEmpty()){
            LOGGER.warn("No client secret set in application.properties, please create one");
            startUpError = true;
        }

        if(redirectUri == null || redirectUri.isEmpty()){
            LOGGER.warn("No redirect URI set in application.properties, please create one");
            startUpError = true;
        }

        if(startUpError){
            System.exit(-1);
        }
    }
}
