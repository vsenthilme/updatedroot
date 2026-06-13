package com.courier.overc360.api.midmile.primary.model.piecedetails;

import com.courier.overc360.api.midmile.primary.model.imagereference.AddImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import com.courier.overc360.api.midmile.primary.model.itemdetails.AddItemDetails;
import com.courier.overc360.api.midmile.primary.model.itemdetails.ItemDetails;
import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class AddPieceDetails {

    private Long pieceDetailsId;

    private List<AddItemDetails> itemDetails;

    private Set<AddImageReference> referenceImageList;

    private String languageId;

    private String companyId;

    private String partnerId;

    private String masterAirwayBill;

    private String houseAirwayBill;

    private String pieceId;

    private String pieceType;

    private String pieceTypeId;

    private String pieceTypeDescription;

    private Date pieceTimeStamp = new Date();

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String languageDescription;

    private Double consignmentValueLocal;

    private Double addIata;

    private Double addInsurance;

    private Double customsValue;

    private Double calculatedTotalDuty;

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
