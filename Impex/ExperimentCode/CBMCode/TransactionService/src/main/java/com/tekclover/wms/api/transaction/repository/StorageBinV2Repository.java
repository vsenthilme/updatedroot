package com.tekclover.wms.api.transaction.repository;

import com.tekclover.wms.api.transaction.model.dto.StorageBinV2;
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
import java.util.Optional;

@Repository
@Transactional
public interface StorageBinV2Repository extends JpaRepository<StorageBinV2, Long>,
        JpaSpecificationExecutor<StorageBinV2>, StreamableJpaSpecificationRepository<StorageBinV2> {

    StorageBinV2 findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndStorageBinAndBinClassIdAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String storageBin, Long binClassId, Long deletionIndicator);

    StorageBinV2 findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndStorageBinAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String storageBin, Long deletionIndicator);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and st_bin not in (:storageBin) and \n" +
            "status_id = :statusId and \n" +
            "CAP_CHECK = 'FALSE' and is_deleted = 0 and st_bin <> 'REC-AL-B2' order by st_bin", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinNonCBM(@Param("binclassId") Long binclassId,
                                            @Param("companyCode") String companyCode,
                                            @Param("plantId") String plantId,
                                            @Param("languageId") String languageId,
                                            @Param("statusId") Long statusId,
                                            @Param("storageBin") List<String> storageBin,
                                            @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and \n" +
//            "putaway_block = 0 and pick_block = 0 and status_id = :statusId and \n" +
            "status_id = :statusId and \n" +
            "CAP_CHECK = 'FALSE' and is_deleted = 0 and st_bin <> 'REC-AL-B2' order by st_bin", nativeQuery = true)                     //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinNonCBM(@Param("binclassId") Long binclassId,
                                            @Param("companyCode") String companyCode,
                                            @Param("plantId") String plantId,
                                            @Param("languageId") String languageId,
                                            @Param("statusId") Long statusId,
                                            @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and st_bin not in (:storageBin) and ST_SEC_ID not in (:storageSectionId) and \n" +
            "status_id = :statusId and \n" +
            "CAP_CHECK = 'FALSE' and is_deleted = 0 and st_bin <> 'REC-AL-B2' order by st_bin", nativeQuery = true)    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinNonCBM(@Param("binclassId") Long binclassId,
                                            @Param("storageSectionId") List<String> storageSectionId,
                                            @Param("companyCode") String companyCode,
                                            @Param("plantId") String plantId,
                                            @Param("languageId") String languageId,
                                            @Param("statusId") Long statusId,
                                            @Param("storageBin") List<String> storageBin,
                                            @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and ST_SEC_ID not in (:storageSectionIds) and \n" +
            "ST_SEC_ID = :storageSectionId and \n" +
            "CAP_CHECK = 'FALSE' and is_deleted = 0 and st_bin <> 'REC-AL-B2' order by st_bin", nativeQuery = true)     //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinNonCBM(@Param("binclassId") Long binclassId,
                                            @Param("storageSectionIds") List<String> storageSectionIds,
                                            @Param("companyCode") String companyCode,
                                            @Param("plantId") String plantId,
                                            @Param("languageId") String languageId,
                                            @Param("storageSectionId") String storageSectionId,
                                            @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and \n" +
            " is_deleted = 0 and st_bin <> 'REC-AL-B2' order by st_bin", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinNonCBMBinClassId(@Param("binclassId") Long binclassId,
                                                      @Param("companyCode") String companyCode,
                                                      @Param("plantId") String plantId,
                                                      @Param("languageId") String languageId,
                                                      @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and st_bin in (:storageBin) and \n" +
            "status_id = :statusId and \n" +
            "CAP_CHECK = 'FALSE' and is_deleted = 0 and st_bin <> 'REC-AL-B2' order by UTD_ON desc", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinNonCBMLastPick(@Param("binclassId") Long binclassId,
                                                    @Param("companyCode") String companyCode,
                                                    @Param("plantId") String plantId,
                                                    @Param("languageId") String languageId,
                                                    @Param("statusId") Long statusId,
                                                    @Param("storageBin") List<String> storageBin,
                                                    @Param("warehouseId") String warehouseId);


    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and CAP_CHECK = 'TRUE' and st_bin in (:storageBin) and\n" +
            "status_id = :statusId and \n" +
            "(case when ISNUMERIC(remain_vol)=1 then CAST(remain_vol AS NUMERIC) else 0 end) > :cbm and \n" +
            "is_deleted = 0 and st_bin <> 'REC-AL-B2' order by remain_vol", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinLastPickCBM(@Param("binclassId") Long binclassId,
                                                 @Param("companyCode") String companyCode,
                                                 @Param("plantId") String plantId,
                                                 @Param("languageId") String languageId,
                                                 @Param("cbm") Double cbm,
                                                 @Param("statusId") Long statusId,
                                                 @Param("storageBin") List<String> storageBin,
                                                 @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and CAP_CHECK = 'TRUE' and st_bin in (:storageBin) and\n" +
            "status_id = :statusId and \n" +
            "(case when ISNUMERIC(remain_vol)=1 then CAST(remain_vol AS NUMERIC) else 0 end) > :cbmPerQty and \n" +
            "is_deleted = 0 and st_bin <> 'REC-AL-B2' order by remain_vol", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinCbmPerQtyLastPick(@Param("binclassId") Long binclassId,
                                                       @Param("companyCode") String companyCode,
                                                       @Param("plantId") String plantId,
                                                       @Param("languageId") String languageId,
                                                       @Param("cbmPerQty") Double cbmPerQty,
                                                       @Param("statusId") Long statusId,
                                                       @Param("storageBin") List<String> storageBin,
                                                       @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and st_bin in (:storageBin) and \n" +
            "CAP_CHECK = 'FALSE' and is_deleted = 0 and st_bin <> 'REC-AL-B2' order by st_bin", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getExistingStorageBinNonCBM(@Param("binclassId") Long binclassId,
                                                    @Param("companyCode") String companyCode,
                                                    @Param("plantId") String plantId,
                                                    @Param("languageId") String languageId,
                                                    @Param("storageBin") List<String> storageBin,
                                                    @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and st_bin in (:storageBin) and ST_SEC_ID not in (:storageSectionId) and \n" +
            "CAP_CHECK = 'FALSE' and is_deleted = 0 and st_bin <> 'REC-AL-B2' order by st_bin", nativeQuery = true)                 //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getExistingStorageBinNonCBM(@Param("binclassId") Long binclassId,
                                                    @Param("storageSectionId") List<String> storageSectionId,
                                                    @Param("companyCode") String companyCode,
                                                    @Param("plantId") String plantId,
                                                    @Param("languageId") String languageId,
                                                    @Param("storageBin") List<String> storageBin,
                                                    @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and CAP_CHECK = 'TRUE' and \n" +
            "status_id = :statusId and \n" +
            "(case when ISNUMERIC(remain_vol)=1 then CAST(remain_vol AS NUMERIC) else 0 end) > :cbm and \n" +
            "is_deleted = 0 and st_bin <> 'REC-AL-B2' order by remain_vol", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinCBM(@Param("binclassId") Long binclassId,
                                         @Param("companyCode") String companyCode,
                                         @Param("plantId") String plantId,
                                         @Param("languageId") String languageId,
                                         @Param("cbm") Double cbm,
                                         @Param("statusId") Long statusId,
                                         @Param("warehouseId") String warehouseId);

    @Query(value = "SELECT top 1 * FROM tblstoragebin WHERE bin_cl_id = :binclassId and c_id = :companyCode and plant_id = :plantId and \n" +
            "wh_id = :warehouseId and lang_id = :languageId and CAP_CHECK = 'TRUE' and \n" +
            "status_id = :statusId and \n" +
            "(case when ISNUMERIC(remain_vol)=1 then CAST(remain_vol AS NUMERIC) else 0 end) > :cbmPerQty and \n" +
            "is_deleted = 0 and st_bin <> 'REC-AL-B2' order by remain_vol", nativeQuery = true)
    //storage-bin excluding direct stock receipt bin
    public StorageBinV2 getStorageBinCbmPerQty(@Param("binclassId") Long binclassId,
                                               @Param("companyCode") String companyCode,
                                               @Param("plantId") String plantId,
                                               @Param("languageId") String languageId,
                                               @Param("cbmPerQty") Double cbmPerQty,
                                               @Param("statusId") Long statusId,
                                               @Param("warehouseId") String warehouseId);

    Optional<StorageBinV2> findTopByBinClassIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndLanguageIdAndDeletionIndicator(
            Long binClassId, String companyCodeId, String plantId, String warehouseId, String languageId, Long deletionIndicator);

    Optional<StorageBinV2> findByStorageBinAndCompanyCodeIdAndPlantIdAndWarehouseIdAndLanguageIdAndDeletionIndicator(
            String storageBin, String companyCodeId, String plantId, String warehouseId, String languageId, Long deletionIndicator);


    @Query(value = "select max(inv_id) inventoryId into #inv from tblinventory \n"
            + "WHERE is_deleted = 0 group by itm_code,barcode_id,mfr_name,pack_barcode,st_bin,plant_id,wh_id,c_id,lang_id \n" +

            "SELECT \n" +
            "iv.ST_BIN storageBin\n" +
            "into #inventoryTempTable from tblinventory iv \n" +                //copy to temp table to avoid deadlock
            "where \n" +
            "iv.inv_id in (select inventoryId from #inv) and \n" +
            "(COALESCE(:companyCodeId, null) IS NULL OR (iv.c_id IN (:companyCodeId))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (iv.lang_id IN (:languageId))) and \n" +
            "(COALESCE(:plantId, null) IS NULL OR (iv.plant_id IN (:plantId))) and \n" +
            "(COALESCE(:warehouseId, null) IS NULL OR (iv.wh_id IN (:warehouseId))) and \n" +
            "(COALESCE(:manufacturerName, null) IS NULL OR (iv.MFR_NAME IN (:manufacturerName))) and \n" +
            "(COALESCE(:itemCode, null) IS NULL OR (iv.ITM_CODE IN (:itemCode))) and \n" +
            "(COALESCE(:binClassId, null) IS NULL OR (iv.BIN_CL_ID IN (:binClassId))) and\n" +
            "iv.is_deleted = 0 and (iv.REF_FIELD_4 > 0) and st_bin <> 'REC-AL-B2' \n" +

            "SELECT * FROM tblstoragebin WHERE st_bin in (select * from #inventoryTempTable) and \n" +
            "(COALESCE(:companyCodeId, null) IS NULL OR (iv.c_id IN (:companyCodeId))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (iv.lang_id IN (:languageId))) and \n" +
            "(COALESCE(:plantId, null) IS NULL OR (iv.plant_id IN (:plantId))) and \n" +
            "(COALESCE(:warehouseId, null) IS NULL OR (iv.wh_id IN (:warehouseId))) and \n" +
            "(COALESCE(:binClassId, null) IS NULL OR (iv.BIN_CL_ID IN (:binClassId))) and\n" +
            "(case when ISNUMERIC(remain_vol)=1 then CAST(remain_vol AS NUMERIC) else 0 end) > :cbmPerQty and \n" +
            "is_deleted = 0 and CAP_CHECK = 'TRUE' order by remain_vol", nativeQuery = true)
    public List<StorageBinV2> storageBinWithCbmPerQty(@Param("companyCodeId") String companyCodeId,
                                                      @Param("plantId") String plantId,
                                                      @Param("languageId") String languageId,
                                                      @Param("warehouseId") String warehouseId,
                                                      @Param("itemCode") String itemCode,
                                                      @Param("manufacturerName") String manufacturerName,
                                                      @Param("binClassId") Long binClassId,
                                                      @Param("cbmPerQty") Double cbmPerQty);


    @Query(value = "select * from tblstoragebin sb \n" +
            "WHERE sb.is_deleted = 0 and sb.c_id = :companyCodeId and sb.plant_id = :plantId and sb.lang_id = :languageId and sb.wh_id = :warehouseId and \n" +
            "(case when ISNUMERIC(remain_vol)=1 then CAST(remain_vol AS NUMERIC) else 0 end) > :cbmPerQty and \n" +
            "sb.st_bin in (select pick_st_bin from tblpickupline ip where ip.itm_code in (:itemCode) and \n" +
            "ip.wh_id in (:warehouseId) and \n" +
            "ip.bin_cl_id in (:binClassId) and \n" +
            "ip.mfr_name in (:manufacturerName) and \n" +
            "ip.c_id in (:companyCodeId) and \n" +
            "ip.lang_id in (:languageId) and \n" +
            "ip.plant_id in (:plantId) and \n" +
            "ip.is_deleted = 0) order by remain_vol", nativeQuery = true)
    public List<StorageBinV2> lastPickedStorageBinWithCbmPerQty(@Param(value = "companyCodeId") String companyCodeId,
                                                                @Param(value = "plantId") String plantId,
                                                                @Param(value = "languageId") String languageId,
                                                                @Param(value = "warehouseId") String warehouseId,
                                                                @Param(value = "itemCode") String itemCode,
                                                                @Param(value = "manufacturerName") String manufacturerName,
                                                                @Param(value = "binClassId") Long binClassId,
                                                                @Param(value = "cbmPerQty") Double cbmPerQty);

    Optional<StorageBinV2> findTopByBinClassIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndLanguageIdAndStorageBinAndDeletionIndicator(
            Long binClassId, String companyCodeId, String plantId, String warehouseId,
            String languageId, String storageBin, Long deletionIndicator);

    StorageBinV2 findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndStorageBinAndBinClassIdAndStorageSectionIdNotInAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String bin, Long binClassId, List<String> storageSectionIds, Long deletionIndicator);

    StorageBinV2 findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndStorageBinAndStorageSectionIdNotInAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String bin, List<String> storageSectionIds, Long deletionIndicator);

    Optional<StorageBinV2> findTopByStorageSectionIdAndBinClassIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndLanguageIdAndStatusIdAndDeletionIndicator(
            String storageSectionId, Long binClassId, String companyCodeId, String plantId,
            String warehouseId, String languageId, Long statusId, Long deletionIndicator);

    Optional<StorageBinV2> findTopByStorageSectionIdAndBinClassIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndLanguageIdAndDeletionIndicator(
            String storageSectionId, Long binClassId, String companyCodeId, String plantId, String warehouseId, String languageId, Long deletionIndicator);

    //===========================================================Impex-V4===================================================================
    StorageBinV2 findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndBinClassIdAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, Long binClassId, Long deletionIndicator);

    boolean existsByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndStorageBinAndBinClassIdInAndDeletionIndicator(
            String companyCodeId, String plantId, String languageId, String warehouseId, String storageBin, List<Long> binClassIds, Long deletionIndicator);

    //===========================================================CBM=====================================================================
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("Update StorageBinV2 ob SET ob.statusId = :statusId, ob.remainingVolume = :remainingVolume, ob.occupiedVolume = :occupiedVolume, \r\n "
            + "ob.allocatedVolume = :allocatedVolume, ob.capacityCheck = :capacityCheck, ob.updatedBy = :loginUserId, ob.updatedOn = :updatedOn \r\n"
            + "WHERE ob.companyCodeId = :companyCodeId AND ob.plantId = :plantId AND ob.languageId = :languageId AND ob.warehouseId = :warehouseId AND ob.storageBin = :storageBin")
    public void updateStorageBin(@Param("companyCodeId") String companyCodeId,
                                 @Param("plantId") String plantId,
                                 @Param("languageId") String languageId,
                                 @Param("warehouseId") String warehouseId,
                                 @Param("storageBin") String storageBin,
                                 @Param("remainingVolume") String remainingVolume,
                                 @Param("occupiedVolume") String occupiedVolume,
                                 @Param("allocatedVolume") Double allocatedVolume,
                                 @Param("statusId") Long statusId,
                                 @Param("capacityCheck") boolean capacityCheck,
                                 @Param("loginUserId") String loginUserId,
                                 @Param("updatedOn") Date updatedOn);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("Update StorageBinV2 ob SET ob.statusId = :statusId, ob.capacityCheck = :capacityCheck, ob.updatedBy = :loginUserId, ob.updatedOn = :updatedOn \r\n"
            + "WHERE ob.companyCodeId = :companyCodeId AND ob.plantId = :plantId AND ob.languageId = :languageId AND ob.warehouseId = :warehouseId AND ob.storageBin = :storageBin")
    public void updateStorageBinStatus(@Param("companyCodeId") String companyCodeId,
                                       @Param("plantId") String plantId,
                                       @Param("languageId") String languageId,
                                       @Param("warehouseId") String warehouseId,
                                       @Param("storageBin") String storageBin,
                                       @Param("statusId") Long statusId,
                                       @Param("capacityCheck") boolean capacityCheck,
                                       @Param("loginUserId") String loginUserId,
                                       @Param("updatedOn") Date updatedOn);

    //Inventory CBM StorageBin order by Remaining Volume
    @Query(value = "select max(inv_id) inventoryId into #inv from tblinventory \n" +
            "WHERE \n" +
            "(COALESCE(:itemCode, null) IS NULL OR (ITM_CODE IN (:itemCode))) and \n" +
            "(COALESCE(:binClassId, null) IS NULL OR (BIN_CL_ID IN (:binClassId))) and\n" +
            "(COALESCE(:manufacturerName, null) IS NULL OR (MFR_NAME IN (:manufacturerName))) and \n" +
            "(COALESCE(:companyCodeId, null) IS NULL OR (c_id IN (:companyCodeId))) and \n" +
            "(COALESCE(:languageId, null) IS NULL OR (lang_id IN (:languageId))) and \n" +
            "(COALESCE(:plantId, null) IS NULL OR (plant_id IN (:plantId))) and \n" +
            "(COALESCE(:warehouseId, null) IS NULL OR (wh_id IN (:warehouseId))) and \n" +
            "is_deleted = 0 \n" +
            "group by itm_code,barcode_id,mfr_name,pack_barcode,alt_uom,bag_size,stck_typ_id,st_bin,plant_id,wh_id,c_id,lang_id \n" +

            "select top 1 * from tblstoragebin sb \n" +
            "WHERE sb.is_deleted = 0 and sb.c_id = :companyCode and sb.plant_id = :plantId and \n" +
            " sb.lang_id = :languageId and sb.wh_id = :warehouseId and sb.CAP_CHECK = true and \n" +
            "(case when ISNUMERIC(sb.remain_vol)=1 then CAST(sb.remain_vol AS NUMERIC) else 0 end) > :cbmPerQty and \n" +
            "sb.st_bin in (select st_bin from tblinventory ip where " +
            "ip.inv_id in (select inventoryId from #inv) and \n" +
            "ip.c_id in (:companyCode) and ip.plant_id in (:plantId) and ip.lang_id in (:languageId) and ip.wh_id in (:warehouseId) and \n" +
            "ip.itm_code in (:itemCode) and ip.mfr_name in (:manufacturerName) and \n" +
            "ip.bin_cl_id in (:binClassId) and ip.stck_typ_id = 1 and \n" +
            "ip.is_deleted = 0) order by sb.remain_vol", nativeQuery = true)
    public StorageBinV2 getInventoryBinOrderByRemainingVolume(@Param(value = "companyCode") String companyCode,
                                                              @Param(value = "plantId") String plantId,
                                                              @Param(value = "languageId") String languageId,
                                                              @Param(value = "warehouseId") String warehouseId,
                                                              @Param(value = "itemCode") String itemCode,
                                                              @Param(value = "manufacturerName") String manufacturerName,
                                                              @Param(value = "binClassId") Long binClassId,
                                                              @Param(value = "cbmPerQty") Double cbmPerQty);

    //Last Picked CBM StorageBin
    @Query(value = "select top 1 * from tblstoragebin sb \n" +
            "WHERE sb.is_deleted = 0 and sb.c_id = :companyCode and sb.plant_id = :plantId and sb.lang_id = :languageId and \n" +
            "sb.wh_id = :warehouseId and sb.CAP_CHECK = true and sb.status_id = :statusId and sb.bin_cl_id in (:binClassId) and \n" +
            "(case when ISNUMERIC(sb.remain_vol)=1 then CAST(sb.remain_vol AS NUMERIC) else 0 end) > :cbm and \n" +
            "sb.st_bin in (select top 1 PICK_ST_BIN from tblpickupline ip where " +
            "ip.c_id in (:companyCode) and ip.plant_id in (:plantId) and ip.lang_id in (:languageId) and ip.wh_id in (:warehouseId) and \n" +
            "ip.itm_code in (:itemCode) and ip.mfr_name in (:manufacturerName) and \n" +
            "ip.is_deleted = 0 order by PICK_CNF_ON DESC)", nativeQuery = true)
    public StorageBinV2 getLastPickedBin(@Param(value = "companyCode") String companyCode,
                                         @Param(value = "plantId") String plantId,
                                         @Param(value = "languageId") String languageId,
                                         @Param(value = "warehouseId") String warehouseId,
                                         @Param(value = "itemCode") String itemCode,
                                         @Param(value = "manufacturerName") String manufacturerName,
                                         @Param(value = "binClassId") Long binClassId,
                                         @Param(value = "statusId") Long statusId,
                                         @Param(value = "cbm") Double cbm);

    //Confirmed CBM StorageBin available in putAwayLine
    @Query(value = "select top 1 * from tblstoragebin sb \n" +
            "WHERE sb.is_deleted = 0 and sb.c_id = :companyCode and sb.plant_id = :plantId and sb.lang_id = :languageId and \n" +
            "sb.wh_id = :warehouseId and sb.CAP_CHECK = true and sb.status_id = :statusId and sb.bin_cl_id in (:binClassId) and \n" +
            "(case when ISNUMERIC(sb.remain_vol)=1 then CAST(sb.remain_vol AS NUMERIC) else 0 end) > :cbm and \n" +
            "sb.st_bin in (select top 1 CNF_ST_BIN from tblputawayline ip where " +
            "ip.c_id in (:companyCode) and ip.plant_id in (:plantId) and ip.lang_id in (:languageId) and ip.wh_id in (:warehouseId) and \n" +
            "ip.itm_code in (:itemCode) and ip.mfr_name in (:manufacturerName) and \n" +
            "ip.is_deleted = 0 and ip.status_id = 20 order by PA_CTD_ON)", nativeQuery = true)
    public StorageBinV2 getConfirmedPutAwayLineBin(@Param(value = "companyCode") String companyCode,
                                                   @Param(value = "plantId") String plantId,
                                                   @Param(value = "languageId") String languageId,
                                                   @Param(value = "warehouseId") String warehouseId,
                                                   @Param(value = "itemCode") String itemCode,
                                                   @Param(value = "manufacturerName") String manufacturerName,
                                                   @Param(value = "binClassId") Long binClassId,
                                                   @Param(value = "statusId") Long statusId,
                                                   @Param(value = "cbm") Double cbm);

    //already Proposed statusId 19 CBM StorageBin available in putAwayHeader
    @Query(value = "select top 1 * from tblstoragebin sb \n" +
            "WHERE sb.is_deleted = 0 and sb.c_id = :companyCode and sb.plant_id = :plantId and sb.lang_id = :languageId and \n" +
            "sb.wh_id = :warehouseId and sb.CAP_CHECK = true and sb.status_id = :statusId and sb.bin_cl_id in (:binClassId) and \n" +
            "(case when ISNUMERIC(sb.remain_vol)=1 then CAST(sb.remain_vol AS NUMERIC) else 0 end) > :cbm and \n" +
            "sb.st_bin in (select top 1 PROP_ST_BIN from tblputawayheader ip where " +
            "ip.c_id in (:companyCode) and ip.plant_id in (:plantId) and ip.lang_id in (:languageId) and ip.wh_id in (:warehouseId) and \n" +
            "ip.REF_FIELD_5 in (:itemCode) and ip.mfr_name in (:manufacturerName) and \n" +
            "ip.is_deleted = 0 and ip.status_id = 19 order by PA_CTD_ON)", nativeQuery = true)
    public StorageBinV2 getProposedPutAwayHeaderBin(@Param(value = "companyCode") String companyCode,
                                                   @Param(value = "plantId") String plantId,
                                                   @Param(value = "languageId") String languageId,
                                                   @Param(value = "warehouseId") String warehouseId,
                                                   @Param(value = "itemCode") String itemCode,
                                                   @Param(value = "manufacturerName") String manufacturerName,
                                                   @Param(value = "binClassId") Long binClassId,
                                                   @Param(value = "statusId") Long statusId,
                                                   @Param(value = "cbm") Double cbm);

    //CBM StorageBin Empty not available in putAwayHeader
    @Query(value = "select top 1 * from tblstoragebin sb \n" +
            "WHERE sb.is_deleted = 0 and sb.c_id = :companyCode and sb.plant_id = :plantId and sb.lang_id = :languageId and \n" +
            "sb.wh_id = :warehouseId and sb.CAP_CHECK = true and sb.status_id = :statusId and sb.bin_cl_id in (:binClassId) and \n" +
            "(case when ISNUMERIC(sb.remain_vol)=1 then CAST(sb.remain_vol AS NUMERIC) else 0 end) > :cbm and \n" +
            "sb.st_bin not in (select PROP_ST_BIN from tblputawayheader ip where " +
            "ip.c_id in (:companyCode) and ip.plant_id in (:plantId) and ip.lang_id in (:languageId) and ip.wh_id in (:warehouseId) and \n" +
            "ip.is_deleted = 0 and ip.status_id = 19 group by PROP_ST_BIN)", nativeQuery = true)
    public StorageBinV2 getProposedEmptyBin(@Param(value = "companyCode") String companyCode,
                                            @Param(value = "plantId") String plantId,
                                            @Param(value = "languageId") String languageId,
                                            @Param(value = "warehouseId") String warehouseId,
                                            @Param(value = "binClassId") Long binClassId,
                                            @Param(value = "statusId") Long statusId,
                                            @Param(value = "cbm") Double cbm);
}