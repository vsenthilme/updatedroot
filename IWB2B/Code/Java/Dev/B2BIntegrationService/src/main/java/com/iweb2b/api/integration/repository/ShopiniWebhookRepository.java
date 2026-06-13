package com.iweb2b.api.integration.repository;

import java.util.Date;
import java.util.List;

import com.iweb2b.api.integration.model.consignment.dto.shopini.IShopiniWebhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iweb2b.api.integration.model.consignment.dto.shopini.ShopiniWebhook;
import com.iweb2b.api.integration.repository.fragments.StreamableJpaSpecificationRepository;

@Repository
@Transactional
public interface ShopiniWebhookRepository extends JpaRepository<ShopiniWebhook, Long>, StreamableJpaSpecificationRepository<ShopiniWebhook> {

	public List<ShopiniWebhook> findByTrackingNo(String trackingNo);
	
	public List<ShopiniWebhook> findByTrackingNoAndItemActionName(String trackingNo, String itemActionName);
	
	@Query(value = "  SELECT TRACKING_NO\r\n"
			+ "  FROM tblshopiniwebhook\r\n"
			+ "  WHERE ITEM_ACTION_NAME <> 'SENT' AND LMD_SYNCED_STATUS = 0 \r\n"
			+ "  GROUP BY TRACKING_NO\r\n"
			+ "  ORDER BY TRACKING_NO", nativeQuery = true)
	public List<String> findUniqueTrackingNo();
	
	@Query(value = "  SELECT TRACKING_NO\r\n"
			+ "  FROM tblshopiniwebhook\r\n"
			+ "  WHERE TRACKING_NO NOT IN (select TRACKING_NO FROM tblshopiniwebhook where ITEM_ACTION_NAME = 'delivered' AND LMD_SYNCED_STATUS = 1) \r\n"
			+ "  GROUP BY TRACKING_NO\r\n"
			+ "  ORDER BY TRACKING_NO", nativeQuery = true)
	public List<String> findShopiniUniqueTrackingNo();
	
	public List<ShopiniWebhook> findByTrackingNoAndItemActionNameNotOrderByActionDate(String trackingNo, String itemActionName);

	public List<ShopiniWebhook> findByTrackingNoAndItemActionNameInOrderByActionDate(String trackingNo, List<String> itemActionName);

	@Query(value = "SELECT TOP 1 TRACKING_NO FROM tblshopiniwebhook WHERE TRACKING_NO = :tracking_No AND ITEM_ACTION_NAME = :item_Action_Name \r\n"
			+ " ORDER BY ACTION_TIME DESC", nativeQuery = true)
	public ShopiniWebhook findLatestByTrackingNoAndItemActionName(String tracking_No, String item_Action_Name);
	
	@Query(value = "SELECT TRACKING_NO FROM tblshopiniwebhook where TRACKING_NO IN :tracking_No AND ITEM_ACTION_NAME <> :item_Action_Name AND LMD_SYNCED_STATUS = 0", nativeQuery = true)
	public List<String> findByActionName(List<String> tracking_No, String item_Action_Name);

	@Query(value = "SELECT TRACKING_NO FROM tblshopiniwebhook where TRACKING_NO IN :tracking_No AND ITEM_ACTION_NAME IN :item_Action_Name AND LMD_SYNCED_STATUS = 0", nativeQuery = true)
	public List<String> findByActionName(List<String> tracking_No, List<String> item_Action_Name);

	@Query(value = "SELECT TRACKING_NO FROM tblshopiniwebhook where \n" +
			"TRACKING_NO IN (select tc.AWB_3RD_PARTY from tblconsignment3 tc join tblconsignmentwebhook tcw on tcw.REFERENCE_NUMBER = tc.REFERENCE_NUMBER WHERE tcw.hub_code = :hubCode GROUP BY tc.AWB_3RD_PARTY) \n" +
			"AND ITEM_ACTION_NAME IN :item_Action_Name AND LMD_SYNCED_STATUS = 0", nativeQuery = true)
	public List<String> findByActionName(@Param(value="hubCode") String hubCode,
										 @Param(value="item_Action_Name") List<String> item_Action_Name);

	@Query(value = "SELECT TRACKING_NO FROM tblshopiniwebhook where TRACKING_NO IN :trackingNo AND ITEM_ACTION_NAME IN :itemActionName", nativeQuery = true)
	public List<String> findByActionName(@Param("trackingNo") String trackingNo,
										 @Param("itemActionName") String itemActionName);

	public List<ShopiniWebhook> findByTrackingNoAndItemActionNameOrderByActionDate(String trackingNo, String itemActionName);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE tblshopiniwebhook set LMD_SYNCED_STATUS = 1 WHERE TRACKING_NO = :trackingNo", nativeQuery = true)
	public void updateTrackingNumber (@Param("trackingNo") String trackingNo);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE tblshopiniwebhook set LMD_SYNCED_STATUS = 1 WHERE TRACKING_NO = :trackingNo AND LMD_SYNCED_STATUS = 0 AND ITEM_ACTION_NAME = 'SENT'", nativeQuery = true)
	public void updateShopiniTrackingNumber (@Param("trackingNo") String trackingNo);

	@Query(value = "select\n" +
			"TRACKING_NO trackingNo,\n" +
			"REFERENCE_NUMBER referenceNumber,\n" +
			"SHIPMENT_STATUS shipmentStatus,\n" +
			"ACTION_DATE actionDate,\n" +
			"ITEM_ACTION_NAME itemActionName,\n" +
			"LMD_SYNCED_STATUS lmdStatus\n" +
			"from tblshopiniwebhook\n" +
			"where\n" +
			"(COALESCE(:trackingNo, null) IS NULL OR (TRACKING_NO IN (:trackingNo))) and \n" +
			"(COALESCE(:referenceNumber, null) IS NULL OR (REFERENCE_NUMBER IN (:referenceNumber))) and \n" +
			"(COALESCE(:shipmentStatus, null) IS NULL OR (SHIPMENT_STATUS IN (:shipmentStatus))) and \n" +
			"(COALESCE(:itemActionName, null) IS NULL OR (ITEM_ACTION_NAME IN (:itemActionName))) and \n" +
			"(COALESCE(:lmdStatus, null) IS NULL OR (LMD_SYNCED_STATUS IN (:lmdStatus))) and \n" +
			"(COALESCE(CONVERT(VARCHAR(255), :fromDate), null) IS NULL OR (action_date between COALESCE(CONVERT(VARCHAR(255), :fromDate), null) and COALESCE(CONVERT(VARCHAR(255), :toDate), null)))", nativeQuery = true)
	public List<IShopiniWebhook> findShopiniWebhook(@Param("trackingNo") List<String> trackingNo,
													@Param("referenceNumber") List<String> referenceNumber,
													@Param("shipmentStatus") List<String> shipmentStatus,
													@Param("itemActionName") List<String> itemActionName,
													@Param("lmdStatus") List<Long> lmdStatus,
													@Param("fromDate") Date fromDate,
													@Param("toDate") Date toDate);

}

