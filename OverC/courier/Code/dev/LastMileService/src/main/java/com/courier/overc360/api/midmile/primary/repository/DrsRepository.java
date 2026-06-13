package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface DrsRepository extends JpaRepository<Drs, String> {

    Optional<Drs> findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId, Long deletionIndicator
    );

    boolean existsByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId, Long deletionIndicator
    );

    @Query(value = "SELECT ADD_ORIGIN_DETAILS from tblconsignment_entity WHERE " +
            " LANG_ID = (:languageId) AND C_ID = (:companyId) AND HOUSE_AIRWAY_BILL = (:houseAirwayBill) " +
            " AND IS_DELETED = 0", nativeQuery = true)
    String consigneeDetails(@Param("languageId") String languageId,
                            @Param("companyId") String companyId,
                            @Param("houseAirwayBill") String houseAirwayBill);

    @Query(value = "SELECT h.HUB_NAME \n " +
            "FROM tblappuser a JOIN tblhub h ON a.ASSIGNED_HUB_CODE = h.HUB_CODE\n" +
            "WHERE a.APP_USER_ID = (:courierId)", nativeQuery = true)
    String hubDes(@Param("courierId") String courierId);

}
