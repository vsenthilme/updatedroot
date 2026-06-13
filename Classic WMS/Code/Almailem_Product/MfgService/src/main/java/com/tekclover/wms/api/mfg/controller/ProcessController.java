package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.process.Process;
import com.tekclover.wms.api.mfg.model.process.SearchProcess;
import com.tekclover.wms.api.mfg.service.ProcessService;
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
@Api(tags = {"Process"}, value = "Process  Operations related to ProcessController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Process ", description = "Operations related to Process")})
@RequestMapping("/process")
@RestController
public class ProcessController {

    @Autowired
    ProcessService processService;

    @ApiOperation(response = Process.class, value = "Get a Process")
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getProcess(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId,
                                        @RequestParam String plantId, @RequestParam String receipeId, @RequestParam String operationNumber,
                                        @RequestParam String phaseNumber, @RequestParam String bomItem, @RequestParam String batchNumber,
                                        @PathVariable String productionOrderNo, @RequestParam Long productionOrderLineNo, @RequestParam String itemCode) {
        Process process = processService.getProcess(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId,
                productionOrderNo, productionOrderLineNo, itemCode, phaseNumber, bomItem, batchNumber);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    @ApiOperation(response = Process.class, value = "Get a Process by OrderNo")
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getProcess(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId,
                                        @RequestParam String plantId, @PathVariable String productionOrderNo) {
        List<Process> process = processService.getProcess(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    @ApiOperation(response = Process.class, value = "Get a Process by Batch")
    @GetMapping("/batch/{productionOrderNo}")
    public ResponseEntity<?> getProcess(@RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String warehouseId,
                                        @RequestParam String plantId, @PathVariable String productionOrderNo, @RequestParam String batchNumber) {
        List<Process> process = processService.getProcess(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    @ApiOperation(response = Process.class, value = "Search Process")
    @PostMapping("/findProcess")
    public Stream<Process> findProcess(@RequestBody SearchProcess searchProcess) {
        return processService.findProcess(searchProcess);
    }

    @ApiOperation(response = Process.class, value = "Create Process Batch")
    @PostMapping("/create")
    public ResponseEntity<?> postProcessBatch(@Valid @RequestBody List<Process> newProcess, @RequestParam String loginUserID) {
        List<Process> createdProcess = processService.createProcess(newProcess, loginUserID);
        return new ResponseEntity<>(createdProcess, HttpStatus.OK);
    }

    @ApiOperation(response = Process.class, value = "Patch Process Batch")
    @PatchMapping("/{productionOrderNo}")
    public ResponseEntity<?> patchProcessBatch(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId,
                                               @RequestParam String warehouseId, @PathVariable String productionOrderNo,
                                               @Valid @RequestBody List<Process> modifyProcess, @RequestParam String loginUserID) {
        List<Process> updatedProcess = processService.updateProcessBatch(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID, modifyProcess);
        return new ResponseEntity<>(updatedProcess, HttpStatus.OK);
    }

    @ApiOperation(response = Process.class, value = "Delete Process by batch")
    @DeleteMapping("/batch/{productionOrderNo}")
    public ResponseEntity<?> deleteProcessBatch(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId,
                                                @RequestParam String batchNumber, @PathVariable String productionOrderNo, @RequestParam String loginUserID) {
        processService.deleteProcess(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = Process.class, value = "Delete Process by production orderNo")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteProcess(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId,
                                           @RequestParam String warehouseId, @PathVariable String productionOrderNo, @RequestParam String loginUserID) {
        processService.deleteProcess(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}