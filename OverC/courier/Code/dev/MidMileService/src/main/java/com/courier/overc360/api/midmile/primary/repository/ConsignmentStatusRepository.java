package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignmentstatus.ConsignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ConsignmentStatusRepository extends JpaRepository<ConsignmentStatus, String>,
        JpaSpecificationExecutor<ConsignmentStatus> {

    Optional<ConsignmentStatus> findByLanguageIdAndCompanyIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String houseAirwayBill, String pieceId, Long deletionIndicator);

    @Query(value = "Select \n" +
            "tl.lang_text langDesc, \n" +
            "tc.c_name companyDesc \n" +
            "From tbllanguage tl \n" +
            "Join tblcompany tc on tl.lang_id = tc.lang_id \n" +
            "Where \n" +
            "tl.lang_id IN (:languageId) and \n" +
            "tc.c_id IN (:companyId) and \n" +
            "tl.is_deleted = 0 and \n" +
            "tc.is_deleted = 0", nativeQuery = true)
    IKeyValuePair getLAndCDescription(@Param(value = "languageId") String languageId,
                                      @Param(value = "companyId") String companyId);

    @Query(value = "Select top 1 CON_STATUS_ID consignmentStatusId from tblconsignmentstatus ORDER BY CON_STATUS_ID DESC", nativeQuery = true)
    public Long statusId();

    @Query(value = "SELECT MAX(CON_STATUS_ID) + 1 FROM tblconsignmentstatus WHERE IS_DELETED = 0", nativeQuery = true)
    Long getMaxConStatusId();

    @Query(value = "SELECT count(HOUSE_AIRWAY_BILL) from tblconsignmentstatus where hawb_typ_id = :hawb and is_deleted = 0 and HOUSE_AIRWAY_BILL = :houseAB", nativeQuery = true)
    Long getStatusCount(@Param("hawb") String hawb,
                        @Param("houseAB") String houseAB);


    // latest statusId 1 return
}
