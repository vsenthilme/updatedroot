package com.tekclover.wms.core.model.idmaster;
<<<<<<< HEAD

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddModuleId {

=======
import lombok.Data;

@Data
public class AddModuleId {
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private String moduleId;
	private String languageId;
<<<<<<< HEAD
=======
	private Long menuId;
	private Long subMenuId;
	private String menuName;
	private String subMenuName;
	private Boolean createUpdate;
	private Boolean delete;
	private Boolean view;
	private Boolean addModule;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private String moduleDescription;
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
}
