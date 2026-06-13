package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.operation.OperationHeader;
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
public interface OperationHeaderRepository extends JpaRepository<OperationHeader, Long>,
        JpaSpecificationExecutor<OperationHeader>, StreamableJpaSpecificationRepository<OperationHeader> {


    Optional<OperationHeader> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long deletionIndicator);

    @Query(value = "SELECT " +
            "top 1 phase_id \n" +
            "from tbloperationmaster \n" +
            "WHERE \n" +
            "OPERATION_ID IN (:operationNumber) and C_ID = :companyCodeId and PLANT_ID = :plantId and LANG_ID = :languageId and WH_ID = :warehouseId and is_deleted = 0 \n" +
            "order by operation_id,phase_id desc", nativeQuery = true)
    String getPhaseId(@Param("companyCodeId") String companyCodeId,
                      @Param("plantId") String plantId,
                      @Param("languageId") String languageId,
                      @Param("warehouseId") String warehouseId,
                      @Param("operationNumber") String operationNumber);

    List<OperationHeader> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndParentProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long deletionIndicator);
}