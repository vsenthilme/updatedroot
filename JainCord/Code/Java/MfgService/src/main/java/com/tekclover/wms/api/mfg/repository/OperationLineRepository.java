package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.operation.OperationLine;
import com.tekclover.wms.api.mfg.model.prodcutionorder.OperationLineImpl;
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
public interface OperationLineRepository extends JpaRepository<OperationLine, Long>,
        JpaSpecificationExecutor<OperationLine>, StreamableJpaSpecificationRepository<OperationLine> {


    Optional<OperationLine> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long productionOrderLineNo, String itemCode, Long deletionIndicator);

    List<OperationLine> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long deletionIndicator);

    OperationLine findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, String batchNumber, Long deletionIndicator);

    @Query(value = "SELECT " +
            "LANG_ID languageId, " +
            "C_ID companyCodeId, " +
            "C_TEXT companyDescription, " +
            "PLANT_ID plantId, " +
            "PLANT_TEXT plantDescription, " +
            "WH_ID warehouseId, " +
            "WH_TEXT warehouseDescription, " +
            "ITM_CODE itemCode, " +
            "ITM_TEXT itemDescription, " +
            "UOM uom, " +
            "PROD_ORD_NO productionOrderNo, " +
            "BATCH_NO batchNumber, " +
            "ORD_QTY orderQuantity, " +
            "EXP_PRD_QTY expectedQuantity, " +
            "ACT_PRD_QTY actualQuantity, " +
            "YIELD_PER yieldPercentage, " +
            "LOSS_QTY lossQuantity, " +
            "CTD_ON createdOn, " +
            "CNF_ON orderConfirmedOn, " +
            "CNF_BY orderConfirmedBy, " +
            "STATUS_TEXT statusDescription " +
            "FROM tbloperationline tol " +
            "WHERE " +
            "(COALESCE(:languageId, null) IS NULL OR tol.LANG_ID IN (:languageId)) AND " +
            "(COALESCE(:companyCodeId, null) IS NULL OR tol.C_ID IN (:companyCodeId)) AND " +
            "(COALESCE(:plantId, null) IS NULL OR tol.PLANT_ID IN (:plantId)) AND " +
            "(COALESCE(:warehouseId, null) IS NULL OR tol.WH_ID IN (:warehouseId)) AND " +
            "(COALESCE(:itemCode, null) IS NULL OR tol.ITM_CODE IN (:itemCode)) AND " +
            "(COALESCE(:productionOrderNo, null) IS NULL OR tol.PROD_ORD_NO IN (:productionOrderNo)) AND " +
            "(COALESCE(:batchNumber, null) IS NULL OR tol.BATCH_NO IN (:batchNumber)) AND " +
            "(COALESCE(CONVERT(VARCHAR(255), :startCreatedOn), null) IS NULL OR tol.CTD_ON BETWEEN COALESCE(CONVERT(VARCHAR(255), :startCreatedOn), null) AND COALESCE(CONVERT(VARCHAR(255), :endCreatedOn), null)) AND " +
            "(COALESCE(:statusId, null) IS NULL OR tol.STATUS_ID IN (:statusId))",
            nativeQuery = true)
    List<OperationLineImpl> findOperationLine(
            @Param("languageId") List<String> languageId,
            @Param("companyCodeId") List<String> companyCodeId,
            @Param("plantId") List<String> plantId,
            @Param("warehouseId") List<String> warehouseId,
            @Param("itemCode") List<String> itemCode,
            @Param("productionOrderNo") List<String> productionOrderNo,
            @Param("batchNumber") List<String> batchNumber,
            @Param("startCreatedOn") Date startCreatedOn,
            @Param("endCreatedOn") Date endCreatedOn,
            @Param("statusId") List<String> statusId);
}