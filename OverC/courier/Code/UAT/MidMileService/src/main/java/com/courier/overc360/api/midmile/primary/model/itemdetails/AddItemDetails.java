package com.courier.overc360.api.midmile.primary.model.itemdetails;

import com.courier.overc360.api.midmile.primary.model.imagereference.AddImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class AddItemDetails {

    private Long itemDetailsId;

    private Set<AddImageReference> referenceImageList;

//    private String languageId;

//    private String companyId;

//    private String partnerId;

//    private String pieceId;

//    private String masterAirwayBill;

//    private String houseAirwayBill;

//    private String pieceItemId;

    private String imageRefId;

    private Double quantity;

    private Double unitValue;

    private String currency;

//    private String languageDescription;

//    private String companyName;

    private String partnerType;

    private String partnerName;

//    private String partnerMasterAirwayBill;

//    private String partnerHouseAirwayBill;

    private String description;

    private String consignmentCurrency;

    private Double consignmentValue;

    private Double exchangeRate;

    private Double iata;

    private Double customsInsurance;

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

    private String pickupId;

    private Long deletionIndicator = 0L;

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

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

}
