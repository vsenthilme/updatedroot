package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.masterphase.MasterPhase;
import com.tekclover.wms.api.mfg.model.masterphase.SearchMasterPhase;
import com.tekclover.wms.api.mfg.service.MasterPhaseService;
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
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Validated
@Api(tags = {"MasterPhase"}, value = "MasterPhase  Operations related to MasterPhaseController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "MasterPhase ", description = "Operations related to MasterPhase")})
@RequestMapping("/masterphase")
@RestController
public class MasterPhaseController {

    @Autowired
    MasterPhaseService masterPhaseService;

    @ApiOperation(response = MasterPhase.class, value = "Get a MasterPhase") // label for swagger 
    @GetMapping("/{phaseNumber}")
    public ResponseEntity<?> getMasterPhase(@PathVariable String phaseNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                            @RequestParam String languageId, @RequestParam String warehouseId) {
        MasterPhase masterphase = masterPhaseService.getMasterPhase(companyCodeId, plantId, languageId, warehouseId, phaseNumber);
        return new ResponseEntity<>(masterphase, HttpStatus.OK);
    }

    @ApiOperation(response = MasterPhase.class, value = "Search MasterPhase") // label for swagger
    @PostMapping("/findMasterPhase")
    public Stream<MasterPhase> findMasterPhase(@RequestBody SearchMasterPhase searchMasterPhase) {
        return masterPhaseService.findMasterPhase(searchMasterPhase);
    }

    @ApiOperation(response = MasterPhase.class, value = "Create MasterPhase") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postMasterPhase(@Valid @RequestBody MasterPhase newMasterPhase, @RequestParam String loginUserID) {
        MasterPhase createdMasterPhase = masterPhaseService.createMasterPhase(newMasterPhase, loginUserID);
        return new ResponseEntity<>(createdMasterPhase, HttpStatus.OK);
    }

    @ApiOperation(response = MasterPhase.class, value = "Patch MasterPhase") // label for swagger
    @PatchMapping("/{phaseNumber}")
    public ResponseEntity<?> patchMasterPhase(@PathVariable String phaseNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                              @RequestParam String languageId, @RequestParam String warehouseId,
                                              @Valid @RequestBody MasterPhase modifyMasterPhase, @RequestParam String loginUserID) {
        MasterPhase createdMasterPhase = masterPhaseService.updateMasterPhase(companyCodeId, plantId, languageId, warehouseId, phaseNumber, loginUserID, modifyMasterPhase);
        return new ResponseEntity<>(createdMasterPhase, HttpStatus.OK);
    }

    @ApiOperation(response = MasterPhase.class, value = "Create MasterPhase Batch") // label for swagger
    @PostMapping("/batch")
    public ResponseEntity<?> postMasterPhaseBatch(@Valid @RequestBody List<MasterPhase> newMasterPhase, @RequestParam String loginUserID) {
        List<MasterPhase> createdMasterPhase = masterPhaseService.createMasterPhaseBatch(newMasterPhase, loginUserID);
        return new ResponseEntity<>(createdMasterPhase, HttpStatus.OK);
    }

    @ApiOperation(response = MasterPhase.class, value = "Patch MasterPhase") // label for swagger
    @PatchMapping("/batch")
    public ResponseEntity<?> patchMasterPhaseBatch(@RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String languageId, @RequestParam String warehouseId,
                                                   @Valid @RequestBody List<MasterPhase> modifyMasterPhase, @RequestParam String loginUserID) {
        List<MasterPhase> createdMasterPhase = masterPhaseService.updateMasterPhaseBatch(companyCodeId, plantId, languageId, warehouseId, loginUserID, modifyMasterPhase);
        return new ResponseEntity<>(createdMasterPhase, HttpStatus.OK);
    }

    @ApiOperation(response = MasterPhase.class, value = "Delete MasterPhase") // label for swagger
    @DeleteMapping("/{phaseNumber}")
    public ResponseEntity<?> deleteMasterPhase(@PathVariable String phaseNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                               @RequestParam String languageId, @RequestParam String warehouseId,
                                               @RequestParam String loginUserID) {
        masterPhaseService.deleteMasterPhase(companyCodeId, plantId, languageId, warehouseId, phaseNumber, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}