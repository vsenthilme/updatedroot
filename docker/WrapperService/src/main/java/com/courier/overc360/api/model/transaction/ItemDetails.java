package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class ItemDetails {

    private String languageId;

    private String companyId;

    private String partnerId;

    private String quantity;

    private String unitValue;

    private String currency;

    private String masterAirwayBill;

    private String houseAirwayBill;

    private String pieceId;

    private String pieceItemId;

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String partnerType;

    private String consignmentCurrency;

    private String consignmentValue;

    private String exchangeRate;

    private String consignmentValueLocal;

    private String addIata;

    private String addInsurance;

    private String customsValue;

    private String calculatedTotalDuty;

    private String iata;

    private String customsInsurance;

    private String duty;

    private String length;

    private String partnerName;

    private String itemCode;

    private String hsCode;

    private String declaredValue;

    private String codAmount;

    private String dimensionUnit;

    private String width;

    private String height;

    private String weight;

    private String weightUnit;

    private String volume;

    private String volumeUnit;

    private List<ReferenceImageList> referenceImageList;

    private String imageReferenceId;

    private String description;

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

}
