package com.mnrclara.api.management.service;


import com.google.api.gax.rpc.NotFoundException;
import com.mnrclara.api.management.model.hhtnotification.CountOrderType;
import com.mnrclara.api.management.model.hhtnotification.FindNotificationMessage;
import com.mnrclara.api.management.model.hhtnotification.NotificationMessage;
import com.mnrclara.api.management.model.hhtnotification.UpdateNotification;
import com.mnrclara.api.management.repository.NotificationMessageRepository;
import com.mnrclara.api.management.repository.specification.NotificationMessageSpecification;
import com.mnrclara.api.management.util.CommonUtils;
import com.mnrclara.api.management.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationMessageService {

    @Autowired
    NotificationMessageRepository notificationMessageRepository;

    // Get All
    public List<NotificationMessage> getAll() {
        List<NotificationMessage> dbNotification = notificationMessageRepository.findAll();
        dbNotification = dbNotification.stream().filter(n -> n.getDeletionIndicator() == 0)
                .collect(Collectors.toList());

        return dbNotification;
    }

    // FindNotificationMessage
    public List<NotificationMessage> findNotification(FindNotificationMessage findNotificationMessage) throws ParseException {

       if(findNotificationMessage.getStartDate() != null && findNotificationMessage.getEndDate() != null) {
           Date[] dates = DateUtils.addTimeToDatesForSearch(findNotificationMessage.getStartDate(), findNotificationMessage.getEndDate());
           findNotificationMessage.setStartDate(dates[0]);
           findNotificationMessage.setEndDate(dates[1]);
       }

        NotificationMessageSpecification messageSpecification = new NotificationMessageSpecification(findNotificationMessage);
        List<NotificationMessage> notificationMessages = notificationMessageRepository.findAll(messageSpecification);
        return notificationMessages;
    }


    // UpdateNotification
    public List<NotificationMessage> updateNotification(List<UpdateNotification> updateNotification, String loginUserID) {
        log.info("Notification to be updated: " + updateNotification);
        List<NotificationMessage> notificationList = new ArrayList<>();
        for (UpdateNotification dbNotification : updateNotification) {
            Long notificationId = dbNotification.getNotificationId();
            if (notificationId != null) {
                NotificationMessage notificationMessage =
                        notificationMessageRepository.findByNotificationId(notificationId);
                if (notificationMessage != null) {
                    BeanUtils.copyProperties(dbNotification, notificationMessage, CommonUtils.getNullPropertyNames(dbNotification));
                    if(notificationMessage.getTab() != null) {
                        notificationMessage.setTab(notificationMessage.getTab());
                    }
                    notificationMessage.setUpdatedOn(new Date());
                    notificationMessage.setUpdatedBy(loginUserID);
                    log.info("Updated Notification : " + notificationMessage);
                    notificationList.add(notificationMessageRepository.save(notificationMessage));
                }
            }
        }
        return notificationList;
    }

    // Delete NotificationMessage
    public void deleteNotificationMessage(Long notificationId, String clientId, String classId, String loginUserID) {

        List<NotificationMessage> notificationMessages;
        if (clientId != null && classId != null && !clientId.isEmpty() && !classId.isEmpty()) {
            notificationMessages = notificationMessageRepository.findByClassIdAndClientIdAndDeletionIndicator(classId, clientId, 0L);
        } else if (notificationId != null) {
            notificationMessages = notificationMessageRepository.findByNotificationIdAndDeletionIndicator(notificationId, 0L);
        } else {
            throw new IllegalArgumentException("At least one of clientId or clientUserId must be provided");
        }

        if(notificationMessages != null && !notificationMessages.isEmpty()) {
            for (NotificationMessage dbNotification : notificationMessages) {
                dbNotification.setDeletionIndicator(1L);
                dbNotification.setUpdatedBy(loginUserID);
                dbNotification.setUpdatedOn(new Date());
                notificationMessageRepository.save(dbNotification);
            }
        } else {
            throw new IllegalArgumentException("Given values doesn't exist");
        }
    }


    // Count
//    public CountOrderType getCountForOrderType(String clientId) {
//
//        CountOrderType countOrderType = new CountOrderType();
//        // OrderType's
//        String matter = "MATTER";
//        String initial = "INITIAL";
//        String paymentPlant = "PAYMENTPLAN";
//        String invoice = "INVOICE";
//        String checkList = "CHECKLIST";
//        String documentUpload = "DOCUMENTUPLOAD";
//        String receipt = "RECEIPT";
//
//        //Count
//        Long matterCount = notificationMessageRepository.countByClientIdAndOrderType(clientId, matter);
//        Long initialRetainerCount = notificationMessageRepository.countByClientIdAndOrderType(clientId, initial);
//        Long paymentPlantCount = notificationMessageRepository.countByClientIdAndOrderType(clientId, paymentPlant);
//        Long invoiceCount = notificationMessageRepository.countByClientIdAndOrderType(clientId, invoice);
//        Long checkListCount = notificationMessageRepository.countByClientIdAndOrderType(clientId, checkList);
//        Long documentUploadCount = notificationMessageRepository.countByClientIdAndOrderType(clientId, documentUpload);
//        Long receiptCount = notificationMessageRepository.countByClientIdAndOrderType(clientId, receipt);
//
//        // sum of all count
//        Long totalCount = (matterCount != null ? matterCount : 0L) +
//                (initialRetainerCount != null ? initialRetainerCount : 0L) +
//                (paymentPlantCount != null ? paymentPlantCount : 0L) +
//                (invoiceCount != null ? invoiceCount : 0L) +
//                (checkListCount != null ? checkListCount : 0L) +
//                (documentUploadCount != null ? documentUploadCount : 0L) +
//                (receiptCount != null ? receiptCount : 0L);
//
//        countOrderType.setMatterCount(matterCount != null ? matterCount : 0L);
//        countOrderType.setInitialRetainerCount(initialRetainerCount != null ? initialRetainerCount : 0L);
//        countOrderType.setPaymentPlantCount(paymentPlantCount != null ? paymentPlantCount : 0L);
//        countOrderType.setInvoiceCount(invoiceCount != null ? invoiceCount : 0L);
//        countOrderType.setCheckListCount(checkListCount != null ? checkListCount : 0L);
//        countOrderType.setDocumentUploadCount(documentUploadCount != null ? documentUploadCount : 0L);
//        countOrderType.setReceiptNoCount(receiptCount != null ? receiptCount : 0L);
//        countOrderType.setOverAllCount(totalCount);
//
//        return countOrderType;
//    }

    // Count
    public CountOrderType getCountForOrderType(String clientId) {

        CountOrderType countOrderType = new CountOrderType();
        // OrderType's
        String matter = "MATTER";
        String initial = "INITIAL";
        String paymentPlant = "PAYMENTPLAN";
        String invoice = "INVOICE";
        String checkList = "CHECKLIST";
        String documentUpload = "DOCUMENTUPLOAD";
        String receipt = "RECEIPT";

        //Count
        Long matterCount = notificationMessageRepository.countByClientIdAndOrderTypeAndTab(clientId, matter, false);
        Long initialRetainerCount = notificationMessageRepository.countByClientIdAndOrderTypeAndTab(clientId, initial, false);
        Long paymentPlantCount = notificationMessageRepository.countByClientIdAndOrderTypeAndTab(clientId, paymentPlant, false);
        Long invoiceCount = notificationMessageRepository.countByClientIdAndOrderTypeAndTab(clientId, invoice, false);
        Long checkListCount = notificationMessageRepository.countByClientIdAndOrderTypeAndTab(clientId, checkList, false);
        Long documentUploadCount = notificationMessageRepository.countByClientIdAndOrderTypeAndTab(clientId, documentUpload, false);
        Long receiptCount = notificationMessageRepository.countByClientIdAndOrderTypeAndTab(clientId, receipt, false);

//        Long totalMenuCount = notificationMessageRepository.countByClientIdAndMenu(clientId,  false);

        // sum of all count
//        Long totalCount = (totalMenuCount != null ? totalMenuCount : 0L);

//         sum of all count
        Long totalCount = (matterCount != null ? matterCount : 0L) +
                (initialRetainerCount != null ? initialRetainerCount : 0L) +
                (paymentPlantCount != null ? paymentPlantCount : 0L) +
                (invoiceCount != null ? invoiceCount : 0L) +
                (checkListCount != null ? checkListCount : 0L) +
                (documentUploadCount != null ? documentUploadCount : 0L) +
                (receiptCount != null ? receiptCount : 0L);

        countOrderType.setMatterCount(matterCount != null ? matterCount : 0L);
        countOrderType.setInitialRetainerCount(initialRetainerCount != null ? initialRetainerCount : 0L);
        countOrderType.setPaymentPlantCount(paymentPlantCount != null ? paymentPlantCount : 0L);
        countOrderType.setInvoiceCount(invoiceCount != null ? invoiceCount : 0L);
        countOrderType.setCheckListCount(checkListCount != null ? checkListCount : 0L);
        countOrderType.setDocumentUploadCount(documentUploadCount != null ? documentUploadCount : 0L);
        countOrderType.setReceiptNoCount(receiptCount != null ? receiptCount : 0L);
        countOrderType.setOverAllCount(totalCount);

        return countOrderType;
    }

    // Count - by ClientUserId
    public CountOrderType getCountForOrderTypeByClientUserId(String clientUserId) {

        CountOrderType countOrderType = new CountOrderType();

        //Count
        Long totalMenuCount = notificationMessageRepository.countByClientUserIdAndOrderTypeAndMenu(clientUserId, "TIMETICKET", false);
        Long totalCount = (totalMenuCount != null ? totalMenuCount : 0L) ;

        countOrderType.setOverAllCount(totalCount);

        return countOrderType;
    }
}
