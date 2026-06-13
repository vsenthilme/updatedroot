package com.mnrclara.api.management.model.mattertask;

import java.util.Date;

public interface IMatterTask {

	String getTaskNumber();
	String getLanguageId();
	Long getClassId();
	String getMatterNumber();
	String getClientId();
	Long getCaseCategoryId();
	Long getCaseSubCategoryId();
	String getTaskTypeCode();
	String getTaskName();
	String getTaskDescription();
	String getPriority();
	String getTaskAssignedTo();
	String getTaskEmailId();
	Date getTimeEstimate();
	Date getCourtDate();
	Long getDeadlineCalculationDays();
	Date getDeadlineDate();
	Long getReminderCalculationDays();
	Date getReminderDate();
	Date getTaskCompletedOn();
	String getTaskCompletedBy();
	Long getStatusId();
	Long getDeletionIndicator();
	String getReferenceField1();
	String getReferenceField2();
	String getReferenceField3();
	String getReferenceField4();
	String getReferenceField5();
	String getReferenceField6();
	String getReferenceField7();
	String getReferenceField8();
	String getReferenceField9();
	String getReferenceField10();
	String getStartTime();
	String getEndTime();
	String getCreatedBy();
	Date getCreatedOn();
	String getUpdatedBy();
	Date getUpdatedOn();
	String getSCourtDate();
	String getSDeadlineDate();
	String getSReminderDate();
	String getSTaskCompletedOn();
	String getSTimeEstimate();
	String getSCreatedOn();
	String getSUpdatedOn();
}