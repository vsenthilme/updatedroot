package com.courier.overc360.api.midmile.primary.model.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "tblrate",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_rate",
                        columnNames = {"LANG_ID", "C_ID", "PARTNER_ID", "RATE_PARAMETER_ID"}
                )
        }
)
@IdClass(RateCompositeKey.class)
public class Rate {

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

    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Id
    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(100)")
    private String partnerName;

    @Id
    @Column(name = "RATE_PARAMETER_ID", columnDefinition = "nvarchar(50)")
    private String rateParameterId;

    @Column(name = "CEILING_VALUE")
    private Double ceilingValue = 0.0;

    @Column(name = "RATE_PARAMETER_TEXT", columnDefinition = "nvarchar(100)")
    private String rateParameterDescription;

    @Column(name = "RATE_PARAMETER_UNIT", columnDefinition = "nvarchar(50)")
    private String rateParameterUnit;

    @Column(name = "RANGE_FROM")
    private Double rangeFrom = 0.0;

    @Column(name = "RANGE_TO")
    private Double rangeTo = 0.0;

    @Column(name = "RATE")
    private Double rate = 0.0;

    @Column(name = "RATE_UNIT", columnDefinition = "nvarchar(50)")
    private String rateUnit;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(100)")
    private String statusDescription;

    @Column(name = "REMARK", columnDefinition = "nvarchar(2000)")
    private String remark;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

}
