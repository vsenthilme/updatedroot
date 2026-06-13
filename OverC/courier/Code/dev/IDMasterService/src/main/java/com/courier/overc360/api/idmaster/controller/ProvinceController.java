package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.province.AddProvince;
import com.courier.overc360.api.idmaster.primary.model.province.Province;
import com.courier.overc360.api.idmaster.primary.model.province.UpdateProvince;
import com.courier.overc360.api.idmaster.replica.model.province.FindProvince;
import com.courier.overc360.api.idmaster.replica.model.province.ReplicaProvince;
import com.courier.overc360.api.idmaster.service.ProvinceService;
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
@Api(tags = {"Province"}, value = "Province country related to ProvinceController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Province", description = "Operations related to Province")})
@RequestMapping("/province")
@RestController
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Province
    @ApiOperation(response = Province.class, value = "Create new Province") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postProvince(@Valid @RequestBody AddProvince addProvince, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Province newProvince = provinceService.createProvince(addProvince, loginUserID);
        return new ResponseEntity<>(newProvince, HttpStatus.OK);
    }

    // Update Province
    @ApiOperation(response = Province.class, value = "Update Province") // label for swagger
    @PatchMapping("/{provinceId}")
    public ResponseEntity<?> patchProvince(@PathVariable String provinceId, @RequestParam String countryId, @RequestParam String languageId,
                                           @RequestParam String companyId, @RequestParam String loginUserID, @RequestBody UpdateProvince updateProvince)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Province updatedProvince = provinceService.updateProvince(languageId, companyId, countryId, provinceId, updateProvince, loginUserID);
        return new ResponseEntity<>(updatedProvince, HttpStatus.OK);
    }

    // Delete Province
    @ApiOperation(response = Province.class, value = "Delete Province") // label for swagger
    @DeleteMapping("/{provinceId}")
    public ResponseEntity<?> deleteProvince(@PathVariable String provinceId, @RequestParam String countryId, @RequestParam String companyId,
                                            @RequestParam String languageId, @RequestParam String loginUserID) {
        provinceService.deleteProvince(languageId, companyId, countryId, provinceId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Province Details
    @ApiOperation(response = ReplicaProvince.class, value = "Get all Province Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllProvinceDetails() {
        List<ReplicaProvince> provinceList = provinceService.getAllProvinces();
        return new ResponseEntity<>(provinceList, HttpStatus.OK);
    }

    // Get Province
    @ApiOperation(response = ReplicaProvince.class, value = "Get a Province") // label for swagger
    @GetMapping("/{provinceId}")
    public ResponseEntity<?> getProvince(@PathVariable String provinceId, @RequestParam String countryId,
                                         @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaProvince dbProvince = provinceService.getReplicaProvince(languageId, companyId, countryId, provinceId);
        return new ResponseEntity<>(dbProvince, HttpStatus.OK);
    }

    // Find Province
    @ApiOperation(response = ReplicaProvince.class, value = "Find Province") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findProvince(@Valid @RequestBody FindProvince findProvince) throws Exception {
        List<ReplicaProvince> provinceList = provinceService.replicaFindProvinces(findProvince);
        return new ResponseEntity<>(provinceList, HttpStatus.OK);
    }
}
