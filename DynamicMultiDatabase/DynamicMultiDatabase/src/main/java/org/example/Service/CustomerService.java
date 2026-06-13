package org.example.Service;


import lombok.RequiredArgsConstructor;
import org.example.Model.Customer;
import org.example.Model.User;
import org.example.Repository.CustomerRepository;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Customer> getAllEmployeeDetails(){
        return customerRepository.findAll();
    }


    public Customer addCustomer(Customer customer) {
        Customer customer1 = new Customer();
        customer1.setCustomerName(customer.getCustomerName());
        customer1.setCustomerRole(customer.getCustomerRole());
        return customerRepository.save(customer1);
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public User findUser(String userName) {
        return userRepository.findByUser(userName);
    }

    public Customer getCustomer(int cusId) {
        return customerRepository.getCustomerData(cusId);
    }
}
