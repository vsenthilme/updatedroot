package com.example.cassandra.springboot.repository;

import com.example.cassandra.springboot.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ProductRepository extends CassandraRepository<Product,Integer> {
}
