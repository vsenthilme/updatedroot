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
        name = "tblrtopricelist",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_rtopricelist",
                        columnNames = {"LANG_ID", "C_ID", "PARTNER_ID"})
        }
)
@IdClass(RtoPriceCompositeKey.class)
public class RtoPriceList {

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

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

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

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

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

}
