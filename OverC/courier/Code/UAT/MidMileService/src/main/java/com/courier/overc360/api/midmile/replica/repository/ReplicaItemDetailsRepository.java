package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.dto.PreAlertManifestImpl;
import com.courier.overc360.api.midmile.replica.model.itemdetails.ReplicaItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReplicaItemDetailsRepository extends JpaRepository<ReplicaItemDetails, String>, JpaSpecificationExecutor<ReplicaItemDetails> {

    Optional<ReplicaItemDetails> findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndDeletionIndicator(
            String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId, Long deletionIndicator);

    List<ReplicaItemDetails> findByLanguageIdAndCompanyIdAndPieceIdAndDeletionIndicator(
            String languageId, String companyId, String pieceId, Long deletionIndicator);

    @Query(value = "Select \n" +
            "it.LANG_ID languageId,\n" +
            "it.C_ID companyId,\n" +
            "it.PARTNER_ID partnerId,\n" +
            "it.MASTER_AIRWAY_BILL masterAirwayBill,\n" +
            "it.HOUSE_AIRWAY_BILL houseAirwayBill,\n" +
            "it.PIECE_ID pieceId,\n" +
            "it.PIECE_ITEM_ID pieceItemId,\n" +
            "it.QUANTITY quantity,\n" +
            "it.UNITVALUE unitValue,\n" +
            "it.CURRENCY currency,\n" +
            "it.IMAGE_REF_ID imageReferenceId,\n" +
            "it.LANG_TEXT languageDescription,\n" +
            "it.C_NAME companyName,\n" +
            "tc.PARTNER_TYPE partnerType,\n" +
            "it.PARTNER_NAME partnerName,\n" +
            "it.PARTNER_MASTER_AIRWAY_BILL partnerMasterAirwayBill,\n" +
            "it.PARTNER_HOUSE_AIRWAY_BILL partnerHouseAirwayBill,\n" +
            "it.DESCRIPTION description,\n" +
            "it.CONSIGNMENT_ID consignmentId,\n" +
            "it.ITEM_CODE itemCode,\n" +
            "it.HS_CODE hsCode,\n" +
            "it.DECLARED_VALUE declaredValue,\n" +
            "it.COD_AMOUNT codAmount,\n" +
            "it.LENGTH length,\n" +
            "it.DIMENSION_UNIT dimensionUnit,\n" +
            "it.WIDTH width,\n" +
            "it.HEIGHT height,\n" +
            "it.WEIGHT weight,\n" +
            "it.WEIGHT_UNIT weightUnit,\n" +
            "it.VOLUME volume,\n" +
            "it.VOLUME_UNIT volumeUnit,\n" +
            "it.IS_DELETED deletionIndicator,\n" +
            "it.REF_FIELD_1 referenceField1,\n" +
            "it.REF_FIELD_2 referenceField2,\n" +
            "it.REF_FIELD_3 referenceField3,\n" +
            "it.REF_FIELD_4 referenceField4,\n" +
            "it.REF_FIELD_5 referenceField5,\n" +
            "it.REF_FIELD_6 referenceField6,\n" +
            "it.REF_FIELD_7 referenceField7,\n" +
            "it.REF_FIELD_8 referenceField8,\n" +
            "it.REF_FIELD_9 referenceField9,\n" +
            "it.REF_FIELD_10 referenceField10,\n" +
            "it.REF_FIELD_11 referenceField11,\n" +
            "it.REF_FIELD_12 referenceField12,\n" +
            "it.REF_FIELD_13 referenceField13,\n" +
            "it.REF_FIELD_14 referenceField14,\n" +
            "it.REF_FIELD_15 referenceField15,\n" +
            "it.REF_FIELD_16 referenceField16,\n" +
            "it.REF_FIELD_17 referenceField17,\n" +
            "it.REF_FIELD_18 referenceField18,\n" +
            "it.REF_FIELD_19 referenceField19,\n" +
            "it.REF_FIELD_20 referenceField20,\n" +
            "tc.INCO_TERMS incoTerms,\n" +
            "tc.PAYMENT_TYPE paymentType,\n" +
            "tc.EVENT_CODE eventCode,\n" +
            "tc.EVENT_TEXT eventText,\n" +
            "tc.STATUS_ID statusId,\n" +
            "tc.STATUS_TEXT statusDescription,\n" +
            "tc.PRE_ALERT_VALIDATION_INDIACATOR preAlertValidationIndicator,\n" +
            "tc.CONSOLE_INDICATOR consoleIndicator,\n" +
            "tc.MANIFEST_INDICATOR manifestIndicator,\n" +
            "it.CTD_BY createdBy,\n" +
            "it.CTD_ON createdOn,\n" +
            "it.UTD_BY updatedBy,\n" +
            "it.UTD_ON updatedOn\n" +
            "from tblitemdetails it \n" +
            "join tblconsignment_entity tc on tc.consignment_id = it.consignment_id \n" +
            "where \n" +
            "(COALESCE(:consignmentId, null) IS NULL OR (tc.CONSIGNMENT_ID IN (:consignmentId))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (tc.LANG_ID IN (:languageId))) and \n" +
            "(COALESCE(:companyId, null) IS NULL OR (tc.C_ID IN (:companyId))) and \n" +
            "(COALESCE(:partnerId, null) IS NULL OR (tc.PARTNER_ID IN (:partnerId))) and \n" +
            "(COALESCE(:statusId, null) IS NULL OR (tc.STATUS_ID IN (:statusId))) and \n" +
            "tc.consignment_id not in (select tc1.consignment_id from tblconsignment_entity tc1 where tc1.is_deleted = 0 and tc1.CONSOLE_INDICATOR = 1 and tc1.MANIFEST_INDICATOR = 1) and \n" +
            "it.is_deleted = 0 and tc.is_deleted = 0 ", nativeQuery = true)
    List<PreAlertManifestImpl> getPreAlertManifest(@Param(value = "consignmentId") List<Long> consignmentId,
                                                   @Param(value = "languageId") List<String> languageId,
                                                   @Param(value = "companyId") List<String> companyId,
                                                   @Param(value = "partnerId") List<String> partnerId,
                                                   @Param(value = "statusId") List<String> statusId);

    @Query(value = "Select \n" +
            "it.LANG_ID languageId,\n" +
            "it.C_ID companyId,\n" +
            "it.PARTNER_ID partnerId,\n" +
            "it.MASTER_AIRWAY_BILL masterAirwayBill,\n" +
            "it.HOUSE_AIRWAY_BILL houseAirwayBill,\n" +
            "it.PIECE_ID pieceId,\n" +
            "it.PIECE_ITEM_ID pieceItemId,\n" +
            "it.QUANTITY quantity,\n" +
            "it.UNITVALUE unitValue,\n" +
            "it.CURRENCY currency,\n" +
            "it.IMAGE_REF_ID imageReferenceId,\n" +
            "it.LANG_TEXT languageDescription,\n" +
            "it.C_NAME companyName,\n" +
            "tc.PARTNER_TYPE partnerType,\n" +
            "it.PARTNER_NAME partnerName,\n" +
            "it.PARTNER_MASTER_AIRWAY_BILL partnerMasterAirwayBill,\n" +
            "it.PARTNER_HOUSE_AIRWAY_BILL partnerHouseAirwayBill,\n" +
            "it.DESCRIPTION description,\n" +
            "it.CONSIGNMENT_ID consignmentId,\n" +
            "it.ITEM_CODE itemCode,\n" +
            "it.HS_CODE hsCode,\n" +
            "it.DECLARED_VALUE declaredValue,\n" +
            "it.COD_AMOUNT codAmount,\n" +
            "it.LENGTH length,\n" +
            "it.DIMENSION_UNIT dimensionUnit,\n" +
            "it.WIDTH width,\n" +
            "it.HEIGHT height,\n" +
            "it.WEIGHT weight,\n" +
            "it.WEIGHT_UNIT weightUnit,\n" +
            "it.VOLUME volume,\n" +
            "it.VOLUME_UNIT volumeUnit,\n" +
            "it.IS_DELETED deletionIndicator,\n" +
            "it.REF_FIELD_1 referenceField1,\n" +
            "it.REF_FIELD_2 referenceField2,\n" +
            "it.REF_FIELD_3 referenceField3,\n" +
            "it.REF_FIELD_4 referenceField4,\n" +
            "it.REF_FIELD_5 referenceField5,\n" +
            "it.REF_FIELD_6 referenceField6,\n" +
            "it.REF_FIELD_7 referenceField7,\n" +
            "it.REF_FIELD_8 referenceField8,\n" +
            "it.REF_FIELD_9 referenceField9,\n" +
            "it.REF_FIELD_10 referenceField10,\n" +
            "it.REF_FIELD_11 referenceField11,\n" +
            "it.REF_FIELD_12 referenceField12,\n" +
            "it.REF_FIELD_13 referenceField13,\n" +
            "it.REF_FIELD_14 referenceField14,\n" +
            "it.REF_FIELD_15 referenceField15,\n" +
            "it.REF_FIELD_16 referenceField16,\n" +
            "it.REF_FIELD_17 referenceField17,\n" +
            "it.REF_FIELD_18 referenceField18,\n" +
            "it.REF_FIELD_19 referenceField19,\n" +
            "it.REF_FIELD_20 referenceField20,\n" +
            "tc.INCO_TERMS incoTerms,\n" +
            "tc.PAYMENT_TYPE paymentType,\n" +
            "tc.EVENT_CODE eventCode,\n" +
            "tc.EVENT_TEXT eventText,\n" +
            "tc.STATUS_ID statusId,\n" +
            "tc.STATUS_TEXT statusDescription,\n" +
            "tc.PRE_ALERT_VALIDATION_INDIACATOR preAlertValidationIndicator,\n" +
            "tc.CONSOLE_INDICATOR consoleIndicator,\n" +
            "tc.MANIFEST_INDICATOR manifestIndicator,\n" +
            "it.CTD_BY createdBy,\n" +
            "it.CTD_ON createdOn,\n" +
            "it.UTD_BY updatedBy,\n" +
            "it.UTD_ON updatedOn\n" +
            "from tblitemdetails it \n" +
            "join tblconsignment_entity tc on tc.consignment_id = it.consignment_id \n" +
            "where \n" +
            "(COALESCE(:consignmentId, null) IS NULL OR (tc.CONSIGNMENT_ID IN (:consignmentId))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (tc.LANG_ID IN (:languageId))) and \n" +
            "(COALESCE(:companyId, null) IS NULL OR (tc.C_ID IN (:companyId))) and \n" +
            "(COALESCE(:partnerId, null) IS NULL OR (tc.PARTNER_ID IN (:partnerId))) and \n" +
            "(COALESCE(:statusId, null) IS NULL OR (tc.STATUS_ID IN (:statusId))) and \n" +
            "tc.CONSOLE_INDICATOR in :consoleIndicator and tc.MANIFEST_INDICATOR in :manifestIndicator and \n" +
            "it.is_deleted = 0 and tc.is_deleted = 0 ", nativeQuery = true)
    List<PreAlertManifestImpl> getPreAlertManifest(@Param(value = "consignmentId") List<Long> consignmentId,
                                                   @Param(value = "languageId") List<String> languageId,
                                                   @Param(value = "companyId") List<String> companyId,
                                                   @Param(value = "partnerId") List<String> partnerId,
                                                   @Param(value = "statusId") List<String> statusId,
                                                   @Param(value = "consoleIndicator") List<Long> consoleIndicator,
                                                   @Param(value = "manifestIndicator") List<Long> manifestIndicator);

}
