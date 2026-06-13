package com.tekclover.wms.core.model.user;

import java.util.Date;

import lombok.Data;

@Data
public class AddUserManagement {

<<<<<<< HEAD
    private String userId;
=======
	private String userId;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private String languageId;
	private String companyCode;
	private String plantId;
	private String warehouseId;
	private Long userRoleId;
	private Long userTypeId;
<<<<<<< HEAD
    private String password;
    private String userName;
    private String firstName;
    private String lastName;
	private Long statusId;
   	private Long dateFormatId;
   	private Long currencyDecimal;
    private String timeZone;
    private String emailId;
	private Long deletionIndicator = 0L;
	private String createdBy;
    private Date createdOn = new Date();
    private String updatedBy;
=======
	private String password;
	private String userName;
	private String companyIdAndDescription;
	private String plantIdAndDescription;
	private String warehouseIdAndDescription;
	private String firstName;
	private String lastName;
	private Long statusId;
	private Long dateFormatId;
	private Long currencyDecimal;
	private String timeZone;
	private String emailId;
	private Boolean isLoggedIn;
	private Boolean resetPassword;
	private Long deletionIndicator = 0L;
	private String createdBy;
	private Date createdOn = new Date();
	private String updatedBy;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private Date updatedOn = new Date();
}
