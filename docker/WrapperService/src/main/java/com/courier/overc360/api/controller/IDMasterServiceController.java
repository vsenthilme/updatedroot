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
                                     @PathVariable String partnerId, @RequestParam String authToken) {
        Rate dbRate = idmasterService.getRate(languageId, companyId, partnerId, rateParameterId, authToken);
        return new ResponseEntity<>(dbRate, HttpStatus.OK);
    }

    // Create Rate
    @ApiOperation(response = Rate.class, value = "Create new Rate") // label for swagger
    @PostMapping("/rate")
    public ResponseEntity<?> postRate(@Valid @RequestBody AddRate addRate, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Rate newRate = idmasterService.createRate(addRate, loginUserID, authToken);
        return new ResponseEntity<>(newRate, HttpStatus.OK);
    }

    // Update Rate
    @ApiOperation(response = Rate.class, value = "Update Rate") // label for swagger
    @PatchMapping("/rate/{partnerId}")
    public ResponseEntity<?> patchRate(@RequestParam String rateParameterId, @RequestParam String languageId, @RequestParam String companyId,
                                       @PathVariable String partnerId, @RequestParam String loginUserID,
                                       @RequestBody UpdateRate updateRate, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        Rate updatedRate = idmasterService.updateRate(languageId, companyId, partnerId, rateParameterId, updateRate, loginUserID, authToken);
        return new ResponseEntity<>(updatedRate, HttpStatus.OK);
    }

    // Delete Rate
    @ApiOperation(response = Rate.class, value = "Delete Rate") // label for swagger
    @DeleteMapping("/rate/{partnerId}")
    public ResponseEntity<?> deleteRate(@RequestParam String rateParameterId, @RequestParam String languageId, @RequestParam String companyId,
                                        @PathVariable String partnerId, @RequestParam String loginUserID, @RequestParam String authToken) {
        idmasterService.deleteRate(languageId, companyId, partnerId, rateParameterId, loginUserID, authToken);
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
                                                  @RequestParam String languageId, @RequestParam String hubCode, @RequestParam String authToken) {
        PartnerHubMapping partnerHubMapping = idmasterService.getPartnerHubMapping(languageId, companyId, hubCode, partnerType, partnerId, authToken);
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
                                                    @RequestParam String companyId, @RequestParam String hubCode, @RequestParam String authToken) {
        PartnerHubMapping partnerHubMapping = idmasterService.updatePartnerHubMapping(languageId, companyId, hubCode, partnerType,
                partnerId, updatePartnerHubMapping, loginUserID, authToken);
        return new ResponseEntity<>(partnerHubMapping, HttpStatus.OK);
    }

    // Delete PartnerHubMapping
    @ApiOperation(response = PartnerHubMapping.class, value = "Delete PartnerHubMapping") // label for swagger
    @DeleteMapping("/partnerHubMapping/{partnerId}")
    public ResponseEntity<?> deletePartnerHubMapping(@PathVariable String partnerId, @RequestParam String partnerType, @RequestParam String languageId,
                                                     @RequestParam String loginUserID, @RequestParam String companyId,
                                                     @RequestParam String hubCode, @RequestParam String authToken) {
        idmasterService.deletePartnerHubMapping(languageId, companyId, hubCode, partnerType, partnerId, loginUserID, authToken);
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
                                       @RequestParam String languageId, @RequestParam String authToken) {
        HSCode dbHsCode = idmasterService.getHSCode(languageId, companyId, hsCode, authToken);
        return new ResponseEntity<>(dbHsCode, HttpStatus.OK);
    }

    // Create HSCode
    @ApiOperation(response = HSCode.class, value = "Create new HSCode") // label for swagger
    @PostMapping("/hsCode")
    public ResponseEntity<?> createHSCode(@RequestBody AddHSCode addHSCode, @RequestParam String loginUserID, @RequestParam String authToken) {
        HSCode hsCode = idmasterService.createHSCode(addHSCode, loginUserID, authToken);
        return new ResponseEntity<>(hsCode, HttpStatus.OK);
    }

    // Update HSCode
    @ApiOperation(response = HSCode.class, value = "Update HSCode") // label for swagger
    @PatchMapping("/hsCode/{hsCode}")
    public ResponseEntity<?> updateHSCode(@PathVariable String hsCode, @RequestParam String languageId,
                                          @RequestParam String loginUserID, @RequestBody UpdateHSCode updateHSCode,
                                          @RequestParam String companyId, @RequestParam String authToken) {
        HSCode dbHSCode = idmasterService.updateHSCode(languageId, companyId, hsCode, updateHSCode, loginUserID, authToken);
        return new ResponseEntity<>(dbHSCode, HttpStatus.OK);
    }

    // Delete HSCode
    @ApiOperation(response = HSCode.class, value = "Delete HSCode") // label for swagger
    @DeleteMapping("/hsCode/{hsCode}")
    public ResponseEntity<?> deleteHSCode(@PathVariable String hsCode, @RequestParam String languageId, @RequestParam String loginUserID,
                                          @RequestParam String companyId, @RequestParam String authToken) {
        idmasterService.deleteHSCode(languageId, companyId, hsCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find HSCode
    @ApiOperation(response = HSCode[].class, value = "Find HSCode") //label for swagger
    @PostMapping("/hsCode/find")
    public HSCode[] findHSCodes(@RequestBody FindHSCode findHSCode, @RequestParam String authToken) throws Exception {
        return idmasterService.findHSCodes(findHSCode, authToken);
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
                                              @RequestBody UpdateRoleAccess updateRoleAccess,
                                              @RequestParam String loginUserID, @RequestParam String authToken) {
        RoleAccess updatedRoleAccess = idmasterService.updateRoleAccess(companyId, languageId, roleId, menuId, subMenuId,
                loginUserID, updateRoleAccess, authToken);
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

}