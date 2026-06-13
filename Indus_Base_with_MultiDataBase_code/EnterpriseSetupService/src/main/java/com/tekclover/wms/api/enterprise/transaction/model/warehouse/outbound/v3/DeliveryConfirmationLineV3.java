package com.tekclover.wms.api.enterprise.transaction.model.warehouse.outbound.v3;

import lombok.Data;

@Data
public class DeliveryConfirmationLineV3 {
    private String outbound;                              
    private String customerCode;                                      
    private String customer;                           
    private String skuCode;  
    private String material;  
    private String priceSegement;  
    private String articleNumber;  
    private String gender;  
    private String color;  
    private String size;  
    private String noOfPairs;  
    private String huSerialNo;  
    private String pickedQty;  
}