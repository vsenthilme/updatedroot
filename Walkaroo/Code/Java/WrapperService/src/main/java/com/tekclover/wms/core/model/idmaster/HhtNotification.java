package com.tekclover.wms.core.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data
public class HhtNotification {

	private Long notificationHeaderId;
	private String languageId;
	private String companyId;
	private String plantId;
	private String warehouseId;
	private String deviceId;
	private String userId;
	private String tokenId;
	private Long deletionIndicator;
	private Boolean isLoggedIn;
	private String createdBy;
<<<<<<< HEAD
	private Date createdOn;
=======
	private Date createdOn = new Date();
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private String updatedBy;
	private Date updatedOn;
	private Boolean portalUser;
}
