package com.courier.overc360.api.midmile.primary.model.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "tblasrpricelist",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_asrpricelist",
                        columnNames = {"LANG_ID", "C_ID", "PARTNER_ID"})
        }
)
@IdClass(AsrPriceListCompositeKey.class)
public class AsrPriceList {

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
    private Double roundOff;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusDescription;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

}
