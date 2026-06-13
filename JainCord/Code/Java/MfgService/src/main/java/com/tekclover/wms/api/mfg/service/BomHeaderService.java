package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.dto.AddBomHeader;
import com.tekclover.wms.api.mfg.model.dto.BomHeader;
import com.tekclover.wms.api.mfg.model.dto.BomLine;
import com.tekclover.wms.api.mfg.repository.BomHeaderRepository;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BomHeaderService extends BaseService {

    @Autowired
    private BomHeaderRepository bomHeaderRepository;

    @Autowired
    private BomLineService bomLineService;

    /**
     * getBomHeader
     *
     * @param parentItemCode
     * @return
     */
    public AddBomHeader getBomHeader(String companyCode, String plantId, String languageId, String warehouseId, String parentItemCode, String bomNumber) {
        Optional<BomHeader> optBomHeader =
                bomHeaderRepository.findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndParentItemCodeAndBomNumberAndDeletionIndicator(
                        languageId,
                        companyCode,
                        plantId,
                        warehouseId,
                        parentItemCode,
                        bomNumber,
                        0L);
        if (optBomHeader.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    "companyCode: " + companyCode +
					"plantId:" + plantId +
					"languageId:" + languageId +
					"parentItemCode: " + parentItemCode +
                    "bomNumber:" + bomNumber +
                    " doesn't exist.");
        }
        BomHeader bomHeader = optBomHeader.get();
        List<BomLine> bomLines = bomLineService.getBomLine(companyCode, plantId, languageId, warehouseId, bomHeader.getBomNumber());
        AddBomHeader addBomHeader = new AddBomHeader();
        BeanUtils.copyProperties(bomHeader, addBomHeader, CommonUtils.getNullPropertyNames(bomHeader));
        addBomHeader.setBomLines(bomLines);
        return addBomHeader;
    }
}