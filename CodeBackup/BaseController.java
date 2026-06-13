package com.tekclover.wms.api.transaction.controller;

import com.tekclover.wms.api.transaction.config.DataSourceContextHolder;
import com.tekclover.wms.api.transaction.config.DataSourceEnum;

public class BaseController {

    private final DataSourceContextHolder dataSourceContextHolder;

    public BaseController() {dataSourceContextHolder = new DataSourceContextHolder();}

    public void setDataSourceContextHolder (String companyCode) {
        if (companyCode.equalsIgnoreCase("1001")) {
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_TWO);
        } else {
            dataSourceContextHolder.setBranchContext(DataSourceEnum.DATASOURCE_ONE);
        }
    }
}