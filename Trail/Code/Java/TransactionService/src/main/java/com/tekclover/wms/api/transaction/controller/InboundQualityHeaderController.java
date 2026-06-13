package com.tekclover.wms.api.transaction.controller;

import com.tekclover.wms.api.transaction.model.inbound.inboundquality.InboundQualityHeader;
import com.tekclover.wms.api.transaction.model.inbound.inboundquality.SearchInboundQualityHeader;
import com.tekclover.wms.api.transaction.service.InboundQualityHeaderService;
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
@Api(tags = {"InboundQualityHeader"}, value = "InboundQualityHeader  Operations related to InboundQualityHeaderController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "InboundQualityHeader ", description = "Operations related to InboundQualityHeader")})
@RequestMapping("/inboundqualityheader")
@RestController
public class InboundQualityHeaderController {

    @Autowired
    InboundQualityHeaderService inboundQualityHeaderService;

    @ApiOperation(response = InboundQualityHeader.class, value = "Get a InboundQualityHeader") // label for swagger 
    @GetMapping("/{inboundQualityNumber}")
    public ResponseEntity<?> getInboundQualityHeader(@PathVariable String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                     @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                     @RequestParam String preInboundNo, @RequestParam String itemCode) {
        InboundQualityHeader inboundqualityheader = inboundQualityHeaderService.getInboundQualityHeader(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber, itemCode);
        return new ResponseEntity<>(inboundqualityheader, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityHeader.class, value = "Search InboundQualityHeader") // label for swagger
    @PostMapping("/findInboundQualityHeader")
    public Stream<InboundQualityHeader> findInboundQualityHeader(@RequestBody SearchInboundQualityHeader searchInboundQualityHeader)
            throws Exception {
        return inboundQualityHeaderService.findInboundQualityHeader(searchInboundQualityHeader);
    }

    @ApiOperation(response = InboundQualityHeader.class, value = "Create InboundQualityHeader") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postInboundQualityHeader(@Valid @RequestBody InboundQualityHeader newInboundQualityHeader, @RequestParam String loginUserID) {
        InboundQualityHeader createdInboundQualityHeader = inboundQualityHeaderService.createInboundQualityHeader(newInboundQualityHeader, loginUserID);
        return new ResponseEntity<>(createdInboundQualityHeader, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityHeader.class, value = "Patch InboundQualityHeader") // label for swagger
    @PatchMapping("/{inboundQualityNumber}")
    public ResponseEntity<?> patchInboundQualityHeader(@PathVariable String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                       @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                       @RequestParam String preInboundNo, @RequestParam String itemCode, @RequestParam String loginUserID,
                                                       @Valid @RequestBody InboundQualityHeader modifyInboundQualityHeader) {
        InboundQualityHeader createdInboundQualityHeader = inboundQualityHeaderService.updateInboundQualityHeader(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber, itemCode, loginUserID, modifyInboundQualityHeader);
        return new ResponseEntity<>(createdInboundQualityHeader, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityHeader.class, value = "Create InboundQualityHeader") // label for swagger
    @PostMapping("/batch")
    public ResponseEntity<?> postInboundQualityHeaderBatch(@Valid @RequestBody List<InboundQualityHeader> newInboundQualityHeader, @RequestParam String loginUserID) {
        List<InboundQualityHeader> createdInboundQualityHeader = inboundQualityHeaderService.createInboundQualityHeaderBatch(newInboundQualityHeader, loginUserID);
        return new ResponseEntity<>(createdInboundQualityHeader, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityHeader.class, value = "Patch InboundQualityHeader") // label for swagger
    @PatchMapping("/batch")
    public ResponseEntity<?> patchInboundQualityHeaderBatch(@RequestParam String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                            @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                            @RequestParam String preInboundNo, @RequestParam String loginUserID,
                                                            @Valid @RequestBody List<InboundQualityHeader> modifyInboundQualityHeader) {
        List<InboundQualityHeader> createdInboundQualityHeader = inboundQualityHeaderService.updateInboundQualityHeaderBatch(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber, loginUserID, modifyInboundQualityHeader);
        return new ResponseEntity<>(createdInboundQualityHeader, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityHeader.class, value = "Delete InboundQualityHeader") // label for swagger
    @DeleteMapping("/{inboundQualityNumber}")
    public ResponseEntity<?> deleteInboundQualityHeader(@PathVariable String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                        @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                        @RequestParam String preInboundNo, @RequestParam String itemCode, @RequestParam String loginUserID) {
        inboundQualityHeaderService.deleteInboundQualityHeader(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber, itemCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}