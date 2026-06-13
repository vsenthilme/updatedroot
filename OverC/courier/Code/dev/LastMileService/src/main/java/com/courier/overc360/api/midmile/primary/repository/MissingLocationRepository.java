package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.maps.MissingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MissingLocationRepository extends JpaRepository<MissingLocation, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MissingLocation")
    void deleteAllEntries();
}
