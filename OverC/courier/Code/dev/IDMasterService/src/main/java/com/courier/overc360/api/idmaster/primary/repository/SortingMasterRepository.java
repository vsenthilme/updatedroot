package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.sortingmaster.SortingMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SortingMasterRepository extends JpaRepository<SortingMaster,String>,JpaSpecificationExecutor<SortingMaster> {


    Optional<SortingMaster> findByLanguageIdAndCompanyIdAndSortingIdAndZoneTypeAndDeletionIndicator(String languageId, String companyId, String sortingId, String zoneType, Long deletionIndicator);


    @Query(value = "Select * From tblsortingmaster tc \n" +
            "Where tc.IS_DELETED = 0 \n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR tc.LANG_ID IN (:languageId)) \n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR tc.C_ID IN (:companyId)) \n" +
            "AND (COALESCE(:zoneType, NULL) IS NULL OR tc.ZONE_TYPE IN (:zoneType)) \n" +
            "AND (COALESCE(:sortingId, NULL) IS NULL OR tc.SORTING_ID IN (:sortingId))", nativeQuery = true)
    List<SortingMaster> getSortMastersWithQry(@Param("languageId") String languageId,
                                              @Param("companyId") String companyId,
                                              @Param("zoneType") String zoneType,
                                              @Param("sortingId") String sortingId);
}
