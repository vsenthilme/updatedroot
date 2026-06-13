package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.serviceprovider.AddServiceProvider;
import com.courier.overc360.api.idmaster.primary.model.serviceprovider.ServiceProvider;
import com.courier.overc360.api.idmaster.primary.model.serviceprovider.UpdateServiceProvider;
import com.courier.overc360.api.idmaster.replica.model.serviceprovider.FindServiceProvider;
import com.courier.overc360.api.idmaster.replica.model.serviceprovider.ReplicaServiceProvider;
import com.courier.overc360.api.idmaster.service.ServiceProviderService;
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
@Api(tags = {"ServiceProvider"}, value = "ServiceProvider Operations related to ServiceProviderController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ServiceProvider", description = "Operations related to ServiceProvider")})
@RequestMapping("/serviceprovider")
@RestController

public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = ServiceProvider.class, value = "Create New ServiceProvider")
    @PostMapping("")
    public ResponseEntity<?> createServiceProvider(@Valid @RequestBody AddServiceProvider addServiceProvider, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ServiceProvider newServiceProvider = serviceProviderService.createServiceProvider(addServiceProvider,loginUserID);
        return new ResponseEntity<>(newServiceProvider, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = ServiceProvider.class, value = "Update ServiceProvider")
    @PatchMapping("/{serviceProvidersId}")
    public ResponseEntity<?> patchServiceProvider(@PathVariable String serviceProvidersId, @RequestParam String companyId, @RequestParam String languageId,
                                             @RequestParam String loginUserID, @RequestBody UpdateServiceProvider updateServiceProvider)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ServiceProvider updatedServiceProvider = serviceProviderService.updateServiceProvider(companyId, languageId, serviceProvidersId,updateServiceProvider, loginUserID);
        return new ResponseEntity<>(updatedServiceProvider, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = ServiceProvider.class, value = "Delete ServiceProvider")
    @DeleteMapping("/{serviceProvidersId}")
    public ResponseEntity<?> deleteServiceProvider(@PathVariable String serviceProvidersId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String loginUserID) {
        serviceProviderService.deleteServiceProvider( companyId,languageId, serviceProvidersId,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ServiceProvider Details
    @ApiOperation(response = ReplicaServiceProvider.class, value = "Get all ServiceProvider Details")
    @GetMapping("")
    public ResponseEntity<?> getAllServiceProviderDetails() {
        List<ReplicaServiceProvider> serviceProviderList = serviceProviderService.getAll();
        return new ResponseEntity<>(serviceProviderList, HttpStatus.OK);
    }
    //Get ServiceProvider
    @ApiOperation(response = ReplicaServiceProvider.class, value = "Get a ServiceProvider")
    @GetMapping("/{serviceProvidersId}")
    public ResponseEntity<?> getServiceProvider(@PathVariable String serviceProvidersId, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaServiceProvider dbServiceProvider = serviceProviderService.getReplicaServiceProvider(companyId,languageId,serviceProvidersId);
        return new ResponseEntity<>(dbServiceProvider, HttpStatus.OK);
    }
    //Find ServiceProvider
    @ApiOperation(response = ReplicaServiceProvider.class, value = "Find ServiceProvider")
    @PostMapping("/find")
    public ResponseEntity<?> findServiceProvider(@Valid @RequestBody FindServiceProvider findServiceProvider) throws Exception {
        List<ReplicaServiceProvider> serviceProviderList = serviceProviderService.findServiceProvider(findServiceProvider);
        return new ResponseEntity<>(serviceProviderList, HttpStatus.OK);
    }
}

