package com.iweb2b.api.integration.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iweb2b.api.integration.model.consignment.entity.FlowConsignmentWebhookEntity;

@Repository
@Transactional
public interface FlowConsignmentWebhookRepository extends JpaRepository<FlowConsignmentWebhookEntity, Long> {
	
	public List<FlowConsignmentWebhookEntity> findByType(String type);
	
	@Query(value = "  SELECT * FROM tblflowconsignmentwebhook\r\n"
			+ "	WHERE CUSTOMER_REFERENCE_NUMBER = :customerRreferenceNumber and TYPE IN (:type) ", 
			nativeQuery = true)
	public List<FlowConsignmentWebhookEntity> findByCustomerReferenceNumberAndType(
			@Param("customerRreferenceNumber") String customerRreferenceNumber, 
			@Param("type") String type);
	
	@Query(value = "  SELECT TOP 1 * FROM tblflowconsignmentwebhook\r\n"
			+ "	WHERE CUSTOMER_REFERENCE_NUMBER = :customerRreferenceNumber ORDER BY EVENT_TIME_EPOCH DESC", 
			nativeQuery = true)
	public FlowConsignmentWebhookEntity findOrdersByType (@Param("customerRreferenceNumber") String customerRreferenceNumber);
	
	@Query(value = "  SELECT type FROM tblflowconsignmentwebhook\r\n"
			+ "	WHERE reference_number = :referenceNumber ", nativeQuery = true)
	public List<String> findTypeByReferenceNumber(@Param("referenceNumber") String referenceNumber);

	@Query(value = "SELECT *\r\n"
			+ "  FROM tblflowconsignmentwebhook\r\n"
			+ "  WHERE TYPE = :type AND EVENT_TIME BETWEEN :start_event_time AND :end_event_time AND CUSTOMER_CODE = :customerCode", 
			nativeQuery = true)
	public List<FlowConsignmentWebhookEntity> findByTypeAndEventTimeAndCustomerCode (
			@Param("type") String type, 
			@Param("start_event_time") Date start_event_time, 
			@Param("end_event_time") Date end_event_time,
			@Param("customerCode") String customerCode);
}