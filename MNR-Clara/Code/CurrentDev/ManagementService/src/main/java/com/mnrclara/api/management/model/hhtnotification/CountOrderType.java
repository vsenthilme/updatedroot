package com.mnrclara.api.management.model.hhtnotification;


import lombok.Data;

@Data
public class CountOrderType {

    private Long matterCount;
    private Long initialRetainerCount;
    private Long paymentPlantCount;
    private Long invoiceCount;
    private Long checkListCount;
    private Long documentUploadCount;
    private Long receiptNoCount;
    private Long overAllCount;

}
