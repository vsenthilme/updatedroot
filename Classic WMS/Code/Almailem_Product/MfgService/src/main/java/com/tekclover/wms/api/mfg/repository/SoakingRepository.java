package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.prodcutionorder.ProcessImpl;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
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
public interface SoakingRepository extends JpaRepository<Soaking, Long>,
        JpaSpecificationExecutor<Soaking>, StreamableJpaSpecificationRepository<Soaking> {

    Optional<Soaking> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long productionOrderLineNo, String receipeId,
            String operationNumber, String itemCode, Long deletionIndicator);

    Optional<Soaking> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long productionOrderLineNo, String receipeId,
            String operationNumber, String itemCode, String phaseNumber,
            String bomItem, String batchNumber, Long deletionIndicator);

    List<Soaking> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long deletionIndicator);

    Soaking findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber, Long deletionIndicator);

    @Query(value = "SELECT " +
            "ITM_CODE itemCode, \n" +
            "chl_item_code bomItem, \n" +
            "PROD_ORD_NO productionOrderNo, \n" +
            "BATCH_NO batchNumber, \n" +
            "phase_id phaseNumber, \n" +
            "operation_id operationNumber, \n" +
            "phase_detail phaseDescription, \n" +
            "start_time startTime, \n" +
            "end_time endTime, \n" +
            "worker_no numberOfWorker, \n" +
            "supervisor_nm supervisorName, \n" +
            "output_qty outputQuantity, \n" +
            "wtr_qty waterQuantity, \n" +
            "STATUS_TEXT statusDescription, \n" +
            "CTD_BY updatedBy, \n" +
            "CTD_ON updatedOn \n" +
            "FROM tblsoaking ts \n" +
            "WHERE \n" +
            "(COALESCE(:itemCode, null) IS NULL OR ts.ITM_CODE IN (:itemCode)) AND \n" +
            "(COALESCE(:batchNumber, null) IS NULL OR ts.BATCH_NO IN (:batchNumber)) AND \n" +
            "(COALESCE(:productionOrderNo, null) IS NULL OR ts.PROD_ORD_NO IN (:productionOrderNo))",
            nativeQuery = true)
    List<ProcessImpl> findSoaking(
            @Param("itemCode") String itemCode,
            @Param("batchNumber") String batchNumber,
            @Param("productionOrderNo") String productionOrderNo
    );

    List<Soaking> findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId,
            String productionOrderNo, String batchNumber, Long deletionIndicator);

    @Query(value = "SELECT * \n" +
            "FROM tblsoaking \n" +
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
    List<Soaking> findSoaking(
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