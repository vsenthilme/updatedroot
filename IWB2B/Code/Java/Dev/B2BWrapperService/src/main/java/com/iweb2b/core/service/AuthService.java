package com.iweb2b.core.service;

import com.iweb2b.core.config.PropertiesConfig;
import com.iweb2b.core.exception.BadRequestException;
import com.iweb2b.core.model.auth.AuthToken;
import com.iweb2b.core.model.auth.CustomerAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private static final String BASIC = "Basic ";
    @Autowired
    IntegrationService integrationService;

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    PropertiesConfig propertiesConfig;

    public boolean isMatching(String authorization) {
        String lifetimeToken = propertiesConfig.getB2bIntegrationToken();

        if (!authorization.startsWith(BASIC)) {
            throw new BadRequestException(
                    "Authorization should be supplied prefixing with Basic<space> " + authorization);
        }

        String passedToken = authorization.substring(6);
        log.info("extracted {} ", passedToken);
        AuthToken integAuthToken = authTokenService.getIntegrationServiceAuthToken();
        CustomerAccessToken customerToken = integrationService.getCustomerToken(passedToken,
                integAuthToken.getAccess_token());
        boolean matched = false;

        if (lifetimeToken.equalsIgnoreCase(passedToken)) {
            matched = true;
        } else if (customerToken.getToken().equalsIgnoreCase(passedToken)) {
            matched = true;
        } else {
            matched = false;
        }

        return matched;
    }

    public void validateAuthorization(String authorization) {
        if(!isMatching(authorization)) {
            throw new BadRequestException("Password wrong. Please enter correct password.");
        }
    }
}
