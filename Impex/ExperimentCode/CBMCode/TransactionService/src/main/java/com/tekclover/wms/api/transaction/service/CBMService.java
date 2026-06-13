package com.tekclover.wms.api.transaction.service;

import com.tekclover.wms.api.transaction.controller.exception.BadRequestException;
import com.tekclover.wms.api.transaction.model.dto.ImBasicData1V2;
import com.tekclover.wms.api.transaction.model.dto.StorageBinV2;
import com.tekclover.wms.api.transaction.repository.ImBasicData1V2Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CBMService extends BaseService {

    @Autowired
    ImBasicData1V2Repository imBasicData1V2Repository;

    @Autowired
    StorageBinService storageBinService;

    /**
     * @param length
     * @param width
     * @param height
     * @return
     */
    public double calculateItemCBM(Double length, Double width, Double height) {
        length = length != null ? length : 0;
        width = width != null ? width : 0;
        height = height != null ? height : 0;
        return length * width * height;             //CBM[Volume]
    }

    /**
     * String to double
     * @param volume
     * @return
     */
    public double getVolume(String volume) {
        return volume != null ? Double.parseDouble(volume) : 0;
    }

    /**
     * Double Value Null check
     * @param volume
     * @return
     */
    public double getVolume(Double volume) {
        return volume != null ? volume : 0;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param itemCode
     * @param manufacturerName
     * @return
     */
    public ImBasicData1V2 getImBasicData1(String companyCodeId, String plantId, String languageId,
                                          String warehouseId, String itemCode, String manufacturerName) {
        return imBasicData1V2Repository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndManufacturerPartNoAndDeletionIndicator(
                languageId, companyCodeId, plantId, warehouseId, itemCode, manufacturerName, 0L);
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param itemCode
     * @param manufacturerName
     * @return
     */
    public Double getImBasicData1CBM(String companyCodeId, String plantId, String languageId,
                                     String warehouseId, String itemCode, String manufacturerName) {
        ImBasicData1V2 imBasicData1 = imBasicData1V2Repository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndManufacturerPartNoAndDeletionIndicator(
                languageId, companyCodeId, plantId, warehouseId, itemCode, manufacturerName, 0L);
        return itemCBM(imBasicData1);
    }

    /**
     * @param imBasicData1
     * @return
     */
    public double itemCBM(ImBasicData1V2 imBasicData1) {
        return calculateItemCBM(imBasicData1.getLength(), imBasicData1.getWidth(), imBasicData1.getHeight());
    }

    /**
     * @param imBasicData1
     * @param storageBin
     * @return
     */
    public boolean capacityCheck(ImBasicData1V2 imBasicData1, StorageBinV2 storageBin) {
        boolean itemCapacityCheck = imBasicData1 != null ? imBasicData1.getCapacityCheck() != null ? imBasicData1.getCapacityCheck() : false : false;
        boolean binCapacityCheck = storageBin != null ? storageBin.isCapacityCheck() : false;
        return itemCapacityCheck && binCapacityCheck;
    }

    /**
     * @param imBasicData1
     * @return
     */
    public boolean itemCapacityCheck(ImBasicData1V2 imBasicData1) {
        return imBasicData1 != null ? imBasicData1.getCapacityCheck() != null ? imBasicData1.getCapacityCheck() : false : false;
    }

    /**
     * @param storageBin
     * @return
     */
    public boolean binCapacityCheck(StorageBinV2 storageBin) {
        return storageBin != null ? storageBin.isCapacityCheck() : false;
    }

    /**
     * Decreasing Occupied Volume
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param itemCode
     * @param manufacturerName
     * @param storageBin
     * @param orderQty
     */
    public Double[] storageBinCapacityExpansion(String companyCodeId, String plantId, String languageId, String warehouseId,
                                              String itemCode, String manufacturerName, String storageBin, Double orderQty, String loginUserId) {
        ImBasicData1V2 imBasicData1 = getImBasicData1(companyCodeId, plantId, languageId, warehouseId, itemCode, manufacturerName);
        StorageBinV2 dbStorageBin = storageBinService.getStorageBinV2(companyCodeId, plantId, languageId, warehouseId, storageBin);
        boolean capacityCheck = capacityCheck(imBasicData1, dbStorageBin);
        if (capacityCheck) {
            log.info("CBM Initiated----> " + capacityCheck);
            double itemCBM = calculateItemCBM(imBasicData1.getLength(), imBasicData1.getWidth(), imBasicData1.getHeight());
            double remainingVolume = getVolume(dbStorageBin.getRemainingVolume());
            double occupiedVolume = getVolume(dbStorageBin.getOccupiedVolume());
            double totalVolume = getVolume(dbStorageBin.getTotalVolume());

            validateBinVolume(remainingVolume, occupiedVolume, totalVolume);

            double allocatedVolume = itemCBM * orderQty;
            occupiedVolume = occupiedVolume - allocatedVolume;
            remainingVolume = totalVolume - occupiedVolume;
            updateStorageBin(companyCodeId, plantId, languageId, warehouseId, remainingVolume, occupiedVolume, allocatedVolume, totalVolume, dbStorageBin.getStorageBin(), loginUserId);
            return new Double[]{itemCBM, allocatedVolume};
        }
        return null;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param dbStorageBin
     * @param imBasicData1
     * @param orderQty
     * @param loginUserId
     */
    public void storageBinCapacityExpansion(String companyCodeId, String plantId, String languageId, String warehouseId,
                                            StorageBinV2 dbStorageBin, ImBasicData1V2 imBasicData1, Double orderQty, String loginUserId) {

        double itemCBM = calculateItemCBM(imBasicData1.getLength(), imBasicData1.getWidth(), imBasicData1.getHeight());
        double remainingVolume = getVolume(dbStorageBin.getRemainingVolume());
        double occupiedVolume = getVolume(dbStorageBin.getOccupiedVolume());
        double totalVolume = getVolume(dbStorageBin.getTotalVolume());
        validateBinVolume(remainingVolume, occupiedVolume, totalVolume);

        double allocatedVolume = itemCBM * orderQty;
        occupiedVolume = occupiedVolume - allocatedVolume;
        remainingVolume = totalVolume - occupiedVolume;
        updateStorageBin(companyCodeId, plantId, languageId, warehouseId, remainingVolume, occupiedVolume, allocatedVolume, totalVolume, dbStorageBin.getStorageBin(), loginUserId);
    }

    /**
     * Increasing Occupied Volume
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param itemCode
     * @param manufacturerName
     * @param storageBin
     * @param orderQty
     */
    public Double[] storageBinCapacityReduction(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                String itemCode, String manufacturerName, String storageBin, Double orderQty, String loginUserId) {
        ImBasicData1V2 imBasicData1 = getImBasicData1(companyCodeId, plantId, languageId, warehouseId, itemCode, manufacturerName);
        StorageBinV2 dbStorageBin = storageBinService.getStorageBinV2(companyCodeId, plantId, languageId, warehouseId, storageBin);
        boolean capacityCheck = capacityCheck(imBasicData1, dbStorageBin);
        if (capacityCheck) {
            double itemCBM = calculateItemCBM(imBasicData1.getLength(), imBasicData1.getWidth(), imBasicData1.getHeight());
            double remainingVolume = getVolume(dbStorageBin.getRemainingVolume());
            double occupiedVolume = getVolume(dbStorageBin.getOccupiedVolume());
            double totalVolume = getVolume(dbStorageBin.getTotalVolume());

            double allocatedVolume = itemCBM * orderQty;
            if (remainingVolume < itemCBM || remainingVolume < allocatedVolume) {
                throw new BadRequestException("Selected Bin doesn't have required space to store the selected quantity. Kindly Select a different Bin!");
            }

            occupiedVolume = occupiedVolume + allocatedVolume;
            remainingVolume = totalVolume - occupiedVolume;
            updateStorageBin(companyCodeId, plantId, languageId, warehouseId, remainingVolume, occupiedVolume, allocatedVolume, totalVolume, dbStorageBin.getStorageBin(), loginUserId);
            return new Double[]{itemCBM, allocatedVolume};
        }
        return null;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param dbStorageBin
     * @param imBasicData1
     * @param orderQty
     * @param loginUserId
     */
    public Double[] storageBinCapacityReduction(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                StorageBinV2 dbStorageBin, ImBasicData1V2 imBasicData1, Double orderQty, String loginUserId) {

        // Validate input
        if (dbStorageBin == null || imBasicData1 == null || orderQty == null || orderQty < 0) {
            throw new IllegalArgumentException("Invalid input: dbStorageBin cannot be null, and cbm must be non-negative.");
        }

        double itemCBM = calculateItemCBM(imBasicData1.getLength(), imBasicData1.getWidth(), imBasicData1.getHeight());
        double remainingVolume = getVolume(dbStorageBin.getRemainingVolume());
        double occupiedVolume = getVolume(dbStorageBin.getOccupiedVolume());
        double totalVolume = getVolume(dbStorageBin.getTotalVolume());

        validateBinVolume(remainingVolume, occupiedVolume, totalVolume);
        double allocatedVolume = itemCBM * orderQty;

        if (remainingVolume < allocatedVolume) {
            throw new BadRequestException("Selected Bin doesn't have required space to store the selected quantity. Kindly Select a different Bin!");
        }

        occupiedVolume = occupiedVolume + allocatedVolume;
        remainingVolume = totalVolume - occupiedVolume;
        updateStorageBin(companyCodeId, plantId, languageId, warehouseId, remainingVolume, occupiedVolume, allocatedVolume, totalVolume, dbStorageBin.getStorageBin(), loginUserId);
        return new Double[]{itemCBM, allocatedVolume};
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param dbStorageBin
     * @param itemCBM
     * @param cbm
     * @param loginUserId
     */
    public void storageBinCapacityReduction(String companyCodeId, String plantId, String languageId, String warehouseId,
                                            StorageBinV2 dbStorageBin, Double itemCBM, Double cbm, String loginUserId) {

        // Validate input
        if (dbStorageBin == null || cbm == null || cbm < 0 || itemCBM == null || itemCBM < 0) {
            throw new IllegalArgumentException("Invalid input: dbStorageBin cannot be null, and cbm must be non-negative.");
        }

        double remainingVolume = getVolume(dbStorageBin.getRemainingVolume());
        double occupiedVolume = getVolume(dbStorageBin.getOccupiedVolume());
        double totalVolume = getVolume(dbStorageBin.getTotalVolume());

        double allocatedVolume = cbm;
        if (remainingVolume < itemCBM || remainingVolume < allocatedVolume) {
            throw new BadRequestException("Selected Bin doesn't have required space to store the selected quantity. Kindly Select a different Bin!");
        }

        occupiedVolume = occupiedVolume + allocatedVolume;
        remainingVolume = totalVolume - occupiedVolume;
        updateStorageBin(companyCodeId, plantId, languageId, warehouseId, remainingVolume, occupiedVolume, allocatedVolume, totalVolume, dbStorageBin.getStorageBin(), loginUserId);
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param dbStorageBin
     * @param cbm
     * @param loginUserId
     * @return
     */
    public Double[] storageBinCapacityReduction(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                StorageBinV2 dbStorageBin, Double cbm, Double orderCBM, Double itemCBM, String loginUserId) {

        // Validate input
        if (dbStorageBin == null || cbm == null || cbm < 0) {
            throw new IllegalArgumentException("Invalid input: dbStorageBin cannot be null, and cbm must be non-negative.");
        }

        double remainingVolume = getVolume(dbStorageBin.getRemainingVolume());
        double occupiedVolume = getVolume(dbStorageBin.getOccupiedVolume());
        double totalVolume = getVolume(dbStorageBin.getTotalVolume());

        validateBinVolume(remainingVolume, occupiedVolume, totalVolume);
        double allocatedVolume = Math.min(cbm, remainingVolume);

        occupiedVolume = occupiedVolume + allocatedVolume;
        remainingVolume = totalVolume - occupiedVolume;
        cbm = cbm - allocatedVolume;

        double putAwayQty = calculatePutAwayQuantity(allocatedVolume, orderCBM, itemCBM);
        updateStorageBin(companyCodeId, plantId, languageId, warehouseId, remainingVolume, occupiedVolume, allocatedVolume, totalVolume, dbStorageBin.getStorageBin(), loginUserId);
        return new Double[]{putAwayQty, cbm};
    }

    /**
     * @param companyCode
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param remainingVolume
     * @param occupiedVolume
     * @param allocatedVolume
     * @param storageBin
     * @param loginUserId
     */
    public void updateStorageBin(String companyCode, String plantId, String languageId, String warehouseId,
                                 Double remainingVolume, Double occupiedVolume, Double allocatedVolume, Double totalVolume,
                                 String storageBin, String loginUserId) {
        Long statusId = remainingVolume.equals(totalVolume) ? 0L : 1L;
        storageBinService.updateStorageBinV2(companyCode, plantId, languageId, warehouseId, storageBin,
                                             String.valueOf(remainingVolume), String.valueOf(occupiedVolume), allocatedVolume,
                                             statusId, true, loginUserId);
    }

    /**
     * @param companyCode
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param dbStorageBin
     * @param storageBin
     * @param loginUserId
     */
    public void updateStorageBinStatus(String companyCode, String plantId, String languageId, String warehouseId,
                                       StorageBinV2 dbStorageBin, String storageBin, String loginUserId) {
        double remainingVolume = getVolume(dbStorageBin.getRemainingVolume());
        double totalVolume = getVolume(dbStorageBin.getTotalVolume());

        Long statusId = remainingVolume == totalVolume ? 0L : 1L;
        storageBinService.updateStorageBinStatusV2(companyCode, plantId, languageId, warehouseId, storageBin,
                                                   statusId, true, loginUserId);
    }

    /**
     * @param allocatedVolume
     * @param orderCBM
     * @param orderQty
     * @return
     */
    public double calculatePutAwayQuantity(Double allocatedVolume, Double orderCBM, Double orderQty) {
        orderCBM = orderCBM != null ? orderCBM : 0D;
        orderQty = orderQty != null ? orderQty : 0D;
        return (allocatedVolume / orderCBM) * orderQty;
    }

    /**
     * @param remainingVolume
     * @param occupiedVolume
     * @param totalVolume
     */
    public void validateBinVolume(Double remainingVolume, Double occupiedVolume, Double totalVolume) {
        if (totalVolume != (remainingVolume + occupiedVolume)) {
            throw new BadRequestException("Inaccurate Bin Volume TOT_VOL!=REM_VOL+OCC_VOL : " + totalVolume + "|" + remainingVolume + "|" + occupiedVolume);
        }
    }
}