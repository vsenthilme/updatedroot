package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.masterphase.MasterPhase;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface MasterPhaseRepository extends JpaRepository<MasterPhase, Long>,
        JpaSpecificationExecutor<MasterPhase>, StreamableJpaSpecificationRepository<MasterPhase> {


    Optional<MasterPhase> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndPhaseNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String phaseNumber, Long deletionIndicator);
}