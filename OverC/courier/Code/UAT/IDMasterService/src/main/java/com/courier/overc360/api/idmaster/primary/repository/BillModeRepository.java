package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.billmode.BillMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface BillModeRepository extends JpaRepository<BillMode, String>, JpaSpecificationExecutor<BillMode> {

    Optional<BillMode> findByCompanyIdAndLanguageIdAndBillModeIdAndDeletionIndicator
            (String companyId, String languageId, String billModeId,Long deletionIndicator);
}
