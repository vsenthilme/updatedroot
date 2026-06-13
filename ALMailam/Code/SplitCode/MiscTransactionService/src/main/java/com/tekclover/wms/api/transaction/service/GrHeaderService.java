package com.tekclover.wms.api.transaction.service;

import com.tekclover.wms.api.transaction.model.inbound.gr.GrHeader;
import com.tekclover.wms.api.transaction.repository.GrHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GrHeaderService extends BaseService {
    @Autowired
    private GrHeaderRepository grHeaderRepository;

    /**
     * @param companyCode
     * @param plantId
     * @param languageId
     * @return
     */
    public List<GrHeader> getGrHeader(String companyCode, String plantId, String languageId, String warehouseId, List<Long> status) {
        return grHeaderRepository.findByCompanyCodeAndLanguageIdAndPlantIdAndWarehouseIdAndStatusIdInAndDeletionIndicator(
                companyCode, languageId, plantId, warehouseId, status, 0L);
    }
}