package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.customer.Customer;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.AddSortMaster;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.SortMasterDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.SortingMaster;
import com.courier.overc360.api.idmaster.primary.model.sortingmaster.UpdateSortMaster;
import com.courier.overc360.api.idmaster.replica.model.customcharges.ReplicaCustomCharges;
import com.courier.overc360.api.idmaster.replica.model.sortingmaster.FindSortMaster;
import com.courier.overc360.api.idmaster.replica.model.sortingmaster.ReplicaSortingMaster;
import com.courier.overc360.api.idmaster.service.SortingMasterService;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Api(tags = {"SortMaster"}, value = "SortMaster Operations related to SortingMasterController")
@SwaggerDefinition(tags = {@Tag(name = "SortMaster", description = "Operations related to SortMaster")})
//Lable for swagger
@RequestMapping("/sortmaster")
@RestController
public class SortingMasterController {

    @Autowired
    private SortingMasterService sortingMasterService;

    //Create Sorting Master
    @ApiOperation(response = SortingMaster.class, value = "Create Sorting Master")
    @PostMapping("")
    public ResponseEntity<?> createSortMaster(@Valid @RequestBody AddSortMaster sortingMaster, @RequestParam String loginUserID) throws IOException, CsvException {
        SortingMaster sortingMasters = sortingMasterService.createSortMaster(sortingMaster, loginUserID);
        return new ResponseEntity<>(sortingMasters, HttpStatus.OK);
    }

    //Update Sort Master
    @ApiOperation(response = SortingMaster.class, value = "Update Sorting Master")
    @PatchMapping("/{sortingId}")
    public ResponseEntity<?> updateSortMaster(@PathVariable String sortingId, @RequestParam String languageId,
                                              @RequestParam String companyId, @RequestParam String zoneType,
                                              @RequestBody UpdateSortMaster sortingMaster, @RequestParam String loginUserID) throws IOException, CsvException {
        SortingMaster sortingMaster1 = sortingMasterService.updateSortMaster(sortingId,languageId,companyId,zoneType,sortingMaster, loginUserID);
        return new ResponseEntity<>(sortingMaster1, HttpStatus.OK);
    }

    //Delete Sort Master Data
    @ApiOperation(response = SortingMaster.class, value = "Delete sort master")
    @PostMapping("/{sortingId}")
    public ResponseEntity<?> deleteSortMaster(@PathVariable String sortingId,@RequestParam String languageId,
                                              @RequestParam String companyId, @RequestParam String zoneType,
                                              @RequestParam String loginUserID) throws IOException, CsvException {
        sortingMasterService.deleteSortMaster(sortingId,languageId,companyId,zoneType, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

        /*--------------------- list api's ----------------------*/

    // Create Sort Master - bulk
    @ApiOperation(response = SortingMaster.class, value = "Create new Sort master - bulk") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postSortMasterBulk(@Valid @RequestBody List<AddSortMaster> addSortMasters, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<SortingMaster> sortingMasters = sortingMasterService.createSortMasterBulk(addSortMasters, loginUserID);
        return new ResponseEntity<>(sortingMasters, HttpStatus.OK);
    }

    // Update Sort Master - bulk
    @ApiOperation(response = SortingMaster.class, value = "Update Sorting Master - bulk") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchSortMasterBulk(@Valid @RequestBody List<UpdateSortMaster> updateSortMasters, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<SortingMaster> updateSortMaster = sortingMasterService.updateSortMasterBulk(updateSortMasters, loginUserID);
        return new ResponseEntity<>(updateSortMaster, HttpStatus.OK);
    }

    // Delete Sort Master - bulk
    @ApiOperation(response = Customer.class, value = "Delete Sort Master - bulk") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteSortMasterBulk(@Valid @RequestBody List<SortMasterDeleteInput> sortMasterDeleteInputs, @RequestParam String loginUserID) throws IOException, CsvException {
        sortingMasterService.deleteSortMasterBulk(sortMasterDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


                     /*--------------- Replica ---------------------*/
    //Get All Sort Master Details
    @ApiOperation(response = ReplicaCustomCharges.class, value = "Get All Sorting master details")
    @GetMapping("")
    public ResponseEntity<?> getAllSortMaster() {
        List<ReplicaSortingMaster> sortingMasters = sortingMasterService.getSortmasterList();
        return new ResponseEntity<>(sortingMasters, HttpStatus.OK);
    }

    // Get Sort Master
    @ApiOperation(response = ReplicaSortingMaster.class, value = "Get a Sort Master") // label for swagger
    @GetMapping("/{sortingId}")
    public ResponseEntity<?> getSortMaster(@PathVariable String sortingId, @RequestParam String languageId, @RequestParam String companyId,
                                         @RequestParam String zoneType) {
        ReplicaSortingMaster dbSortMaster = sortingMasterService.getReplicaSortingMaster(languageId, companyId, sortingId,zoneType);
        return new ResponseEntity<>(dbSortMaster, HttpStatus.OK);
    }

    // Find Sort Master
    @ApiOperation(response = ReplicaSortingMaster.class, value = "Find Sort Master") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findSortMaster(@Valid @RequestBody FindSortMaster sortingMaster) throws Exception {
        List<ReplicaSortingMaster> sortingMasterList = sortingMasterService.findSortMaster(sortingMaster);
        return new ResponseEntity<>(sortingMasterList, HttpStatus.OK);
    }



}
