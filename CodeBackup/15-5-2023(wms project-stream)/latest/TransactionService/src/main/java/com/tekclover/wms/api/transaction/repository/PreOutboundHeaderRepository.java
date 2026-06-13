package com.tekclover.wms.api.transaction.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.tekclover.wms.api.transaction.model.outbound.preoutbound.PreOutboundHeaderStream;
import com.tekclover.wms.api.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tekclover.wms.api.transaction.model.outbound.preoutbound.PreOutboundHeader;

@Repository
//@Transactional
public interface PreOutboundHeaderRepository extends JpaRepository<PreOutboundHeader,Long>,
		JpaSpecificationExecutor<PreOutboundHeader>, StreamableJpaSpecificationRepository<PreOutboundHeader> {
	
	public List<PreOutboundHeader> findAll();
	
	/**
	 * 
	 * @param languageId
	 * @param companyCodeId
	 * @param plantId
	 * @param warehouseId
	 * @param refDocNumber
	 * @param preOutboundNo
	 * @param partnerCode
	 * @param deletionIndicator
	 * @return
	 */
	public Optional<PreOutboundHeader> 
		findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndRefDocNumberAndPreOutboundNoAndPartnerCodeAndDeletionIndicator(
				String languageId, String companyCodeId, String plantId, String warehouseId, String refDocNumber, 
				String preOutboundNo, String partnerCode, Long deletionIndicator);

	public PreOutboundHeader findByPreOutboundNo(String preOutboundNo);

	public Optional<PreOutboundHeader> findByRefDocNumberAndDeletionIndicator(String refDocumentNo, long l);
	
	/**
	 * 
	 * @param warehouseId
	 * @param refDocNumber
	 * @param statusId
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE PreOutboundHeader ib SET ib.statusId = :statusId WHERE ib.warehouseId = :warehouseId AND ib.refDocNumber = :refDocNumber")
	void updatePreOutboundHeaderStatus(@Param ("warehouseId") String warehouseId,
			@Param ("refDocNumber") String refDocNumber, @Param ("statusId") Long statusId);

	public Optional<PreOutboundHeader> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndRefDocNumberAndDeletionIndicator(
			String languageId, String companyCode, String plantId, String warehouseId, String refDocNumber, long l);

	@QueryHints(@javax.persistence.QueryHint(name="org.hibernate.fetchSize",value="100"))
	@Query(value = "select \n" +
			"oh.lang_id languageId, \n" +
			"oh.c_id companyCodeId, \n" +
			"oh.plant_id plantId, \n" +
			"oh.wh_id warehouseId, \n" +
			"oh.ref_doc_no refDocNumber, \n" +
			"oh.pre_ob_no preOutboundNo, \n" +
			"oh.partner_code partnerCode, \n" +
			"oh.ob_ord_typ_id outboundOrderTypeId, \n" +
			"oh.ref_doc_typ referenceDocumentType, \n" +
			"oh.status_id statusId, \n" +
			"oh.ref_doc_date refDocDate, \n" +
			"oh.req_del_date requiredDeliveryDate, \n" +
			"oh.ref_field_1 referenceField1, \n" +
			"oh.ref_field_2 referenceField2, \n" +
			"oh.ref_field_3 referenceField3, \n" +
			"oh.ref_field_4 referenceField4, \n" +
			"oh.ref_field_5 referenceField5, \n" +
			"oh.ref_field_6 referenceField6, \n" +
			"oh.ref_field_7 referenceField7, \n" +
			"oh.ref_field_8 referenceField8, \n" +
			"oh.ref_field_9 referenceField9, \n" +
			"oh.ref_field_10 referenceField10, \n" +
			"oh.is_deleted deletionIndicator, \n" +
			"oh.remark remarks, \n" +
			"oh.pre_ob_ctd_by createdBy, \n" +
			"oh.pre_ob_ctd_on createdOn, \n" +
			"oh.pre_ob_utd_by updatedBy, \n" +
			"oh.pre_ob_utd_on updatedOn, \n" +
			"ts.status_text statusDescription\n" +
			"from tblpreoutboundheader oh join tblstatusid ts on ts.status_id=oh.status_id and ts.lang_id=oh.lang_id where \n"+
			"(COALESCE(:warehouseId, null) IS NULL OR (oh.wh_id IN (:warehouseId))) and \n" +
			"(COALESCE(:preOutboundNo, null) IS NULL OR (oh.pre_ob_no IN (:preOutboundNo))) and \n" +
			"(COALESCE(:outboundOrderTypeId, null) IS NULL OR (oh.ob_ord_typ_id IN (:outboundOrderTypeId))) and \n" +
			"(COALESCE(:soType, null) IS NULL OR (oh.REF_FIELD_1 IN (:soType))) and \n" +
			"(COALESCE(:soNumber, null) IS NULL OR (oh.REF_DOC_NO IN (:soNumber))) and \n" +
			"(COALESCE(:partnerCode, null) IS NULL OR (oh.PARTNER_CODE IN (:partnerCode))) and\n" +
			"(COALESCE(:statusId, null) IS NULL OR (oh.STATUS_ID IN (:statusId))) and\n" +
			"(COALESCE(:createdBy, null) IS NULL OR (oh.PRE_OB_CTD_BY IN (:createdBy))) and\n" +
			"(COALESCE(CONVERT(VARCHAR(255), :startRequiredDeliveryDate), null) IS NULL OR (oh.REQ_DEL_DATE between COALESCE(CONVERT(VARCHAR(255), :startRequiredDeliveryDate), null) and COALESCE(CONVERT(VARCHAR(255), :endRequiredDeliveryDate), null))) and\n" +
			"(COALESCE(CONVERT(VARCHAR(255), :startOrderDate), null) IS NULL OR (oh.REF_DOC_DATE between COALESCE(CONVERT(VARCHAR(255), :startOrderDate), null) and COALESCE(CONVERT(VARCHAR(255), :endOrderDate), null))) and\n" +
			"(COALESCE(CONVERT(VARCHAR(255), :startCreatedOn), null) IS NULL OR (oh.PRE_OB_CTD_ON between COALESCE(CONVERT(VARCHAR(255), :startCreatedOn), null) and COALESCE(CONVERT(VARCHAR(255), :endCreatedOn), null)))", nativeQuery = true)
//	Stream<PreOutboundHeaderStream> findAllPreOutBoundHeaderData (
	List<PreOutboundHeaderStream> findAllPreOutBoundHeaderData (
			@Param(value = "warehouseId") List<String> warehouseId,
			@Param(value = "preOutboundNo") List<String> preOutboundNo,
			@Param(value = "outboundOrderTypeId") List<Long> outboundOrderTypeId,
			@Param(value = "soType") List<String> soType,
			@Param(value = "soNumber") List<String> soNumber,
			@Param(value = "partnerCode") List<String> partnerCode,
			@Param(value = "statusId") List<Long> statusId,
			@Param(value = "createdBy") List<String> createdBy,
			@Param(value = "startRequiredDeliveryDate") Date startRequiredDeliveryDate,
			@Param(value = "endRequiredDeliveryDate") Date endRequiredDeliveryDate,
			@Param(value = "startOrderDate") Date startOrderDate,
			@Param(value = "endOrderDate") Date endOrderDate,
			@Param(value = "startCreatedOn") Date startCreatedOn,
			@Param(value = "endCreatedOn") Date endCreatedOn);

	@QueryHints(@javax.persistence.QueryHint(name="org.hibernate.fetchSize",value="100"))
	@Query(value = "select status_text statusDescription from tblstatusid where is_deleted = 0 and status_id in (:statusId)",nativeQuery = true)
	Stream<String> findStatusDescription(
			@Param(value = "statusId") Long statusId);

}