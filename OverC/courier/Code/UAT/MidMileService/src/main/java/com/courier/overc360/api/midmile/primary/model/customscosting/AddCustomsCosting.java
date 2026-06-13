package com.courier.overc360.api.midmile.primary.model.customscosting;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddCustomsCosting {

    @NotBlank(message = "Partner Id is mandatory")
    private String partnerId;

    @NotBlank(message = "Cost Center is mandatory")
    private String costCenter;

    @NotNull(message = "Line Number is mandatory")
    private Long lineNumber;

    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    private String partnerName;

    private String department;

    private Date date;

    private String cashHolder;

    private Long noOfShipments;

    private String invoiceNumber;

    @NotBlank(message = "Status Id is mandatory")
    private String statusId;

    private Date invoiceDate;

    private String supplierName;

    private String costDescription;

    private Double costAmount;

    private String remark;

    private Long cashNumber;

    private Boolean finance;

    private String subCustomerId;

    private String subCustomerName;

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
