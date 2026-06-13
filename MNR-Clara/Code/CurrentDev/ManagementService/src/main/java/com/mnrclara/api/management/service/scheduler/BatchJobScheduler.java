package com.mnrclara.api.management.service.scheduler;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Date;

import com.google.api.client.util.DateTime;
import com.mnrclara.api.management.service.*;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimestampFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mnrclara.api.management.model.mattergeneral.MatterGenAcc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchJobScheduler {
	
	@Autowired
	ClientGeneralService clientGeneralService;
	
	@Autowired
	CaseInfoSheetService caseInfoSheetService;
	
	@Autowired
	MatterGenAccService matterGenAccService;

	@Autowired
	SendNotificationScheduler sendNotificationScheduler;
	
//	@Scheduled(fixedDelay = 5000)
	public void processClient() throws IllegalAccessException, InvocationTargetException {
		clientGeneralService.syncupClientWithDocket();
	}
	
//	@Scheduled(fixedDelay = 10000)
	public void processMatter() throws IllegalAccessException, InvocationTargetException {
		caseInfoSheetService.syncupMatterWithDocket();
	}
	
	@Scheduled(cron = "0 15 5 * * ?")
	public void processDocketwiseUpdateForAllMatters() throws IllegalAccessException, InvocationTargetException {
		log.info("----------processDocketwiseUpdateForAllMatters-----------started------>: " + new Date());
		matterGenAccService.scheduleUpdatePriorityDateForAllMatters();
		log.info("----------processDocketwiseUpdateForAllMatters-----------ended------>: " + new Date());
	}

	@Scheduled(cron = "0 0 8 * * *")       // Every Day 8AM
	public void processOfSendNotification(){
		LocalDateTime currentDateTime = LocalDateTime.now();    // Current Time

//		log.info("-----------Matter Send Notification - Date " + new Date() + " Time " + currentDateTime);
//		sendNotificationScheduler.sendNotificationMatter();

		log.info("------------CheckList Send Notification - Date " + new Date() + " Time " + currentDateTime);
		sendNotificationScheduler.sendNotificationCheckList();

		log.info("------------Receipt Send Notification - Date " + new Date() + " Time " + currentDateTime);
		sendNotificationScheduler.sendNotificationReceiptNotice();

		log.info("------------Document Upload Send Notification - Date " + new Date() + " Time " + currentDateTime);
		sendNotificationScheduler.sendNotificationDocumentUpload();

	}
}