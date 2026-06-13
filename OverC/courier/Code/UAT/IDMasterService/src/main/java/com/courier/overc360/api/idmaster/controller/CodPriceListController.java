package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.codpricelist.AddCodPriceList;
import com.courier.overc360.api.idmaster.primary.model.codpricelist.CodPriceList;
import com.courier.overc360.api.idmaster.replica.model.codpricelist.FindCodPriceList;
import com.courier.overc360.api.idmaster.replica.model.codpricelist.ReplicaCodPriceList;
import com.courier.overc360.api.idmaster.service.CodPriceListService;
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
@Api(tags = {"CodPriceList"}, value = "CodPriceList Operations related to CodPriceListController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CodPriceList", description = "Operations related to CodPriceList")})
@RequestMapping("/codPriceList")
@RestController
public class CodPriceListController {

    @Autowired
    CodPriceListService codPriceListService;

    /*----------------------------------------------PRIMARY------------------------------------------*/

    // Create CodPriceList List
    @ApiOperation(response = CodPriceList.class, value = "Create CodPriceList List") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postCodPriceListList(@Valid @RequestBody List<AddCodPriceList> addCodPriceLists, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<CodPriceList> createdCodPriceList = codPriceListService.createCodPriceList(addCodPriceLists, loginUserID);
        return new ResponseEntity<>(createdCodPriceList, HttpStatus.OK);
    }

    // Update CodPriceList List
    @ApiOperation(response = CodPriceList.class, value = "Update CodPriceList List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchCodPriceListList(@RequestParam String loginUserID, @RequestBody List<CodPriceList> updateCodPriceList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<CodPriceList> updatedCodPriceList = codPriceListService.updateCodPriceList(updateCodPriceList, loginUserID);
        return new ResponseEntity<>(updatedCodPriceList, HttpStatus.OK);
    }

    // Delete CodPriceList List
    @ApiOperation(response = CodPriceList.class, value = "Delete CodPriceList") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteCodPriceListList(@RequestBody List<CodPriceList> deleteCodPriceList, @RequestParam String loginUserID) {
        codPriceListService.deleteCodPriceList(deleteCodPriceList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All CodPriceList Details
    @ApiOperation(response = ReplicaCodPriceList.class, value = "Get all ReplicaCodPriceList details") // label for swagger
    @GetMapping("/getall")
    public ResponseEntity<?> getAllCodPriceList() {
        List<ReplicaCodPriceList> replicaCodPriceListList = codPriceListService.getAllCodPriceList();
        return new ResponseEntity<>(replicaCodPriceListList, HttpStatus.OK);
    }

    // Get CodPriceList
    @ApiOperation(response = ReplicaCodPriceList.class, value = "Get a ReplicaCodPriceList") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getCodPriceList(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String partnerId, @RequestParam Long lineNo) {

        ReplicaCodPriceList replicaCodPriceList = codPriceListService.getReplicaCodPriceList(languageId, companyId, partnerId, lineNo);
        return new ResponseEntity<>(replicaCodPriceList, HttpStatus.OK);
    }

    // Find CodPriceList
    @ApiOperation(response = ReplicaCodPriceList.class, value = "Find ReplicaCodPriceList") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCodPriceList(@RequestBody FindCodPriceList findCodPriceList) throws Exception {
        List<ReplicaCodPriceList> createdCodPriceList = codPriceListService.findCodPriceList(findCodPriceList);
        return new ResponseEntity<>(createdCodPriceList, HttpStatus.OK);
    }
}
