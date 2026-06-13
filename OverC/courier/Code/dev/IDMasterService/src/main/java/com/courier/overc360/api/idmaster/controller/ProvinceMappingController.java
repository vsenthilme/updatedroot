package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.provincemapping.AddProvinceMapping;
import com.courier.overc360.api.idmaster.primary.model.provincemapping.ProvinceMapping;
import com.courier.overc360.api.idmaster.primary.model.provincemapping.UpdateProvinceMapping;
import com.courier.overc360.api.idmaster.replica.model.provincemapping.FindProvinceMapping;
import com.courier.overc360.api.idmaster.replica.model.provincemapping.ReplicaProvinceMapping;
import com.courier.overc360.api.idmaster.service.ProvinceMappingService;
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
@Api(tags = {"ProvinceMapping"}, value = "ProvinceMapping Operations related to ProvinceController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ProvinceMapping", description = "Operations related to ProvinceMapping")})
@RequestMapping("/provinceMapping")
@RestController
public class ProvinceMappingController {

    @Autowired
    ProvinceMappingService provinceMappingService;

    /*--------------------------------------------------PRIMARY------------------------------------------------------*/

    // Create ProvinceMapping
    @ApiOperation(response = ProvinceMapping.class, value = "Create new ProvinceMapping") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postProvinceMapping(@Valid @RequestBody AddProvinceMapping addProvinceMapping, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ProvinceMapping provinceMapping = provinceMappingService.createProvinceMapping(addProvinceMapping, loginUserID);
        return new ResponseEntity<>(provinceMapping, HttpStatus.OK);
    }

    // Update ProvinceMapping
    @ApiOperation(response = ProvinceMapping.class, value = "Update ProvinceMapping") // label for swagger
    @PatchMapping("/{partnerId}")
    public ResponseEntity<?> patchProvinceMapping(@PathVariable String partnerId, @RequestParam String provinceId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String loginUserID, @RequestBody UpdateProvinceMapping updateProvinceMapping)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ProvinceMapping provinceMapping = provinceMappingService.updateProvinceMapping(languageId, companyId, provinceId, partnerId, updateProvinceMapping, loginUserID);
        return new ResponseEntity<>(provinceMapping, HttpStatus.OK);
    }

    // Delete ProvinceMapping
    @ApiOperation(response = ProvinceMapping.class, value = "Delete ProvinceMapping") // label for swagger
    @DeleteMapping("/{partnerId}")
    public ResponseEntity<?> deleteProvinceMapping(@PathVariable String partnerId, @RequestParam String provinceId, @RequestParam String companyId,
                                                   @RequestParam String languageId, @RequestParam String loginUserID) {
        provinceMappingService.deleteProvinceMapping(languageId, companyId, provinceId, partnerId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    // Get All ProvinceMapping Details
    @ApiOperation(response = ReplicaProvinceMapping.class, value = "Get all ProvinceMapping Details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllProvinceMappingDetails() {
        List<ReplicaProvinceMapping> provinceMappingList = provinceMappingService.getAllProvinceMappings();
        return new ResponseEntity<>(provinceMappingList, HttpStatus.OK);
    }

    // Get ProvinceMapping
    @ApiOperation(response = ReplicaProvinceMapping.class, value = "Get a ProvinceMapping") // label for swagger
    @GetMapping("/{partnerId}")
    public ResponseEntity<?> getProvinceMapping(@PathVariable String partnerId, @RequestParam String provinceId,
                                                @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaProvinceMapping provinceMapping = provinceMappingService.getProvinceMappingReplica(languageId, companyId, provinceId, partnerId);
        return new ResponseEntity<>(provinceMapping, HttpStatus.OK);
    }

    // Find ProvinceMapping
    @ApiOperation(response = ReplicaProvinceMapping.class, value = "Find ProvinceMapping") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findProvinceMapping(@Valid @RequestBody FindProvinceMapping findProvinceMapping) throws Exception {
        List<ReplicaProvinceMapping> provinceMappingList = provinceMappingService.findProvinceMappings(findProvinceMapping);
        return new ResponseEntity<>(provinceMappingList, HttpStatus.OK);
    }

}
