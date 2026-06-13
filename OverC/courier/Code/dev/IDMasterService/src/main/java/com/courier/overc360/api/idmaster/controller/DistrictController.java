package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.district.AddDistrict;
import com.courier.overc360.api.idmaster.primary.model.district.District;
import com.courier.overc360.api.idmaster.primary.model.district.UpdateDistrict;
import com.courier.overc360.api.idmaster.replica.model.district.FindDistrict;
import com.courier.overc360.api.idmaster.replica.model.district.ReplicaDistrict;
import com.courier.overc360.api.idmaster.service.DistrictService;
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
@Api(tags = {"District"}, value = "District related to DistrictController")
@SwaggerDefinition(tags = {@Tag(name = "District", description = "Operations related to District")})
@RequestMapping("/district")
@RestController
public class DistrictController {

    @Autowired
    DistrictService districtService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create District
    @ApiOperation(response = District.class, value = "Create New District")
    @PostMapping("")
    public ResponseEntity<?> createDistrict(@Valid @RequestBody AddDistrict addDistrict, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        District newDistrict = districtService.createDistrict(addDistrict, loginUserID);
        return new ResponseEntity<>(newDistrict, HttpStatus.OK);
    }

    // Update Product
    @ApiOperation(response = District.class, value = "Update District")
    @PatchMapping("/{districtId}")
    public ResponseEntity<?> patchProduct(@PathVariable String districtId, @RequestParam String languageId, @RequestParam String companyId,
                                          @RequestParam String countryId, @RequestParam String provinceId, @RequestParam String loginUserID,
                                          @RequestBody UpdateDistrict updateDistrict)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        District updatedDistrict = districtService.updateDistrict(languageId, companyId, countryId, provinceId, districtId, updateDistrict, loginUserID);
        return new ResponseEntity<>(updatedDistrict, HttpStatus.OK);
    }

    // Delete District
    @ApiOperation(response = District.class, value = "Delete District")
    @DeleteMapping("/{districtId}")
    public ResponseEntity<?> deleteDistrict(@PathVariable String districtId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String countryId, @RequestParam String provinceId, @RequestParam String loginUserID) {
        districtService.deleteDistrict(languageId, companyId, countryId, provinceId, districtId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All District Details
    @ApiOperation(response = ReplicaDistrict.class, value = "Get all District Details")
    @GetMapping("")
    public ResponseEntity<?> getAllDistrictDetails() {
        List<ReplicaDistrict> districtList = districtService.getAllDistricts();
        return new ResponseEntity<>(districtList, HttpStatus.OK);
    }

    // Get District
    @ApiOperation(response = ReplicaDistrict.class, value = "Get a District")
    @GetMapping("/{districtId}")
    public ResponseEntity<?> getDistrict(@PathVariable String districtId, @RequestParam String languageId, @RequestParam String companyId,
                                         @RequestParam String countryId, @RequestParam String provinceId) {
        ReplicaDistrict dbDistrict = districtService.getReplicaDistrict(languageId, companyId, countryId, provinceId, districtId);
        return new ResponseEntity<>(dbDistrict, HttpStatus.OK);
    }

    // Find District
    @ApiOperation(response = ReplicaDistrict.class, value = "Find District")
    @PostMapping("/find")
    public ResponseEntity<?> findDistricts(@Valid @RequestBody FindDistrict findDistrict) throws Exception {
        List<ReplicaDistrict> districtList = districtService.findDistrict(findDistrict);
        return new ResponseEntity<>(districtList, HttpStatus.OK);
    }
}
