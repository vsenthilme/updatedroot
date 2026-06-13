package com.courier.overc360.api.idmaster.replica.model.asrpriceList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "tblasrpricelist",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_asrpricelist",
                        columnNames = {"LANG_ID", "C_ID", "PARTNER_ID","LINE_NO"})
        }
)
@IdClass(ReplicaAsrPriceListCompositeKey.class)
public class ReplicaAsrPriceList {
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
        @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
        private String partnerId;

        @Id
        @Column(name = "LINE_NO", columnDefinition = "nvarchar(50)")
        private Long lineNo;

        @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
        private String partnerType;

        @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
        private String partnerName;

        @Column(name = "SUB_PARTNER_ID", columnDefinition = "nvarchar(50)")
        private String subPartnerId;

        @Column(name = "SUB_PARTNER_NAME", columnDefinition = "nvarchar(50)")
        private String subPartnerName;

        @Column(name = "RATE_PARAMETER_ID", columnDefinition = "nvarchar(50)")
        private String rateParameterId;

        @Column(name = "CEILING_VALUE")
        private Double ceilingValue = 0.0;

        @Column(name = "RANGE_FROM")
        private Double rangeFrom = 0.0;

        @Column(name = "RANGE_TO")
        private Double rangeTo = 0.0;

        @Column(name = "RATE")
        private Double rate = 0.0;

        @Column(name = "RATE_PARAMETER_UNIT", columnDefinition = "nvarchar(50)")
        private String rateParameterUnit;

        @Column(name = "RATE_UNIT", columnDefinition = "nvarchar(50)")
        private String rateUnit;

        @Column(name = "ROUND_OFF")
        private Double roundOff = 0.0;

        @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
        private String statusId;

        @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
        private String statusDescription;

        @Column(name = "REMARK", columnDefinition = "nvarchar(2000)")
        private String remark;

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
