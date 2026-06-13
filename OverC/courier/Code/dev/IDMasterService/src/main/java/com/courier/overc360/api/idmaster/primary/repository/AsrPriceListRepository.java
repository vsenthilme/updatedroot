package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.asrpricelist.AsrPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AsrPriceListRepository extends JpaRepository<AsrPriceList, String>, JpaSpecificationExecutor<AsrPriceList> {

    boolean existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);

    Optional<AsrPriceList> findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(String languageId, String companyId, String partnerId, Long lineNo, Long deletionIndicator);

    List<AsrPriceList> findByLanguageIdAndCompanyIdAndPartnerIdAndDeletionIndicator(String languageId, String companyId, String partnerId, Long deletionIndicator);

    @Modifying
    @Query(value = "UPDATE tblasrpricelist SET IS_DELETED = 1 WHERE C_ID IN (:companyId) AND LANG_ID IN (:languageId) AND PARTNER_ID IN (:partnerId) AND IS_DELETED = 0", nativeQuery = true)
    void deleteAsr(@Param("companyId") String companyId,
                   @Param("languageId") String languageId,
                   @Param("partnerId") String partnerId);
}
