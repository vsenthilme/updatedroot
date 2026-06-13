package com.example.cassandra.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProduct {

    @PrimaryKey
    private int id;
    private String name;
    private String fname;
    private String lname;
    private int age;

}
