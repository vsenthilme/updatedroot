package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.usertype.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserTypeRepository extends JpaRepository<UserType, Long>, JpaSpecificationExecutor<UserType> {

    Optional<UserType>
    findByCompanyIdAndUserTypeIdAndLanguageIdAndDeletionIndicator(
            String companyId, Long userTypeId, String languageId, Long deletionIndicator);

}