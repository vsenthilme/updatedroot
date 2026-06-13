package com.tekclover.wms.api.masters.transaction.model.impl;

public interface OutBoundLineImpl {
    String getRefDocNo();
    Double getLinesOrdered();
    Double getLinesShipped();
    Double getOrderedQty();
    Double getShippedQty();
}