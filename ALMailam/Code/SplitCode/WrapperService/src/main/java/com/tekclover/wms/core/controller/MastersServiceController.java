package com.tekclover.wms.core.controller;

import com.tekclover.wms.core.model.auth.AuthToken;
import com.tekclover.wms.core.model.masters.*;
import com.tekclover.wms.core.model.threepl.*;
import com.tekclover.wms.core.model.transaction.ImPartnerInput;
import com.tekclover.wms.core.model.transaction.PaginatedResponse;
import com.tekclover.wms.core.model.warehouse.inbound.WarehouseApiResponse;
import com.tekclover.wms.core.model.warehouse.mastersorder.Customer;
import com.tekclover.wms.core.model.warehouse.mastersorder.ImBasicData1V2;
import com.tekclover.wms.core.model.warehouse.mastersorder.Item;
import com.tekclover.wms.core.service.AuthTokenService;
import com.tekclover.wms.core.service.MastersService;
import com.tekclover.wms.core.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wms-masters-service")
@Api(tags = {"Masters Service"}, value = "Masters Service Operations") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operations related to Masters Modules")})
public class MastersServiceController {

    @Autowired
    MastersService mastersService;

    @Autowired
    RegisterService registerService;

    @Autowired
    AuthTokenService authTokenService;

    private String getAuthToken() {
        AuthToken authTokenForMasterService = authTokenService.getMastersServiceAuthToken();
        return authTokenForMasterService.getAccess_token();
    }

    /* -----------------------------MASTERS---BomHeader---------------------------------------------------------------*/

    @ApiOperation(response = BomHeader.class, value = "Get all BomHeaders") // label for swagger
    @GetMapping("/bomheader")
    public ResponseEntity<?> getBomHeaders() {
        BomHeader[] parentItemCodeList = mastersService.getBomHeaders(getAuthToken());
        return new ResponseEntity<>(parentItemCodeList, HttpStatus.OK);
    }

    @ApiOperation(response = BomHeader.class, value = "Get a BomHeader") // label for swagger
    @GetMapping("/bomheader/{parentItemCode}")
    public ResponseEntity<?> getBomHeader(@PathVariable String parentItemCode, @RequestParam String languageId,
                                          @RequestParam String companyCode, @RequestParam String plantId, @RequestParam String warehouseId) {

        BomHeader dbBomHeader = mastersService.getBomHeader(warehouseId, parentItemCode, languageId, companyCode, plantId, getAuthToken());
        log.info("BomHeader : " + dbBomHeader);
        return new ResponseEntity<>(dbBomHeader, HttpStatus.OK);
    }

    @ApiOperation(response = BomHeader.class, value = "Search BomHeader") // label for swagger
    @PostMapping("/bomheader/findBomHeader")
    public BomHeader[] findBomHeader(@RequestBody SearchBomHeader searchBomHeader) throws Exception {
        return mastersService.findBomHeader(searchBomHeader, getAuthToken());
    }

    @ApiOperation(response = BomHeader.class, value = "Create BomHeader") // label for swagger
    @PostMapping("/bomheader")
    public ResponseEntity<?> postBomHeader(@Valid @RequestBody BomHeader newBomHeader, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {

        BomHeader createdBomHeader = mastersService.createBomHeader(newBomHeader, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdBomHeader, HttpStatus.OK);
    }

    @ApiOperation(response = BomHeader.class, value = "Update BomHeader") // label for swagger
    @RequestMapping(value = "/bomheader", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchBomHeader(@RequestParam String parentItemCode, @RequestParam String warehouseId, @RequestParam String languageId,
                                            @RequestParam String companyCode, @RequestParam String plantId,
                                            @RequestParam String loginUserID, @Valid @RequestBody BomHeader updateBomHeader)
            throws IllegalAccessException, InvocationTargetException {

        BomHeader updatedBomHeader = mastersService.updateBomHeader(warehouseId, parentItemCode, languageId, companyCode, plantId,
                loginUserID, updateBomHeader, getAuthToken());
        return new ResponseEntity<>(updatedBomHeader, HttpStatus.OK);
    }

    @ApiOperation(response = BomHeader.class, value = "Delete BomHeader") // label for swagger
    @DeleteMapping("/bomheader/{parentItemCode}")
    public ResponseEntity<?> deleteBomHeader(@PathVariable String parentItemCode, @RequestParam String warehouseId, @RequestParam String languageId,
                                             @RequestParam String companyCode, @RequestParam String plantId,
                                             @RequestParam String loginUserID) {

        mastersService.deleteBomHeader(warehouseId, parentItemCode, languageId, companyCode, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* -----------------------------MASTERS---Bom Line---------------------------------------------------------------*/

    @ApiOperation(response = BomLine.class, value = "Get all BomLines") // label for swagger
    @GetMapping("/bomline")
    public ResponseEntity<?> getBomLines() {
        BomLine[] bomLineList = mastersService.getBomLines(getAuthToken());
        return new ResponseEntity<>(bomLineList, HttpStatus.OK);
    }

    @ApiOperation(response = BomLine.class, value = "Get a BomLine") // label for swagger
    @GetMapping("/bomline/{bomNumber}")
    public ResponseEntity<?> getBomLine(@PathVariable Long bomNumber, @RequestParam String companyCode,
                                        @RequestParam String languageId, @RequestParam String plantId,
                                        @RequestParam String warehouseId, @RequestParam String childItemCode) {

        BomLine dbBomLine = mastersService.getBomLine(bomNumber, warehouseId, childItemCode, languageId, companyCode, plantId, getAuthToken());
        log.info("bomline : " + dbBomLine);
        return new ResponseEntity<>(dbBomLine, HttpStatus.OK);
    }

/*@ApiOperation(response = BomLine.class, value = "Search BomLine") // label for swagger
	@PostMapping("/bomline/findBomHeader")
	public BomHeader[] findBomLine(@RequestBody SearchBomLine searchBomLine) throws Exception {

		return mastersService.findBomHeader(searchBomHeader, getAuthToken());
	}

	@ApiOperation(response = BomLine.class, value = "Create BomLine") // label for swagger
	@PostMapping("/bomline")
	public ResponseEntity<?> postBomLine(@Valid @RequestBody BomLine newBomLine, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {

		BomHeader createdBomHeader = mastersService.createBomLine(newBomHeader, loginUserID, getAuthToken());
		return new ResponseEntity<>(createdBomHeader, HttpStatus.OK);
	}

	@ApiOperation(response = BomLine.class, value = "Update BomLine") // label for swagger
	@RequestMapping(value = "/bomline", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchBomLine(@RequestParam String parentItemCode,@RequestParam String warehouseId,@RequestParam String languageId,
											@RequestParam String companyCode,@RequestParam String plantId,
											@RequestParam String loginUserID, @Valid @RequestBody BomHeader updateBomHeader)
			throws IllegalAccessException, InvocationTargetException {

		BomHeader updatedBomHeader = mastersService.updateBomLine(warehouseId, parentItemCode,languageId,companyCode,plantId,
				loginUserID,updateBomHeader, getAuthToken());
		return new ResponseEntity<>(updatedBomHeader, HttpStatus.OK);
	}
*/

    @ApiOperation(response = BomLine.class, value = "Create BomLine") // label for swagger
    @PostMapping("/bomline")
    public ResponseEntity<?> postBomLine(@Valid @RequestBody BomLine newBomLine, @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {

        BomLine createdBomLine = mastersService.createBomLine(newBomLine, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdBomLine, HttpStatus.OK);
    }

    @ApiOperation(response = BomLine.class, value = "Update BomLine") // label for swagger
    @RequestMapping(value = "/bomline", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchBomLine(@RequestParam String warehouseId, @RequestParam Long bomNumber, @RequestParam String companyCode,
                                          @RequestParam String plantId, @RequestParam String languageId, @RequestParam String childItemCode,
                                          @RequestParam String loginUserID, @Valid @RequestBody BomLine updateBomLine)
            throws IllegalAccessException, InvocationTargetException {

        BomLine updateBomLine1 = mastersService.updateBomLine(warehouseId, companyCode, languageId, plantId, bomNumber, childItemCode, loginUserID, updateBomLine, getAuthToken());
        return new ResponseEntity<>(updateBomLine1, HttpStatus.OK);
    }

    @ApiOperation(response = BomLine.class, value = "Delete BomLine") // label for swagger
    @DeleteMapping("/bomline/{bomNumber}")
    public ResponseEntity<?> deleteBomLine(@PathVariable Long bomNumber, @RequestParam String warehouseId, @RequestParam String languageId,
                                           @RequestParam String companyCode, @RequestParam String plantId, @RequestParam String childItemCode,
                                           @RequestParam String loginUserID) {

        mastersService.deleteBomLine(bomNumber, warehouseId, languageId, companyCode, plantId, childItemCode, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = BomLine.class, value = "Search BomLine") // label for swagger
    @PostMapping("/bomline/findBomLine")
    public BomLine[] findBomLine(@RequestBody SearchBomLine searchBomLine) throws Exception {
        return mastersService.findBomLine(searchBomLine, getAuthToken());
    }

    /* -----------------------------MASTERS---BUSINESSPARTNER---------------------------------------------------------------*/

    @ApiOperation(response = BusinessPartner.class, value = "Get all BusinessPartners") // label for swagger
    @RequestMapping(value = "/businesspartner", method = RequestMethod.GET)
    public ResponseEntity<?> getBusinessPartners() {
        BusinessPartner[] businesspartner = mastersService.getBusinessPartners(getAuthToken());

        log.info("BusinessPartner : " + businesspartner);
        return new ResponseEntity<>(businesspartner, HttpStatus.OK);
    }

    @ApiOperation(response = BusinessPartner.class, value = "Get a BusinessPartner") // label for swagger
    @RequestMapping(value = "/businesspartner/{partnerCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getBusinessPartner(@PathVariable String partnerCode, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String warehouseId, @RequestParam String languageId, @RequestParam Long businessPartnerType) {

        BusinessPartnerV2 businesspartner = mastersService.getBusinessPartner(partnerCode, companyCodeId, plantId, warehouseId, languageId, businessPartnerType, getAuthToken());
        log.info("BusinessPartner : " + businesspartner);
        return new ResponseEntity<>(businesspartner, HttpStatus.OK);
    }

    @ApiOperation(response = BusinessPartner.class, value = "Search BusinessPartner") // label for swagger
    @PostMapping("/businesspartner/findBusinessPartner")
    public BusinessPartner[] findBusinessPartner(@RequestBody SearchBusinessPartner searchBusinessPartner) throws Exception {

        return mastersService.findBusinessPartner(searchBusinessPartner, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create BusinessPartner") // label for swagger
    @RequestMapping(value = "/businesspartner", method = RequestMethod.POST)
    public ResponseEntity<?> createBusinessPartner(@RequestBody BusinessPartnerV2 newBusinessPartner, @RequestParam String loginUserID) {

        BusinessPartnerV2 createdBusinessPartner = mastersService.addBusinessPartner(newBusinessPartner, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdBusinessPartner, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update BusinessPartner") // label for swagger
    @RequestMapping(value = "/businesspartner/{partnerCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBusinessPartner(@PathVariable String partnerCode,
                                                   @RequestBody BusinessPartner updatedBusinessPartner, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String warehouseId, @RequestParam String languageId, @RequestParam Long businessPartnerType,
                                                   @RequestParam String loginUserID) {

        BusinessPartner modifiedBusinessPartner = mastersService.updateBusinessPartner(partnerCode, companyCodeId, plantId,
                warehouseId, languageId, businessPartnerType, updatedBusinessPartner, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedBusinessPartner, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update BusinessPartner V2") // label for swagger
    @RequestMapping(value = "/businesspartner/v2/{partnerCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBusinessPartnerV2(@PathVariable String partnerCode,
                                                     @RequestBody BusinessPartnerV2 updatedBusinessPartner, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                     @RequestParam String warehouseId, @RequestParam String languageId, @RequestParam Long businessPartnerType,
                                                     @RequestParam String loginUserID) {

        BusinessPartnerV2 modifiedBusinessPartner = mastersService.updateBusinessPartnerV2(partnerCode, companyCodeId, plantId,
                warehouseId, languageId, businessPartnerType, updatedBusinessPartner, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedBusinessPartner, HttpStatus.OK);
    }

    @ApiOperation(response = BusinessPartner.class, value = "Delete BusinessPartner") // label for swagger
    @RequestMapping(value = "/businesspartner", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBusinessPartner(@RequestParam String companyCodeId, @RequestParam String plantId,
                                                   @RequestParam String warehouseId, @RequestParam String languageId, @RequestParam Long businessPartnerType,
                                                   @RequestParam String loginUserID, @RequestParam String partnerCode) {

        mastersService.deleteBusinessPartner(partnerCode, companyCodeId, plantId, warehouseId, languageId, businessPartnerType,
                loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* -----------------------------MASTERS---HANDLINGEQUIPMENT---------------------------------------------------------------*/

    @ApiOperation(response = HandlingEquipment.class, value = "Get all HandlingEquipment") // label for swagger
    @RequestMapping(value = "/handlingequipment", method = RequestMethod.GET)
    public ResponseEntity<?> getHandlingEquipments() {

        HandlingEquipment[] handlingequipment = mastersService.getHandlingEquipments(getAuthToken());
        log.info("HandlingEquipment : " + handlingequipment);
        return new ResponseEntity<>(handlingequipment, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingEquipment.class, value = "Get a HandlingEquipment") // label for swagger
    @RequestMapping(value = "/handlingequipment/{handlingEquipmentId}", method = RequestMethod.GET)
    public ResponseEntity<?> getHandlingEquipment(@PathVariable String handlingEquipmentId, @RequestParam String warehouseId, @RequestParam String companyCodeId,
                                                  @RequestParam String languageId, @RequestParam String plantId) {

        HandlingEquipment handlingequipment = mastersService.getHandlingEquipment(warehouseId, handlingEquipmentId, companyCodeId, languageId, plantId, getAuthToken());
        log.info("HandlingEquipment : " + handlingequipment);
        return new ResponseEntity<>(handlingequipment, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingEquipment.class, value = "Get HandlingEquipment by Barcode V2") // label for swagger
    @GetMapping("/handlingequipment/{heBarcode}/v2/barCode")
    public ResponseEntity<?> getHandlingEquipmentV2(@PathVariable String heBarcode, @RequestParam String companyCodeId,
                                                    @RequestParam String languageId, @RequestParam String plantId, @RequestParam String warehouseId) {

        HandlingEquipment handlingequipment = mastersService.getHandlingEquipmentV2(warehouseId, heBarcode, companyCodeId, languageId, plantId, getAuthToken());
        log.info("HandlingEquipment : " + handlingequipment);
        return new ResponseEntity<>(handlingequipment, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingEquipment.class, value = "Get HandlingEquipment by Barcode") // label for swagger
    @GetMapping("/handlingequipment/{heBarcode}/barCode")
    public ResponseEntity<?> getHandlingEquipment(@PathVariable String heBarcode, @RequestParam String warehouseId) {

        HandlingEquipment handlingequipment = mastersService.getHandlingEquipment(warehouseId, heBarcode, getAuthToken());
        log.info("HandlingEquipment : " + handlingequipment);
        return new ResponseEntity<>(handlingequipment, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingEquipment.class, value = "Search HandlingEquipment") // label for swagger
    @PostMapping("/handlingequipment/findHandlingEquipment")
    public HandlingEquipment[] findHandlingEquipment(@RequestBody SearchHandlingEquipment searchHandlingEquipment) throws Exception {

        return mastersService.findHandlingEquipment(searchHandlingEquipment, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create HandlingEquipment") // label for swagger
    @RequestMapping(value = "/handlingequipment", method = RequestMethod.POST)
    public ResponseEntity<?> createHandlingEquipment(@RequestBody HandlingEquipment newHandlingEquipment,
                                                     @RequestParam String loginUserID) {

        HandlingEquipment createdHandlingEquipment = mastersService.addHandlingEquipment(newHandlingEquipment, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdHandlingEquipment, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update HandlingEquipment") // label for swagger
    @RequestMapping(value = "/handlingequipment/{handlingEquipmentId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateHandlingEquipment(@PathVariable String handlingEquipmentId, @RequestParam String warehouseId,
                                                     @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId,
                                                     @RequestBody HandlingEquipment updatedHandlingEquipment, @RequestParam String loginUserID) {

        HandlingEquipment modifiedHandlingEquipment = mastersService.updateHandlingEquipment(warehouseId, handlingEquipmentId, companyCodeId,
                languageId, plantId, updatedHandlingEquipment, loginUserID, getAuthToken());

        return new ResponseEntity<>(modifiedHandlingEquipment, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingEquipment.class, value = "Delete HandlingEquipment") // label for swagger
    @RequestMapping(value = "/handlingequipment", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteHandlingEquipment(@RequestParam String handlingEquipmentId, @RequestParam String warehouseId,
                                                     @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId,
                                                     @RequestParam String loginUserID) {

        mastersService.deleteHandlingEquipment(warehouseId, handlingEquipmentId, companyCodeId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* -----------------------------MASTERS---HANDLINGUNIT---------------------------------------------------------------*/

    @ApiOperation(response = HandlingUnit.class, value = "Get all HandlingUnit") // label for swagger
    @RequestMapping(value = "/handlingunit", method = RequestMethod.GET)
    public ResponseEntity<?> getHandlingUnits() {
        HandlingUnit[] handlingunit = mastersService.getHandlingUnits(getAuthToken());

        log.info("HandlingUnit : " + handlingunit);
        return new ResponseEntity<>(handlingunit, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingUnit.class, value = "Get a HandlingUnit") // label for swagger
    @RequestMapping(value = "/handlingunit/{handlingUnit}", method = RequestMethod.GET)
    public ResponseEntity<?> getHandlingUnit(@PathVariable String handlingUnit, @RequestParam String companyCodeId, @RequestParam String plantId,
                                             @RequestParam String warehouseId, @RequestParam String languageId) {
        HandlingUnit handlingunit = mastersService.getHandlingUnit(handlingUnit, companyCodeId, plantId, warehouseId, languageId, getAuthToken());
        log.info("HandlingUnit : " + handlingunit);
        return new ResponseEntity<>(handlingunit, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingUnit.class, value = "Search HandlingUnit") // label for swagger
    @PostMapping("/handlingunit/findHandlingUnit")
    public HandlingUnit[] findHandlingUnit(@RequestBody SearchHandlingUnit searchHandlingUnit) throws Exception {
        return mastersService.findHandlingUnit(searchHandlingUnit, getAuthToken());
    }

    @ApiOperation(response = HandlingUnit.class, value = "Create a HandlingUnitId") // label for swagger
    @PostMapping("/handlingunit")
    public ResponseEntity<?> PostHandlingUnit(@Valid @RequestBody AddHandlingUnit newHandlingUnit,
                                              @RequestParam String loginUserID) throws IllegalAccessException, InvocationTargetException {
        HandlingUnit createHandlingUnit = mastersService.createHandlingUnit(newHandlingUnit, loginUserID, getAuthToken());
        return new ResponseEntity<>(createHandlingUnit, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingUnit.class, value = "Update HandlingUnit") // label for swagger
    @RequestMapping(value = "/handlingunit/{handlingUnit}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateHandlingUnit(@PathVariable String handlingUnit, @RequestParam String warehouseId,
                                                @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId,
                                                @RequestBody HandlingUnit updatedHandlingUnit, @RequestParam String loginUserID) {

        HandlingUnit modifiedHandlingUnit = mastersService.updateHandlingUnit(handlingUnit, warehouseId, companyCodeId, languageId, plantId,
                updatedHandlingUnit, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedHandlingUnit, HttpStatus.OK);
    }

    @ApiOperation(response = HandlingUnit.class, value = "Delete HandlingUnit") // label for swagger
    @RequestMapping(value = "/handlingunit", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteHandlingUnit(@RequestParam String warehouseId, @RequestParam String companyCodeId,
                                                @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                                @RequestParam String handlingUnit) {

        mastersService.deleteHandlingUnit(handlingUnit, warehouseId, companyCodeId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* -----------------------------MASTERS---IMALTERNATEUOM---------------------------------------------------------------*/

    //GET ALL
    @ApiOperation(response = ImAlternateUom.class, value = "Get all ImAlternateUom") // label for swagger
    @RequestMapping(value = "/imalternateuom", method = RequestMethod.GET)
    public ResponseEntity<?> getImAlternateUoms() {
        ImAlternateUom[] imalternateuom = mastersService.getImAlternateUoms(getAuthToken());

        log.info("ImAlternateUom : " + imalternateuom);
        return new ResponseEntity<>(imalternateuom, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = ImAlternateUom.class, value = "Get a ImAlternateUom") // label for swagger
    @RequestMapping(value = "/imalternateuom/{uomId}", method = RequestMethod.GET)
    public ResponseEntity<?> getImAlternateUom(@PathVariable String uomId, @RequestParam String companyCodeId, @RequestParam String plantId,
                                               @RequestParam String warehouseId, @RequestParam String itemCode, @RequestParam String alternateUom,
                                               @RequestParam String languageId) {

        ImAlternateUom[] imalternateuom = mastersService.getImAlternateUom(alternateUom, companyCodeId, plantId, warehouseId,
                itemCode, uomId, languageId, getAuthToken());

        log.info("ImAlternateUom : " + imalternateuom);
        return new ResponseEntity<>(imalternateuom, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Create ImAlternateUom") // label for swagger
    @RequestMapping(value = "/imalternateuom", method = RequestMethod.POST)
    public ResponseEntity<?> createImAlternateUom(@RequestBody List<AddImAlternateUom> newImAlternateUom, @RequestParam String loginUserID) {

        ImAlternateUom[] createdImAlternateUom = mastersService.addImAlternateUom(newImAlternateUom, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdImAlternateUom, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ImAlternateUom") // label for swagger
    @RequestMapping(value = "/imalternateuom/{uomId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImAlternateUom(@PathVariable String uomId, @RequestParam String companyCodeId,
                                                  @RequestParam String plantId, @RequestParam String warehouseId,
                                                  @RequestParam String itemCode, @RequestParam String alternateUom,
                                                  @RequestParam String languageId, @RequestParam String loginUserID,
                                                  @RequestBody List<UpdateImAlternateUom> updatedImAlternateUom) {

        ImAlternateUom[] modifiedImAlternateUom = mastersService.updateImAlternateUom(alternateUom, companyCodeId, plantId,
                warehouseId, itemCode, uomId, languageId, updatedImAlternateUom, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedImAlternateUom, HttpStatus.OK);
    }

    @ApiOperation(response = ImAlternateUom.class, value = "Delete ImAlternateUom") // label for swagger
    @RequestMapping(value = "/imalternateuom/{uomId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImAlternateUom(@RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String warehouseId,
                                                  @RequestParam String itemCode, @PathVariable String uomId, @RequestParam String languageId,
                                                  @RequestParam String loginUserID, @RequestParam String alternateUom) {

        mastersService.deleteImAlternateUom(alternateUom, companyCodeId, plantId, warehouseId, itemCode,
                uomId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = ImAlternateUom.class, value = "Search ImAlternateUom") // label for swagger
    @PostMapping("/imalternateuom/findImAlternateUom")
    public ImAlternateUom[] findImAlternateUom(@RequestBody SearchImAlternateUom searchImAlternateUom) throws Exception {
        return mastersService.findImAlternateUom(searchImAlternateUom, getAuthToken());
    }

    /* -----------------------------MASTERS---IMBASICDATA1---------------------------------------------------------------*/

    @ApiOperation(response = ImBasicData1.class, value = "Get all ImBasicData1") // label for swagger
    @RequestMapping(value = "/imbasicdata1", method = RequestMethod.GET)
    public ResponseEntity<?> getImBasicData1s() {

        ImBasicData1[] imbasicdata1 = mastersService.getImBasicData1s(getAuthToken());
        log.info("ImBasicData1 : " + imbasicdata1);
        return new ResponseEntity<>(imbasicdata1, HttpStatus.OK);
    }

    @ApiOperation(response = ImBasicData1V2.class, value = "Get a ImBasicData1") // label for swagger
    @RequestMapping(value = "/imbasicdata1/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getImBasicData1(@PathVariable String itemCode, @RequestParam String companyCodeId, @RequestParam String plantId,
                                             @RequestParam String manufacturerPartNo, @RequestParam String uomId, @RequestParam String languageId,
                                             @RequestParam String warehouseId) {

        ImBasicData1V2 imbasicdata1 = mastersService.getImBasicData1(itemCode, warehouseId, companyCodeId, plantId, uomId, languageId, manufacturerPartNo, getAuthToken());
        log.info("ImBasicData1 : " + imbasicdata1);
        return new ResponseEntity<>(imbasicdata1, HttpStatus.OK);
    }

    @ApiOperation(response = ImBasicData1V2.class, value = "Create ImBasicData1") // label for swagger
    @RequestMapping(value = "/imbasicdata1", method = RequestMethod.POST)
    public ResponseEntity<?> createImBasicData1(@RequestBody ImBasicData1V2 newImBasicData1, @RequestParam String loginUserID) {
        ImBasicData1V2 createdImBasicData1 = mastersService.addImBasicData1(newImBasicData1, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdImBasicData1, HttpStatus.OK);
    }

    @ApiOperation(response = ImBasicData1V2.class, value = "Update ImBasicData1") // label for swagger
    @RequestMapping(value = "/imbasicdata1/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImBasicData1(@PathVariable String itemCode, @RequestParam String warehouseId, @RequestParam String companyCodeId,
                                                @RequestParam String plantId, @RequestParam String languageId, @RequestParam String uomId,
                                                @RequestBody ImBasicData1V2 updatedImBasicData1, @RequestParam String manufacturerPartNo,
                                                @RequestParam String loginUserID) {

        ImBasicData1V2 modifiedImBasicData1 = mastersService.updateImBasicData1(itemCode, warehouseId, companyCodeId, plantId, manufacturerPartNo,
                uomId, languageId, updatedImBasicData1, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedImBasicData1, HttpStatus.OK);
    }

    @ApiOperation(response = ImBasicData1.class, value = "Delete ImBasicData1") // label for swagger
    @RequestMapping(value = "/imbasicdata1", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImBasicData1(@RequestParam String itemCode, @RequestParam String warehouseId,
                                                @RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId,
                                                @RequestParam String loginUserID, @RequestParam String uomId, @RequestParam String manufacturerPartNo) {

        mastersService.deleteImBasicData1(itemCode, warehouseId, companyCodeId,
                plantId, uomId, languageId, manufacturerPartNo, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = ImBasicData1.class, value = "Search ImBasicData1") // label for swagger
    @PostMapping("/imbasicdata1/findImBasicData1/pagination")
    public PaginatedResponse<ImBasicData1> findImBasicData1(@RequestBody SearchImBasicData1 searchImBasicData1,
                                                            @RequestParam(defaultValue = "0") Integer pageNo,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @RequestParam(defaultValue = "itemCode") String sortBy) throws Exception {

        return mastersService.findImBasicData11(searchImBasicData1, pageNo, pageSize, sortBy, getAuthToken());
    }

    @ApiOperation(response = ImBasicData1.class, value = "Search ImBasicData1") // label for swagger
    @PostMapping("/imbasicdata1/findImBasicData1")
    public ImBasicData1[] findImBasicData1(@RequestBody SearchImBasicData1 searchImBasicData1) throws Exception {
        return mastersService.findImBasicData1(searchImBasicData1, getAuthToken());
    }

    //Streaming
    @ApiOperation(response = ImBasicData1.class, value = "Search ImBasicData1 Stream") // label for swagger
    @PostMapping("/imbasicdata1/findImBasicData1New")
    public ImBasicData1[] findImBasicData1New(@RequestBody SearchImBasicData1 searchImBasicData1) throws Exception {
        return mastersService.findImBasicData1New(searchImBasicData1, getAuthToken());
    }

    @ApiOperation(response = ImBasicData1.class, value = "Like Search ImBasicData1") // label for swagger
    @GetMapping("/imbasicdata1/findItemCodeByLike")
    public ItemCodeDesc[] getImBasicData1LikeSearch(@RequestParam String likeSearchByItemCodeNDesc) throws Exception {
        return mastersService.findImBasicData1LikeSearch(likeSearchByItemCodeNDesc, getAuthToken());
    }

    //Input Parameters added
    @ApiOperation(response = ImBasicData1.class, value = "Like Search ImBasicData1 New") // label for swagger
    @GetMapping("/imbasicdata1/findItemCodeByLikeNew")
    public ItemCodeDesc[] getImBasicData1LikeSearchNew(@RequestParam String likeSearchByItemCodeNDesc, @RequestParam String companyCodeId,
                                                       @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId) throws Exception {

        return mastersService.findImBasicData1LikeSearchNew(likeSearchByItemCodeNDesc, companyCodeId, plantId,
                languageId, warehouseId, getAuthToken());
    }

    //Input Parameters added
    @ApiOperation(response = ImBasicData1V2.class, value = "Like Search ImBasicData1 New v2") // label for swagger
    @PostMapping("/imbasicdata1/v2/findItemCodeByLikeNew")
    public ItemCodeDesc[] getImBasicData1LikeSearchNew(@Valid @RequestBody LikeSearchInput likeSearchInput) throws Exception {
        return mastersService.findImBasicData1LikeSearchNewV2(likeSearchInput, getAuthToken());
    }


    /* -----------------------------MASTERS---IMBASICDATA2---------------------------------------------------------------*/

    @ApiOperation(response = ImBasicData2.class, value = "Get all ImBasicData2") // label for swagger
    @RequestMapping(value = "/imbasicdata2", method = RequestMethod.GET)
    public ResponseEntity<?> getImBasicData2s() {
        ImBasicData2[] imbasicdata2 = mastersService.getImBasicData2s(getAuthToken());
        log.info("ImBasicData2 : " + imbasicdata2);
        return new ResponseEntity<>(imbasicdata2, HttpStatus.OK);
    }

    @ApiOperation(response = ImBasicData2.class, value = "Get a ImBasicData2") // label for swagger
    @RequestMapping(value = "/imbasicdata2/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getImBasicData2(@PathVariable String itemCode, @RequestParam String companyCodeId, @RequestParam String plantId,
                                             @RequestParam String warehouseId, @RequestParam String languageId) {

        ImBasicData2 imbasicdata2 = mastersService.getImBasicData2(itemCode, companyCodeId, plantId, warehouseId, languageId, getAuthToken());
        log.info("ImBasicData2 : " + imbasicdata2);
        return new ResponseEntity<>(imbasicdata2, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Create ImBasicData2") // label for swagger
    @RequestMapping(value = "/imbasicdata2", method = RequestMethod.POST)
    public ResponseEntity<?> createImBasicData2(@RequestBody ImBasicData2 newImBasicData2, @RequestParam String loginUserID) {

        ImBasicData2 createdImBasicData2 = mastersService.addImBasicData2(newImBasicData2, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdImBasicData2, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ImBasicData2") // label for swagger
    @RequestMapping(value = "/imbasicdata2/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImBasicData2(@PathVariable String itemCode, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String warehouseId, @RequestParam String languageId, @RequestBody ImBasicData2 updatedImBasicData2,
                                                @RequestParam String loginUserID) {

        ImBasicData2 modifiedImBasicData2 = mastersService.updateImBasicData2(itemCode, companyCodeId, plantId, warehouseId,
                languageId, updatedImBasicData2, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedImBasicData2, HttpStatus.OK);
    }

    @ApiOperation(response = ImBasicData2.class, value = "Delete ImBasicData2") // label for swagger
    @RequestMapping(value = "/imbasicdata2", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImBasicData2(@RequestParam String companyCodeId, @RequestParam String plantId,
                                                @RequestParam String warehouseId, @RequestParam String languageId, @RequestParam String loginUserID,
                                                @RequestParam String itemCode) {

        mastersService.deleteImBasicData2(itemCode, companyCodeId, plantId, warehouseId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = ImBasicData2.class, value = "Search ImBasicData2") // label for swagger
    @PostMapping("/imbasicdata2/findImBasicData2")
    public ImBasicData2[] findImBasicData2(@RequestBody SearchImBasicData2 searchImBasicData2) throws Exception {
        return mastersService.findImBasicData2(searchImBasicData2, getAuthToken());
    }

    /* -----------------------------MASTERS---IMPACKING---------------------------------------------------------------*/

    @ApiOperation(response = ImPacking.class, value = "Get all ImPacking") // label for swagger
    @RequestMapping(value = "/impacking", method = RequestMethod.GET)
    public ResponseEntity<?> getImPackings() {
        ImPacking[] impacking = mastersService.getImPackings(getAuthToken());
        log.info("ImPacking : " + impacking);
        return new ResponseEntity<>(impacking, HttpStatus.OK);
    }

    @ApiOperation(response = ImPacking.class, value = "Get a ImPacking") // label for swagger
    @RequestMapping(value = "/impacking/{packingMaterialNo}", method = RequestMethod.GET)
    public ResponseEntity<?> getImPacking(@PathVariable String packingMaterialNo, @RequestParam String companyCodeId,
                                          @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId,
                                          @RequestParam String itemCode) {

        ImPacking impacking = mastersService.getImPacking(packingMaterialNo, companyCodeId, plantId, languageId, warehouseId, itemCode, getAuthToken());
        log.info("ImPacking : " + impacking);
        return new ResponseEntity<>(impacking, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Create ImPacking") // label for swagger
    @RequestMapping(value = "/impacking", method = RequestMethod.POST)
    public ResponseEntity<?> createImPacking(@RequestBody ImPacking newImPacking, @RequestParam String loginUserID) {
        ImPacking createdImPacking = mastersService.addImPacking(newImPacking, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdImPacking, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ImPacking") // label for swagger
    @RequestMapping(value = "/impacking/{packingMaterialNo}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImPacking(@PathVariable String packingMaterialNo, @RequestParam String companyCodeId, @RequestParam String plantId,
                                             @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String itemCode,
                                             @RequestBody ImPacking updatedImPacking, @RequestParam String loginUserID) {

        ImPacking modifiedImPacking = mastersService.updateImPacking(packingMaterialNo, companyCodeId, plantId, languageId, warehouseId, itemCode,
                updatedImPacking, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedImPacking, HttpStatus.OK);
    }

    @ApiOperation(response = ImPacking.class, value = "Delete ImPacking") // label for swagger
    @RequestMapping(value = "/impacking", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImPacking(@RequestParam String companyCodeId,
                                             @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String itemCode,
                                             @RequestParam String loginUserID, @RequestParam String packingMaterialNo) {

        mastersService.deleteImPacking(packingMaterialNo, companyCodeId, plantId, languageId, warehouseId, itemCode, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = ImPacking.class, value = "Search ImPacking") // label for swagger
    @PostMapping("/impacking/findImPacking")
    public ImPacking[] findImPacking(@RequestBody SearchImPacking searchImPacking) throws Exception {
        return mastersService.findImPacking(searchImPacking, getAuthToken());
    }

    /* -----------------------------MASTERS---IMPARTNER---------------------------------------------------------------*/

    @ApiOperation(response = ImPartner.class, value = "Get all ImPartner") // label for swagger
    @RequestMapping(value = "/impartner", method = RequestMethod.GET)
    public ResponseEntity<?> getImPartners() {
        ImPartner[] impartner = mastersService.getImPartners(getAuthToken());
        log.info("ImPartner : " + impartner);
        return new ResponseEntity<>(impartner, HttpStatus.OK);
    }

    @ApiOperation(response = ImPartner.class, value = "Get a ImPartner") // label for swagger
    @RequestMapping(value = "/impartner/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getImPartner(@PathVariable String itemCode, @RequestParam String companyCodeId, @RequestParam String manufacturerName,
                                          @RequestParam String plantId, @RequestParam String languageId, @RequestParam String partnerItemBarcode,
                                          @RequestParam String warehouseId) {

        ImPartner[] impartner = mastersService.getImPartner(companyCodeId, plantId, languageId, warehouseId, itemCode, manufacturerName, partnerItemBarcode, getAuthToken());

        log.info("ImPartner : " + impartner);
        return new ResponseEntity<>(impartner, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Create ImPartner") // label for swagger
    @RequestMapping(value = "/impartner", method = RequestMethod.POST)
    public ResponseEntity<?> createImPartner(@RequestBody List<ImPartner> newImPartner, @RequestParam String loginUserID) {
        ImPartner[] createdImPartner = mastersService.addImPartner(newImPartner, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdImPartner, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ImPartner") // label for swagger
    @RequestMapping(value = "/impartner/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImPartner(@PathVariable String itemCode, @RequestParam String manufacturerName, @RequestParam String companyCodeId,
                                             @RequestParam String plantId, @RequestParam String languageId,
                                             @RequestParam String warehouseId, @RequestBody List<ImPartner> updatedImPartner,
                                             @RequestParam String loginUserID) {

        ImPartner[] modifiedImPartner = mastersService.updateImPartner(companyCodeId, plantId, languageId,
                warehouseId, itemCode, manufacturerName, updatedImPartner, loginUserID, getAuthToken());

        return new ResponseEntity<>(modifiedImPartner, HttpStatus.OK);
    }

    @ApiOperation(response = ImPartner.class, value = "Delete ImPartner") // label for swagger
    @RequestMapping(value = "/impartner/{itemCode}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImPartner(@RequestParam String companyCodeId, @RequestParam String manufacturerName,
                                             @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId,
                                             @PathVariable String itemCode, @RequestParam String partnerItemBarcode, @RequestParam String loginUserID) {
        mastersService.deleteImPartner(companyCodeId, plantId, languageId, warehouseId, itemCode, manufacturerName, partnerItemBarcode, loginUserID, getAuthToken());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = ImPartner.class, value = "Get a ImPartner") // label for swagger
    @RequestMapping(value = "/impartner/v2/get", method = RequestMethod.POST)
    public ResponseEntity<?> getImPartnerV2(@RequestBody ImPartnerInput imPartnerInput) {
        ImPartner[] impartner = mastersService.getImPartnerV2(imPartnerInput, getAuthToken());
        log.info("ImPartner : " + impartner);
        return new ResponseEntity<>(impartner, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ImPartner") // label for swagger
    @RequestMapping(value = "/impartner/v2/update", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImPartnerV2(@RequestBody List<ImPartner> updatedImPartner, @RequestParam String loginUserID) {
        ImPartner[] modifiedImPartner = mastersService.updateImPartnerV2(updatedImPartner, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedImPartner, HttpStatus.OK);
    }

    @ApiOperation(response = ImPartner.class, value = "Delete ImPartner") // label for swagger
    @RequestMapping(value = "/impartner/v2/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteImPartnerV2(@RequestBody List<ImPartnerInput> imPartnerInput, @RequestParam String loginUserID) {
        mastersService.deleteImPartnerV2(imPartnerInput, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = ImPartner.class, value = "Search ImPartner") // label for swagger
    @PostMapping("/impartner/findImPartner")
    public ImPartner[] findImPartner(@RequestBody SearchImPartner searchImPartner) throws Exception {
        return mastersService.findImPartner(searchImPartner, getAuthToken());
    }

    /* -----------------------------MASTERS---IMSTRATEGIES---------------------------------------------------------------*/

    @ApiOperation(response = ImStrategies.class, value = "Get all ImStrategies") // label for swagger
    @RequestMapping(value = "/imstrategies", method = RequestMethod.GET)
    public ResponseEntity<?> getImStrategiess() {
        ImStrategies[] imstrategies = mastersService.getImStrategiess(getAuthToken());

        log.info("ImStrategies : " + imstrategies);
        return new ResponseEntity<>(imstrategies, HttpStatus.OK);
    }

    @ApiOperation(response = ImStrategies.class, value = "Get a ImStrategies") // label for swagger
    @RequestMapping(value = "/imstrategies/{strategyTypeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getImStrategies(@PathVariable Long strategyTypeId, @RequestParam String companyCodeId,
                                             @RequestParam String plantId, @RequestParam String warehouseId, @RequestParam String itemCode,
                                             @RequestParam Long sequenceIndicator, @RequestParam String languageId) {

        ImStrategies imstrategies = mastersService.getImStrategies(strategyTypeId, companyCodeId, plantId, warehouseId,
                itemCode, sequenceIndicator, languageId, getAuthToken());

        log.info("ImStrategies : " + imstrategies);
        return new ResponseEntity<>(imstrategies, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Create ImStrategies") // label for swagger
    @RequestMapping(value = "/imstrategies", method = RequestMethod.POST)
    public ResponseEntity<?> createImStrategies(@RequestBody ImStrategies newImStrategies, @RequestParam String loginUserID) {
        ImStrategies createdImStrategies = mastersService.addImStrategies(newImStrategies, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdImStrategies, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ImStrategies") // label for swagger
    @RequestMapping(value = "/imstrategies/{strategyTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImStrategies(@PathVariable Long strategyTypeId, @RequestBody ImStrategies updatedImStrategies,
                                                @RequestParam String loginUserID, @RequestParam String companyCodeId,
                                                @RequestParam String languageId, @RequestParam String plantId, @RequestParam String warehouseId,
                                                @RequestParam String itemCode, @RequestParam Long sequenceIndicator) {
        ImStrategies modifiedImStrategies =
                mastersService.updateImStrategies(strategyTypeId, companyCodeId, plantId, warehouseId, itemCode, sequenceIndicator,
                        languageId, updatedImStrategies, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedImStrategies, HttpStatus.OK);
    }

    @ApiOperation(response = ImStrategies.class, value = "Delete ImStrategies") // label for swagger
    @RequestMapping(value = "/imstrategies", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImStrategies(@RequestParam String companyCodeId,
                                                @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId,
                                                @RequestParam String itemCode, @RequestParam Long sequenceIndicator,
                                                @RequestParam String loginUserID, @RequestParam Long strategyTypeId) {
        mastersService.deleteImStrategies(strategyTypeId, companyCodeId, plantId, warehouseId, itemCode, sequenceIndicator,
                languageId, loginUserID, getAuthToken());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = ImStrategies.class, value = "Search ImStrategies") // label for swagger
    @PostMapping("/imstrategies/findImStrategies")
    public ImStrategies[] findImStrategies(@RequestBody SearchImStrategies searchImStrategies) throws Exception {
        return mastersService.findImStrategies(searchImStrategies, getAuthToken());
    }

    /* -----------------------------MASTERS---PACKINGMATERIAL---------------------------------------------------------------*/

    @ApiOperation(response = PackingMaterial.class, value = "Get all PackingMaterial") // label for swagger
    @RequestMapping(value = "/packingmaterial", method = RequestMethod.GET)
    public ResponseEntity<?> getPackingMaterials() {
        PackingMaterial[] packingmaterial = mastersService.getPackingMaterials(getAuthToken());
        log.info("PackingMaterial : " + packingmaterial);
        return new ResponseEntity<>(packingmaterial, HttpStatus.OK);
    }

    @ApiOperation(response = PackingMaterial.class, value = "Get a PackingMaterial") // label for swagger
    @RequestMapping(value = "/packingmaterial/{packingMaterialNo}", method = RequestMethod.GET)
    public ResponseEntity<?> getPackingMaterial(@PathVariable String packingMaterialNo, @RequestParam String companyCodeId,
                                                @RequestParam String languageId, @RequestParam String plantId,
                                                @RequestParam String warehouseId) {
        PackingMaterial packingmaterial = mastersService.getPackingMaterial(packingMaterialNo, companyCodeId,
                plantId, languageId, warehouseId, getAuthToken());

        log.info("PackingMaterial : " + packingmaterial);
        return new ResponseEntity<>(packingmaterial, HttpStatus.OK);
    }

    @ApiOperation(response = PackingMaterial.class, value = "Search PackingMaterial") // label for swagger
    @PostMapping("/packingmaterial/findPackingMaterial")
    public PackingMaterial[] findPackingMaterial(@RequestBody SearchPackingMaterial searchPackingMaterial) throws Exception {
        return mastersService.findPackingMaterial(searchPackingMaterial, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create PackingMaterial") // label for swagger
    @RequestMapping(value = "/packingmaterial", method = RequestMethod.POST)
    public ResponseEntity<?> createPackingMaterial(@RequestBody PackingMaterial newPackingMaterial,
                                                   @RequestParam String loginUserID) {

        PackingMaterial createdPackingMaterial =
                mastersService.addPackingMaterial(newPackingMaterial, loginUserID, getAuthToken());

        return new ResponseEntity<>(createdPackingMaterial, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update PackingMaterial") // label for swagger
    @RequestMapping(value = "/packingmaterial/{packingMaterialNo}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updatePackingMaterial(@PathVariable String packingMaterialNo, @RequestParam String companyCodeId,
                                                   @RequestParam String plantId, @RequestParam String languageId, @RequestParam String warehouseId,
                                                   @RequestBody PackingMaterial updatedPackingMaterial, @RequestParam String loginUserID) {

        PackingMaterial modifiedPackingMaterial = mastersService.updatePackingMaterial(packingMaterialNo, companyCodeId, languageId, plantId,
                warehouseId, updatedPackingMaterial, loginUserID, getAuthToken());

        return new ResponseEntity<>(modifiedPackingMaterial, HttpStatus.OK);
    }

    @ApiOperation(response = PackingMaterial.class, value = "Delete PackingMaterial") // label for swagger
    @RequestMapping(value = "/packingmaterial/{packingMaterialNo}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePackingMaterial(@RequestParam String companyCodeId,
                                                   @RequestParam String languageId, @RequestParam String plantId,
                                                   @RequestParam String warehouseId, @RequestParam String loginUserID,
                                                   @PathVariable String packingMaterialNo) {

        mastersService.deletePackingMaterial(packingMaterialNo, companyCodeId, plantId, languageId, warehouseId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* -----------------------------MASTERS---IMALTERNATEPART---------------------------------------------------------------*/

    @ApiOperation(response = ImAlternatePart.class, value = "Get all ImAlternatePart") // label for swagger
    @RequestMapping(value = "/imalternatepart", method = RequestMethod.GET)
    public ResponseEntity<?> getImAlternatePart() {

        ImAlternatePart[] imAlternateParts = mastersService.getImAlternateParts(getAuthToken());
        log.info("ImAlternateParts : " + imAlternateParts);
        return new ResponseEntity<>(imAlternateParts, HttpStatus.OK);
    }

    @ApiOperation(response = ImAlternatePart.class, value = "Get a ImAlternatePart") // label for swagger
    @RequestMapping(value = "/imalternatepart/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getImAlternatePart(@PathVariable String itemCode, @RequestParam String companyCodeId,
                                                @RequestParam String languageId, @RequestParam String plantId,
                                                @RequestParam String warehouseId) {
        ImAlternatePart[] imAlternatePart =
                mastersService.getImAlternatePart(companyCodeId, languageId, warehouseId, plantId, itemCode, getAuthToken());
        log.info("ImAlternate : " + imAlternatePart);
        return new ResponseEntity<>(imAlternatePart, HttpStatus.OK);
    }

    @ApiOperation(response = ImAlternatePart.class, value = "Create ImAlternatePart") // label for swagger
    @RequestMapping(value = "/imalternatepart", method = RequestMethod.POST)
    public ResponseEntity<?> createImAlternatePart(@RequestBody List<AddImAlternatePart> newImPartner, @RequestParam String loginUserID) {
        ImAlternatePart[] createdImAlternatePart = mastersService.addImalternatePart(newImPartner, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdImAlternatePart, HttpStatus.OK);
    }

    @ApiOperation(response = ImAlternatePart.class, value = "Update ImAlternatePart") // label for swagger
    @RequestMapping(value = "/imalternatepart/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImAlternatePart(@PathVariable String itemCode, @RequestParam String companyCodeId,
                                                   @RequestParam String languageId, @RequestParam String plantId,
                                                   @RequestParam String warehouseId, @Valid @RequestBody List<AddImAlternatePart> updateImAlternatePart,
                                                   @RequestParam String loginUserID) {
        ImAlternatePart[] modifiedImAlternatePart =
                mastersService.updateImAlternatePart(companyCodeId, languageId, warehouseId, plantId, itemCode,
                        updateImAlternatePart, loginUserID, getAuthToken());

        return new ResponseEntity<>(modifiedImAlternatePart, HttpStatus.OK);
    }

    @ApiOperation(response = ImAlternatePart.class, value = "Delete ImAlternatePart") // label for swagger
    @RequestMapping(value = "/imalternatepart/{itemCode}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImAlternatePart(@PathVariable String itemCode, @RequestParam String companyCodeId, @RequestParam String languageId,
                                                   @RequestParam String plantId, @RequestParam String warehouseId, @RequestParam String loginUserID) {

        mastersService.deleteImAlternateCode(companyCodeId, languageId, warehouseId, plantId, itemCode, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = ImAlternatePart.class, value = "Search ImAlternatePart") // label for swagger
    @PostMapping("/imalternatepart/findImAlternatePart")
    public ImAlternatePart[] findStorageBin(@RequestBody SearchImAlternateParts searchImAlternateParts) throws Exception {
        return mastersService.findImAlternatePart(searchImAlternateParts, getAuthToken());
    }

    /* -----------------------------MASTERS---STORAGEBIN---------------------------------------------------------------*/

    @ApiOperation(response = StorageBin.class, value = "Get all StorageBin") // label for swagger
    @RequestMapping(value = "/storagebin", method = RequestMethod.GET)
    public ResponseEntity<?> getStorageBins() {
        StorageBin[] storagebin = mastersService.getStorageBins(getAuthToken());
        log.info("StorageBin : " + storagebin);
        return new ResponseEntity<>(storagebin, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBin.class, value = "Get a StorageBin") // label for swagger
    @RequestMapping(value = "/storagebin/{storageBin}", method = RequestMethod.GET)
    public ResponseEntity<?> getStorageBin(@PathVariable String storageBin, @RequestParam String companyCodeId,
                                           @RequestParam String plantId, @RequestParam String warehouseId,
                                           @RequestParam String languageId) {

        StorageBin storagebin = mastersService.getStorageBin(storageBin, companyCodeId, plantId, warehouseId, languageId, getAuthToken());
        log.info("StorageBin : " + storagebin);
        return new ResponseEntity<>(storagebin, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBin.class, value = "Get a StorageBin") // label for swagger
    @GetMapping("/storagebin/{storageBin}/warehouseId")
    public ResponseEntity<?> getStorageBinbyWarehouseId(@PathVariable String storageBin,
                                                        @RequestParam String warehouseId) {
        StorageBin storagebin = mastersService.getStorageBin(warehouseId, storageBin, getAuthToken());
        log.info("StorageBin : " + storagebin);
        return new ResponseEntity<>(storagebin, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBin.class, value = "Search StorageBin") // label for swagger
    @PostMapping("/storagebin/findStorageBin")
    public StorageBin[] findStorageBin(@RequestBody SearchStorageBin searchStorageBin) throws Exception {
        return mastersService.findStorageBin(searchStorageBin, getAuthToken());
    }

    //Streaming
    @ApiOperation(response = StorageBin.class, value = "Search StorageBin Stream") // label for swagger
    @PostMapping("/storagebin/findStorageBinNew")
    public StorageBin[] findStorageBinNew(@RequestBody SearchStorageBin searchStorageBin) throws Exception {
        return mastersService.findStorageBinNew(searchStorageBin, getAuthToken());
    }

    @ApiOperation(response = StorageBin.class, value = "Like Search StorageBin") // label for swagger
    @GetMapping("/storagebin/findStorageBinByLike")
    public StorageBinDesc[] getStorageBinLikeSearch(@RequestParam String likeSearchByStorageBinNDesc) throws Exception {
        return mastersService.findStorageBinLikeSearch(likeSearchByStorageBinNDesc, getAuthToken());
    }

    //Input Parameters added
    @ApiOperation(response = StorageBin.class, value = "Like Search StorageBin New") // label for swagger
    @GetMapping("/storagebin/findStorageBinByLikeNew")
    public StorageBinDesc[] getStorageBinLikeSearchNew(@RequestParam String likeSearchByStorageBinNDesc, @RequestParam String companyCodeId, @RequestParam String plantId,
                                                       @RequestParam String languageId, @RequestParam String warehouseId) throws Exception {

        return mastersService.findStorageBinLikeSearchNew(likeSearchByStorageBinNDesc, companyCodeId, plantId,
                languageId, warehouseId, getAuthToken());
    }

    //Input Parameters added
    @ApiOperation(response = StorageBin.class, value = "Like Search StorageBin New V2") // label for swagger
    @PostMapping("/storagebin/v2/findStorageBinByLikeNew")
    public StorageBinDesc[] getStorageBinLikeSearchNewV2(@Valid @RequestBody LikeSearchInput likeSearchInput) throws Exception {

        return mastersService.findStorageBinLikeSearchNewV2(likeSearchInput, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create StorageBin") // label for swagger
    @RequestMapping(value = "/storagebin", method = RequestMethod.POST)
    public ResponseEntity<?> createStorageBin(@RequestBody StorageBin newStorageBin, @RequestParam String loginUserID) {

        StorageBin createdStorageBin = mastersService.addStorageBin(newStorageBin, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdStorageBin, HttpStatus.OK);
    }

    //V2
    @ApiOperation(response = StorageBinV2.class, value = "Create StorageBin V2") // label for swagger
    @RequestMapping(value = "/storagebin/v2", method = RequestMethod.POST)
    public ResponseEntity<?> createStorageBinV2(@RequestBody StorageBinV2 newStorageBin, @RequestParam String loginUserID) {

        StorageBinV2 createdStorageBin = mastersService.addStorageBinV2(newStorageBin, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdStorageBin, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update StorageBin") // label for swagger
    @RequestMapping(value = "/storagebin/{storageBin}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStorageBin(@PathVariable String storageBin,
                                              @RequestBody StorageBin updatedStorageBin, @RequestParam String loginUserID, @RequestParam String companyCodeId,
                                              @RequestParam String languageId, @RequestParam String plantId,
                                              @RequestParam String warehouseId) {

        StorageBin modifiedStorageBin = mastersService.updateStorageBin(storageBin, companyCodeId, plantId, languageId,
                warehouseId, updatedStorageBin, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedStorageBin, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBin.class, value = "Delete StorageBin") // label for swagger
    @DeleteMapping(value = "/storagebin/{storageBin}")
    public ResponseEntity<?> deleteStorageBin(@PathVariable String storageBin, @RequestParam String warehouseId,
                                              @RequestParam String companyCodeId, @RequestParam String plantId, @RequestParam String languageId,
                                              @RequestParam String loginUserID) {
        mastersService.deleteStorageBin(storageBin, warehouseId, companyCodeId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(response = StorageBinV2.class, value = "Get a StorageBinV2") // label for swagger
    @RequestMapping(value = "/storagebin/v2/{storageBin}", method = RequestMethod.GET)
    public ResponseEntity<?> getStorageBinV2(@PathVariable String storageBin, @RequestParam String companyCodeId,
                                             @RequestParam String plantId, @RequestParam String warehouseId,
                                             @RequestParam String languageId) {

        StorageBinV2 storageBinV2 = mastersService.getStorageBinV2(storageBin, companyCodeId, plantId, warehouseId, languageId, getAuthToken());
        log.info("StorageBinV2 : " + storageBinV2);
        return new ResponseEntity<>(storageBinV2, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBinV2.class, value = "Update StorageBinV2") // label for swagger
    @RequestMapping(value = "/storagebin/v2/{storageBin}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStorageBinV2(@PathVariable String storageBin,
                                                @RequestBody StorageBinV2 updatedStorageBinV2, @RequestParam String loginUserID, @RequestParam String companyCodeId,
                                                @RequestParam String languageId, @RequestParam String plantId,
                                                @RequestParam String warehouseId) {

        StorageBinV2 modifiedStorageBin = mastersService.updateStorageBinV2(storageBin, companyCodeId, plantId, languageId,
                warehouseId, updatedStorageBinV2, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedStorageBin, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBinV2.class, value = "Delete StorageBinV2") // label for swagger
    @DeleteMapping(value = "/storagebin/v2/{storageBin}")
    public ResponseEntity<?> deleteStoreBin(@PathVariable String storageBin, @RequestParam String warehouseId,
                                            @RequestParam String companyCodeId, @RequestParam String plantId,
                                            @RequestParam String languageId, @RequestParam String loginUserID) {
        mastersService.deleteStorageBinV2(storageBin, warehouseId, companyCodeId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* -----------------------------MASTERS---AUDITLOG---------------------------------------------------------------*/
    @ApiOperation(response = AuditLog.class, value = "Get all AuditLog") // label for swagger
    @RequestMapping(value = "/auditlog", method = RequestMethod.GET)
    public ResponseEntity<?> getAuditLogs() {
        AuditLog[] auditlog = mastersService.getAuditLogs(getAuthToken());

        log.info("AuditLog : " + auditlog);
        return new ResponseEntity<>(auditlog, HttpStatus.OK);
    }

    @ApiOperation(response = AuditLog.class, value = "Get a AuditLog") // label for swagger
    @RequestMapping(value = "/auditlog/{auditFileNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getAuditLog(@PathVariable String auditFileNumber) {
        AuditLog auditlog = mastersService.getAuditLog(auditFileNumber, getAuthToken());

        log.info("AuditLog : " + auditlog);
        return new ResponseEntity<>(auditlog, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Create AuditLog") // label for swagger
    @RequestMapping(value = "/auditlog", method = RequestMethod.POST)
    public ResponseEntity<?> createAuditLog(@RequestBody AuditLog newAuditLog, @RequestParam String loginUserID) {

        AuditLog createdAuditLog = mastersService.addAuditLog(newAuditLog, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdAuditLog, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update AuditLog") // label for swagger
    @RequestMapping(value = "/auditlog/{auditFileNumber}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateAuditLog(@PathVariable String auditFileNumber, @RequestBody AuditLog updatedAuditLog) {

        AuditLog modifiedAuditLog = mastersService.updateAuditLog(auditFileNumber, updatedAuditLog, getAuthToken());
        return new ResponseEntity<>(modifiedAuditLog, HttpStatus.OK);
    }

    @ApiOperation(response = AuditLog.class, value = "Delete AuditLog") // label for swagger
    @RequestMapping(value = "/auditlog", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAuditLog(@RequestParam String auditFileNumber) {
        mastersService.deleteAuditLog(auditFileNumber, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = AuditLog[].class, value = "Find AuditLog")//label for swagger
    @PostMapping("/auditlog/findAuditLog")
    public AuditLog[] findAuditLog(@RequestBody SearchAuditLog searchAuditLog) throws Exception {
        return mastersService.findAuditLog(searchAuditLog, getAuthToken());
    }

    //-------------------------Notifications-------------------------------------------------------------
    @ApiOperation(response = Notification[].class, value = "Notification List") // label for swagger
    @GetMapping("/notification-message")
    public Notification[] getAllNotifications(@RequestParam String userId) throws Exception {
        return mastersService.getAllNotifications(userId, getAuthToken());
    }

    @ApiOperation(response = Notification.class, value = "Mark Read Notification") // label for swagger
    @GetMapping("/notification-message/mark-read/{id}")
    public ResponseEntity<?> markNotificationRead(@RequestParam String loginUserID, @PathVariable Long id)
            throws IllegalAccessException, InvocationTargetException {
        Boolean result = mastersService.updateNotificationAsRead(loginUserID, id, getAuthToken());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(response = Notification.class, value = "Mark Read All Notification") // label for swagger
    @GetMapping("/notification-message/mark-read-all")
    public ResponseEntity<?> markNotificationReadAll(@RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        Boolean result = mastersService.updateNotificationAsReadAll(loginUserID, getAuthToken());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* --------------------------------ImBatchSerial-------------------------------------------------------------------------*/

    @ApiOperation(response = ImBatchSerial.class, value = "Get All ImBatchSerial") // label for swagger
    @RequestMapping(value = "/imbatchserial", method = RequestMethod.GET)
    public ResponseEntity<?> getImBatchSerial() {
        ImBatchSerial[] imBatchSerials = mastersService.getImBatchSerials(getAuthToken());
        return new ResponseEntity<>(imBatchSerials, HttpStatus.OK);
    }

    @ApiOperation(response = ImBatchSerial.class, value = "Get ImBatchSerial") // label for swagger
    @RequestMapping(value = "/imbatchserial/{storageMethod}", method = RequestMethod.GET)
    public ResponseEntity<?> getImBatchSerial(@RequestParam String warehouseId, @PathVariable String storageMethod, @RequestParam String itemCode,
                                              @RequestParam String companyCodeId, @RequestParam String languageId,
                                              @RequestParam String plantId) {

        ImBatchSerial imBatchSerial = mastersService.getImBatchSerial(storageMethod, companyCodeId, plantId, languageId,
                warehouseId, itemCode, getAuthToken());

        log.info("ImBatchSerial::: " + imBatchSerial);
        return new ResponseEntity<>(imBatchSerial, HttpStatus.OK);
    }

    @ApiOperation(response = ImBatchSerial.class, value = "Create new ImBatchSerial") // label for swagger
    @RequestMapping(value = "/imbatchserial", method = RequestMethod.POST)
    public ResponseEntity<?> createImBatchSerial(@RequestParam String loginUserID, @RequestBody AddImBatchSerial addImBatchSerial) {

        ImBatchSerial imBatchSerial = mastersService.addImBatchSerial(addImBatchSerial, loginUserID, getAuthToken());
        return new ResponseEntity<>(imBatchSerial, HttpStatus.OK);
    }

    @ApiOperation(response = ImBatchSerial.class, value = "Update ImBatchSerial") // label for swagger
    @RequestMapping(value = "/imbatchserial/{storageMethod}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBatchSerial(@PathVariable String storageMethod, @RequestParam String companyCodeId, @RequestParam String itemCode,
                                               @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                               @RequestParam String warehouseId, @RequestBody UpdateImBatchSerial updateBatchSerial) {

        ImBatchSerial imBatchSerial = mastersService.updateImBatchSerial(storageMethod, companyCodeId,
                plantId, languageId, warehouseId, itemCode, updateBatchSerial, loginUserID, getAuthToken());

        return new ResponseEntity<>(imBatchSerial, HttpStatus.OK);
    }

    @ApiOperation(response = ImBatchSerial.class, value = "Delete ImBatchSerial") // label for swagger
    @RequestMapping(value = "/imbatchserial/{storageMethod}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImBatchSerial(@PathVariable String storageMethod, @RequestParam String itemCode, @RequestParam String companyCodeId,
                                                 @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                                 @RequestParam String warehouseId) {

        mastersService.deleteImBatchSerial(storageMethod, companyCodeId, plantId, languageId, warehouseId, itemCode, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = ImBatchSerial[].class, value = "Find ImBatchSerial")//label for swagger
    @PostMapping("/imbatchserial/findImBatchSerial")
    public ImBatchSerial[] findImBatchSerial(@RequestBody SearchImBatchSerial searchImBatchSerial) throws Exception {
        return mastersService.findImBatchSerial(searchImBatchSerial, getAuthToken());
    }


    /* --------------------------------ImCapacity-------------------------------------------------------------------------*/

    @ApiOperation(response = ImCapacity.class, value = "Get All ImCapacity") // label for swagger
    @RequestMapping(value = "/imcapacity", method = RequestMethod.GET)
    public ResponseEntity<?> getImCapacity() {
        ImCapacity[] imCapacities = mastersService.getAllImCapacity(getAuthToken());
        return new ResponseEntity<>(imCapacities, HttpStatus.OK);
    }

    @ApiOperation(response = ImCapacity.class, value = "Get ImCapacity") // label for swagger
    @RequestMapping(value = "/imcapacity/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getImCapacity(@RequestParam String warehouseId, @PathVariable String itemCode, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId) {

        ImCapacity imCapacity = mastersService.getImCapacity(companyCodeId, plantId, languageId, warehouseId, itemCode, getAuthToken());
        log.info("ImCapacity::: " + imCapacity);

        return new ResponseEntity<>(imCapacity, HttpStatus.OK);
    }

    @ApiOperation(response = ImCapacity.class, value = "Create new ImCapacity") // label for swagger
    @RequestMapping(value = "/imcapacity", method = RequestMethod.POST)
    public ResponseEntity<?> createImCapacity(@RequestParam String loginUserID, @RequestBody AddImCapacity addImCapacity) {

        ImCapacity imCapacity = mastersService.addImCapacity(addImCapacity, loginUserID, getAuthToken());
        return new ResponseEntity<>(imCapacity, HttpStatus.OK);
    }

    @ApiOperation(response = ImCapacity.class, value = "Update ImCapacity") // label for swagger
    @RequestMapping(value = "/imcapacity/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImCapacity(@PathVariable String itemCode, @RequestParam String companyCodeId,
                                              @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                              @RequestParam String warehouseId, @RequestBody UpdateImCapacity updateImCapacity) {

        ImCapacity imCapacity = mastersService.updateImCapacity(companyCodeId, plantId, languageId, warehouseId,
                itemCode, updateImCapacity, loginUserID, getAuthToken());

        return new ResponseEntity<>(imCapacity, HttpStatus.OK);
    }

    @ApiOperation(response = ImCapacity.class, value = "Delete ImCapacity") // label for swagger
    @RequestMapping(value = "/imcapacity/{itemCode}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImCapacity(@PathVariable String itemCode, @RequestParam String companyCodeId,
                                              @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                              @RequestParam String warehouseId) {

        mastersService.deleteImCapacity(companyCodeId, plantId, languageId, warehouseId, itemCode, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = ImCapacity[].class, value = "Find ImCapacity")//label for swagger
    @PostMapping("/imcapacity/findImCapacity")
    public ImCapacity[] findImCapacity(@RequestBody SearchImCapacity searchImCapacity) throws Exception {

        return mastersService.findImCapacity(searchImCapacity, getAuthToken());
    }


    /* --------------------------------ImVariant-------------------------------------------------------------------------*/

    @ApiOperation(response = ImVariant.class, value = "Get All ImVariant") // label for swagger
    @RequestMapping(value = "/imvariant", method = RequestMethod.GET)
    public ResponseEntity<?> getAllImVariant() {

        ImVariant[] imVariants = mastersService.getAllImVariant(getAuthToken());
        return new ResponseEntity<>(imVariants, HttpStatus.OK);
    }

    @ApiOperation(response = ImVariant.class, value = "Get ImVariant") // label for swagger
    @RequestMapping(value = "/imvariant/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getImVariant(@RequestParam String warehouseId, @PathVariable String itemCode,
                                          @RequestParam String companyCodeId, @RequestParam String languageId,
                                          @RequestParam String plantId) {

        ImVariant[] imVariant = mastersService.getImVariant(companyCodeId, plantId, languageId, warehouseId, itemCode, getAuthToken());
        log.info("ImVariant::: " + imVariant);
        return new ResponseEntity<>(imVariant, HttpStatus.OK);
    }

    @ApiOperation(response = ImVariant.class, value = "Create new ImVariant") // label for swagger
    @RequestMapping(value = "/imvariant", method = RequestMethod.POST)
    public ResponseEntity<?> createImVariant(@RequestParam String loginUserID,
                                             @RequestBody List<AddImVariant> addImVariant) {

        ImVariant[] imVariant = mastersService.addImVariant(addImVariant, loginUserID, getAuthToken());
        return new ResponseEntity<>(imVariant, HttpStatus.OK);
    }

    @ApiOperation(response = ImVariant.class, value = "Update ImVariant") // label for swagger
    @RequestMapping(value = "/imvariant/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateVariant(@PathVariable String itemCode,
                                           @RequestParam String companyCodeId, @RequestParam String languageId,
                                           @RequestParam String plantId, @RequestParam String loginUserID,
                                           @RequestParam String warehouseId, @RequestBody List<UpdateImVariant> updateImVariant) {

        ImVariant[] imVariant = mastersService.updateImVariant(companyCodeId, plantId, languageId, warehouseId, itemCode,
                updateImVariant, loginUserID, getAuthToken());
        return new ResponseEntity<>(imVariant, HttpStatus.OK);
    }

    @ApiOperation(response = ImVariant.class, value = "Delete ImVariant") // label for swagger
    @RequestMapping(value = "/imvariant/{itemCode}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImVariant(@PathVariable String itemCode, @RequestParam String companyCodeId,
                                             @RequestParam String languageId, @RequestParam String plantId,
                                             @RequestParam String loginUserID, @RequestParam String warehouseId) {

        mastersService.deleteImVariant(companyCodeId, plantId, languageId, warehouseId, itemCode, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = ImVariant[].class, value = "Find ImVariant")//label for swagger
    @PostMapping("/imvariant/findImVariant")
    public ImVariant[] findImVariant(@RequestBody SearchImVariant searchImVariant) throws Exception {

        return mastersService.findImVariant(searchImVariant, getAuthToken());
    }


    /* --------------------------------ImPalletization-------------------------------------------------------------------------*/

    @ApiOperation(response = ImPalletization.class, value = "Get All ImPalletization") // label for swagger
    @RequestMapping(value = "/impalletization", method = RequestMethod.GET)
    public ResponseEntity<?> getAllImPalletization() {

        ImPalletization[] imPalletization = mastersService.getAllPalletization(getAuthToken());
        return new ResponseEntity<>(imPalletization, HttpStatus.OK);
    }

    @ApiOperation(response = ImPalletization.class, value = "Get ImPalletization") // label for swagger
    @RequestMapping(value = "/impalletization/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getImPalletization(@RequestParam String warehouseId, @PathVariable String itemCode,
                                                @RequestParam String companyCodeId, @RequestParam String languageId,
                                                @RequestParam String plantId) {
        ImPalletization[] imPalletization = mastersService.getImPalletization(companyCodeId, plantId, languageId, warehouseId, itemCode, getAuthToken());
        log.info("ImPalletization::: " + imPalletization);
        return new ResponseEntity<>(imPalletization, HttpStatus.OK);
    }

    @ApiOperation(response = ImPalletization.class, value = "Create new ImPalletization") // label for swagger
    @RequestMapping(value = "/impalletization", method = RequestMethod.POST)
    public ResponseEntity<?> createImPalletization(@RequestParam String loginUserID, @RequestBody List<AddImPalletization> addImPalletization) {

        ImPalletization[] imPalletization = mastersService.addImPalletization(addImPalletization, loginUserID, getAuthToken());
        return new ResponseEntity<>(imPalletization, HttpStatus.OK);
    }

    @ApiOperation(response = ImPalletization.class, value = "Update ImPalletization") // label for swagger
    @RequestMapping(value = "/impalletization/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateImPalletization(@PathVariable String itemCode, @RequestParam String companyCodeId,
                                                   @RequestParam String languageId, @RequestParam String plantId,
                                                   @RequestParam String loginUserID, @RequestParam String warehouseId,
                                                   @RequestBody List<AddImPalletization> updateImPalletization) {

        ImPalletization[] imPalletization = mastersService.updatePalletization(companyCodeId, plantId, languageId, warehouseId,
                itemCode, updateImPalletization, loginUserID, getAuthToken());
        return new ResponseEntity<>(imPalletization, HttpStatus.OK);
    }

    @ApiOperation(response = ImPalletization.class, value = "Delete ImPalletization") // label for swagger
    @RequestMapping(value = "/impalletization/{itemCode}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImPalletization(@PathVariable String itemCode, @RequestParam String companyCodeId, @RequestParam String languageId,
                                                   @RequestParam String plantId, @RequestParam String loginUserID, @RequestParam String warehouseId) {

        mastersService.deleteImPalletization(companyCodeId, plantId, languageId, warehouseId, itemCode,
                loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = ImPalletization[].class, value = "Find ImPalletization")//label for swagger
    @PostMapping("/impalletization/findImPalletization")
    public ImPalletization[] findImPalletization(@RequestBody SearchImPalletization searchImPalletization) throws Exception {
        return mastersService.findImPalletization(searchImPalletization, getAuthToken());
    }


    /* --------------------------------CycleCountScheduler-------------------------------------------------------------------------*/

    @ApiOperation(response = CycleCountScheduler.class, value = "Get All CycleCountScheduler") // label for swagger
    @RequestMapping(value = "/cyclecountscheduler", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCycleCountScheduler() {
        CycleCountScheduler[] cycleCountSchedulers = mastersService.getAllCycleCountScheduler(getAuthToken());
        return new ResponseEntity<>(cycleCountSchedulers, HttpStatus.OK);
    }

    @ApiOperation(response = CycleCountScheduler.class, value = "Get CycleCountScheduler") // label for swagger
    @RequestMapping(value = "/cyclecountscheduler/{cycleCountTypeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCycleCountScheduler(@RequestParam String warehouseId, @PathVariable Long cycleCountTypeId,
                                                    @RequestParam String schedulerNumber, @RequestParam Long levelId, @RequestParam String companyCodeId,
                                                    @RequestParam String languageId, @RequestParam String plantId) {

        CycleCountScheduler dbCycleCountScheduler = mastersService.getCycleCountScheduler(companyCodeId, plantId, languageId, warehouseId,
                cycleCountTypeId, levelId, schedulerNumber, getAuthToken());

        log.info("CycleCountScheduler::: " + dbCycleCountScheduler);
        return new ResponseEntity<>(dbCycleCountScheduler, HttpStatus.OK);
    }

    @ApiOperation(response = CycleCountScheduler.class, value = "Create new CycleCountScheduler") // label for swagger
    @RequestMapping(value = "/cyclecountscheduler", method = RequestMethod.POST)
    public ResponseEntity<?> createCycleCountScheduler(@RequestParam String loginUserID,
                                                       @RequestBody AddCycleCountScheduler addCycleCountScheduler) {

        CycleCountScheduler dbCycleCountScheduler = mastersService.addCycleCountScheduler(addCycleCountScheduler, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbCycleCountScheduler, HttpStatus.OK);
    }

    @ApiOperation(response = CycleCountScheduler.class, value = "Update CycleCountScheduler") // label for swagger
    @RequestMapping(value = "/cyclecountscheduler/{cycleCountTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateCycleCountScheduler(@PathVariable Long cycleCountTypeId, @RequestParam String companyCodeId,
                                                       @RequestParam String schedulerNumber, @RequestParam Long levelId, @RequestParam String languageId,
                                                       @RequestParam String plantId, @RequestParam String loginUserID, @RequestParam String warehouseId,
                                                       @RequestBody UpdateCycleCountScheduler updateCycleCountScheduler) {

        CycleCountScheduler dbCycleCountScheduler = mastersService.updateCycleCountScheduler(companyCodeId, plantId, languageId, warehouseId,
                cycleCountTypeId, levelId, schedulerNumber, updateCycleCountScheduler, loginUserID, getAuthToken());

        return new ResponseEntity<>(dbCycleCountScheduler, HttpStatus.OK);
    }

    @ApiOperation(response = CycleCountScheduler.class, value = "Delete CycleCountScheduler") // label for swagger
    @RequestMapping(value = "/cyclecountscheduler/{cycleCountTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCycleCountScheduler(@PathVariable Long cycleCountTypeId, @RequestParam String companyCodeId, @RequestParam Long levelId,
                                                       @RequestParam String schedulerNumber, @RequestParam String languageId, @RequestParam String plantId,
                                                       @RequestParam String loginUserID, @RequestParam String warehouseId) {

        mastersService.deleteCycleCountScheduler(companyCodeId, plantId, languageId, warehouseId, levelId,
                schedulerNumber, cycleCountTypeId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND

    @ApiOperation(response = CycleCountScheduler[].class, value = "Find CycleCountScheduler")//label for swagger
    @PostMapping("/cyclecountscheduler/findCycleCountScheduler")
    public CycleCountScheduler[] findCycleCountScheduler(@RequestBody SearchCycleCountScheduler searchCycleCountScheduler) throws Exception {
        return mastersService.findCycleCountScheduler(searchCycleCountScheduler, getAuthToken());
    }

    /* --------------------------------Dock-------------------------------------------------------------------------*/

    @ApiOperation(response = Dock.class, value = "Get All Dock") // label for swagger
    @RequestMapping(value = "/dock", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDock() {
        Dock[] docks = mastersService.getAllDock(getAuthToken());
        return new ResponseEntity<>(docks, HttpStatus.OK);
    }

    @ApiOperation(response = Dock.class, value = "Get Dock") // label for swagger
    @RequestMapping(value = "/dock/{dockId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDock(@RequestParam String warehouseId, @PathVariable String dockId, @RequestParam String dockType,
                                     @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId) {

        Dock dbDock = mastersService.getDock(companyCodeId, plantId, languageId, warehouseId, dockId, dockType, getAuthToken());
        log.info("Dock::: " + dbDock);
        return new ResponseEntity<>(dbDock, HttpStatus.OK);
    }

    @ApiOperation(response = Dock.class, value = "Create new Dock") // label for swagger
    @RequestMapping(value = "/dock", method = RequestMethod.POST)
    public ResponseEntity<?> createDock(@RequestParam String loginUserID, @RequestBody AddDock addDock) {

        Dock dbDock = mastersService.addDock(addDock, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbDock, HttpStatus.OK);
    }

    @ApiOperation(response = Dock.class, value = "Update Dock") // label for swagger
    @RequestMapping(value = "/dock/{dockId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateDock(@PathVariable String dockId, @RequestParam String companyCodeId,
                                        @RequestParam String dockType, @RequestParam String languageId, @RequestParam String plantId,
                                        @RequestParam String loginUserID, @RequestParam String warehouseId, @RequestBody UpdateDock updateDock) {

        Dock dbDock = mastersService.updateDock(companyCodeId, plantId, languageId, warehouseId, dockId, dockType, updateDock, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbDock, HttpStatus.OK);
    }

    @ApiOperation(response = Dock.class, value = "Delete Dock") // label for swagger
    @RequestMapping(value = "/dock/{dockId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDock(@PathVariable String dockId, @RequestParam String companyCodeId, @RequestParam String dockType,
                                        @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                        @RequestParam String warehouseId) {

        mastersService.deleteDock(companyCodeId, plantId, languageId, warehouseId, dockId, dockType, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = Dock[].class, value = "Find Dock")//label for swagger
    @PostMapping("/dock/findDock")
    public Dock[] findDock(@RequestBody SearchDock searchDock) throws Exception {
        return mastersService.findDock(searchDock, getAuthToken());
    }

    /* --------------------------------NumberRangeItem-------------------------------------------------------------------------*/

    @ApiOperation(response = NumberRangeItem.class, value = "Get All NumberRangeItem") // label for swagger
    @RequestMapping(value = "/numberrangeitem", method = RequestMethod.GET)
    public ResponseEntity<?> getNumberRangeItem() {

        NumberRangeItem[] numberRangeItems = mastersService.getAllNumberRangeItem(getAuthToken());
        return new ResponseEntity<>(numberRangeItems, HttpStatus.OK);
    }

    @ApiOperation(response = NumberRangeItem.class, value = "Get NumberRangeItem") // label for swagger
    @RequestMapping(value = "/numberrangeitem/{itemTypeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getNumberRangeItem(@RequestParam String warehouseId, @PathVariable Long itemTypeId, @RequestParam Long sequenceNo,
                                                @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId) {

        NumberRangeItem dbNumberRangeItem = mastersService.getNumberRange(companyCodeId, plantId, languageId, warehouseId,
                itemTypeId, sequenceNo, getAuthToken());
        log.info("NumberRangeItem::: " + dbNumberRangeItem);
        return new ResponseEntity<>(dbNumberRangeItem, HttpStatus.OK);
    }

    @ApiOperation(response = NumberRangeItem.class, value = "Create new NumberRangeItem") // label for swagger
    @RequestMapping(value = "/numberrangeitem", method = RequestMethod.POST)
    public ResponseEntity<?> createNumberRange(@RequestParam String loginUserID, @RequestBody AddNumberRangeItem addNumberRangeItem) {

        NumberRangeItem dbNumberRange = mastersService.addNumberRangeItem(addNumberRangeItem, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbNumberRange, HttpStatus.OK);
    }

    @ApiOperation(response = NumberRangeItem.class, value = "Update NumberRangeItem") // label for swagger
    @RequestMapping(value = "/numberrangeitem/{itemTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateNumberRange(@PathVariable Long itemTypeId, @RequestParam String companyCodeId,
                                               @RequestParam Long sequenceNo, @RequestParam String languageId, @RequestParam String plantId,
                                               @RequestParam String loginUserID, @RequestParam String warehouseId, @RequestBody UpdateNumberRangeItem updateNumberRangeItem) {

        NumberRangeItem dbNumberRangeItem = mastersService.updateNumberRangeItem(companyCodeId, plantId, languageId,
                warehouseId, itemTypeId, sequenceNo, updateNumberRangeItem, loginUserID, getAuthToken());

        return new ResponseEntity<>(dbNumberRangeItem, HttpStatus.OK);
    }

    @ApiOperation(response = NumberRangeItem.class, value = "Delete NumberRangeItem") // label for swagger
    @RequestMapping(value = "/numberrangeitem/{itemTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteNUmberRangeItem(@PathVariable Long itemTypeId, @RequestParam String companyCodeId, @RequestParam Long sequenceNo,
                                                   @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                                   @RequestParam String warehouseId) {

        mastersService.deleteNumberRangeItem(companyCodeId, plantId, languageId, warehouseId,
                itemTypeId, sequenceNo, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = NumberRangeItem[].class, value = "Find NumberRangeItem")//label for swagger
    @PostMapping("/numberrangeitem/find")
    public NumberRangeItem[] findNumberRangeItem(@RequestBody SearchNumberRangeItem searchNumberRangeItem) throws Exception {
        return mastersService.findNumberRangeItem(searchNumberRangeItem, getAuthToken());
    }

    /* --------------------------------NumberRangeStorageBin-------------------------------------------------------------------------*/

    // GET ALL
    @ApiOperation(response = NumberRangeStorageBin.class, value = "Get All NumberRangeStorageBin") // label for swagger
    @RequestMapping(value = "/numberrangestoragebin", method = RequestMethod.GET)
    public ResponseEntity<?> getNumberRangeStorageBin() {
        NumberRangeStorageBin[] numberRangeStorageBins = mastersService.getAllNumberRangeStorageBin(getAuthToken());
        return new ResponseEntity<>(numberRangeStorageBins, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = NumberRangeStorageBin.class, value = "Get NumberRangeStorageBin") // label for swagger
    @RequestMapping(value = "/numberrangestoragebin/{storageSectionId}", method = RequestMethod.GET)
    public ResponseEntity<?> getNumberRangeStorageBin(@RequestParam String warehouseId, @PathVariable String storageSectionId,
                                                      @RequestParam Long floorId, @RequestParam String aisleNumber, @RequestParam String rowId,
                                                      @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId) {

        NumberRangeStorageBin dbNumberRangeStorageBin = mastersService.getNumberRangeStorageBin(companyCodeId, plantId, languageId,
                warehouseId, floorId, storageSectionId, rowId, aisleNumber, getAuthToken());
        log.info("NumberRangeStorageBin::: " + dbNumberRangeStorageBin);
        return new ResponseEntity<>(dbNumberRangeStorageBin, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = NumberRangeStorageBin.class, value = "Create new NumberRangeStorageBin")
    // label for swagger
    @RequestMapping(value = "/numberrangestoragebin", method = RequestMethod.POST)
    public ResponseEntity<?> createNumberRangeStorageBin(@RequestParam String loginUserID, @RequestBody AddNumberRangeStorageBin addNumberRangeStorageBin) {

        NumberRangeStorageBin dbNumberRangeStorageBin = mastersService.addNumberRangeStorageBin(addNumberRangeStorageBin, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbNumberRangeStorageBin, HttpStatus.OK);
    }

    //UPDATE
    @ApiOperation(response = NumberRangeStorageBin.class, value = "Update NumberRangeStorageBin") // label for swagger
    @RequestMapping(value = "/numberrangestoragebin/{storageSectionId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateNumberRangeStorageBin(@PathVariable String storageSectionId,
                                                         @RequestParam String companyCodeId, @RequestParam Long floorId,
                                                         @RequestParam String rowId, @RequestParam String aisleNumber,
                                                         @RequestParam String languageId, @RequestParam String plantId,
                                                         @RequestParam String loginUserID, @RequestParam String warehouseId,
                                                         @RequestBody UpdateNumberRangeStorageBin updateNumberRangeStorageBin) {

        NumberRangeStorageBin dbNumberRangeStorageBin = mastersService.updateNumberRangeStorageBin(companyCodeId, plantId,
                languageId, warehouseId, storageSectionId, floorId, rowId, aisleNumber, updateNumberRangeStorageBin, loginUserID, getAuthToken());

        return new ResponseEntity<>(dbNumberRangeStorageBin, HttpStatus.OK);
    }

    //DELETE
    @ApiOperation(response = NumberRangeStorageBin.class, value = "Delete NumberRangeStorageBin") // label for swagger
    @RequestMapping(value = "/numberrangestoragebin/{storageSectionId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteNUmberRangeItem(@PathVariable String storageSectionId, @RequestParam String companyCodeId, @RequestParam Long floorId,
                                                   @RequestParam String rowId, @RequestParam String aisleNumber, @RequestParam String languageId,
                                                   @RequestParam String plantId, @RequestParam String loginUserID,
                                                   @RequestParam String warehouseId) {

        mastersService.deleteNumberRangeStorageBin(companyCodeId, plantId, languageId, warehouseId, floorId, storageSectionId,
                rowId, aisleNumber, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = NumberRangeStorageBin[].class, value = "Find NumberRangeStorageBin")//label for swagger
    @PostMapping("/numberrangestoragebin/findNumberRangeStorageBin")
    public NumberRangeStorageBin[] findNumberRangeStorageBin(@RequestBody SearchNumberRangeStorageBin searchNumberRangeStorageBin) throws Exception {
        return mastersService.findNumberRangeStorageBin(searchNumberRangeStorageBin, getAuthToken());
    }

    /* --------------------------------WorkCenter-------------------------------------------------------------------------*/

    // WORKCENTER
    @ApiOperation(response = WorkCenter.class, value = "Get All WorkCenter") // label for swagger
    @RequestMapping(value = "/workcenter", method = RequestMethod.GET)
    public ResponseEntity<?> getWorkCenter() {
        WorkCenter[] workCentersList = mastersService.getAllWorkCenter(getAuthToken());
        return new ResponseEntity<>(workCentersList, HttpStatus.OK);
    }

//GET

    @ApiOperation(response = WorkCenter.class, value = "Get WorkCenter") // label for swagger
    @RequestMapping(value = "/workcenter/{workCenterId}", method = RequestMethod.GET)
    public ResponseEntity<?> getWorkCenter(@RequestParam String warehouseId, @PathVariable Long workCenterId,
                                           @RequestParam String workCenterType, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId) {

        WorkCenter dbWorkCenter = mastersService.getWorkCenter(companyCodeId, plantId, languageId, warehouseId, workCenterId, workCenterType, getAuthToken());

        log.info("WorkCenter::: " + dbWorkCenter);
        return new ResponseEntity<>(dbWorkCenter, HttpStatus.OK);
    }

//CREATE

    @ApiOperation(response = WorkCenter.class, value = "Create new WorkCenter") // label for swagger
    @RequestMapping(value = "/workcenter", method = RequestMethod.POST)
    public ResponseEntity<?> createWorkCenter(@RequestParam String loginUserID, @RequestBody AddWorkCenter addWorkCenter) {

        WorkCenter dbWorkCenter = mastersService.addWorkCenter(addWorkCenter, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbWorkCenter, HttpStatus.OK);
    }

//UPDATE

    @ApiOperation(response = WorkCenter.class, value = "Update WorkCenter") // label for swagger
    @RequestMapping(value = "/workcenter/{workCenterId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateWorkCenter(@PathVariable Long workCenterId, @RequestParam String companyCodeId,
                                              @RequestParam String languageId, @RequestParam String plantId, @RequestParam String workCenterType,
                                              @RequestParam String loginUserID, @RequestParam String warehouseId, @RequestBody UpdateWorkCenter updateWorkCenter) {

        WorkCenter dbWorkCenter = mastersService.updateWorkCenter(companyCodeId, plantId,
                languageId, warehouseId, workCenterId, updateWorkCenter, workCenterType, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbWorkCenter, HttpStatus.OK);
    }

    // DELETE
    @ApiOperation(response = WorkCenter.class, value = "Delete WorkCenter") // label for swagger
    @RequestMapping(value = "/workcenter/{workCenterId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWorkCenter(@PathVariable Long workCenterId, @RequestParam String companyCodeId,
                                              @RequestParam String languageId, @RequestParam String workCenterType,
                                              @RequestParam String plantId, @RequestParam String loginUserID,
                                              @RequestParam String warehouseId) {

        mastersService.deleteWorkCenter(companyCodeId, plantId, languageId, warehouseId, workCenterId, workCenterType, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = WorkCenter[].class, value = "Find WorkCenter")//label for swagger
    @PostMapping("/workCenter/findWorkCenter")
    public WorkCenter[] findWorkCenter(@RequestBody SearchWorkCenter searchWorkCenter) throws Exception {
        return mastersService.findWorkCenter(searchWorkCenter, getAuthToken());
    }

    // ThreePL
    /* --------------------------------Billing-----------------------------------------------------------------------*/
    // GET ALL
    @ApiOperation(response = Billing.class, value = "Get AllBillings") // label for swagger
    @RequestMapping(value = "/billing", method = RequestMethod.GET)
    public ResponseEntity<?> getBillings() {
        Billing[] Billing = mastersService.getBillings(getAuthToken());
        return new ResponseEntity<>(Billing, HttpStatus.OK);
    }

    // GET
    @ApiOperation(response = Billing.class, value = "Get Billing") // label for swagger
    @RequestMapping(value = "/billing/{partnerCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getbilling(@RequestParam String warehouseId, @RequestParam String moduleId,
                                        @PathVariable String partnerCode, @RequestParam String companyCodeId,
                                        @RequestParam String languageId, @RequestParam String plantId) {

        Billing billing = mastersService.getBilling(warehouseId, moduleId, partnerCode, companyCodeId, languageId, plantId, getAuthToken());
        log.info("Billing::: " + billing);
        return new ResponseEntity<>(billing, HttpStatus.OK);
    }

    // CREATE
    @ApiOperation(response = Billing.class, value = "Create new Billing") // label for swagger
    @RequestMapping(value = "/billing", method = RequestMethod.POST)
    public ResponseEntity<?> createBilling(@RequestParam String loginUserID, @RequestBody AddBilling newBilling) {

        Billing billing = mastersService.addBilling(newBilling, loginUserID, getAuthToken());
        return new ResponseEntity<>(billing, HttpStatus.OK);
    }

    // UPDATE
    @ApiOperation(response = Billing.class, value = "Update Billing") // label for swagger
    @RequestMapping(value = "/billing/{partnerCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBilling(@RequestParam String moduleId, @PathVariable String partnerCode,
                                           @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId,
                                           @RequestParam String loginUserID, @RequestParam String warehouseId, @RequestBody UpdateBilling updateBilling) {

        Billing Billing = mastersService.updateBilling(warehouseId, moduleId, partnerCode, companyCodeId, languageId,
                plantId, loginUserID, updateBilling, getAuthToken());
        return new ResponseEntity<>(Billing, HttpStatus.OK);
    }

    // DELETE
    @ApiOperation(response = Billing.class, value = "Delete Billing") // label for swagger
    @RequestMapping(value = "/billing/{partnerCode}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBilling(@PathVariable String partnerCode, @RequestParam String moduleId, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                           @RequestParam String warehouseId) {

        mastersService.deleteBilling(warehouseId, moduleId, partnerCode, companyCodeId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // FIND
    @ApiOperation(response = Billing[].class, value = "Find Billing")//label for swagger
    @PostMapping("/billing/findBilling")
    public Billing[] findBilling(@RequestBody FindBilling findBilling) throws Exception {
        return mastersService.findBilling(findBilling, getAuthToken());
    }

    /* --------------------------------CbmInbound-------------------------------------------------------------------*/
    // GET ALL
    @ApiOperation(response = CbmInbound.class, value = "Get AllCbmInbounds") // label for swagger
    @RequestMapping(value = "/cbminbound", method = RequestMethod.GET)
    public ResponseEntity<?> getCbmInbounds() {

        CbmInbound[] CbmInbound = mastersService.getCbmInbounds(getAuthToken());
        return new ResponseEntity<>(CbmInbound, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = CbmInbound.class, value = "Get CbmInbound") // label for swagger
    @RequestMapping(value = "/cbminbound/{itemCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getCbmInbound(@RequestParam String warehouseId, @PathVariable String itemCode, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId) {

        CbmInbound CbmInbound = mastersService.getCbmInbound(warehouseId, itemCode, companyCodeId, languageId, plantId, getAuthToken());
        log.info("CbmInbound::: " + CbmInbound);
        return new ResponseEntity<>(CbmInbound, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = CbmInbound.class, value = "Create new CbmInbound") // label for swagger
    @RequestMapping(value = "/cbminbound", method = RequestMethod.POST)
    public ResponseEntity<?> createCbmInbound(@RequestParam String loginUserID, @RequestBody AddCbmInbound newCbmInbound) {

        CbmInbound CbmInbound = mastersService.addCbmInbound(newCbmInbound, loginUserID, getAuthToken());
        return new ResponseEntity<>(CbmInbound, HttpStatus.OK);
    }

//UPDATE

    @ApiOperation(response = CbmInbound.class, value = "Update CbmInbound") // label for swagger
    @RequestMapping(value = "/cbminbound/{itemCode}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateCbmInbound(@PathVariable String itemCode, @RequestParam String companyCodeId,
                                              @RequestParam String languageId, @RequestParam String plantId, @RequestParam String loginUserID,
                                              @RequestParam String warehouseId, @RequestBody UpdateCbmInbound updateCbmInbound) {
        CbmInbound CbmInbound = mastersService.updateCbmInbound(warehouseId, itemCode, companyCodeId, languageId, plantId,
                loginUserID, updateCbmInbound, getAuthToken());
        return new ResponseEntity<>(CbmInbound, HttpStatus.OK);
    }

//DELETE

    @ApiOperation(response = CbmInbound.class, value = "Delete CbmInbound") // label for swagger
    @RequestMapping(value = "/cbminbound/{itemCode}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCbmInbound(@PathVariable String itemCode, @RequestParam String loginUserID, @RequestParam String warehouseId,
                                              @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId) {
        mastersService.deleteCbmInbound(warehouseId, itemCode, companyCodeId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = CbmInbound[].class, value = "Find CbmInbound")//label for swagger
    @PostMapping("/cbminbound/findCbmInbound")
    public CbmInbound[] findCbmInbound(@RequestBody FindCbmInbound findCbmInbound) throws Exception {
        return mastersService.findCbmInbound(findCbmInbound, getAuthToken());
    }

    /* --------------------------------PriceList-------------------------------------------------------------------------*/
//GET ALL
    @ApiOperation(response = PriceList.class, value = "Get AllPriceLists") // label for swagger
    @RequestMapping(value = "/pricelist", method = RequestMethod.GET)
    public ResponseEntity<?> getPriceLists() {

        PriceList[] PriceList = mastersService.getPriceLists(getAuthToken());
        return new ResponseEntity<>(PriceList, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = PriceList.class, value = "Get PriceList") // label for swagger
    @RequestMapping(value = "/pricelist/{priceListId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPriceList(@RequestParam String warehouseId, @RequestParam String moduleId, @RequestParam String companyCodeId,
                                          @RequestParam String languageId, @RequestParam String plantId,
                                          @PathVariable Long priceListId, @RequestParam Long serviceTypeId, @RequestParam Long chargeRangeId) {

        PriceList PriceList = mastersService.getPriceList(warehouseId, moduleId, priceListId, serviceTypeId, chargeRangeId,
                companyCodeId, languageId, plantId, getAuthToken());
        log.info("PriceList::: " + PriceList);
        return new ResponseEntity<>(PriceList, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = PriceList.class, value = "Create new PriceList") // label for swagger
    @RequestMapping(value = "/pricelist", method = RequestMethod.POST)
    public ResponseEntity<?> createPriceList(@RequestParam String loginUserID,
                                             @RequestBody AddPriceList newPriceList) {

        PriceList PriceList = mastersService.addPriceList(newPriceList, loginUserID, getAuthToken());
        return new ResponseEntity<>(PriceList, HttpStatus.OK);
    }

    //UPDATE
    @ApiOperation(response = PriceList.class, value = "Update PriceList") // label for swagger
    @RequestMapping(value = "/pricelist/{priceListId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updatePriceList(@PathVariable Long priceListId, @RequestParam String companyCodeId,
                                             @RequestParam String languageId, @RequestParam String plantId,
                                             @RequestParam Long serviceTypeId, @RequestParam String moduleId,
                                             @RequestParam Long chargeRangeId, @RequestParam String loginUserID,
                                             @RequestParam String warehouseId, @RequestBody UpdatePriceList updatePriceList) {

        PriceList PriceList = mastersService.updatePriceList(warehouseId, moduleId, priceListId, serviceTypeId, chargeRangeId,
                companyCodeId, languageId, plantId, loginUserID, updatePriceList, getAuthToken());
        return new ResponseEntity<>(PriceList, HttpStatus.OK);
    }

    //DELETE
    @ApiOperation(response = PriceList.class, value = "Delete PriceList") // label for swagger
    @RequestMapping(value = "/pricelist/{priceListId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePriceList(@PathVariable Long priceListId, @RequestParam String moduleId,
                                             @RequestParam Long serviceTypeId, @RequestParam Long chargeRangeId, @RequestParam String loginUserID,
                                             @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId,
                                             @RequestParam String warehouseId) {

        mastersService.deletePriceList(warehouseId, moduleId, priceListId, serviceTypeId, chargeRangeId, companyCodeId,
                languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // FIND
    @ApiOperation(response = PriceList[].class, value = "Find PriceList")//label for swagger
    @PostMapping("/pricelist/findPriceList")
    public PriceList[] findPriceList(@RequestBody FindPriceList findPriceList) throws Exception {
        return mastersService.findPriceList(findPriceList, getAuthToken());
    }

    /* --------------------------------PriceListAssignment-----------------------------------------------------------*/
    // GET ALL
    @ApiOperation(response = PriceListAssignment.class, value = "Get AllPriceListAssignments") // label for swagger
    @RequestMapping(value = "/pricelistassignment", method = RequestMethod.GET)
    public ResponseEntity<?> getPriceListAssignments() {

        PriceListAssignment[] PriceListAssignment = mastersService.getPriceListAssignments(getAuthToken());
        return new ResponseEntity<>(PriceListAssignment, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = PriceListAssignment.class, value = "Get PriceListAssignment") // label for swagger
    @RequestMapping(value = "/pricelistassignment/{priceListId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPriceListAssignment(@RequestParam String warehouseId, @RequestParam String companyCodeId,
                                                    @RequestParam String languageId, @RequestParam String plantId,
                                                    @RequestParam String partnerCode, @PathVariable Long priceListId) {

        PriceListAssignment PriceListAssignment = mastersService.getPriceListAssignment(warehouseId, partnerCode, priceListId, companyCodeId,
                languageId, plantId, getAuthToken());
        log.info("PriceListAssignment::: " + PriceListAssignment);
        return new ResponseEntity<>(PriceListAssignment, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = PriceListAssignment.class, value = "Create new PriceListAssignment") // label for swagger
    @RequestMapping(value = "/pricelistassignment", method = RequestMethod.POST)
    public ResponseEntity<?> createPriceListAssignment(@RequestParam String loginUserID,
                                                       @RequestBody AddPriceListAssignment newPriceListAssignment) {
        PriceListAssignment PriceListAssignment = mastersService.addPriceListAssignment(newPriceListAssignment, loginUserID, getAuthToken());
        return new ResponseEntity<>(PriceListAssignment, HttpStatus.OK);
    }

    //UPDATE
    @ApiOperation(response = PriceListAssignment.class, value = "Update PriceListAssignment") // label for swagger
    @RequestMapping(value = "/pricelistassignment/{priceListId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updatePriceListAssignment(@RequestParam String partnerCode,
                                                       @PathVariable Long priceListId, @RequestParam String loginUserID,
                                                       @RequestParam String companyCodeId, @RequestParam String languageId, @RequestParam String plantId,
                                                       @RequestParam String warehouseId, @RequestBody UpdatePriceListAssignment updatePriceListAssignment) {

        PriceListAssignment PriceListAssignment = mastersService.updatePriceListAssignment(warehouseId, partnerCode, priceListId,
                companyCodeId, languageId, plantId, loginUserID, updatePriceListAssignment, getAuthToken());
        return new ResponseEntity<>(PriceListAssignment, HttpStatus.OK);
    }

    //DELETE
    @ApiOperation(response = PriceListAssignment.class, value = "Delete PriceListAssignment") // label for swagger
    @RequestMapping(value = "/pricelistassignment/{priceListId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePriceListAssignment(@RequestParam String partnerCode,
                                                       @PathVariable Long priceListId, @RequestParam String loginUserID, @RequestParam String companyCodeId,
                                                       @RequestParam String languageId, @RequestParam String plantId, @RequestParam String warehouseId) {

        mastersService.deletePriceListAssignment(warehouseId, priceListId, partnerCode, companyCodeId, languageId,
                plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = PriceListAssignment[].class, value = "Find PriceListAssignment")//label for swagger
    @PostMapping("/pricelistassignment/findPriceListAssignment")
    public PriceListAssignment[] findPriceListAssignment(@RequestBody FindPriceListAssignment findPriceListAssignment) throws Exception {
        return mastersService.findPriceListAssignment(findPriceListAssignment, getAuthToken());
    }

    /* --------------------------------Driver------------------------------------------------------------------------*/

    //GET ALL
    @ApiOperation(response = Driver.class, value = "Get All Driver") // label for swagger
    @RequestMapping(value = "/driver", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDriver() {
        Driver[] drivers = mastersService.getAllDriver(getAuthToken());
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = Driver.class, value = "Get Driver") // label for swagger
    @RequestMapping(value = "/driver/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriver(@RequestParam String warehouseId, @PathVariable Long driverId,
                                       @RequestParam String companyCodeId, @RequestParam String languageId,
                                       @RequestParam String plantId) {

        Driver dbDriver = mastersService.getDriver(companyCodeId, plantId, languageId,
                warehouseId, driverId, getAuthToken());
        log.info("driver::: " + dbDriver);
        return new ResponseEntity<>(dbDriver, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = Driver.class, value = "Create new Driver") // label for swagger
    @RequestMapping(value = "/driver", method = RequestMethod.POST)
    public ResponseEntity<?> createDriver(@RequestParam String loginUserID, @RequestBody AddDriver addDriver) {

        Driver dbDriver = mastersService.addDriver(addDriver, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbDriver, HttpStatus.OK);
    }

    //UPDATE
    @ApiOperation(response = Driver.class, value = "Update Driver") // label for swagger
    @RequestMapping(value = "/driver/{driverId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateDriver(@PathVariable Long driverId, @RequestParam String companyCodeId, @RequestParam String languageId,
                                          @RequestParam String plantId, @RequestParam String loginUserID,
                                          @RequestParam String warehouseId, @RequestBody UpdateDriver updateDriver) {

        Driver dbDriver = mastersService.updateDriver(companyCodeId, plantId,
                languageId, warehouseId, driverId, updateDriver, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbDriver, HttpStatus.OK);
    }

    //DELETE
    @ApiOperation(response = Driver.class, value = "Delete Driver") // label for swagger
    @RequestMapping(value = "/driver/{driverId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDriver(@PathVariable Long driverId, @RequestParam String companyCodeId, @RequestParam String languageId,
                                          @RequestParam String plantId, @RequestParam String loginUserID,
                                          @RequestParam String warehouseId) {

        mastersService.deleteDriver(companyCodeId, plantId, languageId, warehouseId, driverId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = Driver[].class, value = "Find Driver")//label for swagger
    @PostMapping("/driver/findDriver")
    public Driver[] findDriver(@RequestBody SearchDriver searchDriver) throws Exception {
        return mastersService.findDriver(searchDriver, getAuthToken());
    }


    /* --------------------------------Vehicle-------------------------------------------------------------------------*/

    //GET ALL
    @ApiOperation(response = Vehicle.class, value = "Get All vehicle") // label for swagger
    @RequestMapping(value = "/vehicle", method = RequestMethod.GET)
    public ResponseEntity<?> getAllVehicle() {
        Vehicle[] vehicles = mastersService.getAllVehicle(getAuthToken());
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = Vehicle.class, value = "Get Vehicle") // label for swagger
    @RequestMapping(value = "/vehicle/{vehicleNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getVehicle(@RequestParam String warehouseId, @PathVariable String vehicleNumber, @RequestParam String companyCodeId,
                                        @RequestParam String languageId, @RequestParam String plantId) {

        Vehicle dbVehicle = mastersService.getVehicle(companyCodeId, plantId, languageId, warehouseId, vehicleNumber, getAuthToken());
        log.info("vehicle::: " + dbVehicle);
        return new ResponseEntity<>(dbVehicle, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = Vehicle.class, value = "Create new Vehicle") // label for swagger
    @RequestMapping(value = "/vehicle", method = RequestMethod.POST)
    public ResponseEntity<?> createVehicle(@RequestParam String loginUserID, @RequestBody AddVehicle addVehicle) {

        Vehicle dbVehicle = mastersService.addVehicle(addVehicle, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbVehicle, HttpStatus.OK);
    }

    //UPDATE
    @ApiOperation(response = Vehicle.class, value = "Update Vehicle") // label for swagger
    @RequestMapping(value = "/vehicle/{vehicleNumber}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateVehicle(@PathVariable String vehicleNumber, @RequestParam String companyCodeId,
                                           @RequestParam String languageId, @RequestParam String plantId,
                                           @RequestParam String loginUserID, @RequestParam String warehouseId, @RequestBody UpdateVehicle updateVehicle) {

        Vehicle dbVehicle = mastersService.updateVehicle(companyCodeId, plantId,
                languageId, warehouseId, vehicleNumber, updateVehicle, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbVehicle, HttpStatus.OK);
    }

    //DELETE
    @ApiOperation(response = Vehicle.class, value = "Delete Vehicle") // label for swagger
    @RequestMapping(value = "/vehicle/{vehicleNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVehicle(@PathVariable String vehicleNumber, @RequestParam String companyCodeId, @RequestParam String languageId,
                                           @RequestParam String plantId, @RequestParam String loginUserID,
                                           @RequestParam String warehouseId) {

        mastersService.deleteVehicle(companyCodeId, plantId, languageId, warehouseId, vehicleNumber, loginUserID, getAuthToken());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = Vehicle[].class, value = "Find Vehicle")//label for swagger
    @PostMapping("/vehicle/findVehicle")
    public Vehicle[] findvehicle(@RequestBody SearchVehicle searchVehicle) throws Exception {
        return mastersService.findVehicle(searchVehicle, getAuthToken());
    }

    /* --------------------------------Route-------------------------------------------------------------------------*/
    //GET ALL
    @ApiOperation(response = Route.class, value = "Get All Route") // label for swagger
    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRoute() {
        Route[] routes = mastersService.getAllRoute(getAuthToken());
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = Route.class, value = "Get Route") // label for swagger
    @RequestMapping(value = "/route/{routeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoute(@RequestParam String warehouseId, @PathVariable Long routeId,
                                      @RequestParam String companyCodeId, @RequestParam String languageId,
                                      @RequestParam String plantId) {

        Route dbRouteId = mastersService.getRoute(companyCodeId, plantId, languageId, warehouseId, routeId, getAuthToken());
        log.info("Route::: " + dbRouteId);
        return new ResponseEntity<>(dbRouteId, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = Route.class, value = "Create new Route") // label for swagger
    @RequestMapping(value = "/route", method = RequestMethod.POST)
    public ResponseEntity<?> createRoute(@RequestParam String loginUserID, @RequestBody AddRoute addRoute) {

        Route dbRoute = mastersService.addRoute(addRoute, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbRoute, HttpStatus.OK);
    }

    //UPDATE
    @ApiOperation(response = Route.class, value = "Update Route") // label for swagger
    @RequestMapping(value = "/route/{routeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateRoute(@PathVariable Long routeId,
                                         @RequestParam String companyCodeId, @RequestParam String languageId,
                                         @RequestParam String plantId, @RequestParam String loginUserID,
                                         @RequestParam String warehouseId, @RequestBody UpdateRoute updateRoute) {

        Route dbRouteId = mastersService.updateRoute(companyCodeId, plantId,
                languageId, warehouseId, routeId, updateRoute, loginUserID, getAuthToken());

        return new ResponseEntity<>(dbRouteId, HttpStatus.OK);
    }

    //DELETE
    @ApiOperation(response = Route.class, value = "Delete Route") // label for swagger
    @RequestMapping(value = "/route/{routeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRoute(@PathVariable Long routeId, @RequestParam String companyCodeId,
                                         @RequestParam String languageId, @RequestParam String plantId,
                                         @RequestParam String loginUserID, @RequestParam String warehouseId) {

        mastersService.deleteRoute(companyCodeId, plantId, languageId, warehouseId, routeId, loginUserID, getAuthToken());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = Route[].class, value = "Find Route")//label for swagger
    @PostMapping("/route/findRoute")
    public Route[] findRoute(@RequestBody SearchRoute searchRoute) throws Exception {
        return mastersService.findRoute(searchRoute, getAuthToken());
    }

    /* --------------------------------DriverVehicleAssignment-------------------------------------------------------------------------*/

    //GET ALL
    @ApiOperation(response = DriverVehicleAssignment.class, value = "Get All DriverVehicleAssignment")
    // label for swagger
    @RequestMapping(value = "/drivervehicleassignment", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDriverVehicleAssignment() {

        DriverVehicleAssignment[] driverVehicleAssignments = mastersService.getAllDriverVehicleAssignment(getAuthToken());
        return new ResponseEntity<>(driverVehicleAssignments, HttpStatus.OK);
    }

    //GET
    @ApiOperation(response = DriverVehicleAssignment.class, value = "Get DriverVehicleAssignment") // label for swagger
    @RequestMapping(value = "/drivervehicleassignment/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDriverVehicleAssignment(@RequestParam String warehouseId, @PathVariable Long driverId,
                                                        @RequestParam String vehicleNumber, @RequestParam Long routeId,
                                                        @RequestParam String companyCodeId, @RequestParam String languageId,
                                                        @RequestParam String plantId) {

        DriverVehicleAssignment dbDriverVehicleAssignment = mastersService.getDriverVehicleAssignment(companyCodeId, plantId, languageId,
                warehouseId, routeId, driverId, vehicleNumber, getAuthToken());
        log.info("DriverVehicleAssignment::: " + dbDriverVehicleAssignment);
        return new ResponseEntity<>(dbDriverVehicleAssignment, HttpStatus.OK);
    }

    //CREATE
    @ApiOperation(response = DriverVehicleAssignment.class, value = "Create new DriverVehicleAssignment")
    // label for swagger
    @RequestMapping(value = "/drivervehicleassignment", method = RequestMethod.POST)
    public ResponseEntity<?> createDriverVehicleAssignment(@RequestParam String loginUserID,
                                                           @RequestBody AddDriverVehicleAssignment addDriverVehicleAssignment) {

        DriverVehicleAssignment dbDriverVehicleAssignment = mastersService.addDriverVehicleAssignment(addDriverVehicleAssignment, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbDriverVehicleAssignment, HttpStatus.OK);
    }

    //UPDATE
    @ApiOperation(response = DriverVehicleAssignment.class, value = "Update DriverVehicleAssignment")
    // label for swagger
    @RequestMapping(value = "/drivervehicleassignment/{driverId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateDriverVehicleAssignment(@PathVariable Long driverId, @RequestParam String vehicleNumber, @RequestParam Long routeId,
                                                           @RequestParam String companyCodeId, @RequestParam String languageId,
                                                           @RequestParam String plantId, @RequestParam String loginUserID, @RequestParam String warehouseId,
                                                           @RequestBody UpdateDriverVehicleAssignment updateDriverVehicleAssignment) {

        DriverVehicleAssignment dbDriverVehicleAssignment =
                mastersService.updateDriverVehicleAssignment(companyCodeId, plantId, languageId, warehouseId,
                        routeId, driverId, vehicleNumber, updateDriverVehicleAssignment, loginUserID, getAuthToken());
        return new ResponseEntity<>(dbDriverVehicleAssignment, HttpStatus.OK);
    }

    //DELETE
    @ApiOperation(response = DriverVehicleAssignment.class, value = "Delete DriverVehicleAssignment") // label for swagger
    @RequestMapping(value = "/drivervehicleassignment/{driverId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDriverVehicleAssignment(@PathVariable Long driverId, @RequestParam String vehicleNumber,
                                                           @RequestParam Long routeId, @RequestParam String companyCodeId,
                                                           @RequestParam String languageId, @RequestParam String plantId,
                                                           @RequestParam String loginUserID, @RequestParam String warehouseId) {

        mastersService.deleteDriverVehicleAssignment(companyCodeId, plantId, languageId, warehouseId, routeId,
                vehicleNumber, driverId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = DriverVehicleAssignment[].class, value = "Find DriverVehicleAssignment") //label for swagger
    @PostMapping("/drivervehicleassignment/findDriverVehicleAssignment")
    public DriverVehicleAssignment[] findDriverVehicleAssignment(@RequestBody SearchDriverVehicleAssignment searchDriverVehicleAssignment) throws Exception {
        return mastersService.findDriverVehicleAssignment(searchDriverVehicleAssignment, getAuthToken());
    }

    //-----------------------------------------Master-OrderAPI------------------------------------------------


    // Item - Master
    @ApiOperation(response = Item.class, value = "Create Item Master Order") //label for swagger
    @PostMapping("/warehouse/master/item")
    public ResponseEntity<?> postItem(@Valid @RequestBody Item item)
            throws IllegalAccessException, InvocationTargetException {
        WarehouseApiResponse createdItem = mastersService.postItem(item, getAuthToken());
        return new ResponseEntity<>(createdItem, HttpStatus.OK);
    }

    //Customer-Master

    @ApiOperation(response = Customer.class, value = "Create Customer Master Order") // label for swagger
    @PostMapping("/warehouse/master/customer")
    public ResponseEntity<?> postCustomer(@Valid @RequestBody Customer customer)
            throws IllegalAccessException, InvocationTargetException {

        WarehouseApiResponse createdCustomer = mastersService.postCustomer(customer, getAuthToken());
        return new ResponseEntity<>(createdCustomer, HttpStatus.OK);
    }

}