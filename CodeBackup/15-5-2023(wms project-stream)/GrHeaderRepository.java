package com.tekclover.wms.api.transaction.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.tekclover.wms.api.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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


//	@QueryHints(@javax.persistence.QueryHint(name="org.hibernate.fetchSize",value="100"))
//	@Query(value = "SELECT \r\n" +
//			"mt.LANG_ID	languageId, \r\n" +
//			"mt.C_ID companyCode, \r\n" +
//			"mt.PLANT_ID plantId, \r\n" +
//			"mt.WH_ID warehouseId, \r\n" +
//			"mt.PRE_IB_NO preInboundNo, \r\n" +
//			"mt.REF_DOC_NO refDocNumber, \r\n" +
//			"mt.STG_NO stagingNo, \r\n" +
//			"mt.GR_NO goodsReceiptNo, \r\n" +
//			"mt.PAL_CODE palletCode, \r\n" +
//			"mt.CASE_CODE caseCode, \r\n" +
//			"mt.IB_ORD_TYP_ID inboundOrderTypeId, \r\n" +
//			"mt.STATUS_ID statusId, \r\n" +
//			"mt.GR_MTD grMethod, \r\n" +
//			"mt.CONT_REC_NO containerReceiptNo, \r\n" +
//			"mt.DOCK_ALL_NO dockAllocationNo, \r\n" +
//			"mt.CONT_NO containerNo, \r\n" +
//			"mt.VEH_NO vechicleNo, \r\n" +
//			"mt.EA_DATE expectedArrivalDate, \r\n" +
//			"mt.GR_DATE goodsReceiptDate, \r\n" +
//			"mt.IS_DELETED deletionIndicator, \r\n" +
//			"mt.REF_FIELD_1	referenceField1, \r\n" +
//			"mt.REF_FIELD_2	referenceField2, \r\n" +
//			"mt.REF_FIELD_3	referenceField3, \r\n" +
//			"mt.REF_FIELD_4	referenceField4, \r\n" +
//			"mt.REF_FIELD_5	referenceField5, \r\n" +
//			"mt.REF_FIELD_6	referenceField6, \r\n" +
//			"mt.REF_FIELD_7	referenceField7, \r\n" +
//			"mt.REF_FIELD_8	referenceField8, \r\n" +
//			"mt.REF_FIELD_9	referenceField9, \r\n" +
//			"mt.REF_FIELD_10 referenceField10, \r\n" +
//			"mt.GR_CTD_BY createdBy, \r\n" +
//			"mt.GR_UTD_BY updatedBy, \r\n" +
//			"mt.GR_CNF_BY confirmedBy, \r\n" +
//			"mt.GR_CTD_ON createdOn, \r\n" +
//			"mt.GR_UTD_ON updatedOn, \r\n" +
//			"mt.GR_CNF_ON confirmedOn, \r\n" +
//			"ts.STATUS_TEXT statusDescription \r\n" +
//			"FROM tblgrheader mt \r\n" +
//			"left join tblstatusid ts on ts.status_id = mt.status_id and ts.lang_id = mt.lang_id \n"+
//			"WHERE \n"+
//			"(COALESCE(:inboundOrderTypeId,null) IS NULL OR (mt.IB_ORD_TYP_ID IN (:inboundOrderTypeId))) and \n"+
//			"(COALESCE(:goodsReceiptNo,null) IS NULL OR (mt.GR_NO IN (:goodsReceiptNo))) and \n"+
//			"(COALESCE(:preInboundNo,null) IS NULL OR (mt.PRE_IB_NO IN (:preInboundNo))) and \n"+
//			"(COALESCE(:refDocNumber,null) IS NULL OR (mt.REF_DOC_NO IN (:refDocNumber))) and \n"+
////			 "(COALESCE(:startCreatedOn,null) IS NULL OR (mt.GR_CTD_ON BETWEEN :startCreatedOn AND :endCreatedOn)) and \n"+
//			"(COALESCE(:statusId,null) IS NULL OR (mt.STATUS_ID IN (:statusId))) and \n"+
//			"(COALESCE(:warehouseId,null) IS NULL OR (mt.WH_ID IN (:warehouseId))) and \n"+
//			 "(COALESCE(:caseCode,null) IS NULL OR (mt.CASE_CODE IN (:caseCode))) and \n"+
//			"(COALESCE(:createdBy,null) IS NULL OR (mt.GR_CTD_BY IN (:createdBy))) and \n"+
//			"mt.is_deleted = 0", nativeQuery = true)
//	Stream<GrHeader> findAllGrHeader (
//			@Param(value = "inboundOrderTypeId") List<Long> inboundOrderTypeId,
//			@Param(value = "goodsReceiptNo") List<String> goodsReceiptNo,
//			@Param(value = "preInboundNo") List<String> preInboundNo,
//			@Param(value = "refDocNumber") List<String> refDocNumber,
//			@Param(value = "statusId") List<Long> statusId,
//			@Param(value = "warehouseId") List<String> warehouseId,
//			@Param(value = "caseCode") List<String> caseCode,
//			@Param(value = "createdBy") List<String> createdBy
////			@Param(value = "startCreatedOn") Date startCreatedOn,
////			@Param(value = "endCreatedOn") Date endCreatedOn
//	);


}