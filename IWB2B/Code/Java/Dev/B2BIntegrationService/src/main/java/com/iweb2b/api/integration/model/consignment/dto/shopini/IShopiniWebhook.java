package com.iweb2b.api.integration.model.consignment.dto.shopini;

public interface IShopiniWebhook {
    String getTrackingNo();
    String getReferenceNumber();
    String getShipmentStatus();
    String getActionDate();
    String getItemActionName();
    Long getLmdStatus();
}