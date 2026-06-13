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
@Table("tblimagereferencev2")
public class ImageListV2 {
    @Id
    @PrimaryKey
    private Long imageId;

    private Long consignmentId;
    private Long pieceDetailsId;
    private Long itemDetailsId;
    private String typeId;

    private String imageRefId;
    private String languageId;
    private String companyId;
    private String partnerId;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private String pieceId;
    private String pieceItemId;
    private String languageDescription;
    private String companyName;
    private String partnerType;
    private String partnerName;
    private String imageRef;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;
    private String referenceImageUrl;
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