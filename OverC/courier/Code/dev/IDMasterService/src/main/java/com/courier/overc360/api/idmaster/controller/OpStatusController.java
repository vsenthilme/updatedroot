package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.opstatus.AddOpStatus;
import com.courier.overc360.api.idmaster.primary.model.opstatus.OpStatus;
import com.courier.overc360.api.idmaster.primary.model.opstatus.UpdateOpStatus;
import com.courier.overc360.api.idmaster.replica.model.opStatus.FindOpStatus;
import com.courier.overc360.api.idmaster.replica.model.opStatus.ReplicaOpStatus;
import com.courier.overc360.api.idmaster.service.OpStatusService;
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
@Api(tags = {"OpStatus"}, value = "OpStatus operations related to OpStatusController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "OpStatus", description = "Operations related to OpStatus")})
@RequestMapping("/opStatus")
@RestController
public class OpStatusController {

    @Autowired
    OpStatusService opStatusService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create OpStatus
    @ApiOperation(response = OpStatus.class, value = "Create new OpStatus") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postOpStatus(@Valid @RequestBody AddOpStatus addOpStatus, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        OpStatus newOpStatus = opStatusService.createOpStatus(addOpStatus, loginUserID);
        return new ResponseEntity<>(newOpStatus, HttpStatus.OK);
    }

    // Update OpStatus
    @ApiOperation(response = OpStatus.class, value = "Update OpStatus") // label for swagger
    @PatchMapping("/{statusCode}")
    public ResponseEntity<?> patchOpStatus(@PathVariable String statusCode, @RequestParam String languageId,
                                         @RequestParam String companyId, @RequestParam String loginUserID, @RequestBody UpdateOpStatus updateOpStatus)
            throws IllegalAccessException, InvocationTargetException, ParseException, IOException, CsvException {
        OpStatus updatedOpStatus = opStatusService.updateOpStatus(languageId, companyId, statusCode, loginUserID, updateOpStatus);
        return new ResponseEntity<>(updatedOpStatus, HttpStatus.OK);
    }

    // Delete OpStatus
    @ApiOperation(response = OpStatus.class, value = "Delete OpStatus") // label for swagger
    @DeleteMapping("/{statusCode}")
    public ResponseEntity<?> deleteOpStatus(@PathVariable String statusCode, @RequestParam String languageId, @RequestParam String companyId,
                                          @RequestParam String loginUserID) {
        opStatusService.deleteOpStatus(languageId, companyId, statusCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-------------------------------------------Replica-------------------------------------------------------------------*/

    // Get All OpStatus Details
    @ApiOperation(response = ReplicaOpStatus.class, value = "Get all OpStatus Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllOpStatusDetails() {
        List<ReplicaOpStatus> opStatusList = opStatusService.getAllOpStatus();
        return new ResponseEntity<>(opStatusList, HttpStatus.OK);
    }

    // Get OpStatus
    @ApiOperation(response = ReplicaOpStatus.class, value = "Get a OpStatus") // label for swagger
    @GetMapping("/{statusCode}")
    public ResponseEntity<?> getOpStatus(@PathVariable String statusCode, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaOpStatus dbOpStatus = opStatusService.replicaGetOpStatus(languageId, companyId, statusCode);
        return new ResponseEntity<>(dbOpStatus, HttpStatus.OK);
    }
    // Find OpStatus
    @ApiOperation(response = ReplicaOpStatus.class, value = "Find OpStatus") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findOpStatus(@Valid @RequestBody FindOpStatus findOpStatus) throws Exception {
        List<ReplicaOpStatus> opStatusList = opStatusService.findOpStatus(findOpStatus);
        return new ResponseEntity<>(opStatusList, HttpStatus.OK);
    }
}
