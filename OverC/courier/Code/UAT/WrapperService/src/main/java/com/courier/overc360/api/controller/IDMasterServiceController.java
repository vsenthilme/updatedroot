package com.courier.overc360.api.controller;

import com.courier.overc360.api.exception.BadRequestException;
import com.courier.overc360.api.exception.CustomErrorResponse;
import com.courier.overc360.api.model.idmaster.Module;
import com.courier.overc360.api.model.idmaster.*;
import com.courier.overc360.api.model.user.AddUserManagement;
import com.courier.overc360.api.model.user.FindUserManagement;
import com.courier.overc360.api.model.user.UpdateUserManagement;
import com.courier.overc360.api.model.user.UserManagement;
import com.courier.overc360.api.service.IDMasterService;
import com.opencsv.exceptions.CsvException;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/overc-idmaster-service")
@Api(tags = {"IDMaster Service"}, value = "IDMaster Service Operations") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operations related to IDMaster Service")})
public class IDMasterServiceController {

    @Autowired
    IDMasterService idmasterService;


    //==============================================NumberRange========================================================
    // Get All NumberRangeCode Details
    @ApiOperation(response = NumberRange[].class, value = "Get all NumberRange details") // label for swagger
    @GetMapping("/numberRange")
    public ResponseEntity<?> getAllNumberRangeDetails(@RequestParam String authToken) {
        NumberRange[] numberRanges = idmasterService.getAllNumberRanges(authToken);
        return new ResponseEntity<>(numberRanges, HttpStatus.OK);
    }

    // Get a NumberRange
    @ApiOperation(response = NumberRange.class, value = "Get a NumberRange") // label for swagger
    @GetMapping("/numberRange/{numberRangeCode}")
    public ResponseEntity<?> getCompany(@PathVariable Long numberRangeCode, @RequestParam String languageId, @RequestParam String authToken) {
        NumberRange numberRange = idmasterService.getNumberRange(languageId, numberRangeCode, authToken);
        return new ResponseEntity<>(numberRange, HttpStatus.OK);
    }

    // Create new NumberRange
    @ApiOperation(response = NumberRange.class, value = "Create new NumberRange") // label for swagger
    @PostMapping("/numberRange")
    public ResponseEntity<?> postNumberRange(@RequestBody AddNumberRange addNumberRange,
                                             @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        NumberRange createdNumberRange = idmasterService.createNumberRange(addNumberRange, loginUserID, authToken);
        return new ResponseEntity<>(createdNumberRange, HttpStatus.OK);
    }

    // Update NumberRange
    @ApiOperation(response = NumberRange.class, value = "Update NumberRange") // label for swagger
    @PatchMapping("/numberRange/{numberRangeCode}")
    public ResponseEntity<?> patchNumberRange(@PathVariable Long numberRangeCode, @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestBody UpdateNumberRange updateNumberRange,
                                              @RequestParam String numberRangeObject, @RequestParam String authToken) {
        NumberRange updatedNumberRange = idmasterService.updateNumberRange(languageId, numberRangeCode, loginUserID, numberRangeObject, updateNumberRange, authToken);
        return new ResponseEntity<>(updatedNumberRange, HttpStatus.OK);
    }

    // Delete NumberRange
    @ApiOperation(response = NumberRange.class, value = "Delete NumberRange") // label for swagger
    @DeleteMapping("/numberRange/{numberRangeCode}")
    public ResponseEntity<?> deleteNumberRange(@PathVariable Long numberRangeCode, @RequestParam String languageId,
                                               @RequestParam String numberRangeObject, @RequestParam String loginUserID,
                                               @RequestParam String authToken) {
        idmasterService.deleteNumberRange(languageId, numberRangeCode, numberRangeObject, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find NumberRange
    @ApiOperation(response = NumberRange[].class, value = "Find NumberRange") // label for swagger
    @PostMapping("/numberRange/find")
    public NumberRange[] findNumberRange(@RequestBody FindNumberRange findNumberRange,
                                         @RequestParam String authToken) throws Exception {
        return idmasterService.findNumberRange(findNumberRange, authToken);
    }

    //=================================================ErrorLog========================================================
    // Get All ErrorLog Details
    @ApiOperation(response = ErrorLog[].class, value = "Get all ErrorLogs") // label for Swagger
    @GetMapping("/errorLog/getAll")
    public ResponseEntity<?> getAllErrorLogs(@RequestParam String authToken) {
        ErrorLog[] errorLogs = idmasterService.getAllErrorLogs(authToken);
        return new ResponseEntity<>(errorLogs, HttpStatus.OK);
    }

    // Find ErrorLog
    @ApiOperation(response = ErrorLog[].class, value = "Find ErrorLog") //label for swagger
    @PostMapping("/errorLog/find")
    public ErrorLog[] findErrorLogs(@RequestBody FindErrorLog findErrorLog,
                                    @RequestParam String authToken) throws Exception {
        return idmasterService.findErrorLogs(findErrorLog, authToken);
    }

    //===============================================Language==========================================================
    // Get All Language Details
    @ApiOperation(response = Language[].class, value = "Get all Language details") // label for swagger
    @GetMapping("/language")
    public ResponseEntity<?> getAllLanguages(@RequestParam String authToken) {
        Language[] languages = idmasterService.getAllLanguages(authToken);
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    // Get Language
    @ApiOperation(response = Language.class, value = "Get a Language") // label for swagger
    @GetMapping("/language/{languageId}")
    public ResponseEntity<?> getLanguage(@PathVariable String languageId, @RequestParam String authToken) {
        Language dbLanguage = idmasterService.getLanguage(languageId, authToken);
        return new ResponseEntity<>(dbLanguage, HttpStatus.OK);
    }

    // Create Language
    @ApiOperation(response = Language.class, value = "Create new Language") // label for swagger
    @PostMapping("/language")
    public ResponseEntity<?> postLanguage(@RequestBody AddLanguage newLanguage,
                                          @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Language createdLanguage = idmasterService.createLanguage(newLanguage, loginUserID, authToken);
        return new ResponseEntity<>(createdLanguage, HttpStatus.OK);
    }

    // Update Language
    @ApiOperation(response = Language.class, value = "Update Language") // label for swagger
    @PatchMapping("/language/{languageId}")
    public ResponseEntity<?> updateLanguage(@PathVariable String languageId, @RequestParam String loginUserID, @RequestBody UpdateLanguage updateLanguage,
                                            @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        Language updatedLanguage = idmasterService.updateLanguage(languageId, loginUserID, updateLanguage, authToken);
        return new ResponseEntity<>(updatedLanguage, HttpStatus.OK);
    }

    // Delete Language
    @ApiOperation(response = Language.class, value = "Delete Language") // label for swagger
    @DeleteMapping("/language/{languageId}")
    public ResponseEntity<?> deleteLanguage(@PathVariable String languageId,
                                            @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteLanguage(languageId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Language
    @ApiOperation(response = Language[].class, value = "Find Language") //label for swagger
    @PostMapping("/language/find")
    public Language[] findLanguages(@RequestBody FindLanguage findLanguage,
                                    @RequestParam String authToken) throws Exception {
        return idmasterService.findLanguages(findLanguage, authToken);
    }

    //===============================================Company==========================================================
    // Get All Company Details
    @ApiOperation(response = Company[].class, value = "Get all Company details") // label for swagger
    @GetMapping("/company")
    public ResponseEntity<?> getAllCompanies(@RequestParam String authToken) {
        Company[] companies = idmasterService.getAllCompanies(authToken);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    // Get Company
    @ApiOperation(response = Company.class, value = "Get a Company") // label for swagger
    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getCompany(@PathVariable String companyId, @RequestParam String languageId, @RequestParam String authToken) {
        Company dbCompany = idmasterService.getCompany(languageId, companyId, authToken);
        return new ResponseEntity<>(dbCompany, HttpStatus.OK);
    }

    // Create Company
    @ApiOperation(response = Company.class, value = "Create new Company") // label for swagger
    @PostMapping("/company")
    public ResponseEntity<?> postCompany(@RequestBody AddCompany addCompany, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Company createdCompany = idmasterService.createCompany(addCompany, loginUserID, authToken);
        return new ResponseEntity<>(createdCompany, HttpStatus.OK);
    }

    // Update Company
    @ApiOperation(response = Company.class, value = "Update Company") // label for swagger
    @PatchMapping("/company/{companyId}")
    public ResponseEntity<?> patchCompany(@PathVariable String companyId, @RequestParam String languageId,
                                          @RequestParam String loginUserID, @RequestBody UpdateCompany updateCompany,
                                          @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        Company updatedCompany = idmasterService.updateCompany(languageId, companyId, loginUserID, updateCompany, authToken);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    // Delete Company
    @ApiOperation(response = Company.class, value = "Delete Company") // label for swagger
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable String companyId, @RequestParam String languageId,
                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCompany(languageId, companyId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Company
    @ApiOperation(response = Company[].class, value = "Find Company")//label for swagger
    @PostMapping("/company/find")
    public Company[] findCompany(@RequestBody FindCompany findCompany,
                                 @RequestParam String authToken) throws Exception {
        return idmasterService.findCompany(findCompany, authToken);
    }

    //===============================================SubProduct========================================================
    // Get all SubProduct Details
    @ApiOperation(response = SubProduct.class, value = "Get All SubProduct Details") // label for swagger
    @GetMapping("/subProduct")
    public ResponseEntity<?> getAllSubProductDetails(@RequestParam String authToken) {
        SubProduct[] subProducts = idmasterService.getAllSubProducts(authToken);
        return new ResponseEntity<>(subProducts, HttpStatus.OK);
    }

    // Get SubProduct
    @ApiOperation(response = SubProduct.class, value = "Get SubProduct") // label for swagger
    @GetMapping("/subProduct/{subProductId}")
    public ResponseEntity<?> getSubProduct(@PathVariable String subProductId, @RequestParam String companyId, @RequestParam String subProductValue,
                                           @RequestParam String languageId, @RequestParam String authToken) {
        SubProduct subProduct = idmasterService.getSubProduct(languageId, companyId, subProductId, subProductValue, authToken);
        return new ResponseEntity<>(subProduct, HttpStatus.OK);
    }

    // Create SubProduct
    @ApiOperation(response = SubProduct.class, value = "Create new SubProduct") // label for swagger
    @PostMapping("/subProduct")
    public ResponseEntity<?> postSubProduct(@RequestBody AddSubProduct addSubProduct,
                                            @RequestParam String loginUserID, @RequestParam String authToken) {
        SubProduct subProduct = idmasterService.createSubProduct(addSubProduct, loginUserID, authToken);
        return new ResponseEntity<>(subProduct, HttpStatus.OK);
    }

    // Update SubProduct
    @ApiOperation(response = SubProduct.class, value = "Update SubProduct") // label for swagger
    @PatchMapping("/subProduct/{subProductId}")
    public ResponseEntity<?> patchSubProduct(@PathVariable String subProductId, @RequestParam String languageId, @RequestParam String subProductValue,
                                             @RequestParam String loginUserID, @RequestBody UpdateSubProduct updateSubProduct,
                                             @RequestParam String companyId, @RequestParam String authToken) {
        SubProduct subProduct = idmasterService.updateSubProduct(languageId, companyId, subProductId, subProductValue,
                updateSubProduct, loginUserID, authToken);
        return new ResponseEntity<>(subProduct, HttpStatus.OK);
    }

    // Delete SubProduct
    @ApiOperation(response = SubProduct.class, value = "Delete SubProduct") // label for swagger
    @DeleteMapping("/subProduct/{subProductId}")
    public ResponseEntity<?> deleteSubProduct(@PathVariable String subProductId, @RequestParam String languageId, @RequestParam String loginUserID,
                                              @RequestParam String companyId, @RequestParam String subProductValue, @RequestParam String authToken) {
        idmasterService.deleteSubProduct(languageId, companyId, subProductId, subProductValue, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find SubProduct
    @ApiOperation(response = SubProduct[].class, value = "Find SubProduct") //label for swagger
    @PostMapping("/subProduct/find")
    public SubProduct[] findSubProducts(@RequestBody FindSubProduct findSubProduct, @RequestParam String authToken) throws Exception {
        return idmasterService.findSubProducts(findSubProduct, authToken);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create SubProducts - bulk
    @ApiOperation(response = SubProduct[].class, value = "Create new SubProducts - bulk") // label for swagger
    @PostMapping("/subProduct/create/list")
    public ResponseEntity<?> postSubProductBulk(@RequestBody List<AddSubProduct> addSubProductList,
                                                @RequestParam String loginUserID, @RequestParam String authToken) {
        SubProduct[] subProducts = idmasterService.createSubProductBulk(addSubProductList, loginUserID, authToken);
        return new ResponseEntity<>(subProducts, HttpStatus.OK);
    }

    // Update SubProducts - bulk
    @ApiOperation(response = SubProduct[].class, value = "Update SubProducts - bulk") // label for swagger
    @PatchMapping("/subProduct/update/list")
    public ResponseEntity<?> patchSubProductBulk(@RequestBody List<UpdateSubProduct> updateSubProductList,
                                                 @RequestParam String loginUserID, @RequestParam String authToken) {
        SubProduct[] subProducts = idmasterService.updateSubProductBulk(updateSubProductList, loginUserID, authToken);
        return new ResponseEntity<>(subProducts, HttpStatus.OK);
    }

    // Delete SubProducts - bulk
    @ApiOperation(response = SubProduct.class, value = "Delete SubProducts - bulk") // label for swagger
    @PostMapping("/subProduct/delete/list")
    public ResponseEntity<?> deleteSubProductBulk(@RequestBody List<SubProductDeleteInput> deleteInputs,
                                                  @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteSubProductBulk(deleteInputs, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //================================================Product==========================================================
    // Get all Product Details
    @ApiOperation(response = Product.class, value = "Get All Product Details") // label for swagger
    @GetMapping("/product")
    public ResponseEntity<?> getAllProductDetails(@RequestParam String authToken) {
        Product[] products = idmasterService.getAllProducts(authToken);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get a Product
    @ApiOperation(response = Product.class, value = "Get Product") // label for swagger
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId, @RequestParam String companyId, @RequestParam String subProductId,
                                        @RequestParam String languageId, @RequestParam String authToken) {
        Product product = idmasterService.getProduct(languageId, companyId, subProductId, productId, authToken);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Create Product
    @ApiOperation(response = Product.class, value = "Create new Product") // label for swagger
    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody AddProduct addProduct, @RequestParam String loginUserID, @RequestParam String authToken) {
        Product product = idmasterService.createProduct(addProduct, loginUserID, authToken);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Update Product
    @ApiOperation(response = Product.class, value = "Update Product") // label for swagger
    @PatchMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestParam String languageId, @RequestParam String subProductId,
                                           @RequestParam String loginUserID, @RequestBody UpdateProduct updateProduct,
                                           @RequestParam String companyId, @RequestParam String authToken) {
        Product product = idmasterService.updateProduct(languageId, companyId, subProductId, productId, updateProduct, loginUserID, authToken);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Delete Product
    @ApiOperation(response = Product.class, value = "Delete Product") // label for swagger
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId, @RequestParam String languageId, @RequestParam String loginUserID,
                                           @RequestParam String subProductId, @RequestParam String companyId, @RequestParam String authToken) {
        idmasterService.deleteProduct(languageId, companyId, subProductId, productId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Product
    @ApiOperation(response = Product[].class, value = "Find Product") //label for swagger
    @PostMapping("/product/find")
    public Product[] findProducts(@RequestBody FindProduct findProduct, @RequestParam String authToken) throws Exception {
        return idmasterService.findProducts(findProduct, authToken);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Products - bulk
    @ApiOperation(response = Product.class, value = "Create new Products - bulk") // label for swagger
    @PostMapping("/product/create/list")
    public ResponseEntity<?> postProductBulk(@RequestBody List<AddProduct> addProductList,
                                             @RequestParam String loginUserID, @RequestParam String authToken) {
        Product[] products = idmasterService.createProductBulk(addProductList, loginUserID, authToken);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Update Products - bulk
    @ApiOperation(response = Product.class, value = "Update Products - bulk") // label for swagger
    @PatchMapping("/product/update/list")
    public ResponseEntity<?> patchProductBulk(@RequestBody List<UpdateProduct> updateProductList,
                                              @RequestParam String loginUserID, @RequestParam String authToken) {
        Product[] products = idmasterService.updateProductBulk(updateProductList, loginUserID, authToken);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Delete Products - bulk
    @ApiOperation(response = Product.class, value = "Delete Products - bulk") // label for swagger
    @PostMapping("/product/delete/list")
    public ResponseEntity<?> deleteProductBulk(@RequestBody List<ProductDeleteInput> deleteInputs,
                                               @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteProductBulk(deleteInputs, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //==================================================ServiceType====================================================
    // Get All ServiceType Details
    @ApiOperation(response = ServiceType[].class, value = "Get all ServiceType details") // label for swagger
    @GetMapping("/serviceType")
    public ResponseEntity<?> getAllServiceTypes(@RequestParam String authToken) {
        ServiceType[] userServiceType = idmasterService.getAllServiceTypes(authToken);
        return new ResponseEntity<>(userServiceType, HttpStatus.OK);
    }

    // Get ServiceType
    @ApiOperation(response = ServiceType.class, value = "Get ServiceType") // label for swagger
    @GetMapping("/serviceType/{serviceTypeId}")
    public ResponseEntity<?> getServiceType(@PathVariable String serviceTypeId, @RequestParam String languageId,
                                            @RequestParam String companyId, @RequestParam String authToken) {
        ServiceType dbServiceType = idmasterService.getServiceType(companyId, languageId, serviceTypeId, authToken);
        return new ResponseEntity<>(dbServiceType, HttpStatus.OK);
    }


    // Create new ServiceType
    @ApiOperation(response = ServiceType.class, value = "Create new ServiceType") // label for swagger
    @PostMapping("/serviceType")
    public ResponseEntity<?> postServiceType(@RequestBody AddServiceType newServiceType,
                                             @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ServiceType createServiceType = idmasterService.createServiceType(newServiceType, loginUserID, authToken);
        return new ResponseEntity<>(createServiceType, HttpStatus.OK);
    }

    // Update ServiceType
    @ApiOperation(response = ServiceType.class, value = "Update ServiceType") // label for swagger
    @PatchMapping("/serviceType/{serviceTypeId}")
    public ResponseEntity<?> updateServiceType(@PathVariable String serviceTypeId, @RequestParam String languageId,
                                               @RequestParam String companyId, @RequestParam String loginUserID,
                                               @RequestBody UpdateServiceType updateServiceType, @RequestParam String authToken) {
        ServiceType updatedServiceType = idmasterService.updateServiceType(companyId, languageId, serviceTypeId, loginUserID, updateServiceType, authToken);
        return new ResponseEntity<>(updatedServiceType, HttpStatus.OK);
    }

    // Delete ServiceType
    @ApiOperation(response = ServiceType.class, value = "Delete ServiceType") // label for swagger
    @DeleteMapping("/serviceType/{serviceTypeId}")
    public ResponseEntity<?> deleteServiceType(@PathVariable String serviceTypeId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteServiceType(companyId, languageId, serviceTypeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ServiceType
    @ApiOperation(response = ServiceType[].class, value = "Find ServiceType")//label for swagger
    @PostMapping("/serviceType/find")
    public ServiceType[] findServiceType(@RequestBody FindServicetype findServiceType,
                                         @RequestParam String authToken) throws Exception {
        return idmasterService.findServiceType(findServiceType, authToken);
    }

    //==============================================ConsignmentType====================================================
    // Get All ConsignmentType Details
    @ApiOperation(response = ConsignmentType[].class, value = "Get all ConsignmentType details") // label for swagger
    @GetMapping("/consignmentType")
    public ResponseEntity<?> getConsignmentTypes(@RequestParam String authToken) {
        ConsignmentType[] userConsignmentType = idmasterService.getConsignmentTypes(authToken);
        return new ResponseEntity<>(userConsignmentType, HttpStatus.OK);
    }

    // Get ConsignmentType
    @ApiOperation(response = ConsignmentType.class, value = "Get ConsignmentType") // label for swagger
    @GetMapping("/consignmentType/{consignmentTypeId}")
    public ResponseEntity<?> getConsignmentType(@PathVariable String consignmentTypeId, @RequestParam String languageId,
                                                @RequestParam String companyId, @RequestParam String authToken) {
        ConsignmentType dbConsignmentType = idmasterService.getConsignmentType(companyId, languageId, consignmentTypeId, authToken);
        return new ResponseEntity<>(dbConsignmentType, HttpStatus.OK);
    }

    // Create new ConsignmentType
    @ApiOperation(response = ConsignmentType.class, value = "Create new ConsignmentType") // label for swagger
    @PostMapping("/consignmentType")
    public ResponseEntity<?> postConsignmentType(@RequestBody AddConsignmentType newConsignmentType,
                                                 @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ConsignmentType createConsignmentType = idmasterService.createConsignmentType(newConsignmentType, loginUserID, authToken);
        return new ResponseEntity<>(createConsignmentType, HttpStatus.OK);
    }

    // Update ConsignmentType
    @ApiOperation(response = ConsignmentType.class, value = "Update ConsignmentType") // label for swagger
    @RequestMapping(value = "/consignmentType/{consignmentTypeId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateConsignmentType(@PathVariable String consignmentTypeId, @RequestParam String languageId,
                                                   @RequestParam String companyId, @RequestParam String loginUserID,
                                                   @RequestBody UpdateConsignmentType updateConsignmentType, @RequestParam String authToken) {
        ConsignmentType UpdateConsignmentType =
                idmasterService.updateConsignmentType(companyId, languageId, consignmentTypeId, loginUserID, updateConsignmentType, authToken);
        return new ResponseEntity<>(UpdateConsignmentType, HttpStatus.OK);
    }

    // Delete ConsignmentType
    @ApiOperation(response = ConsignmentType.class, value = "Delete ConsignmentType") // label for swagger
    @DeleteMapping("/consignmentType/{consignmentTypeId}")
    public ResponseEntity<?> deleteConsignmentType(@PathVariable String consignmentTypeId, @RequestParam String languageId, @RequestParam String companyId,
                                                   @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteConsignmentType(companyId, languageId, consignmentTypeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ConsignmentType
    @ApiOperation(response = ConsignmentType[].class, value = "Find ConsignmentType")//label for swagger
    @PostMapping("/consignmentType/find")
    public ConsignmentType[] findConsignmentType(@RequestBody FindConsignmentType findConsignmentType,
                                                 @RequestParam String authToken) throws Exception {
        return idmasterService.findConsignmentType(findConsignmentType, authToken);
    }

    //================================================LoadType=========================================================
    // Get All LoadType Details
    @ApiOperation(response = LoadType.class, value = "Get All LoadType Details") // label for swagger
    @GetMapping("/loadType")
    public ResponseEntity<?> getAllLoadTypeDetails(@RequestParam String authToken) {
        LoadType[] loadTypes = idmasterService.getAllLoadTypes(authToken);
        return new ResponseEntity<>(loadTypes, HttpStatus.OK);
    }

    // Get LoadType
    @ApiOperation(response = LoadType.class, value = "Get LoadType") // label for swagger
    @GetMapping("/loadType/{loadTypeId}")
    public ResponseEntity<?> getLoadType(@PathVariable String loadTypeId, @RequestParam String companyId,
                                         @RequestParam String languageId, @RequestParam String authToken) {
        LoadType loadType = idmasterService.getLoadType(languageId, companyId, loadTypeId, authToken);
        return new ResponseEntity<>(loadType, HttpStatus.OK);
    }

    // Create LoadType
    @ApiOperation(response = LoadType.class, value = "Create new LoadType") // label for swagger
    @PostMapping("/loadType")
    public ResponseEntity<?> createLoadType(@RequestBody AddLoadType addLoadType, @RequestParam String loginUserID, @RequestParam String authToken) {
        LoadType loadType = idmasterService.createLoadType(addLoadType, loginUserID, authToken);
        return new ResponseEntity<>(loadType, HttpStatus.OK);
    }

    // Update LoadType
    @ApiOperation(response = LoadType.class, value = "Update LoadType") // label for swagger
    @PatchMapping("/loadType/{loadTypeId}")
    public ResponseEntity<?> updateLoadType(@PathVariable String loadTypeId, @RequestParam String languageId,
                                            @RequestParam String loginUserID, @RequestBody UpdateLoadType updateLoadType,
                                            @RequestParam String companyId, @RequestParam String authToken) {
        LoadType loadType = idmasterService.updateLoadType(languageId, companyId, loadTypeId, updateLoadType, loginUserID, authToken);
        return new ResponseEntity<>(loadType, HttpStatus.OK);
    }

    // Delete LoadType
    @ApiOperation(response = LoadType.class, value = "Delete LoadType") // label for swagger
    @DeleteMapping("/loadType/{loadTypeId}")
    public ResponseEntity<?> deleteLoadType(@PathVariable String loadTypeId, @RequestParam String languageId, @RequestParam String loginUserID,
                                            @RequestParam String companyId, @RequestParam String authToken) {
        idmasterService.deleteLoadType(languageId, companyId, loadTypeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find LoadType
    @ApiOperation(response = LoadType[].class, value = "Find LoadType") //label for swagger
    @PostMapping("/loadType/find")
    public LoadType[] findLoadTypes(@RequestBody FindLoadType findLoadType, @RequestParam String authToken) throws Exception {
        return idmasterService.findLoadTypes(findLoadType, authToken);
    }

    //================================================Country==========================================================
    // Get Country
    @ApiOperation(response = Country.class, value = "Get All Country Details") // label for swagger
    @GetMapping("/country")
    public ResponseEntity<?> getAllCountryDetails(@RequestParam String authToken) {
        Country[] countries = idmasterService.getAllCountries(authToken);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    // Get Country
    @ApiOperation(response = Country.class, value = "Get Country") // label for swagger
    @GetMapping("/country/{countryId}")
    public ResponseEntity<?> getCountry(@PathVariable String countryId, @RequestParam String languageId,
                                        @RequestParam String companyId, @RequestParam String authToken) {
        Country country = idmasterService.getCountry(languageId, companyId, countryId, authToken);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    // Create Country
    @ApiOperation(response = Country.class, value = "Create new Country") // label for swagger
    @PostMapping("/country")
    public ResponseEntity<?> createCountry(@RequestBody AddCountry addCountry, @RequestParam String loginUserID, @RequestParam String authToken) {
        Country country = idmasterService.createCountry(addCountry, loginUserID, authToken);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    // Update Country
    @ApiOperation(response = Country.class, value = "Update Country") // label for swagger
    @PatchMapping("/country/{countryId}")
    public ResponseEntity<?> updateCountry(@PathVariable String countryId, @RequestParam String languageId, @RequestParam String loginUserID,
                                           @RequestParam String companyId, @RequestBody UpdateCountry updateCountry, @RequestParam String authToken) {
        Country country = idmasterService.updateCountry(languageId, companyId, countryId, updateCountry, loginUserID, authToken);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    // Delete Country
    @ApiOperation(response = Country.class, value = "Delete Country") // label for swagger
    @DeleteMapping("/country/{countryId}")
    public ResponseEntity<?> deleteCountry(@PathVariable String countryId, @RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCountry(languageId, companyId, countryId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Country
    @ApiOperation(response = Country[].class, value = "Find Country") //label for swagger
    @PostMapping("/country/find")
    public Country[] findCountries(@RequestBody FindCountry findCountry, @RequestParam String authToken) throws Exception {
        return idmasterService.findCountries(findCountry, authToken);
    }

    //==================================================Province=======================================================
    // Get All Province Details
    @ApiOperation(response = Province[].class, value = "Get all Province Details") // label for swagger
    @GetMapping("/province")
    public ResponseEntity<?> getAllProvinceDetails(@RequestParam String authToken) {
        Province[] provinceList = idmasterService.getAllProvinces(authToken);
        return new ResponseEntity<>(provinceList, HttpStatus.OK);
    }

    // Get Province
    @ApiOperation(response = Province.class, value = "Get a Province") // label for swagger
    @GetMapping("/province/{provinceId}")
    public ResponseEntity<?> getProvince(@PathVariable String provinceId, @RequestParam String countryId, @RequestParam String companyId,
                                         @RequestParam String languageId, @RequestParam String authToken) {
        Province dbProvince = idmasterService.getProvince(languageId, companyId, countryId, provinceId, authToken);
        return new ResponseEntity<>(dbProvince, HttpStatus.OK);
    }

    // Create new Province
    @ApiOperation(response = Province.class, value = "Create new Province") // label for swagger
    @PostMapping("/province")
    public ResponseEntity<?> postProvince(@RequestBody AddProvince addProvince, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Province newProvince = idmasterService.createProvince(addProvince, loginUserID, authToken);
        return new ResponseEntity<>(newProvince, HttpStatus.OK);
    }

    // Update Province
    @ApiOperation(response = Province.class, value = "Update Province") // label for swagger
    @PatchMapping("/province/{provinceId}")
    public ResponseEntity<?> patchProvince(@PathVariable String provinceId, @RequestParam String countryId, @RequestParam String languageId,
                                           @RequestParam String companyId, @RequestParam String loginUserID,
                                           @RequestBody UpdateProvince updateProvince, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Province updatedProvince = idmasterService.updateProvince(languageId, companyId, countryId, provinceId, updateProvince, loginUserID, authToken);
        return new ResponseEntity<>(updatedProvince, HttpStatus.OK);
    }

    // Delete Province
    @ApiOperation(response = Province.class, value = "Delete Province") // label for swagger
    @DeleteMapping("/province/{provinceId}")
    public ResponseEntity<?> deleteProvince(@PathVariable String provinceId, @RequestParam String countryId, @RequestParam String languageId,
                                            @RequestParam String companyId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteProvince(languageId, companyId, countryId, provinceId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Province
    @ApiOperation(response = Province[].class, value = "Find Province") // label for swagger
    @PostMapping("/province/find")
    public ResponseEntity<?> findProvince(@RequestBody FindProvince findProvince, @RequestParam String authToken) throws Exception {
        Province[] provinceList = idmasterService.findProvinces(findProvince, authToken);
        return new ResponseEntity<>(provinceList, HttpStatus.OK);
    }

    //===================================================City==========================================================
    // Get all City Details
    @ApiOperation(response = City.class, value = "Get All City Details") // label for swagger
    @GetMapping("/city")
    public ResponseEntity<?> getAllCityDetails(@RequestParam String authToken) {
        City[] city = idmasterService.getAllCities(authToken);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // Get City
    @ApiOperation(response = City.class, value = "Get City") // label for swagger
    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> getCity(@PathVariable String cityId, @RequestParam String languageId, @RequestParam String companyId,
                                     @RequestParam String countryId, @RequestParam String provinceId,
                                     @RequestParam String districtId, @RequestParam String authToken) {
        City city = idmasterService.getCity(languageId, companyId, countryId, provinceId, districtId, cityId, authToken);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // Create City
    @ApiOperation(response = City.class, value = "Create new City") // label for swagger
    @PostMapping("/city")
    public ResponseEntity<?> createCity(@RequestBody AddCity addCity, @RequestParam String loginUserID, @RequestParam String authToken) {
        City city = idmasterService.createCity(addCity, loginUserID, authToken);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // Update City
    @ApiOperation(response = City.class, value = "Update City") // label for swagger
    @PatchMapping("/city/{cityId}")
    public ResponseEntity<?> updateCity(@PathVariable String cityId, @RequestParam String languageId, @RequestParam String companyId,
                                        @RequestParam String countryId, @RequestParam String provinceId,
                                        @RequestParam String districtId, @RequestParam String loginUserID,
                                        @RequestBody UpdateCity updateCity, @RequestParam String authToken) {
        City city = idmasterService.updateCity(languageId, companyId, countryId, provinceId, districtId, cityId, updateCity, loginUserID, authToken);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    // Delete City
    @ApiOperation(response = City.class, value = "Delete City") // label for swagger
    @DeleteMapping("/city/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable String cityId, @RequestParam String languageId, @RequestParam String companyId,
                                        @RequestParam String countryId, @RequestParam String provinceId, @RequestParam String districtId,
                                        @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCity(languageId, companyId, countryId, provinceId, districtId, cityId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find City
    @ApiOperation(response = City[].class, value = "Find City") //label for swagger
    @PostMapping("/city/find")
    public City[] findCity(@RequestBody FindCity findCity, @RequestParam String authToken) throws Exception {
        return idmasterService.findCity(findCity, authToken);
    }

    //===========================================RateParameter=========================================================
    // Get All RateParameter Details
    @ApiOperation(response = RateParameter[].class, value = "Get all RateParameter Details") // label for swagger
    @GetMapping("/rateParameter")
    public ResponseEntity<?> getAllRateParameterDetails(@RequestParam String authToken) {
        RateParameter[] rateParameterList = idmasterService.getAllRateParameters(authToken);
        return new ResponseEntity<>(rateParameterList, HttpStatus.OK);
    }

    // Get RateParameter
    @ApiOperation(response = RateParameter.class, value = "Get a RateParameter") // label for swagger
    @GetMapping("/rateParameter/{rateParameterId}")
    public ResponseEntity<?> getRateParameter(@PathVariable String rateParameterId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String authToken) {
        RateParameter dbRateParameter = idmasterService.getRateParameter(rateParameterId, companyId, languageId, authToken);
        return new ResponseEntity<>(dbRateParameter, HttpStatus.OK);
    }

    // Create new RateParameter
    @ApiOperation(response = RateParameter.class, value = "Create new RateParameter") // label for swagger
    @PostMapping("/rateParameter")
    public ResponseEntity<?> postRateParameter(@Valid @RequestBody AddRateParameter addRateParameter, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        RateParameter newRateParameter = idmasterService.createRateParameter(addRateParameter, loginUserID, authToken);
        return new ResponseEntity<>(newRateParameter, HttpStatus.OK);
    }

    // Update RateParameter
    @ApiOperation(response = RateParameter.class, value = "Update RateParameter") // label for swagger
    @PatchMapping("/rateParameter/{rateParameterId}")
    public ResponseEntity<?> patchRateParameter(@PathVariable String rateParameterId, @RequestParam String companyId,
                                                @RequestParam String languageId, @RequestParam String loginUserID,
                                                @RequestBody UpdateRateParameter updateRateParameter, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        RateParameter updatedRateParameter = idmasterService.updateRateParameter(companyId, rateParameterId,
                languageId, updateRateParameter, loginUserID, authToken);
        return new ResponseEntity<>(updatedRateParameter, HttpStatus.OK);
    }

    // Delete RateParameter
    @ApiOperation(response = RateParameter.class, value = "Delete RateParameter") // label for swagger
    @DeleteMapping("/rateParameter/{rateParameterId}")
    public ResponseEntity<?> deleteRateParameter(@PathVariable String rateParameterId, @RequestParam String companyId, @RequestParam String languageId,
                                                 @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteRateParameter(companyId, rateParameterId, languageId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find RateParameter
    @ApiOperation(response = RateParameter[].class, value = "Find RateParameter") // label for swagger
    @PostMapping("/rateParameter/find")
    public ResponseEntity<?> findRateParameter(@Valid @RequestBody FindRateParameter findRateParameter, @RequestParam String authToken) throws Exception {
        RateParameter[] rateParameterList = idmasterService.findRateParameter(findRateParameter, authToken);
        return new ResponseEntity<>(rateParameterList, HttpStatus.OK);
    }

    //=================================================Status==========================================================
    // Get All Status Details
    @ApiOperation(response = Status[].class, value = "Get all Status Details") // label for swagger
    @GetMapping("/status")
    public ResponseEntity<?> getAllStatusDetails(@RequestParam String authToken) {
        Status[] statusList = idmasterService.getAllStatus(authToken);
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }

    // Get Status
    @ApiOperation(response = Status.class, value = "Get a Status") // label for swagger
    @GetMapping("/status/{statusId}")
    public ResponseEntity<?> getStatus(@PathVariable String statusId, @RequestParam String languageId, @RequestParam String authToken) {
        Status dbStatus = idmasterService.getStatus(languageId, statusId, authToken);
        return new ResponseEntity<>(dbStatus, HttpStatus.OK);
    }

    // Create new Status
    @ApiOperation(response = Status.class, value = "Create new Status") // label for swagger
    @PostMapping("/status")
    public ResponseEntity<?> postStatus(@RequestBody AddStatus addStatus, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Status newStatus = idmasterService.createStatus(addStatus, loginUserID, authToken);
        return new ResponseEntity<>(newStatus, HttpStatus.OK);
    }

    // Update Status
    @ApiOperation(response = Status.class, value = "Update Status") // label for swagger
    @PatchMapping("/status/{statusId}")
    public ResponseEntity<?> patchStatus(@PathVariable String statusId, @RequestParam String languageId, @RequestParam String loginUserID,
                                         @RequestBody UpdateStatus updateStatus, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Status updatedStatus = idmasterService.updateStatus(languageId, statusId, updateStatus, loginUserID, authToken);
        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    // Delete Status
    @ApiOperation(response = Status.class, value = "Delete Status") // label for swagger
    @DeleteMapping("/status/{statusId}")
    public ResponseEntity<?> deleteStatus(@PathVariable String statusId, @RequestParam String languageId,
                                          @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteStatus(languageId, statusId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Status
    @ApiOperation(response = Status[].class, value = "Find Status") // label for swagger
    @PostMapping("/status/find")
    public ResponseEntity<?> findStatus(@RequestBody FindStatus findStatus, @RequestParam String authToken) throws Exception {
        Status[] statusList = idmasterService.findStatus(findStatus, authToken);
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }

    //===============================================Rate==========================================================
    // Get All Rates
    @ApiOperation(response = Rate[].class, value = "Get all Rate Details") // label for swagger
    @GetMapping("/rate")
    public ResponseEntity<?> getAllRateDetails(@RequestParam String authToken) {
        Rate[] ratesList = idmasterService.getAllRateDetails(authToken);
        return new ResponseEntity<>(ratesList, HttpStatus.OK);
    }

    // Get Rate
    @ApiOperation(response = Rate.class, value = "Get a Rate") // label for swagger
    @GetMapping("/rate/{partnerId}")
    public ResponseEntity<?> getRate(@RequestParam String rateParameterId, @RequestParam String languageId, @RequestParam String companyId,
                                     @PathVariable String partnerId, @RequestParam Long lineNo, @RequestParam String authToken) {
        Rate dbRate = idmasterService.getRate(languageId, companyId, partnerId, rateParameterId, lineNo, authToken);
        return new ResponseEntity<>(dbRate, HttpStatus.OK);
    }

    // Create Rate
    @ApiOperation(response = Rate.class, value = "Create new Rate") // label for swagger
    @PostMapping("/rate")
    public ResponseEntity<?> postRate(@Valid @RequestBody AddRate[] addRate, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Rate[] newRate = idmasterService.createRate(addRate, loginUserID, authToken);
        return new ResponseEntity<>(newRate, HttpStatus.OK);
    }

    // Update Rate
    @ApiOperation(response = Rate.class, value = "Update Rate") // label for swagger
    @PatchMapping("/rate/update")
    public ResponseEntity<?> patchRate(@Valid @RequestParam String loginUserID,
                                       @RequestBody Rate[] updateRate, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Rate[] updatedRate = idmasterService.updateRate(updateRate, loginUserID, authToken);
        return new ResponseEntity<>(updatedRate, HttpStatus.OK);
    }

    // Delete Rate
    @ApiOperation(response = Rate.class, value = "Delete Rate") // label for swagger
    @PostMapping("/rate/delete")
    public ResponseEntity<?> deleteRate(@Valid @RequestBody DeleteRate[] deleteRate, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteRate(deleteRate, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Rate
    @ApiOperation(response = Rate[].class, value = "Find Rate")
    @PostMapping("/rate/find")
    public ResponseEntity<?> findRate(@Valid @RequestBody FindRate findRate, @RequestParam String authToken) throws Exception {
        Rate[] rateList = idmasterService.findRate(findRate, authToken);
        return new ResponseEntity<>(rateList, HttpStatus.OK);
    }

    //===============================================Currency==========================================================
    // Get All Currency Details
    @ApiOperation(response = Currency[].class, value = "Get all Currency Details") // label for swagger
    @GetMapping("/currency")
    public ResponseEntity<?> getAllCurrencyDetails(@RequestParam String authToken) {
        Currency[] currencyList = idmasterService.getAllCurrency(authToken);
        return new ResponseEntity<>(currencyList, HttpStatus.OK);
    }

    // Get Currency
    @ApiOperation(response = Currency.class, value = "Get a Currency") // label for swagger
    @GetMapping("/currency/{currencyId}")
    public ResponseEntity<?> getCurrency(@PathVariable String currencyId, @RequestParam String authToken) {
        Currency dbCurrency = idmasterService.getCurrency(currencyId, authToken);
        return new ResponseEntity<>(dbCurrency, HttpStatus.OK);
    }

    // Create new Currency
    @ApiOperation(response = Currency.class, value = "Create new Currency") // label for swagger
    @PostMapping("/currency")
    public ResponseEntity<?> postCurrency(@RequestBody AddCurrency addCurrency, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Currency newCurrency = idmasterService.createCurrency(addCurrency, loginUserID, authToken);
        return new ResponseEntity<>(newCurrency, HttpStatus.OK);
    }

    // Update Currency
    @ApiOperation(response = Currency.class, value = "Update Currency") // label for swagger
    @PatchMapping("/currency/{currencyId}")
    public ResponseEntity<?> patchCurrency(@PathVariable String currencyId, @RequestParam String loginUserID,
                                           @RequestBody UpdateCurrency updateCurrency, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Currency updatedCurrency = idmasterService.updateCurrency(currencyId, updateCurrency, loginUserID, authToken);
        return new ResponseEntity<>(updatedCurrency, HttpStatus.OK);
    }

    // Delete Currency
    @ApiOperation(response = Currency.class, value = "Delete Currency") // label for swagger
    @DeleteMapping("/currency/{currencyId}")
    public ResponseEntity<?> deleteCurrency(@PathVariable String currencyId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCurrency(currencyId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Currency
    @ApiOperation(response = Currency[].class, value = "Find Currency") // label for swagger
    @PostMapping("/currency/find")
    public ResponseEntity<?> findCurrency(@RequestBody FindCurrency findCurrency, @RequestParam String authToken) throws Exception {
        Currency[] currencyList = idmasterService.findCurrency(findCurrency, authToken);
        return new ResponseEntity<>(currencyList, HttpStatus.OK);
    }

    //====================================================Hub==========================================================
    // Get Hub
    @ApiOperation(response = Hub.class, value = "Get All Hub Details") // label for swagger
    @GetMapping("/hub")
    public ResponseEntity<?> getAllHubDetails(@RequestParam String authToken) {
        Hub[] hubs = idmasterService.getAllHubs(authToken);
        return new ResponseEntity<>(hubs, HttpStatus.OK);
    }

    // Get Hub
    @ApiOperation(response = Hub.class, value = "Get Hub") // label for swagger
    @GetMapping("/hub/{hubCode}")
    public ResponseEntity<?> getHub(@PathVariable String hubCode, @RequestParam String companyId,
                                    @RequestParam String languageId, @RequestParam String authToken) {
        Hub hub = idmasterService.getHub(languageId, companyId, hubCode, authToken);
        return new ResponseEntity<>(hub, HttpStatus.OK);
    }

    // Create Hub
    @ApiOperation(response = Hub.class, value = "Create new Hub") // label for swagger
    @PostMapping("/hub")
    public ResponseEntity<?> postHub(@RequestBody AddHub addHub, @RequestParam String loginUserID, @RequestParam String authToken) {
        Hub hub = idmasterService.createHub(addHub, loginUserID, authToken);
        return new ResponseEntity<>(hub, HttpStatus.OK);
    }

    // Update Hub
    @ApiOperation(response = Hub.class, value = "Update Hub") // label for swagger
    @PatchMapping("/hub/{hubCode}")
    public ResponseEntity<?> patchHub(@PathVariable String hubCode, @RequestParam String languageId,
                                      @RequestParam String loginUserID, @RequestBody UpdateHub updateHub,
                                      @RequestParam String companyId, @RequestParam String authToken) {
        Hub hub = idmasterService.updateHub(languageId, companyId, hubCode, updateHub, loginUserID, authToken);
        return new ResponseEntity<>(hub, HttpStatus.OK);
    }

    // Delete Hub
    @ApiOperation(response = Hub.class, value = "Delete Hub") // label for swagger
    @DeleteMapping("/hub/{hubCode}")
    public ResponseEntity<?> deleteHub(@PathVariable String hubCode, @RequestParam String languageId, @RequestParam String loginUserID,
                                       @RequestParam String companyId, @RequestParam String authToken) {
        idmasterService.deleteHub(languageId, companyId, hubCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Hub
    @ApiOperation(response = Hub[].class, value = "Find Hub") //label for swagger
    @PostMapping("/hub/find")
    public Hub[] findHubs(@RequestBody FindHub findHub, @RequestParam String authToken) throws Exception {
        return idmasterService.findHubs(findHub, authToken);
    }

    //==================================================Customer=======================================================
    // Get all Customer Details
    @ApiOperation(response = Customer.class, value = "Get All Customer Details") // label for swagger
    @GetMapping("/customer")
    public ResponseEntity<?> getAllCustomerDetails(@RequestParam String authToken) {
        Customer[] customers = idmasterService.getAllCustomers(authToken);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Get a Customer
    @ApiOperation(response = Customer.class, value = "Get Customer") // label for swagger
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable String customerId, @RequestParam String companyId, @RequestParam String subProductId,
                                         @RequestParam String subProductValue, @RequestParam String productId,
                                         @RequestParam String languageId, @RequestParam String authToken) {
        Customer customer = idmasterService.getCustomer(languageId, companyId, subProductId, subProductValue, productId, customerId, authToken);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // Create Customer
    @ApiOperation(response = Customer.class, value = "Create new Customer") // label for swagger
    @PostMapping("/customer")
    public ResponseEntity<?> postCustomer(@RequestBody AddCustomer addCustomer, @RequestParam String loginUserID,
                                          @RequestParam String authToken) {
        Customer customer = idmasterService.createCustomer(addCustomer, loginUserID, authToken);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // Update Customer
    @ApiOperation(response = Customer.class, value = "Update Customer") // label for swagger
    @PatchMapping("/customer/{customerId}")
    public ResponseEntity<?> patchCustomer(@PathVariable String customerId, @RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String subProductId, @RequestParam String loginUserID, @RequestParam String subProductValue,
                                           @RequestBody UpdateCustomer updateCustomer, @RequestParam String productId, @RequestParam String authToken) {
        Customer customer = idmasterService.updateCustomer(languageId, companyId, subProductId, subProductValue, productId, customerId, updateCustomer, loginUserID, authToken);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // Delete Customer
    @ApiOperation(response = Customer.class, value = "Delete Customer") // label for swagger
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String customerId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String subProductId, @RequestParam String subProductValue, @RequestParam String productId,
                                            @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCustomer(languageId, companyId, subProductId, subProductValue, productId, customerId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Customer
    @ApiOperation(response = Customer[].class, value = "Find Customer") //label for swagger
    @PostMapping("/customer/find")
    public Customer[] findCustomers(@RequestBody FindCustomer findCustomer, @RequestParam String authToken) throws Exception {
        return idmasterService.findCustomers(findCustomer, authToken);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Customers - bulk
    @ApiOperation(response = Customer.class, value = "Create new Customers - bulk") // label for swagger
    @PostMapping("/customer/create/list")
    public ResponseEntity<?> postCustomerBulk(@RequestBody List<AddCustomer> addCustomerList,
                                              @RequestParam String loginUserID, @RequestParam String authToken) {
        Customer[] customers = idmasterService.createCustomerBulk(addCustomerList, loginUserID, authToken);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Update Customers - bulk
    @ApiOperation(response = Customer.class, value = "Update Customers - bulk") // label for swagger
    @PatchMapping("/customer/update/list")
    public ResponseEntity<?> patchCustomerBulk(@RequestBody List<UpdateCustomer> updateCustomerList,
                                               @RequestParam String loginUserID, @RequestParam String authToken) {
        Customer[] customers = idmasterService.updateCustomerBulk(updateCustomerList, loginUserID, authToken);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Delete Customers - bulk
    @ApiOperation(response = Customer.class, value = "Delete Customers - bulk") // label for swagger
    @PostMapping("/customer/delete/list")
    public ResponseEntity<?> deleteCustomerBulk(@RequestBody List<CustomerDeleteInput> deleteInputs,
                                                @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCustomerBulk(deleteInputs, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //==================================================Consignor========================================================
    // Get all Consignor Details
    @ApiOperation(response = Consignor.class, value = "Get All Consignor Details") // label for swagger
    @GetMapping("/consignor")
    public ResponseEntity<?> getAllConsignorDetails(@RequestParam String authToken) {
        Consignor[] consignors = idmasterService.getAllConsignors(authToken);
        return new ResponseEntity<>(consignors, HttpStatus.OK);
    }

    // Get a Consignor
    @ApiOperation(response = Consignor.class, value = "Get Consignor") // label for swagger
    @GetMapping("/consignor/{consignorId}")
    public ResponseEntity<?> getConsignor(@PathVariable String consignorId, @RequestParam String companyId, @RequestParam String subProductId,
                                          @RequestParam String subProductValue, @RequestParam String productId, @RequestParam String customerId,
                                          @RequestParam String languageId, @RequestParam String authToken) {
        Consignor consignor = idmasterService.getConsignor(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, authToken);
        return new ResponseEntity<>(consignor, HttpStatus.OK);
    }

    // Create Consignor
    @ApiOperation(response = Consignor.class, value = "Create new Consignor") // label for swagger
    @PostMapping("/consignor")
    public ResponseEntity<?> postConsignor(@RequestBody AddConsignor addConsignor, @RequestParam String loginUserID,
                                           @RequestParam String authToken) {
        Consignor consignor = idmasterService.createConsignor(addConsignor, loginUserID, authToken);
        return new ResponseEntity<>(consignor, HttpStatus.OK);
    }

    // Update Consignor
    @ApiOperation(response = Consignor.class, value = "Update Consignor") // label for swagger
    @PatchMapping("/consignor/{consignorId}")
    public ResponseEntity<?> patchConsignor(@PathVariable String consignorId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String subProductId, @RequestParam String loginUserID, @RequestParam String subProductValue,
                                            @RequestBody UpdateConsignor updateConsignor, @RequestParam String productId,
                                            @RequestParam String customerId, @RequestParam String authToken) {
        Consignor consignor = idmasterService.updateConsignor(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, updateConsignor, loginUserID, authToken);
        return new ResponseEntity<>(consignor, HttpStatus.OK);
    }

    // Delete Consignor
    @ApiOperation(response = Consignor.class, value = "Delete Consignor") // label for swagger
    @DeleteMapping("/consignor/{consignorId}")
    public ResponseEntity<?> deleteShipper(@PathVariable String consignorId, @RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String subProductId, @RequestParam String subProductValue, @RequestParam String productId,
                                           @RequestParam String customerId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteConsignor(languageId, companyId, subProductId, subProductValue, productId, customerId, consignorId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Consignor
    @ApiOperation(response = Consignor[].class, value = "Find Consignor") //label for swagger
    @PostMapping("/consignor/find")
    public Consignor[] findShippers(@RequestBody FindConsignor findConsignor, @RequestParam String authToken) throws Exception {
        return idmasterService.findConsignors(findConsignor, authToken);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Consignors - bulk
    @ApiOperation(response = Consignor.class, value = "Create new Consignors - bulk") // label for swagger
    @PostMapping("/consignor/create/list")
    public ResponseEntity<?> postConsignorBulk(@RequestBody List<AddConsignor> addConsignorList,
                                               @RequestParam String loginUserID, @RequestParam String authToken) {
        Consignor[] consignors = idmasterService.createConsignorBulk(addConsignorList, loginUserID, authToken);
        return new ResponseEntity<>(consignors, HttpStatus.OK);
    }

    // Update Consignors - bulk
    @ApiOperation(response = Consignor.class, value = "Update Consignors - bulk") // label for swagger
    @PatchMapping("/consignor/update/list")
    public ResponseEntity<?> updateCustomerBulk(@RequestBody List<UpdateConsignor> updateConsignorList,
                                                @RequestParam String loginUserID, @RequestParam String authToken) {
        Consignor[] consignors = idmasterService.updateConsignorBulk(updateConsignorList, loginUserID, authToken);
        return new ResponseEntity<>(consignors, HttpStatus.OK);
    }

    // Delete Consignors - bulk
    @ApiOperation(response = Consignor.class, value = "Delete Consignors - bulk") // label for swagger
    @PostMapping("/consignor/delete/list")
    public ResponseEntity<?> deleteConsignorBulk(@RequestBody List<ConsignorDeleteInput> deleteInputs,
                                                 @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteConsignorBulk(deleteInputs, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //===============================================PartnerHubMapping=================================================
    // Get All PartnerHubMapping Details
    @ApiOperation(response = PartnerHubMapping.class, value = "Get All PartnerHubMapping Details") // label for swagger
    @GetMapping("/partnerHubMapping")
    public ResponseEntity<?> getAllPartnerHubMappingDetails(@RequestParam String authToken) {
        PartnerHubMapping[] partnerHubMappings = idmasterService.getAllPartnerHubMappings(authToken);
        return new ResponseEntity<>(partnerHubMappings, HttpStatus.OK);
    }

    // Get PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping.class, value = "Get PartnerHubMapping") // label for swagger
    @GetMapping("/partnerHubMapping/{partnerId}")
    public ResponseEntity<?> getPartnerHubMapping(@PathVariable String partnerId, @RequestParam String partnerType, @RequestParam String companyId,
                                                  @RequestParam String languageId, @RequestParam String hubCode, @RequestParam String authToken,@RequestParam String productCode) {
        PartnerHubMapping partnerHubMapping = idmasterService.getPartnerHubMapping(languageId, companyId, hubCode, partnerType, partnerId, authToken,productCode);
        return new ResponseEntity<>(partnerHubMapping, HttpStatus.OK);
    }

    // Create PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping.class, value = "Create new PartnerHubMapping") // label for swagger
    @PostMapping("/partnerHubMapping")
    public ResponseEntity<?> postPartnerHubMapping(@RequestBody AddPartnerHubMapping addPartnerHubMapping,
                                                   @RequestParam String loginUserID, @RequestParam String authToken) {
        PartnerHubMapping partnerHubMapping = idmasterService.createPartnerHubMapping(addPartnerHubMapping, loginUserID, authToken);
        return new ResponseEntity<>(partnerHubMapping, HttpStatus.OK);
    }

    // Update PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping.class, value = "Update PartnerHubMapping") // label for swagger
    @PatchMapping("/partnerHubMapping/{partnerId}")
    public ResponseEntity<?> patchPartnerHubMapping(@PathVariable String partnerId, @RequestParam String partnerType, @RequestParam String languageId,
                                                    @RequestParam String loginUserID, @RequestBody UpdatePartnerHubMapping updatePartnerHubMapping,
                                                    @RequestParam String companyId, @RequestParam String hubCode, @RequestParam String authToken, @RequestParam String productCode) {
        PartnerHubMapping partnerHubMapping = idmasterService.updatePartnerHubMapping(languageId, companyId, hubCode, partnerType,
                partnerId, updatePartnerHubMapping, loginUserID, authToken,productCode);
        return new ResponseEntity<>(partnerHubMapping, HttpStatus.OK);
    }

    // Delete PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping.class, value = "Delete PartnerHubMapping") // label for swagger
    @DeleteMapping("/partnerHubMapping/{partnerId}")
    public ResponseEntity<?> deletePartnerHubMapping(@PathVariable String partnerId, @RequestParam String partnerType, @RequestParam String languageId,
                                                     @RequestParam String loginUserID, @RequestParam String companyId,
                                                     @RequestParam String hubCode, @RequestParam String authToken,@RequestParam String productCode) {
        idmasterService.deletePartnerHubMapping(languageId, companyId, hubCode, partnerType, partnerId, loginUserID, authToken,productCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping[].class, value = "Find PartnerHubMapping") //label for swagger
    @PostMapping("/partnerHubMapping/find")
    public PartnerHubMapping[] findPartnerHubMappings(@RequestBody FindPartnerHubMapping findPartnerHubMapping,
                                                      @RequestParam String authToken) throws Exception {
        return idmasterService.findPartnerHubMappings(findPartnerHubMapping, authToken);
    }

    //=================================================HSCode==========================================================
    // Get All HSCode Details
    @ApiOperation(response = HSCode.class, value = "Get All HSCode Details") // label for swagger
    @GetMapping("/hsCode")
    public ResponseEntity<?> getAllHSCodeDetails(@RequestParam String authToken) {
        HSCode[] hsCodes = idmasterService.getAllHSCodes(authToken);
        return new ResponseEntity<>(hsCodes, HttpStatus.OK);
    }

    // Get HSCode
    @ApiOperation(response = HSCode.class, value = "Get HSCode") // label for swagger
    @GetMapping("/hsCode/{hsCode}")
    public ResponseEntity<?> getHSCode(@PathVariable String hsCode, @RequestParam String companyId,
                                       @RequestParam String languageId, @RequestParam String specialApprovalId, @RequestParam String authToken) {
        HSCode dbHsCode = idmasterService.getHSCode(languageId, companyId, hsCode, specialApprovalId, authToken);
        return new ResponseEntity<>(dbHsCode, HttpStatus.OK);
    }

//    // Create HSCode
//    @ApiOperation(response = HSCode.class, value = "Create new HSCode") // label for swagger
//    @PostMapping("/hsCode")
//    public ResponseEntity<?> createHSCode(@RequestBody AddHSCode addHSCode, @RequestParam String loginUserID, @RequestParam String authToken) {
//        HSCode hsCode = idmasterService.createHSCode(addHSCode, loginUserID, authToken);
//        return new ResponseEntity<>(hsCode, HttpStatus.OK);
//    }

//    // Update HSCode
//    @ApiOperation(response = HSCode.class, value = "Update HSCode") // label for swagger
//    @PatchMapping("/hsCode/{hsCode}")
//    public ResponseEntity<?> updateHSCode(@PathVariable String hsCode, @RequestParam String languageId,
//                                          @RequestParam String loginUserID, @RequestBody UpdateHSCode updateHSCode,
//                                          @RequestParam String companyId, @RequestParam String specialApprovalId,
//                                          @RequestParam String authToken) {
//        HSCode dbHSCode = idmasterService.updateHSCode(languageId, companyId, hsCode, specialApprovalId, updateHSCode, loginUserID, authToken);
//        return new ResponseEntity<>(dbHSCode, HttpStatus.OK);
//    }

//    // Delete HSCode
//    @ApiOperation(response = HSCode.class, value = "Delete HSCode") // label for swagger
//    @DeleteMapping("/hsCode/{hsCode}")
//    public ResponseEntity<?> deleteHSCode(@PathVariable String hsCode, @RequestParam String languageId, @RequestParam String loginUserID,
//                                          @RequestParam String companyId, @RequestParam String specialApprovalId,
//                                          @RequestParam String authToken) {
//        idmasterService.deleteHSCode(languageId, companyId, hsCode, specialApprovalId, loginUserID, authToken);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    // Find HSCode
    @ApiOperation(response = HSCode[].class, value = "Find HSCode") //label for swagger
    @PostMapping("/hsCode/find")
    public HSCode[] findHSCodes(@RequestBody FindHSCode findHSCode, @RequestParam String authToken) throws Exception {
        return idmasterService.findHSCodes(findHSCode, authToken);
    }

    // Create HSCode - bulk
    @ApiOperation(response = HSCode[].class, value = "Create new HSCode - bulk") // label for swagger
    @PostMapping("/hsCode")
    public ResponseEntity<?> postHsCodeBulk(@Valid @RequestBody List<AddHSCode> addHSCodes, @RequestParam String loginUserID,@RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        HSCode[] newHsCode = idmasterService.createHSCodeBulk(addHSCodes, loginUserID,authToken);
        return new ResponseEntity<>(newHsCode, HttpStatus.OK);
    }

    // Update HSCode - bulk
    @ApiOperation(response = HSCode[].class, value = "Update HSCode - bulk") // label for swagger
    @PatchMapping("/hsCode/{hsCode}")
    public ResponseEntity<?> patchHsCodeBulk(@PathVariable String hsCode,@Valid @RequestBody List<UpdateHSCode> updateHsCodeList, @RequestParam String loginUserID,
                                             @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        HSCode[] updatedHsCode = idmasterService.updateHsCodeBulk(hsCode,updateHsCodeList, loginUserID,authToken);
        return new ResponseEntity<>(updatedHsCode, HttpStatus.OK);
    }

    // Delete HsCode - bulk
    @ApiOperation(response = HSCode[].class, value = "Delete HsCode - bulk") // label for swagger
    @PostMapping("/hsCode/{hsCode}")
    public ResponseEntity<?> deleteHSCodeBulk(@PathVariable String hsCode,@Valid @RequestBody List<HsCodeDeleteInput> hsCodeDeleteInputList, @RequestParam String loginUserID,
                                              @RequestParam String authToken) {
        idmasterService.deleteHsCodeBulk(hsCode,hsCodeDeleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //================================================ProvinceMapping==================================================
    // Get All ProvinceMapping Details
    @ApiOperation(response = ProvinceMapping[].class, value = "Get all ProvinceMapping Details") // label for swagger
    @GetMapping("/provinceMapping")
    public ResponseEntity<?> getAllProvinceMappingDetails(@RequestParam String authToken) {
        ProvinceMapping[] provinceMappings = idmasterService.getAllProvinceMappings(authToken);
        return new ResponseEntity<>(provinceMappings, HttpStatus.OK);
    }

    // Get ProvinceMapping
    @ApiOperation(response = ProvinceMapping.class, value = "Get a ProvinceMapping") // label for swagger
    @GetMapping("/provinceMapping/{partnerId}")
    public ResponseEntity<?> getProvinceMapping(@PathVariable String partnerId, @RequestParam String provinceId, @RequestParam String companyId,
                                                @RequestParam String languageId, @RequestParam String authToken) {
        ProvinceMapping provinceMapping = idmasterService.getProvinceMapping(languageId, companyId, provinceId, partnerId, authToken);
        return new ResponseEntity<>(provinceMapping, HttpStatus.OK);
    }

    // Create new ProvinceMapping
    @ApiOperation(response = ProvinceMapping.class, value = "Create new ProvinceMapping") // label for swagger
    @PostMapping("/provinceMapping")
    public ResponseEntity<?> postProvinceMapping(@RequestBody AddProvinceMapping addProvinceMapping,
                                                 @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ProvinceMapping provinceMapping = idmasterService.createProvinceMapping(addProvinceMapping, loginUserID, authToken);
        return new ResponseEntity<>(provinceMapping, HttpStatus.OK);
    }

    // Update ProvinceMapping
    @ApiOperation(response = Province.class, value = "Update ProvinceMapping") // label for swagger
    @PatchMapping("/provinceMapping/{partnerId}")
    public ResponseEntity<?> patchProvinceMapping(@PathVariable String partnerId, @RequestParam String provinceId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String loginUserID,
                                                  @RequestBody UpdateProvinceMapping updateProvinceMapping, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ProvinceMapping provinceMapping = idmasterService.updateProvinceMapping(languageId, companyId, provinceId,
                partnerId, updateProvinceMapping, loginUserID, authToken);
        return new ResponseEntity<>(provinceMapping, HttpStatus.OK);
    }

    // Delete ProvinceMapping
    @ApiOperation(response = ProvinceMapping.class, value = "Delete ProvinceMapping") // label for swagger
    @DeleteMapping("/provinceMapping/{partnerId}")
    public ResponseEntity<?> deleteProvinceMapping(@PathVariable String partnerId, @RequestParam String provinceId, @RequestParam String languageId,
                                                   @RequestParam String companyId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteProvinceMapping(languageId, companyId, provinceId, partnerId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ProvinceMapping
    @ApiOperation(response = ProvinceMapping[].class, value = "Find ProvinceMapping") // label for swagger
    @PostMapping("/provinceMapping/find")
    public ResponseEntity<?> findProvinceMapping(@RequestBody FindProvinceMapping findProvinceMapping,
                                                 @RequestParam String authToken) throws Exception {
        ProvinceMapping[] provinceMappings = idmasterService.findProvinceMappings(findProvinceMapping, authToken);
        return new ResponseEntity<>(provinceMappings, HttpStatus.OK);
    }


    //================================================CountryMapping==================================================
    // Get All CountryMapping Details
    @ApiOperation(response = CountryMapping[].class, value = "Get all CountryMapping Details") // label for swagger
    @GetMapping("/countryMapping")
    public ResponseEntity<?> getAllCountryMappingDetails(@RequestParam String authToken) {
        CountryMapping[] countryMappings = idmasterService.getAllCountryMappings(authToken);
        return new ResponseEntity<>(countryMappings, HttpStatus.OK);
    }

    // Get CountryMapping
    @ApiOperation(response = CountryMapping.class, value = "Get a CountryMapping") // label for swagger
    @GetMapping("/countryMapping/{partnerId}")
    public ResponseEntity<?> getCountryMapping(@PathVariable String partnerId, @RequestParam String countryId, @RequestParam String companyId,
                                               @RequestParam String languageId, @RequestParam String authToken) {
        CountryMapping countryMapping = idmasterService.getCountryMapping(languageId, companyId, countryId, partnerId, authToken);
        return new ResponseEntity<>(countryMapping, HttpStatus.OK);
    }

    // Create new CountryMapping
    @ApiOperation(response = CountryMapping.class, value = "Create new CountryMapping") // label for swagger
    @PostMapping("/countryMapping")
    public ResponseEntity<?> postCountryMapping(@RequestBody AddCountryMapping addCountryMapping,
                                                @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        CountryMapping countryMapping = idmasterService.createCountryMapping(addCountryMapping, loginUserID, authToken);
        return new ResponseEntity<>(countryMapping, HttpStatus.OK);
    }

    // Update CountryMapping
    @ApiOperation(response = Country.class, value = "Update CountryMapping") // label for swagger
    @PatchMapping("/countryMapping/{partnerId}")
    public ResponseEntity<?> patchCountryMapping(@PathVariable String partnerId, @RequestParam String countryId, @RequestParam String languageId,
                                                 @RequestParam String companyId, @RequestParam String loginUserID,
                                                 @RequestBody UpdateCountryMapping updateCountryMapping, @RequestParam String authToken) {
        CountryMapping countryMapping = idmasterService.updateCountryMapping(languageId, companyId, countryId,
                partnerId, updateCountryMapping, loginUserID, authToken);
        return new ResponseEntity<>(countryMapping, HttpStatus.OK);
    }

    // Delete CountryMapping
    @ApiOperation(response = CountryMapping.class, value = "Delete CountryMapping") // label for swagger
    @DeleteMapping("/countryMapping/{partnerId}")
    public ResponseEntity<?> deleteCountryMapping(@PathVariable String partnerId, @RequestParam String countryId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCountryMapping(languageId, companyId, countryId, partnerId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find CountryMapping
    @ApiOperation(response = CountryMapping[].class, value = "Find CountryMapping") // label for swagger
    @PostMapping("/countryMapping/find")
    public ResponseEntity<?> findCountryMapping(@RequestBody FindCountryMapping findCountryMapping,
                                                @RequestParam String authToken) throws Exception {
        CountryMapping[] countryMappings = idmasterService.findCountryMappings(findCountryMapping, authToken);
        return new ResponseEntity<>(countryMappings, HttpStatus.OK);
    }

    //===========================================Iata=======================================================

    // Get All Iata Details
    @ApiOperation(response = Iata[].class, value = "Get all Iata Details") // label for swagger
    @GetMapping("/iata")
    public ResponseEntity<?> getAllIataDetails(@RequestParam String authToken) {
        Iata[] iataList = idmasterService.getAllIata(authToken);
        return new ResponseEntity<>(iataList, HttpStatus.OK);
    }

    // Get Iata
    @ApiOperation(response = Iata.class, value = "Get a Iata") // label for swagger
    @GetMapping("/iata/{originCode}")
    public ResponseEntity<?> getIata(@PathVariable String originCode, @RequestParam String companyId,
                                     @RequestParam String languageId, @RequestParam String origin,
                                     @RequestParam String authToken) {
        Iata dbIata = idmasterService.getIata(companyId, languageId, origin, originCode, authToken);
        return new ResponseEntity<>(dbIata, HttpStatus.OK);
    }

    // Create new Iata
    @ApiOperation(response = Iata.class, value = "Create new Iata") // label for swagger
    @PostMapping("/iata")
    public ResponseEntity<?> postIata(@RequestBody AddIata addIata, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Iata newIata = idmasterService.createIata(addIata, loginUserID, authToken);
        return new ResponseEntity<>(newIata, HttpStatus.OK);
    }

    // Update Iata
    @ApiOperation(response = Iata.class, value = "Update Iata") // label for swagger
    @PatchMapping("/iata/{originCode}")
    public ResponseEntity<?> patchIata(@PathVariable String originCode, @RequestParam String companyId, @RequestParam String languageId,
                                       @RequestParam String origin, @RequestParam String loginUserID,
                                       @RequestBody UpdateIata updateIata, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Iata updatedIata = idmasterService.updateIata(companyId, languageId, origin, originCode, updateIata, loginUserID, authToken);
        return new ResponseEntity<>(updatedIata, HttpStatus.OK);
    }

    // Delete Iata
    @ApiOperation(response = Iata.class, value = "Delete Iata") // label for swagger
    @DeleteMapping("/iata/{originCode}")
    public ResponseEntity<?> deleteIata(@PathVariable String originCode, @RequestParam String companyId, @RequestParam String languageId,
                                        @RequestParam String origin, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteIata(companyId, origin, originCode, languageId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Iata
    @ApiOperation(response = Iata[].class, value = "Find Iata") // label for swagger
    @PostMapping("/iata/find")
    public ResponseEntity<?> findRateParameter(@RequestBody FindIata findIata, @RequestParam String authToken) throws Exception {
        Iata[] iataList = idmasterService.findIata(findIata, authToken);
        return new ResponseEntity<>(iataList, HttpStatus.OK);
    }

    //================================================CityMapping==================================================
    // Get All CityMapping Details
    @ApiOperation(response = CityMapping[].class, value = "Get all CityMapping Details") // label for swagger
    @GetMapping("/cityMapping")
    public ResponseEntity<?> getAllCityMappingDetails(@RequestParam String authToken) {
        CityMapping[] cityMappings = idmasterService.getAllCityMappings(authToken);
        return new ResponseEntity<>(cityMappings, HttpStatus.OK);
    }

    // Get CityMapping
    @ApiOperation(response = CityMapping.class, value = "Get a CityMapping") // label for swagger
    @GetMapping("/cityMapping/{partnerId}")
    public ResponseEntity<?> getCityMapping(@PathVariable String partnerId, @RequestParam String cityId, @RequestParam String companyId,
                                            @RequestParam String languageId, @RequestParam String authToken) {
        CityMapping cityMapping = idmasterService.getCityMapping(languageId, companyId, cityId, partnerId, authToken);
        return new ResponseEntity<>(cityMapping, HttpStatus.OK);
    }

    // Create new CityMapping
    @ApiOperation(response = CityMapping.class, value = "Create new CityMapping") // label for swagger
    @PostMapping("/cityMapping")
    public ResponseEntity<?> postCityMapping(@RequestBody AddCityMapping addCityMapping,
                                             @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        CityMapping cityMapping = idmasterService.createCityMapping(addCityMapping, loginUserID, authToken);
        return new ResponseEntity<>(cityMapping, HttpStatus.OK);
    }

    // Update CityMapping
    @ApiOperation(response = City.class, value = "Update CityMapping") // label for swagger
    @PatchMapping("/cityMapping/{partnerId}")
    public ResponseEntity<?> patchCityMapping(@PathVariable String partnerId, @RequestParam String cityId, @RequestParam String languageId,
                                              @RequestParam String companyId, @RequestParam String loginUserID,
                                              @RequestBody UpdateCityMapping updateCityMapping, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        CityMapping cityMapping = idmasterService.updateCityMapping(languageId, companyId, cityId, partnerId, updateCityMapping, loginUserID, authToken);
        return new ResponseEntity<>(cityMapping, HttpStatus.OK);
    }

    // Delete CityMapping
    @ApiOperation(response = CityMapping.class, value = "Delete CityMapping") // label for swagger
    @DeleteMapping("/cityMapping/{partnerId}")
    public ResponseEntity<?> deletecityMapping(@PathVariable String partnerId, @RequestParam String cityId, @RequestParam String languageId,
                                               @RequestParam String companyId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCityMapping(languageId, companyId, cityId, partnerId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find CityMapping
    @ApiOperation(response = CityMapping[].class, value = "Find CityMapping") // label for swagger
    @PostMapping("/cityMapping/find")
    public ResponseEntity<?> findCityMapping(@RequestBody FindCityMapping findCityMapping,
                                             @RequestParam String authToken) throws Exception {
        CityMapping[] cityMappings = idmasterService.findCityMappings(findCityMapping, authToken);
        return new ResponseEntity<>(cityMappings, HttpStatus.OK);
    }

    //---------------------------------------------------Menu----------------------------------------------------------
    // Get All Menu Details
    @ApiOperation(response = Menu.class, value = "Get all Menu details") // label for swagger
    @GetMapping("/menu")
    public ResponseEntity<?> getAllMenus(@RequestParam String authToken) {
        Menu[] menuList = idmasterService.getAllMenus(authToken);
        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }

    // Get a Menu
    @ApiOperation(response = Menu.class, value = "Get a Menu") // label for swagger
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<?> getMenu(@PathVariable Long menuId, @RequestParam Long subMenuId, @RequestParam Long authorizationObjectId,
                                     @RequestParam String companyId, @RequestParam String languageId, @RequestParam String authToken) {
        Menu dbMenu = idmasterService.getMenu(menuId, subMenuId, authorizationObjectId, companyId, languageId, authToken);
        return new ResponseEntity<>(dbMenu, HttpStatus.OK);
    }

    // Create new Menu
    @ApiOperation(response = Menu.class, value = "Create Menu") // label for swagger
    @PostMapping("/menu")
    public ResponseEntity<?> postMenu(@Valid @RequestBody AddMenu addMenu,
                                      @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Menu menu = idmasterService.createMenu(addMenu, loginUserID, authToken);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    // Create Menu Bulk
    @ApiOperation(response = Menu.class, value = "Create Multiple Menus") // label for swagger
    @PostMapping("/menu/bulk")
    public ResponseEntity<?> postMenuBulk(@Valid @RequestBody List<AddMenu> addMenuList,
                                          @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Menu[] menuBulk = idmasterService.createMenuBulk(addMenuList, loginUserID, authToken);
        return new ResponseEntity<>(menuBulk, HttpStatus.OK);
    }

    // Update Menu
    @ApiOperation(response = Menu.class, value = "Update Menu") // label for swagger
    @PatchMapping("/menu/{menuId}")
    public ResponseEntity<?> patchMenu(@PathVariable Long menuId, @RequestParam Long subMenuId, @RequestParam Long authorizationObjectId,
                                       @RequestParam String companyId, @RequestParam String languageId, @RequestParam String loginUserID,
                                       @RequestBody UpdateMenu updateMenu, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Menu menu = idmasterService.updateMenu(menuId, subMenuId, authorizationObjectId, companyId, languageId,
                loginUserID, updateMenu, authToken);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    // Delete Menu
    @ApiOperation(response = Menu.class, value = "Delete Menu") // label for swagger
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long menuId, @RequestParam Long subMenuId, @RequestParam Long authorizationObjectId,
                                        @RequestParam String companyId, @RequestParam String languageId,
                                        @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteMenu(menuId, subMenuId, authorizationObjectId, companyId, languageId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Menus
    @ApiOperation(response = Menu[].class, value = "Find Menu") //label for swagger
    @PostMapping("/menu/find")
    public Menu[] findMenu(@RequestBody FindMenu findMenu, @RequestParam String authToken) throws Exception {
        return idmasterService.findMenus(findMenu, authToken);
    }


    //===============================================District==========================================================
    // Get All District
    @ApiOperation(response = District[].class, value = "Get all District Details") // label for swagger
    @GetMapping("/district")
    public ResponseEntity<?> getAllDistrict(@RequestParam String authToken) {
        District[] districtList = idmasterService.getAllDistrict(authToken);
        return new ResponseEntity<>(districtList, HttpStatus.OK);
    }

    // Get District
    @ApiOperation(response = District.class, value = "Get a District") // label for swagger
    @GetMapping("/district/{districtId}")
    public ResponseEntity<?> getDistrict(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String countryId,
                                         @PathVariable String districtId, @RequestParam String provinceId, @RequestParam String authToken) {
        District dbDistrict = idmasterService.getDistrict(languageId, companyId, countryId, provinceId, districtId, authToken);
        return new ResponseEntity<>(dbDistrict, HttpStatus.OK);
    }

    // Create District
    @ApiOperation(response = District.class, value = "Create new District") // label for swagger
    @PostMapping("/district")
    public ResponseEntity<?> postDistrict(@Valid @RequestBody AddDistrict addDistrict,
                                          @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        District newDistrict = idmasterService.createDistrict(addDistrict, loginUserID, authToken);
        return new ResponseEntity<>(newDistrict, HttpStatus.OK);
    }

    // Update District
    @ApiOperation(response = District.class, value = "Update District") // label for swagger
    @PatchMapping("/district/{districtId}")
    public ResponseEntity<?> patchDistrict(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String countryId,
                                           @PathVariable String districtId, @RequestParam String provinceId, @RequestParam String loginUserID,
                                           @RequestBody UpdateDistrict updateDistrict, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        District updatedDistrict = idmasterService.updateDistrict(languageId, companyId, countryId, provinceId,
                districtId, updateDistrict, loginUserID, authToken);
        return new ResponseEntity<>(updatedDistrict, HttpStatus.OK);
    }

    // Delete District
    @ApiOperation(response = District.class, value = "Delete District") // label for swagger
    @DeleteMapping("/district/{districtId}")
    public ResponseEntity<?> deleteDistrict(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String countryId,
                                            @PathVariable String districtId, @RequestParam String provinceId,
                                            @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteDistrict(languageId, companyId, countryId, provinceId, districtId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find District
    @ApiOperation(response = District[].class, value = "Find District")
    @PostMapping("/district/find")
    public ResponseEntity<?> findDistrict(@Valid @RequestBody FindDistrict findDistrict,
                                          @RequestParam String authToken) throws Exception {
        District[] districtList = idmasterService.findDistrict(findDistrict, authToken);
        return new ResponseEntity<>(districtList, HttpStatus.OK);
    }


    //===========================================DistrictMapping=======================================================//

    // Get All DistrictMapping Details
    @ApiOperation(response = DistrictMapping[].class, value = "Get all DistrictMapping Details") // label for swagger
    @GetMapping("/districtMapping")
    public ResponseEntity<?> getAllDistrictMappingDetails(@RequestParam String authToken) {
        DistrictMapping[] districtMappings = idmasterService.getAllDistrictMappings(authToken);
        return new ResponseEntity<>(districtMappings, HttpStatus.OK);
    }

    // Get DistrictMapping
    @ApiOperation(response = DistrictMapping.class, value = "Get a DistrictMapping") // label for swagger
    @GetMapping("/districtMapping/{partnerId}")
    public ResponseEntity<?> getDistrictMapping(@PathVariable String partnerId, @RequestParam String languageId,
                                                @RequestParam String companyId, @RequestParam String districtId, @RequestParam String authToken) {
        DistrictMapping districtMapping = idmasterService.getDistrictMapping(languageId, companyId, partnerId, districtId, authToken);
        return new ResponseEntity<>(districtMapping, HttpStatus.OK);
    }

    // Create new DistrictMapping
    @ApiOperation(response = DistrictMapping.class, value = "Create new DistrictMapping") // label for swagger
    @PostMapping("/districtMapping")
    public ResponseEntity<?> postDistrictMapping(@RequestBody AddDistrictMapping addDistrictMapping,
                                                 @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        DistrictMapping districtMapping = idmasterService.createDistrictMapping(addDistrictMapping, loginUserID, authToken);
        return new ResponseEntity<>(districtMapping, HttpStatus.OK);
    }

    // Update DistrictMapping
    @ApiOperation(response = District.class, value = "Update DistrictMapping") // label for swagger
    @PatchMapping("/districtMapping/{partnerId}")
    public ResponseEntity<?> patchDistrictMapping(@PathVariable String partnerId, @RequestParam String languageId, @RequestParam String companyId,
                                                  @RequestParam String districtId, @RequestParam String loginUserID,
                                                  @RequestBody UpdateDistrictMapping updateDistrictMapping, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        DistrictMapping districtMapping = idmasterService.updateDistrictMapping(languageId, companyId, partnerId,
                districtId, updateDistrictMapping, loginUserID, authToken);
        return new ResponseEntity<>(districtMapping, HttpStatus.OK);
    }

    // Delete DistrictMapping
    @ApiOperation(response = DistrictMapping.class, value = "Delete DistrictMapping") // label for swagger
    @DeleteMapping("/districtMapping/{partnerId}")
    public ResponseEntity<?> deleteDistrictMapping(@PathVariable String partnerId, @RequestParam String languageId,
                                                   @RequestParam String companyId, @RequestParam String districtId,
                                                   @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteDistrictMapping(languageId, companyId, partnerId, districtId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find DistrictMapping
    @ApiOperation(response = DistrictMapping[].class, value = "Find DistrictMapping") // label for swagger
    @PostMapping("/districtMapping/find")
    public ResponseEntity<?> findDistrictMapping(@RequestBody FindDistrictMapping findDistrictMapping,
                                                 @RequestParam String authToken) throws Exception {
        DistrictMapping[] districtMappings = idmasterService.findDistrictMappings(findDistrictMapping, authToken);
        return new ResponseEntity<>(districtMappings, HttpStatus.OK);
    }


    //===============================================CurrencyExchangeRate==========================================================

    // Get All CurrencyExchangeRate Details
    @ApiOperation(response = CurrencyExchangeRate[].class, value = "Get all CurrencyExchangeRate details")
    // label for swagger
    @GetMapping("/currencyExchangeRate")
    public ResponseEntity<?> getAllCurrencyExchangeRate(@RequestParam String authToken) {
        CurrencyExchangeRate[] currencyExchangeRate = idmasterService.getAllCurrencyExchangeRate(authToken);
        return new ResponseEntity<>(currencyExchangeRate, HttpStatus.OK);
    }

    // Get CurrencyExchangeRate
    @ApiOperation(response = CurrencyExchangeRate.class, value = "Get a CurrencyExchangeRate") // label for swagger
    @GetMapping("/currencyExchangeRate/{fromCurrencyId}")
    public ResponseEntity<?> getCurrencyExchangeRate(@PathVariable String fromCurrencyId, @RequestParam String languageId,
                                                     @RequestParam String companyId, @RequestParam String toCurrencyId,
                                                     @RequestParam String authToken) {
        CurrencyExchangeRate dbCurrencyExchangeRate = idmasterService.getCurrencyExchangeRate(
                languageId, companyId, fromCurrencyId, toCurrencyId, authToken);
        return new ResponseEntity<>(dbCurrencyExchangeRate, HttpStatus.OK);
    }

    // Create CurrencyExchangeRate
    @ApiOperation(response = CurrencyExchangeRate.class, value = "Create new CurrencyExchangeRate") // label for swagger
    @PostMapping("/currencyExchangeRate")
    public ResponseEntity<?> postCurrencyExchangeRate(@RequestBody AddCurrencyExchangeRate addCurrencyExchangeRate,
                                                      @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        CurrencyExchangeRate createdCurrencyExchangeRate = idmasterService.createCurrencyExchangeRate
                (addCurrencyExchangeRate, loginUserID, authToken);
        return new ResponseEntity<>(createdCurrencyExchangeRate, HttpStatus.OK);
    }

    // Update CurrencyExchangeRate
    @ApiOperation(response = CurrencyExchangeRate.class, value = "Update CurrencyExchangeRate") // label for swagger
    @PatchMapping("/currencyExchangeRate/{fromCurrencyId}")
    public ResponseEntity<?> patchCurrencyExchangeRate(@PathVariable String fromCurrencyId, @RequestParam String languageId,
                                                       @RequestParam String companyId, @RequestParam String toCurrencyId,
                                                       @RequestParam String loginUserID, @RequestBody UpdateCurrencyExchangeRate updateCurrencyExchangeRate,
                                                       @RequestParam String authToken) {
        CurrencyExchangeRate updatedCurrencyExchangeRate = idmasterService.updateCurrencyExchangeRate
                (languageId, companyId, fromCurrencyId, toCurrencyId, updateCurrencyExchangeRate, loginUserID, authToken);
        return new ResponseEntity<>(updatedCurrencyExchangeRate, HttpStatus.OK);
    }

    // Delete CurrencyExchangeRate
    @ApiOperation(response = CurrencyExchangeRate.class, value = "Delete CurrencyExchangeRate") // label for swagger
    @DeleteMapping("/currencyExchangeRate/{fromCurrencyId}")
    public ResponseEntity<?> deleteCurrencyExchangeRate(@PathVariable String fromCurrencyId, @RequestParam String languageId,
                                                        @RequestParam String companyId, @RequestParam String toCurrencyId,
                                                        @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteCurrencyExchangeRate(languageId, companyId, fromCurrencyId, toCurrencyId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find CurrencyExchangeRate
    @ApiOperation(response = CurrencyExchangeRate[].class, value = "Find CurrencyExchangeRate")//label for swagger
    @PostMapping("/currencyExchangeRate/find")
    public CurrencyExchangeRate[] findCurrencyExchangeRate(@RequestBody FindCurrencyExchangeRate findCurrencyExchangeRate,
                                                           @RequestParam String authToken) throws Exception {
        return idmasterService.findCurrencyExchangeRate(findCurrencyExchangeRate, authToken);
    }

    //===================================================Event==========================================================
    // Get all Event Details
    @ApiOperation(response = Event.class, value = "Get All Event Details") // label for swagger
    @GetMapping("/event")
    public ResponseEntity<?> getAllEventDetails(@RequestParam String authToken) {
        Event[] event = idmasterService.getAllEvents(authToken);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    // Get Event
    @ApiOperation(response = Event.class, value = "Get Event") // label for swagger
    @GetMapping("/event/{eventCode}")
    public ResponseEntity<?> getEvent(@PathVariable String eventCode, @RequestParam String languageId,
                                      @RequestParam String companyId, @RequestParam String authToken) {
        Event event = idmasterService.getEvent(languageId, companyId, eventCode, authToken);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    // Create Event
    @ApiOperation(response = Event.class, value = "Create new Event") // label for swagger
    @PostMapping("/event")
    public ResponseEntity<?> createEvent(@RequestBody AddEvent addEvent, @RequestParam String loginUserID, @RequestParam String authToken) {
        Event event = idmasterService.createEvent(addEvent, loginUserID, authToken);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    // Update Event
    @ApiOperation(response = Event.class, value = "Update Event") // label for swagger
    @PatchMapping("/event/{eventCode}")
    public ResponseEntity<?> updateEvent(@PathVariable String eventCode, @RequestParam String languageId,
                                         @RequestParam String companyId, @RequestParam String loginUserID,
                                         @RequestBody UpdateEvent updateEvent, @RequestParam String authToken) {
        Event event = idmasterService.updateEvent(languageId, companyId, eventCode, updateEvent, loginUserID, authToken);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    // Delete Event
    @ApiOperation(response = Event.class, value = "Delete Event") // label for swagger
    @DeleteMapping("/event/{eventCode}")
    public ResponseEntity<?> deleteEvent(@PathVariable String eventCode, @RequestParam String languageId,
                                         @RequestParam String companyId,
                                         @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteEvent(languageId, companyId, eventCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Event
    @ApiOperation(response = Event[].class, value = "Find Event") //label for swagger
    @PostMapping("/event/find")
    public Event[] findEvent(@RequestBody FindEvent findEvent, @RequestParam String authToken) throws Exception {
        return idmasterService.findEvent(findEvent, authToken);
    }

    //==============================================OpStatus====================================================
    // Get All OpStatus Details
    @ApiOperation(response = OpStatus[].class, value = "Get all OpStatus details") // label for swagger
    @GetMapping("/opStatus")
    public ResponseEntity<?> getOpStatuses(@RequestParam String authToken) {
        OpStatus[] opStatuses = idmasterService.getOpStatuses(authToken);
        return new ResponseEntity<>(opStatuses, HttpStatus.OK);
    }

    // Get OpStatus
    @ApiOperation(response = OpStatus.class, value = "Get OpStatus") // label for swagger
    @GetMapping("/opStatus/{statusCode}")
    public ResponseEntity<?> getOpStatus(@PathVariable String statusCode, @RequestParam String languageId,
                                         @RequestParam String companyId, @RequestParam String authToken) {
        OpStatus dbOpStatus = idmasterService.getOpStatus(companyId, languageId, statusCode, authToken);
        return new ResponseEntity<>(dbOpStatus, HttpStatus.OK);
    }

    // Create new OpStatus
    @ApiOperation(response = OpStatus.class, value = "Create new OpStatus") // label for swagger
    @PostMapping("/opStatus")
    public ResponseEntity<?> postOpStatus(@RequestBody AddOpStatus newOpStatus,
                                          @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        OpStatus createOpStatus = idmasterService.createOpStatus(newOpStatus, loginUserID, authToken);
        return new ResponseEntity<>(createOpStatus, HttpStatus.OK);
    }

    // Update OpStatus
    @ApiOperation(response = OpStatus.class, value = "Update OpStatus") // label for swagger
    @PatchMapping("/opStatus/{statusCode}")
    public ResponseEntity<?> updateOpStatus(@PathVariable String statusCode, @RequestParam String languageId,
                                            @RequestParam String companyId, @RequestParam String loginUserID,
                                            @RequestBody UpdateOpStatus updateOpStatus, @RequestParam String authToken) {
        OpStatus updatedOpStatus = idmasterService.updateOpStatus(companyId, languageId, statusCode, loginUserID, updateOpStatus, authToken);
        return new ResponseEntity<>(updatedOpStatus, HttpStatus.OK);
    }

    // Delete OpStatus
    @ApiOperation(response = OpStatus.class, value = "Delete OpStatus") // label for swagger
    @DeleteMapping("/opStatus/{statusCode}")
    public ResponseEntity<?> deleteOpStatus(@PathVariable String statusCode, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteOpStatus(companyId, languageId, statusCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find OpStatus
    @ApiOperation(response = OpStatus[].class, value = "Find OpStatus")//label for swagger
    @PostMapping("/opStatus/find")
    public OpStatus[] findOpStatus(@RequestBody FindOpStatus findOpStatus, @RequestParam String authToken)
            throws Exception {
        return idmasterService.findOpStatus(findOpStatus, authToken);
    }

    //---------------------------------------------------Module----------------------------------------------------------
    // Get All Module Details
    @ApiOperation(response = Module.class, value = "Get all Module details") // label for swagger
    @GetMapping("/module")
    public ResponseEntity<?> getAllModule(@RequestParam String authToken) {
        Module[] modules = idmasterService.getAllModule(authToken);
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    // Get ModuleList
    @ApiOperation(response = Module.class, value = "Get ModuleList") // label for swagger
    @GetMapping("/module/modulelist/{moduleId}")
    public ResponseEntity<?> getModuleList(@PathVariable String moduleId, @RequestParam String companyId,
                                           @RequestParam String languageId, @RequestParam String authToken) {
        Module[] dbModule = idmasterService.getModuleList(companyId, languageId, moduleId, authToken);
        return new ResponseEntity<>(dbModule, HttpStatus.OK);
    }

    // Get a Module
    @ApiOperation(response = Module.class, value = "Get a Module") // label for swagger
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<?> getModule(@PathVariable String moduleId, @RequestParam Long subMenuId, @RequestParam Long menuId,
                                       @RequestParam String companyId, @RequestParam String languageId, @RequestParam String authToken) {
        Module dbModule = idmasterService.getModule(menuId, companyId, languageId, moduleId, subMenuId, authToken);
        return new ResponseEntity<>(dbModule, HttpStatus.OK);
    }

    // Create Module
    @ApiOperation(response = Module.class, value = "Create Module") // label for swagger
    @PostMapping("/module/bulk")
    public ResponseEntity<?> postModule(@Valid @RequestBody List<AddModule> addModule,
                                        @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Module[] module = idmasterService.createModule(addModule, loginUserID, authToken);
        return new ResponseEntity<>(module, HttpStatus.OK);
    }

    // Update Module
    @ApiOperation(response = Module.class, value = "Update Module") // label for swagger
    @PatchMapping("/module/{moduleId}")
    public ResponseEntity<?> patchModule(@PathVariable String moduleId, @RequestParam String companyId,
                                         @RequestParam String languageId, @RequestParam String loginUserID,
                                         @RequestBody List<UpdateModule> updateModule, @RequestParam String authToken) {
        Module[] module = idmasterService.updateModule(moduleId, companyId, languageId, loginUserID, updateModule, authToken);
        return new ResponseEntity<>(module, HttpStatus.OK);
    }

    // Delete Module
    @ApiOperation(response = Module.class, value = "Delete Module") // label for swagger
    @DeleteMapping("/module/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable String moduleId,
                                          @RequestParam String companyId, @RequestParam String languageId,
                                          @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteModule(moduleId, companyId, languageId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Modules
    @ApiOperation(response = Module[].class, value = "Find Module") //label for swagger
    @PostMapping("/module/find")
    public Module[] findModule(@RequestBody FindModule findModule, @RequestParam String authToken) throws Exception {
        return idmasterService.findModule(findModule, authToken);
    }

    //==================================================SpecialApproval====================================================
    // Get All SpecialApproval Details
    @ApiOperation(response = SpecialApproval[].class, value = "Get all SpecialApproval details") // label for swagger
    @GetMapping("/specialApproval")
    public ResponseEntity<?> getAllSpecialApproval(@RequestParam String authToken) {
        SpecialApproval[] specialApprovals = idmasterService.getAllSpecialApproval(authToken);
        return new ResponseEntity<>(specialApprovals, HttpStatus.OK);
    }

    // Get SpecialApproval
    @ApiOperation(response = SpecialApproval.class, value = "Get SpecialApproval") // label for swagger
    @GetMapping("/specialApproval/{specialApprovalId}")
    public ResponseEntity<?> getSpecialApproval(@PathVariable String specialApprovalId, @RequestParam String languageId,
                                                @RequestParam String companyId, @RequestParam String authToken) {
        SpecialApproval dbSpecialApproval = idmasterService.getSpecialApproval(companyId, languageId, specialApprovalId, authToken);
        return new ResponseEntity<>(dbSpecialApproval, HttpStatus.OK);
    }


    // Create new SpecialApproval
    @ApiOperation(response = SpecialApproval.class, value = "Create new SpecialApproval") // label for swagger
    @PostMapping("/specialApproval")
    public ResponseEntity<?> postSpecialApproval(@RequestBody AddSpecialApproval addSpecialApproval,
                                                 @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        SpecialApproval createSpecialApproval = idmasterService.createSpecialApproval(addSpecialApproval, loginUserID, authToken);
        return new ResponseEntity<>(createSpecialApproval, HttpStatus.OK);
    }

    // Update SpecialApproval
    @ApiOperation(response = SpecialApproval.class, value = "Update SpecialApproval") // label for swagger
    @PatchMapping("/specialApproval/{specialApprovalId}")
    public ResponseEntity<?> updateSpecialApproval(@PathVariable String specialApprovalId, @RequestParam String languageId,
                                                   @RequestParam String companyId, @RequestParam String loginUserID,
                                                   @RequestBody UpdateSpecialApproval updateSpecialApproval, @RequestParam String authToken) {
        SpecialApproval updatedSpecialApproval = idmasterService.updateSpecialApproval(companyId, languageId, specialApprovalId,
                loginUserID, updateSpecialApproval, authToken);
        return new ResponseEntity<>(updatedSpecialApproval, HttpStatus.OK);
    }

    // Delete SpecialApproval
    @ApiOperation(response = SpecialApproval.class, value = "Delete SpecialApproval") // label for swagger
    @DeleteMapping("/specialApproval/{specialApprovalId}")
    public ResponseEntity<?> deleteSpecialApproval(@PathVariable String specialApprovalId, @RequestParam String languageId,
                                                   @RequestParam String companyId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteSpecialApproval(companyId, languageId, specialApprovalId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find SpecialApproval
    @ApiOperation(response = SpecialApproval[].class, value = "Find SpecialApproval")//label for swagger
    @PostMapping("/specialApproval/find")
    public SpecialApproval[] findSpecialApproval(@RequestBody FindSpecialApproval findSpecialApproval,
                                                 @RequestParam String authToken) throws Exception {
        return idmasterService.findSpecialApproval(findSpecialApproval, authToken);
    }

    //==================================================RoleAccess ====================================================

    // Get All RoleAccess Details
    @ApiOperation(response = RoleAccess[].class, value = "Get all RoleAccess details") // label for swagger
    @GetMapping("/roleAccess")
    public ResponseEntity<?> getAllRoleAccess(@RequestParam String authToken) {
        RoleAccess[] roleAccess = idmasterService.getAllRoleAccess(authToken);
        return new ResponseEntity<>(roleAccess, HttpStatus.OK);
    }

//    // Get RoleAccess
//    @ApiOperation(response = RoleAccess.class, value = "Get RoleAccess") // label for swagger
//    @GetMapping("/roleAccess/{roleId}")
//    public ResponseEntity<?> getRoleAccess(@PathVariable Long roleId, @RequestParam Long menuId, @RequestParam String companyId,
//                                           @RequestParam String languageId, @RequestParam Long subMenuId, @RequestParam String authToken) {
//        RoleAccess roleAccesses = idmasterService.getRoleAccess(companyId, languageId, roleId, menuId, subMenuId, authToken);
//        return new ResponseEntity<>(roleAccesses, HttpStatus.OK);
//    }

    // Get RoleAccess List
    @ApiOperation(response = RoleAccess[].class, value = "Get RoleAccess List") // label for swagger
    @GetMapping("/roleAccess/{roleId}")
    public ResponseEntity<?> getRoleAccessList(@PathVariable Long roleId, @RequestParam String companyId,
                                               @RequestParam String languageId, @RequestParam String authToken) {
        RoleAccess[] roleAccesses = idmasterService.getRoleAccessList(companyId, languageId, roleId, authToken);
        return new ResponseEntity<>(roleAccesses, HttpStatus.OK);
    }


    // Create new RoleAccess
    @ApiOperation(response = RoleAccess.class, value = "Create new RoleAccess") // label for swagger
    @PostMapping("/roleAccess")
    public ResponseEntity<?> postRoleAccess(@Valid @RequestBody List<AddRoleAccess> newRoleAccess,
                                            @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        RoleAccess[] createRoleAccess = idmasterService.createRoleAccess(newRoleAccess, loginUserID, authToken);
        return new ResponseEntity<>(createRoleAccess, HttpStatus.OK);
    }

    // Update RoleAccess
    @ApiOperation(response = RoleAccess.class, value = "Update RoleAccess") // label for swagger
    @PatchMapping("/roleAccess/{roleId}")
    public ResponseEntity<?> updateRoleAccess(@PathVariable Long roleId, @RequestParam Long menuId, @RequestParam String companyId,
                                              @RequestParam String languageId, @RequestParam Long subMenuId,
                                              @RequestBody AddRoleAccess[] updateRoleAccess,
                                              @RequestParam String loginUserID, @RequestParam String authToken) {
        RoleAccess[] updatedRoleAccess = idmasterService.updateRoleAccess(companyId, languageId, roleId, loginUserID, updateRoleAccess, authToken);
        return new ResponseEntity<>(updatedRoleAccess, HttpStatus.OK);
    }

    // Delete RoleAccess
    @ApiOperation(response = RoleAccess.class, value = "Delete RoleAccess") // label for swagger
    @DeleteMapping("/roleAccess/{roleId}")
    public ResponseEntity<?> deleteRoleAccess(@PathVariable Long roleId, @RequestParam String companyId,
                                              @RequestParam String languageId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteRoleAccess(languageId, companyId, roleId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find RoleAccess
    @ApiOperation(response = RoleAccess[].class, value = "Find RoleAccess")//label for swagger
    @PostMapping("/roleAccess/find")
    public RoleAccess[] findRoleAccess(@RequestBody FindRoleAccess findRoleAccess, @RequestParam String authToken) throws Exception {
        return idmasterService.findRoleAccess(findRoleAccess, authToken);
    }

    //==================================================UserType====================================================
    // Get All UserType Details
    @ApiOperation(response = UserType[].class, value = "Get all UserType details") // label for swagger
    @GetMapping("/userType")
    public ResponseEntity<?> getAllUserType(@RequestParam String authToken) {
        UserType[] userTypes = idmasterService.getAllUserType(authToken);
        return new ResponseEntity<>(userTypes, HttpStatus.OK);
    }

    // Get UserType
    @ApiOperation(response = UserType.class, value = "Get UserType") // label for swagger
    @GetMapping("/userType/{userTypeId}")
    public ResponseEntity<?> getUserType(@PathVariable Long userTypeId, @RequestParam String languageId,
                                         @RequestParam String companyId, @RequestParam String authToken) {
        UserType dbUserType = idmasterService.getUserType(userTypeId, companyId, languageId, authToken);
        return new ResponseEntity<>(dbUserType, HttpStatus.OK);
    }

    // Create UserType
    @ApiOperation(response = UserType.class, value = "Create new UserType") // label for swagger
    @PostMapping("/userType")
    public ResponseEntity<?> postUserType(@RequestBody AddUserType addUserType,
                                          @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        UserType createUserType = idmasterService.createUserType(addUserType, loginUserID, authToken);
        return new ResponseEntity<>(createUserType, HttpStatus.OK);
    }

    // Update UserType
    @ApiOperation(response = UserType.class, value = "Update UserType") // label for swagger
    @PatchMapping("/userType/{userTypeId}")
    public ResponseEntity<?> updateUserType(@PathVariable Long userTypeId, @RequestParam String languageId,
                                            @RequestParam String companyId, @RequestParam String loginUserID,
                                            @RequestBody UpdateUserType updateUserType, @RequestParam String authToken) {
        UserType updatedUserType = idmasterService.updateUserType(companyId, languageId, userTypeId, loginUserID, updateUserType, authToken);
        return new ResponseEntity<>(updatedUserType, HttpStatus.OK);
    }

    // Delete UserType
    @ApiOperation(response = UserType.class, value = "Delete UserType") // label for swagger
    @DeleteMapping("/userType/{userTypeId}")
    public ResponseEntity<?> deleteUserType(@PathVariable Long userTypeId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteUserType(companyId, languageId, userTypeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find UserType
    @ApiOperation(response = UserType[].class, value = "Find UserType")//label for swagger
    @PostMapping("/userType/find")
    public UserType[] findUserType(@RequestBody FindUserType findUserType,
                                   @RequestParam String authToken) throws Exception {
        return idmasterService.findUserType(findUserType, authToken);
    }

    /* --------------------------------User Management-------------------------------------------------------------------------------------*/
    // Login - Validate User
    @ApiOperation(response = Optional.class, value = "Login User") // label for swagger
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> loginUser(@RequestParam String userID, @RequestParam String password,
                                       @RequestParam String authToken, @RequestParam(required = false) String version) {
        try {
            UserManagement loggedUser = idmasterService.validateUserID(userID, password, authToken, version);
            log.info("LoginUser::: " + loggedUser);
            log.info("version::: " + version);
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Invalid user");
            String errMsg = "Either UserId is invalid or Password does not match.";
            CustomErrorResponse error = new CustomErrorResponse();
            error.setTimestamp(LocalDateTime.now());
            error.setError(errMsg);
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(response = UserManagement.class, value = "Get all UserManagement details") // label for swagger
    @GetMapping("/usermanagement")
    public ResponseEntity<?> getAllUserManagementDetails(@RequestParam String authToken) {
        UserManagement[] userManagementList = idmasterService.getAllUserManagements(authToken);
        return new ResponseEntity<>(userManagementList, HttpStatus.OK);
    }

    @ApiOperation(response = UserManagement.class, value = "Get a UserManagement") // label for swagger
    @GetMapping("/usermanagement/{userId}")
    public ResponseEntity<?> getUserManagement(@PathVariable String userId, @RequestParam String companyId, @RequestParam String languageId,
                                               @RequestParam String authToken) {
        UserManagement dbUserManagement = idmasterService.getUserManagement(userId, companyId, languageId, authToken);
        return new ResponseEntity<>(dbUserManagement, HttpStatus.OK);
    }

    @ApiOperation(response = UserManagement.class, value = "Create UserManagement") // label for swagger
    @PostMapping("/usermanagement")
    public ResponseEntity<?> postUserManagement(@Valid @RequestBody AddUserManagement addUserManagement,
                                                @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        UserManagement createdUserManagement = idmasterService.createUserManagement(addUserManagement, loginUserID, authToken);
        return new ResponseEntity<>(createdUserManagement, HttpStatus.OK);
    }

    @ApiOperation(response = UserManagement.class, value = "Update UserManagement") // label for swagger
    @PatchMapping("/usermanagement/{userId}")
    public ResponseEntity<?> patchUserManagement(@PathVariable String userId, @RequestParam String companyId, @RequestParam String languageId,
                                                 @RequestParam String loginUserID, @RequestParam String authToken,
                                                 @Valid @RequestBody UpdateUserManagement updateUserManagement) {
        UserManagement updatedUserManagement =
                idmasterService.updateUserManagement(userId, loginUserID, companyId, languageId, updateUserManagement, authToken);
        return new ResponseEntity<>(updatedUserManagement, HttpStatus.OK);
    }

    @ApiOperation(response = UserManagement.class, value = "Delete UserManagement") // label for swagger
    @DeleteMapping("/usermanagement/{userId}")
    public ResponseEntity<?> deleteUserManagement(@PathVariable String userId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteUserManagement(userId, companyId, languageId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //FIND
    @ApiOperation(response = UserManagement[].class, value = "Find UserManagement")//label for swagger
    @PostMapping("/usermanagement/find")
    public UserManagement[] findUserManagement(@RequestBody FindUserManagement findUserManagement, @RequestParam String authToken) throws Exception {
        return idmasterService.findUserManagement(findUserManagement, authToken);
    }


    //==================================================Notification===================================================
    // Get All Notification Details

    @ApiOperation(response = Notification[].class, value = "Get all Notification details")
    @GetMapping("/notification")
    public ResponseEntity<?> getAllNotification(@RequestParam String authToken) {
        Notification[] notifications = idmasterService.getAllNotification(authToken);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // Get Notification
    @ApiOperation(response = Notification.class, value = "Get Notification") // label for swagger
    @GetMapping("/notification/{notificationId}")
    public ResponseEntity<?> getNotification(@PathVariable String notificationId, @RequestParam String languageId,
                                             @RequestParam String companyId, @RequestParam String authToken) {
        Notification dbNotification = idmasterService.getNotification
                (languageId, companyId, notificationId, authToken);
        return new ResponseEntity<>(dbNotification, HttpStatus.OK);
    }

    // Create Notification
    @ApiOperation(response = Notification.class, value = "Create new Notification") // label for swagger
    @PostMapping("/notification")
    public ResponseEntity<?> postNotification(@RequestBody AddNotification addNotification, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Notification createdNotification = idmasterService.createNotification
                (addNotification, loginUserID, authToken);
        return new ResponseEntity<>(createdNotification, HttpStatus.OK);
    }

    // Update Notification
    @ApiOperation(response = Notification.class, value = "Update Notification") // label for swagger
    @PatchMapping("/notification/{notificationId}")
    public ResponseEntity<?> patchNotification(@PathVariable String notificationId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestBody UpdateNotification updateNotification, @RequestParam String loginUserID,
                                               @RequestParam String authToken) {
        Notification updatedNotification = idmasterService.updateNotification
                (languageId, companyId, notificationId, updateNotification, loginUserID, authToken);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    // Delete Notification
    @ApiOperation(response = Notification.class, value = "Delete Notification") // label for swagger
    @DeleteMapping("/notification/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable String notificationId, @RequestParam String languageId,
                                                @RequestParam String companyId, @RequestParam String loginUserID,
                                                @RequestParam String authToken) {
        idmasterService.deleteNotification(languageId, companyId, notificationId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Notification
    @ApiOperation(response = Notification[].class, value = "Find Notification")
    @PostMapping("/notification/find")
    public ResponseEntity<?> findNotification(@Valid @RequestBody FindNotification findNotification,
                                              @RequestParam String authToken) throws Exception {
        Notification[] notificationList = idmasterService.findNotification(findNotification, authToken);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    //==================================================AirportCode===================================================
    // Get All AirportCode Details

    @ApiOperation(response = AirportCode[].class, value = "Get all AirportCode details")
    @GetMapping("/airportCode")
    public ResponseEntity<?> getAllAirportCode(@RequestParam String authToken) {
        AirportCode[] airportCode = idmasterService.getAllAirportCode(authToken);
        return new ResponseEntity<>(airportCode, HttpStatus.OK);
    }

    // Get AirportCode
    @ApiOperation(response = AirportCode.class, value = "Get AirportCode") // label for swagger
    @GetMapping("/airportCode/{airportCode}")
    public ResponseEntity<?> getAirportCode(@PathVariable String airportCode, @RequestParam String languageId,
                                            @RequestParam String companyId, @RequestParam String authToken) {
        AirportCode dbAirportCode = idmasterService.getAirportCode(languageId, companyId, airportCode, authToken);
        return new ResponseEntity<>(dbAirportCode, HttpStatus.OK);
    }

    // Create AirportCode
    @ApiOperation(response = AirportCode.class, value = "Create new AirportCode") // label for swagger
    @PostMapping("/airportCode")
    public ResponseEntity<?> postAirportCode(@RequestBody AddAirportCode addAirportCode, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        AirportCode createdAirportCode = idmasterService.createAirportCode(addAirportCode, loginUserID, authToken);
        return new ResponseEntity<>(createdAirportCode, HttpStatus.OK);
    }

    // Update AirportCode
    @ApiOperation(response = AirportCode.class, value = "Update AirportCode") // label for swagger
    @PatchMapping("/airportCode/{airportCode}")
    public ResponseEntity<?> patchAirportCode(@PathVariable String airportCode, @RequestParam String languageId, @RequestParam String companyId,
                                              @RequestBody UpdateAirportCode updateAirportCode, @RequestParam String loginUserID,
                                              @RequestParam String authToken) {
        AirportCode updatedAirportCode = idmasterService.updateAirportCode(languageId, companyId, airportCode, updateAirportCode, loginUserID, authToken);
        return new ResponseEntity<>(updatedAirportCode, HttpStatus.OK);
    }

    // Delete AirportCode
    @ApiOperation(response = AirportCode.class, value = "Delete AirportCode") // label for swagger
    @DeleteMapping("/airportCode/{airportCode}")
    public ResponseEntity<?> deleteAirportCode(@PathVariable String airportCode, @RequestParam String languageId,
                                               @RequestParam String companyId, @RequestParam String loginUserID,
                                               @RequestParam String authToken) {
        idmasterService.deleteAirportCode(languageId, companyId, airportCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find AirportCode
    @ApiOperation(response = AirportCode[].class, value = "Find AirportCode")
    @PostMapping("/airportCode/find")
    public ResponseEntity<?> findAirportCode(@Valid @RequestBody FindAirportCode findAirportCode,
                                             @RequestParam String authToken) throws Exception {
        AirportCode[] airportCodeList = idmasterService.findAirportCode(findAirportCode, authToken);
        return new ResponseEntity<>(airportCodeList, HttpStatus.OK);
    }

    //==================================================StatusEvent===================================================
    // Get All StatusEvent Details

    @ApiOperation(response = StatusEvent[].class, value = "Get all StatusEvent details")
    @GetMapping("/statusevent")
    public ResponseEntity<?> getAllStatusEvent(@RequestParam String authToken) {
        StatusEvent[] statusEvents = idmasterService.getAllStatusEvent(authToken);
        return new ResponseEntity<>(statusEvents, HttpStatus.OK);
    }

    // Get StatusEvent
    @ApiOperation(response = StatusEvent.class, value = "Get StatusEvent") // label for swagger
    @GetMapping("/statusevent/{typeId}")
    public ResponseEntity<?> getStatusEvent(@PathVariable String typeId, @RequestParam String companyId,
                                            @RequestParam String languageId, @RequestParam String authToken) {
        StatusEvent dbStatusEvent = idmasterService.getStatusEvent(companyId, languageId, typeId, authToken);
        return new ResponseEntity<>(dbStatusEvent, HttpStatus.OK);
    }

    // Create StatusEvent
    @ApiOperation(response = StatusEvent.class, value = "Create new StatusEvent") // label for swagger
    @PostMapping("/statusevent")
    public ResponseEntity<?> postStatusEvent(@RequestBody AddStatusEvent addStatusEvent, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        StatusEvent createdStatusEvent = idmasterService.createStatusEvent(addStatusEvent, loginUserID, authToken);
        return new ResponseEntity<>(createdStatusEvent, HttpStatus.OK);
    }

    // Update StatusEvent
    @ApiOperation(response = StatusEvent.class, value = "Update StatusEvent") // label for swagger
    @PatchMapping("/statusevent/{typeId}")
    public ResponseEntity<?> patchStatusEvent(@PathVariable String typeId, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestBody UpdateStatusEvent updateStatusEvent, @RequestParam String loginUserID,
                                              @RequestParam String authToken) {
        StatusEvent updatedStatusEvent = idmasterService.updateStatusEvent(companyId, languageId, typeId, updateStatusEvent, loginUserID, authToken);
        return new ResponseEntity<>(updatedStatusEvent, HttpStatus.OK);
    }

    // Delete StatusEvent
    @ApiOperation(response = StatusEvent.class, value = "Delete StatusEvent") // label for swagger
    @DeleteMapping("/statusevent/{typeId}")
    public ResponseEntity<?> deleteStatusEvent(@PathVariable String typeId, @RequestParam String companyId,
                                               @RequestParam String languageId, @RequestParam String loginUserID,
                                               @RequestParam String authToken) {
        idmasterService.deleteStatusEvent(companyId, languageId, typeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find StatusEvent
    @ApiOperation(response = StatusEvent[].class, value = "Find StatusEvent")
    @PostMapping("/statusevent/find")
    public ResponseEntity<?> findStatusEvent(@Valid @RequestBody FindStatusEvent findStatusEvent,
                                             @RequestParam String authToken) throws Exception {
        StatusEvent[] statusEventList = idmasterService.findStatusEvent(findStatusEvent, authToken);
        return new ResponseEntity<>(statusEventList, HttpStatus.OK);
    }


    //==================================================UOM===================================================
    // Get All UOM Details
    @ApiOperation(response = Uom[].class, value = "Get all UOM details")
    @GetMapping("/uom")
    public ResponseEntity<?> getAllUom(@RequestParam String authToken) {
        Uom[] uom = idmasterService.getAllUom(authToken);
        return new ResponseEntity<>(uom, HttpStatus.OK);
    }

    // Get UOM
    @ApiOperation(response = Uom.class, value = "Get UOM") // label for swagger
    @GetMapping("/uom/{uomId}")
    public ResponseEntity<?> getUom(@PathVariable String uomId, @RequestParam String companyId,
                                    @RequestParam String languageId, @RequestParam String authToken) {
        Uom dbUom = idmasterService.getUom(companyId, languageId, uomId, authToken);
        return new ResponseEntity<>(dbUom, HttpStatus.OK);
    }

    // Create UOM
    @ApiOperation(response = Uom.class, value = "Create new UOM") // label for swagger
    @PostMapping("/uom")
    public ResponseEntity<?> postUom(@RequestBody AddUom addUom, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Uom createdUom = idmasterService.createUom(addUom, loginUserID, authToken);
        return new ResponseEntity<>(createdUom, HttpStatus.OK);
    }

    // Update UOM
    @ApiOperation(response = Uom.class, value = "Update UOM") // label for swagger
    @PatchMapping("/uom/{uomId}")
    public ResponseEntity<?> patchUom(@PathVariable String uomId, @RequestParam String companyId, @RequestParam String languageId,
                                      @RequestBody UpdateUom updateUom, @RequestParam String loginUserID,
                                      @RequestParam String authToken) {
        Uom updatedUom = idmasterService.updateUom(companyId, languageId, uomId, updateUom, loginUserID, authToken);
        return new ResponseEntity<>(updatedUom, HttpStatus.OK);
    }

    // Delete UOM
    @ApiOperation(response = Uom.class, value = "Delete UOM") // label for swagger
    @DeleteMapping("/uom/{uomId}")
    public ResponseEntity<?> deleteUom(@PathVariable String uomId, @RequestParam String companyId,
                                       @RequestParam String languageId, @RequestParam String loginUserID,
                                       @RequestParam String authToken) {
        idmasterService.deleteUom(companyId, languageId, uomId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find UOM
    @ApiOperation(response = Uom[].class, value = "Find UOM")
    @PostMapping("/uom/find")
    public ResponseEntity<?> findUom(@Valid @RequestBody FindUom findUom,
                                     @RequestParam String authToken) throws Exception {
        Uom[] uomList = idmasterService.findUom(findUom, authToken);
        return new ResponseEntity<>(uomList, HttpStatus.OK);
    }


    //==============================================AppUser====================================================
    // Get All AppUser Details
    @ApiOperation(response = AppUser[].class, value = "Get all AppUser details") // label for swagger
    @GetMapping("/appUser")
    public ResponseEntity<?> getAppUsers(@RequestParam String authToken) {
        AppUser[] userAppUser = idmasterService.getAppUsers(authToken);
        return new ResponseEntity<>(userAppUser, HttpStatus.OK);
    }

    // Get AppUser
    @ApiOperation(response = AppUser.class, value = "Get AppUser") // label for swagger
    @GetMapping("/appUser/{appUserId}")
    public ResponseEntity<?> getAppUser(@PathVariable String appUserId, @RequestParam String languageId,
                                        @RequestParam String companyId, @RequestParam String authToken) {
        AppUser dbAppUser = idmasterService.getAppUser(companyId, languageId, appUserId, authToken);
        return new ResponseEntity<>(dbAppUser, HttpStatus.OK);
    }

    // Create new AppUser
    @ApiOperation(response = AppUser.class, value = "Create new AppUser") // label for swagger
    @PostMapping("/appUser")
    public ResponseEntity<?> postAppUser(@RequestBody AddAppUser newAppUser,
                                         @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        AppUser createAppUser = idmasterService.createAppUser(newAppUser, loginUserID, authToken);
        return new ResponseEntity<>(createAppUser, HttpStatus.OK);
    }

    // Update AppUser
    @ApiOperation(response = AppUser.class, value = "Update AppUser") // label for swagger
    @RequestMapping(value = "/appUser/{appUserId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateAppUser(@PathVariable String appUserId, @RequestParam String languageId,
                                           @RequestParam String companyId, @RequestParam String loginUserID,
                                           @RequestBody UpdateAppUser updateAppUser, @RequestParam String authToken) {
        AppUser UpdateAppUser =
                idmasterService.updateAppUser(companyId, languageId, appUserId, loginUserID, updateAppUser, authToken);
        return new ResponseEntity<>(UpdateAppUser, HttpStatus.OK);
    }

    // Delete AppUser
    @ApiOperation(response = AppUser.class, value = "Delete AppUser") // label for swagger
    @DeleteMapping("/appUser/{appUserId}")
    public ResponseEntity<?> deleteAppUser(@PathVariable String appUserId, @RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteAppUser(companyId, languageId, appUserId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find AppUser
    @ApiOperation(response = AppUser[].class, value = "Find AppUser")//label for swagger
    @PostMapping("/appUser/find")
    public AppUser[] findAppUser(@RequestBody FindAppUser findAppUser,
                                 @RequestParam String authToken) throws Exception {
        return idmasterService.findAppUser(findAppUser, authToken);
    }

    /* --------------------------------User Management-------------------------------------------------------------------------------------*/
    // Login - Validate User
    @ApiOperation(response = Optional.class, value = "Login Mobile AppUser") // label for swagger
    @RequestMapping(value = "/login/mobile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> loginMobileAppUser(@RequestParam String appUserId, @RequestParam String password,
                                                @RequestParam String authToken, @RequestParam(required = false) String version,@RequestParam String appUserType) {
//        try {
            AppUser loggedUser = idmasterService.validateMobileUserID(appUserId, password, authToken, version,appUserType);
            log.info("LoginUser::: " + loggedUser);
            log.info("version::: " + version);
            log.info("appUserType::: " + appUserType);
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
//        } catch (BadRequestException e) {
//            log.error("Invalid user");
//            String errMsg = "Either UserId is invalid or Password does not match.";
//            CustomErrorResponse error = new CustomErrorResponse();
//            error.setTimestamp(LocalDateTime.now());
//            error.setError(errMsg);
//            error.setStatus(HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
//        }
    }

    // CreateHHtNotification
    @ApiOperation(response = HhtNotification.class, value = "Create HhtNotification") // label for swagger
    @PostMapping("/hhtnotification/createnotification")
    public ResponseEntity<?> createHhtNotification(@Valid @RequestBody HhtNotification newHhtNotification, @RequestParam String loginUserID,
                                                   @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        HhtNotification createHhtNotification = idmasterService.createHhtNotification(newHhtNotification, loginUserID, authToken);
        return new ResponseEntity<>(createHhtNotification, HttpStatus.OK);
    }

    // GetHHtNotification
    @ApiOperation(response = HhtNotification.class, value = "Get a HhtNotification") // label for swagger
    @GetMapping("/hhtnotification/getnotification")
    public ResponseEntity<?> getHhtNotification(@RequestParam String companyId, @RequestParam String languageId, @RequestParam String deviceId,
                                                @RequestParam String userId, @RequestParam String tokenId, @RequestParam String authToken) {
        HhtNotification dbHhtNotification = idmasterService.getHhtNotification(companyId, languageId, deviceId, userId, tokenId, authToken);

        log.info("HhtNotification : " + dbHhtNotification);
        return new ResponseEntity<>(dbHhtNotification, HttpStatus.OK);
    }

    // Update HHtNotification
    @ApiOperation(response = HhtNotification.class, value = "Update HhtNotification") // label for swagger
    @PatchMapping("/hhtnotification/update")
    public ResponseEntity<?> patchHhtNotification(@Valid @RequestBody HhtNotification updateHhtNotification, @RequestParam String loginUserID,
                                                  @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        HhtNotification updatedHhtNotification = idmasterService.updateHhtNotification(updateHhtNotification, loginUserID, authToken);
        return new ResponseEntity<>(updatedHhtNotification, HttpStatus.OK);
    }

    // Find HHtNotifications
    @ApiOperation(response = HhtNotification[].class, value = "Find HhtNotifications") // label for swagger
    @PostMapping("/hhtnotification/find")
    public ResponseEntity<?> findHhtNotification(@Valid @RequestBody FindHhtNotification findHhtNotification, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        HhtNotification[] hhtNotifications = idmasterService.findHhtNotifications(findHhtNotification, authToken);
        return new ResponseEntity<>(hhtNotifications, HttpStatus.OK);
    }

    // Get All HHtNotifications
    @ApiOperation(response = HhtNotification[].class, value = "Get All HhtNotifications") // label for swagger
    @PostMapping("/hhtnotification/getAll")
    public ResponseEntity<?> getAllHhtNotifications(@RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        HhtNotification[] hhtNotifications = idmasterService.getAllHhtNotifications(authToken);
        return new ResponseEntity<>(hhtNotifications, HttpStatus.OK);
    }

    //==================================================Route===================================================
    // Get All Route Details
    @ApiOperation(response = Route[].class, value = "Get all Route details")
    @GetMapping("/route")
    public ResponseEntity<?> getAllRoute(@RequestParam String authToken) {
        Route[] route = idmasterService.getAllRoute(authToken);
        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    // Get Route
    @ApiOperation(response = Route.class, value = "Get Route") // label for swagger
    @GetMapping("/route/{routeId}")
    public ResponseEntity<?> getRoute(@PathVariable String routeId, @RequestParam String companyId,
                                      @RequestParam String languageId, @RequestParam String authToken) {
        Route dbRoute = idmasterService.getRoute(companyId, languageId, routeId, authToken);
        return new ResponseEntity<>(dbRoute, HttpStatus.OK);
    }

    // Create Route
    @ApiOperation(response = Route.class, value = "Create new Route") // label for swagger
    @PostMapping("/route")
    public ResponseEntity<?> postRoute(@RequestBody AddRoute addRoute, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Route createdRoute = idmasterService.createRoute(addRoute, loginUserID, authToken);
        return new ResponseEntity<>(createdRoute, HttpStatus.OK);
    }

    // Update Route
    @ApiOperation(response = Route.class, value = "Update Route") // label for swagger
    @PatchMapping("/route/{routeId}")
    public ResponseEntity<?> patchRoute(@PathVariable String routeId, @RequestParam String companyId, @RequestParam String languageId,
                                        @RequestBody UpdateRoute updateRoute, @RequestParam String loginUserID,
                                        @RequestParam String authToken) {
        Route updatedRoute = idmasterService.updateRoute(companyId, languageId, routeId, updateRoute, loginUserID, authToken);
        return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
    }

    // Delete Route
    @ApiOperation(response = Route.class, value = "Delete Route") // label for swagger
    @DeleteMapping("/route/{routeId}")
    public ResponseEntity<?> deleteRoute(@PathVariable String routeId, @RequestParam String companyId,
                                         @RequestParam String languageId, @RequestParam String loginUserID,
                                         @RequestParam String authToken) {
        idmasterService.deleteRoute(companyId, languageId, routeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Route
    @ApiOperation(response = Route[].class, value = "Find Route")
    @PostMapping("/route/find")
    public ResponseEntity<?> findRoute(@Valid @RequestBody FindRoute findRoute,
                                       @RequestParam String authToken) throws Exception {
        Route[] routeList = idmasterService.findRoute(findRoute, authToken);
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }

    //==================================================BillMode===================================================
    // Get All BillMode Details
    @ApiOperation(response = BillMode[].class, value = "Get all BillMode details")
    @GetMapping("/billmode")
    public ResponseEntity<?> getAllBillMode(@RequestParam String authToken) {
        BillMode[] billMode = idmasterService.getAllBillMode(authToken);
        return new ResponseEntity<>(billMode, HttpStatus.OK);
    }

    // Get BillMode
    @ApiOperation(response = BillMode.class, value = "Get BillMode") // label for swagger
    @GetMapping("/billmode/{billModeId}")
    public ResponseEntity<?> getBillMode(@PathVariable String billModeId, @RequestParam String companyId,
                                         @RequestParam String languageId, @RequestParam String authToken) {
        BillMode dbBillMode = idmasterService.getBillMode(companyId, languageId, billModeId, authToken);
        return new ResponseEntity<>(dbBillMode, HttpStatus.OK);
    }

    // Create BillMode
    @ApiOperation(response = BillMode.class, value = "Create new BillMode") // label for swagger
    @PostMapping("/billmode")
    public ResponseEntity<?> postBillMode(@RequestBody AddBillMode addBillMode, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        BillMode createdBillMode = idmasterService.createBillMode(addBillMode, loginUserID, authToken);
        return new ResponseEntity<>(createdBillMode, HttpStatus.OK);
    }

    // Update BillMode
    @ApiOperation(response = BillMode.class, value = "Update BillMode") // label for swagger
    @PatchMapping("/billmode/{billModeId}")
    public ResponseEntity<?> patchBillMode(@PathVariable String billModeId, @RequestParam String companyId, @RequestParam String languageId,
                                           @RequestBody UpdateBillMode updateBillMode, @RequestParam String loginUserID,
                                           @RequestParam String authToken) {
        BillMode updatedBillMode = idmasterService.updateBillMode(companyId, languageId, billModeId, updateBillMode, loginUserID, authToken);
        return new ResponseEntity<>(updatedBillMode, HttpStatus.OK);
    }

    // Delete BillMode
    @ApiOperation(response = BillMode.class, value = "Delete BillMode") // label for swagger
    @DeleteMapping("/billmode/{billModeId}")
    public ResponseEntity<?> deleteBillMode(@PathVariable String billModeId, @RequestParam String companyId,
                                            @RequestParam String languageId, @RequestParam String loginUserID,
                                            @RequestParam String authToken) {
        idmasterService.deleteBillMode(companyId, languageId, billModeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find BillMode
    @ApiOperation(response = BillMode[].class, value = "Find BillMode")
    @PostMapping("/billmode/find")
    public ResponseEntity<?> findBillMode(@Valid @RequestBody FindBillMode findBillMode,
                                          @RequestParam String authToken) throws Exception {
        BillMode[] billModeList = idmasterService.findBillMode(findBillMode, authToken);
        return new ResponseEntity<>(billModeList, HttpStatus.OK);
    }

    //==================================================Vehicle====================================================
    // Get All Vehicle Details
    @ApiOperation(response = Vehicle[].class, value = "Get all Vehicle details") // label for swagger
    @GetMapping("/vehicle")
    public ResponseEntity<?> getAllVehicles(@RequestParam String authToken) {
        Vehicle[] userVehicle = idmasterService.getAllVehicles(authToken);
        return new ResponseEntity<>(userVehicle, HttpStatus.OK);
    }

    // Get Vehicle
    @ApiOperation(response = Vehicle.class, value = "Get Vehicle") // label for swagger
    @GetMapping("/vehicle/{vehicleRegNumber}")
    public ResponseEntity<?> getVehicle(@PathVariable String vehicleRegNumber, @RequestParam String languageId,
                                        @RequestParam String companyId, @RequestParam String authToken) {
        Vehicle dbVehicle = idmasterService.getVehicle(companyId, languageId, vehicleRegNumber, authToken);
        return new ResponseEntity<>(dbVehicle, HttpStatus.OK);
    }


    // Create new Vehicle
    @ApiOperation(response = Vehicle.class, value = "Create new Vehicle") // label for swagger
    @PostMapping("/vehicle")
    public ResponseEntity<?> postVehicle(@RequestBody AddVehicle newVehicle,
                                         @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Vehicle createVehicle = idmasterService.createVehicle(newVehicle, loginUserID, authToken);
        return new ResponseEntity<>(createVehicle, HttpStatus.OK);
    }

    // Update Vehicle
    @ApiOperation(response = Vehicle.class, value = "Update Vehicle") // label for swagger
    @PatchMapping("/vehicle/{vehicleRegNumber}")
    public ResponseEntity<?> updateVehicle(@PathVariable String vehicleRegNumber, @RequestParam String languageId,
                                           @RequestParam String companyId, @RequestParam String loginUserID,
                                           @RequestBody UpdateVehicle updateVehicle, @RequestParam String authToken) {
        Vehicle updatedVehicle = idmasterService.updateVehicle(companyId, languageId, vehicleRegNumber, loginUserID, updateVehicle, authToken);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }

    // Delete Vehicle
    @ApiOperation(response = Vehicle.class, value = "Delete Vehicle") // label for swagger
    @DeleteMapping("/vehicle/{vehicleRegNumber}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String vehicleRegNumber, @RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteVehicle(companyId, languageId, vehicleRegNumber, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Vehicle
    @ApiOperation(response = Vehicle[].class, value = "Find Vehicle")//label for swagger
    @PostMapping("/vehicle/find")
    public Vehicle[] findVehicle(@RequestBody FindVehicle findVehicle,
                                 @RequestParam String authToken) throws Exception {
        return idmasterService.findVehicle(findVehicle, authToken);
    }

    //==================================================DriverRouteAssignment====================================================
    // Get All DriverRouteAssignment Details
    @ApiOperation(response = DriverRouteAssignment[].class, value = "Get all DriverRouteAssignment details")
    // label for swagger
    @GetMapping("/driverRouteAssignment")
    public ResponseEntity<?> getAllDriverRouteAssignments(@RequestParam String authToken) {
        DriverRouteAssignment[] driverRouteAssignment = idmasterService.getAllDriverRouteAssignments(authToken);
        return new ResponseEntity<>(driverRouteAssignment, HttpStatus.OK);
    }

    // Get DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment.class, value = "Get DriverRouteAssignment") // label for swagger
    @GetMapping("/driverRouteAssignment/{courierId}")
    public ResponseEntity<?> getDriverRouteAssignment(@PathVariable String courierId, @RequestParam String companyId,
                                                      @RequestParam String languageId, @RequestParam String routeId, @RequestParam String vehicleRegNumber, @RequestParam String assignedHubCode, @RequestParam String authToken) {
        DriverRouteAssignment dbDriverRouteAssignment = idmasterService.getDriverRouteAssignment(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode, authToken);
        return new ResponseEntity<>(dbDriverRouteAssignment, HttpStatus.OK);
    }

    // Create new DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment.class, value = "Create new DriverRouteAssignment")
    // label for swagger
    @PostMapping("/driverRouteAssignment")
    public ResponseEntity<?> postDriverRouteAssignment(@Valid @RequestBody AddDriverRouteAssignment newDriverRouteAssignment,
                                                       @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        DriverRouteAssignment createDriverRouteAssignment = idmasterService.createDriverRouteAssignment(newDriverRouteAssignment, loginUserID, authToken);
        return new ResponseEntity<>(createDriverRouteAssignment, HttpStatus.OK);
    }

    // Update DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment.class, value = "Update DriverRouteAssignment") // label for swagger
    @PatchMapping("/driverRouteAssignment/{courierId}")
    public ResponseEntity<?> updateDriverRouteAssignment(@PathVariable String courierId, @RequestParam String languageId,
                                                         @RequestParam String companyId, @RequestParam String routeId, @RequestParam String vehicleRegNumber, @RequestParam String assignedHubCode, @RequestParam String loginUserID,
                                                         @RequestBody UpdateDriverRouteAssignment updateDriverRouteAssignment, @RequestParam String authToken) {
        DriverRouteAssignment updatedDriverRouteAssignment = idmasterService.updateDriverRouteAssignment(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode, loginUserID, updateDriverRouteAssignment, authToken);
        return new ResponseEntity<>(updatedDriverRouteAssignment, HttpStatus.OK);
    }

    // Delete DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment.class, value = "Delete DriverRouteAssignment") // label for swagger
    @DeleteMapping("/driverRouteAssignment/{courierId}")
    public ResponseEntity<?> deleteDriverRouteAssignment(@PathVariable String courierId, @RequestParam String languageId, @RequestParam String companyId,
                                                         @RequestParam String routeId, @RequestParam String vehicleRegNumber, @RequestParam String assignedHubCode, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteDriverRouteAssignment(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find DriverRouteAssignment
    @ApiOperation(response = DriverRouteAssignment[].class, value = "Find DriverRouteAssignment")//label for swagger
    @PostMapping("/driverRouteAssignment/find")
    public DriverRouteAssignment[] findDriverRouteAssignment(@RequestBody FindDriverRouteAssignment findDriverRouteAssignment,
                                                             @RequestParam String authToken) throws Exception {
        return idmasterService.findDriverRouteAssignment(findDriverRouteAssignment, authToken);
    }

    //==================================================ZoneMaster===================================================
    // Get All ZoneMaster Details
    @ApiOperation(response = ZoneMaster[].class, value = "Get all ZoneMaster details")
    @GetMapping("/zonemaster")
    public ResponseEntity<?> getAllZoneMaster(@RequestParam String authToken) {
        ZoneMaster[] zoneMaster = idmasterService.getAllZoneMaster(authToken);
        return new ResponseEntity<>(zoneMaster, HttpStatus.OK);
    }

    // Get ZoneMaster
    @ApiOperation(response = ZoneMaster.class, value = "Get ZoneMaster") // label for swagger
    @GetMapping("/zonemaster/{zoneId}")
    public ResponseEntity<?> getZoneMaster(@PathVariable String zoneId, @RequestParam String companyId,
                                           @RequestParam String languageId,@RequestParam String zoneType,
                                           @RequestParam String hubCode, @RequestParam String authToken) {
        ZoneMaster dbZoneMaster = idmasterService.getZoneMaster(companyId, languageId, zoneId, zoneType,hubCode, authToken);
        return new ResponseEntity<>(dbZoneMaster, HttpStatus.OK);
    }

    // Create ZoneMaster
    @ApiOperation(response = ZoneMaster.class, value = "Create new ZoneMaster") // label for swagger
    @PostMapping("/zonemaster")
    public ResponseEntity<?> postZoneMaster(@Valid @RequestBody AddZoneMaster addZoneMaster, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ZoneMaster createdZoneMaster = idmasterService.createZoneMaster(addZoneMaster, loginUserID, authToken);
        return new ResponseEntity<>(createdZoneMaster, HttpStatus.OK);
    }

    // Update ZoneMaster
    @ApiOperation(response = ZoneMaster.class, value = "Update ZoneMaster") // label for swagger
    @PatchMapping("/zonemaster/{zoneId}")
    public ResponseEntity<?> patchZoneMaster(@PathVariable String zoneId, @RequestParam String companyId, @RequestParam String languageId,
                                             @RequestParam String zoneType, @RequestBody UpdateZoneMaster updateZoneMaster,
                                             @RequestParam String hubCode, @RequestParam String loginUserID,
                                             @RequestParam String authToken) {
        ZoneMaster updatedZoneMaster = idmasterService.updateZoneMaster(companyId, languageId, zoneId, zoneType, hubCode, updateZoneMaster, loginUserID, authToken);
        return new ResponseEntity<>(updatedZoneMaster, HttpStatus.OK);
    }

    // Delete ZoneMaster
    @ApiOperation(response = ZoneMaster.class, value = "Delete ZoneMaster") // label for swagger
    @DeleteMapping("/zonemaster/{zoneId}")
    public ResponseEntity<?> deleteZoneMaster(@PathVariable String zoneId, @RequestParam String companyId,
                                              @RequestParam String languageId, @RequestParam String zoneType,
                                              @RequestParam String hubCode,
                                              @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteZoneMaster(companyId, languageId, zoneId, zoneType, hubCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ZoneMaster
    @ApiOperation(response = ZoneMaster[].class, value = "Find ZoneMaster")
    @PostMapping("/zonemaster/find")
    public ResponseEntity<?> findZoneMaster(@Valid @RequestBody FindZoneMaster findZoneMaster,
                                            @RequestParam String authToken) throws Exception {
        ZoneMaster[] zoneMasterList = idmasterService.findZoneMaster(findZoneMaster, authToken);
        return new ResponseEntity<>(zoneMasterList, HttpStatus.OK);
    }

    //==================================================CourierPartner===================================================
    // Get All CourierPartner Details
    @ApiOperation(response = CourierPartner[].class, value = "Get all CourierPartner details")
    @GetMapping("/courierPartner")
    public ResponseEntity<?> getAllCourierPartner(@RequestParam String authToken) {
        CourierPartner[] courierPartners = idmasterService.getAllCourierPartner(authToken);
        return new ResponseEntity<>(courierPartners, HttpStatus.OK);
    }

    // Get CourierPartner
    @ApiOperation(response = CourierPartner.class, value = "Get CourierPartner") // label for swagger
    @GetMapping("/courierPartner/{courierPartnerId}")
    public ResponseEntity<?> getCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId,
                                                @RequestParam String languageId, @RequestParam String partnerId, @RequestParam String authToken) {
        CourierPartner dbCourierPartner = idmasterService.getCourierPartner(companyId, languageId, courierPartnerId, partnerId, authToken);
        return new ResponseEntity<>(dbCourierPartner, HttpStatus.OK);
    }

    // Create CourierPartner
    @ApiOperation(response = CourierPartner.class, value = "Create new CourierPartner") // label for swagger
    @PostMapping("/courierPartner")
    public ResponseEntity<?> postCourierPartner(@Valid @RequestBody AddCourierPartner addCourierPartner, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        CourierPartner createdCourierPartner = idmasterService.createCourierPartner(addCourierPartner, loginUserID, authToken);
        return new ResponseEntity<>(createdCourierPartner, HttpStatus.OK);
    }

    // Update CourierPartner
    @ApiOperation(response = CourierPartner.class, value = "Update CourierPartner") // label for swagger
    @PatchMapping("/courierPartner/{courierPartnerId}")
    public ResponseEntity<?> patchCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestBody UpdateCourierPartner updateCourierPartner, @RequestParam String loginUserID,
                                                  @RequestParam String partnerId, @RequestParam String authToken) {
        CourierPartner updatedCourierPartner = idmasterService.updateCourierPartner(companyId, languageId, courierPartnerId, partnerId,
                updateCourierPartner, loginUserID, authToken);
        return new ResponseEntity<>(updatedCourierPartner, HttpStatus.OK);
    }

    // Delete CourierPartner
    @ApiOperation(response = CourierPartner.class, value = "Delete CourierPartner") // label for swagger
    @DeleteMapping("/courierPartner/{courierPartnerId}")
    public ResponseEntity<?> deleteCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId,
                                                   @RequestParam String languageId, @RequestParam String loginUserID,
                                                   @RequestParam String partnerId, @RequestParam String authToken) {
        idmasterService.deleteCourierPartner(companyId, languageId, courierPartnerId, partnerId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find CourierPartner
    @ApiOperation(response = CourierPartner[].class, value = "Find CourierPartner")
    @PostMapping("/courierPartner/find")
    public ResponseEntity<?> findCourierPartner(@Valid @RequestBody FindCourierPartner findCourierPartner,
                                                 @RequestParam String authToken) throws Exception {
        CourierPartner[] courierPartnerList = idmasterService.findCourierPartner(findCourierPartner, authToken);
        return new ResponseEntity<>(courierPartnerList, HttpStatus.OK);
    }

    // GetAll NotificationMessage
    @ApiOperation(response = NotificationMessage.class, value = "Get all NotificationMessage details")
    // label for swagger
    @GetMapping("/notificationMessage")
    public ResponseEntity<?> getNotificationMessage(@RequestParam String authToken) {
        NotificationMessage[] notificationMessages = idmasterService.getAllNotificationMessage(authToken);
        return new ResponseEntity<>(notificationMessages, HttpStatus.OK);
    }

    // Find NotificationMessage
    @ApiOperation(response = NotificationMessage.class, value = "Search NotificationMessage") // label for swagger
    @PostMapping("/notificationMessage/findNotificationMessage")
    public NotificationMessage[] findNotification(@RequestBody FindNotificationMessage findNotificationMessage,
                                                  @RequestParam String authToken) throws Exception {
        return idmasterService.findNotificationMessage(findNotificationMessage, authToken);
    }

    //Update Notification
    @ApiOperation(response = NotificationMessage.class, value = "Update Notification Message")
    @PatchMapping("/notificationMessage/update")
    public ResponseEntity<?> updateNotification(@RequestBody List<NotificationMessage> updateNotification,
                                                @RequestParam String loginUserID, @RequestParam String authToken){
        NotificationMessage[] dbNotification = idmasterService.updateNotificationMessage(updateNotification, loginUserID, authToken);
        return new ResponseEntity<>(dbNotification, HttpStatus.OK);
    }

    //Delete NotificationMessage
    @ApiOperation(response = NotificationMessage.class, value = "Delete Notification Message")
    @DeleteMapping("/notificationMessage/delete")
    public ResponseEntity<?> deleteNotificationMessage(@RequestParam(required = false) Long notificationId, @RequestParam(required = false) String companyId,
                                                       @RequestParam(required = false) String languageId, @RequestParam(required = false) String houseAirwayBill,
                                                       @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteNotificationMessage(notificationId, companyId, languageId, houseAirwayBill, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //==================================================StorageTypeMaster===================================================
    // Get All StorageTypeMaster Details
    @ApiOperation(response = StorageTypeMaster[].class, value = "Get all StorageTypeMaster details")
    @GetMapping("/storageTypeMaster")
    public ResponseEntity<?> getAllStorageTypeMaster(@RequestParam String authToken) {
        StorageTypeMaster[] storageTypeMaster = idmasterService.getAllStorageTypeMaster(authToken);
        return new ResponseEntity<>(storageTypeMaster, HttpStatus.OK);
    }

    // Get StorageTypeMaster
    @ApiOperation(response = StorageTypeMaster.class, value = "Get StorageTypeMaster") // label for swagger
    @GetMapping("/storageTypeMaster/{storageTypeId}")
    public ResponseEntity<?> getStorageTypeMaster(@PathVariable String storageTypeId, @RequestParam String companyId,
                                                  @RequestParam String languageId, @RequestParam String authToken) {
        StorageTypeMaster dbStorageTypeMaster = idmasterService.getStorageTypeMaster(companyId, languageId, storageTypeId, authToken);
        return new ResponseEntity<>(dbStorageTypeMaster, HttpStatus.OK);
    }

    // Create StorageTypeMaster
    @ApiOperation(response = StorageTypeMaster.class, value = "Create new StorageTypeMaster") // label for swagger
    @PostMapping("/storageTypeMaster")
    public ResponseEntity<?> postStorageTypeMaster(@Valid @RequestBody AddStorageTypeMaster addStorageTypeMaster, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        StorageTypeMaster createdStorageTypeMaster = idmasterService.createStorageTypeMaster(addStorageTypeMaster, loginUserID, authToken);
        return new ResponseEntity<>(createdStorageTypeMaster, HttpStatus.OK);
    }

    // Update StorageTypeMaster
    @ApiOperation(response = StorageTypeMaster.class, value = "Update StorageTypeMaster") // label for swagger
    @PatchMapping("/storageTypeMaster/{storageTypeId}")
    public ResponseEntity<?> patchStorageTypeMaster(@PathVariable String storageTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                                    @RequestBody UpdateStorageTypeMaster updateStorageTypeMaster, @RequestParam String loginUserID,
                                                    @RequestParam String authToken) {
        StorageTypeMaster updatedStorageTypeMaster = idmasterService.updateStorageTypeMaster(companyId, languageId, storageTypeId, updateStorageTypeMaster, loginUserID, authToken);
        return new ResponseEntity<>(updatedStorageTypeMaster, HttpStatus.OK);
    }

    // Delete StorageTypeMaster
    @ApiOperation(response = StorageTypeMaster.class, value = "Delete StorageTypeMaster") // label for swagger
    @DeleteMapping("/storageTypeMaster/{storageTypeId}")
    public ResponseEntity<?> deleteStorageTypeMaster(@PathVariable String storageTypeId, @RequestParam String companyId,
                                                     @RequestParam String languageId, @RequestParam String loginUserID,
                                                     @RequestParam String authToken) {
        idmasterService.deleteStorageTypeMaster(companyId, languageId, storageTypeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find StorageTypeMaster
    @ApiOperation(response = StorageTypeMaster[].class, value = "Find StorageTypeMaster")
    @PostMapping("/storageTypeMaster/find")
    public ResponseEntity<?> findStorageTypeMaster(@Valid @RequestBody FindStorageTypeMaster findStorageTypeMaster,
                                                   @RequestParam String authToken) throws Exception {
        StorageTypeMaster[] storageTypeMasterList = idmasterService.findStorageTypeMaster(findStorageTypeMaster, authToken);
        return new ResponseEntity<>(storageTypeMasterList, HttpStatus.OK);
    }


    //==================================================ZoneTypeMaster===================================================
    // Get All ZoneTypeMaster Details
    @ApiOperation(response = ZoneTypeMaster[].class, value = "Get all ZoneTypeMaster details")
    @GetMapping("/zoneTypeMaster")
    public ResponseEntity<?> getAllZoneTypeMaster(@RequestParam String authToken) {
        ZoneTypeMaster[] zoneTypeMaster = idmasterService.getAllZoneTypeMaster(authToken);
        return new ResponseEntity<>(zoneTypeMaster, HttpStatus.OK);
    }

    // Get ZoneTypeMaster
    @ApiOperation(response = ZoneTypeMaster.class, value = "Get ZoneTypeMaster") // label for swagger
    @GetMapping("/zoneTypeMaster/{zoneTypeId}")
    public ResponseEntity<?> getZoneTypeMaster(@PathVariable String zoneTypeId, @RequestParam String companyId,
                                                  @RequestParam String languageId, @RequestParam String authToken) {
        ZoneTypeMaster dbZoneTypeMaster = idmasterService.getZoneTypeMaster(companyId, languageId, zoneTypeId, authToken);
        return new ResponseEntity<>(dbZoneTypeMaster, HttpStatus.OK);
    }

    // Create ZoneTypeMaster
    @ApiOperation(response = ZoneTypeMaster.class, value = "Create new ZoneTypeMaster") // label for swagger
    @PostMapping("/zoneTypeMaster")
    public ResponseEntity<?> postZoneTypeMaster(@Valid @RequestBody AddZoneTypeMaster addZoneTypeMaster, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ZoneTypeMaster createdZoneTypeMaster = idmasterService.createZoneTypeMaster(addZoneTypeMaster, loginUserID, authToken);
        return new ResponseEntity<>(createdZoneTypeMaster, HttpStatus.OK);
    }

    // Update ZoneTypeMaster
    @ApiOperation(response = ZoneTypeMaster.class, value = "Update ZoneTypeMaster") // label for swagger
    @PatchMapping("/zoneTypeMaster/{zoneTypeId}")
    public ResponseEntity<?> patchZoneTypeMaster(@PathVariable String zoneTypeId, @RequestParam String companyId, @RequestParam String languageId,
                                                     @RequestBody UpdateZoneTypeMaster updateZoneTypeMaster, @RequestParam String loginUserID,
                                                    @RequestParam String authToken) {
        ZoneTypeMaster updatedZoneTypeMaster = idmasterService.updateZoneTypeMaster(companyId, languageId, zoneTypeId, updateZoneTypeMaster, loginUserID, authToken);
        return new ResponseEntity<>(updatedZoneTypeMaster, HttpStatus.OK);
    }

    // Delete ZoneTypeMaster
    @ApiOperation(response = ZoneTypeMaster.class, value = "Delete ZoneTypeMaster") // label for swagger
    @DeleteMapping("/zoneTypeMaster/{zoneTypeId}")
    public ResponseEntity<?> deleteZoneTypeMaster(@PathVariable String zoneTypeId, @RequestParam String companyId,
                                                     @RequestParam String languageId, @RequestParam String loginUserID,
                                                     @RequestParam String authToken) {
        idmasterService.deleteZoneTypeMaster(companyId, languageId, zoneTypeId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ZoneTypeMaster
    @ApiOperation(response = ZoneTypeMaster[].class, value = "Find ZoneTypeMaster")
    @PostMapping("/zoneTypeMaster/find")
    public ResponseEntity<?> findZoneTypeMaster(@Valid @RequestBody FindZoneTypeMaster findZoneTypeMaster,
                                                   @RequestParam String authToken) throws Exception {
        ZoneTypeMaster[] zoneTypeMastersList = idmasterService.findZoneTypeMaster(findZoneTypeMaster, authToken);
        return new ResponseEntity<>(zoneTypeMastersList, HttpStatus.OK);
    }

    //==================================================LogicMaster===================================================
    // Get All LogicMaster Details
    @ApiOperation(response = LogicMaster[].class, value = "Get all LogicMaster details")
    @GetMapping("/logicMaster")
    public ResponseEntity<?> getAllLogicMaster(@RequestParam String authToken) {
        LogicMaster[] logicMaster = idmasterService.getAllLogicMaster(authToken);
        return new ResponseEntity<>(logicMaster, HttpStatus.OK);
    }

    // Get LogicMaster
    @ApiOperation(response = LogicMaster.class, value = "Get LogicMaster") // label for swagger
    @GetMapping("/logicMaster/{consoleCountId}")
    public ResponseEntity<?> getLogicMaster(@PathVariable String consoleCountId, @RequestParam String companyId,
                                           @RequestParam String languageId, @RequestParam String authToken) {
        LogicMaster dbLogicMaster = idmasterService.getLogicMaster(companyId, languageId, consoleCountId, authToken);
        return new ResponseEntity<>(dbLogicMaster, HttpStatus.OK);
    }

    // Create LogicMaster
    @ApiOperation(response = LogicMaster.class, value = "Create new LogicMaster") // label for swagger
    @PostMapping("/logicMaster")
    public ResponseEntity<?> postLogicMaster(@Valid @RequestBody AddLogicMaster addLogicMaster, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        LogicMaster createdLogicMaster = idmasterService.createLogicMaster(addLogicMaster, loginUserID, authToken);
        return new ResponseEntity<>(createdLogicMaster, HttpStatus.OK);
    }

    // Update LogicMaster
    @ApiOperation(response = LogicMaster.class, value = "Update LogicMaster") // label for swagger
    @PatchMapping("/logicMaster/{consoleCountId}")
    public ResponseEntity<?> patchLogicMaster(@PathVariable String consoleCountId, @RequestParam String companyId, @RequestParam String languageId,
                                             @RequestBody UpdateLogicMaster updateLogicMaster, @RequestParam String loginUserID,
                                             @RequestParam String authToken) {
        LogicMaster updatedLogicMaster = idmasterService.updateLogicMaster(companyId, languageId, consoleCountId, updateLogicMaster, loginUserID, authToken);
        return new ResponseEntity<>(updatedLogicMaster, HttpStatus.OK);
    }

    // Delete LogicMaster
    @ApiOperation(response = LogicMaster.class, value = "Delete LogicMaster") // label for swagger
    @DeleteMapping("/logicMaster/{consoleCountId}")
    public ResponseEntity<?> deleteLogicMaster(@PathVariable String consoleCountId, @RequestParam String companyId,
                                              @RequestParam String languageId, @RequestParam String loginUserID,
                                              @RequestParam String authToken) {
        idmasterService.deleteLogicMaster(companyId, languageId, consoleCountId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find LogicMaster
    @ApiOperation(response = LogicMaster[].class, value = "Find LogicMaster")
    @PostMapping("/logicMaster/find")
    public ResponseEntity<?> findLogicMaster(@Valid @RequestBody FindLogicMaster findLogicMaster,
                                            @RequestParam String authToken) throws Exception {
        LogicMaster[] logicMasterList = idmasterService.findLogicMaster(findLogicMaster, authToken);
        return new ResponseEntity<>(logicMasterList, HttpStatus.OK);
    }

    /*   ------- sort master -------*/

    //Create Sorting Master
    @ApiOperation(response = SortingMaster.class, value = "Create Sorting Master")
    @PostMapping("/sortmaster/create/list")
    public ResponseEntity<?> createSortMaster(@Valid @RequestBody List<AddSortMaster> sortingMaster, @RequestParam String loginUserID, @RequestParam String authToken) throws IOException, CsvException {
        SortingMaster[] sortingMasters = idmasterService.createSortMaster(sortingMaster, loginUserID, authToken);
        return new ResponseEntity<>(sortingMasters, HttpStatus.OK);
    }

    // Update Sort Master
    @ApiOperation(response = SortingMaster.class, value = "Update Sorting Master") // label for swagger
    @PatchMapping("/sortmaster/update/list")
    public ResponseEntity<?> updateSortMaster(@RequestBody List<UpdateSortMaster> sortingMaster, @RequestParam String loginUserID,
                                              @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        SortingMaster[] sortingMaster1 = idmasterService.updateSortMaster(sortingMaster, loginUserID, authToken);
        return new ResponseEntity<>(sortingMaster1, HttpStatus.OK);
    }

    // Get All Sort Master List
    @ApiOperation(response = SortingMaster.class, value = "Get All Sort Master Data")
    @GetMapping("/sortmaster")
    public ResponseEntity<?> getAllSortMaster(@RequestParam String authToken) {
        SortingMaster[] sortingMasters = idmasterService.getAllSortMaster(authToken);
        return new ResponseEntity<>(sortingMasters, HttpStatus.OK);

    }

    // Delete Sort Master
    @PostMapping("/sortmaster/delete/list")
    public ResponseEntity<?> deleteSortMaster(@RequestBody List<SortMasterDeleteInput> sortingMasters, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteSortMaster(sortingMasters, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    // Get a Sort master
    @ApiOperation(response = SortingMaster.class, value = "Get Sort master") // label for swagger
    @GetMapping("/sortmaster/{sortingId}")
    public ResponseEntity<?> getSortMaster(@PathVariable String sortingId, @RequestParam String languageId, @RequestParam String companyId,
                                           @RequestParam String zoneType, @RequestParam String authToken) {
        SortingMaster sortingMaster = idmasterService.getSortingMaster(languageId, companyId, sortingId, zoneType, authToken);
        return new ResponseEntity<>(sortingMaster, HttpStatus.OK);
    }

    // Create Sort Master
    @ApiOperation(response = SortingMaster.class, value = "Create new Sort master") // label for swagger
    @PostMapping("/sortmaster")
    public ResponseEntity<?> postSortMaster(@RequestBody AddSortMaster addSortMaster, @RequestParam String loginUserID,
                                            @RequestParam String authToken) {
        SortingMaster sortingMaster = idmasterService.createSingleSortMaster(addSortMaster, loginUserID, authToken);
        return new ResponseEntity<>(sortingMaster, HttpStatus.OK);
    }

    // Update Sort Master
    @ApiOperation(response = SortingMaster.class, value = "Update Sort Master") // label for swagger
    @PatchMapping("/sortmaster/{sortingId}")
    public ResponseEntity<?> updateSort(@PathVariable String sortingId, @RequestParam String languageId,
                                        @RequestParam String companyId, @RequestParam String zoneType,@RequestBody UpdateSortMaster updateSortMaster, @RequestParam String loginUserID, @RequestParam String authToken) {
        SortingMaster sortingMaster = idmasterService.updateOneSortMaster(languageId, companyId, zoneType, sortingId, updateSortMaster, loginUserID, authToken);
        return new ResponseEntity<>(sortingMaster, HttpStatus.OK);
    }

    // Delete Sort Master
    @ApiOperation(response = SortingMaster.class, value = "Delete Sort Master") // label for swagger
    @DeleteMapping("/sortmaster/{sortingId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String sortingId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String zoneType, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteOneSortMaster(languageId, companyId, zoneType,  sortingId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Sort Master
    @ApiOperation(response = SortingMaster[].class, value = "Find Sort Master") //label for swagger
    @PostMapping("/sortmaster/find")
    public SortingMaster[] findSortMaster(@RequestBody FindSortMaster findSortMaster, @RequestParam String authToken) throws Exception {
        return idmasterService.findSortMaster(findSortMaster, authToken);
    }


    /* --------------custom charge --------*/

    //Create Custom Charge
    @ApiOperation(response = CustomCharges.class, value = "Create Custom Charges")
    @PostMapping("/customCharge/create/list")
    public ResponseEntity<?> createCustomCharges(@Valid @RequestBody List<AddCustomCharge> customCharges,@RequestParam String loginUserID,@RequestParam String authToken) throws IOException, CsvException {
        CustomCharges[] customCharges1 = idmasterService.createCustomCharge(customCharges,loginUserID,authToken);
        return new ResponseEntity<>(customCharges1, HttpStatus.OK);
    }

    // Update Custom Charge
    @ApiOperation(response = CustomCharges.class, value = "Update Custom Charges") // label for swagger
    @PatchMapping("/customCharge/update/list")
    public ResponseEntity<?> updateCustomCharge(@RequestBody List<UpdateCustomCharge> customCharges, @RequestParam String loginUserID,
                                                @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        CustomCharges[] customCharges1 = idmasterService.updateCustomCharge(customCharges, loginUserID, authToken);
        return new ResponseEntity<>(customCharges1, HttpStatus.OK);
    }

    // Delete Custom Charge
    @PostMapping("/customCharge/delete/list")
    public ResponseEntity<?> deleteCustomCharge(@RequestBody List<CustomCharges> customCharges,@RequestParam String loginUserID,@RequestParam String authToken){
        idmasterService.deleteCustomCharge(customCharges,loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    // Create Custom Charge
    @ApiOperation(response = CustomCharges.class, value = "Create new CustomCharge") // label for swagger
    @PostMapping("/customCharge")
    public ResponseEntity<?> postCustomCharge(@RequestBody AddCustomCharge addCustomCharge, @RequestParam String loginUserID,
                                              @RequestParam String authToken) {
        CustomCharges customCharges = idmasterService.createCustom(addCustomCharge, loginUserID, authToken);
        return new ResponseEntity<>(customCharges, HttpStatus.OK);
    }

    // Update Custom Charge
    @ApiOperation(response = CustomCharges.class, value = "Update Custom Charge") // label for swagger
    @PatchMapping("/customCharge")
    public ResponseEntity<?> patchCustomCharge(@RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String loginUserID, @RequestBody UpdateCustomCharge updateCustomCharge, @RequestParam String authToken) {
        CustomCharges customCharges = idmasterService.updateCustom(languageId, companyId, updateCustomCharge, loginUserID, authToken);
        return new ResponseEntity<>(customCharges, HttpStatus.OK);
    }


    // Get All Custom Charge List
    @ApiOperation(response = CustomCharges.class, value = "Get All Custom Charge Data")
    @GetMapping("/customCharge")
    public ResponseEntity<?> getAllCustomCharge(@RequestParam String authToken){
        CustomCharges[] customCharges = idmasterService.getAllCustomCharge(authToken);
        return new ResponseEntity<>(customCharges,HttpStatus.OK);

    }

    // Find Custom Charge
    @ApiOperation(response = CustomCharges[].class, value = "Find Custom Charges") //label for swagger
    @PostMapping("/customCharge/find")
    public CustomCharges[] findCustomCharge(@RequestBody FindCustomCharge findCustomCharge, @RequestParam String authToken) throws Exception {
        return idmasterService.findCustomCharge(findCustomCharge, authToken);
    }

    //==================================================ReasonsListPickup====================================================
    // Get All ReasonsListPickup Details
    @ApiOperation(response = ReasonsListPickup[].class, value = "Get all ReasonsListPickup details") // label for swagger
    @GetMapping("/reasonsListPickup")
    public ResponseEntity<?> getAllReasonsListPickups(@RequestParam String authToken) {
        ReasonsListPickup[] userReasonsListPickup = idmasterService.getAllReasonsListPickups(authToken);
        return new ResponseEntity<>(userReasonsListPickup, HttpStatus.OK);
    }

    // Get ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup.class, value = "Get ReasonsListPickup") // label for swagger
    @GetMapping("/reasonsListPickup/{reasonsId}")
    public ResponseEntity<?> getReasonsListPickup(@PathVariable String reasonsId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String authToken) {
        ReasonsListPickup dbReasonsListPickup = idmasterService.getReasonsListPickup(companyId, languageId, reasonsId, authToken);
        return new ResponseEntity<>(dbReasonsListPickup, HttpStatus.OK);
    }


    // Create new ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup.class, value = "Create new ReasonsListPickup") // label for swagger
    @PostMapping("/reasonsListPickup")
    public ResponseEntity<?> postReasonsListPickup(@RequestBody AddReasonsListPickup newReasonsListPickup,
                                                   @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ReasonsListPickup createReasonsListPickup = idmasterService.createReasonsListPickup(newReasonsListPickup, loginUserID, authToken);
        return new ResponseEntity<>(createReasonsListPickup, HttpStatus.OK);
    }

    // Update ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup.class, value = "Update ReasonsListPickup") // label for swagger
    @PatchMapping("/reasonsListPickup/{reasonsId}")
    public ResponseEntity<?> updateReasonsList(@PathVariable String reasonsId, @RequestParam String languageId,
                                               @RequestParam String companyId, @RequestParam String loginUserID,
                                               @RequestBody UpdateReasonsListPickup updateReasonsListPickup, @RequestParam String authToken) {
        ReasonsListPickup updatedReasonsListPickup = idmasterService.updateReasonsListPickup(companyId, languageId, reasonsId, loginUserID, updateReasonsListPickup, authToken);
        return new ResponseEntity<>(updatedReasonsListPickup, HttpStatus.OK);
    }

    // Delete ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup.class, value = "Delete ReasonsListPickup") // label for swagger
    @DeleteMapping("/reasonsListPickup/{reasonsId}")
    public ResponseEntity<?> deleteReasonsListPickup(@PathVariable String reasonsId, @RequestParam String languageId, @RequestParam String companyId,
                                                     @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteReasonsListPickup(companyId, languageId, reasonsId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ReasonsListPickup
    @ApiOperation(response = ReasonsListPickup[].class, value = "Find ReasonsListPickup")//label for swagger
    @PostMapping("/reasonsListPickup/find")
    public ReasonsListPickup[] findReasonsListPickup(@RequestBody FindReasonsListPickup findReasonsListPickup,
                                                     @RequestParam String authToken) throws Exception {
        return idmasterService.findReasonsListPickup(findReasonsListPickup, authToken);
    }

    //==================================================ReasonsListDelivery====================================================
    // Get All ReasonsListDelivery Details
    @ApiOperation(response = ReasonsListDelivery[].class, value = "Get all ReasonsListDelivery details") // label for swagger
    @GetMapping("/reasonsListDelivery")
    public ResponseEntity<?> getAllReasonsListDeliveries(@RequestParam String authToken) {
        ReasonsListDelivery[] userReasonsListDelivery = idmasterService.getAllReasonsListDeliveries(authToken);
        return new ResponseEntity<>(userReasonsListDelivery, HttpStatus.OK);
    }

    // Get ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery.class, value = "Get ReasonsListDelivery") // label for swagger
    @GetMapping("/reasonsListDelivery/{reasonsId}")
    public ResponseEntity<?> getReasonsListDelivery(@PathVariable String reasonsId, @RequestParam String languageId,
                                                    @RequestParam String companyId, @RequestParam String authToken) {
        ReasonsListDelivery dbReasonsListDelivery = idmasterService.getReasonsListDelivery(companyId, languageId, reasonsId, authToken);
        return new ResponseEntity<>(dbReasonsListDelivery, HttpStatus.OK);
    }


    // Create new ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery.class, value = "Create new ReasonsListDelivery") // label for swagger
    @PostMapping("/reasonsListDelivery")
    public ResponseEntity<?> postReasonsListDelivery(@RequestBody AddReasonsListDelivery newReasonsListDelivery,
                                                     @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ReasonsListDelivery createReasonsListDelivery = idmasterService.createReasonsListDelivery(newReasonsListDelivery, loginUserID, authToken);
        return new ResponseEntity<>(createReasonsListDelivery, HttpStatus.OK);
    }

    // Update ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery.class, value = "Update ReasonsListDelivery") // label for swagger
    @PatchMapping("/reasonsListDelivery/{reasonsId}")
    public ResponseEntity<?> updateReasonsListDelivery(@PathVariable String reasonsId, @RequestParam String languageId,
                                                       @RequestParam String companyId, @RequestParam String loginUserID,
                                                       @RequestBody UpdateReasonsListDelivery updateReasonsListDelivery, @RequestParam String authToken) {
        ReasonsListDelivery updatedReasonsListDelivery = idmasterService.updateReasonsListDelivery(companyId, languageId, reasonsId, loginUserID, updateReasonsListDelivery, authToken);
        return new ResponseEntity<>(updatedReasonsListDelivery, HttpStatus.OK);
    }

    // Delete ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery.class, value = "Delete ReasonsListDelivery") // label for swagger
    @DeleteMapping("/reasonsListDelivery/{reasonsId}")
    public ResponseEntity<?> deleteReasonsListDelivery(@PathVariable String reasonsId, @RequestParam String languageId, @RequestParam String companyId,
                                                       @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteReasonsListDelivery(companyId, languageId, reasonsId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ReasonsListDelivery
    @ApiOperation(response = ReasonsListDelivery[].class, value = "Find ReasonsListDelivery")//label for swagger
    @PostMapping("/reasonsListDelivery/find")
    public ReasonsListDelivery[] findReasonsListDelivery(@RequestBody FindReasonsListDelivery findReasonsListDelivery,
                                                         @RequestParam String authToken) throws Exception {
        return idmasterService.findReasonsListDelivery(findReasonsListDelivery, authToken);
    }

    //==================================================Timeslot====================================================

    // Create TimeSlot
    @ApiOperation(response = TimeSlot[].class, value = "Create TimeSlot") // label for swagger
    @PostMapping("/timeSlot/create/list")
    public ResponseEntity<?> postTimeSlot(@Valid @RequestBody List<AddTimeSlot> addTimeSlot, @RequestParam String loginUserID,@RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        TimeSlot[] createTimeSlot = idmasterService.createTimeslot(addTimeSlot, loginUserID,authToken);
        return new ResponseEntity<>(createTimeSlot, HttpStatus.OK);
    }

    // Update Timeslot List
    @ApiOperation(response = TimeSlot[].class, value = "Update Timeslot List") // label for swagger
    @PatchMapping("/timeSlot/update/list")
    public ResponseEntity<?> patchTimeslotList(@RequestParam String loginUserID, @RequestBody List<AddTimeSlot> updateTimeslot,@RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        TimeSlot[] updatedTimeslot = idmasterService.updateTimeslot(updateTimeslot, loginUserID,authToken);
        return new ResponseEntity<>(updatedTimeslot, HttpStatus.OK);
    }

    // Delete Timeslot List
    @ApiOperation(response = TimeSlot[].class, value = "Delete Timeslot") // label for swagger
    @PostMapping("/timeSlot/delete/list")
    public ResponseEntity<?> deleteNprList(@RequestBody List<TimeSlot> deleteTimeslotList, @RequestParam String loginUserID,@RequestParam String authToken) {
        idmasterService.deleteTimeslotList(deleteTimeslotList, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All Timeslot Details
    @ApiOperation(response = TimeSlot[].class, value = "Get all Timeslot details") // label for swagger
    @GetMapping("/timeSlot/getAll")
    public ResponseEntity<?> getAllTimeslot(@RequestParam String authToken) {
        TimeSlot[] replicaTimeslot = idmasterService.getAllTimeslot(authToken);
        return new ResponseEntity<>(replicaTimeslot, HttpStatus.OK);
    }

    // Get Timeslot
    @ApiOperation(response = TimeSlot.class, value = "Get a Timeslot") // label for swagger
    @GetMapping("/timeSlot/get")
    public ResponseEntity<?> getTimeslot(@RequestParam String languageId, @RequestParam String companyId,
                                         @RequestParam String timeslotId,@RequestParam String authToken) {

        TimeSlot replicaTimeSlot = idmasterService.getReplicaTimeslot(languageId, companyId, timeslotId,authToken);
        return new ResponseEntity<>(replicaTimeSlot, HttpStatus.OK);
    }


    // Find Timeslot
    @ApiOperation(response = TimeSlot[].class, value = "Find Timeslot") // label for swagger
    @PostMapping("/timeSlot/find")
    public ResponseEntity<?> findTimeslot(@RequestBody FindTimeSlot findTimeSlot,@RequestParam String authToken) throws Exception {
        TimeSlot[] findTimeslots = idmasterService.findTimeSlot(findTimeSlot,authToken);
        return new ResponseEntity<>(findTimeslots, HttpStatus.OK);
    }


    //==================================================PaymentType====================================================

    // Create PaymentType
    @ApiOperation(response = PaymentType[].class, value = "Create PaymentType") // label for swagger
    @PostMapping("/paymentType/create/list")
    public ResponseEntity<?> postPaymentType(@RequestParam String authToken,@Valid @RequestBody List<AddPaymentType> addPaymentTypes, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        PaymentType[] createPaymentType = idmasterService.createPaymentType(addPaymentTypes, loginUserID,authToken);
        return new ResponseEntity<>(createPaymentType, HttpStatus.OK);
    }

    // Update PaymentType List
    @ApiOperation(response = PaymentType[].class, value = "Update PaymentType List") // label for swagger
    @PatchMapping("/paymentType/update/list")
    public ResponseEntity<?> patchPaymentTypeList(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<AddPaymentType> updatePaymentType)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        PaymentType[] updatedPaymentType = idmasterService.updatePaymentType(updatePaymentType, loginUserID,authToken);
        return new ResponseEntity<>(updatedPaymentType, HttpStatus.OK);
    }

    // Delete PaymentType List
    @ApiOperation(response = PaymentType[].class, value = "Delete PaymentType") // label for swagger
    @PostMapping("/paymentType/delete/list")
    public ResponseEntity<?> deletePaymentTypeList(@RequestParam String authToken,@RequestBody List<PaymentType> deletePaymentList, @RequestParam String loginUserID) {
        idmasterService.deletePaymentTypeList(deletePaymentList, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All PaymentType Details
    @ApiOperation(response = PaymentType[].class, value = "Get all PaymentType details") // label for swagger
    @GetMapping("/paymentType/getAll")
    public ResponseEntity<?> getAllPaymentType(@RequestParam String authToken) {
        PaymentType[] replicaPaymentTypes = idmasterService.getAllPaymentTypes(authToken);
        return new ResponseEntity<>(replicaPaymentTypes, HttpStatus.OK);
    }


    // Get PaymentType
    @ApiOperation(response = PaymentType.class, value = "Get a PaymentType") // label for swagger
    @GetMapping("/paymentType/get")
    public ResponseEntity<?> getPaymentType(@RequestParam String authToken,@RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String paymentTypeId) {

        PaymentType replicaPaymentType = idmasterService.getPaymentTypeid(languageId, companyId, paymentTypeId,authToken);
        return new ResponseEntity<>(replicaPaymentType, HttpStatus.OK);
    }

    // Find PaymentType
    @ApiOperation(response = PaymentType[].class, value = "Find PaymentType") // label for swagger
    @PostMapping("/paymentType/find")
    public ResponseEntity<?> findPaymentType(@RequestBody FindPaymentType findPaymentType,@RequestParam String authToken) throws Exception {
        PaymentType[] findPaymentTypes = idmasterService.findPaymentType(findPaymentType,authToken);
        return new ResponseEntity<>(findPaymentTypes, HttpStatus.OK);
    }

    // Create RetailPrice
    @ApiOperation(response = RetailPrice[].class, value = "Create RetailPrice") // label for swagger
    @PostMapping("/retailPrice/create/list")
    public ResponseEntity<?> postRetailPrice(@RequestParam String authToken,@Valid @RequestBody List<AddRetailPrice> addRetailPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RetailPrice[] createRetailPrice = idmasterService.createRetailPrice(addRetailPrices, loginUserID,authToken);
        return new ResponseEntity<>(createRetailPrice, HttpStatus.OK);
    }

    // Update RetailPrice List
    @ApiOperation(response = RetailPrice[].class, value = "Update RetailPrice List") // label for swagger
    @PatchMapping("/retailPrice/update/list")
    public ResponseEntity<?> patchRetailPrice(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<AddRetailPrice> updateRetailPrice)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RetailPrice[] updatedRetailPrice = idmasterService.updateRetailPrice(updateRetailPrice, loginUserID,authToken);
        return new ResponseEntity<>(updatedRetailPrice, HttpStatus.OK);
    }

    // Delete RetailPrice List
    @ApiOperation(response = RetailPrice[].class, value = "Delete Retail Price") // label for swagger
    @PostMapping("/retailPrice/delete/list")
    public ResponseEntity<?> deleteRetailPrice(@RequestParam String authToken,@RequestBody List<RetailPrice> deleteRetailPrice, @RequestParam String loginUserID) {
        idmasterService.deleteRetailPrice(deleteRetailPrice, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All RetailPrice Details
    @ApiOperation(response = RetailPrice[].class, value = "Get all RetailPrice details") // label for swagger
    @GetMapping("/retailPrice/getAll")
    public ResponseEntity<?> getAllRetailPrice(@RequestParam String authToken) {
        RetailPrice[] replicaRetailPrice = idmasterService.getAllRetailList(authToken);
        return new ResponseEntity<>(replicaRetailPrice, HttpStatus.OK);
    }


    // Get Retail Price
    @ApiOperation(response = RetailPrice.class, value = "Get a Retail Price") // label for swagger
    @GetMapping("/retailPrice/get")
    public ResponseEntity<?> getRetailPrice(@RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam Long lineNo,
                                            @RequestParam String authToken) {

        RetailPrice replicaRetailPrice = idmasterService.getRetailPriceId(languageId, companyId, partnerId, lineNo, authToken);
        return new ResponseEntity<>(replicaRetailPrice, HttpStatus.OK);
    }

    // Find Retail price
    @ApiOperation(response = RetailPrice[].class, value = "Find Retail Price") // label for swagger
    @PostMapping("/retailPrice/find")
    public ResponseEntity<?> findRetailPrice(@RequestParam String authToken,@RequestBody FindRetailPrice findRetailPrice) throws Exception {
        RetailPrice[] findRetails = idmasterService.findRetail(findRetailPrice,authToken);
        return new ResponseEntity<>(findRetails, HttpStatus.OK);
    }


    //==================================================Fulfillment Price====================================================

    // Create Fulfillment Price
    @ApiOperation(response = FulfillmentPrice[].class, value = "Create Fulfillment Price") // label for swagger
    @PostMapping("/fulfillmentPrice/create/list")
    public ResponseEntity<?> postFulfillmentPrice(@RequestParam String authToken,@Valid @RequestBody List<AddFulfillmentPrice> addFulfillmentPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        FulfillmentPrice[] createFulfillment = idmasterService.createFulfillmentPrice(addFulfillmentPrices, loginUserID,authToken);
        return new ResponseEntity<>(createFulfillment, HttpStatus.OK);
    }

    // Update Fulfillment Price List
    @ApiOperation(response = FulfillmentPrice[].class, value = "Update Fulfillment Price List") // label for swagger
    @PatchMapping("/fulfillmentPrice/update/list")
    public ResponseEntity<?> patchFulfillmentList(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<AddFulfillmentPrice> updateFulfillment)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        FulfillmentPrice[] updatedFulfillment = idmasterService.updateFulfillment(updateFulfillment, loginUserID,authToken);
        return new ResponseEntity<>(updatedFulfillment, HttpStatus.OK);
    }

    // Delete Fulfillment Price List
    @ApiOperation(response = FulfillmentPrice[].class, value = "Delete Fulfillment Price") // label for swagger
    @PostMapping("/fulfillmentPrice/delete/list")
    public ResponseEntity<?> deleteFulfillment(@RequestParam String authToken,@RequestBody List<FulfillmentPrice> deleteFulfillment, @RequestParam String loginUserID) {
        idmasterService.deleteFulfillmentList(deleteFulfillment, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Fulfillment Details
    @ApiOperation(response = FulfillmentPrice[].class, value = "Get all Fulfillment Details") // label for swagger
    @GetMapping("/fulfillmentPrice/getAll")
    public ResponseEntity<?> getAllFulfillment(@RequestParam String authToken) {
        FulfillmentPrice[] replicaFulfillment = idmasterService.getAllFulfillment(authToken);
        return new ResponseEntity<>(replicaFulfillment, HttpStatus.OK);
    }

    // Get Fulfillment Price
    @ApiOperation(response = FulfillmentPrice.class, value = "Get a Fulfillment Price") // label for swagger
    @GetMapping("/fulfillmentPrice/get")
    public ResponseEntity<?> getFulfillment(@RequestParam String authToken,@RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam Long lineNo) {

        FulfillmentPrice replicaFulfillmentPrice = idmasterService.getFulfillmentid(languageId, companyId, partnerId, lineNo, authToken);
        return new ResponseEntity<>(replicaFulfillmentPrice, HttpStatus.OK);
    }

    // Find Fulfillment Price
    @ApiOperation(response = FulfillmentPrice[].class, value = "Find Fulfillment Price") // label for swagger
    @PostMapping("/fulfillmentPrice/find")
    public ResponseEntity<?> findFulfillment(@RequestParam String authToken,@RequestBody FindFulfillmentPrice findFulfillmentPrice) throws Exception {
        FulfillmentPrice[] findFulfillment = idmasterService.findFulfillmentList(findFulfillmentPrice,authToken);
        return new ResponseEntity<>(findFulfillment, HttpStatus.OK);
    }

    //==================================================COD Price====================================================

    // Create CodPrice List
    @ApiOperation(response = CodPriceList[].class, value = "Create CodPrice List") // label for swagger
    @PostMapping("/codPrice/create/list")
    public ResponseEntity<?> postCodPriceList(@RequestParam String authToken,@Valid @RequestBody List<AddCodPriceList> addCodPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CodPriceList[] createCod = idmasterService.createCodPriceList(addCodPrices, loginUserID,authToken);
        return new ResponseEntity<>(createCod, HttpStatus.OK);
    }

    // Update CodPrice List
    @ApiOperation(response = CodPriceList[].class, value = "Update CodPrice List") // label for swagger
    @PatchMapping("/codPrice/update/list")
    public ResponseEntity<?> patchCodPriceList(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<CodPriceList> updateCodPriceList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CodPriceList[] updatedCodPriceList = idmasterService.updateCodPriceList(updateCodPriceList, loginUserID,authToken);
        return new ResponseEntity<>(updatedCodPriceList, HttpStatus.OK);
    }

    // Delete CodPrice List
    @ApiOperation(response = CodPriceList[].class, value = "Delete CodPrice List") // label for swagger
    @PostMapping("/codPrice/delete/list")
    public ResponseEntity<?> deleteCodPriceList(@RequestParam String authToken,@RequestBody List<CodPriceList> deleteCodPriceList, @RequestParam String loginUserID) {
        idmasterService.deleteCodPriceList(deleteCodPriceList, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All CodPrice List
    @ApiOperation(response = CodPriceList[].class, value = "Get all CodPrice List") // label for swagger
    @GetMapping("/codPrice/getAll")
    public ResponseEntity<?> getAllCodPriceList(@RequestParam String authToken) {
        CodPriceList[] replicaCodPriceList = idmasterService.getAllCodPriceList(authToken);
        return new ResponseEntity<>(replicaCodPriceList, HttpStatus.OK);
    }

    // Get CodPrice List
    @ApiOperation(response = CodPriceList.class, value = "Get a CodPrice List") // label for swagger
    @GetMapping("/codPrice/get")
    public ResponseEntity<?> getCodPriceList(@RequestParam String authToken,@RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam Long lineNo) {

        CodPriceList replicaCodPriceList = idmasterService.getCodPriceList(languageId, companyId, partnerId, lineNo, authToken);
        return new ResponseEntity<>(replicaCodPriceList, HttpStatus.OK);
    }

    // Find CodPrice List
    @ApiOperation(response = CodPriceList[].class, value = "Find CodPrice List") // label for swagger
    @PostMapping("/codPrice/find")
    public ResponseEntity<?> findCodPrice(@RequestParam String authToken,@RequestBody FindCodPriceList findCodPriceList) throws Exception {
        CodPriceList[] findCodPrice = idmasterService.findCodPriceList(findCodPriceList,authToken);
        return new ResponseEntity<>(findCodPrice, HttpStatus.OK);
    }

    //==================================================Rto Price====================================================

    // Create Rto Price
    @ApiOperation(response = RtoPriceList[].class, value = "Create Rto Price") // label for swagger
    @PostMapping("/rtoPriceList/create/list")
    public ResponseEntity<?> postRtoPrice(@RequestParam String authToken,@Valid @RequestBody List<AddRtoPrice> addRtoPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RtoPriceList[] createRtoPrice = idmasterService.createRtoPriceList(addRtoPrices, loginUserID,authToken);
        return new ResponseEntity<>(createRtoPrice, HttpStatus.OK);
    }

    // Update Rto Price List
    @ApiOperation(response = RtoPriceList[].class, value = "Update Rto Price List") // label for swagger
    @PatchMapping("/rtoPriceList/update/list")
    public ResponseEntity<?> patchRtoPriceList(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<AddRtoPrice> updateRto)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RtoPriceList[] updatedRto = idmasterService.updateRtoPriceList(updateRto, loginUserID,authToken);
        return new ResponseEntity<>(updatedRto, HttpStatus.OK);
    }

    // Delete Rto Price List
    @ApiOperation(response = RtoPriceList[].class, value = "Delete Rto Price") // label for swagger
    @PostMapping("/rtoPriceList/delete/list")
    public ResponseEntity<?> deleteRtoPrice(@RequestParam String authToken,@RequestBody List<RtoPriceList> deleteRto, @RequestParam String loginUserID) {
        idmasterService.deleteRtoPrice(deleteRto, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All RtoPriceList Details
    @ApiOperation(response = RtoPriceList[].class, value = "Get all Rto Price Details") // label for swagger
    @GetMapping("/rtoPriceList/getAll")
    public ResponseEntity<?> getAllRto(@RequestParam String authToken) {
        RtoPriceList[] replicaRto = idmasterService.getAllRtoPrice(authToken);
        return new ResponseEntity<>(replicaRto, HttpStatus.OK);
    }

    // Get Rto Price
    @ApiOperation(response = RtoPriceList.class, value = "Get a Rto Price") // label for swagger
    @GetMapping("/rtoPriceList/get")
    public ResponseEntity<?> getRto(@RequestParam String authToken,@RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam Long lineNo) {

        RtoPriceList replicaRto = idmasterService.getRtoPrice(languageId, companyId, partnerId, lineNo, authToken);
        return new ResponseEntity<>(replicaRto, HttpStatus.OK);
    }

    // Find Rto Price
    @ApiOperation(response = RtoPriceList[].class, value = "Find Rto Price") // label for swagger
    @PostMapping("/rtoPriceList/find")
    public ResponseEntity<?> findRto(@RequestParam String authToken,@RequestBody FindRtoPrice findRtoPrice) throws Exception {
        RtoPriceList[] findRto = idmasterService.findRtoPriceList(findRtoPrice,authToken);
        return new ResponseEntity<>(findRto, HttpStatus.OK);
    }

    //==================================================Asr Price====================================================

    // Create Asr Price
    @ApiOperation(response = AsrPriceList[].class, value = "Create Asr Price") // label for swagger
    @PostMapping("/asrPriceList/create/list")
    public ResponseEntity<?> postAsrPrice(@RequestParam String authToken,@Valid @RequestBody List<AddAsrPriceList> addAsrPrices, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        AsrPriceList[] createAsrPrice = idmasterService.createAsrPriceList(addAsrPrices, loginUserID,authToken);
        return new ResponseEntity<>(createAsrPrice, HttpStatus.OK);
    }

    // Update Asr Price List
    @ApiOperation(response = AsrPriceList[].class, value = "Update Asr Price List") // label for swagger
    @PatchMapping("/asrPriceList/update/list")
    public ResponseEntity<?> patchAsrPriceList(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<AddAsrPriceList> updateAsr)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        AsrPriceList[] updatedAsr = idmasterService.updateAsrPriceList(updateAsr, loginUserID,authToken);
        return new ResponseEntity<>(updatedAsr, HttpStatus.OK);
    }

    // Delete Asr Price List
    @ApiOperation(response = AsrPriceList[].class, value = "Delete Asr Price") // label for swagger
    @PostMapping("/asrPriceList/delete/list")
    public ResponseEntity<?> deleteAsrPrice(@RequestParam String authToken,@RequestBody List<AsrPriceList> deleteAsr, @RequestParam String loginUserID) {
        idmasterService.deleteAsrPrice(deleteAsr, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All AsrPriceList Details
    @ApiOperation(response = AsrPriceList[].class, value = "Get all Asr Price Details") // label for swagge
    @GetMapping("/asrPriceList/getAll")
       public ResponseEntity<?> getAllAsr(@RequestParam String authToken) {
        AsrPriceList[] replicaAsr = idmasterService.getAllAsrPrice(authToken);
        return new ResponseEntity<>(replicaAsr, HttpStatus.OK);
    }

    // Get Asr Price
    @ApiOperation(response = AsrPriceList.class, value = "Get a Asr Price") // label for swagger
    @GetMapping("/asrPriceList/get")
    public ResponseEntity<?> getAsr(@RequestParam String authToken,@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String partnerId, @RequestParam Long lineNo) {

        AsrPriceList replicaAsr = idmasterService.getAsrPrice(languageId, companyId, partnerId, lineNo, authToken);
        return new ResponseEntity<>(replicaAsr, HttpStatus.OK);
    }

    // Find Asr Price
    @ApiOperation(response = AsrPriceList[].class, value = "Find Asr Price") // label for swagger
    @PostMapping("/asrPriceList/find")
    public ResponseEntity<?> findAsr(@RequestParam String authToken,@RequestBody FindAsrPriceList findAsrPrice) throws Exception {
        AsrPriceList[] findAsr = idmasterService.findAsrPriceList(findAsrPrice,authToken);
        return new ResponseEntity<>(findAsr, HttpStatus.OK);
    }

    //==================================================MovingShipment Price List====================================================

    // Create MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList[].class, value = "Create MovingShipmentPriceList") // label for swagger
    @PostMapping("/movingShipmentPriceList/create/list")
    public ResponseEntity<?> postMovingShipmentPriceList(@RequestParam String authToken,@Valid @RequestBody List<AddMovingShipmentPriceList> addMovingShipmentPriceList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        MovingShipmentPriceList[] createMovingShipmentPriceList = idmasterService.createMovingShipmentPriceList(addMovingShipmentPriceList, loginUserID,authToken);
        return new ResponseEntity<>(createMovingShipmentPriceList, HttpStatus.OK);
    }

    // Update MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList[].class, value = "Update MovingShipmentPriceList") // label for swagger
    @PatchMapping("/movingShipmentPriceList/update/list")
    public ResponseEntity<?> patchMovingShipmentPriceList(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<AddMovingShipmentPriceList> updateMovingShipmentPrice)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        MovingShipmentPriceList[] updatedMovingShipmentPriceList = idmasterService.updateMovingShipmentPriceList(updateMovingShipmentPrice, loginUserID,authToken);
        return new ResponseEntity<>(updatedMovingShipmentPriceList, HttpStatus.OK);
    }

    // Delete MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList[].class, value = "Delete MovingShipmentPriceList") // label for swagger
    @PostMapping("/movingShipmentPriceList/delete/list")
    public ResponseEntity<?> deleteMovingShipmentPriceList(@RequestParam String authToken,@RequestBody List<MovingShipmentPriceList> deleteMovingShipmentPrice, @RequestParam String loginUserID) {
        idmasterService.deleteMovingShipmentPriceList(deleteMovingShipmentPrice, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All MovingShipmentPriceList Details
    @ApiOperation(response = MovingShipmentPriceList[].class, value = "Get all MovingShipmentPriceList Details") // label for swagge
    @GetMapping("/movingShipmentPriceList/getAll")
    public ResponseEntity<?> getAllMovingShipmentPriceList(@RequestParam String authToken) {
        MovingShipmentPriceList[] replicaMovingShipmentPriceList = idmasterService.getAllMovingShipmentPriceList(authToken);
        return new ResponseEntity<>(replicaMovingShipmentPriceList, HttpStatus.OK);
    }

    // Get MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList.class, value = "Get a MovingShipmentPriceList") // label for swagger
    @GetMapping("/movingShipmentPriceList/get")
    public ResponseEntity<?> getMovingShipmentPriceList(@RequestParam String authToken,@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String partnerId, @RequestParam Long lineNo) {

        MovingShipmentPriceList replicaMovingShipmentPriceList = idmasterService.getMovingShipmentPriceList(languageId, companyId, partnerId, lineNo, authToken);
        return new ResponseEntity<>(replicaMovingShipmentPriceList, HttpStatus.OK);
    }

    // Find MovingShipmentPriceList
    @ApiOperation(response = MovingShipmentPriceList[].class, value = "Find MovingShipmentPriceList") // label for swagger
    @PostMapping("/movingShipmentPriceList/find")
    public ResponseEntity<?> findMovingShipmentPriceList(@RequestParam String authToken,@RequestBody FindMovingShipmentPriceList findMovingShipmentPriceLists) throws Exception {
        MovingShipmentPriceList[] findMovingShipmentPrice = idmasterService.findMovingShipmentPriceList(findMovingShipmentPriceLists,authToken);
        return new ResponseEntity<>(findMovingShipmentPrice, HttpStatus.OK);
    }

    //==================================================Billing Frequency====================================================

    // Create BillingFrequency
    @ApiOperation(response = BillingFrequency[].class, value = "Create BillingFrequency") // label for swagger
    @PostMapping("/billingFrequency/create/list")
    public ResponseEntity<?> postBillingFrequency(@RequestParam String authToken,@Valid @RequestBody List<AddBillingFrequency> addBillingFrequency, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        BillingFrequency[] createBillingFrequency = idmasterService.createBillingFrequency(addBillingFrequency, loginUserID,authToken);
        return new ResponseEntity<>(createBillingFrequency, HttpStatus.OK);
    }

    // Update BillingFrequency
    @ApiOperation(response = BillingFrequency[].class, value = "Update BillingFrequency") // label for swagger
    @PatchMapping("/billingFrequency/update/list")
    public ResponseEntity<?> patchBillingFrequency(@RequestParam String authToken,@RequestParam String loginUserID, @RequestBody List<AddBillingFrequency> updateBillingFrequency)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        BillingFrequency[] updatedBillingFrequency = idmasterService.updateBillingFrequency(updateBillingFrequency, loginUserID,authToken);
        return new ResponseEntity<>(updatedBillingFrequency, HttpStatus.OK);
    }

    // Delete MovingShipmentPriceList
    @ApiOperation(response = BillingFrequency[].class, value = "Delete BillingFrequency") // label for swagger
    @PostMapping("/billingFrequency/delete/list")
    public ResponseEntity<?> deleteBillingFrequency(@RequestParam String authToken,@RequestBody List<BillingFrequency> deleteBillingFrequency, @RequestParam String loginUserID) {
        idmasterService.deleteBillingFrequency(deleteBillingFrequency, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All BillingFrequency Details
    @ApiOperation(response = BillingFrequency[].class, value = "Get all BillingFrequency Details") // label for swagge
    @GetMapping("/billingFrequency/getAll")
    public ResponseEntity<?> getAllBillingFrequency(@RequestParam String authToken) {
        BillingFrequency[] replicaBillingFrequency = idmasterService.getAllBillingFrequency(authToken);
        return new ResponseEntity<>(replicaBillingFrequency, HttpStatus.OK);
    }

    // Get BillingFrequency
    @ApiOperation(response = BillingFrequency.class, value = "Get a BillingFrequency") // label for swagger
    @GetMapping("/billingFrequency/get")
    public ResponseEntity<?> getBillingFrequency(@RequestParam String authToken,@RequestParam String languageId, @RequestParam String companyId,
                                                        @RequestParam String billingFrequencyId) {

        BillingFrequency replicaBillingFrequency = idmasterService.getBillingFrequency(languageId, companyId, billingFrequencyId,authToken);
        return new ResponseEntity<>(replicaBillingFrequency, HttpStatus.OK);
    }

    // Find BillingFrequency
    @ApiOperation(response = BillingFrequency[].class, value = "Find BillingFrequency") // label for swagger
    @PostMapping("/billingFrequency/find")
    public ResponseEntity<?> findBillingFrequency(@RequestParam String authToken,@RequestBody FindBillingFrequency findBillingFrequencys) throws Exception {
        BillingFrequency[] findBillingFrequency = idmasterService.findBillingFrequency(findBillingFrequencys,authToken);
        return new ResponseEntity<>(findBillingFrequency, HttpStatus.OK);
    }

}
