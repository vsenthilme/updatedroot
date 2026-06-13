package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.user.AddUserManagement;
import com.courier.overc360.api.idmaster.primary.model.user.UpdateUserManagement;
import com.courier.overc360.api.idmaster.primary.model.user.UserManagement;
import com.courier.overc360.api.idmaster.replica.model.user.ReplicaUserManagement;
import com.courier.overc360.api.idmaster.replica.model.user.FindUserManagement;
import com.courier.overc360.api.idmaster.service.UserManagementService;
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
import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"UserManagement"}, value = "UserManagement  Operations related to UserManagementController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "UserManagement ", description = "Operations related to UserManagement ")})
@RequestMapping("/usermanagement")
@RestController
public class UserManagementController {

    @Autowired
    UserManagementService userManagementService;


    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create UserManagement
    @ApiOperation(response = UserManagement.class, value = "Create new UserManagement") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postUserManagement(@Valid @RequestBody AddUserManagement addUserManagement, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        UserManagement createdUserManagement = userManagementService.createUserManagement(addUserManagement, loginUserID);
        return new ResponseEntity<>(createdUserManagement, HttpStatus.OK);
    }

    //Update UserManagement
    @ApiOperation(response = UserManagement.class, value = "Update UserManagement") // label for swagger
    @PatchMapping("/{userId}")
    public ResponseEntity<?> patchUserManagement(@PathVariable String userId, @RequestParam String companyId, @RequestParam String languageId,
                                                 @RequestBody UpdateUserManagement updateUserManagement, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        UserManagement updatedUserManagement =
                userManagementService.updateUserManagement(userId, companyId, languageId, updateUserManagement, loginUserID);
        return new ResponseEntity<>(updatedUserManagement, HttpStatus.OK);
    }

    //Delete UserManagement
    @ApiOperation(response = UserManagement.class, value = "Delete UserManagement") // label for swagger
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserManagement(@PathVariable String userId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String loginUserID) {
        userManagementService.deleteUserManagement(userId, languageId, companyId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    //Get all
    @ApiOperation(response = ReplicaUserManagement.class, value = "Get all UserManagement details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<ReplicaUserManagement> userManagementList = userManagementService.replicaGetUserManagements();
        return new ResponseEntity<>(userManagementList, HttpStatus.OK);
    }

    //Get UserManagement
    @ApiOperation(response = ReplicaUserManagement.class, value = "Get a UserManagement") // label for swagger
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserManagement(@PathVariable String userId, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaUserManagement userManagement = userManagementService.replicaGetUserManagement(languageId, companyId, userId);
        return new ResponseEntity<>(userManagement, HttpStatus.OK);
    }

    //Find UserManagement
    @ApiOperation(response = ReplicaUserManagement.class, value = "Find UserManagement") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findUserManagement(@Valid @RequestBody FindUserManagement findUserManagement) throws Exception {
        List<ReplicaUserManagement> userManagementList = userManagementService.findUserManagement(findUserManagement);
        return new ResponseEntity<>(userManagementList, HttpStatus.OK);
    }

}