package com.mnrclara.api.crm.model.InquiryId;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindInquiryId {

    private List<Long> id;
    private List<Long> inquiryId;
    private List<String> fileName;
    private List<Long> statusId;
    private List<String> uploadedBy;
    private Date startDate;
    private Date endDate;

    private String sStartDate;
    private String sEndDate;
}