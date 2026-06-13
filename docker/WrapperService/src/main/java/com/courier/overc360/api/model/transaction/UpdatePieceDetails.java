package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UpdatePieceDetails {

        private String pieceId;

        private String partnerType;

        private String partnerMasterAirwayBill;

        private String partnerHouseAirwayBill;

        private String partnerName;

        private String pieceProductCode;

        private String description;

        private String declaredValue;

        private String codAmount;

        private String length;

        private String dimensionUnit;

        private String width;

        private String height;

        private String weight;

        private String weight_unit;

        private String volume;

        private String volumeUnit;

        private String pieceStatusId;

        private String hsCode;

        private Date pieceStatusTimestamp;

        private List<ReferenceImageList> referenceImageList;

        private String pieceValue;

        private String pieceCurrency;

        private String consignmentLocalValue;

        private String addIata;

        private String addInsurance;

        private String customsValue;

        private String calculatedTotalDuty;

        private String packReferenceNumber;

        private String tags;

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

        private String referenceField11;

        private String referenceField12;

        private String referenceField13;

        private String referenceField14;

        private String referenceField15;

        private String referenceField16;

        private String referenceField17;

        private String referenceField18;

        private String referenceField19;

        private String referenceField20;

        private List<UpdateItemDetails> itemDetails = new ArrayList<>();

}






