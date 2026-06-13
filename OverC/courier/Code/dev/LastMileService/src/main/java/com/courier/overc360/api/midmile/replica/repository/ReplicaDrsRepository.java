package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaDrsRepository extends JpaRepository<ReplicaDrs, String>, JpaSpecificationExecutor<ReplicaDrs> {

    Optional<ReplicaDrs> findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId, Long deletionIndicator
    );

    @Query(value = "select notification_text notificationText, USER_ROLE userRole from tblnotification where " +
            "c_id = :companyId and lang_id = :languageId and notification_id = :notificationId and is_deleted = 0 ", nativeQuery = true)
    public IKeyValuePair getNotificationId(@Param("companyId") String companyId,
                                           @Param("languageId") String languageId,
                                           @Param("notificationId") String notificationId);

    @Query(value = "select user_id userId from tblusermanagement where user_role_id = :roleId " +
            " and c_id = :companyId and lang_id = :languageId and is_deleted = 0 ", nativeQuery = true)
    public List<String> getUserId(@Param("companyId") String companyId,
                                  @Param("languageId") String languageId,
                                  @Param("roleId") String roleId);

    @Query(value = "select token_id as tokenId from tblhhtnotification where " +
            " c_id = :companyId and usr_id in :userId and is_deleted = 0 ", nativeQuery = true)
    public List<String> getToken(@Param("companyId") String companyId,
                                 @Param("userId") List<String> userId);

    @Modifying
    @Query(value = "update tblconsole set noti_status = 1 where console_id = :consoleId and " +
            " c_id = :companyId and lang_id = :languageId and is_deleted =0 ", nativeQuery = true)
    public void updateNotificationInConsoleTable(@Param("companyId") String companyId,
                                                 @Param("languageId") String languageId,
                                                 @Param("consoleId") String consoleId);

}
