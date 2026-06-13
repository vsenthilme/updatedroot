package com.mnrclara.api.accounting.service.scheduler;

import com.mnrclara.api.accounting.model.IkeyValuePair;
import com.mnrclara.api.accounting.model.Notification;
import com.mnrclara.api.accounting.repository.InvoiceHeaderRepository;
import com.mnrclara.api.accounting.repository.PaymentPlanHeaderRepository;
import com.mnrclara.api.accounting.repository.QuotationHeaderRepository;
import com.mnrclara.api.accounting.service.ManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SendNotificationScheduleService {

    @Autowired
    InvoiceHeaderRepository invoiceHeaderRepository;

    @Autowired
    QuotationHeaderRepository quotationHeaderRepository;

    @Autowired
    ManagementService managementService;

    @Autowired
    PaymentPlanHeaderRepository paymentPlanHeaderRepository;

    // Send Notification
    public void sendNotificationForInvoice() {
        List<IkeyValuePair> matterGenDetails =
                invoiceHeaderRepository.findByInvoiceNoAndClassIdAndClientId();

        try {
            if (matterGenDetails != null && !matterGenDetails.isEmpty()) {
                for (IkeyValuePair matterAccData : matterGenDetails) {

                    List<String> deviceToken = quotationHeaderRepository.getDeviceToken(
                            matterAccData.getClassId(), matterAccData.getClientId());

                    if (deviceToken != null && !deviceToken.isEmpty()) {
                        // notification
                        Notification notification = new Notification();
                        notification.setTitle("New Invoice");
                        notification.setOrderType("INVOICE");
                        notification.setToken(deviceToken);
                        notification.setClassId(matterAccData.getClassId());
                        notification.setClientId(matterAccData.getClientId());
                        notification.setMessage("Your invoice from M&R is now available");

                        String response = managementService.sendNotification(notification);
                        log.info("status update successfully");
                        if (response.equalsIgnoreCase("OK")) {
                            invoiceHeaderRepository.updateNotificationStatus(
                                    matterAccData.getInvoiceNo(), matterAccData.getClassId(), matterAccData.getClientId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception -------" + e.getMessage());
        }
    }

    // Payment Plan Send Notification
    public void paymentPlantSendNotification() {
        List<IkeyValuePair> matterGenDetails =
                paymentPlanHeaderRepository.findByPaymentPlanNoAndClassIdAndClientId();
        try {
            if (matterGenDetails != null && !matterGenDetails.isEmpty()) {
                for (IkeyValuePair matterAccData : matterGenDetails) {
                    List<String> deviceToken = quotationHeaderRepository.getDeviceToken(
                            matterAccData.getClassId(), matterAccData.getClientId());

                    if (deviceToken != null && !deviceToken.isEmpty()) {
                        // Notification
                        Notification notification = new Notification();
                        notification.setToken(deviceToken);
                        notification.setTitle("Payment Plan");
                        notification.setOrderType("PAYMENTPLAN");
                        notification.setClassId(matterAccData.getClassId());
                        notification.setClientId(matterAccData.getClientId());
                        notification.setMessage("A new payment plan - " + matterAccData.getPaymentPlanNo() + " has been created ");

                        String response = managementService.sendNotification(notification);
                        log.info("status update successfully");
                        if (response.equals("OK")) {
                            paymentPlanHeaderRepository.updateNotificationStatus(
                                    matterAccData.getPaymentPlanNo(), matterAccData.getClassId(), matterAccData.getClientId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception ---------" + e.getMessage());
        }
    }


    // Send QuotationHeader Notification
    public void sendQuotationHeaderNotification() {
        // send Notification
        List<IkeyValuePair> matterGenDetails =
                quotationHeaderRepository.findByQuotationNoAndClassIdAndClientId();

        try {
            if (matterGenDetails != null) {
                for (IkeyValuePair matterAccData : matterGenDetails) {

                    List<String> deviceToken = quotationHeaderRepository.getDeviceToken(
                            matterAccData.getClassId(), matterAccData.getClientId());

                    if (deviceToken != null && !deviceToken.isEmpty()) {
                        Notification notification = new Notification();
                        notification.setToken(deviceToken);
                        notification.setTitle("Initial Retainer");     //QUOTATION
                        notification.setOrderType("INITIAL");
                        notification.setClassId(matterAccData.getClassId());
                        notification.setClientId(matterAccData.getClientId());
                        notification.setMessage("A new retainer has been shared by M&R.");

                        String response = managementService.sendNotification(notification);
                        log.info("status update successfully");
                        if (response.equals("OK")) {
                            quotationHeaderRepository.updateNotificationStatus(
                                    matterAccData.getQuotationNo(), matterAccData.getClassId(), matterAccData.getClientId());
                        }
                    }
                }
            }
        }catch (Exception e) {
            log.info(" Exception ----------------" + e.getMessage());
        }
    }
}
