<<<<<<< HEAD
//package com.mnrclara.spark.core.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.mnrclara.spark.core.model.UserLogin;
//import com.mnrclara.spark.core.model.UsersHelper;
//import com.mnrclara.spark.core.repository.UserLoginRepository;
//
//@Service
//public class UserLoginService implements UserDetailsService {
//
//    @Autowired
//    UserLoginRepository userLoginRepository;
//
//    @Override
//    public UsersHelper loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserLogin userLogin = null;
//        try {
//            userLogin = userLoginRepository.getUserLoginDetails(username);
//            UsersHelper usersHelper = new UsersHelper(userLogin);
//            return usersHelper;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new UsernameNotFoundException(username + " not found..");
//        }
//    }
//}
=======
package com.mnrclara.spark.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mnrclara.spark.core.model.UserLogin;
import com.mnrclara.spark.core.model.UsersHelper;
import com.mnrclara.spark.core.repository.UserLoginRepository;

@Service
public class UserLoginService implements UserDetailsService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Override
    public UsersHelper loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin = null;
        try {
            userLogin = userLoginRepository.getUserLoginDetails(username);
            UsersHelper usersHelper = new UsersHelper(userLogin);
            return usersHelper;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(username + " not found..");
        }
    }
}
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
