package com.tekclover.wms.api.transaction.controller;

import com.tekclover.wms.api.transaction.config.dBConfig.DataSourceContextHolder;
import com.tekclover.wms.api.transaction.config.dBConfig.DataSourceEnum;

public class BaseController {

    private final DataSourceContextHolder dataSourceContextHolder;

    public BaseController() {dataSourceContextHolder = new DataSourceContextHolder();}

    public void setDataSourceContextHolder (String companyCode, String plantId, String languageId, String warehouseId) {
        if (companyCode.equalsIgnoreCase("1001") &&
                plantId.equalsIgnoreCase("222") &&
                languageId.equalsIgnoreCase("EN") &&
                warehouseId.equalsIgnoreCase("200")) {
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
        } else if (companyCode.equalsIgnoreCase("2000") &&
                plantId.equalsIgnoreCase("2001") &&
                languageId.equalsIgnoreCase("EN") &&
                warehouseId.equalsIgnoreCase("200")) {
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_ONE);
        }
    }
}