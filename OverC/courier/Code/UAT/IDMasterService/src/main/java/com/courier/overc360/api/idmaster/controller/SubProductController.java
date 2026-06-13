package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.subproject.AddSubProduct;
import com.courier.overc360.api.idmaster.primary.model.subproject.SubProduct;
import com.courier.overc360.api.idmaster.primary.model.subproject.SubProductDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.subproject.UpdateSubProduct;
import com.courier.overc360.api.idmaster.replica.model.subproduct.FindSubProduct;
import com.courier.overc360.api.idmaster.replica.model.subproduct.ReplicaSubProduct;
import com.courier.overc360.api.idmaster.service.SubProductService;
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
@Api(tags = {"SubProduct"}, value = "SubProduct Operations related to SubProductController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "SubProduct", description = "Operations related to SubProduct")})
@RequestMapping("/subProduct")
@RestController
public class SubProductController {

    @Autowired
    SubProductService subProductService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create SubProduct
    @ApiOperation(response = SubProduct.class, value = "Create new SubProduct") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> addSubProduct(@Valid @RequestBody AddSubProduct addSubProduct, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        SubProduct newSubProduct = subProductService.createSubProduct(addSubProduct, loginUserID);
        return new ResponseEntity<>(newSubProduct, HttpStatus.OK);
    }

    // Update SubProduct
    @ApiOperation(response = SubProduct.class, value = "Update SubProduct") // label for swagger
    @PatchMapping("/{subProductId}")
    public ResponseEntity<?> patchSubProduct(@PathVariable String subProductId, @RequestParam String languageId, @RequestParam String loginUserID,
                                             @RequestParam String companyId, @RequestParam String subProductValue,
                                             @Valid @RequestBody UpdateSubProduct updateSubProduct)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        SubProduct updatedSubProduct = subProductService.updateSubProduct(languageId, companyId, subProductId, subProductValue, updateSubProduct, loginUserID);
        return new ResponseEntity<>(updatedSubProduct, HttpStatus.OK);
    }

    // Delete SubProduct
    @ApiOperation(response = SubProduct.class, value = "Delete SubProduct") // label for swagger
    @DeleteMapping("/{subProductId}")
    public ResponseEntity<?> deleteSubProduct(@PathVariable String subProductId, @RequestParam String languageId, @RequestParam String subProductValue,
                                              @RequestParam String companyId, @RequestParam String loginUserID) {
        subProductService.deleteSubProduct(languageId, companyId, subProductId, subProductValue, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create SubProducts - bulk
    @ApiOperation(response = SubProduct.class, value = "Create new SubProducts - bulk") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postSubProductBulk(@Valid @RequestBody List<AddSubProduct> addSubProductList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<SubProduct> newSubProducts = subProductService.createSubProductBulk(addSubProductList, loginUserID);
        return new ResponseEntity<>(newSubProducts, HttpStatus.OK);
    }

    // Update SubProducts - bulk
    @ApiOperation(response = SubProduct.class, value = "Update SubProducts - bulk") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchSubProductBulk(@Valid @RequestBody List<UpdateSubProduct> updateSubProductList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<SubProduct> updatedSubProducts = subProductService.updateSubProductBulk(updateSubProductList, loginUserID);
        return new ResponseEntity<>(updatedSubProducts, HttpStatus.OK);
    }

    // Delete SubProducts - bulk
    @ApiOperation(response = SubProduct.class, value = "Delete SubProducts - bulk") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteProductBulk(@Valid @RequestBody List<SubProductDeleteInput> subProductDeleteInputList, @RequestParam String loginUserID) {
        subProductService.deleteSubProductBulk(subProductDeleteInputList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All SubProduct Details
    @ApiOperation(response = ReplicaSubProduct.class, value = "Get all SubProduct Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllSubProductDetails() {
        List<ReplicaSubProduct> subProductList = subProductService.getAllSubProducts();
        return new ResponseEntity<>(subProductList, HttpStatus.OK);
    }

    // Get SubProduct
    @ApiOperation(response = ReplicaSubProduct.class, value = "Get a SubProduct") // label for swagger
    @GetMapping("/{subProductId}")
    public ResponseEntity<?> getSubProduct(@PathVariable String subProductId, @RequestParam String languageId,
                                           @RequestParam String companyId, @RequestParam String subProductValue) {
        ReplicaSubProduct dbSubProduct = subProductService.replicaGetSubProduct(languageId, companyId, subProductId, subProductValue);
        return new ResponseEntity<>(dbSubProduct, HttpStatus.OK);
    }

    // Find SubProduct
    @ApiOperation(response = ReplicaSubProduct.class, value = "Find SubProduct") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findSubProducts(@Valid @RequestBody FindSubProduct findSubProduct) throws Exception {
        List<ReplicaSubProduct> subProductList = subProductService.findSubProducts(findSubProduct);
        return new ResponseEntity<>(subProductList, HttpStatus.OK);
    }

}
