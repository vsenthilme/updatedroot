package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrix;
import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrixRequest;
import com.courier.overc360.api.midmile.primary.model.maps.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistanceMatrixRepository extends JpaRepository<DistanceMatrix, Long> {

    List<DistanceMatrix> findByFromLocationId(Location fromLocationId);

    boolean existsByFromAddressAndToAddress(String fromAddress, String toAddress);

    Optional<DistanceMatrix> findByFromLocationIdAndToLocationId(Location fromLocation, Location toLocation);

    @Query(value = "SELECT PICKUP_ID from tblpickup_entity where (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill) " +
            " AND LANG_ID = (:languageId) AND C_ID = (:companyId) AND PARTNER_ID = (:partnerId) " +
            " AND IS_DELETED = 0 AND PICKUP_ENTITY_ID = (:pickupEntityId)", nativeQuery = true)
    String getPickupId(@Param("houseAirwayBill") String houseAirwayBill,
                       @Param("languageId") String languageId,
                       @Param("companyId") String companyId,
                       @Param("partnerId") String partnerId,
                       @Param("pickupEntityId") Long pickupEntityId);

    @Query(value = "SELECT DELIVERY_ID from tbldelivery where (COALESCE(:houseAirwayBill, NULL) IS NULL OR HOUSE_AIRWAY_BILL = :houseAirwayBill) " +
            " AND LANG_ID = (:languageId) AND C_ID = (:companyId) AND PIECE_ID = (:pieceId) " +
            " AND IS_DELETED = 0", nativeQuery = true)
    String getDeliveryId(@Param("houseAirwayBill") String houseAirwayBill,
                         @Param("languageId") String languageId,
                         @Param("companyId") String companyId,
                         @Param("pieceId") String pieceId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DistanceMatrix")
    void deleteAllEntries();

    @Query(value = "SELECT * from tbldistancematrix WHERE " +
            " HOUSE_AIRWAY_BILL = (:houseAirwayBill) ", nativeQuery = true)
    String getDurationForSameAddress(@Param("houseAirwayBill") String houseAirwayBill);

    @Modifying
    @Transactional
    @Query(value = "INSERT into tbldistancematrix (DELIVERY_ID, distance, duration, from_address, PICKUP_ID, to_address) " +
            " values (NULL, NULL, NULL, '7X74+7H3, Al-Dajeej, Kuwait', NULL, 'NA')", nativeQuery = true)
    void insertRecord();

    // Method to retrieve the inserted DistanceMatrix
    @Query(value = "SELECT * FROM tbldistancematrix WHERE from_address = '7X74+7H3, Al-Dajeej, Kuwait' AND to_address = 'NA'", nativeQuery = true)
    Optional<DistanceMatrix> findInsertedRecord();
}
