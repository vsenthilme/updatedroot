package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.usertype.AddUserType;
import com.courier.overc360.api.idmaster.primary.model.usertype.UpdateUserType;
import com.courier.overc360.api.idmaster.primary.model.usertype.UserType;
import com.courier.overc360.api.idmaster.replica.model.usertype.ReplicaUserType;
import com.courier.overc360.api.idmaster.replica.model.usertype.FindUserType;
import com.courier.overc360.api.idmaster.service.UserTypeService;
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
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"UserType"}, value = "UserType  Operations related to UserTypeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "UserType ",description = "Operations related to UserType ")})
@RequestMapping("/userType")
@RestController
public class UserTypeController {
	
	@Autowired
	UserTypeService usertypeService;

	/*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

	//Create UserType
    @ApiOperation(response = UserType.class, value = "Create UserType") // label for swagger
	@PostMapping("")
	public ResponseEntity<?> postUserType(@Valid @RequestBody AddUserType addUserType,
			@RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException, ParseException {
		UserType createdUserType = usertypeService.createUserType(addUserType, loginUserID);
		return new ResponseEntity<>(createdUserType , HttpStatus.OK);
	}

	//Update UserType
    @ApiOperation(response = UserType.class, value = "Update UserType") // label for swagger
    @PatchMapping("/{userTypeId}")
	public ResponseEntity<?> patchUserType(@PathVariable Long userTypeId, @RequestParam String companyId, @RequestParam String languageId,
										   @Valid @RequestBody UpdateUserType updateUserType, @RequestParam String loginUserID)
			throws IllegalAccessException, InvocationTargetException, ParseException {
		UserType createdUserType =
				usertypeService.updateUserType(userTypeId, companyId, languageId, loginUserID, updateUserType);
		return new ResponseEntity<>(createdUserType , HttpStatus.OK);
	}

	//Delete UserType
    @ApiOperation(response = UserType.class, value = "Delete UserType") // label for swagger
	@DeleteMapping("/{userTypeId}")
	public ResponseEntity<?> deleteUserType(@PathVariable Long userTypeId, @RequestParam String companyId,
											  @RequestParam String languageId, @RequestParam String loginUserID) {
    	usertypeService.deleteUserType(userTypeId,companyId,languageId,loginUserID);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/*-----------------------------------------------REPLICA---------------------------------------------------------------*/

	//Get all UserTypes
	@ApiOperation(response = ReplicaUserType.class, value = "Get all UserType details") // label for swagger
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		List<ReplicaUserType> usertypeList = usertypeService.getAllUserTypeIds();
		return new ResponseEntity<>(usertypeList, HttpStatus.OK);
	}

	//Get a UserType
	@ApiOperation(response = ReplicaUserType.class, value = "Get a UserType") // label for swagger
	@GetMapping("/{userTypeId}")
	public ResponseEntity<?> getUserType(@PathVariable Long userTypeId,@RequestParam String companyId,
										   @RequestParam String languageId) {
		ReplicaUserType usertype =
				usertypeService.getReplicaUserType(userTypeId,companyId,languageId);
		log.info("UserType : " + usertype);
		return new ResponseEntity<>(usertype, HttpStatus.OK);
	}
	//Find UserTypes
	@ApiOperation(response = ReplicaUserType.class, value = "Find UserType") // label for swagger
	@PostMapping("/find")
	public ResponseEntity<?> findUserType(@Valid @RequestBody FindUserType findUserType) throws Exception {
		List<ReplicaUserType> createdUserTypeId = usertypeService.findUserType(findUserType);
		return new ResponseEntity<>(createdUserTypeId, HttpStatus.OK);
	}
}