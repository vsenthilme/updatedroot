package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.uom.AddUom;
import com.courier.overc360.api.idmaster.primary.model.uom.Uom;
import com.courier.overc360.api.idmaster.primary.model.uom.UpdateUom;
import com.courier.overc360.api.idmaster.replica.model.uom.FindUom;
import com.courier.overc360.api.idmaster.replica.model.uom.ReplicaUom;
import com.courier.overc360.api.idmaster.service.UomService;
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
@Api(tags = {"Uom"}, value = "Uom Operations related to UomController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Uom", description = "Operations related to Uom")})
@RequestMapping("/uom")
@RestController
public class UomController {
    @Autowired
    private UomService uomService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create UOM
    @ApiOperation(response = Uom.class, value = "Create New UOM")
    @PostMapping("")
    public ResponseEntity<?> createUom(@Valid @RequestBody AddUom addUom, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Uom newUom = uomService.createUom(addUom, loginUserID);
        return new ResponseEntity<>(newUom, HttpStatus.OK);
    }
    //Update UOM
    @ApiOperation(response = Uom.class, value = "Update UOM")
    @PatchMapping("/{uomId}")
    public ResponseEntity<?> patchUom(@PathVariable String uomId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestBody UpdateUom updateUom)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Uom updatedUom = uomService.updateUom(companyId, languageId, uomId,updateUom, loginUserID);
        return new ResponseEntity<>(updatedUom, HttpStatus.OK);
    }
    //Delete UOM
    @ApiOperation(response = Uom.class, value = "Delete UOM")
    @DeleteMapping("/{uomId}")
    public ResponseEntity<?> deleteUom(@PathVariable String uomId,@RequestParam String companyId, @RequestParam String languageId,
                                               @RequestParam String loginUserID) {
        uomService.deleteUom( companyId,languageId, uomId,loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All UOM Details
    @ApiOperation(response = ReplicaUom.class, value = "Get all UOM Details")
    @GetMapping("")
    public ResponseEntity<?> getAllUomDetails() {
        List<ReplicaUom> uomList = uomService.getAllUom();
        return new ResponseEntity<>(uomList, HttpStatus.OK);
    }
    //Get UOM
    @ApiOperation(response = ReplicaUom.class, value = "Get a UOM")
    @GetMapping("/{uomId}")
    public ResponseEntity<?> getUom(@PathVariable String uomId,@RequestParam String companyId, @RequestParam String languageId) {
        ReplicaUom dbUom = uomService.getReplicaUom(companyId,languageId,uomId);
        return new ResponseEntity<>(dbUom, HttpStatus.OK);
    }
    //Find UOM
    @ApiOperation(response = ReplicaUom.class, value = "Find UOM")
    @PostMapping("/find")
    public ResponseEntity<?> findUom(@Valid @RequestBody FindUom findUom) throws Exception {
        List<ReplicaUom> uomList = uomService.findUom(findUom);
        return new ResponseEntity<>(uomList, HttpStatus.OK);
    }
}
