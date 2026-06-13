package com.tekclover.wms.api.transaction.model.impl;

import java.util.Date;

public interface StockMovementReportImpl {
    String getWarehouseId();
    String getManufacturerSKU();
    String getItemCode();
    String getItemText();
    String getDocumentType();
    String getDocumentNumber();
    Double getMovementQty();
    String getCustomerCode();
    Date getConfirmedOn();
    Double getBalanceOHQty();
    Double getOpeningStock();
<<<<<<< HEAD
    String getCreatedOn();
    String getCreatedTime();
}
=======
}
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
