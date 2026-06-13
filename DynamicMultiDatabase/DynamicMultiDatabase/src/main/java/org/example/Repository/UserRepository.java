package org.example.Repository;

import org.example.Model.Customer;
import org.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {



    @Query(value ="SELECT u FROM User u")
    List<User> findAll();

    @Query(value ="SELECT u FROM User u where u.userName = ?1")
    User findByUser(String userName);
}
