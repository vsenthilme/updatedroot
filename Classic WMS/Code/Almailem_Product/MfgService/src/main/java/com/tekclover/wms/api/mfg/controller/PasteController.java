package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.paste.SearchPaste;
import com.tekclover.wms.api.mfg.service.PasteService;
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
@Api(tags = {"Paste"}, value = "Paste  Operations related to PasteController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Paste ", description = "Operations related to Paste")})
@RequestMapping("/paste")
@RestController
public class PasteController {

    @Autowired
    PasteService pasteService;

    //-----------------------------------------Soaking Controller------------------------------------------------------

    //Get Gingerpaste
    @ApiOperation(response = Paste.class, value = "Get a Paste")
    @GetMapping("/{productionOrderNo}")
    public ResponseEntity<?> getPaste(@PathVariable String productionOrderNo , @RequestParam String languageId,
                                            @RequestParam String companyCodeId, @RequestParam String plantId,
                                            @RequestParam String warehouseId, @RequestParam Long productionOrderLineNo,
                                            @RequestParam String recipeId, @RequestParam String operationNumber,
                                            @RequestParam String itemCode){
        Paste paste = pasteService.getPaste(languageId, companyCodeId, plantId, warehouseId, productionOrderNo, productionOrderLineNo, recipeId, operationNumber, itemCode);
        return new ResponseEntity<>(paste, HttpStatus.OK);
    }

    //Get BulkPaste
    @ApiOperation(response = Paste.class, value = "Get BulkPaste")
    @GetMapping("/v2/{productionOrderNo}")
    public ResponseEntity<?> getBulkPaste(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                                @RequestParam String plantId, @RequestParam String languageId,
                                                @RequestParam String warehouseId){
        List<Paste> paste = pasteService.getBulkPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        return new ResponseEntity<>(paste, HttpStatus.OK);
    }


    //Create Paste
    @ApiOperation(response = Paste.class, value = "Create Paste")
    @PostMapping("")
    public ResponseEntity<?> createPaste(@RequestBody List<Paste> addPaste, @RequestParam String loginUserID){
        List<Paste> paste = pasteService.createPaste(addPaste, loginUserID);
        return new ResponseEntity<>(paste, HttpStatus.OK);
    }

    //Update Paste
    @ApiOperation(response = Paste.class, value = "Patch Paste")
    @PatchMapping("{productionOrderNo}")
    public ResponseEntity<?> updatePaste(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                               @RequestParam String languageId, @RequestParam String plantId,
                                               @RequestParam String warehouseId, @RequestParam String loginUserID,
                                               @Valid @RequestBody List<Paste> modifyPaste ){
        List<Paste> updatePaste = pasteService.updatePaste(languageId, plantId, companyCodeId, warehouseId, productionOrderNo, loginUserID, modifyPaste);
        return new ResponseEntity<>(updatePaste, HttpStatus.OK);
    }

    //Delete Paste
    @ApiOperation(response = Paste.class, value = "Delete Paste")
    @DeleteMapping("/{productionOrderNo}")
    public ResponseEntity<?> deletePaste(@PathVariable String productionOrderNo, @RequestParam String companyCodeId,
                                               @RequestParam String plantId, @RequestParam String languageId,
                                               @RequestParam String warehouseId, @RequestParam String loginUserID){
        pasteService.deletePaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Find Paste
    @ApiOperation(response = Paste.class, value = "Find Paste")
    @PostMapping("/findPaste")
    public Stream<Paste> findPaste(@RequestBody SearchPaste searchpaste) throws Exception{
        return pasteService.findPaste(searchpaste);
    }
}
