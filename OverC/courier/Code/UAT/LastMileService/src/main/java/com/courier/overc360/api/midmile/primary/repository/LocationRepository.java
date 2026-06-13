package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.maps.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByAddress(String address);
    List<Location> findAllByAddressIn(List<String> addresses);

    Optional<Location> findByLatitudeAndLongitude(Double latitude, Double longitude);

    @Modifying
    @Transactional
    @Query("DELETE FROM Location")
    void deleteAllEntries();

    @Modifying
    @Transactional
    @Query(value = "INSERT into tbllocation (ADDRESS_TEXT, latitude, longitude, C_ID, LANG_ID, PARTNER_ID, PICKUP_ID, STATUS_ID, ACTUAL_SEQUENCE_NO, REF_FIELD_1) " +
            " values ('7X74+7H3, Al-Dajeej, Kuwait', 29.2631438, 47.953843, 'IWE', 'EN', 'P01', '0', '48', '0', 'Hub Address');", nativeQuery = true)
    void insertOriginEntry();
}
