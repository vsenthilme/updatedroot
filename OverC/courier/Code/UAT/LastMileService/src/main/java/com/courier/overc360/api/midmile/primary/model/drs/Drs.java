package com.courier.overc360.api.midmile.primary.model.drs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbldrs",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_drs",
                        columnNames = {"LANG_ID", "C_ID", "CUSTOMER_ID", "HOUSE_AIRWAY_BILL"}
                )
        }
)
@IdClass(DrsCompositeKey.class)
public class Drs {

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Id
    @Column(name = "CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String customerId;

    @Column(name = "CUSTOMER_NAME", columnDefinition = "nvarchar(100)")
    private String customerName;

    @Id
    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Id
    @Column(name = "PIECE_ID", columnDefinition = "nvarchar(50)")
    private String pieceId;

    @Column(name = "MANIFEST_NUMBER", columnDefinition = "nvarchar(50)")
    private String manifestNumber;

    @Column(name = "PARTNER_MAWB", columnDefinition = "nvarchar(50)")
    private String partnerMasterAirwayBill;

    @Column(name = "PARTNER_HAWB", columnDefinition = "nvarchar(50)")
    private String partnerHouseAirwayBill;

    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "PICKUP_ID", columnDefinition = "nvarchar(50)")
    private String masterAirwayBill;

    @Column(name = "CONSIGNMENT_BAG_ID", columnDefinition = "nvarchar(50)")
    private String consignmentBagId;

    @Column(name = "DELIVERY_ID", columnDefinition = "nvarchar(50)")
    private String deliveryId;

    @Column(name = "COURIER_ID", columnDefinition = "nvarchar(50)")
    private String courierId;

    @Column(name = "CONSIGNEE_DETAILS", columnDefinition = "nvarchar(2000)")
    private String consigneeDetails;

    @Column(name = "CONSIGNOR_DETAILS", columnDefinition = "nvarchar(2000)")
    private String consignorDetails;

    @Column(name = "DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "PIECE_COUNT", columnDefinition = "nvarchar(50)")
    private String pieceCount;

    @Column(name = "AMOUNT", columnDefinition = "nvarchar(50)")
    private String amount;

    @Column(name = "SIGN", columnDefinition = "nvarchar(50)")
    private String sign;

    @Column(name = "REMARK", columnDefinition = "nvarchar(50)")
    private String remark;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(200)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(200)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(200)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(200)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(200)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(200)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(200)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(200)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(200)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(200)")
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
