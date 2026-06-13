package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.invoice.InvoiceHeader;
import com.courier.overc360.api.midmile.primary.model.invoice.InvoiceLine;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.primary.repository.InvoiceHeaderRepository;
import com.courier.overc360.api.midmile.primary.repository.InvoiceLineRepository;
import com.courier.overc360.api.midmile.primary.repository.PreAlertRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.invoice.FindInvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaInvoiceHeader;
import com.courier.overc360.api.midmile.replica.repository.ReplicaClearanceChargesRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaInvoiceHeaderRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.InvoiceHeaderSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceService {

    @Autowired
    private InvoiceHeaderRepository invoiceHeaderRepository;

    @Autowired
    private ReplicaInvoiceHeaderRepository replicaInvoiceHeaderRepository;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private PreAlertRepository preAlertRepository;

    @Autowired
    CustomsCostingService customsCostingService;

    @Autowired
    ReplicaClearanceChargesRepository chargesRepository;

    // GetAll Invoice
    public List<InvoiceHeader> getAllInvoice() {
        List<InvoiceHeader> invoiceHeaderList = invoiceHeaderRepository.findAll();
        invoiceHeaderList = invoiceHeaderList.stream().filter(i -> i.getDeletionIndicator() == 0L).collect(Collectors.toList());
        return invoiceHeaderList;
    }

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param invoiceNo
     * @return
     */
    public InvoiceHeader getInvoiceHeader(String companyId, String languageId, String invoiceNo) {
        Optional<InvoiceHeader> dbInvoice = invoiceHeaderRepository.findByCompanyIdAndLanguageIdAndInvoiceNoAndDeletionIndicator
                (companyId, languageId, invoiceNo, 0L);
        if (dbInvoice.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + " languageId - " + languageId +
                    " InvoiceNo - " + invoiceNo + " doesn't exists");
        }
        return dbInvoice.get();
    }

    // Create Invoice

    /**
     * @param invoiceHeader
     * @param loginUserID
     * @return
     */
    public List<InvoiceHeader> createInvoice(List<InvoiceHeader> invoiceHeader, String loginUserID) {

        List<InvoiceHeader> invoiceHeaderList = new ArrayList<>();

        try {
            invoiceHeader.stream().forEach(invoice -> {

                String INVOICE_NO = numberRangeService.getNextNumberRange("DDPINVOICE");
                invoiceHeaderRepository.findByCompanyIdAndLanguageIdAndInvoiceNoAndDeletionIndicator(
                                invoice.getCompanyId(), invoice.getLanguageId(), invoice.getInvoiceNo(), 0L)
                        .ifPresent(duplicate -> {
                            throw new BadRequestException("Record is Getting Duplicate with the given value InvoiceNo " + invoice.getInvoiceNo());
                        });

                InvoiceHeader newInvoice = new InvoiceHeader();
                BeanUtils.copyProperties(invoice, newInvoice, CommonUtils.getNullPropertyNames(invoice));
                newInvoice.setInvoiceNo(INVOICE_NO);
                // Set StatusDescription
                newInvoice.setStatusId(6L);
                String desc = chargesRepository.getStatusDescription("6");
                if(desc != null && !desc.isEmpty()) {
                    newInvoice.setStatusDescription(desc);
                }

                        List<InvoiceLine> invoiceLineList = new ArrayList<>();
                newInvoice.getInvoiceLines().stream().forEach(invoiceLine -> {

                    invoiceLineRepository.findByCompanyIdAndLanguageIdAndInvoiceNoAndPartnerMasterAirwayBillAndDeletionIndicator(
                                    invoiceLine.getCompanyId(), invoiceLine.getLanguageId(), invoiceLine.getInvoiceNo(), invoiceLine.getPartnerMasterAirwayBill(), 0L)
                            .ifPresent(duplicate -> {
                                throw new BadRequestException("Record is Getting Duplicate with the given value InvoiceNo " + invoice.getInvoiceNo());
                            });

                    InvoiceLine newInvoiceLine = new InvoiceLine();
                    BeanUtils.copyProperties(invoiceLine, newInvoiceLine, CommonUtils.getNullPropertyNames(invoiceLine));
                    newInvoiceLine.setCompanyId(newInvoice.getCompanyId());
                    newInvoiceLine.setLanguageId(newInvoice.getLanguageId());
                    newInvoiceLine.setInvoiceNo(newInvoice.getInvoiceNo());
                    newInvoiceLine.setCreatedBy(loginUserID);
                    newInvoiceLine.setCreatedOn(new Date());

                    Double noOfShipment = Double.valueOf(newInvoiceLine.getNoOfShipments());

                    double totalApproval = 0.0;
                    double clearanceCh = 0.0;
                    double handlingFee = 0.0;

                    if(newInvoiceLine.getHandlingFees() != null && newInvoiceLine.getHandlingFees() != 0) {
                        handlingFee = noOfShipment / newInvoiceLine.getHandlingFees();
                    }
                    if(newInvoiceLine.getClearanceCharge() != null) {
                        clearanceCh = noOfShipment / Double.parseDouble(newInvoiceLine.getClearanceCharge());
                    }
                    if (newInvoiceLine.getTotalApproval() != null && newInvoiceLine.getTotalApproval() != 0) {
                        totalApproval = noOfShipment / newInvoiceLine.getTotalApproval();
                    }

                    double roundedHandlingFee = handlingFee != 0.0 ? Math.round(handlingFee * 100.0) / 100.0 : 0.0;
                    double roundedClearanceCh = clearanceCh != 0.0 ? Math.round(clearanceCh * 100.0) / 100.0 : 0.0;
                    double roundedTotalApproval = totalApproval != 0.0 ? Math.round(totalApproval * 100.0) / 100.0 : 0.0;

                    log.info("Updating PreAlert with: PartnerMAB = {}, handlingFee = {}, clearanceCh = {}, totalApproval = {}",
                            newInvoiceLine.getPartnerMasterAirwayBill(), roundedHandlingFee, roundedClearanceCh, roundedTotalApproval);

                    preAlertRepository.invoiceCreatePreAlertUpdate(newInvoiceLine.getPartnerMasterAirwayBill(), roundedHandlingFee,
                            roundedClearanceCh, roundedTotalApproval, newInvoice.getInvoiceNo());
                    log.info("Invoice Created : PreAlert Updated {}", newInvoiceLine);
                    //Set partnerId&PartnerName from preAlert for the given partnerMasterHouseAirwayBill
                    log.info("partnerMasterAirwaybill -- {}",newInvoiceLine.getPartnerMasterAirwayBill());
                    IKeyValuePair preAlert = preAlertRepository.findPartnerId(newInvoiceLine.getPartnerMasterAirwayBill());
                    if(preAlert != null) {
                        log.info("partner id -- {}", preAlert.getPartnerId());
                        newInvoice.setPartnerName(preAlert.getPartnerName());
                        newInvoice.setPartnerId(preAlert.getPartnerId());
                    }
                    invoiceLineList.add(newInvoiceLine);
                });
                newInvoice.setInvoiceLines(invoiceLineList);
                newInvoice.setCreatedBy(loginUserID);
                newInvoice.setCreatedOn(new Date());
                invoiceHeaderList.add(invoiceHeaderRepository.save(newInvoice));
            });

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return invoiceHeaderList;
    }

    // UpdateInvoice
    /**
     * @param invoiceHeaderList
     * @param loginUserID
     * @return
     */
    public List<InvoiceHeader> updateInvoice(List<InvoiceHeader> invoiceHeaderList, String loginUserID) {

        List<InvoiceHeader> invoiceHeaders = new ArrayList<>();

        try {
            invoiceHeaderList.stream().forEach(invoiceHeader -> {

                InvoiceHeader oldInvoiceHeader = getInvoiceHeader(invoiceHeader.getCompanyId(), invoiceHeader.getLanguageId(), invoiceHeader.getInvoiceNo());

                BeanUtils.copyProperties(invoiceHeader, oldInvoiceHeader, CommonUtils.getNullPropertyNames(invoiceHeader));

                List<InvoiceLine> invoiceLineList = new ArrayList<>();
                oldInvoiceHeader.getInvoiceLines().stream().forEach(oldInvoiceLine -> {
                    invoiceHeader.getInvoiceLines().stream().forEach(updateInvoiceline -> {
                        if (Objects.equals(oldInvoiceLine.getInvoiceLineId(), updateInvoiceline.getInvoiceLineId())) {
                            BeanUtils.copyProperties(updateInvoiceline, oldInvoiceLine, CommonUtils.getNullPropertyNames(updateInvoiceline));
                            oldInvoiceLine.setUpdatedBy(loginUserID);
                            oldInvoiceLine.setUpdatedOn(new Date());
                            invoiceLineList.add(oldInvoiceLine);
                        }
                    });
                });
                oldInvoiceHeader.setInvoiceLines(invoiceLineList);
                oldInvoiceHeader.setUpdatedBy(loginUserID);
                oldInvoiceHeader.setUpdatedOn(new Date());
                invoiceHeaders.add(invoiceHeaderRepository.save(oldInvoiceHeader));
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return invoiceHeaders;
    }


    /**
     * Delete Invoice
     *
     * @param deleteInvoiceHeader
     * @param loginUserID
     */
    public void deleteInvoiceHeader(List<InvoiceHeader> deleteInvoiceHeader, String loginUserID) {

        if (deleteInvoiceHeader != null && !deleteInvoiceHeader.isEmpty()) {
            for (InvoiceHeader invoiceHeader : deleteInvoiceHeader) {
                InvoiceHeader dbInvoiceHeader = getInvoiceHeader(invoiceHeader.getCompanyId(), invoiceHeader.getLanguageId(), invoiceHeader.getInvoiceNo());

                if (dbInvoiceHeader != null) {
                    boolean allLinesDeleted = true;

                    // Update the deletion indicator for the specific invoice lines
                    for (InvoiceLine dbInvoiceLine : dbInvoiceHeader.getInvoiceLines()) {
                        boolean lineDeleted = false;
                        for (InvoiceLine inputInvoiceLine : invoiceHeader.getInvoiceLines()) {
                            if (Objects.equals(dbInvoiceLine.getInvoiceLineId(), inputInvoiceLine.getInvoiceLineId())) {
                                preAlertRepository.invoiceCreatePreAlertUpdate(dbInvoiceLine.getPartnerMasterAirwayBill());
                                log.info("PreAlert Update invoice 0");
                                dbInvoiceLine.setDeletionIndicator(1L);
                                dbInvoiceLine.setUpdatedBy(loginUserID);
                                dbInvoiceLine.setUpdatedOn(new Date());
                                lineDeleted = true;
                                break;
                            }
                        }

                        if (!lineDeleted && dbInvoiceLine.getDeletionIndicator() != 1L) {
                            allLinesDeleted = false;
                        }
                    }

                    // All line is delete set header deletionIndicator 1
                    if (allLinesDeleted) {
                        dbInvoiceHeader.setDeletionIndicator(1L);
                    }
                    dbInvoiceHeader.setUpdatedBy(loginUserID);
                    dbInvoiceHeader.setUpdatedOn(new Date());
                    invoiceHeaderRepository.save(dbInvoiceHeader);
                } else {
                    throw new EntityNotFoundException("Invoice header not found for the provided details");
                }
            }
        } else {
            throw new EntityNotFoundException("Error in deleting Invoice: No invoice headers provided");
        }
    }


    /*======================================================REPLICA=====================================================*/


    /**
     * Get All
     *
     * @return
     */
    public List<ReplicaInvoiceHeader> getAll() {
        List<ReplicaInvoiceHeader> invoiceHeaderList = replicaInvoiceHeaderRepository.findAll();
        invoiceHeaderList = invoiceHeaderList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return invoiceHeaderList;
    }


    /**
     * Get InvoiceHeader
     *
     * @param companyId
     * @param languageId
     * @param invoiceNo
     * @return
     */
    public ReplicaInvoiceHeader getReplicaInvoiceHeader(String companyId, String languageId, String invoiceNo) {
        Optional<ReplicaInvoiceHeader> dbInvoiceHeader =
                replicaInvoiceHeaderRepository.findByCompanyIdAndLanguageIdAndInvoiceNoAndDeletionIndicator(companyId, languageId, invoiceNo, 0L);
        if (dbInvoiceHeader.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId + " and InvoiceNo - " + invoiceNo + " doesn't exists");
        }
        return dbInvoiceHeader.get();
    }

    /**
     * FindInvoice
     *
     * @param findInvoiceHeader
     * @return
     */
    public List<ReplicaInvoiceHeader> findInvoiceHeader(FindInvoiceHeader findInvoiceHeader) {
//        if (findInvoiceHeader.getStartDate() != null && findInvoiceHeader.getEndDate() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(findInvoiceHeader.getStartDate(), findInvoiceHeader.getEndDate());
//            findInvoiceHeader.setStartDate(dates[0]);
//            findInvoiceHeader.setEndDate(dates[1]);
//        }
        InvoiceHeaderSpecification spec = new InvoiceHeaderSpecification(findInvoiceHeader);
        List<ReplicaInvoiceHeader> results = replicaInvoiceHeaderRepository.findAll(spec);
        log.info("found InvoiceHeader --> {}", results);
        return results;
    }


}
