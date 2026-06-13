package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.debrief.AddDebrief;
import com.courier.overc360.api.midmile.primary.model.debrief.Debrief;
import com.courier.overc360.api.midmile.primary.model.drs.AddDrs;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.drs.UpdateDrs;
import com.courier.overc360.api.midmile.replica.model.debrief.FindDebrief;
import com.courier.overc360.api.midmile.replica.model.debrief.ReplicaDebrief;
import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.service.DebriefService;
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
@Api(tags = {"Debrief"}, value = "Debrief Operations related to Debrief Controller") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Debrief", description = "Operations related to Debrief")})
@RequestMapping("/debrief")
@RestController
public class DebriefController {

    @Autowired
    DebriefService debriefService;

    // Create Debrief
    @ApiOperation(response = Debrief.class, value = "Create Debrief") // label for swagger
    @PostMapping("/create")
    public ResponseEntity<?> postDebrief(@Valid @RequestBody AddDebrief newDebrief, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Debrief createdDebrief = debriefService.createDebrief(newDebrief, loginUserID);
        return new ResponseEntity<>(createdDebrief, HttpStatus.OK);
    }

    // Update Debrief
    @ApiOperation(response = Debrief.class, value = "Update Debrief") // label for swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchDebrief(@RequestParam String loginUserID, @RequestBody Debrief updateDebrief)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Debrief updatedDebrief = debriefService.updateDebrief(updateDebrief, loginUserID);
        return new ResponseEntity<>(updatedDebrief, HttpStatus.OK);
    }

    // Delete Debrief
    @ApiOperation(response = Debrief.class, value = "Delete Debrief") // label for swagger
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDebrief(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String courierId
            , @RequestParam String loginUserID) {
        debriefService.deleteDebrief(languageId, companyId, courierId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Debrief
    @ApiOperation(response = ReplicaDebrief.class, value = "Find ReplicaDebrief") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findDebrief(@RequestBody FindDebrief findDebrief) throws Exception {
        List<ReplicaDebrief> createdDebrief = debriefService.findDebrief(findDebrief);
        return new ResponseEntity<>(createdDebrief, HttpStatus.OK);
    }
}
