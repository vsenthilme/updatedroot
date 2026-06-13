package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.customcharges.CustomCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CustomChargesRepository extends JpaRepository<CustomCharges,String> {


    Optional<CustomCharges> findByLanguageIdAndCompanyIdAndDeletionIndicator(String languageId, String companyId, long deletionIndicator);
}
