package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.model.auth.AuthToken;
import com.tekclover.wms.api.mfg.model.dto.KeyValueImpl;
import com.tekclover.wms.api.mfg.repository.MasterOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseService {

    @Autowired
    MasterOperationRepository masterOperationRepository;

    @Autowired
    protected IDMasterService idmasterService;

    @Autowired
    protected AuthTokenService authTokenService;

    protected String statusDescription = null;
    protected String numberRangeId = null;
    protected KeyValueImpl description = null;
    protected Long NUMBER_RANGE_CODE = 0L;
    protected static final String RM_ITEM_TYPE_ID = "2";         //Raw material
    protected static final String SFG_ITEM_TYPE_ID = "5";         //Semi finished goods
    protected static final Long IB_ORD_TYP_ID_FG = 7L;             //Indus Inventory transfer - supplier invoice
    protected static final Long IB_ORD_TYP_ID_SFG = 8L;             //Indus Inventory transfer - supplier invoice
    protected static final String OB_IPL_ORD_TYP_ID_SFG = "5";         //Indus pickList - raw material to finished goods production order
    protected static final String OB_IPL_ORD_TYP_ID_FG = "6";         //Indus pickList - raw material to semi-finished goods production order

    protected static final String P_ORD_TYP_FG = "FG";         //Indus Finished goods order type
    protected static final String P_ORD_TYP_SFG = "SFG";         //Indus semi Finished goods order type

    protected static final String ST_SEC_ID_PSFG = "7";         //Indus semi Finished goods storage section id
    protected static final String ST_SEC_ID_PFG = "8";         //Indus Finished goods storage section id

    protected static final String SORTING = "sorting";
    protected static final String SOAKING = "soaking";
    protected static final String PASTE = "paste";
    protected static final String POWDER = "powder";
    protected static final String DICESLICECHOP = "diceslicechop";
    protected static final String COOKING = "cooking";
    protected static final String PEELING = "peeling";

    //--------------------------------------------------------------------------------------------------------

    /**
     * @param NUM_RAN_CODE
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @return
     */
    protected String getNextRangeNumber(Long NUM_RAN_CODE, String companyCodeId, String plantId,
                                        String languageId, String warehouseId) {
        AuthToken authTokenForIDMasterService = authTokenService.getIDMasterServiceAuthToken();
        String nextNumberRange = idmasterService.getNextNumberRange(NUM_RAN_CODE, warehouseId, companyCodeId, plantId, languageId, authTokenForIDMasterService.getAccess_token());
        return nextNumberRange;
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @return
     */
    protected KeyValueImpl getDescription(String companyCodeId, String plantId, String languageId, String warehouseId) {
        KeyValueImpl description = masterOperationRepository.getDescription(companyCodeId, plantId, languageId, warehouseId);
        return description;
    }

    /**
     *
     * @param statusId
     * @param languageId
     * @return
     */
    protected String getStatusDescription(Long statusId, String languageId) {
        statusDescription = masterOperationRepository.getStatusDescription(statusId, languageId);
        return statusDescription;
    }

    /**
     *
     * @param value
     * @return
     */
    protected static double round(Double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}