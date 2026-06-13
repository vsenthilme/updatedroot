package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.gingerpaste.GingerPaste;
import com.tekclover.wms.api.mfg.model.gingerpaste.SearchGingerPaste;
import com.tekclover.wms.api.mfg.service.GingerPasteService;
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
@Api(tags = {"GingerPaste"}, value = "GingerPaste  Operations related to GingerPasteController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "GingerPaste ", description = "Operations related to GingerPaste")})
@RequestMapping("/gingerPaste")
@RestController
public class GingerPasteController {

    @Autowired
    GingerPasteService gingerPasteService;

    //-----------------------------------------GingerPaste Controller------------------------------------------------------

    //Get GingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Get a GingerPaste")
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getGingerPaste(@PathVariable String productionOrderNo , @RequestParam String languageId,
                                        @RequestParam String companyCodeId, @RequestParam String plantId,
                                        @RequestParam String warehouseId, @RequestParam Long productionOrderLineNo,
                                        @RequestParam String recipeId, @RequestParam String operationNumber,
                                        @RequestParam String itemCode){
        GingerPaste gingerPaste = gingerPasteService.getGingerPaste(languageId, companyCodeId, plantId, warehouseId, productionOrderNo, productionOrderLineNo, recipeId, operationNumber, itemCode);
        return new ResponseEntity<>(gingerPaste, HttpStatus.OK);
    }

    //Get BulkGingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Get BulkGingerPaste")
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getBulkGingerPaste(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                            @RequestParam String plantId, @RequestParam String languageId,
                                            @RequestParam String warehouseId){
        List<GingerPaste> gingerPaste = gingerPasteService.getBulkGingerPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(gingerPaste, HttpStatus.OK);
    }


    //Create GingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Create GingerPaste")
    @PostMapping("")
    public ResponseEntity<?> createGingerPaste(@RequestBody List<GingerPaste> addGingerPaste, @RequestParam String loginUserID){
        List<GingerPaste> gingerPaste = gingerPasteService.createGingerPaste(addGingerPaste, loginUserID);
        return new ResponseEntity<>(gingerPaste, HttpStatus.OK);
    }

    //Update GingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Patch GingerPaste")
    @PatchMapping("{productionOrderNo}")
    public ResponseEntity<?> updateGingerPaste(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId,
                                           @RequestParam String warehouseId, @RequestParam String loginUserID,
                                           @Valid @RequestBody List<GingerPaste> modifyGingerPaste ){
        List<GingerPaste> updateGingerPaste = gingerPasteService.updateGingerPaste(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, modifyGingerPaste);
        return new ResponseEntity<>(updateGingerPaste, HttpStatus.OK);
    }

    //Delete GingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Delete GingerPaste")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deleteGingerPaste(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                           @RequestParam String plantId, @RequestParam String languageId,
                                           @RequestParam String warehouseId, @RequestParam String loginUserID){
        gingerPasteService.deleteGingerPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Find GingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Find GingerPaste")
    @PostMapping("/findGingerPaste")
    public Stream<GingerPaste> findGingerPaste(@RequestBody SearchGingerPaste searchGingerPaste) throws Exception{
        return gingerPasteService.findGingerPaste(searchGingerPaste);
    }

}
