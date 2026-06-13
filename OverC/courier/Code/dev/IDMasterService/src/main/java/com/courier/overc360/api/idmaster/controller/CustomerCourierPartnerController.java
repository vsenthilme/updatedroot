package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.customercourierpartner.AddCustomerCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.customercourierpartner.CustomerCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.customercourierpartner.UpdateCustomerCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.customercourierpartner.FindCustomerCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.customercourierpartner.ReplicaCustomerCourierPartner;
import com.courier.overc360.api.idmaster.service.CustomerCourierPartnerService;
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
@Api(tags = {"CustomerCourierPartner"}, value = "CustomerCourierPartner Operations related to CustomerCourierPartnerController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CustomerCourierPartner", description = "Operations related to CustomerCourierPartner")})
@RequestMapping("/customerCourierPartner")
@RestController
public class CustomerCourierPartnerController {
    @Autowired
    private CustomerCourierPartnerService customerCourierPartnerService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = CustomerCourierPartner.class, value = "Create New CustomerCourierPartner")
    @PostMapping("")
    public ResponseEntity<?> createCustomerCourierPartner(@Valid @RequestBody AddCustomerCourierPartner addCustomerCourierPartner, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CustomerCourierPartner newCustomerCourierPartner = customerCourierPartnerService.createCustomerCourierPartner(addCustomerCourierPartner, loginUserID);
        return new ResponseEntity<>(newCustomerCourierPartner, HttpStatus.OK);
    }

    //Update
    @ApiOperation(response = CustomerCourierPartner.class, value = "Update CustomerCourierPartner")
    @PatchMapping("/{courierPartnerId}")
    public ResponseEntity<?> patchCustomerCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId, @RequestParam String languageId,
                                                         @RequestParam String partnerId, @RequestParam String loginUserID, @RequestBody UpdateCustomerCourierPartner updateCustomerCourierPartner)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CustomerCourierPartner updatedCustomerCourierPartner = customerCourierPartnerService.updateCustomerCourierPartner(companyId, languageId, courierPartnerId, partnerId,
                updateCustomerCourierPartner, loginUserID);
        return new ResponseEntity<>(updatedCustomerCourierPartner, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = CustomerCourierPartner.class, value = "Delete CustomerCourierPartner")
    @DeleteMapping("/{courierPartnerId}")
    public ResponseEntity<?> deleteCustomerCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId, @RequestParam String languageId,
                                                          @RequestParam String partnerId, @RequestParam String loginUserID) {
        customerCourierPartnerService.deleteCustomerCourierPartner(companyId, languageId, courierPartnerId, partnerId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All CustomerCourierPartner Details
    @ApiOperation(response = ReplicaCustomerCourierPartner.class, value = "Get all CustomerCourierPartner Details")
    @GetMapping("")
    public ResponseEntity<?> getAllCustomerCourierPartnerDetails() {
        List<ReplicaCustomerCourierPartner> customerCourierPartnerList = customerCourierPartnerService.getAll();
        return new ResponseEntity<>(customerCourierPartnerList, HttpStatus.OK);
    }

    //Get CustomerCourierPartner
    @ApiOperation(response = ReplicaCustomerCourierPartner.class, value = "Get a CustomerCourierPartner")
    @GetMapping("/{courierPartnerId}")
    public ResponseEntity<?> getCustomerCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId,
                                                       @RequestParam String partnerId, @RequestParam String languageId) {
        ReplicaCustomerCourierPartner dbCustomerCourierPartner = customerCourierPartnerService.getReplicaCustomerCourierPartner(companyId, languageId, courierPartnerId, partnerId);
        return new ResponseEntity<>(dbCustomerCourierPartner, HttpStatus.OK);
    }

    //Find CustomerCourierPartner
    @ApiOperation(response = ReplicaCustomerCourierPartner.class, value = "Find CustomerCourierPartner")
    @PostMapping("/find")
    public ResponseEntity<?> findCustomerCourierPartner(@Valid @RequestBody FindCustomerCourierPartner findCustomerCourierPartner) throws Exception {
        List<ReplicaCustomerCourierPartner> customerCourierPartnerList = customerCourierPartnerService.findCustomerCourierPartner(findCustomerCourierPartner);
        return new ResponseEntity<>(customerCourierPartnerList, HttpStatus.OK);
    }
}

