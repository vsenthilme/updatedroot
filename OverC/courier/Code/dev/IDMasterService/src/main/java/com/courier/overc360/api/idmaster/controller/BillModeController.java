package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.billmode.AddBillMode;
import com.courier.overc360.api.idmaster.primary.model.billmode.BillMode;
import com.courier.overc360.api.idmaster.primary.model.billmode.UpdateBillMode;
import com.courier.overc360.api.idmaster.replica.model.billmode.FindBillMode;
import com.courier.overc360.api.idmaster.replica.model.billmode.ReplicaBillMode;
import com.courier.overc360.api.idmaster.service.BillModeService;
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
@Api(tags = {"BillMode"}, value = "BillMode Operations related to BillModeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "BillMode", description = "Operations related to BillMode")})
@RequestMapping("/billmode")
@RestController
public class BillModeController {

    @Autowired
    private BillModeService billModeService;


    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create BillMode
    @ApiOperation(response = BillMode.class, value = "Create New BillMode")
    @PostMapping("")
    public ResponseEntity<?> createBillMode(@Valid @RequestBody AddBillMode addBillMode, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        BillMode newBillMode = billModeService.createBillMode(addBillMode, loginUserID);
        return new ResponseEntity<>(newBillMode, HttpStatus.OK);
    }
    //Update BillMode
    @ApiOperation(response = BillMode.class, value = "Update BillMode")
    @PatchMapping("/{billModeId}")
    public ResponseEntity<?> patchBillMode(@PathVariable String billModeId, @RequestParam String companyId, @RequestParam String languageId,
                                      @RequestParam String loginUserID, @RequestBody UpdateBillMode updateBillMode)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        BillMode updatedBillMode= billModeService.updateBillMode(companyId, languageId, billModeId,updateBillMode, loginUserID);
        return new ResponseEntity<>(updatedBillMode, HttpStatus.OK);
    }
    //Delete BillMode
    @ApiOperation(response = BillMode.class, value = "Delete BillMode")
    @DeleteMapping("/{billModeId}")
    public ResponseEntity<?> deleteBillMode(@PathVariable String billModeId,@RequestParam String companyId, @RequestParam String languageId,
                                       @RequestParam String loginUserID) {
        billModeService.deleteBillMode( companyId,languageId, billModeId,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All BillMode Details
    @ApiOperation(response = ReplicaBillMode.class, value = "Get all BillMode Details")
    @GetMapping("")
    public ResponseEntity<?> getAllBillModeDetails() {
        List<ReplicaBillMode> billModeList = billModeService.getAllBillMode();
        return new ResponseEntity<>(billModeList, HttpStatus.OK);
    }
    //Get BillMode
    @ApiOperation(response = ReplicaBillMode.class, value = "Get a BillMode")
    @GetMapping("/{billModeId}")
    public ResponseEntity<?> getBillMode(@PathVariable String billModeId,@RequestParam String companyId, @RequestParam String languageId) {
        ReplicaBillMode dbBillMode= billModeService.getReplicaBillMode(companyId,languageId,billModeId);
        return new ResponseEntity<>(dbBillMode, HttpStatus.OK);
    }
    //Find BillMode
    @ApiOperation(response = ReplicaBillMode.class, value = "Find BillMode")
    @PostMapping("/find")
    public ResponseEntity<?> findBillMode(@Valid @RequestBody FindBillMode findBillMode) throws Exception {
        List<ReplicaBillMode> billModeList = billModeService.findBillMode(findBillMode);
        return new ResponseEntity<>(billModeList, HttpStatus.OK);
    }
}


