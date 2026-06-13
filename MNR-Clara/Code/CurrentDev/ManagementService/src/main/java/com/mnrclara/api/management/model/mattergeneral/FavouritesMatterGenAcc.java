package com.mnrclara.api.management.model.mattergeneral;

import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblfavouritesmattergenaccid")
public class FavouritesMatterGenAcc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FAV_MAT_ID")
	private Long favouriteMatterId;

	@Column(name = "MATTER_NO")
	private String matterNumber;
	
	@Column(name = "LANG_ID")
	private String languageId;

	@Column(name = "CLASS_ID")
	private Long classId;

	@Column(name = "CLASS_TEXT")
	private String classIdDescription;

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "STATUS_TEXT")
	private String statusDesc;

	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "CLIENT_NAME")
	private String clientName;

	@Column(name = "MATTER_TEXT")
	private String matterDescription;

	@Column(name = "FAV", columnDefinition = "BIT default 0")
	private boolean favourites;

	@Column(name = "VIEW_DATE")
	private Date viewedDate;

	@Column(name = "IS_DELETED")
	private Long deletionIndicator = 0L;

	@Column(name = "CTD_BY")
	private String createdBy;

	@Column(name = "CTD_ON")
	private Date createdOn;

	@Column(name = "UTD_BY")
	private String updatedBy;

	@Column(name = "UTD_ON")
	private Date updatedOn;
}