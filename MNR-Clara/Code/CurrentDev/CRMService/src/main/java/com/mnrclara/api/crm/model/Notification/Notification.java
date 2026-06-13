package com.mnrclara.api.crm.model.Notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblnotification")
@Where(clause = "status='false'")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id = 0L;

	@Column(name = "topic")
	private String topic;

	@Column(name = "message", columnDefinition = "text")
	private String message;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_type")
	private String userType;

	@Column(name = "status")
	private Boolean status = false;

	@Column(name = "IS_DELETED")
	private Boolean deletionIndicator = false;
	
	@Column(name = "CTD_BY")
	private String createdBy;

	@Column(name = "CTD_ON")
    private Date createdOn = new Date();

	@Column(name = "UTD_BY")
    private String updatedBy;

	@Column(name = "UTD_ON")
	private Date updatedOn = new Date();

	//CR-Fields Added
	@Column(name = "DOC_NO")
	private String documentNumber;

	@Column(name = "INQ_NO")
	private String inquiryNo;

	@Column(name = "MATTER_NO")
	private String matterNo;

	@Column(name = "MATTER_DESC")
	private String matterDesc;

	@Column(name = "CLASS_ID")
	private Long classID;

	@Column(name = "LANG_ID")
	private String language;

	@Column(name = "IT_FORM_ID")
	private Long itFormID;
}
