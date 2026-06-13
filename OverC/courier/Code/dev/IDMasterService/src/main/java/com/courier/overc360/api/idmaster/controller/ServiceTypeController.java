package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.serviceType.AddServiceType;
import com.courier.overc360.api.idmaster.primary.model.serviceType.ServiceType;
import com.courier.overc360.api.idmaster.primary.model.serviceType.UpdateServiceType;
import com.courier.overc360.api.idmaster.replica.model.serviceType.FindServiceType;
import com.courier.overc360.api.idmaster.replica.model.serviceType.ReplicaServiceType;
import com.courier.overc360.api.idmaster.service.ServiceTypeService;
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
@Api(tags = {"ServiceType"}, value = "ServiceType  Operations related to ServiceTypeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ServiceType", description = "Operations related to ServiceType")})
@RequestMapping("/serviceType")
@RestController
public class ServiceTypeController {

    @Autowired
    ServiceTypeService serviceTypeService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create ServiceType
    @ApiOperation(response = ServiceType.class, value = "Create ServiceType") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postServiceType(@Valid @RequestBody AddServiceType addServiceType, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ServiceType serviceType = serviceTypeService.createServiceType(addServiceType, loginUserID);
        return new ResponseEntity<>(serviceType, HttpStatus.OK);
    }

    // Update ServiceType
    @ApiOperation(response = ServiceType.class, value = "Update ServiceType") // label for swagger
    @PatchMapping("/{serviceTypeId}")
    public ResponseEntity<?> patchServiceType(@PathVariable String serviceTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestBody UpdateServiceType updateServiceType)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ServiceType updatedServicetype =
                serviceTypeService.updateServiceType(companyId, languageId, serviceTypeId, loginUserID, updateServiceType);
        return new ResponseEntity<>(updatedServicetype, HttpStatus.OK);
    }

    // Delete ServiceType
    @ApiOperation(response = ServiceType.class, value = "Delete ServiceType") // label for swagger
    @DeleteMapping("/{serviceTypeId}")
    public ResponseEntity<?> deleteServiceType(@PathVariable String serviceTypeId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String loginUserID) {
        serviceTypeService.deleteServiceType(companyId, languageId, serviceTypeId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ServiceType Details
    @ApiOperation(response = ReplicaServiceType.class, value = "Get all ReplicaServiceType details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllServiceTypes() {
        List<ReplicaServiceType> servicetypeList = serviceTypeService.getAllServiceTypes();
        return new ResponseEntity<>(servicetypeList, HttpStatus.OK);
    }

    // Get ServiceType
    @ApiOperation(response = ReplicaServiceType.class, value = "Get a ReplicaServiceType") // label for swagger
    @GetMapping("/{serviceTypeId}")
    public ResponseEntity<?> getServiceType(@PathVariable String serviceTypeId, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaServiceType dbservicetype = serviceTypeService.getReplicaServiceType(companyId, languageId, serviceTypeId);
        return new ResponseEntity<>(dbservicetype, HttpStatus.OK);
    }

    // Find ServiceType
    @ApiOperation(response = ReplicaServiceType.class, value = "Find ReplicaServiceType") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findServiceTypes(@Valid @RequestBody FindServiceType findServiceType) throws Exception {
        List<ReplicaServiceType> serviceTypeList = serviceTypeService.findServiceTypes(findServiceType);
        return new ResponseEntity<>(serviceTypeList, HttpStatus.OK);
    }


}
