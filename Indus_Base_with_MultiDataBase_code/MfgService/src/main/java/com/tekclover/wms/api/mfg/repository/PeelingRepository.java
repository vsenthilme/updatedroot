package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.prodcutionorder.ProcessImpl;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PeelingRepository extends JpaRepository<Peeling, Long>,
        JpaSpecificationExecutor<Peeling>, StreamableJpaSpecificationRepository<Peeling> {

    Optional<Peeling> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long productionOrderLineNo, String receipeId,
            String operationNumber, String itemCode, Long deletionIndicator);

    Optional<Peeling> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long productionOrderLineNo, String receipeId,
            String operationNumber, String itemCode, String phaseNumber,
            String bomItem, String batchNumber, Long deletionIndicator);

    List<Peeling> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long deletionIndicator);

    Peeling findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber, Long deletionIndicator);

    @Query(value = "SELECT " +
            "ITM_CODE itemCode, \n" +
            "chl_item_code bomItem, \n" +
            "PROD_ORD_NO productionOrderNo, \n" +
            "BATCH_NO batchNumber, \n" +
            "phase_id phaseNumber, \n" +
            "operation_id operationNumber, \n" +
            "phase_detail phaseDescription, \n" +
            "peel_start_time startTime, \n" +
            "peel_end_time endTime, \n" +
            "worker_no numberOfWorker, \n" +
            "supervisor_nm supervisorName, \n" +
            "pst_boil_wgt outputQuantity, \n" +
            "STATUS_TEXT statusDescription, \n" +
            "CTD_BY updatedBy, \n" +
            "CTD_ON updatedOn \n" +
            "FROM tblpeeling tp \n" +
            "WHERE \n" +
            "(COALESCE(:itemCode, null) IS NULL OR tp.ITM_CODE IN (:itemCode)) AND \n" +
            "(COALESCE(:batchNumber, null) IS NULL OR tp.BATCH_NO IN (:batchNumber)) AND \n" +
            "(COALESCE(:productionOrderNo, null) IS NULL OR tp.PROD_ORD_NO IN (:productionOrderNo))",
            nativeQuery = true)
    List<ProcessImpl> findPeeling(
            @Param("itemCode") String itemCode,
            @Param("batchNumber") String batchNumber,
            @Param("productionOrderNo") String productionOrderNo);

    List<Peeling> findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId,
            String productionOrderNo, String batchNumber, Long deletionIndicator);

    @Query(value = "SELECT * \n" +
            "FROM tblpeeling \n" +
            "WHERE \n" +
            "(COALESCE(:languageId, null) IS NULL OR LANG_ID IN (:languageId)) AND " +
            "(COALESCE(:companyCodeId, null) IS NULL OR C_ID IN (:companyCodeId)) AND " +
            "(COALESCE(:plantId, null) IS NULL OR PLANT_ID IN (:plantId)) AND " +
            "(COALESCE(:warehouseId, null) IS NULL OR WH_ID IN (:warehouseId)) AND " +
            "(COALESCE(:itemCode, null) IS NULL OR ITM_CODE IN (:itemCode)) AND " +
            "(COALESCE(:operationNumber, null) IS NULL OR OPERATION_ID IN (:operationNumber)) AND " +
            "(COALESCE(:phaseNumber, null) IS NULL OR PHASE_ID IN (:phaseNumber)) AND " +
            "(COALESCE(:receipeId, null) IS NULL OR RECEIPE_ID IN (:receipeId)) AND " +
            "(COALESCE(:supervisorName, null) IS NULL OR SUPERVISOR_NM IN (:supervisorName)) AND " +
            "(COALESCE(:bomItem, null) IS NULL OR CHL_ITEM_CODE IN (:bomItem)) AND " +
            "(COALESCE(:productionOrderNo, null) IS NULL OR PROD_ORD_NO IN (:productionOrderNo)) AND " +
            "(COALESCE(:batchNumber, null) IS NULL OR BATCH_NO IN (:batchNumber)) AND " +
            "(COALESCE(CONVERT(VARCHAR(255), :startCreatedOn), null) IS NULL OR CTD_ON BETWEEN COALESCE(CONVERT(VARCHAR(255), :startCreatedOn), null) AND COALESCE(CONVERT(VARCHAR(255), :endCreatedOn), null)) AND " +
            "(COALESCE(:statusId, null) IS NULL OR STATUS_ID IN (:statusId)) and is_deleted = 0",
            nativeQuery = true)
    List<Peeling> findPeeling(
            @Param("companyCodeId") List<String> companyCodeId,
            @Param("plantId") List<String> plantId,
            @Param("languageId") List<String> languageId,
            @Param("warehouseId") List<String> warehouseId,
            @Param("itemCode") List<String> itemCode,
            @Param("batchNumber") List<String> batchNumber,
            @Param("bomItem") List<String> bomItem,
            @Param("phaseNumber") List<String> phaseNumber,
            @Param("operationNumber") List<String> operationNumber,
            @Param("receipeId") List<String> receipeId,
            @Param("productionOrderNo") List<String> productionOrderNo,
            @Param("statusId") List<Long> statusId,
            @Param("supervisorName") List<String> supervisorName,
            @Param("startCreatedOn") Date startCreatedOn,
            @Param("endCreatedOn") Date endCreatedOn);
}