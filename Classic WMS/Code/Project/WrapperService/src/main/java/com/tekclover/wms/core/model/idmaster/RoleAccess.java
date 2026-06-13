package com.tekclover.wms.core.model.idmaster;

<<<<<<< HEAD
=======
import lombok.Data;

>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RoleAccess {

	private String languageId;
<<<<<<< HEAD
	private String companyCode;
	private String plantId;
	private String warehouseId;
	private Long userRoleId;
	private Long menuId;
	private Long subMenuId;
	private Long authorizationObjectId;
	private String authorizationObjectValue;
	private String userRoleName;
	private String description;
	private Long statusId;
=======
	private String companyCodeId;
	private String plantId;
	private String warehouseId;
//	private Long userRoleId;
	private Long menuId;
	private Long subMenuId;
	private Long roleId;
	private Long authorizationObjectId;
	private String authorizationObjectValue;
	private String userRoleName;
	private String companyIdAndDescription;
	private String plantIdAndDescription;
	private String warehouseIdAndDescription;
	private String description;
	private Long statusId;
	private String menuName;
	private String subMenuName;
	private String moduleId;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
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
	private Long deletionIndicator;
<<<<<<< HEAD
	private Integer create;
	private Integer edit;
	private Integer view;
	private Integer delete;
=======
	private Boolean createUpdate;
	private Boolean edit;
	private Boolean view;
	private Boolean delete;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private String createdBy;
	private Date createdOn = new Date();
	private String updatedBy;
	private Date updatedOn = new Date();
}
