package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.customcharges.AddCustomCharge;
import com.courier.overc360.api.idmaster.primary.model.customcharges.CustomCharges;
import com.courier.overc360.api.idmaster.primary.model.customcharges.UpdateCustomCharge;
import com.courier.overc360.api.idmaster.replica.model.customcharges.FindCustomCharge;
import com.courier.overc360.api.idmaster.replica.model.customcharges.ReplicaCustomCharges;
import com.courier.overc360.api.idmaster.service.CustomChargesService;
import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"CustomCharges"}, value = "Custom Charges Operations related to CustomChargesController")
@SwaggerDefinition(tags = {@Tag(name = "CustomCharges", description = "Operations related to CustomCharges")}) //Lable for swagger
@RequestMapping("/customCharge")
@RestController
public class CustomChargesController {

    @Autowired
    private CustomChargesService customChargesService;

    //Create Custom Charges
    @ApiOperation(response = CustomCharges.class, value = "Create Custom Charge")
    @PostMapping("")
    public ResponseEntity<?> createCustomCharge(@Valid @RequestBody AddCustomCharge addCustomCharge, @RequestParam String loginUserID) throws IOException, CsvException {
        CustomCharges customCharges = customChargesService.createNewCustomCharge(addCustomCharge, loginUserID);
        return new ResponseEntity<>(customCharges, HttpStatus.OK);
    }

    //Update Custom Charge
    @ApiOperation(response = CustomCharges.class, value = "Update Sorting Master")
    @PatchMapping("")
    public ResponseEntity<?> updateSortMaster(@RequestParam String languageId, @RequestParam String companyId,
                                              @RequestBody UpdateCustomCharge updateCustomCharge, @RequestParam String loginUserID) throws IOException, CsvException {
        CustomCharges customCharges = customChargesService.updateOneCustomCharge(languageId,companyId,updateCustomCharge, loginUserID);
        return new ResponseEntity<>(customCharges, HttpStatus.OK);
    }

                   /* ------ list api's   ---------------*/

    //Create Custom Charge
    @ApiOperation(response = CustomCharges.class, value = "Create Custom Charges")
    @PostMapping("/create/list")
    public ResponseEntity<?>  createCustomCharges(@Valid @RequestBody List<AddCustomCharge> customChargesList, @RequestParam String loginUserID) throws IOException, CsvException {
        List<CustomCharges> customCharges = customChargesService.createCustomCharge(customChargesList,loginUserID);
        return new ResponseEntity<>(customCharges, HttpStatus.OK);
    }

    //Update Custom Charge Data
    @ApiOperation(response = CustomCharges.class, value = "Update Custom Charges")
    @PatchMapping("/update/list")
    public ResponseEntity<?> updateCustomCharge(@RequestBody List<UpdateCustomCharge> customCharges,@RequestParam String loginUserID) throws IOException, CsvException {
        List<CustomCharges> customCharges1 = customChargesService.updateCustomCharge(customCharges,loginUserID);
        return new ResponseEntity<>(customCharges1,HttpStatus.OK);
    }

    //Delete Custom Charge Data
    @ApiOperation(response = CustomCharges.class, value = "Delete Custom Charge")
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteCustomCharge(@Valid @RequestBody List<CustomCharges> customCharges,@RequestParam String loginUserID) throws IOException, CsvException {
        customChargesService.deleteCustomCharge(customCharges,loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

                /* ----- replica -------*/

    //Get All Custom Charges Info
    @ApiOperation(response = ReplicaCustomCharges.class, value = "Get All Custom Charges")
    @GetMapping("")
    public ResponseEntity<?> getAllCustomCharge(){
        List<ReplicaCustomCharges> customCharges = customChargesService.getAllCustomCharge();
        return new ResponseEntity<>(customCharges,HttpStatus.OK);
    }

    // Find Custom Charge
    @ApiOperation(response = ReplicaCustomCharges.class, value = "Find Custom Charge") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCustomCharge(@Valid @RequestBody FindCustomCharge findCustomCharge) throws Exception {
        List<ReplicaCustomCharges> customCharges = customChargesService.findCustomCharge(findCustomCharge);
        return new ResponseEntity<>(customCharges, HttpStatus.OK);
    }


}
