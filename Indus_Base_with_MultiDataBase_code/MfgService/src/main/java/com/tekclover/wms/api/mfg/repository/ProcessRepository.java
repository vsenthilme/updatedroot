package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.process.Process;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProcessRepository extends JpaRepository<Process, Long>,
        JpaSpecificationExecutor<Process>, StreamableJpaSpecificationRepository<Process> {

    Optional<Process> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo,
            Long productionOrderLineNo, String itemCode, String phaseNumber, String bomItem, String batchNumber, Long deletionIndicator);

    List<Process> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long deletionIndicator);

    List<Process> findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber, Long deletionIndicator);
}