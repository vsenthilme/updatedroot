package com.mnrclara.api.setup.controller;

import com.mnrclara.api.setup.repository.TimeTicketTrackingRepository;
import com.mnrclara.api.setup.service.TimeTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mnrclara.api.setup.model.clientuser.ClientUser;
import com.mnrclara.api.setup.model.userprofile.UserProfile;
import com.mnrclara.api.setup.service.UserProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@Validated
@CrossOrigin(origins = "*")
@Api(tags = {"Login"}, value = "Login Operations related to User Login") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Login",description = "Operations related to Login")})
@RequestMapping("/login")
@RestController
public class LoginController {
	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	TimeTrackingService timeTrackingService;

    @ApiOperation(response = UserProfile.class, value = "Validate Login User") // label for swagger
	@GetMapping("")
	public ResponseEntity<?> validateUser(@RequestParam String userId, @RequestParam String password) {
    	log.info("UserId: " + userId + " - " + "Password: " + password);
    	UserProfile userProfile = userProfileService.validateUser(userId, password);
		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}
    
    //-------------------Client-Portal-UserLogin-------------------------------------------------
    @ApiOperation(response = UserProfile.class, value = "Validate Login User") 	// label for swagger
	@GetMapping("/clientUser/sendOTP")
	public ResponseEntity<?> sendOTP(@RequestParam String contactNumber) {
    	Boolean response = userProfileService.sendOTP(contactNumber);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    @ApiOperation(response = UserProfile.class, value = "Validate Login User") 	// label for swagger
   	@GetMapping("/clientUser/verifyOTP")
   	public ResponseEntity<?> verifyOTP (@RequestParam String contactNumber, @RequestParam Long otp) {
       	ClientUser clientUser = userProfileService.validateOtp (contactNumber, otp);
   		return new ResponseEntity<>(clientUser, HttpStatus.OK);
   	}
    
    @ApiOperation(response = UserProfile.class, value = "Send OTP") 			// label for swagger
   	@GetMapping("/clientUser/emailOTP")
   	public ResponseEntity<?> clientUserEmailOTP (@RequestParam String emailId) {
       	boolean response = userProfileService.clientUserEmailOTP(emailId);
   		return new ResponseEntity<>(response, HttpStatus.OK);
   	}
    
    @ApiOperation(response = UserProfile.class, value = "Send OTP") 			// label for swagger
	@GetMapping("/clientUser/verifyEmailOTP")
	public ResponseEntity<?> clientUserVerifyEmailOTP (@RequestParam String emailId, @RequestParam Long otp) {
    	ClientUser response = userProfileService.verifyEmailOTPForAppUser(emailId, otp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    //-----------------------EMAIL-OTP-----------------------------------------------------------
    @ApiOperation(response = UserProfile.class, value = "Send OTP") 			// label for swagger
	@GetMapping("/emailOTP")
	public ResponseEntity<?> emailOTP (@RequestParam String userId) {
    	boolean response = userProfileService.emailOTP(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// typeOfLogin if value present that is Portal, not value present that is mobile
    @ApiOperation(response = UserProfile.class, value = "Send OTP") 			// label for swagger
	@GetMapping("/verifyEmailOTP")
	public ResponseEntity<?> verifyEmailOTP (@RequestParam String userId, @RequestParam Long otp,
											 @RequestParam(required = false) Long typeOfLogin) {
    	UserProfile response = userProfileService.verifyEmailOTP(userId, otp);
		log.info("response - " + response);
		if(response != null && typeOfLogin == null ) {
			timeTrackingService.createTimeTracking(userId, response);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// @ApiOperation(response = UserProfile.class, value = "Send OTP") 			// label for swagger
	//	@GetMapping("/verifyEmailOTP")
	//	public ResponseEntity<?> verifyEmailOTP (@RequestParam String userId, @RequestParam Long otp) {
	//    	UserProfile response = userProfileService.verifyEmailOTP(userId, otp);
	//		log.info("response - " + response);
	//		if(response != null ) {
	//			timeTrackingService.createTimeTracking(userId, response);
	//		}
	//		return new ResponseEntity<>(response, HttpStatus.OK);
	//	}

	@ApiOperation(response = UserProfile.class, value = "Check Token")
	@PostMapping("/checkToken")
	public ResponseEntity<?> getToken(@RequestParam String userId, @Valid @RequestBody UserProfile userProfile){
		userProfileService.sendNotification(userId, userProfile);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}


}