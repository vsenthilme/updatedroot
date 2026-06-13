package com.mnrclara.api.management.service.scheduler;


import com.mnrclara.api.management.model.dto.IKeyValuePair;
import com.mnrclara.api.management.repository.*;
import com.mnrclara.api.management.service.MatterDocListHeaderService;
import com.mnrclara.api.management.service.PushNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SendNotificationScheduler {

    @Autowired
    MatterGenAccRepository matterGenAccRepository;

    @Autowired
    HhtNotificationRepository hhtNotificationRepository;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    ReceiptAppNoticeRepository receiptAppNoticeRepository;

    @Autowired
    MatterDocListHeaderRepository matterDocListHeaderRepository;

    @Autowired
    MatterDocumentRepository matterDocumentRepository;

//    // Send Notification Matter
//    public void sendNotificationMatter() {
//        // send Notification
//        List<IKeyValuePair> matterGenDetails =
//                matterGenAccRepository.findByMatterNoAndClassIdAndClientId();
//
//        if (matterGenDetails != null && !matterGenDetails.isEmpty()) {
//            for (IKeyValuePair matterAccData : matterGenDetails) {
//
//                List<String> deviceToken = hhtNotificationRepository.getDeviceToken(matterAccData.getClassId(), matterAccData.getClientId());
////                log.info("DeviceToken -------" + deviceToken);
//                if (deviceToken != null && !deviceToken.isEmpty()) {
//                    String title = "MATTER";
//                    String orderType = "MATTER";
//                    String message = " A NEW MATTER - " + matterAccData.getMatterNo() + " HAS BEEN CREATED ";
//                    try {
//                        String response = pushNotificationService.sendPushNotification(matterAccData.getClassId(), matterAccData.getClientId(),
//                                deviceToken, title, message, orderType);
//                        log.info("status update successfully");
//                        if ("OK".equals(response)) {
//                            matterGenAccRepository.updateNotificationStatus(matterAccData.getMatterNo(), matterAccData.getClassId(), matterAccData.getClientId());
//                            log.info("Notification Update Successfully");
//                        }
//                    } catch (Exception e) {
//                        log.info("Exception - " + e.getMessage());
//                    }
//                }
//            }
//        }
//    }


    // Send Notification CheckList
    public void sendNotificationCheckList() {
        // send Notification
        List<IKeyValuePair> receiptAppNotice =
                matterDocListHeaderRepository.findByCheckListNoAndClassIdAndClientId();

        try {
            if (receiptAppNotice != null && !receiptAppNotice.isEmpty()) {
                for (IKeyValuePair matterHeader : receiptAppNotice) {
                    List<String> deviceToken = hhtNotificationRepository.getDeviceToken(matterHeader.getClassId(), matterHeader.getClientId());
//                    log.info("DeviceToken -------" + deviceToken);
                    if (deviceToken != null && !deviceToken.isEmpty()) {
                        String title = "Checklist";
                        String orderType = "CHECKLIST";
                        String message = " A new document checklist has been created by M&R. Please upload documents at your convenience ";
                        String response = pushNotificationService.sendPushNotification(matterHeader.getClassId(), matterHeader.getClientId(),
                                deviceToken, title, message, orderType);
                        log.info("status update successfully");
                        if (response.equals("OK")) {
                            matterDocListHeaderRepository.updateNotificationStatus(matterHeader.getMatterNo(), matterHeader.getClassId(), matterHeader.getClientId());
                        }
                    }
                }
            }
        }catch (Exception e) {
            log.info("Exception - " + e.getMessage());
        }
    }


    // Send Notification ReceiptNotice
    public void sendNotificationReceiptNotice()  {
        List<IKeyValuePair> receiptAppNotice =
                receiptAppNoticeRepository.findByReceiptNoAndClassIdAndClientId();
        try {
            if (receiptAppNotice != null && !receiptAppNotice.isEmpty()) {
                for (IKeyValuePair matterAccData : receiptAppNotice) {

                    List<String> deviceToken = hhtNotificationRepository.getDeviceToken(matterAccData.getClassId(), matterAccData.getClientId());
//                    log.info("DeviceToken -------" + deviceToken);
                    if (deviceToken != null && !deviceToken.isEmpty()) {
                        String title = "Government Notice";
                        String orderType = "RECEIPT";
                        String message = " A new document has been uploaded by M&R ";
                        String response = pushNotificationService.sendPushNotification(matterAccData.getClassId(), matterAccData.getClientId(),
                                deviceToken, title, message, orderType);
                        log.info("status update successfully");
                        if (response.equals("OK")) {
                            receiptAppNoticeRepository.updateNotificationStatus(matterAccData.getReceiptNo(), matterAccData.getClassId(), matterAccData.getClientId());
                        }
                    }
                }
            }
        }catch (Exception e) {
            log.info("Exception - " + e.getMessage());
        }
    }


    // Send Notification DocumentUpload
    public void sendNotificationDocumentUpload()  {
        // send Notification
        List<IKeyValuePair> receiptAppNotice =
                matterDocumentRepository.findByMatterDocIdAndClassIdAndClientId();

        try {
            if (receiptAppNotice != null && !receiptAppNotice.isEmpty()) {
                for (IKeyValuePair matterDoc : receiptAppNotice) {
                    String matterText = matterDocumentRepository.findByMatterText(matterDoc.getMatterNo());
                    List<String> deviceToken = hhtNotificationRepository.getDeviceToken(
                            matterDoc.getClassId(), matterDoc.getClientId());
                    log.info("DeviceToken -------" + deviceToken);
                    if (deviceToken != null && !deviceToken.isEmpty()) {
                        String title = "Document Upload";
                        String orderType = "DOCUMENTUPLOAD";
                        String message = " A new document has been uploaded by M&R.";
                        String response = pushNotificationService.sendPushNotification(matterDoc.getClassId(), matterDoc.getClientId(),
                                deviceToken, title, message, orderType);
                        log.info("status update successfully");
                        if (response.equals("OK")) {
                            matterDocumentRepository.updateNotificationStatus(
                                    matterDoc.getMatterNo(), matterDoc.getClassId(), matterDoc.getClientId());
                        }
                    }
                }
            }
        }catch (Exception e) {
            log.info("Exception - " + e.getMessage());
        }
    }

}
