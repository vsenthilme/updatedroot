package com.mnrclara.api.crm.model.Notification;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
public class NotificationSave {

	private String topic;
	private String message;
	private List<String> userId;
	private String userType;
	private Date createdOn;
	private String createdBy;

	private String documentNumber;
	private String inquiryNo;
	private Long classID;
	private String language;
	private Long itFormID;
	private String matterNo;
	private String matterDesc;
}
