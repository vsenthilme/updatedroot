package com.tekclover.wms.core.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.tekclover.wms.core.model.auth.AuthToken;
import com.tekclover.wms.core.model.enterprise.*;
import com.tekclover.wms.core.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tekclover.wms.core.service.EnterpriseSetupService;
import com.tekclover.wms.core.service.RegisterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wms-enterprise-service")
@Api(tags = {"Enterprise Service"}, value = "Enterprise Service Operations") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operations related to Enterprise Modules")})
public class EnterpriseServiceController {

    @Autowired
    EnterpriseSetupService enterpriseSetupService;

    @Autowired
    RegisterService registerService;

    @Autowired
    AuthTokenService authTokenService;

    private String getAuthToken() {
        AuthToken authTokenForEnterpriseService = authTokenService.getEnterpriseServiceAuthToken();
        return authTokenForEnterpriseService.getAccess_token();
    }

    /*
     * -----------------------------ENTERPRISE---BARCODE----------------------------
     */
    @ApiOperation(response = Barcode.class, value = "Get all Barcode") // label for swagger
    @RequestMapping(value = "/barcode", method = RequestMethod.GET)
    public ResponseEntity<?> getBarcodes() {
        Barcode[] barcode = enterpriseSetupService.getBarcodes(getAuthToken());
        log.info("Barcode : " + barcode);
        return new ResponseEntity<>(barcode, HttpStatus.OK);
    }

    @ApiOperation(response = Barcode.class, value = "Get a Barcode")
    @GetMapping("/barcode/{barcodeTypeId}")
    public ResponseEntity<?> getBarcode(@PathVariable Long barcodeTypeId, @RequestParam String warehouseId,
                                        @RequestParam String method, @RequestParam Long barcodeSubTypeId, @RequestParam Long levelId,
                                        @RequestParam String levelReference, @RequestParam Long processId, @RequestParam String companyId,
                                        @RequestParam String languageId, @RequestParam String plantId) {
        Barcode barcode = enterpriseSetupService.getBarcode(warehouseId, method, barcodeTypeId,
                barcodeSubTypeId, levelId, levelReference, processId, companyId, plantId, languageId, getAuthToken());
        log.info("Barcode : " + barcode);
        return new ResponseEntity<>(barcode, HttpStatus.OK);
    }

    @ApiOperation(response = Barcode.class, value = "Search Barcode") // label for swagger
    @PostMapping("/barcode/findBarcode")
    public Barcode[] findBarcode(@RequestBody SearchBarcode searchBarcode) throws Exception {
        return enterpriseSetupService.findBarcode(searchBarcode, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New Barcode") // label for swagger
    @RequestMapping(value = "/barcode", method = RequestMethod.POST)
    public ResponseEntity<?> createBarcode(@RequestBody Barcode newBarcode, @RequestParam String loginUserID) {
        Barcode createdBarcode = enterpriseSetupService.addBarcode(newBarcode, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdBarcode, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update Barcode") // label for swagger
    @RequestMapping(value = "/barcode/{barcodeTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBarcode(@PathVariable Long barcodeTypeId, @RequestParam String warehouseId,
                                           @RequestParam String method, @RequestParam Long barcodeSubTypeId, @RequestParam Long levelId,
                                           @RequestParam String companyId, @RequestParam String languageId, @RequestParam String plantId,
                                           @RequestParam String levelReference, @RequestParam Long processId,
                                           @RequestBody Barcode updatedBarcode, @RequestParam String loginUserID) {
        Barcode modifiedBarcode =
                enterpriseSetupService.updateBarcode(warehouseId, method, barcodeTypeId, barcodeSubTypeId, companyId, plantId, languageId, levelId,
                        levelReference, processId, updatedBarcode, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedBarcode, HttpStatus.OK);
    }

    @ApiOperation(response = Barcode.class, value = "Delete Barcode") // label for swagger
    @RequestMapping(value = "/barcode/{barcodeTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBarcode(@PathVariable Long barcodeTypeId, @RequestParam String warehouseId,
                                           @RequestParam String method, @RequestParam Long barcodeSubTypeId, @RequestParam Long levelId,
                                           @RequestParam String levelReference, @RequestParam Long processId, @RequestParam String companyId,
                                           @RequestParam String plantId, @RequestParam String languageId, @RequestParam String loginUserID) {
        enterpriseSetupService.deleteBarcode(warehouseId, method, barcodeTypeId, barcodeSubTypeId, companyId, languageId, plantId, levelId,
                levelReference, processId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---COMPANY----------------------------
     */
    @ApiOperation(response = Company.class, value = "Get all Company") // label for swagger
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public ResponseEntity<?> getCompanys() {
        Company[] companyMaster = enterpriseSetupService.getCompanies(getAuthToken());
        log.info("Company : " + companyMaster);
        return new ResponseEntity<>(companyMaster, HttpStatus.OK);
    }

    @ApiOperation(response = Company.class, value = "Get a Company") // label for swagger
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCompany(@PathVariable String companyId, @RequestParam String languageId) {
        Company companyMaster = enterpriseSetupService.getCompany(companyId, languageId, getAuthToken());
        log.info("Company : " + companyMaster);
        return new ResponseEntity<>(companyMaster, HttpStatus.OK);
    }

    @ApiOperation(response = Company.class, value = "Search Company") // label for swagger
    @PostMapping("/company/findCompany")
    public Company[] findCompany(@RequestBody SearchCompany searchCompany)
            throws ParseException {
        return enterpriseSetupService.findCompany(searchCompany, getAuthToken());
    }

    @ApiOperation(response = Company.class, value = "Create Company") // label for swagger
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public ResponseEntity<?> createCompany(@RequestBody Company newCompany, @RequestParam String loginUserID) {
        Company createdCompany = enterpriseSetupService.addCompany(newCompany, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdCompany, HttpStatus.OK);
    }

    @ApiOperation(response = Company.class, value = "Update Company") // label for swagger
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateCompany(@PathVariable String companyId, @RequestParam String languageId, @RequestParam String loginUserID,
                                           @RequestBody Company updatedCompany) {
        Company modifiedCompany = enterpriseSetupService.updateCompany(companyId, languageId, loginUserID, updatedCompany, getAuthToken());
        return new ResponseEntity<>(modifiedCompany, HttpStatus.OK);
    }

    @ApiOperation(response = Company.class, value = "Delete Company") // label for swagger
    @RequestMapping(value = "/company", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompany(@RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String loginUserID) {
        enterpriseSetupService.deleteCompany(companyId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---BATCHSERIAL------------------------
     */
    @ApiOperation(response = BatchSerial.class, value = "Get all BatchSerial") // label for swagger
    @RequestMapping(value = "/batchserial", method = RequestMethod.GET)
    public ResponseEntity<?> getBatchSerials() {
        BatchSerial[] batchserial = enterpriseSetupService.getBatchSerials(getAuthToken());
        log.info("BatchSerial : " + batchserial);
        return new ResponseEntity<>(batchserial, HttpStatus.OK);
    }

    @ApiOperation(response = BatchSerial.class, value = "Get a BatchSerial") // label for swagger
    @RequestMapping(value = "/batchserial/{storageMethod}", method = RequestMethod.GET)
    public ResponseEntity<?> getBatchSerial(@PathVariable String storageMethod, @RequestParam String companyId,
                                            @RequestParam String languageId, @RequestParam String plantId,
                                            @RequestParam String warehouseId, @RequestParam String maintenance,
                                            @RequestParam Long levelId) {

        BatchSerial[] batchserial = enterpriseSetupService.getBatchSerial(storageMethod, companyId, languageId, plantId,
                warehouseId, levelId, maintenance, getAuthToken());
        log.info("BatchSerial : " + batchserial);
        return new ResponseEntity<>(batchserial, HttpStatus.OK);
    }

    @ApiOperation(response = BatchSerial.class, value = "Search BatchSerial") // label for swagger
    @PostMapping("/batchserial/findBatchSerial")
    public BatchSerial[] findBatchSerial(@RequestBody SearchBatchSerial searchBatchSerial) throws Exception {
        return enterpriseSetupService.findBatchSerial(searchBatchSerial, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New BatchSerial") // label for swagger
    @RequestMapping(value = "/batchserial", method = RequestMethod.POST)
    public ResponseEntity<?> createBatchSerial(@RequestBody List<AddBatchSerial> newBatchSerial, @RequestParam String loginUserID) {
        BatchSerial[] createdBatchSerial = enterpriseSetupService.addBatchSerial(newBatchSerial, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdBatchSerial, HttpStatus.OK);
    }

//	@ApiOperation(response = Optional.class, value = "Create New BatchSerial") // label for swagger
//	@RequestMapping(value = "/batchserial", method = RequestMethod.POST)
//	public ResponseEntity<?> createBatchSerial(@RequestBody BatchSerial newBatchSerial, @RequestParam String loginUserID) {
//		BatchSerial createdBatchSerial = enterpriseSetupService.addBatchSerial(newBatchSerial, loginUserID, getAuthToken());
//		return new ResponseEntity<>(createdBatchSerial , HttpStatus.OK);
//	}

    @ApiOperation(response = Optional.class, value = "Update BatchSerial") // label for swagger
    @RequestMapping(value = "/batchserial/{storageMethod}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBatchSerial(@PathVariable String storageMethod, @RequestParam String companyId,
                                               @RequestParam String plantId, @RequestParam String warehouseId,
                                               @RequestParam String languageId, @RequestParam Long levelId, @RequestParam String maintenance,
                                               @Valid @RequestBody List<UpdateBatchSerial> updatedBatchSerial, @RequestParam String loginUserID) {

        BatchSerial[] modifiedBatchSerial = enterpriseSetupService.updateBatchSerial(storageMethod, companyId, languageId, maintenance,
                plantId, warehouseId, levelId, updatedBatchSerial, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedBatchSerial, HttpStatus.OK);
    }
//	@ApiOperation(response = Optional.class, value = "Update BatchSerial") // label for swagger
//    @RequestMapping(value = "/batchserial/{storageMethod}", method = RequestMethod.PATCH)
//	public ResponseEntity<?> updateBatchSerial(@PathVariable String storageMethod,
//			@Valid @RequestBody BatchSerial updatedBatchSerial, @RequestParam String loginUserID) {
//		BatchSerial modifiedBatchSerial = enterpriseSetupService.updateBatchSerial(storageMethod, updatedBatchSerial, loginUserID, getAuthToken());
//		return new ResponseEntity<>(modifiedBatchSerial, HttpStatus.OK);
//	}

    @ApiOperation(response = BatchSerial.class, value = "Delete BatchSerial") // label for swagger
    @RequestMapping(value = "/batchserial/{storageMethod}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBatchSerial(@RequestParam String storageMethod, @RequestParam String companyId, @RequestParam String plantId,
                                               @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam Long levelId,
                                               @RequestParam String loginUserID, @RequestParam String maintenance) {
        enterpriseSetupService.deleteBatchSerial(storageMethod, companyId, plantId, warehouseId, maintenance, levelId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---FLOOR------------------------------
     */

    @ApiOperation(response = Floor.class, value = "Get all Floor") // label for swagger
    @RequestMapping(value = "/floor", method = RequestMethod.GET)
    public ResponseEntity<?> getFloors() {
        Floor[] floor = enterpriseSetupService.getFloors(getAuthToken());
        log.info("Floor : " + floor);
        return new ResponseEntity<>(floor, HttpStatus.OK);
    }

    @ApiOperation(response = Floor.class, value = "Get a Floor") // label for swagger
    @RequestMapping(value = "/floor/{floorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getFloor(@PathVariable Long floorId, @RequestParam String companyId, @RequestParam String plantId,
                                      @RequestParam String languageId, @RequestParam String warehouseId) {
        Floor floor = enterpriseSetupService.getFloor(floorId, warehouseId, companyId, plantId, languageId, getAuthToken());
        log.info("Floor : " + floor);
        return new ResponseEntity<>(floor, HttpStatus.OK);
    }

    @ApiOperation(response = Floor.class, value = "Search Floor") // label for swagger
    @PostMapping("/floor/findFloor")
    public Floor[] findFloor(@RequestBody SearchFloor searchFloor)
            throws Exception {
        return enterpriseSetupService.findFloor(searchFloor, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New Floor") // label for swagger
    @RequestMapping(value = "/floor", method = RequestMethod.POST)
    public ResponseEntity<?> createFloor(@RequestBody Floor newFloor, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        Floor createdFloor = enterpriseSetupService.addFloor(newFloor, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdFloor, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update Floor") // label for swagger
    @RequestMapping(value = "/floor/{floorId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateFloor(@PathVariable Long floorId, @RequestParam String companyId, @RequestParam String plantId, @RequestParam String languageId,
                                         @RequestBody Floor updatedFloor, @RequestParam String loginUserID, @RequestParam String warehouseId) {
        Floor modifiedFloor = enterpriseSetupService.updateFloor(floorId, warehouseId, companyId, plantId, languageId, updatedFloor, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedFloor, HttpStatus.OK);
    }

    @ApiOperation(response = Floor.class, value = "Delete Floor") // label for swagger
    @RequestMapping(value = "/floor/{floorId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFloor(@PathVariable Long floorId, @RequestParam String companyId, @RequestParam String plantId,
                                         @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID) {
        enterpriseSetupService.deleteFloor(floorId, warehouseId, companyId, plantId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---EMPLOYEE--------------------------
     */
    @ApiOperation(response = Employee.class, value = "Get all Employee") // label for swagger
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployees() {
        Employee[] employee = enterpriseSetupService.getEMployees(getAuthToken());
        log.info("ItemGroup : " + employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @ApiOperation(response = Employee.class, value = "Get a Employee")
    @GetMapping("/employee/{employee}")
    public ResponseEntity<?> getEmployee(@PathVariable String employee) {
        Employee employee1 = enterpriseSetupService.getEmployee(employee, getAuthToken());
        log.info("ItemGroup : " + employee1);
        return new ResponseEntity<>(employee1, HttpStatus.OK);
    }

//	@ApiOperation(response = Employee.class, value = "Search ItemGroup") // label for swagger
//	@PostMapping("/employee/findEmployee")
//	public Employee[] findEmployee(@RequestBody search searchItemGroup)
//			throws Exception {
//		return enterpriseSetupService.findItemGroup(searchItemGroup, getAuthToken());
//	}

    @ApiOperation(response = Employee.class, value = "Create New Employee") // label for swagger
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee newEmployee, @RequestParam String loginUserID) {
        Employee createdEmployee = enterpriseSetupService.addEmployee(newEmployee, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @ApiOperation(response = Employee.class, value = "Update Employee") // label for swagger
    @RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateEmployee(@PathVariable String employee, @Valid @RequestBody Employee updateEmployee,
                                            @RequestParam String loginUserID) {
        Employee modifiedEmployee = enterpriseSetupService.updateEmployee(employee, updateEmployee, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedEmployee, HttpStatus.OK);
    }

    @ApiOperation(response = Employee.class, value = "Delete Employee") // label for swagger
    @RequestMapping(value = "/employee/{employee}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemGroup(@PathVariable String employee, @RequestParam String loginUserID) {
        enterpriseSetupService.deleteEmployee(employee, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---ITEMGROUP--------------------------
     */
    @ApiOperation(response = ItemGroup.class, value = "Get all ItemGroup") // label for swagger
    @RequestMapping(value = "/itemgroup", method = RequestMethod.GET)
    public ResponseEntity<?> getItemGroups() {
        ItemGroup[] itemgroup = enterpriseSetupService.getItemGroups(getAuthToken());
        log.info("ItemGroup : " + itemgroup);
        return new ResponseEntity<>(itemgroup, HttpStatus.OK);
    }

    @ApiOperation(response = ItemGroup.class, value = "Get a ItemGroup")
    @GetMapping("/itemgroup/{itemTypeId}")
    public ResponseEntity<?> getItemGroup(@PathVariable Long itemTypeId, @RequestParam String warehouseId,
                                          @RequestParam String companyId, @RequestParam String languageId,
                                          @RequestParam String plantId) {

        ItemGroup[] itemgroup = enterpriseSetupService.getItemGroup(companyId, languageId, plantId, warehouseId, itemTypeId, getAuthToken());
        log.info("ItemGroup : " + itemgroup);
        return new ResponseEntity<>(itemgroup, HttpStatus.OK);
    }

    @ApiOperation(response = ItemGroup.class, value = "Search ItemGroup") // label for swagger
    @PostMapping("/itemgroup/findItemGroup")
    public ItemGroup[] findItemGroup(@RequestBody SearchItemGroup searchItemGroup)
            throws Exception {
        return enterpriseSetupService.findItemGroup(searchItemGroup, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New ItemGroup") // label for swagger
    @RequestMapping(value = "/itemgroup", method = RequestMethod.POST)
    public ResponseEntity<?> createItemGroup(@RequestBody List<ItemGroup> newItemGroup, @RequestParam String loginUserID) {
        ItemGroup[] createdItemGroup = enterpriseSetupService.addItemGroup(newItemGroup, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdItemGroup, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ItemGroup") // label for swagger
    @RequestMapping(value = "/itemgroup/{itemTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateItemGroup(@PathVariable Long itemTypeId, @RequestParam String warehouseId,
                                             @RequestParam String companyId, @RequestParam String languageId,
                                             @RequestParam String plantId, @Valid @RequestBody List<ItemGroup> updateItemGroup,
                                             @RequestParam String loginUserID) {
        ItemGroup[] modifiedItemGroup = enterpriseSetupService.updateItemGroup(warehouseId, itemTypeId, updateItemGroup, companyId,
                languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedItemGroup, HttpStatus.OK);
    }

    @ApiOperation(response = ItemGroup.class, value = "Delete ItemGroup") // label for swagger
    @RequestMapping(value = "/itemgroup/{itemTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemGroup(@PathVariable Long itemTypeId, @RequestParam String warehouseId,
                                             @RequestParam String companyId, @RequestParam String languageId,
                                             @RequestParam String plantId, @RequestParam String loginUserID) {

        enterpriseSetupService.deleteItemGroup(warehouseId, itemTypeId, companyId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---ITEMTYPE---------------------------
     */
    @ApiOperation(response = ItemType.class, value = "Get all ItemType") // label for swagger
    @RequestMapping(value = "/itemtype", method = RequestMethod.GET)
    public ResponseEntity<?> getItemTypes() {
        ItemType[] itemtype = enterpriseSetupService.getItemTypes(getAuthToken());
        log.info("ItemType : " + itemtype);
        return new ResponseEntity<>(itemtype, HttpStatus.OK);
    }

    @ApiOperation(response = ItemType.class, value = "Get a ItemType")
    @GetMapping("/itemtype/{itemTypeId}")
    public ResponseEntity<?> getItemType(@PathVariable Long itemTypeId, @RequestParam String warehouseId,
                                         @RequestParam String companyId, @RequestParam String plantId, @RequestParam String languageId) {
        ItemType itemtype = enterpriseSetupService.getItemType(warehouseId, itemTypeId, companyId, plantId, languageId, getAuthToken());
        log.info("ItemType : " + itemtype);
        return new ResponseEntity<>(itemtype, HttpStatus.OK);
    }

    @ApiOperation(response = ItemType.class, value = "Search ItemType") // label for swagger
    @PostMapping("/itemtype/findItemType")
    public ItemType[] findItemType(@RequestBody SearchItemType searchItemType) throws Exception {
        return enterpriseSetupService.findItemType(searchItemType, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New ItemType") // label for swagger
    @RequestMapping(value = "/itemtype", method = RequestMethod.POST)
    public ResponseEntity<?> createItemType(@RequestBody ItemType newItemType, @RequestParam String loginUserID) {
        ItemType createdItemType = enterpriseSetupService.addItemType(newItemType, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdItemType, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update ItemType") // label for swagger
    @RequestMapping(value = "/itemtype/{itemTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateItemType(@PathVariable Long itemTypeId, @RequestParam String warehouseId, @RequestParam String companyId,
                                            @RequestParam String plantId, @RequestParam String languageId,
                                            @RequestBody ItemType updatedItemType, @RequestParam String loginUserID) {
        ItemType modifiedItemType = enterpriseSetupService.updateItemType(warehouseId, itemTypeId, companyId, plantId,
                languageId, updatedItemType, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedItemType, HttpStatus.OK);
    }

    @ApiOperation(response = ItemType.class, value = "Delete ItemType") // label for swagger
    @RequestMapping(value = "/itemtype/{itemTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemType(@PathVariable Long itemTypeId, @RequestParam String companyId, @RequestParam String plantId,
                                            @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam String loginUserID) {
        enterpriseSetupService.deleteItemType(warehouseId, itemTypeId, companyId, plantId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---PLANT------------------------------
     */
    @ApiOperation(response = Plant.class, value = "Get all Plant") // label for swagger
    @RequestMapping(value = "/plant", method = RequestMethod.GET)
    public ResponseEntity<?> getPlants() {
        Plant[] plant = enterpriseSetupService.getPlants(getAuthToken());
        return new ResponseEntity<>(plant, HttpStatus.OK);
    }

    @ApiOperation(response = Plant.class, value = "Get a Plant") // label for swagger
    @RequestMapping(value = "/plant/{plantId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlant(@PathVariable String plantId, @RequestParam String companyId,
                                      @RequestParam String languageId) {
        Plant plant = enterpriseSetupService.getPlant(plantId, companyId, languageId, getAuthToken());
        return new ResponseEntity<>(plant, HttpStatus.OK);
    }

    @ApiOperation(response = Plant.class, value = "Search Plant") // label for swagger
    @PostMapping("/plant/findPlant")
    public Plant[] findPlant(@RequestBody SearchPlant searchPlant)
            throws Exception {
        return enterpriseSetupService.findPlant(searchPlant, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New Plant") // label for swagger
    @RequestMapping(value = "/plant", method = RequestMethod.POST)
    public ResponseEntity<?> createPlant(@RequestBody Plant newPlant, @RequestParam String loginUserID) {
        Plant createdPlant = enterpriseSetupService.addPlant(newPlant, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdPlant, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update Plant") // label for swagger
    @RequestMapping(value = "/plant/{plantId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updatePlant(@PathVariable String plantId, @RequestParam String companyId, @RequestParam String languageId,
                                         @RequestBody Plant updatedPlant, @RequestParam String loginUserID) {
        Plant modifiedPlant = enterpriseSetupService.updatePlant(plantId, updatedPlant, companyId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedPlant, HttpStatus.OK);
    }

    @ApiOperation(response = Plant.class, value = "Delete Plant") // label for swagger
    @RequestMapping(value = "/plant/{plantId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePlant(@PathVariable String plantId, @RequestParam String companyId,
                                         @RequestParam String languageId, @RequestParam String loginUserID) {
        enterpriseSetupService.deletePlant(plantId, companyId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---STORAGEBINTYPE---------------------
     */
    @ApiOperation(response = StorageBinType.class, value = "Get all StorageBinType") // label for swagger
    @RequestMapping(value = "/storagebintype", method = RequestMethod.GET)
    public ResponseEntity<?> getStorageBinTypes() {
        StorageBinType[] storageBinType = enterpriseSetupService.getStorageBinTypes(getAuthToken());
        log.info("StorageBinType : " + storageBinType);
        return new ResponseEntity<>(storageBinType, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBinType.class, value = "Get a StorageBinType")
    @GetMapping("/storagebintype/{storageBinTypeId}")
    public ResponseEntity<?> getStorageBinType(@PathVariable Long storageBinTypeId, @RequestParam String warehouseId, @RequestParam Long storageClassId,
                                               @RequestParam Long storageTypeId, @RequestParam String companyId,
                                               @RequestParam String languageId, @RequestParam String plantId) {
        StorageBinType storagebintype =
                enterpriseSetupService.getStorageBinType(warehouseId, storageTypeId, storageClassId, storageBinTypeId, companyId, languageId, plantId, getAuthToken());
        log.info("StorageBinType : " + storagebintype);
        return new ResponseEntity<>(storagebintype, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBinType.class, value = "Search StorageBinType") // label for swagger
    @PostMapping("/storagebintype/findStorageBinType")
    public StorageBinType[] findStorageBinType(@RequestBody SearchStorageBinType searchStorageBinType) throws Exception {
        return enterpriseSetupService.findStorageBinType(searchStorageBinType, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New StorageBinType") // label for swagger
    @RequestMapping(value = "/storagebintype", method = RequestMethod.POST)
    public ResponseEntity<?> createStorageBinType(@RequestBody StorageBinType newStorageBinType, @RequestParam String loginUserID) {
        StorageBinType createdStorageBinType = enterpriseSetupService.addStorageBinType(newStorageBinType, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdStorageBinType, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update StorageBinType") // label for swagger
    @RequestMapping(value = "/storagebintype/{storageBinTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStorageBinType(@PathVariable Long storageBinTypeId, @RequestParam String companyId, @RequestParam Long storageClassId, @RequestParam String languageId, @RequestParam String plantId, @RequestParam String warehouseId,
                                                  @RequestParam Long storageTypeId, @RequestBody StorageBinType updatedStorageBinType, @RequestParam String loginUserID) {
        StorageBinType modifiedStorageBinType =
                enterpriseSetupService.updateStorageBinType(warehouseId, storageTypeId, storageClassId, storageBinTypeId, companyId, languageId, plantId,
                        updatedStorageBinType, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedStorageBinType, HttpStatus.OK);
    }

    @ApiOperation(response = StorageBinType.class, value = "Delete StorageBinType") // label for swagger
    @RequestMapping(value = "/storagebintype/{storageBinTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStorageBinType(@PathVariable Long storageBinTypeId, @RequestParam String warehouseId,
                                                  @RequestParam Long storageTypeId, @RequestParam String loginUserID, @RequestParam String companyId,
                                                  @RequestParam Long storageClassId, @RequestParam String languageId, @RequestParam String plantId) {
        enterpriseSetupService.deleteStorageBinType(warehouseId, storageTypeId, storageClassId, storageBinTypeId, companyId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---STORAGECLASS-----------------------
     */
    @ApiOperation(response = StorageClass.class, value = "Get all StorageClass") // label for swagger
    @RequestMapping(value = "/storageclass", method = RequestMethod.GET)
    public ResponseEntity<?> getStorageClasss() {
        StorageClass[] storageClass = enterpriseSetupService.getStorageClasss(getAuthToken());
        log.info("StorageClass : " + storageClass);
        return new ResponseEntity<>(storageClass, HttpStatus.OK);
    }

    @ApiOperation(response = StorageClass.class, value = "Get a StorageClass")
    @GetMapping("/storageclass/{storageClassId}")
    public ResponseEntity<?> getStorageClass(@PathVariable Long storageClassId, @RequestParam String warehouseId,
                                             @RequestParam String companyId, @RequestParam String languageId, @RequestParam String plantId) {
        StorageClass storageclass = enterpriseSetupService.getStorageClass(warehouseId, storageClassId, companyId, plantId, languageId, getAuthToken());
        log.info("StorageClass : " + storageclass);
        return new ResponseEntity<>(storageclass, HttpStatus.OK);
    }

    @ApiOperation(response = StorageClass.class, value = "Search StorageClass") // label for swagger
    @PostMapping("/storageclass/findStorageClass")
    public StorageClass[] findStorageClass(@RequestBody SearchStorageClass searchStorageClass) throws Exception {
        return enterpriseSetupService.findStorageClass(searchStorageClass, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New StorageClass") // label for swagger
    @RequestMapping(value = "/storageclass", method = RequestMethod.POST)
    public ResponseEntity<?> createStorageClass(@RequestBody StorageClass newStorageClass, @RequestParam String loginUserID) {
        StorageClass createdStorageClass = enterpriseSetupService.addStorageClass(newStorageClass, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdStorageClass, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update StorageClass") // label for swagger
    @RequestMapping(value = "/storageclass/{storageClassId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStorageClass(@PathVariable Long storageClassId, @RequestParam String warehouseId,
                                                @RequestParam String companyId, @RequestParam String plantId, @RequestParam String languageId,
                                                @RequestBody StorageClass updatedStorageClass, @RequestParam String loginUserID) {
        StorageClass modifiedStorageClass =
                enterpriseSetupService.updateStorageClass(warehouseId, storageClassId, companyId, languageId, plantId, updatedStorageClass, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedStorageClass, HttpStatus.OK);
    }

    @ApiOperation(response = StorageClass.class, value = "Delete StorageClass") // label for swagger
    @RequestMapping(value = "/storageclass/{storageClassId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStorageClass(@PathVariable Long storageClassId, @RequestParam String warehouseId, @RequestParam String companyId,
                                                @RequestParam String plantId, @RequestParam String languageId,
                                                @RequestParam String loginUserID) {
        enterpriseSetupService.deleteStorageClass(warehouseId, storageClassId, companyId, plantId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---STORAGESECTION---------------------
     */
    @ApiOperation(response = StorageSection.class, value = "Get all StorageSection") // label for swagger
    @RequestMapping(value = "/storagesection", method = RequestMethod.GET)
    public ResponseEntity<?> getStorageSections() {
        StorageSection[] storageSection = enterpriseSetupService.getStorageSections(getAuthToken());
        log.info("StorageSection : " + storageSection);
        return new ResponseEntity<>(storageSection, HttpStatus.OK);
    }

    @ApiOperation(response = StorageSection.class, value = "Get a StorageSection")
    @GetMapping("/storagesection/{storageSectionId}")
    public ResponseEntity<?> getStorageSection(@PathVariable String storageSectionId, @RequestParam String companyId, @RequestParam String plantId,
                                               @RequestParam String languageId, @RequestParam String warehouseId, @RequestParam Long floorId) {
        StorageSection storagesection = enterpriseSetupService.getStorageSection(warehouseId, floorId, storageSectionId, companyId, languageId, plantId, getAuthToken());
        log.info("StorageSection : " + storagesection);
        return new ResponseEntity<>(storagesection, HttpStatus.OK);
    }

    @ApiOperation(response = StorageSection.class, value = "Search StorageSection") // label for swagger
    @PostMapping("/storagesection/findStorageSection")
    public StorageSection[] findStorageSection(@RequestBody SearchStorageSection searchStorageSection) throws Exception {
        return enterpriseSetupService.findStorageSection(searchStorageSection, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New StorageSection") // label for swagger
    @RequestMapping(value = "/storagesection", method = RequestMethod.POST)
    public ResponseEntity<?> createStorageSection(@RequestBody StorageSection newStorageSection, @RequestParam String loginUserID) {
        StorageSection createdStorageSection = enterpriseSetupService.addStorageSection(newStorageSection, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdStorageSection, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update StorageSection") // label for swagger
    @RequestMapping(value = "/storagesection/{storageSectionId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStorageSection(@PathVariable String storageSectionId, @RequestParam String warehouseId,
                                                  @RequestParam Long floorId, @RequestBody StorageSection updatedStorageSection, @RequestParam String loginUserID,
                                                  @RequestParam String companyId, @RequestParam String languageId, @RequestParam String plantId) {
        StorageSection modifiedStorageSection =
                enterpriseSetupService.updateStorageSection(warehouseId, floorId, storageSectionId, companyId, languageId, plantId,
                        updatedStorageSection, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedStorageSection, HttpStatus.OK);
    }

    @ApiOperation(response = StorageSection.class, value = "Delete StorageSection") // label for swagger
    @RequestMapping(value = "/storagesection/{storageSectionId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStorageSection(@PathVariable String storageSectionId, @RequestParam String warehouseId,
                                                  @RequestParam Long floorId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestParam String plantId, @RequestParam String loginUserID) {
        enterpriseSetupService.deleteStorageSection(warehouseId, floorId, storageSectionId, companyId, plantId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---STORAGETYPE------------------------
     */
    @ApiOperation(response = StorageType.class, value = "Get all StorageType") // label for swagger
    @RequestMapping(value = "/storagetype", method = RequestMethod.GET)
    public ResponseEntity<?> getStorageTypes() {
        StorageType[] storagetype = enterpriseSetupService.getStorageTypes(getAuthToken());
        log.info("StorageType : " + storagetype);
        return new ResponseEntity<>(storagetype, HttpStatus.OK);
    }

    @ApiOperation(response = StorageType.class, value = "Get a StorageType")
    @GetMapping("/storagetype/{storageTypeId}")
    public ResponseEntity<?> getStorageType(@PathVariable Long storageTypeId, @RequestParam String warehouseId, @RequestParam String companyId,
                                            @RequestParam String languageId, @RequestParam String plantId,
                                            @RequestParam Long storageClassId) {
        StorageType storagetype = enterpriseSetupService.getStorageType(warehouseId, storageClassId, storageTypeId, companyId, languageId, plantId, getAuthToken());
        log.info("StorageType : " + storagetype);
        return new ResponseEntity<>(storagetype, HttpStatus.OK);
    }

    @ApiOperation(response = StorageType.class, value = "Search StorageType") // label for swagger
    @PostMapping("/storagetype/findStorageType")
    public StorageType[] findStorageType(@RequestBody SearchStorageType searchStorageType) throws Exception {
        return enterpriseSetupService.findStorageType(searchStorageType, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New StorageType") // label for swagger
    @RequestMapping(value = "/storagetype", method = RequestMethod.POST)
    public ResponseEntity<?> createStorageType(@RequestBody StorageType newStorageType, @RequestParam String loginUserID) {
        StorageType createdStorageType = enterpriseSetupService.addStorageType(newStorageType, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdStorageType, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update StorageType") // label for swagger
    @RequestMapping(value = "/storagetype/{storageTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStorageType(@PathVariable Long storageTypeId, @RequestParam String warehouseId,
                                               @RequestParam Long storageClassId, @RequestParam String companyId, @RequestParam String plantId,
                                               @RequestParam String languageId, @RequestBody StorageType updatedStorageType, @RequestParam String loginUserID) {
        StorageType modifiedStorageType =
                enterpriseSetupService.updateStorageType(warehouseId, storageClassId, storageTypeId, updatedStorageType, companyId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedStorageType, HttpStatus.OK);
    }

    @ApiOperation(response = StorageType.class, value = "Delete StorageType") // label for swagger
    @RequestMapping(value = "/storagetype/{storageTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStorageType(@PathVariable Long storageTypeId, @RequestParam String warehouseId, @RequestParam String companyId,
                                               @RequestParam String plantId, @RequestParam String languageId,
                                               @RequestParam Long storageClassId, @RequestParam String loginUserID) {
        enterpriseSetupService.deleteStorageType(warehouseId, storageClassId, storageTypeId, companyId, languageId, plantId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---STRATEGIES-------------------------
     */
    @ApiOperation(response = Strategies.class, value = "Get all Strategies") // label for swagger
    @RequestMapping(value = "/strategies", method = RequestMethod.GET)
    public ResponseEntity<?> getStrategiess() {
        Strategies[] strategies = enterpriseSetupService.getStrategiess(getAuthToken());
        log.info("Strategies : " + strategies);
        return new ResponseEntity<>(strategies, HttpStatus.OK);
    }

    @ApiOperation(response = Strategies.class, value = "Get a Strategies")
    @GetMapping("/strategies/get")
    public ResponseEntity<?> getStrategies(@RequestParam String warehouseId, @RequestParam String companyId,
                                           @RequestParam String languageId, @RequestParam String plantId) {

        Strategies[] strategies = enterpriseSetupService.getStrategies(companyId, languageId, plantId, warehouseId, getAuthToken());
        log.info("Strategies : " + strategies);
        return new ResponseEntity<>(strategies, HttpStatus.OK);
    }

    @ApiOperation(response = Strategies.class, value = "Search Strategies") // label for swagger
    @PostMapping("/strategies/findStrategies")
    public Strategies[] findStrategies(@RequestBody SearchStrategies searchStrategies) throws Exception {
        return enterpriseSetupService.findStrategies(searchStrategies, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New Strategies") // label for swagger
    @RequestMapping(value = "/strategies", method = RequestMethod.POST)
    public ResponseEntity<?> createStrategies(@RequestBody List<Strategies> newStrategies, @RequestParam String loginUserID) {
        Strategies[] createdStrategies = enterpriseSetupService.addStrategies(newStrategies, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdStrategies, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update Strategies") // label for swagger
    @RequestMapping(value = "/strategies/update", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateStrategies(@RequestParam String warehouseId,
                                              @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String plantId, @RequestBody List<Strategies> updatedStrategies,
                                              @RequestParam String loginUserID) {

        Strategies[] modifiedStrategies = enterpriseSetupService.updateStrategies(companyId, languageId, plantId,
                warehouseId, updatedStrategies, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedStrategies, HttpStatus.OK);
    }

    @ApiOperation(response = Strategies.class, value = "Delete Strategies") // label for swagger
    @RequestMapping(value = "/strategies/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStrategies(@RequestParam String warehouseId, @RequestParam String companyId,
                                              @RequestParam String languageId, @RequestParam String plantId,
                                              @RequestParam String loginUserID) {

        enterpriseSetupService.deleteStrategies(companyId, languageId, plantId, warehouseId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---VARIANT----------------------------
     */
    @ApiOperation(response = Variant.class, value = "Get all Variant") // label for swagger
    @RequestMapping(value = "/variant", method = RequestMethod.GET)
    public ResponseEntity<?> getVariants() {
        Variant[] variant = enterpriseSetupService.getVariants(getAuthToken());
        log.info("Variant : " + variant);
        return new ResponseEntity<>(variant, HttpStatus.OK);
    }

    @ApiOperation(response = Variant.class, value = "Get a Variant") // label for swagger
    @RequestMapping(value = "/variant/{variantId}", method = RequestMethod.GET)
    public ResponseEntity<?> getVariant(@PathVariable String variantId, @RequestParam String companyId,
                                        @RequestParam String plantId, @RequestParam String languageId, @RequestParam String variantSubId,
                                        @RequestParam Long levelId, @RequestParam String warehouseId) {
        Variant[] variant = enterpriseSetupService.getVariant(variantId, companyId, languageId, plantId, levelId, warehouseId, variantSubId, getAuthToken());
        log.info("Variant : " + variant);
        return new ResponseEntity<>(variant, HttpStatus.OK);
    }

    @ApiOperation(response = Variant.class, value = "Search Variant") // label for swagger
    @PostMapping("/variant/findVariant")
    public Variant[] findVariant(@RequestBody SearchVariant searchVariant) throws Exception {
        return enterpriseSetupService.findVariant(searchVariant, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New Variant") // label for swagger
    @RequestMapping(value = "/variant", method = RequestMethod.POST)
    public ResponseEntity<?> createVariant(@RequestBody List<AddVariant> newVariant, @RequestParam String loginUserID) {
        Variant[] createdVariant = enterpriseSetupService.addVariant(newVariant, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdVariant, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update Variant") // label for swagger
    @RequestMapping(value = "/variant/{variantId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateVariant(@PathVariable String variantId, @RequestParam String companyId,
                                           @RequestParam String plantId, @RequestParam String languageId,
                                           @RequestParam String warehouseId, @RequestParam Long levelId,
                                           @RequestParam String variantSubId, @RequestBody List<UpdateVariant> updatedVariant,
                                           @RequestParam String loginUserID) {

        Variant[] modifiedVariant = enterpriseSetupService.updateVariant(variantId, updatedVariant, companyId,
                plantId, warehouseId, languageId, levelId, variantSubId, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedVariant, HttpStatus.OK);
    }

    @ApiOperation(response = Variant.class, value = "Delete Variant") // label for swagger
    @RequestMapping(value = "/variant", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVariant(@RequestParam String companyId, @RequestParam String languageId, @RequestParam String plantId,
                                           @RequestParam String warehouseId, @RequestParam Long levelId, @RequestParam String variantId,
                                           @RequestParam String variantSubId, @RequestParam String loginUserID) {

        enterpriseSetupService.deleteVariant(variantId, companyId, plantId, warehouseId,
                variantSubId, languageId, levelId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * -----------------------------ENTERPRISE---WAREHOUSE--------------------------
     */
    @ApiOperation(response = Warehouse.class, value = "Get all Warehouse") // label for swagger
    @RequestMapping(value = "/warehouse", method = RequestMethod.GET)
    public ResponseEntity<?> getWarehouses() {
        Warehouse[] warehouse = enterpriseSetupService.getWarehouses(getAuthToken());
        log.info("Warehouse : " + warehouse);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @ApiOperation(response = Warehouse.class, value = "Get a Warehouse") // label for swagger
    @RequestMapping(value = "/warehouse/{warehouseId}", method = RequestMethod.GET)
    public ResponseEntity<?> getWarehouse(@PathVariable String warehouseId, @RequestParam String modeOfImplementation,
                                          @RequestParam String companyId, @RequestParam String plantId, @RequestParam String languageId,
                                          @RequestParam Long warehouseTypeId) {
        Warehouse warehouse = enterpriseSetupService.getWarehouse(warehouseId, modeOfImplementation, warehouseTypeId, companyId, plantId, languageId, getAuthToken());
        log.info("Warehouse : " + warehouse);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @ApiOperation(response = Warehouse.class, value = "Search Warehouse") // label for swagger
    @PostMapping("/warehouse/findWarehouse")
    public Warehouse[] findWarehouse(@RequestBody SearchWarehouse searchWarehouse) throws Exception {
        return enterpriseSetupService.findWarehouse(searchWarehouse, getAuthToken());
    }

    @ApiOperation(response = Optional.class, value = "Create New Warehouse") // label for swagger
    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    public ResponseEntity<?> createWarehouse(@RequestBody Warehouse newWarehouse, @RequestParam String loginUserID) {
        Warehouse createdWarehouse = enterpriseSetupService.addWarehouse(newWarehouse, loginUserID, getAuthToken());
        return new ResponseEntity<>(createdWarehouse, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Update Warehouse") // label for swagger
    @RequestMapping(value = "/warehouse/{warehouseId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateWarehouse(@PathVariable String warehouseId, @RequestParam String modeOfImplementation, @RequestParam Long warehouseTypeId,
                                             @RequestParam String companyId, @RequestParam String plantId, @RequestParam String languageId,
                                             @RequestBody Warehouse updatedWarehouse, @RequestParam String loginUserID) {
        Warehouse modifiedWarehouse = enterpriseSetupService.updateWarehouse(warehouseId, modeOfImplementation, warehouseTypeId, companyId,
                plantId, languageId, updatedWarehouse, loginUserID, getAuthToken());
        return new ResponseEntity<>(modifiedWarehouse, HttpStatus.OK);
    }

    @ApiOperation(response = Warehouse.class, value = "Delete Warehouse") // label for swagger
    @RequestMapping(value = "/warehouse/{warehouseId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWarehouse(@PathVariable String warehouseId, @RequestParam String modeOfImplementation,
                                             @RequestParam Long warehouseTypeId, @RequestParam String companyId,
                                             @RequestParam String plantId, @RequestParam String languageId, @RequestParam String loginUserID) {
        enterpriseSetupService.deleteWarehouse(warehouseId, modeOfImplementation, warehouseTypeId, companyId, plantId, languageId, loginUserID, getAuthToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}