package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.opStatus.ReplicaOpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaOpStatusRepository extends JpaRepository<ReplicaOpStatus, String>, JpaSpecificationExecutor<ReplicaOpStatus> {

    Optional<ReplicaOpStatus> findByLanguageIdAndCompanyIdAndStatusCodeAndDeletionIndicator(
            String languageId, String companyId, String statusCode, Long deletionIndicator);


    // Get OpStatus Description
    @Query(value = "Select \n" +
            "CONCAT (ts.STATUS_CODE, ' - ', ts.STATUS_TEXT) \n" +
            "From tblopstatus ts \n" +
            "Where \n" +
            "ts.LANG_ID IN (:languageId) and \n" +
            "ts.C_ID IN (:companyId) and \n" +
            "ts.STATUS_CODE IN (:statusCode) and \n" +
            "ts.IS_DELETED = 0", nativeQuery = true)
    String getOpStatusDescription(@Param(value = "languageId") String languageId,
                                  @Param(value = "companyId") String companyId,
                                  @Param(value = "statusCode") String statusCode);

}
