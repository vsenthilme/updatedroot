package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.airportcode.AirportCode;
import com.courier.overc360.api.idmaster.primary.model.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface AppUserRepository  extends JpaRepository<AppUser, String>, JpaSpecificationExecutor<AppUser> {

    Optional<AppUser> findByCompanyIdAndLanguageIdAndAppUserIdAndDeletionIndicator(
            String companyId, String languageId, String appUserId, Long deletionIndicator);

    List<AppUser> findByAppUserIdAndAppUserTypeAndDeletionIndicator(String appUserId, String appUserType,Long deletionIndicator);
}
