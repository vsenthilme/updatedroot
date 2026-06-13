package com.courier.overc360.api.midmile.replica.model.dto;


import com.courier.overc360.api.midmile.primary.model.consignment.DestinationDetails;
import com.courier.overc360.api.midmile.primary.model.consignment.OriginDetails;
import com.courier.overc360.api.midmile.primary.model.consignment.ReturnDetails;
import com.courier.overc360.api.midmile.primary.model.imagereference.AddImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
//import com.courier.overc360.api.midmile.replica.model.itemdetails.ReplicaItemDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.AddPieceDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.PieceDetails;
import com.courier.overc360.api.midmile.replica.model.itemdetails.ReplicaItemDetails;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class PreAlertManifestPieceDetails {

    private String languageId;

    //    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    //    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    //    @NotBlank(message = "MasterAirwayBill is mandatory")
    private String masterAirwayBill;

    //    @NotBlank(message = "HouseAirwayBill is mandatory")
    private String houseAirwayBill;

    private String pieceId;

    private String partnerType;

    private String partnerName;

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String pieceProductCode;

    private String description;

    private String declaredValue;

    private String codAmount;

    private Double length;

    private String dimensionUnit;

    private String width;

    private Double height;

    private Double weight;

    private String weight_unit;

    private Double volume;

    private String volumeUnit;

    private String hsCode;

    private String pieceStatusId;
    private Date pieceStatusTimestamp;

    private Set<ImageReference> referenceImageList;

    private String packReferenceNumber;

    private String tags;

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
    private Long consignmentId;

    private List<ReplicaItemDetails> itemDetails = new ArrayList<>();
}
