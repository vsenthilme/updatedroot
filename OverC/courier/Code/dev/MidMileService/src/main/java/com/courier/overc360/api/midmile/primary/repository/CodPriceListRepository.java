package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.finance.CodPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CodPriceListRepository extends JpaRepository<CodPriceList, String>, JpaSpecificationExecutor<CodPriceList> {

    List<CodPriceList> findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, Long deletionIndicator
    );

}
