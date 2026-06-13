package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.logicmaster.AddLogicMaster;
import com.courier.overc360.api.idmaster.primary.model.logicmaster.LogicMaster;
import com.courier.overc360.api.idmaster.primary.model.logicmaster.UpdateLogicMaster;
import com.courier.overc360.api.idmaster.replica.model.logicmaster.FindLogicMaster;
import com.courier.overc360.api.idmaster.replica.model.logicmaster.ReplicaLogicMaster;
import com.courier.overc360.api.idmaster.service.LogicMasterService;
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
@Api(tags = {"LogicMaster"}, value = "LogicMaster Operations related to LogicMasterController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "LogicMaster", description = "Operations related to LogicMaster")})
@RequestMapping("/logicMaster")
@RestController
public class LogicMasterController {

    @Autowired
    private LogicMasterService logicMasterService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = LogicMaster.class, value = "Create New LogicMaster")
    @PostMapping("")
    public ResponseEntity<?> postLogicMaster(@Valid @RequestBody AddLogicMaster addLogicMaster, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        LogicMaster newLogicMaster = logicMasterService.createLogicMaster(addLogicMaster, loginUserID);
        return new ResponseEntity<>(newLogicMaster, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = LogicMaster.class, value = "Update LogicMaster")
    @PatchMapping("/{consoleCountId}")
    public ResponseEntity<?> patchLogicMaster(@PathVariable String consoleCountId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestBody UpdateLogicMaster updateLogicMaster)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        LogicMaster updatedLogicMaster = logicMasterService.updateLogicMaster(companyId, languageId, consoleCountId, updateLogicMaster, loginUserID);
        return new ResponseEntity<>(updatedLogicMaster, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = LogicMaster.class, value = "Delete LogicMaster")
    @DeleteMapping("/{consoleCountId}")
    public ResponseEntity<?> deleteLogicMaster(@PathVariable String consoleCountId, @RequestParam String companyId, @RequestParam String languageId,
                                               @RequestParam String loginUserID) {
        logicMasterService.deleteLogicMaster(companyId, languageId, consoleCountId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All LogicMaster Details
    @ApiOperation(response = ReplicaLogicMaster.class, value = "Get all LogicMaster Details")
    @GetMapping("")
    public ResponseEntity<?> getAllLogicMasterDetails() {
        List<ReplicaLogicMaster> logicMasterList = logicMasterService.getAll();
        return new ResponseEntity<>(logicMasterList, HttpStatus.OK);
    }

    //Get LogicMaster
    @ApiOperation(response = ReplicaLogicMaster.class, value = "Get a LogicMaster")
    @GetMapping("/{consoleCountId}")
    public ResponseEntity<?> getLogicMaster(@PathVariable String consoleCountId, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaLogicMaster dbLogicMaster = logicMasterService.getReplicaLogicMaster(companyId, languageId, consoleCountId);
        return new ResponseEntity<>(dbLogicMaster, HttpStatus.OK);
    }

    //Find LogicMaster
    @ApiOperation(response = ReplicaLogicMaster.class, value = "Find LogicMaster")
    @PostMapping("/find")
    public ResponseEntity<?> findLogicMaster(@Valid @RequestBody FindLogicMaster findLogicMaster) throws Exception {
        List<ReplicaLogicMaster> logicMasterList = logicMasterService.findLogicMaster(findLogicMaster);
        return new ResponseEntity<>(logicMasterList, HttpStatus.OK);
    }

}
