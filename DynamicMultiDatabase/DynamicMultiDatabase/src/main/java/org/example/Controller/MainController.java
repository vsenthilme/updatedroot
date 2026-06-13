package org.example.Controller;


import lombok.RequiredArgsConstructor;
import org.example.Model.Customer;
import org.example.Model.User;
import org.example.Repository.UserRepository;
import org.example.Service.CustomerService;
import org.example.config.DataSourceContextHolder;
import org.example.config.DataSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {


    Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private UserRepository userRepository;

    private final CustomerService customerService;



    private final DataSourceContextHolder dataSourceContextHolder;

    @GetMapping(value = "/getCustomerDetails/{dataSourceType}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllCustomer(@PathVariable("dataSourceType") String dataSourceType){
        if (DataSourceEnum.DATASOURCE_TWO.toString().equals(dataSourceType)){
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
        }
        else{
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_ONE);
        }
        return customerService.getAllEmployeeDetails();
    }

    @PostMapping(value = "addCustomer/{dataSourceType}",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCustomer(@PathVariable("dataSourceType") String dataSourceType, @RequestBody Customer customer){

        if (DataSourceEnum.DATASOURCE_TWO.toString().equals(dataSourceType)){
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
        }
        else{
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_ONE);
        }

        Customer customer1 = customerService.addCustomer(customer);

        return new ResponseEntity<>(customer1, HttpStatus.OK);

    }

    @GetMapping(value = "/getDetails/{dataSourceType}/{userName}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllUser(@PathVariable("dataSourceType") String dataSourceType,@PathVariable("userName") String userName) {

        if (DataSourceEnum.DATASOURCE_TWO.toString().equals(dataSourceType)) {
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);

        } else {
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_ONE);
        }

        User user = customerService.findUser(userName);
        List<Customer> allCustomers = null;
        if (user != null) {
            allCustomers = customerService.getAllEmployeeDetails();
        }
        else {
            allCustomers = null;
        }
        return allCustomers;

    }


    @GetMapping(value = "/getCustomer/{cusId}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("cusId") int cusId) {

//        if (DataSourceEnum.DATASOURCE_TWO.toString().equals(dataSourceType)) {
//            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
//
//        } else {
//            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_ONE);
//        }

        Customer  customer = customerService.getCustomer(cusId);
        return customer;

    }




}
