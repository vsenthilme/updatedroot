package com.courier.overc360.api.midmile.primary.model.npr;

import com.courier.overc360.api.midmile.primary.model.delivery.DeliveryCompositeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tblnpr")
public class Npr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NPR_NO")
    private Long nprNo;

    @NotBlank(message = "LanguageId is mandatory")
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)", nullable = false)
    private String languageId;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

    @NotBlank(message = "CompanyId is mandatory")
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)", nullable = false)
    private String companyId;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "CONSIGNMENT_BAG_ID", columnDefinition = "nvarchar(50)")
    private String consignmentBagId;

    @NotBlank(message = "PickupId is mandatory")
    @Column(name = "PICKUP_ID",columnDefinition = "nvarchar(50)",nullable = false)
    private String pickupId;

    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Column(name = "CONSIGNMENT_ID", columnDefinition = "nvarchar(50)")
    private String consignmentId;

    @Column(name = "NPR_ID", columnDefinition = "nvarchar(50)")
    private String nprId;

    @Column(name = "NPR_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String nprDescription;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(500)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(500)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(500)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(500)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(500)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(500)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(500)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(500)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(500)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(500)")
    private String referenceField10;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();



}
