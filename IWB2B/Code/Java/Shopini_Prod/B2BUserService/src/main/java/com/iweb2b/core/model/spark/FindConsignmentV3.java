package com.iweb2b.core.model.spark;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindConsignmentV3 {

    private List<Long> consignmentId;
    private List<String> referenceNumber;
    private List<String> customerCode;
    private List<String> serviceTypeId;
    private List<String> consignmentType;
    private List<String> customerReferenceNumber;
    private List<String> customerCivilId;
    private List<String> receiverCivilId;
    private List<String> awb3rdParty;
    private List<String> scanType3rdParty;
    private List<String> hubCode3rdParty;
    private List<String> orderType;
    private List<String> jntPushStatus;
    private List<String> boutiqaatPushStatus;
    private Date startDate;
    private Date EndDate;
}
