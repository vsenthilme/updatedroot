package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.dto.BomLine;
import com.tekclover.wms.api.mfg.repository.BomLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BomLineService {

    @Autowired
    private BomLineRepository bomLineRepository;

    /**
     * @param warehouseId
     * @param bomNumber
     * @return
     */
    public List<BomLine> getBomLine(String companyCode, String plantId, String languageId, String warehouseId, Long bomNumber) {
        List<BomLine> bomLines = bomLineRepository.findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndBomNumber(
                languageId,
                companyCode,
                plantId,
                warehouseId,
                bomNumber);
        return bomLines;
    }
    /**
     *
     * @param companyCode
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param childItemCode
     * @param bomNumber
     * @return
     */
    public BomLine getBomLine(String companyCode, String plantId, String languageId, String warehouseId, String childItemCode, Long bomNumber) {
        Optional<BomLine> bomLine = bomLineRepository.findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndBomNumberAndChildItemCodeAndDeletionIndicator(
                languageId,
                companyCode,
                plantId,
                warehouseId,
                bomNumber,
                childItemCode,
                0L);
        if(!bomLine.isEmpty()) {
            return bomLine.get();
        } else {
            throw new BadRequestException("BomLine Not Present: " + bomNumber );
        }
    }
}