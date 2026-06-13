package com.courier.overc360.api.midmile.replica.repository.finance;

import com.courier.overc360.api.midmile.replica.model.finance.ReplicaCodPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReplicaCodPriceListRepository extends JpaRepository<ReplicaCodPriceList, String>, JpaSpecificationExecutor<ReplicaCodPriceList> {
    List<ReplicaCodPriceList> findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, Long deletionIndicator
    );

}
