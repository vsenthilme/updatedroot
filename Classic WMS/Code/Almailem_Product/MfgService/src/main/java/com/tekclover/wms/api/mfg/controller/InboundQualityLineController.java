package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.inboundquality.InboundQualityLine;
import com.tekclover.wms.api.mfg.model.inboundquality.SearchInboundQualityLine;
import com.tekclover.wms.api.mfg.service.InboundQualityLineService;
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
@Api(tags = {"InboundQualityLine"}, value = "InboundQualityLine  Operations related to InboundQualityLineController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "InboundQualityLine ", description = "Operations related to InboundQualityLine")})
@RequestMapping("/inboundqualityline")
@RestController
public class InboundQualityLineController {

    @Autowired
    InboundQualityLineService inboundQualityLineService;

    @ApiOperation(response = InboundQualityLine.class, value = "Get a InboundQualityLine") // label for swagger 
    @GetMapping("/{inboundQualityNumber}")
    public ResponseEntity<?> getInboundQualityLine(@PathVariable String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                   @RequestParam String preInboundNo, @RequestParam Long lineNo, @RequestParam String itemCode) {
        InboundQualityLine inboundqualityline = inboundQualityLineService.getInboundQualityLine(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber, lineNo, itemCode);
        return new ResponseEntity<>(inboundqualityline, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityLine.class, value = "Get all InboundQualityLines") // label for swagger
    @GetMapping("/v2/{inboundQualityNumber}")
    public ResponseEntity<?> getInboundQualityLine(@PathVariable String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                   @RequestParam String preInboundNo) {
        List<InboundQualityLine> inboundqualityline = inboundQualityLineService.getInboundQualityLines(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber);
        return new ResponseEntity<>(inboundqualityline, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityLine.class, value = "Search InboundQualityLine") // label for swagger
    @PostMapping("/findInboundQualityLine")
    public Stream<InboundQualityLine> findInboundQualityLine(@RequestBody SearchInboundQualityLine searchInboundQualityLine)
            throws Exception {
        return inboundQualityLineService.findInboundQualityLine(searchInboundQualityLine);
    }

    @ApiOperation(response = InboundQualityLine.class, value = "Create InboundQualityLine") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postInboundQualityLine(@Valid @RequestBody List<InboundQualityLine> newInboundQualityLine, @RequestParam String loginUserID) {
        List<InboundQualityLine> createdInboundQualityLine = inboundQualityLineService.createInboundQualityLine(newInboundQualityLine, loginUserID);
        return new ResponseEntity<>(createdInboundQualityLine, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityLine.class, value = "Patch InboundQualityLine") // label for swagger
    @PatchMapping("/{inboundQualityNumber}")
    public ResponseEntity<?> patchInboundQualityLine(@PathVariable String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                     @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                     @RequestParam String preInboundNo, @RequestParam String itemCode, @RequestParam String loginUserID,
                                                     @Valid @RequestBody List<InboundQualityLine> modifyInboundQualityLine) {
        List<InboundQualityLine> createdInboundQualityLine = inboundQualityLineService.updateInboundQualityLine(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber, loginUserID, modifyInboundQualityLine);
        return new ResponseEntity<>(createdInboundQualityLine, HttpStatus.OK);
    }

    @ApiOperation(response = InboundQualityLine.class, value = "Delete InboundQualityLine") // label for swagger
    @DeleteMapping("/{inboundQualityNumber}")
    public ResponseEntity<?> deleteInboundQualityLine(@PathVariable String inboundQualityNumber, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                      @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String refDocNumber,
                                                      @RequestParam String preInboundNo, @RequestParam String loginUserID) {
        inboundQualityLineService.deleteInboundQualityLine(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundQualityNumber, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}