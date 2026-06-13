package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.partnerhubmapping.AddPartnerHubMapping;
import com.courier.overc360.api.idmaster.primary.model.partnerhubmapping.PartnerHubMapping;
import com.courier.overc360.api.idmaster.primary.model.partnerhubmapping.UpdatePartnerHubMapping;
import com.courier.overc360.api.idmaster.replica.model.partnerhubmapping.FindPartnerHubMapping;
import com.courier.overc360.api.idmaster.replica.model.partnerhubmapping.ReplicaPartnerHubMapping;
import com.courier.overc360.api.idmaster.service.PartnerHubMappingService;
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
@Api(tags = {"PartnerHubMapping"}, value = "PartnerHubMapping Operations related to PartnerHubMappingController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "PartnerHubMapping", description = "Operations related to PartnerHubMapping")})
@RequestMapping("/partnerHubMapping")
@RestController
public class PartnerHubMappingController {

    @Autowired
    PartnerHubMappingService partnerHubMappingService;

    /*--------------------------------------------------PRIMARY------------------------------------------------------*/

    // Create PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping.class, value = "Create new PartnerHubMapping") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postPartnerHubMapping(@Valid @RequestBody AddPartnerHubMapping addPartnerHubMapping, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        PartnerHubMapping newPartnerHubMapping = partnerHubMappingService.createPartnerHubMapping(addPartnerHubMapping, loginUserID);
        return new ResponseEntity<>(newPartnerHubMapping, HttpStatus.OK);
    }

    // Update PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping.class, value = "Update PartnerHubMapping") // label for swagger
    @PatchMapping("/{partnerId}")
    public ResponseEntity<?> patchPartnerHubMapping(@PathVariable String partnerId, @RequestParam String partnerType,
                                                    @RequestParam String languageId, @RequestParam String loginUserID,
                                                    @RequestParam String companyId, @RequestParam String hubCode,
                                                    @RequestParam String productCode, @RequestBody UpdatePartnerHubMapping updatePartnerHubMapping)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        PartnerHubMapping dbPartnerHubMapping = partnerHubMappingService.updatePartnerHubMapping(languageId, companyId, hubCode, partnerType, partnerId, productCode,updatePartnerHubMapping, loginUserID);
        return new ResponseEntity<>(dbPartnerHubMapping, HttpStatus.OK);
    }

    // Delete Hub
    @ApiOperation(response = PartnerHubMapping.class, value = "Delete PartnerHubMapping") // label for swagger
    @DeleteMapping("/{partnerId}")
    public ResponseEntity<?> deletePartnerHubMapping(@PathVariable String partnerId, @RequestParam String partnerType,
                                                     @RequestParam String languageId, @RequestParam String loginUserID,
                                                     @RequestParam String companyId, @RequestParam String hubCode,@RequestParam String productCode) {
        partnerHubMappingService.deletePartnerHubMapping(languageId, companyId, hubCode, partnerType, partnerId, loginUserID,productCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    // Get All PartnerHubMapping Details
    @ApiOperation(response = ReplicaPartnerHubMapping.class, value = "Get all PartnerHubMapping Details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllPartnerHubMappingDetails() {
        List<ReplicaPartnerHubMapping> partnerHubMappings = partnerHubMappingService.getAllPartnerHubMappings();
        return new ResponseEntity<>(partnerHubMappings, HttpStatus.OK);
    }

    // Get PartnerHubMapping
    @ApiOperation(response = ReplicaPartnerHubMapping.class, value = "Get a PartnerHubMapping") // label for swagger
    @GetMapping("/{partnerId}")
    public ResponseEntity<?> getPartnerHubMapping(@PathVariable String partnerId, @RequestParam String partnerType, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String hubCode, @RequestParam String productCode) {
        ReplicaPartnerHubMapping dbPartnerHubMapping = partnerHubMappingService.getPartnerHubMappingReplica(languageId, companyId, hubCode, partnerType, partnerId, productCode);
        return new ResponseEntity<>(dbPartnerHubMapping, HttpStatus.OK);
    }

    // Find PartnerHubMapping
    @ApiOperation(response = ReplicaPartnerHubMapping.class, value = "Find PartnerHubMapping") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findPartnerHubMappings(@Valid @RequestBody FindPartnerHubMapping findPartnerHubMapping) throws Exception {
        List<ReplicaPartnerHubMapping> partnerHubMappingList = partnerHubMappingService.findPartnerHubMappings(findPartnerHubMapping);
        return new ResponseEntity<>(partnerHubMappingList, HttpStatus.OK);
    }

}
