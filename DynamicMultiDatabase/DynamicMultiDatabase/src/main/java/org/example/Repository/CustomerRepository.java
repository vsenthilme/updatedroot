package org.example.Repository;

import org.example.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query(value = "select c from Customer c")
    List<Customer> findAll();

    @Query(value = "select c from Customer c where c.customerId=?1")
    Customer getCustomerData(int cusId);
}
