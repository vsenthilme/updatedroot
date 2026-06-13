package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.masterreceipe.MasterReceipe;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MasterReceipeRepository extends JpaRepository<MasterReceipe, Long>,
        JpaSpecificationExecutor<MasterReceipe>, StreamableJpaSpecificationRepository<MasterReceipe> {

    List<MasterReceipe> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String itemCode, Long deletionIndicator);

    List<MasterReceipe> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndReceipeIdAndOperationNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String receipeId, String operationNumber, Long deletionIndicator);

    Optional<MasterReceipe> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndItemCodeAndBomNumberAndPhaseNumberAndChildItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String operationNumber,
            String receipeId, String itemCode, String bomNumber, String phaseNumber, String childItemCode, Long deletionIndicator);

    Optional<MasterReceipe> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndItemCodeAndBomNumberAndPhaseNumberAndChildItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String operationNumber,
            String itemCode, String bomNumber, String phaseNumber, String childItemCode, Long deletionIndicator);

    List<MasterReceipe> findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String itemCode, Long deletionIndicator);
}