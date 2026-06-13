package com.mnrclara.wrapper.core.model.management;


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
