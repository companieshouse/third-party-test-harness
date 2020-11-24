package uk.gov.companieshouse.exception;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler
implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        //Don't throw any exceptions, erroneous responses handled in methods
    }
}
