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
@Table("tblpiecesdetailsv2")
public class PieceDetailsV2 {

    @Id
    @PrimaryKey
    private Long pieceDetailsId;
    private Long consignmentId;

    private String languageId;
    private String companyId;
    private String partnerId;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private String pieceId;
    private String pieceType;
    private String pieceTypeId;
    private String pieceTypeDescription;
    private Date pieceTimeStamp;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;
    private String languageDescription;
    private String companyName;
    private String partnerType;
    private String partnerName;
    private String pieceProductCode;
    private String description;
    private Double declaredValue;
    private String codAmount;
    private Double length;
    private String dimensionUnit;
    private String hsCode;
    private Double width;
    private Double height;
    private Double weight;
    private String weight_unit;
    private Double volume;
    private String volumeUnit;
    private Double consignmentValueLocal;
    private Double addIata;
    private Double addInsurance;
    private Double customsValue;
    private Double calculatedTotalDuty;
    private String packReferenceNumber;
    private String tags;
    private String pieceStatusId;
    private Date pieceStatusTimestamp;
    private String pieceStatusText;
    private String pieceEventCode;
    private String pieceEventText;
    private Date pieceEventTimestamp;
    private Double pieceValue;
    private String pieceCurrency;
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