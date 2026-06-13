package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.uom.Uom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UomRepository extends JpaRepository<Uom, String>, JpaSpecificationExecutor<Uom> {

    Optional<Uom>findByCompanyIdAndLanguageIdAndUomIdAndDeletionIndicator
            (String companyId, String languageId, String uomId, Long deletionIndicator);
}
