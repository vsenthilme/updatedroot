package com.iweb2b.api.integration.repository;

import com.iweb2b.api.integration.model.consignment.entity.EPConsignmentWebhookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EPConsignmentWebhookRepository extends JpaRepository<EPConsignmentWebhookEntity, Long> {

    @Query(value = "  SELECT * FROM tblemiratesconsignmentwebhook\r\n"
            + "	WHERE CUSTOMER_REFERENCE_NUMBER = :customerRreferenceNumber and TYPE IN (:type) ",
            nativeQuery = true)
    public List<EPConsignmentWebhookEntity> findByCustomerReferenceNumberAndType(@Param("customerRreferenceNumber") String customerRreferenceNumber,
                                                                                 @Param("type") String type);

    @Query(value =
            "SELECT REFERENCE_NUMBER FROM tblemiratesconsignmentwebhook where REFERENCE_NUMBER NOT IN \n" +
            "(SELECT REFERENCE_NUMBER FROM tblemiratesconsignmentwebhook where REFERENCE_NUMBER IN \n" +
                    "(select tc.REFERENCE_NUMBER from tblconsignment3 tc\n" +
                    "join tblconsignmentwebhook tcw on tcw.REFERENCE_NUMBER = tc.REFERENCE_NUMBER \n" +
                    "WHERE tcw.hub_code = :hubCode GROUP BY tc.REFERENCE_NUMBER) \n" +
                    "AND TYPE = :status)", nativeQuery = true)
    public List<String> findByActionName(String status, String hubCode);

    @Query(value =
            "select tc1.REFERENCE_NUMBER from tblconsignment3 tc1 \n" +
            "join tblconsignmentwebhook tcw1 on tcw1.REFERENCE_NUMBER = tc1.REFERENCE_NUMBER \n" +
            "where tcw1.hub_code = :hubCode AND tc1.REFERENCE_NUMBER NOT IN \n" +
            "(select tcw.REFERENCE_NUMBER from tblconsignmentwebhook tcw \n" +
            "WHERE tcw.hub_code = :hubCode AND tcw.TYPE = :status GROUP BY tcw.REFERENCE_NUMBER) GROUP BY tc1.REFERENCE_NUMBER", nativeQuery = true)
    public List<String> findByReferenceNumber(String status, String hubCode);
}