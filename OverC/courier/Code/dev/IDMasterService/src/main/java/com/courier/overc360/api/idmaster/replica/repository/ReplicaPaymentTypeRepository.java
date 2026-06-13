package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.paymenttype.ReplicaPaymentType;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaPaymentTypeRepository extends JpaRepository<ReplicaPaymentType,String>, JpaSpecificationExecutor<ReplicaPaymentType> {
    Optional<ReplicaPaymentType> findByLanguageIdAndCompanyIdAndPaymentTypeIdAndDeletionIndicator(String languageId, String companyId, String paymentTypeId, long l);



    @Query(value = "Select \n" +
            "CONCAT (ts.STATUS_ID, ' - ', ts.STATUS_TEXT) \n" +
            "From tblstatus ts \n" +
            "Where \n" +
            "ts.STATUS_ID IN (:statusCode) and \n" +
            "ts.IS_DELETED = 0", nativeQuery = true)
    String getStatusDescription(@Param(value = "statusCode") String statusCode);
}
