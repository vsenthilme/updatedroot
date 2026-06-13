package com.mnrclara.wrapper.core.model.crm;

import lombok.Data;

@Data
public class AddInquiryId {

    private Long id;

    private Long inquiryId;

    private String fileName;

    private Long statusId;

    private Long deletionIndicator = 0L;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;
}
