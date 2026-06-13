package com.tekclover.wms.api.transaction.repository;

import com.tekclover.wms.api.transaction.model.IKeyValuePair;
import com.tekclover.wms.api.transaction.model.cyclecount.periodic.v2.PeriodicLineV2;
import com.tekclover.wms.api.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface PeriodicLineV2Repository extends JpaRepository<PeriodicLineV2, Long>,
        JpaSpecificationExecutor<PeriodicLineV2>, StreamableJpaSpecificationRepository<PeriodicLineV2> {

    //	SELECT ITM_CODE FROM tblperiodicline
//	WHERE WH_ID = 110 AND ITM_CODE IN :itemCode AND STATUS_ID <> 70
    @Query(value = "SELECT ITM_CODE FROM tblperiodicline \r\n"
            + "WHERE WH_ID = 110 AND ITM_CODE IN :itemCode \r\n"
            + "AND STATUS_ID <> 70 AND IS_DELETED = 0", nativeQuery = true)
    public List<String> findByStatusIdNotIn70(@Param(value = "itemCode") Set<String> itemCode);

    PeriodicLineV2 findByCompanyCodeAndPlantIdAndLanguageIdAndWarehouseIdAndCycleCountNoAndStorageBinAndItemCodeAndPackBarcodesAndDeletionIndicator(
            String companyCode, String plantId, String languageId, String warehouseId, String cycleCountNo,
            String storageBin, String itemCode, String packBarcodes, Long deletionIndicator);

    List<PeriodicLineV2> findByCompanyCodeAndPlantIdAndLanguageIdAndWarehouseIdAndCycleCountNoAndDeletionIndicator(
            String companyCode, String plantId, String languageId, String warehouseId, String cycleCountNo, Long deletionIndicator);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE PeriodicLineV2 pl \r\n"
            + " SET pl.cycleCounterId = :cycleCounterId, pl.cycleCounterName = :cycleCounterName, pl.statusId = :statusId, pl.statusDescription = :statusDescription,  \r\n"
            + " pl.countedBy = :countedBy, pl.countedOn = :countedOn "
            + " WHERE pl.companyCode = :companyCode AND pl.plantId = :plantId AND pl.languageId = :languageId AND pl.warehouseId = :warehouseId AND \r\n "
            + " pl.cycleCountNo = :cycleCountNo AND pl.storageBin in :storageBin AND pl.itemCode = :itemCode AND pl.packBarcodes = :packBarcodes"
            + " AND deletionIndicator = 0")
    public void updateHHTUser(
            @Param("cycleCounterId") String cycleCounterId,
            @Param("cycleCounterName") String cycleCounterName,
            @Param("statusId") Long statusId,
            @Param("statusDescription") String statusDescription,
            @Param("countedBy") String countedBy,
            @Param("countedOn") Date countedOn,
            @Param("companyCode") String companyCode,
            @Param("plantId") String plantId,
            @Param("languageId") String languageId,
            @Param("warehouseId") String warehouseId,
            @Param("cycleCountNo") String cycleCountNo,
            @Param("storageBin") String storageBin,
            @Param("itemCode") String itemCode,
            @Param("packBarcodes") String packBarcodes);

    List<PeriodicLineV2> findByCompanyCodeAndPlantIdAndLanguageIdAndWarehouseIdAndCycleCountNoAndStatusIdInAndDeletionIndicator(
            String companyCode, String plantId, String languageId, String warehouseId, String cycleCountNo, List<Long> statusId, Long deletionIndicator);


    @Query(value = "SELECT distinct ref_doc_no as refDocNumber, wh_id as warehouseId,\n" +
            "ref_doc_type as refDocType from tblperiodicheader\n" +
            "WHERE status_id = 72 AND noti_status = 0 AND is_deleted = 0", nativeQuery = true)
    List<IKeyValuePair> findByStatusIdAndNotificationStatusAndDeletionIndicatorDistinctRefDocNo();

    // Get Device Tokens
    @Query(value = "SELECT token_id AS tokenId FROM tblhhtnotification WHERE is_deleted=0\n" +
            "And lang_id = :languageId And c_id = :companyId And plant_id = :plantId And wh_id = :warehouseId And usr_id IN (:userId)", nativeQuery = true)
    List<String> getDeviceToken(@Param("languageId") String languageId,
                                @Param("companyId") String companyId,
                                @Param("plantId") String plantId,
                                @Param("warehouseId") String warehouseId,
                                @Param("userId") List<String> userId);

    // Update Notification Status
    @Modifying
    @Query(value = "UPDATE tblperiodicheader SET noti_status = 1\n" +
            "WHERE wh_id = :warehouseId and ref_doc_no = :refDocNo and is_deleted=0", nativeQuery = true)
    void updateNotificationStatus(@Param("refDocNo") String refDocNo,
                                  @Param("warehouseId") String warehouseId);

    @Query(value = "select notification_text notificationText, USER_ROLE userRole from tblnotification where\n" +
            "c_id = :companyId and lang_id = :languageId and notification_id = :notificationId and is_deleted = 0", nativeQuery = true)
    IKeyValuePair getNotificationId(@Param("companyId") String companyId,
                                    @Param("languageId") String languageId,
                                    @Param("notificationId") String notificationId);

    @Query(value = "select user_id userId from tblusermanagement where user_role_id = :roleId\n" +
            "and c_id = :companyId and lang_id = :languageId and is_deleted = 0", nativeQuery = true)
    List<String> getUserId(@Param("companyId") String companyId,
                           @Param("languageId") String languageId,
                           @Param("roleId") String roleId);

//===========================================================JainCord-V4================================================================================

    PeriodicLineV2 findByCompanyCodeAndPlantIdAndLanguageIdAndWarehouseIdAndCycleCountNoAndStorageBinAndItemCodeAndManufacturerNameAndPackBarcodesAndDeletionIndicator(
            String companyCode, String plantId, String languageId, String warehouseId, String cycleCountNo,
            String storageBin, String itemCode, String manufacturerName, String packBarcodes, Long deletionIndicator);

    PeriodicLineV2 findByCompanyCodeAndPlantIdAndLanguageIdAndWarehouseIdAndCycleCountNoAndStorageBinAndItemCodeAndManufacturerNameAndPackBarcodesAndBarcodeIdAndDeletionIndicator(
            String companyCode, String plantId, String languageId, String warehouseId, String cycleCountNo, String storageBin,
            String itemCode, String manufacturerName, String packBarcodes, String barcodeId, Long deletionIndicator);

}
