package com.mnrclara.api.management.repository;

import com.mnrclara.api.management.model.mattertask.IMatterTask;
import com.mnrclara.api.management.model.mattertask.MatterTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MatterTaskRepository extends JpaRepository<MatterTask,Long>,JpaSpecificationExecutor<MatterTask> {

	public List<MatterTask> findAll();
	public Optional<MatterTask> findByTaskNumber(String taskNumber);

    @Query(value = "select \n" +
            "TASK_NO taskNumber, \n" +
            "LANG_ID languageId, \n" +
            "CLASS_ID classId, \n" +
            "MATTER_NO matterNumber, \n" +
            "CLIENT_ID clientId, \n" +
            "CASE_CATEGORY_ID caseCategoryId, \n" +
            "CASE_SUB_CATEGORY_ID caseSubCategoryId, \n" +
            "TASK_TYP_CODE taskTypeCode, \n" +
            "TASK_NM taskName, \n" +
            "TASK_TEXT taskDescription, \n" +
            "PRIORITY priority, \n" +
            "TASK_ASSIGN taskAssignedTo, \n" +
            "TASK_EMAIL_ID taskEmailId, \n" +
            "TIME_ESTIMATE timeEstimate, \n" +
            "COURT_DATE courtDate, \n" +
            "DEADLINE_DAYS deadlineCalculationDays, \n" +
            "DEADLINE_DATE deadlineDate, \n" +
            "REMINDER_DAYS reminderCalculationDays, \n" +
            "REMINDER_DATE reminderDate, \n" +
            "TASK_COMP_ON taskCompletedOn, \n" +
            "TASK_COMP_BY taskCompletedBy, \n" +
            "STATUS_ID statusId, \n" +
            "IS_DELETED deletionIndicator, \n" +
            "REF_FIELD_1 referenceField1, \n" +
            "REF_FIELD_2 referenceField2, \n" +
            "REF_FIELD_3 referenceField3, \n" +
            "REF_FIELD_4 referenceField4, \n" +
            "REF_FIELD_5 referenceField5, \n" +
            "REF_FIELD_6 referenceField6, \n" +
            "REF_FIELD_7 referenceField7, \n" +
            "REF_FIELD_8 referenceField8, \n" +
            "REF_FIELD_9 referenceField9, \n" +
            "REF_FIELD_10 referenceField10, \n" +
            "START_TIME startTime, \n" +
            "END_TIME endTime, \n" +
            "CTD_BY createdBy, \n" +
            "CTD_ON createdOn, \n" +
            "UTD_BY updatedBy, \n" +
            "UTD_ON updatedOn, \n" +
            "date_format(COURT_DATE,'%Y-%m-%d') sCourtDate,\n" +
            "date_format(DEADLINE_DATE,'%Y-%m-%d') sDeadlineDate,\n" +
            "date_format(REMINDER_DATE,'%Y-%m-%d') sReminderDate,\n" +
            "date_format(TASK_COMP_ON,'%Y-%m-%d') sTaskCompletedOn,\n" +
            "date_format(TIME_ESTIMATE,'%Y-%m-%d') sTimeEstimate,\n" +
            "date_format(CTD_ON,'%Y-%m-%d') sCreatedOn,\n" +
            "date_format(UTD_ON,'%Y-%m-%d') sUpdatedOn\n" +
            "from tblmattertaskid \n" +
            "where \n" +
            "(TASK_NO IN (:taskNumber)) and \n" +
            "is_deleted=0 ", nativeQuery = true)
    public IMatterTask getMatterTask(@Param(value = "taskNumber") String taskNumber);

	@Query(value = "select \n" +
			"TASK_NO taskNumber, \n" +
			"LANG_ID languageId, \n" +
			"CLASS_ID classId, \n" +
			"MATTER_NO matterNumber, \n" +
			"CLIENT_ID clientId, \n" +
			"CASE_CATEGORY_ID caseCategoryId, \n" +
			"CASE_SUB_CATEGORY_ID caseSubCategoryId, \n" +
			"TASK_TYP_CODE taskTypeCode, \n" +
			"TASK_NM taskName, \n" +
			"TASK_TEXT taskDescription, \n" +
			"PRIORITY priority, \n" +
			"TASK_ASSIGN taskAssignedTo, \n" +
			"TASK_EMAIL_ID taskEmailId, \n" +
			"TIME_ESTIMATE timeEstimate, \n" +
			"COURT_DATE courtDate, \n" +
			"DEADLINE_DAYS deadlineCalculationDays, \n" +
			"DEADLINE_DATE deadlineDate, \n" +
			"REMINDER_DAYS reminderCalculationDays, \n" +
			"REMINDER_DATE reminderDate, \n" +
			"TASK_COMP_ON taskCompletedOn, \n" +
			"TASK_COMP_BY taskCompletedBy, \n" +
			"STATUS_ID statusId, \n" +
			"IS_DELETED deletionIndicator, \n" +
			"REF_FIELD_1 referenceField1, \n" +
			"REF_FIELD_2 referenceField2, \n" +
			"REF_FIELD_3 referenceField3, \n" +
			"REF_FIELD_4 referenceField4, \n" +
			"REF_FIELD_5 referenceField5, \n" +
			"REF_FIELD_6 referenceField6, \n" +
			"REF_FIELD_7 referenceField7, \n" +
			"REF_FIELD_8 referenceField8, \n" +
			"REF_FIELD_9 referenceField9, \n" +
			"REF_FIELD_10 referenceField10, \n" +
			"START_TIME startTime, \n" +
			"END_TIME endTime, \n" +
			"CTD_BY createdBy, \n" +
			"CTD_ON createdOn, \n" +
			"UTD_BY updatedBy, \n" +
			"UTD_ON updatedOn, \n" +
			"date_format(COURT_DATE,'%Y-%m-%d') sCourtDate,\n" +
			"date_format(DEADLINE_DATE,'%Y-%m-%d') sDeadlineDate,\n" +
			"date_format(REMINDER_DATE,'%Y-%m-%d') sReminderDate,\n" +
			"date_format(TASK_COMP_ON,'%Y-%m-%d') sTaskCompletedOn,\n" +
			"date_format(TIME_ESTIMATE,'%Y-%m-%d') sTimeEstimate,\n" +
			"date_format(CTD_ON,'%Y-%m-%d') sCreatedOn,\n" +
			"date_format(UTD_ON,'%Y-%m-%d') sUpdatedOn\n" +
			"from tblmattertaskid \n" +
			"where \n" +
			"(COALESCE(:taskNumber,null) IS NULL OR (TASK_NO IN (:taskNumber))) and \n" +
			"(COALESCE(:taskTypeCode,null) IS NULL OR (TASK_TYP_CODE IN (:taskTypeCode))) and \n" +
			"(COALESCE(:statusId,null) IS NULL OR (STATUS_ID IN (:statusId))) and \n" +
			"(COALESCE(:classId,null) IS NULL OR (CLASS_ID IN (:classId))) and \n" +
			"(COALESCE(:matterNumber,null) IS NULL OR (MATTER_NO IN (:matterNumber))) and \n" +
			"(COALESCE(:createdBy,null) IS NULL OR (CTD_BY IN (:createdBy))) and \n" +
			"(COALESCE(:taskName,null) IS NULL OR (TASK_NM IN (:taskName))) and \n" +
			"(COALESCE(:taskAssignedTo,null) IS NULL OR (TASK_ASSIGN IN (:taskAssignedTo))) and \n" +
			"(COALESCE(:sCreatedOn,null) IS NULL OR (CTD_ON BETWEEN :sCreatedOn AND :eCreatedOn)) and \n" +
			"(COALESCE(:sDeadlineDate,null) IS NULL OR (DEADLINE_DATE BETWEEN :sDeadlineDate AND :eDeadlineDate)) and \n" +
			"(COALESCE(:sReminderDate,null) IS NULL OR (REMINDER_DATE BETWEEN :sReminderDate AND :eReminderDate)) and \n" +
			"is_deleted=0 ", nativeQuery = true)
	public List<IMatterTask> findMatterTask(@Param(value = "taskNumber") List<String> taskNumber,
											@Param(value = "taskTypeCode") List<String> taskTypeCode,
											@Param(value = "statusId") List<Long> statusId,
											@Param(value = "classId") List<Long> classId,
											@Param(value = "matterNumber") List<String> matterNumber,
											@Param(value = "createdBy") List<String> createdBy,
											@Param(value = "taskName") String taskName,
											@Param(value = "taskAssignedTo") String taskAssignedTo,
											@Param(value = "sCreatedOn") Date sCreatedOn,
											@Param(value = "eCreatedOn") Date eCreatedOn,
											@Param(value = "sDeadlineDate") Date sDeadlineDate,
											@Param(value = "eDeadlineDate") Date eDeadlineDate,
											@Param(value = "sReminderDate") Date sReminderDate,
											@Param(value = "eReminderDate") Date eReminderDate);

	@Query(value = "select \n" +
			"TASK_NO taskNumber, \n" +
			"LANG_ID languageId, \n" +
			"CLASS_ID classId, \n" +
			"MATTER_NO matterNumber, \n" +
			"CLIENT_ID clientId, \n" +
			"CASE_CATEGORY_ID caseCategoryId, \n" +
			"CASE_SUB_CATEGORY_ID caseSubCategoryId, \n" +
			"TASK_TYP_CODE taskTypeCode, \n" +
			"TASK_NM taskName, \n" +
			"TASK_TEXT taskDescription, \n" +
			"PRIORITY priority, \n" +
			"TASK_ASSIGN taskAssignedTo, \n" +
			"TASK_EMAIL_ID taskEmailId, \n" +
			"TIME_ESTIMATE timeEstimate, \n" +
			"COURT_DATE courtDate, \n" +
			"DEADLINE_DAYS deadlineCalculationDays, \n" +
			"DEADLINE_DATE deadlineDate, \n" +
			"REMINDER_DAYS reminderCalculationDays, \n" +
			"REMINDER_DATE reminderDate, \n" +
			"TASK_COMP_ON taskCompletedOn, \n" +
			"TASK_COMP_BY taskCompletedBy, \n" +
			"STATUS_ID statusId, \n" +
			"IS_DELETED deletionIndicator, \n" +
			"REF_FIELD_1 referenceField1, \n" +
			"REF_FIELD_2 referenceField2, \n" +
			"REF_FIELD_3 referenceField3, \n" +
			"REF_FIELD_4 referenceField4, \n" +
			"REF_FIELD_5 referenceField5, \n" +
			"REF_FIELD_6 referenceField6, \n" +
			"REF_FIELD_7 referenceField7, \n" +
			"REF_FIELD_8 referenceField8, \n" +
			"REF_FIELD_9 referenceField9, \n" +
			"REF_FIELD_10 referenceField10, \n" +
			"START_TIME startTime, \n" +
			"END_TIME endTime, \n" +
			"CTD_BY createdBy, \n" +
			"CTD_ON createdOn, \n" +
			"UTD_BY updatedBy, \n" +
			"UTD_ON updatedOn, \n" +
			"date_format(COURT_DATE,'%Y-%m-%d') sCourtDate,\n" +
			"date_format(DEADLINE_DATE,'%Y-%m-%d') sDeadlineDate,\n" +
			"date_format(REMINDER_DATE,'%Y-%m-%d') sReminderDate,\n" +
			"date_format(TASK_COMP_ON,'%Y-%m-%d') sTaskCompletedOn,\n" +
			"date_format(TIME_ESTIMATE,'%Y-%m-%d') sTimeEstimate,\n" +
			"date_format(CTD_ON,'%Y-%m-%d') sCreatedOn,\n" +
			"date_format(UTD_ON,'%Y-%m-%d') sUpdatedOn\n" +
			"from tblmattertaskid \n" +
			"where \n" +
			"is_deleted=0 ", nativeQuery = true)
	public List<IMatterTask> getAllMatterTask();
}