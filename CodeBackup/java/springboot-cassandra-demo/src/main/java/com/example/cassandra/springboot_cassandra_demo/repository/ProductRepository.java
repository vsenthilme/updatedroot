package com.example.cassandra.springboot_cassandra_demo.repository;

import com.example.cassandra.springboot_cassandra_demo.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ProductRepository extends CassandraRepository<Product,Integer> {
}
