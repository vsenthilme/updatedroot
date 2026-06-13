package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.consignment.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DeliveryRepository extends JpaRepository<Delivery,Long>, JpaSpecificationExecutor<Delivery> {

    @Query(value = "SELECT * FROM tbldelivery WHERE (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) " +
            "AND IS_DELETED = 0 ", nativeQuery = true)
    Delivery getMobUpdateDelivery(@Param("houseAirwayBill") String houseAirwayBill,
                                         @Param("languageId") String languageId,
                                         @Param("companyId") String companyId);
    @Modifying
    @Query(value = "UPDATE tbldelivery SET COURIER_ID = (:riderId) WHERE (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL IN (:houseAirwayBill)) " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR LANG_ID IN (:languageId)) AND (COALESCE(:companyId, NULL) IS NULL OR C_ID IN (:companyId)) " +
            "AND IS_DELETED = 0 ", nativeQuery = true)
    void updateCourierId(@Param("riderId") String riderId,
                         @Param("houseAirwayBill") String houseAirwayBill,
                         @Param("languageId") String languageId,
                         @Param("companyId") String companyId);
}
