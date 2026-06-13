package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.pickup.ReplicaPickupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaPickupDetailsRepository extends JpaRepository<ReplicaPickupDetails, Long> {

    @Query(value = "SELECT * FROM tblpickupdetails WHERE " +
            "(COALESCE(:id, NULL) IS NULL OR PICKUP_DETAIL_ID = :id)",nativeQuery = true)
    ReplicaPickupDetails findPickupId(Long id);


    @Query(value = "SELECT TOP 1 COUNTRY_TEXT countryName, \n" +
            "CITY_NAME cityName, \n" +
            "DISTRICT_TEXT districtName, \n " +
            "PROVINCE_TEXT provinceName \n " +
            "FROM tblcity \n " +
            "WHERE IS_DELETED = 0 \n " +
            "AND (COALESCE(:cityId, NULL) IS NULL OR CITY_ID IN (:cityId)) \n" +
            "AND (COALESCE(:countryId, NULL) IS NULL OR COUNTRY_ID IN (:countryId)) \n " +
            "AND (COALESCE(:districtId, NULL) IS NULL OR DISTRICT_ID IN (:districtId)) \n" +
            "AND (COALESCE(:provinceId, NULL) IS NULL OR PROVINCE_ID IN (:provinceId))", nativeQuery = true)
    public IKeyValuePair getDescription(@Param("cityId") String cityId,
                                        @Param("countryId") String countryId,
                                        @Param("districtId") String districtId,
                                        @Param("provinceId") String provinceId);

    @Query(value = "select type_text as typeText from tblstatusevent where  \n" +
            "type_id = :typeId and is_deleted = 0", nativeQuery = true)
    String getStatusDescription(@Param("typeId") String typeId);

//    @Query(value = "Select \n" +
//            "CONCAT (ts.STATUS_ID, ' - ', ts.STATUS_TEXT) \n" +
//            "From tblstatus ts \n" +
//            "Where \n" +
//            "ts.STATUS_ID IN (:statusId) and \n" +
//            "ts.IS_DELETED = 0", nativeQuery = true)
//    String getStatusDescription(@Param(value = "statusId") String statusId);
}
