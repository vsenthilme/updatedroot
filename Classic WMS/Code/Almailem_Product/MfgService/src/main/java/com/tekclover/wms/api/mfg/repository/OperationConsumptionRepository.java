package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.prodcutionorder.OperationConsumptionImpl;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
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
public interface OperationConsumptionRepository extends JpaRepository<OperationConsumption, Long>,
        JpaSpecificationExecutor<OperationConsumption>, StreamableJpaSpecificationRepository<OperationConsumption> {

    Optional<OperationConsumption> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long productionOrderLineNo, String itemCode, Long deletionIndicator);

    Optional<OperationConsumption> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndOperationNumberAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long productionOrderLineNo,
            String itemCode, String operationNumber, String phaseNumber, String bomItem, String batchNumber, Long deletionIndicator);

    List<OperationConsumption> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long deletionIndicator);

    List<OperationConsumption> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndItemTypeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, String itemType, Long deletionIndicator);

    List<OperationConsumption> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndItemTypeAndBatchNumberAndParentProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, String itemType, String batchNumber, String parentProductionOrderNo, Long deletionIndicator);

    @Query(value = "select max(inv_id) inventoryId into #inv from tblinventory \n" +
            "WHERE is_deleted = 0 and \n" +
            "C_ID = :companyCodeId AND PLANT_ID = :plantId AND LANG_ID = :languageId AND WH_ID = :warehouseId AND ITM_CODE = :itemCode AND BIN_CL_ID = :binClassId \n" +
            "group by itm_code,mfr_name,pack_barcode,st_bin,plant_id,wh_id,c_id,lang_id \n" +

            "SELECT SUM(INV_QTY) FROM tblinventory WHERE inv_id in (select inventoryId from #inv) and \n"
            + "C_ID = :companyCodeId AND PLANT_ID = :plantId AND LANG_ID = :languageId AND WH_ID = :warehouseId AND \n"
            + "ITM_CODE = :itemCode AND BIN_CL_ID = :binClassId AND IS_DELETED = 0 AND ST_SEC_ID IN (2,3)", nativeQuery = true)
    Double findSFGInventory(@Param("companyCodeId") String companyCodeId,
                            @Param("plantId") String plantId,
                            @Param("languageId") String languageId,
                            @Param("warehouseId") String warehouseId,
                            @Param("itemCode") String itemCode,
                            @Param("binClassId") Long binClassId);

        List<OperationConsumption> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                String languageId, String companyCodeId, String plantId, String warehouseId,
                String productionOrderNo, String batchNumber, Long deletionIndicator);

    @Query(value = "SELECT \n" +
            "C_TEXT companyDescription, \n" +
            "PLANT_TEXT plantDescription, \n" +
            "WH_TEXT warehouseDescription, \n" +
            "BOM_ITM itemCode, \n" +
            "REF_FIELD_1 itemDescription, \n" +
            "RECEIPE_QTY receipeQuantity, \n" +
            "ISSUED_QTY issuedQuantity, \n" +
            "CONSUMED_QTY consumedQuantity, \n" +
            "LOSS_QTY loss, \n" +
            "YLD_QTY yield, \n" +
            "STATUS_TEXT statusDescription \n" +
            "FROM tbloperationconsumption oc \n" +
            "WHERE \n" +
            "(COALESCE(:itemCode, null) IS NULL OR oc.ITM_CODE IN (:itemCode)) AND \n" +
            "(COALESCE(:batchNumber, null) IS NULL OR oc.BATCH_NO IN (:batchNumber)) AND \n" +
            "(COALESCE(:productionOrderNo, null) IS NULL OR oc.PROD_ORD_NO IN (:productionOrderNo))",
            nativeQuery = true)
    List<OperationConsumptionImpl> findOperationConsumption(
            @Param("itemCode") String itemCode,
            @Param("batchNumber") String batchNumber,
            @Param("productionOrderNo") String productionOrderNo);

    @Query(value = "SELECT " +
            "ord_line_no productionOrderLineNo,\n" +
            "bom_itm itemCode,\n" +
            "ref_field_1 itemDescription,\n" +
            "sum(receipe_qty) receipeQuantity,\n" +
            "uom uom,\n" +
            "prod_ord_no productionOrderNo,\n" +
            "batch_no batchNumber,\n" +
            "prod_ord_type productionOrderType\n" +
            "from tbloperationconsumption \n" +
            "WHERE \n" +
            "(COALESCE(:productionOrderNo, null) IS NULL OR PROD_ORD_NO IN (:productionOrderNo)) and\n" +
            "(COALESCE(:batchNumber, null) IS NULL OR BATCH_NO IN (:batchNumber)) and is_deleted = 0 \n" +
            "group by prod_ord_no,prod_ord_type,uom,batch_no,ord_line_no,bom_itm,ref_field_1 ",
            nativeQuery = true)
    List<OperationConsumptionImpl> findOperationConsumption(
            @Param("productionOrderNo") String productionOrderNo,
            @Param("batchNumber") String batchNumber);

    @Query(value = "SELECT " +
            "C_ID companyCodeId,\n" +
            "PLANT_ID plantId,\n" +
            "ord_line_no productionOrderLineNo,\n" +
            "bom_itm itemCode,\n" +
            "ref_field_1 itemDescription,\n" +
            "sum(receipe_qty) receipeQuantity,\n" +
            "sum(ISSUED_QTY) issuedQuantity,\n" +
            "BOM_QTY bomQuantity,\n" +
            "uom uom,\n" +
            "OPERATION_ID operationNumber,\n" +
            "prod_ord_no productionOrderNo,\n" +
            "PAR_PROD_ORD_NO parentProductionOrderNo,\n" +
            "batch_no batchNumber,\n" +
            "prod_ord_type productionOrderType\n" +
            "from tbloperationconsumption \n" +
            "WHERE \n" +
            "(COALESCE(:productionOrderNo, null) IS NULL OR PROD_ORD_NO IN (:productionOrderNo)) and is_deleted = 0 \n" +
            "group by prod_ord_no,operation_id,par_prod_ord_no,prod_ord_type,uom,batch_no,ord_line_no,bom_itm,ref_field_1,c_id,plant_id,bom_qty ",
            nativeQuery = true)
    List<OperationConsumptionImpl> findOperationConsumptionIB(@Param("productionOrderNo") String productionOrderNo);
}