package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.roleaccess.AddRoleAccess;
import com.courier.overc360.api.idmaster.primary.model.roleaccess.RoleAccess;
import com.courier.overc360.api.idmaster.primary.model.roleaccess.UpdateRoleAccess;
import com.courier.overc360.api.idmaster.replica.model.roleaccess.FindRoleAccess;
import com.courier.overc360.api.idmaster.replica.model.roleaccess.ReplicaRoleAccess;
import com.courier.overc360.api.idmaster.service.RoleAccessService;
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
@Api(tags = {"RoleAccess"}, value = "RoleAccess  Operations related to RoleAccessController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "RoleAccess", description = "Operations related to RoleAccess")})
@RequestMapping("/roleAccess")
@RestController
public class RoleAccessController {

    @Autowired
    RoleAccessService roleAccessService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------*/

    // Create RoleAccess
    @ApiOperation(response = RoleAccess.class, value = "Create RoleAccess") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postRoleAccess(@Valid @RequestBody List<AddRoleAccess> newRoleAccess, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        List<RoleAccess> createdRoleAccess = roleAccessService.createRoleAccess(newRoleAccess, loginUserID);
        return new ResponseEntity<>(createdRoleAccess, HttpStatus.OK);
    }

    // Update RoleAccess
//    @ApiOperation(response = RoleAccess.class, value = "Update RoleAccess") // label for swagger
//    @PatchMapping("/{roleId}")
//    public ResponseEntity<?> patchRoleAccess(@PathVariable Long roleId, @RequestParam Long menuId, @RequestParam String companyId,
//                                             @RequestParam String languageId, @RequestParam Long subMenuId,
//                                             @Valid @RequestBody UpdateRoleAccess updateRoleAccess, @RequestParam String loginUserID)
//            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
//        RoleAccess updatedRoleAccess = roleAccessService.updateRoleAccess(companyId, languageId, roleId, menuId,
//                subMenuId, loginUserID, updateRoleAccess);
//        return new ResponseEntity<>(updatedRoleAccess, HttpStatus.OK);
//    }

    @ApiOperation(response = RoleAccess.class, value = "Update RoleAccess ") // label for swagger
    @PatchMapping("/{roleId}")
    public ResponseEntity<?> patchRoleAccess(@PathVariable Long roleId, @RequestParam String companyId,
                                             @RequestParam String languageId, @Valid @RequestBody List<AddRoleAccess> updateRoleAccess, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<RoleAccess> updatedRoleAccess = roleAccessService.updateRoleAccess(companyId, languageId, roleId, loginUserID, updateRoleAccess);
        return new ResponseEntity<>(updatedRoleAccess, HttpStatus.OK);
    }

    // Delete RoleAccess
    @ApiOperation(response = RoleAccess.class, value = "Delete RoleAccess") // label for swagger
    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRoleAccess(@PathVariable Long roleId, @RequestParam String companyId,
                                              @RequestParam String languageId, @RequestParam String loginUserID) {
        roleAccessService.deleteRoleAccess(languageId, companyId, roleId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    // Get all RoleAccess Details
    @ApiOperation(response = ReplicaRoleAccess.class, value = "Get all RoleAccess details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<ReplicaRoleAccess> roleAccessList = roleAccessService.getAllRoleAccesses();
        return new ResponseEntity<>(roleAccessList, HttpStatus.OK);
    }

//    // Get RoleAccess
//    @ApiOperation(response = ReplicaRoleAccess.class, value = "Get RoleAccess") // label for swagger
//    @GetMapping("/{roleId}")
//    public ResponseEntity<?> getRoleAccess(@PathVariable Long roleId, @RequestParam Long menuId, @RequestParam String companyId,
//                                           @RequestParam String languageId, @RequestParam Long subMenuId) {
//        ReplicaRoleAccess roleAccesses = roleAccessService.getReplicaRoleAccess(companyId, languageId, roleId, menuId, subMenuId);
//        return new ResponseEntity<>(roleAccesses, HttpStatus.OK);
//    }

    // Get RoleAccess List
    @ApiOperation(response = ReplicaRoleAccess.class, value = "Get RoleAccess List") // label for swagger
    @GetMapping("/{roleId}")
    public ResponseEntity<?> getRoleAccessList(@PathVariable Long roleId, @RequestParam String companyId,
                                               @RequestParam String languageId) {
        List<ReplicaRoleAccess> roleAccesses = roleAccessService.getReplicaRoleAccessList(companyId, languageId, roleId);
        return new ResponseEntity<>(roleAccesses, HttpStatus.OK);
    }

    // Find RoleAccesses
    @ApiOperation(response = ReplicaRoleAccess.class, value = "Find RoleAccess") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findRoleAccess(@Valid @RequestBody FindRoleAccess findRoleAccess) throws Exception {
        List<ReplicaRoleAccess> roleAccessList = roleAccessService.findRoleAccess(findRoleAccess);
        return new ResponseEntity<>(roleAccessList, HttpStatus.OK);
    }

}
