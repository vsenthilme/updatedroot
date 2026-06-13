package com.mnrclara.wrapper.core.model.report;

import lombok.Data;
import java.util.Date;

@Data
public class FavouritesMatterGenAcc {

	private String matterNumber;
	private String languageId;
	private String statusId;
	private String statusDesc;
	private Long classId;
	private String classIdDescription;
	private String clientId;
	private String clientName;
	private String matterDescription;
	private boolean favourites;
	private Date viewedDate;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
}