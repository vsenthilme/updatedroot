package com.tekclover.wms.core.model.idmaster;

<<<<<<< HEAD
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class HhtUser {
=======
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HhtUser { 
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

	private String languageId;
	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private String userId;
	private String password;
	private String userName;
	private Long statusId;
	private Boolean caseReceipts;
	private Boolean itemReceipts;
	private Boolean putaway;
<<<<<<< HEAD
=======
	private Long levelId;
	private String companyIdAndDescription;
	private String plantIdAndDescription;
	private String warehouseIdAndDescription;
	private String levelIdAndDescription;
	private List<OrderTypeId> orderTypeIds;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private Boolean transfer;
	private Boolean picking;
	private Boolean quality;
	private Boolean inventory;
	private Boolean customerReturn;
	private Boolean supplierReturn;
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
	private Long deletionIndicator = 0L;
	private String createdBy;
	private Date createdOn = new Date();
	private String updatedBy;
	private Date updatedOn = new Date();
<<<<<<< HEAD
}
=======
}
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
