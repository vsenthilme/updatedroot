package com.tekclover.wms.api.mfg.controller;

import com.tekclover.wms.api.mfg.model.prodcutionorder.*;
import com.tekclover.wms.api.mfg.service.OrderConfirmationService;
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

@Slf4j
@Validated
@Api(tags = {"BlackDal"}, value = "BlackDal  Operations related to BlackDalController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "BlackDal", description = "Operations related to BlackDal")})
@RequestMapping("/orderconfirmation")
@RestController
public class OrderConfirmationController {

    @Autowired
    OrderConfirmationService orderConfirmationService;

    //-------------------------------------OrderConfirmationController--------------------------------------------------

    //Update BlackDal
    @ApiOperation(response = BlackDal.class, value = "Patch BlackDal")
    @PatchMapping("/blackDal")
    public ResponseEntity<?> updateBlackDal(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String loginUserID, @Valid @RequestBody BlackDal modifyBlackDal) {
        orderConfirmationService.updateBlackDal(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, modifyBlackDal, loginUserID);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Create BlackDal
    @ApiOperation(response = BlackDal.class, value = "Post BlackDal")
    @PostMapping("/blackDal/create")
    public ResponseEntity<?> createBlackDal(@Valid @RequestBody BlackDal blackDal, @RequestParam String loginUserID) {
        orderConfirmationService.createBlackDal(blackDal, loginUserID);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(response = BlackDal.class, value = "Get BlackDal")
    @GetMapping("/blackDal/get")
    public ResponseEntity<?> getBlackDalOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        BlackDal blackDal = orderConfirmationService.getBlackDal(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(blackDal, HttpStatus.OK);
    }

    //Update ChanaDal
    @ApiOperation(response = ChanaDal.class, value = "Patch ChanaDal")
    @PatchMapping("/chanaDal")
    public ResponseEntity<?> updateChanaDal(@RequestParam String companyCodeID, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String loginUserID, @Valid @RequestBody ChanaDal modifyChanaDal) {
        orderConfirmationService.updateChanaDal(companyCodeID, plantId, languageId, warehouseId, productionOrderNo, modifyChanaDal, loginUserID);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Create ChanaDal
    @ApiOperation(response = ChanaDal.class, value = "Post ChanaDal")
    @PostMapping("/chanaDal/create")
    public ResponseEntity<?> createChanaDal(@Valid @RequestBody ChanaDal chanaDal, @RequestParam String loginUserID) {
        orderConfirmationService.createChanaDal(chanaDal, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get ChanaDal
    @ApiOperation(response = ChanaDal.class, value = "Get ChanaDal")
    @GetMapping("/chanaDal/get")
    public ResponseEntity<?> getChanaDalOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        ChanaDal chanaDal = orderConfirmationService.getChanaDal(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(chanaDal, HttpStatus.OK);
    }

    //-----------------------------------------------------------------------------------------

    //Create ChitraRajma
    @ApiOperation(response = ChitraRajma.class, value = "Post ChitraRajma")
    @PostMapping("/chitraRajma/create")
    public ResponseEntity<?> createChitraRajma(@Valid @RequestBody ChitraRajma chitraRajma, @RequestParam String loginUserID) {
        orderConfirmationService.createChitraRajma(chitraRajma, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get ChitraRajma
    @ApiOperation(response = ChitraRajma.class, value = "Get ChitraRajma")
    @GetMapping("/chitraRajma/get")
    public ResponseEntity<?> getChitraRajmaOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        ChitraRajma chitraRajma = orderConfirmationService.getChitraRajma(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(chitraRajma, HttpStatus.OK);
    }

    //-------------------------------------------------------------------------------------------

    //Create TomatoPuree
    @ApiOperation(response = TomatoPuree.class, value = "Post TomatoPuree")
    @PostMapping("/tomatoPuree/create")
    public ResponseEntity<?> createTomatoPuree(@Valid @RequestBody TomatoPuree tomatoPuree, @RequestParam String loginUserID) {
        orderConfirmationService.createTomatoPuree(tomatoPuree, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get TomatoPuree
    @ApiOperation(response = TomatoPuree.class, value = "Get TomatoPuree")
    @GetMapping("/tomatoPuree/get")
    public ResponseEntity<?> getTomatoPureeOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        TomatoPuree tomatoPuree = orderConfirmationService.getTomatoPuree(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(tomatoPuree, HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------

    //Create GingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Get GingerPaste")
    @PostMapping("/gingerPaste/create")
    public ResponseEntity<?> createGingerPaste(@Valid @RequestBody GingerPaste gingerPaste, @RequestParam String loginUserID) {
        orderConfirmationService.createGingerPaste(gingerPaste, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get GingerPaste
    @ApiOperation(response = GingerPaste.class, value = "Get GingerPaste")
    @GetMapping("/gingerPaste/get")
    public ResponseEntity<?> getGingerPasteOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        GingerPaste gingerPaste = orderConfirmationService.getGingerPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(gingerPaste, HttpStatus.OK);
    }

    //-------------------------------------------------------------------------------------------

    //Create GarlicPaste
    @ApiOperation(response = GarlicPaste.class, value = "Get GarlicPaste")
    @PostMapping("/garlicPaste/create")
    public ResponseEntity<?> createGarlicPaste(@Valid @RequestBody GarlicPaste garlicPaste, @RequestParam String loginUserID) {
        orderConfirmationService.createGarlicPaste(garlicPaste, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get GarlicPaste
    @ApiOperation(response = GarlicPaste.class, value = "Get GarlicPaste")
    @GetMapping("/garlicPaste/get")
    public ResponseEntity<?> getGarlicPasteOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        GarlicPaste garlicPaste = orderConfirmationService.getGarlicPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(garlicPaste, HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------------

    //Create GarlicChop
    @ApiOperation(response = GarlicChop.class, value = "Get GarlicPaste")
    @PostMapping("/garlicChop/create")
    public ResponseEntity<?> createGarlicChop(@Valid @RequestBody GarlicChop garlicChop, @RequestParam String loginUserID) {
        orderConfirmationService.createGarlicChop(garlicChop, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get GarlicChop
    @ApiOperation(response = GarlicChop.class, value = "Get GarlicPaste")
    @GetMapping("/garlicChop/get")
    public ResponseEntity<?> getGarlicChopOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        GarlicChop garlicChop = orderConfirmationService.getGarlicChop(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(garlicChop, HttpStatus.OK);
    }

    //--------------------------------------------------------------------------------------

    //Create RoastedKasturiMethi
    @ApiOperation(response = RoastedKasturiMethi.class, value = "Get RoastedKasturiMethi")
    @PostMapping("/roastedKasturiMethi/create")
    public ResponseEntity<?> createRoastedKasturiMethi(@Valid @RequestBody RoastedKasturiMethi roastedKasturiMethi, @RequestParam String loginUserID ) {
        orderConfirmationService.createRoastedKasturiMethi(roastedKasturiMethi, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get RoastedKasturiMethi
    @ApiOperation(response = RoastedKasturiMethi.class, value = "Get RoastedKasturiMethi")
    @GetMapping("/roastedKasturiMethi/get")
    public ResponseEntity<?> getRoastedKasturiMethiOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        RoastedKasturiMethi roastedKasturiMethi = orderConfirmationService.getRoastedKasturiMethi(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(roastedKasturiMethi, HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------------------

    //Create RoastedCuminPowder
    @ApiOperation(response = RoastedCuminPowder.class, value = "Post RoastedCuminPowder")
    @PostMapping("/roastedCuminPowder/create")
    public ResponseEntity<?> createRoastedCuminPowder(@Valid @RequestBody RoastedCuminPowder roastedCuminPowder, @RequestParam String loginUserID ) {
        orderConfirmationService.createRoastedCuminPowder(roastedCuminPowder, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get RoastedCuminPowder
    @ApiOperation(response = RoastedCuminPowder.class, value = "Get RoastedCuminPowder")
    @GetMapping("/roastedCuminPowder/get")
    public ResponseEntity<?> getRoastedCuminPowderOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        RoastedCuminPowder roastedCuminPowder = orderConfirmationService.getRoastedCuminPowder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(roastedCuminPowder, HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------

    //Create OrderConfirmation
    @ApiOperation(response = OrderConfirmation.class, value = "Post Confirm OrderConfirmation")
    @PostMapping("/create")
    public ResponseEntity<?> createOrderConfirmation(@Valid @RequestBody OrderConfirmation orderConfirmation, @RequestParam String loginUserID) {
        orderConfirmationService.createOrderConfirmation(orderConfirmation, loginUserID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Get OrderConfirmation
    @ApiOperation(response = OrderConfirmation.class, value = "Get OrderConfirmation")
    @GetMapping("/get")
    public ResponseEntity<?> getOrderConfirmation(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String productionOrderNo, @RequestParam String batchNumber) {
        OrderConfirmation orderConfirmation = orderConfirmationService.getOrderConfirmation(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);
        return new ResponseEntity<>(orderConfirmation, HttpStatus.OK);
    }
}
