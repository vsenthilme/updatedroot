package com.tekclover.wms.api.transaction.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.tekclover.wms.api.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tekclover.wms.api.transaction.model.inbound.gr.GrHeader;

@Repository
@Transactional
public interface GrHeaderRepository extends JpaRepository<GrHeader,Long>, JpaSpecificationExecutor<GrHeader>,
		StreamableJpaSpecificationRepository<GrHeader> {
	
	public List<GrHeader> findAll();
	
	public Optional<GrHeader> 
		findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndPreInboundNoAndRefDocNumberAndStagingNoAndGoodsReceiptNoAndPalletCodeAndCaseCodeAndDeletionIndicator(
				String languageId, String companyCodeId, String plantId, String warehouseId, String preInboundNo, String refDocNumber, String stagingNo, String goodsReceiptNo, 
				String palletCode, String caseCode, Long deletionIndicator);

	/**
	 * 
	 * @param languageId
	 * @param companyCode
	 * @param plantId
	 * @param warehouseId
	 * @param goodsReceiptNo
	 * @param caseCode
	 * @param refDocNumber
	 * @param deletionIndicator
	 * @return
	 */
	public List<GrHeader> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndGoodsReceiptNoAndCaseCodeAndRefDocNumberAndDeletionIndicator(
			String languageId, String companyCode, String plantId, String warehouseId, String goodsReceiptNo,
			String caseCode, String refDocNumber, Long deletionIndicator);

	/**
	 * 
	 * @param languageId
	 * @param companyCode
	 * @param plantId
	 * @param refDocNumber
	 * @param warehouseId
	 * @param preInboundNo
	 * @param caseCode
	 * @param deletionIndicator
	 * @return
	 */
	public List<GrHeader> findByLanguageIdAndCompanyCodeAndPlantIdAndRefDocNumberAndWarehouseIdAndPreInboundNoAndCaseCodeAndDeletionIndicator(
			String languageId, String companyCode, String plantId, String refDocNumber, String warehouseId, 
			String preInboundNo, String caseCode, Long deletionIndicator);

	public List<GrHeader> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndPreInboundNoAndRefDocNumberAndDeletionIndicator(
			String languageId, String companyCode, String plantId, String warehouseId, String preInboundNo,
			String refDocNumber, long l);

	@Query(value = "select status_text from tblstatusid \n" +
			"where is_deleted = 0 and \n" +
			"status_id in (:statusId) and lang_id in (:languageId)",nativeQuery = true)
	public String getStatusDescription(@Param(value = "statusId") Long statusId,
								@Param(value = "languageId") String languageId);

	/**
	 * 
	 * @param refDocNumber
	 * @param statusId
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE GrHeader ib SET ib.statusId = :statusId WHERE ib.warehouseId = :warehouseId AND ib.refDocNumber = :refDocNumber")
	void updateGrHeaderStatus(@Param ("warehouseId") String warehouseId,
			@Param ("refDocNumber") String refDocNumber, @Param ("statusId") Long statusId);
}