package com.tekclover.wms.api.transaction.repository;

<<<<<<< HEAD
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
=======
import java.util.List;
import java.util.Optional;

import com.tekclover.wms.api.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tekclover.wms.api.transaction.model.outbound.ordermangement.OrderManagementLine;
<<<<<<< HEAD
import com.tekclover.wms.api.transaction.repository.fragments.StreamableJpaSpecificationRepository;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

@Repository
@Transactional
public interface OrderManagementLineRepository extends JpaRepository<OrderManagementLine,Long>,
		JpaSpecificationExecutor<OrderManagementLine>, StreamableJpaSpecificationRepository<OrderManagementLine> {
	
	public List<OrderManagementLine> findAll();
	
	public Optional<OrderManagementLine> findByRefDocNumber(String refDocNumber);
	
	public List<OrderManagementLine> findByPreOutboundNoAndLineNumberAndItemCodeAndDeletionIndicator(String preOutboundNo,
			Long lineNumber, String itemCode, Long deletionIndicator);

	public OrderManagementLine findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndPreOutboundNoAndRefDocNumberAndPartnerCodeAndLineNumberAndItemCodeAndProposedStorageBinAndProposedPackBarCodeAndDeletionIndicator(
			String languageId, String companyCodeId, String plantId, String warehouseId, String preOutboundNo,
			String refDocNumber, String partnerCode, Long lineNumber, String itemCode, String proposedStorageBin, String proposedPackBarCode, Long deletionIndicator);

	public OrderManagementLine findByWarehouseIdAndPreOutboundNoAndRefDocNumberAndPartnerCodeAndLineNumberAndItemCodeAndProposedStorageBinAndProposedPackBarCodeAndDeletionIndicator(
			String warehouseId, String preOutboundNo, String refDocNumber, String partnerCode, Long lineNumber,
			String itemCode, String proposedStorageBin, String proposedPackBarCode, Long deletionIndicator);

	public List<OrderManagementLine> findByWarehouseIdAndPreOutboundNoAndRefDocNumberAndPartnerCodeAndLineNumberAndItemCodeAndDeletionIndicator(
			String warehouseId, String preOutboundNo, String refDocNumber, String partnerCode, Long lineNumber,
			String itemCode, Long deletionIndicator);

	public List<OrderManagementLine> findAllByWarehouseIdAndPreOutboundNoAndRefDocNumberAndPartnerCodeAndLineNumberAndItemCodeAndDeletionIndicator(
			String warehouseId, String preOutboundNo, String refDocNumber, String partnerCode, Long lineNumber,
			String itemCode, Long deletionIndicator);

	public List<OrderManagementLine> findAllByWarehouseIdAndPreOutboundNoAndRefDocNumberAndPartnerCodeAndLineNumberAndItemCodeAndProposedStorageBinAndProposedPackBarCodeAndDeletionIndicator(
			String warehouseId, String preOutboundNo, String refDocNumber, String partnerCode, Long lineNumber,
			String itemCode, String proposedStorageBin, String proposedPackBarCode, Long deletionIndicator);
	
	public List<OrderManagementLine> findByWarehouseIdAndStatusIdIn(String warehouseId, List<Long> statusIds);
	
	public List<OrderManagementLine> 
	findByWarehouseIdAndAndRefDocNumberAndStatusIdInAndDeletionIndicator(String warehouseId, String refDocNumber, List<Long> statusIds, Long delIndicator);
	
	@Query("Select count(ob) from OrderManagementLine ob where ob.warehouseId=:warehouseId and ob.refDocNumber=:refDocNumber \r\n"
			+ " and ob.preOutboundNo=:preOutboundNo and ob.statusId in :statusId and ob.deletionIndicator=:deletionIndicator")
	public long getByWarehouseIdAndAndRefDocNumberAndPreOutboundNoAndStatusIdInAndDeletionIndicator (
			 @Param ("warehouseId") String warehouseId, @Param ("refDocNumber") String refDocNumber, @Param ("preOutboundNo") String preOutboundNo, 
			 @Param ("statusId") List<Long> statusId, @Param ("deletionIndicator") long deletionIndicator);
<<<<<<< HEAD
	
	//----------------------------------------------------------------------------------------------------------------------------------------
	@Modifying(clearAutomatically = true)
	@Query("UPDATE OrderManagementLine ob SET ob.requiredDeliveryDate = :requiredDeliveryDate WHERE ob.warehouseId = :warehouseId AND ob.refDocNumber = :refDocNumber")
	void updateOrderManagementLineRequiredDeliveryDate(@Param ("warehouseId") String warehouseId,
			@Param ("refDocNumber") String refDocNumber, @Param ("requiredDeliveryDate") Date requiredDeliveryDate);
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}