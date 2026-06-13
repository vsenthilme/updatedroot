package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.customer.AddCustomer;
import com.courier.overc360.api.idmaster.primary.model.customer.Customer;
import com.courier.overc360.api.idmaster.primary.model.customer.CustomerDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.customer.UpdateCustomer;
import com.courier.overc360.api.idmaster.replica.model.customer.FindCustomer;
import com.courier.overc360.api.idmaster.replica.model.customer.ReplicaCustomer;
import com.courier.overc360.api.idmaster.service.CustomerService;
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
@Api(tags = {"Customer"}, value = "Customer related to CustomerController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Customer", description = "Operations related to Customer")})
@RequestMapping("/customer")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Customer
    @ApiOperation(response = Customer.class, value = "Create new Customer") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCustomer(@Valid @RequestBody AddCustomer addCustomer, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Customer newCustomer = customerService.createCustomer(addCustomer, loginUserID);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    // Update Customer
    @ApiOperation(response = Customer.class, value = "Update Customer") // label for swagger
    @PatchMapping("/{customerId}")
    public ResponseEntity<?> patchCustomer(@PathVariable String customerId, @RequestParam String languageId, @RequestParam String subProductId,
                                           @RequestParam String loginUserID, @RequestParam String companyId, @RequestParam String productId,
                                           @RequestParam String subProductValue, @Valid @RequestBody UpdateCustomer updateCustomer)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Customer updatedCustomer = customerService.updateCustomer(languageId, companyId, subProductId, productId,
                customerId, subProductValue, updateCustomer, loginUserID);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    // Delete Customer
    @ApiOperation(response = Customer.class, value = "Delete Customer") // label for swagger
    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String customerId, @RequestParam String languageId,
                                            @RequestParam String subProductId, @RequestParam String subProductValue,
                                            @RequestParam String companyId, @RequestParam String productId, @RequestParam String loginUserID) {
        customerService.deleteCustomer(languageId, companyId, subProductId, subProductValue, productId, customerId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*----------------------------------------------list_APIs'-------------------------------------------------------*/
    // Create Customers - bulk
    @ApiOperation(response = Customer.class, value = "Create new Customers - bulk") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postCustomerBulk(@Valid @RequestBody List<AddCustomer> addCustomerList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Customer> newCustomers = customerService.createCustomerBulk(addCustomerList, loginUserID);
        return new ResponseEntity<>(newCustomers, HttpStatus.OK);
    }

    // Update Customers - bulk
    @ApiOperation(response = Customer.class, value = "Update Customers - bulk") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchCustomerBulk(@Valid @RequestBody List<UpdateCustomer> updateCustomerList, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Customer> updatedCustomers = customerService.updateCustomerBulk(updateCustomerList, loginUserID);
        return new ResponseEntity<>(updatedCustomers, HttpStatus.OK);
    }

    // Delete Customers - bulk
    @ApiOperation(response = Customer.class, value = "Delete Customers - bulk") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteCustomerBulk(@Valid @RequestBody List<CustomerDeleteInput> customerDeleteInputList, @RequestParam String loginUserID) {
        customerService.deleteCustomerBulk(customerDeleteInputList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Customer Details
    @ApiOperation(response = ReplicaCustomer.class, value = "Get all Customer Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCustomerDetails() {
        List<ReplicaCustomer> customerList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    // Get Customer
    @ApiOperation(response = ReplicaCustomer.class, value = "Get a Customer") // label for swagger
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable String customerId, @RequestParam String languageId, @RequestParam String companyId,
                                         @RequestParam String subProductId, @RequestParam String subProductValue, @RequestParam String productId) {
        ReplicaCustomer dbCustomer = customerService.getReplicaCustomer(languageId, companyId, subProductId, subProductValue, productId, customerId);
        return new ResponseEntity<>(dbCustomer, HttpStatus.OK);
    }

    // Find Customer
    @ApiOperation(response = ReplicaCustomer.class, value = "Find Customer") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCustomers(@Valid @RequestBody FindCustomer findCustomer) throws Exception {
        List<ReplicaCustomer> customerList = customerService.findCustomers(findCustomer);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

}
