package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.appuser.AddAppUser;
import com.courier.overc360.api.idmaster.primary.model.appuser.AppUser;
import com.courier.overc360.api.idmaster.primary.model.appuser.UpdateAppUser;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.AddConsignmentType;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.ConsignmentType;
import com.courier.overc360.api.idmaster.primary.model.consignmentType.UpdateConsignmentType;
import com.courier.overc360.api.idmaster.replica.model.appuser.FindAppUser;
import com.courier.overc360.api.idmaster.replica.model.appuser.ReplicaAppUser;
import com.courier.overc360.api.idmaster.replica.model.consignmentType.FindConsignmentType;
import com.courier.overc360.api.idmaster.replica.model.consignmentType.ReplicaConsignmentType;
import com.courier.overc360.api.idmaster.service.AppUserService;
import com.courier.overc360.api.idmaster.service.ConsignmentTypeService;
import com.opencsv.exceptions.CsvException;
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

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"AppUser"}, value = "AppUser  Operations related to AppUserController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "AppUser", description = "Operations related to AppUser")})
@RequestMapping("/appUser")
@RestController
public class AppUserController {
    @Autowired
    AppUserService appUserService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create AppUser
    @ApiOperation(response = AppUser.class, value = "Create AppUser") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postAppUser(@Valid @RequestBody AddAppUser addAppUser, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        AppUser appUser = appUserService.createAppUser(addAppUser, loginUserID);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    // Update AppUser
    @ApiOperation(response = AppUser.class, value = "Update AppUser") // label for swagger
    @PatchMapping("/{appUserId}")
    public ResponseEntity<?> patchAppUser(@PathVariable String appUserId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestParam String loginUserID, @RequestBody UpdateAppUser updateAppUser)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        AppUser updatedAppUser =
                appUserService.updateAppUser(companyId, languageId, appUserId, loginUserID, updateAppUser);
        return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
    }

    // Delete AppUser
    @ApiOperation(response = AppUser.class, value = "Delete AppUser") // label for swagger
    @DeleteMapping("/{appUserId}")
    public ResponseEntity<?> deleteAppUser(@PathVariable String appUserId, @RequestParam String languageId, @RequestParam String companyId,
                                                   @RequestParam String loginUserID) {
        appUserService.deleteAppUser(companyId, languageId, appUserId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All AppUser Details
    @ApiOperation(response = ReplicaAppUser.class, value = "Get all ReplicaAppUser details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllAppUsers() {
        List<ReplicaAppUser> appUserList = appUserService.getAllAppUsers();
        return new ResponseEntity<>(appUserList, HttpStatus.OK);
    }

    // Get AppUser
    @ApiOperation(response = ReplicaAppUser.class, value = "Get a ReplicaAppUser") // label for swagger
    @GetMapping("/{appUserId}")
    public ResponseEntity<?> getAppUser(@PathVariable String appUserId, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaAppUser dbAppUser = appUserService.getReplicaAppUser(companyId, languageId, appUserId);
        return new ResponseEntity<>(dbAppUser, HttpStatus.OK);
    }

    // Find AppUser
    @ApiOperation(response = ReplicaAppUser.class, value = "Find ReplicaAppUser") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findAppUsers(@Valid @RequestBody FindAppUser findAppUser) throws Exception {
        List<ReplicaAppUser> appUserList = appUserService.findAppUsers(findAppUser);
        return new ResponseEntity<>(appUserList, HttpStatus.OK);
    }

}
