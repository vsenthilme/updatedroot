//package com.courier.overc360.api.repository;
//
//import com.courier.overc360.api.model.dto.Country;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {
//
//    List<Country> findAll();
//    Optional<Country> findByCountryIdAndLanguageId (String countryId,String languageId);
//
//}