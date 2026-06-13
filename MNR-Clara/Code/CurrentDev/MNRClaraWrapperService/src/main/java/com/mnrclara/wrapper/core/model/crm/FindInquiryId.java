package com.mnrclara.wrapper.core.model.crm;

import lombok.Data;

import java.util.List;

@Data
public class FindInquiryId {

    private List<Long> id;
    private List<Long> inquiryId;
    private List<String> fileName;
    private List<Long> statusId;
    private List<String> uploadedBy;

    private String sStartDate;
    private String sEndDate;
}