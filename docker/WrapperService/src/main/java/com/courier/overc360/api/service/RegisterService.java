package com.courier.overc360.api.service;

import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.exception.BadRequestException;
import com.courier.overc360.api.repository.RegisterRepository;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.auth.AuthTokenRequest;
import com.courier.overc360.api.model.user.NewUser;
import com.courier.overc360.api.model.user.RegisteredUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
public class RegisterService {
	
	@Autowired
	private RegisterRepository registerRepository;

	@Autowired
	private AuthTokenService authTokenService;

	@Autowired
    PropertiesConfig propertiesConfig;
	
	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
	
	private String getIDMasterServiceApiUrl () {
		return propertiesConfig.getIdmasterServiceUrl();
	}
	
	public String getClientSecretID () {
		return propertiesConfig.getClientId();
	}
	

	public String validateRegisteredUser(@Valid RegisteredUser registeredUser) {
		Optional<NewUser> newUserOpt = registerRepository.findByRegisterId(registeredUser.getRegisterId());
		if (newUserOpt.isEmpty()) {
			throw new BadRequestException("Given Client Register ID: " + registeredUser.getRegisterId() + " does not exist.");
		}
		
		String clientSecretIDFromDB = newUserOpt.get().getClientSecretId();
		
		/*
		 * Client ID validation check
		 */
		if (registeredUser.getClientSecretId().equalsIgnoreCase(clientSecretIDFromDB)) {
			return propertiesConfig.getClientSecretKey();
		} else {
			throw new BadRequestException("Client Secret ID doesn't match. Please provide valid Client ID.");
		}
	}

	public NewUser registerNewUser (String clientName) {
		NewUser newUser = new NewUser();
		
		Optional<NewUser> newUserOpt = registerRepository.findByClientName(clientName);
		if (!newUserOpt.isEmpty()) {
			throw new BadRequestException("Already " + clientName + " exists.");
		}
		newUser.setClientName(clientName);
//		newUser.setRegisterId(getRandomUUID ());
		newUser.setClientSecretId(getClientSecretID());
		newUser = registerRepository.save(newUser);
		return newUser;
	}

	public AuthToken getAuthToken(@Valid AuthTokenRequest authTokenRequest) {
		return authTokenService.generateOAuthToken(authTokenRequest.getApiName(),
				authTokenRequest.getClientId(), 
				authTokenRequest.getClientSecretKey(),
				authTokenRequest.getGrantType(),
				authTokenRequest.getOauthUserName(),
				authTokenRequest.getOauthPassword());	
	}
}