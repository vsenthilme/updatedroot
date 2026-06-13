package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.replica.model.company.ReplicaCompany;
import com.opencsv.exceptions.CsvException;
import com.courier.overc360.api.idmaster.primary.model.company.AddCompany;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.company.UpdateCompany;
import com.courier.overc360.api.idmaster.replica.model.company.FindCompany;
import com.courier.overc360.api.idmaster.service.CompanyService;
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
@Api(tags = {"Company"}, value = "Company Operations related to CompanyController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Company", description = "Operations related to Company")})
@RequestMapping("/company")
@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Company
    @ApiOperation(response = Company.class, value = "Create new Company") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCompany(@Valid @RequestBody AddCompany addCompany, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Company createdCompany = companyService.createCompany(addCompany, loginUserID);
        return new ResponseEntity<>(createdCompany, HttpStatus.OK);
    }

    // Update Company
    @ApiOperation(response = Company.class, value = "Update Company") // label for swagger
    @PatchMapping("/{companyId}")
    public ResponseEntity<?> patchCompany(@PathVariable String companyId, @RequestParam String languageId, @RequestParam String loginUserID,
                                          @Valid @RequestBody UpdateCompany updateCompany)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Company createdCompany = companyService.updateCompany(companyId, languageId, loginUserID, updateCompany);
        return new ResponseEntity<>(createdCompany, HttpStatus.OK);
    }

    // Delete Company
    @ApiOperation(response = Company.class, value = "Delete Company") // label for swagger
    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable String companyId, @RequestParam String languageId,
                                           @RequestParam String loginUserID) {
        companyService.deleteCompany(companyId, languageId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Company Details
    @ApiOperation(response = ReplicaCompany.class, value = "Get all Company details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCompanyDetails() {
        List<ReplicaCompany> companyList = companyService.getAllCompanyDetails();
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

    // Get Company
    @ApiOperation(response = ReplicaCompany.class, value = "Get a Company") // label for swagger
    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompany(@PathVariable String companyId, @RequestParam String languageId) {
        ReplicaCompany dbCompany = companyService.replicaGetCompany(companyId, languageId);
        return new ResponseEntity<>(dbCompany, HttpStatus.OK);
    }


    // Find Company
    @ApiOperation(response = ReplicaCompany.class, value = "Find Company") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCompany(@Valid @RequestBody FindCompany findCompany) throws Exception {
        List<ReplicaCompany> companyList = companyService.findCompany(findCompany);
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

}