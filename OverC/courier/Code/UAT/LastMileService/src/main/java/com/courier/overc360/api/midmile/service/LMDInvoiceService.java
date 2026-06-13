package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.invoice.ChargeInfo;
import com.courier.overc360.api.midmile.primary.model.invoice.DeleteInvoice;
import com.courier.overc360.api.midmile.primary.model.invoice.LMDInvoiceHeader;
import com.courier.overc360.api.midmile.primary.model.invoice.LMDInvoiceLine;
import com.courier.overc360.api.midmile.primary.repository.LMDInvoiceHeaderRepository;
import com.courier.overc360.api.midmile.primary.repository.LMDInvoiceLineRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.invoice.FindLMDInvoiceHeader;
import com.courier.overc360.api.midmile.replica.model.invoice.LMDInvoice;
import com.courier.overc360.api.midmile.replica.model.invoice.ReplicaLMDInvoiceHeader;
import com.courier.overc360.api.midmile.replica.repository.ReplicaLMDInvoiceHeaderRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaLMDInvoiceHeaderSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class LMDInvoiceService {

    @Autowired
    LMDInvoiceHeaderRepository invoiceRepository;

    @Autowired
    LMDInvoiceLineRepository invoiceLineRepository;

    @Autowired
    ReplicaLMDInvoiceHeaderRepository replicaLMDInvoiceHeaderRepository;

    @Autowired
    NumberRangeService numberRangeService;

    // Initialize line number starting at 1
    AtomicInteger lineNumberCounter = new AtomicInteger(1);

    // Create Invoice
    public List<LMDInvoiceHeader> createInvoice(List<LMDInvoiceHeader> invoiceHeaders, String loginUserID) {
        List<LMDInvoiceHeader> invoiceHeaderList = new ArrayList<>();
        invoiceHeaders.forEach(invoice -> {
            boolean duplicate = invoiceRepository.existsByCompanyIdAndLanguageIdAndCustomerIdAndDeletionIndicator(
                    invoice.getCompanyId(), invoice.getLanguageId(), invoice.getCustomerId(), 0L);
            if (duplicate) {
                throw new BadRequestException("Duplicate the Given Records");
            }
            LMDInvoiceHeader dbInvoice = new LMDInvoiceHeader();
//            String lmdInvoiceNo = numberRangeService.getNextNumberRange("LMDINVOICENO");
//            if (lmdInvoiceNo == null) {
//                throw new BadRequestException("LMDINVOICENO NumberRange Not Maintain in NumberRange Table");
//            }
//            dbInvoice.setInvoiceNo(lmdInvoiceNo);
            BeanUtils.copyProperties(invoice, dbInvoice, CommonUtils.getNullPropertyNames(invoice));
            dbInvoice.setCreatedBy(loginUserID);
            dbInvoice.setCreatedOn(new Date());
            dbInvoice.getInvoiceLines().forEach(line -> {
                line.setCompanyId(dbInvoice.getCompanyId());
                line.setLanguageId(dbInvoice.getLanguageId());
                line.setInvoiceNo(dbInvoice.getInvoiceNo());
                line.setCreatedBy(loginUserID);
                line.setCreatedOn(new Date());
            });
            invoiceHeaderList.add(invoiceRepository.save(dbInvoice));
        });
        return invoiceHeaderList;
    }

    // Update LMD_Invoice
    public List<LMDInvoiceHeader> updateInvoice(List<LMDInvoiceHeader> invoiceHeaders, String loginUserID) {
        List<LMDInvoiceHeader> invoiceHeaderList = new ArrayList<>();
        invoiceHeaders.forEach(invoice -> {
            Optional<LMDInvoiceHeader> dbInvoice = invoiceRepository.findByCompanyIdAndLanguageIdAndInvoiceNoAndCustomerIdAndDeletionIndicator(
                    invoice.getCompanyId(), invoice.getLanguageId(), invoice.getInvoiceNo(), invoice.getCustomerId(), 0L);
            if (dbInvoice.isPresent()) {
                LMDInvoiceHeader invoiceHeader = dbInvoice.get();
                BeanUtils.copyProperties(invoice, invoiceHeader, CommonUtils.getNullPropertyNames(invoice));
                invoiceHeader.setUpdatedBy(loginUserID);
                invoiceHeader.setUpdatedOn(new Date());
                invoiceHeaderList.add(invoiceRepository.save(invoiceHeader));
            } else {
                throw new BadRequestException("Given Values Doesn't exist");
            }
        });
        return invoiceHeaderList;
    }


    // Delete_LMD_Invoice
    public void deleteInvoice(List<DeleteInvoice> deleteInvoices, String loginUserID) {

        deleteInvoices.forEach(invoice -> {
            if (invoice.getLineNo() == null) {
                Optional<LMDInvoiceHeader> dbHeader = invoiceRepository.findByCompanyIdAndLanguageIdAndInvoiceNoAndCustomerIdAndDeletionIndicator(
                        invoice.getCompanyId(), invoice.getLanguageId(), invoice.getInvoiceNo(), invoice.getCustomerId(), 0L);
                if (dbHeader.isPresent()) {
                    LMDInvoiceHeader ih = dbHeader.get();
                    ih.setDeletionIndicator(1L);
                    ih.setUpdatedOn(new Date());
                    ih.setUpdatedBy(loginUserID);
                    invoiceRepository.save(ih);
                } else {
                    throw new BadRequestException("Given Values Doesn't exist");
                }
            } else {
                Optional<LMDInvoiceLine> dbLine = invoiceLineRepository.findByCompanyIdAndLanguageIdAndCustomerIdAndInvoiceNoAndLineNumberAndDeletionIndicator(
                        invoice.getCompanyId(), invoice.getLanguageId(), invoice.getCustomerId(), invoice.getInvoiceNo(), invoice.getLineNo(), 0L);
                if (dbLine.isPresent()) {
                    LMDInvoiceLine il = dbLine.get();
                    il.setDeletionIndicator(1L);
                    il.setUpdatedBy(loginUserID);
                    il.setUpdatedOn(new Date());
                    invoiceLineRepository.save(il);
                } else {
                    throw new BadRequestException("Given Values Doesn't exist");
                }
            }
        });
    }


    // CreateInvoice
    @Transactional
    public void createInvoice(IKeyValuePair iKeyValuePair, String customerId) {

        LMDInvoiceHeader newInvoice = new LMDInvoiceHeader();
        String lmdInvoiceNo = numberRangeService.getNextNumberRange("LMDINVOICENO");
        if (lmdInvoiceNo == null) {
            throw new BadRequestException("LMDINVOICENO NumberRange Not Maintain in NumberRange Table");
        }
        newInvoice.setCustomerId(customerId);
        newInvoice.setCompanyId(iKeyValuePair.getCompanyId());
        newInvoice.setLanguageId(iKeyValuePair.getLangId());
        newInvoice.setCustomerName(iKeyValuePair.getCustomerName());
        newInvoice.setInvoiceNo(lmdInvoiceNo);
        Double ceilingValue = iKeyValuePair.getCeilingValue();
        Double chargeableWeight = iKeyValuePair.getChargeableWeight();
        Double frightCharge = iKeyValuePair.getFrightCharge();
        Double codCharge = iKeyValuePair.getCodCharge();
        Double fulfilmentCharge = iKeyValuePair.getFulfilmentCharge();
        Double rtoCharge = iKeyValuePair.getRtoCharge();
        Double asrCharge = iKeyValuePair.getAsrCharge();
        Double movementCharge = iKeyValuePair.getMovementCharge();
        Double truckCharge = iKeyValuePair.getTruckCharge();
        Long ceilingValueCount = iKeyValuePair.getCeilingValueCount();
        Long chargeableWeightCount = iKeyValuePair.getChargeableWeightCount();
        Long frightChargeCount = iKeyValuePair.getFrightChargeCount();
        Long codChargeCount = iKeyValuePair.getCodChargeCount();
        Long fulfilmentChargeCount = iKeyValuePair.getFulfilmentChargeCount();
        Long rtoChargeCount = iKeyValuePair.getRtoChargeCount();
        Long asrChargeCount = iKeyValuePair.getAsrChargeCount();
        Long movementChargeCount = iKeyValuePair.getMovementChargeCount();
        Long truckChargeCount = iKeyValuePair.getTruckChargeCount();

        boolean duplicate = invoiceRepository.existsByCompanyIdAndLanguageIdAndCustomerIdAndDeletionIndicator(
                newInvoice.getCompanyId(), newInvoice.getLanguageId(), newInvoice.getCustomerId(), 0L);
        if (duplicate) {
            throw new BadRequestException("Duplicate the Given Records");
        }
        // Create ChargeValue
        List<ChargeInfo> chargeInfoList = new ArrayList<>();
        if (ceilingValue != null && ceilingValue != 0.0) {
            chargeInfoList.add(new ChargeInfo("Ceiling Value", ceilingValue, ceilingValueCount));
        }
        if (chargeableWeight != null && chargeableWeight != 0.0) {
            chargeInfoList.add(new ChargeInfo("Chargeable Weight", chargeableWeight, chargeableWeightCount));
        }
        if (frightCharge != null && frightCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Delivery Charges", frightCharge, frightChargeCount));
        }
        if (codCharge != null && codCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("COD Collection Charges", codCharge, codChargeCount));
        }
        if (fulfilmentCharge != null && fulfilmentCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Fulfilment", fulfilmentCharge, fulfilmentChargeCount));
        }
        if (rtoCharge != null && rtoCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("RTO Charges", rtoCharge, rtoChargeCount));
        }
        if (asrCharge != null && asrCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("ASR", asrCharge, asrChargeCount));
        }
        if (movementCharge != null && movementCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Moving shipments from AWB level to SKU level", movementCharge, movementChargeCount));
        }
        if (truckCharge != null && truckCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Truck Charges", truckCharge, truckChargeCount));
        }
        long lineNumber = 1;
        // Multiple_Invoice_Line
        for (ChargeInfo chargeInfo : chargeInfoList) {
            LMDInvoiceLine line = new LMDInvoiceLine();
            BeanUtils.copyProperties(newInvoice, line, CommonUtils.getNullPropertyNames(newInvoice));
            line.setChargeDescription(chargeInfo.getChargeDescription());
            line.setLineNumber(lineNumber++);
            line.setChargeAmount(chargeInfo.getChargeAmount());
            line.setNoOfShipment(String.valueOf(chargeInfo.getNoOfShipment()));
            newInvoice.getInvoiceLines().add(line);
        }
        invoiceRepository.save(newInvoice);
    }

    // Find_
    public List<ReplicaLMDInvoiceHeader> findInvoice(FindLMDInvoiceHeader findLMDInvoiceHeader) {
        ReplicaLMDInvoiceHeaderSpecification spec = new ReplicaLMDInvoiceHeaderSpecification(findLMDInvoiceHeader);
        List<ReplicaLMDInvoiceHeader> results = replicaLMDInvoiceHeaderRepository.findAll(spec);
        log.info("Found Ndr -->{}", results);
        return results;
    }


    // ManualCreate LMD_INVOICE
    public LMDInvoiceHeader findLMDInvoice(LMDInvoice lmdInvoice, String loginUserID) throws ParseException {
        LMDInvoiceHeader lmdInvoiceHeader = null;
        Date fromDate = lmdInvoice.getFromDate();
        Date toDate = lmdInvoice.getToDate();
        if (lmdInvoice.getFromDate() != null && lmdInvoice.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(lmdInvoice.getFromDate(), lmdInvoice.getToDate());
            lmdInvoice.setFromDate(dates[0]);
            lmdInvoice.setToDate(dates[1]);
            log.info("FROM DATE --> {}", lmdInvoice.getFromDate());
            log.info("TO DATE --> {}", lmdInvoice.getToDate());
        }
        IKeyValuePair charge = replicaLMDInvoiceHeaderRepository.getChargeValue(lmdInvoice.getCustomerId(), lmdInvoice.getFromDate(), lmdInvoice.getToDate());
        if (charge != null) {
            lmdInvoiceHeader = createInvoiceManual(charge, lmdInvoice.getCustomerId(), loginUserID);
            lmdInvoiceHeader.getInvoiceLines()
                    .forEach(line -> {
                        line.setToDate(toDate);
                        line.setFromDate(fromDate);
                    });
        } else {
            throw new BadRequestException("Given Values Consignment table doesn't record CustomerId -" + lmdInvoice.getCustomerId() + " From Date --" + lmdInvoice.getFromDate() + " To Date - " + lmdInvoice.getToDate());
        }
        return lmdInvoiceHeader;
    }

    // ManualCreate
    public LMDInvoiceHeader createInvoiceManual(IKeyValuePair iKeyValuePair, String customerId, String loginUserID) {

        LMDInvoiceHeader newInvoice = new LMDInvoiceHeader();
        String lmdInvoiceNo = numberRangeService.getNextNumberRange("LMDINVOICENO");
        if (lmdInvoiceNo == null) {
            throw new BadRequestException("LMDINVOICENO NumberRange Not Maintain in NumberRange Table");
        }
        newInvoice.setCustomerId(customerId);
        newInvoice.setCompanyId(iKeyValuePair.getCompanyId());
        newInvoice.setLanguageId(iKeyValuePair.getLangId());
        newInvoice.setCompanyName(iKeyValuePair.getCompanyDesc());
        newInvoice.setLanguageDescription(iKeyValuePair.getLangDesc());
        newInvoice.setCustomerName(iKeyValuePair.getCustomerName());
        newInvoice.setInvoiceNo(lmdInvoiceNo);
        newInvoice.setCreatedBy(loginUserID);
        newInvoice.setCreatedOn(new Date());
        Double ceilingValue = iKeyValuePair.getCeilingValue();
        Double chargeableWeight = iKeyValuePair.getChargeableWeight();
        Double frightCharge = iKeyValuePair.getFrightCharge();
        Double codCharge = iKeyValuePair.getCodCharge();
        Double fulfilmentCharge = iKeyValuePair.getFulfilmentCharge();
        Double rtoCharge = iKeyValuePair.getRtoCharge();
        Double asrCharge = iKeyValuePair.getAsrCharge();
        Double movementCharge = iKeyValuePair.getMovementCharge();
        Double truckCharge = iKeyValuePair.getTruckCharge();
        Long ceilingValueCount = iKeyValuePair.getCeilingValueCount();
        Long chargeableWeightCount = iKeyValuePair.getChargeableWeightCount();
        Long frightChargeCount = iKeyValuePair.getFrightChargeCount();
        Long codChargeCount = iKeyValuePair.getCodChargeCount();
        Long fulfilmentChargeCount = iKeyValuePair.getFulfilmentChargeCount();
        Long rtoChargeCount = iKeyValuePair.getRtoChargeCount();
        Long asrChargeCount = iKeyValuePair.getAsrChargeCount();
        Long movementChargeCount = iKeyValuePair.getMovementChargeCount();
        Long truckChargeCount = iKeyValuePair.getTruckChargeCount();

        // Create ChargeValue
        List<ChargeInfo> chargeInfoList = new ArrayList<>();
        if (ceilingValue != null && ceilingValue != 0.0) {
            chargeInfoList.add(new ChargeInfo("Ceiling Value", ceilingValue, ceilingValueCount));
        }
        if (chargeableWeight != null && chargeableWeight != 0.0) {
            chargeInfoList.add(new ChargeInfo("Chargeable Weight", chargeableWeight, chargeableWeightCount));
        }
        if (frightCharge != null && frightCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Delivery Charges", frightCharge, frightChargeCount));
        }
        if (codCharge != null && codCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("COD Collection Charges", codCharge, codChargeCount));
        }
        if (fulfilmentCharge != null && fulfilmentCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Fulfilment", fulfilmentCharge, fulfilmentChargeCount));
        }
        if (rtoCharge != null && rtoCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("RTO Charges", rtoCharge, rtoChargeCount));
        }
        if (asrCharge != null && asrCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("ASR", asrCharge, asrChargeCount));
        }
        if (movementCharge != null && movementCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Moving shipments from AWB level to SKU level", movementCharge, movementChargeCount));
        }
        if (truckCharge != null && truckCharge != 0.0) {
            chargeInfoList.add(new ChargeInfo("Truck Charges", truckCharge, truckChargeCount));
        }
        long lineNumber = 1;
        // Multiple_Invoice_Line
        for (ChargeInfo chargeInfo : chargeInfoList) {
            LMDInvoiceLine line = new LMDInvoiceLine();
            BeanUtils.copyProperties(newInvoice, line, CommonUtils.getNullPropertyNames(newInvoice));
            line.setInvoiceNo(newInvoice.getInvoiceNo());
            line.setCompanyId(newInvoice.getCompanyId());
            line.setLanguageId(newInvoice.getLanguageId());
            line.setChargeDescription(chargeInfo.getChargeDescription());
            line.setNoOfShipment(String.valueOf(chargeInfo.getNoOfShipment()));
            line.setLineNumber(lineNumber++);
            line.setChargeAmount(chargeInfo.getChargeAmount());
            line.setCreatedBy(loginUserID);
            line.setCreatedOn(new Date());

            newInvoice.getInvoiceLines().add(line);
        }
        newInvoice.getInvoiceLines().sort(Comparator.comparing(LMDInvoiceLine::getLineNumber));
        return newInvoice;
    }
}
