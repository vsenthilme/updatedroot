package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.notification.ReplicaNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaNotificationRepository extends JpaRepository<ReplicaNotification, String>, JpaSpecificationExecutor<ReplicaNotification> {

    Optional<ReplicaNotification> findByLanguageIdAndCompanyIdAndNotificationIdAndDeletionIndicator
            (String languageId, String companyId, String notificationId, Long deletionIndicator);


    // Get Service Type Name
    @Query(value = "Select \n" +
            "CONCAT (tst.SERVICE_TYPE_ID, ' - ', tst.SERVICE_TYPE_TEXT)\n" +
            "From tblservicetype tst \n" +
            "Where \n" +
            "tst.LANG_ID IN (:languageId) and \n" +
            "tst.C_ID IN (:companyId) and \n" +
            "tst.SERVICE_TYPE_ID IN (:serviceTypeId) and \n" +
            "tst.IS_DELETED = 0", nativeQuery = true)
    String getServiceTypeDesc(@Param(value = "serviceTypeId") String serviceTypeId,
                              @Param(value = "languageId") String languageId,
                              @Param(value = "companyId") String companyId);

    // Get Product Name
    @Query(value = "Select Top 1 \n" +
            "CONCAT (tp.PRODUCT_ID, ' - ', tp.PRODUCT_NAME)\n" +
            "From tblproduct tp \n" +
            "Where \n" +
            "tp.LANG_ID IN (:languageId) and \n" +
            "tp.C_ID IN (:companyId) and \n" +
            "tp.PRODUCT_ID IN (:productId) and \n" +
            "tp.SUB_PRODUCT_ID IN (:subProductId) and \n" +
            "tp.IS_DELETED = 0", nativeQuery = true)
    String getProductDesc(@Param(value = "productId") String productId,
                          @Param(value = "languageId") String languageId,
                          @Param(value = "companyId") String companyId,
                          @Param(value = "subProductId") String subProductId);


    // Get SubProduct Name
    @Query(value = "Select Top 1 \n" +
            "CONCAT (tsp.SUB_PRODUCT_ID, ' - ', tsp.SUB_PRODUCT_NAME) \n" +
            "From tblsubproduct tsp \n" +
            "Where \n" +
            "tsp.LANG_ID IN (:languageId) and \n" +
            "tsp.C_ID IN (:companyId) and \n" +
            "tsp.SUB_PRODUCT_ID IN (:subProductId) and \n" +
            "tsp.IS_DELETED = 0", nativeQuery = true)
    String getSubProductDesc(@Param(value = "subProductId") String subProductId,
                             @Param(value = "languageId") String languageId,
                             @Param(value = "companyId") String companyId);

}
