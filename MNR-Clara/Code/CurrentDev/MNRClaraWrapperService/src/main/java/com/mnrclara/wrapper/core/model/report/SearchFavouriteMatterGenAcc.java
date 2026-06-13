package com.mnrclara.wrapper.core.model.report;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchFavouriteMatterGenAcc {

	private List<String> matterNumber;
	private List<String> clientId;
	private List<Long> classId;
	private List<Long> statusId;
	private Long number;
	private Boolean favourites;
	private List<String> createdBy;
	private Date fromDate;
	private Date toDate;

	private String sFromDate;
	private String sToDate;
}