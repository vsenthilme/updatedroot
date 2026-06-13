package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.product.AddProduct;
import com.courier.overc360.api.idmaster.primary.model.product.Product;
import com.courier.overc360.api.idmaster.primary.model.product.ProductDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.product.UpdateProduct;
import com.courier.overc360.api.idmaster.replica.model.product.FindProduct;
import com.courier.overc360.api.idmaster.replica.model.product.ReplicaProduct;
import com.courier.overc360.api.idmaster.service.ProductService;
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
@Api(tags = {"Product"}, value = "Product country related to ProductController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Product", description = "Operations related to Product")})
@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Product
    @ApiOperation(response = Product.class, value = "Create new Product") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody AddProduct addProduct, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Product newProduct = productService.createProduct(addProduct, loginUserID);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    // Update Product
    @ApiOperation(response = Product.class, value = "Update Product") // label for swagger
    @PatchMapping("/{productId}")
    public ResponseEntity<?> patchProduct(@PathVariable String productId, @RequestParam String languageId, @RequestParam String subProductId,
                                          @RequestParam String loginUserID, @RequestParam String companyId, @RequestParam String subProductValue,
                                          @Valid @RequestBody UpdateProduct updateProduct)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Product updatedProduct = productService.updateProduct(languageId, companyId, subProductId, productId, subProductValue, updateProduct, loginUserID);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete Product
    @ApiOperation(response = Product.class, value = "Delete Product") // label for swagger
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId, @RequestParam String languageId, @RequestParam String subProductId,
                                           @RequestParam String companyId, @RequestParam String subProductValue, @RequestParam String loginUserID) {
        productService.deleteProduct(languageId, companyId, subProductId, productId, subProductValue, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Products - bulk
    @ApiOperation(response = Product.class, value = "Create new Products - bulk") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postProductBulk(@Valid @RequestBody List<AddProduct> addProductList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Product> newProducts = productService.createProductBulk(addProductList, loginUserID);
        return new ResponseEntity<>(newProducts, HttpStatus.OK);
    }

    // Update Products - bulk
    @ApiOperation(response = Product.class, value = "Update Products - bulk") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchProductBulk(@Valid @RequestBody List<UpdateProduct> updateProductList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Product> updatedProducts = productService.updateProductBulk(updateProductList, loginUserID);
        return new ResponseEntity<>(updatedProducts, HttpStatus.OK);
    }

    // Delete Products - bulk
    @ApiOperation(response = Product.class, value = "Delete Products - bulk") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteProductBulk(@Valid @RequestBody List<ProductDeleteInput> productDeleteInputList, @RequestParam String loginUserID) {
        productService.deleteProductBulk(productDeleteInputList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------*/

    // Get All Product Details
    @ApiOperation(response = ReplicaProduct.class, value = "Get all Product Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllProductDetails() {
        List<ReplicaProduct> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    // Get Product
    @ApiOperation(response = ReplicaProduct.class, value = "Get a Product") // label for swagger
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId, @RequestParam String languageId, @RequestParam String subProductValue,
                                        @RequestParam String companyId, @RequestParam String subProductId) {
        ReplicaProduct dbProduct = productService.getReplicaProduct(languageId, companyId, subProductId, productId, subProductValue);
        return new ResponseEntity<>(dbProduct, HttpStatus.OK);
    }

    // Find Product
    @ApiOperation(response = ReplicaProduct.class, value = "Find Product") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findProducts(@Valid @RequestBody FindProduct findProduct) throws Exception {
        List<ReplicaProduct> productList = productService.findProducts(findProduct);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
