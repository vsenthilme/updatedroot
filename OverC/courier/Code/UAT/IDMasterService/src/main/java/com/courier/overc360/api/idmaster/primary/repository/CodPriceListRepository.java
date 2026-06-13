package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.codpricelist.CodPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CodPriceListRepository extends JpaRepository<CodPriceList, String>, JpaSpecificationExecutor<CodPriceList> {

    Optional<CodPriceList> findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
            String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator
    );

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
            String languageId, String companyId, String partnerId, Long lineNo,Long deletionIndicator
    );
}
