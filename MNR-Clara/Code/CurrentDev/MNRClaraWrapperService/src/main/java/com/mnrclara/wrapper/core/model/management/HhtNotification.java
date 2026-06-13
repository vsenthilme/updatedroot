package com.mnrclara.wrapper.core.model.management;

import lombok.Data;

import java.util.Date;

@Data
public class HhtNotification {

	private Long notificationHeaderId;

	private String classId;

	private String clientId;

	private String clientUserId;

	private String deviceId;

	private String tokenId;

	private Boolean isLoggedIn;

	private Long deletionIndicator;

	private String createdBy;

	private Date createdOn = new Date();

	private String updatedBy;

	private Date updatedOn;


}
