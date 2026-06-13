package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.dto.KeyValueImpl;
import com.tekclover.wms.api.mfg.model.masteroperation.MasterOperation;
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
public interface MasterOperationRepository extends JpaRepository<MasterOperation, Long>,
        JpaSpecificationExecutor<MasterOperation>, StreamableJpaSpecificationRepository<MasterOperation> {

    Optional<MasterOperation> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndPhaseNumberAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String operationNumber, String phaseNumber, Long deletionIndicator);

    //Description
    @Query(value = "select tc.c_text companyDescription,\n" +
            "tp.plant_text plantDescription,\n" +
            "tw.wh_text warehouseDescription from \n" +
            "tblcompanyid tc\n" +
            "join tblplantid tp on tp.c_id = tc.c_id and tp.lang_id = tc.lang_id\n" +
            "join tblwarehouseid tw on tw.c_id = tc.c_id and tw.lang_id=tc.lang_id and tw.plant_id = tp.plant_id\n" +
            "where\n" +
            "tc.c_id IN (:companyCodeId) and \n" +
            "tc.lang_id IN (:languageId) and \n" +
            "tp.plant_id IN(:plantId) and \n" +
            "tw.wh_id IN (:warehouseId) and \n" +
            "tc.is_deleted=0 and \n" +
            "tp.is_deleted=0 and \n" +
            "tw.is_deleted=0", nativeQuery = true)
    KeyValueImpl getDescription(@Param(value = "companyCodeId") String companyCodeId,
                                @Param(value = "plantId") String plantId,
                                @Param(value = "languageId") String languageId,
                                @Param(value = "warehouseId") String warehouseId);

    //Status Description
    @Query(value = "select status_text \n" +
            "from tblstatusid \n" +
            "where \n" +
            "status_id IN (:statusId) and \n" +
            "lang_id IN (:languageId) and \n" +
            "is_deleted=0", nativeQuery = true)
    String getStatusDescription(@Param(value = "statusId") Long statusId,
                                @Param(value = "languageId") String languageId);

    List<MasterOperation> findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndOperationNumberAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, Long deletionIndicator);
}