package com.courier.overc360.api.common.model.consignment.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table("tblitemdetailsv2")
public class ItemDetailsV2 {

    @Id
    @PrimaryKey
    private Long itemDetailsId;
    private Long pieceDetailsId;
    private Long consignmentId;

    private String languageId;
    private String companyId;
    private String partnerId;
    private String pieceId;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private String pieceItemId;
    private String imageRefId;
    private String quantity;
    private Double unitValue;
    private String currency;
    private String languageDescription;
    private String companyName;
    private String partnerType;
    private String partnerName;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;
    private String description;
    private String consignmentCurrency;
    private Double consignmentValue;
    private Double exchangeRate;
    private Double iata;
    private String customsInsurance;
    private String duty;
    private Double consignmentValueLocal;
    private Double addIata;
    private Double addInsurance;
    private Double customsValue;
    private Double calculatedTotalDuty;
    private String itemCode;
    private String hsCode;
    private Double declaredValue;
    private String codAmount;
    private Double length;
    private String dimensionUnit;
    private Double width;
    private Double height;
    private Double weight;
    private String weightUnit;
    private Double volume;
    private String volumeUnit;
    private Long deletionIndicator;
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
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;

}