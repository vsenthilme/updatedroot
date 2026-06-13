package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.appuser.AppUser;
import com.courier.overc360.api.idmaster.primary.model.user.UserManagement;
import com.courier.overc360.api.idmaster.service.AppUserService;
import com.courier.overc360.api.idmaster.service.UserManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin(origins = "*")
@Validated
@Api(tags = {"Login"}, value = "Login Operations related to User Login") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Login", description = "Operations related to Login")})
@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    AppUserService appUserService;

    // Validate User Login
    @ApiOperation(response = UserManagement.class, value = "Validate Login User") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> validateUserID(@RequestParam String userID, @RequestParam String password, @RequestParam String version) {
        log.info("UserID :" + userID + " - Password : " + password + " - Version : " + version);
        UserManagement validatedUser = userManagementService.validateUser(userID, password, version);
        log.info("Login : " + validatedUser);
        return new ResponseEntity<>(validatedUser, HttpStatus.OK);
    }

    // Validate User Login
    @ApiOperation(response = AppUser.class, value = "Validate Login AppUser") // label for swagger
    @GetMapping("/mobile")
    public ResponseEntity<?> validateAppUser(@RequestParam String appUserId, @RequestParam String password, @RequestParam String version, @RequestParam String appUserType) {
        log.info("appUserId :" + appUserId + " - Password : " + password + " - Version : " + version + " - App User Type : " + appUserType);
        AppUser validatedUser = appUserService.validateAppUser(appUserId, password, version,appUserType);
        log.info("Login : " + validatedUser);
        return new ResponseEntity<>(validatedUser, HttpStatus.OK);
    }

}
