package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.console.Console;
import com.courier.overc360.api.midmile.primary.model.customscosting.*;
import com.courier.overc360.api.midmile.primary.repository.*;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.clearancecharges.ReplicaClearanceCharges;
import com.courier.overc360.api.midmile.replica.model.customscosting.FindCustomsCosting;
import com.courier.overc360.api.midmile.replica.model.customscosting.ReplicaCustomsCosting;
import com.courier.overc360.api.midmile.replica.repository.ReplicaClearanceChargesRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCustomsCostingRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPreAlertRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaCustomsCostingSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomsCostingService {

    @Autowired
    private CustomsCostingRepository customsCostingRepository;
    @Autowired
    private ReplicaCustomsCostingRepository replicaCustomsCostingRepository;

    @Autowired
    private ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    private PreAlertRepository preAlertRepository;

    @Autowired
    private ReplicaPreAlertRepository replicaPreAlertRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private CustomsClearanceInvoiceRepository customsClearanceInvoiceRepository;

    @Autowired
    private ReplicaClearanceChargesRepository chargesRepository;

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private ConsoleRepository consoleRepository;

    private final ExecutorService executorService;

    public CustomsCostingService(ReplicaCustomsCostingRepository replicaCustomsCostingRepository) {
        this.replicaCustomsCostingRepository = replicaCustomsCostingRepository;
        this.executorService = Executors.newFixedThreadPool(10); // Adjust the pool size as needed
    }

    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param costCenter
     * @param partnerId
     * @param lineNumber
     * @return
     */

    public CustomsCosting getCustomsCosting(String companyId, String languageId, String partnerId, String costCenter, Long lineNumber, Long cashNumber) {
        Optional<CustomsCosting> dbCustomsCosting = customsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndLineNumberAndCashNumberAndDeletionIndicator(
                companyId, languageId, partnerId, costCenter, lineNumber, cashNumber, 0L);
        if (dbCustomsCosting.isEmpty()) {
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " partnerId - " + partnerId + " lineNumber - " + lineNumber + " cashNumber - " + cashNumber + "  costCenter - " + costCenter + " doesn't exists");
        }
        return dbCustomsCosting.get();
    }


    /**
     * Create
     *
     * @param addCustomsCosting
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<CustomsCosting> createCustomsCosting(List<CustomsCosting> addCustomsCosting, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
       List<CustomsCosting> customsCostingList = new ArrayList<>();
        try {
            for (CustomsCosting customsCosting : addCustomsCosting){

                replicaCustomsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndLineNumberAndCashNumberAndDeletionIndicator(
                                customsCosting.getCompanyId(), customsCosting.getLanguageId(), customsCosting.getPartnerId(),
                                customsCosting.getCostCenter(), customsCosting.getLineNumber(), customsCosting.getCashNumber(), 0L)
                        .ifPresent(duplicate -> {
                            throw new BadRequestException("Record is getting duplicated with the given values : CostCenter - " + customsCosting.getCostCenter() +
                                    "CompanyId - " + customsCosting.getCompanyId() + " LanguageId - " + customsCosting.getLanguageId() + " PartnerId - " + customsCosting.getPartnerId() +
                                    " Line No - " + customsCosting.getLineNumber() + " CashNumber - "  + customsCosting.getCashNumber());
                        });
                if(customsCosting.getCostDescription().equalsIgnoreCase("CustomDuty")) {
                    replicaCustomsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndCostDescriptionAndDeletionIndicator(
                                    customsCosting.getCompanyId(), customsCosting.getLanguageId(), customsCosting.getPartnerId(),
                                    customsCosting.getCostCenter(), customsCosting.getCostDescription(), 0L)
                            .ifPresent(duplicate -> {
                                throw new BadRequestException("Record is getting duplicated with the given values : CostCenter - " + customsCosting.getCostCenter() +
                                        "CompanyId - " + customsCosting.getCompanyId() + " LanguageId - " + customsCosting.getLanguageId() + " PartnerId - " + customsCosting.getPartnerId() +
                                        "CostDescription" + customsCosting.getCostDescription());
                            });
                }
                log.info("new CustomsCosting --> " + addCustomsCosting);
                CustomsCosting newCustomsCosting = new CustomsCosting();
                BeanUtils.copyProperties(customsCosting, newCustomsCosting, CommonUtils.getNullPropertyNames(customsCosting));
                //Save without spacing
                newCustomsCosting.setCostCenter(newCustomsCosting.getCostCenter().replaceAll("\\s", "\\"));

                if(customsCosting.getCashNumber() == null || customsCosting.getLineNumber() == null) {
                    throw new BadRequestException("CashNumber - " + customsCosting.getCashNumber() + " OR " + " LineNumber " + customsCosting.getLineNumber() + " Given Null");
                }

                //Getting noOfShipments from preAlert
                Long preAlert = replicaPreAlertRepository.noOfShipments(newCustomsCosting.getCostCenter());

                newCustomsCosting.setPartnerName(newCustomsCosting.getPartnerId() + " - " + newCustomsCosting.getPartnerName());
//                updateConsignmentPreAlert(newCustomsCosting, costAmount, noOfShipments);

                if (customsCosting.getCheckField() != null) {
                    BigDecimal checkFieldValue = customsCosting.getCheckField().setScale(3, RoundingMode.DOWN);
                    customsCosting.setCheckField(checkFieldValue);
                }

                if(newCustomsCosting.getCompanyName() == null) {
                    String[] description = replicaCustomsCostingRepository.getDescription(newCustomsCosting.getCompanyId(), newCustomsCosting.getLanguageId());
                    log.info("Description : " + description);
                    if (description != null && description.length > 1) {
                        newCustomsCosting.setCompanyName(description[0]);
                        newCustomsCosting.setLanguageDescription(description[1]);
                    }
                }
                if(preAlert!= null) {
                    newCustomsCosting.setNoOfShipments(preAlert);
                }
                // Set StatusDescription
                newCustomsCosting.setStatusId("1");
                String desc = chargesRepository.getStatusDescription("1");
                if(desc != null && !desc.isEmpty()) {
                    newCustomsCosting.setStatusDescription(desc);
                }

                //Set subCustomerId&Name from preAlert table
                IKeyValuePair iKeyValuePair = replicaPreAlertRepository.findSubcustomerId(customsCosting.getCostCenter());
                if(iKeyValuePair != null){
                   newCustomsCosting.setSubCustomerId(iKeyValuePair.getSubCustomerId());
                    newCustomsCosting.setSubCustomerName(iKeyValuePair.getSubCustomerName());
                }

                newCustomsCosting.setDepartment("Airport Operation");
                newCustomsCosting.setCashHolder("Ahmad Krez");
                newCustomsCosting.setApprovedBy("Ahmad Krez");
                newCustomsCosting.setDeletionIndicator(0L);
                newCustomsCosting.setCreatedBy(loginUserID);
                newCustomsCosting.setCreatedOn(new Date());
                newCustomsCosting.setUpdatedBy(loginUserID);
                newCustomsCosting.setUpdatedOn(new Date());

                customsCostingList.add(customsCostingRepository.save(newCustomsCosting));
            }
            return customsCostingList;
        } catch (Exception e) {
                StackTraceElement[] stackTrace = e.getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    System.out.println("Class: " + element.getClassName());
                    System.out.println("Method: " + element.getMethodName());
                    System.out.println("File: " + element.getFileName());
                    System.out.println("Line: " + element.getLineNumber());
                    System.out.println("--------------------------");
                }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Consignment and Prealert
     *
     * @param customsCosting
     * @param costAmount
     * @param noOfShipments
     */
    @Transactional
    public void updateConsignmentPreAlert(CustomsCosting customsCosting, Double costAmount, Double noOfShipments) {

        Double calculatedAmount = costAmount / noOfShipments;
        String costDescription = customsCosting.getCostDescription();

        // Check for NAS-Delivery case, you can add more cases as needed
        if ("Nas-Delivery".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateNasDelivery(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateNasDelivery(calculatedAmount, customsCosting.getCostCenter());
        } else if ("Global".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateGlobal(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateGlobal(calculatedAmount, customsCosting.getCostCenter());
        } else if ("Approval".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateApproval(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateApproval(calculatedAmount, customsCosting.getCostCenter());
        } else if ("Handling&Fork".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateHandlingFork(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateHandlingFork(calculatedAmount, customsCosting.getCostCenter());
//        } else if ("Stamp Charges".equalsIgnoreCase(costDescription)) {
//            consignmentEntityRepository.bulkUpdateStampCharges(calculatedAmount, customsCosting.getCostCenter());
//            preAlertRepository.bulkUpdateStampCharges(calculatedAmount, customsCosting.getCostCenter());
        } else if ("StampChrgs".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateStampCharges(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateStampCharges(calculatedAmount, customsCosting.getCostCenter());
        } else if ("ClearanceCharge".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateClearanceCharge(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateClearanceCharge(calculatedAmount, customsCosting.getCostCenter());
        } else if ("HandlingFees".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateHandlingFees(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateHandlingFees(calculatedAmount, customsCosting.getCostCenter());
        } else if ("FoodApprovals".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateFoodApprovals(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateFoodApprovals(calculatedAmount, customsCosting.getCostCenter());
        } else if ("OtherApprovals".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateOtherApprovals(calculatedAmount, customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateOtherApprovals(calculatedAmount, customsCosting.getCostCenter());
        } else if ("SpecialApprovals".equalsIgnoreCase(costDescription)) {
            consignmentEntityRepository.bulkUpdateSpecialApproval(customsCosting.getCostAmount(), customsCosting.getCostCenter());
            preAlertRepository.bulkUpdateSpecialApproval(customsCosting.getCostAmount(), customsCosting.getCostCenter());
        }

    }

//    /**
//     * Update Consignment and PreAlert
//     *
//     * @param costAmount
//     * @param noOfShipments
//     */
//    public void updateConsignmentAndPreAlert(CustomsCosting customsCosting, Double costAmount, Double noOfShipments) {
//
//        List<ConsignmentEntity> dbConsignment = consignmentEntityRepository.consignment(customsCosting.getCostCenter());
//
//        List<PreAlert> dbPreAlert = preAlertRepository.preAlert(customsCosting.getCostCenter());
//
//        Double calculatedAmount = costAmount / noOfShipments;
//
//        for (ConsignmentEntity consignment : dbConsignment){
//
//            if (customsCosting.getCostDescription().equalsIgnoreCase("NAS-Delivery")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setNasDelivery(getUpdatedValue(consignment.getNasDelivery(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Global")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setGlobal(getUpdatedValue(consignment.getGlobal(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Approval")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setApproval(getUpdatedValue(consignment.getApproval(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Handling&Fork")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setHandlingFork(getUpdatedValue(consignment.getHandlingFork(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Stamp Charges")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setStampCharges(getUpdatedValue(consignment.getStampCharges(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Clearance Charge")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setClearanceCharge(getUpdatedValue(consignment.getClearanceCharge(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Handling Fees")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setHandlingFees(getUpdatedValue(consignment.getHandlingFees(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Food Approvals")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setFoodApprovals(getUpdatedValue(consignment.getFoodApprovals(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Other Approvals")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                consignment.setOtherApprovals(getUpdatedValue(consignment.getOtherApprovals(), calculatedAmount));
//            }
//        }
//
//        consignmentEntityRepository.saveAll(dbConsignment);
//
//        for (PreAlert preAlert : dbPreAlert){
//
//            if (customsCosting.getCostDescription().equalsIgnoreCase("NAS-Delivery")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setNasDelivery(getUpdatedValue(preAlert.getNasDelivery(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Global")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setGlobal(getUpdatedValue(preAlert.getGlobal(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Approval")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setApproval(getUpdatedValue(preAlert.getApproval(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Handling&Fork")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setHandlingFork(getUpdatedValue(preAlert.getHandlingFork(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Stamp Charges")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setStampCharges(getUpdatedValue(preAlert.getStampCharges(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Clearance Charge")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setClearanceCharge(getUpdatedValue(preAlert.getClearanceCharge(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Handling Fees")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setHandlingFees(getUpdatedValue(preAlert.getHandlingFees(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Food Approvals")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setFoodApprovals(getUpdatedValue(preAlert.getFoodApprovals(), calculatedAmount));
//
//            } else if (customsCosting.getCostDescription().equalsIgnoreCase("Other Approvals")) {
//
//                // Update dbConsignment and dbPreAlert fields, adding existing values if present
//                preAlert.setOtherApprovals(getUpdatedValue(preAlert.getOtherApprovals(), calculatedAmount));
//            }
//        }
//
//        preAlertRepository.saveAll(dbPreAlert);
//
//    }

//    /**
//     * Returns the updated value by adding the calculated amount to the existing value, if present.
//     * If the existing value is null, it returns the calculated amount directly.
//     */
//    private Double getUpdatedValue(Double existingValue, Double calculatedAmount) {
//        return (existingValue != null) ? existingValue + calculatedAmount : calculatedAmount;
//    }



    // Special Approval is Value return NewRecord Create CustomCosting
    /**
     *
     * @param console
     * @param loginUserID
     */
    public void createCustomCosting(Console console, String loginUserID) {

        Optional<ReplicaCustomsCosting> duplicateCCosting = replicaCustomsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndInvoiceNumberAndDeletionIndicator(
                        console.getCompanyId(), console.getLanguageId(), console.getPartnerId(),
                        console.getPartnerMasterAirwayBill(), console.getPartnerHouseAirwayBill(), 0L);
        if(duplicateCCosting.isEmpty()) {
            CustomsCosting newCustomCosting = new CustomsCosting();
            BeanUtils.copyProperties(console, newCustomCosting, CommonUtils.getNullPropertyNames(console));
            newCustomCosting.setPartnerId(console.getPartnerId());
            newCustomCosting.setCostCenter(console.getPartnerMasterAirwayBill());
            newCustomCosting.setInvoiceNumber(console.getPartnerHouseAirwayBill());
            newCustomCosting.setCostAmount(console.getSpecialApprovalCharge());
            newCustomCosting.setCostDescription("SpecialApprovals");

            Long cashNo = customsCostingRepository.getCashNumber(console.getPartnerMasterAirwayBill(), console.getPartnerId());
            Long lineNumber = customsCostingRepository.getLineNumber(console.getPartnerMasterAirwayBill(), console.getPartnerId());

            if(cashNo != null) {
                newCustomCosting.setCashNumber(cashNo);
            } else {
                String cashNumber = numberRangeService.getNextNumberRange("CASHNUMBER");
                newCustomCosting.setCashNumber(Long.valueOf(cashNumber));
            }
            // Set StatusDescription
            newCustomCosting.setStatusId("1");
            String desc = chargesRepository.getStatusDescription("1");
            if(desc != null && !desc.isEmpty()) {
                newCustomCosting.setStatusDescription(desc);
            }
            newCustomCosting.setLineNumber(lineNumber);
            newCustomCosting.setDepartment("Airport Operation");
            newCustomCosting.setCashHolder("Ahmad Krez");
            newCustomCosting.setApprovedBy("Ahmad Krez");
            newCustomCosting.setCreatedOn(new Date());
            newCustomCosting.setCreatedBy(loginUserID);
            customsCostingRepository.save(newCustomCosting);

//            updateConsignmentPreAlert(newCustomCosting, newCustomCosting.getCostAmount(), Double.valueOf(newCustomCosting.getNoOfShipments()));
            consignmentEntityRepository.updateSpecialApproval(newCustomCosting.getCostCenter(), newCustomCosting.getCompanyId(),
                    console.getLanguageId(), console.getSpecialApprovalCharge(), console.getPartnerId());
            preAlertRepository.updateSpecialApproval(newCustomCosting.getCostCenter(), newCustomCosting.getCompanyId(),
                    newCustomCosting.getLanguageId(), console.getSpecialApprovalCharge(), console.getPartnerId());
        }
    }

    /**
     * Create a new Custom Costing entry for Bayan HV and Console
     *
     * @param console
     * @param loginUserID
     */
    public void createCustomCostingForBayanValue(Console console, String loginUserID, Double customDuty) {

        try {
            // Check if a duplicate customs costing already exists
            Optional<ReplicaCustomsCosting> duplicateCCosting = replicaCustomsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndCostDescriptionAndDeletionIndicator(
                    console.getCompanyId(), console.getLanguageId(), console.getPartnerId(),
                    console.getPartnerMasterAirwayBill(), "CustomDuty", 0L);

            if (duplicateCCosting.isPresent()) {
                log.info("Duplicate Custom Costing found for PartnerHouseAirwayBill: {}", console.getPartnerMasterAirwayBill());
//                Double totalDuty = consoleRepository.getTotalDuty(console.getPartnerMasterAirwayBill());

                customsCostingRepository.updateAmount(console.getCompanyId(), console.getLanguageId(),
                        console.getPartnerMasterAirwayBill(), customDuty, "CustomDuty");
                return;
            }

            // Create a new Custom Costing
            CustomsCosting newCustomCosting = new CustomsCosting();
            BeanUtils.copyProperties(console, newCustomCosting, CommonUtils.getNullPropertyNames(console));
            newCustomCosting.setPartnerId(console.getPartnerId());
            newCustomCosting.setCostCenter(console.getPartnerMasterAirwayBill());
            newCustomCosting.setInvoiceNumber(console.getConsoleId());
            newCustomCosting.setReferenceField1(null);
            newCustomCosting.setCostAmount(customDuty);
            newCustomCosting.setInvoiceDate(new Date());
            newCustomCosting.setCostDescription("CustomDuty");

            Long cashNo = customsCostingRepository.getCashNumber(console.getPartnerMasterAirwayBill(), console.getPartnerId());
            Long lineNumber = customsCostingRepository.getLineNumber(console.getPartnerMasterAirwayBill(), console.getPartnerId());

            if (cashNo != null) {
                newCustomCosting.setCashNumber(cashNo);
                log.info("Retrieved existing cash number: {}", cashNo);
            } else {
                String cashNumber = numberRangeService.getNextNumberRange("CASHNUMBER");
                newCustomCosting.setCashNumber(Long.valueOf(cashNumber));
                log.info("Generated new cash number: {}", cashNumber);
            }

            // Set StatusDescription
            newCustomCosting.setStatusId("1");
            String desc = chargesRepository.getStatusDescription("1");
            if(desc != null && !desc.isEmpty()) {
                newCustomCosting.setStatusDescription(desc);
            }

            Long noOfShipment = replicaPreAlertRepository.noOfShipments(console.getPartnerMasterAirwayBill());
            if(noOfShipment != null) {
             newCustomCosting.setNoOfShipments(noOfShipment);
            }
            newCustomCosting.setDepartment("Airport Operation");
            newCustomCosting.setCashHolder("Ahmad Krez");
            newCustomCosting.setApprovedBy("Ahmad Krez");
            newCustomCosting.setLineNumber(lineNumber);
            log.info("Retrieved line number: {}", lineNumber);
            newCustomCosting.setCreatedOn(new Date());
            newCustomCosting.setCreatedBy(loginUserID);
            customsCostingRepository.save(newCustomCosting);
            consignmentEntityRepository.updateTotalDutyFromCustomCost(newCustomCosting.getCostCenter(), newCustomCosting.getCompanyId(),
                    console.getLanguageId(), console.getTotalDuty());
        }catch (Exception e) {
            // Log the error with the stack trace for debugging
            log.error("Error occurred while creating customs costing for console: {}", console, e);
        }
    }

    /**
     * create Custom Costing for stampCharge create
     *
     * @param console
     * @param loginUserID
     */
    public void createCustomCostingForStampCharge(Console console, String loginUserID) {

        try {
            // Check if a duplicate customs costing already exists
            Optional<ReplicaCustomsCosting> duplicateCCosting = replicaCustomsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndCostDescriptionAndDeletionIndicator(
                    console.getCompanyId(), console.getLanguageId(), console.getPartnerId(),
                    console.getPartnerMasterAirwayBill(), "StampChrgs", 0L);

            if (duplicateCCosting.isPresent()) {
                log.info("Duplicate Custom Costing found when Stamp Charge Create for PartnerHouseAirwayBill: {}", console.getPartnerMasterAirwayBill());
            } else {
                // Create a new Custom Costing
                CustomsCosting newCustomCosting = new CustomsCosting();
                BeanUtils.copyProperties(console, newCustomCosting, CommonUtils.getNullPropertyNames(console));
                newCustomCosting.setReferenceField1(null);
                newCustomCosting.setPartnerId(console.getPartnerId());
                newCustomCosting.setCostCenter(console.getPartnerMasterAirwayBill());
                newCustomCosting.setInvoiceDate(console.getCreatedOn());
                newCustomCosting.setCostDescription("StampChrgs");

                Long cashNo = customsCostingRepository.getCashNumber(console.getPartnerMasterAirwayBill(), console.getPartnerId());
                Long lineNumber = customsCostingRepository.getLineNumber(console.getPartnerMasterAirwayBill(), console.getPartnerId());

                if (cashNo != null) {
                    newCustomCosting.setCashNumber(cashNo);
                    log.info("Retrieved existing cash number: {}", cashNo);
                } else {
                    String cashNumber = numberRangeService.getNextNumberRange("CASHNUMBER");
                    newCustomCosting.setCashNumber(Long.valueOf(cashNumber));
                    log.info("Generated new cash number: {}", cashNumber);
                }

                Long noOfShipment = replicaPreAlertRepository.noOfShipments(console.getPartnerMasterAirwayBill());
                if (noOfShipment != null) {
                    newCustomCosting.setNoOfShipments(noOfShipment);
                    newCustomCosting.setCostAmount(Double.valueOf(noOfShipment));
                }

                // Set StatusDescription
                newCustomCosting.setStatusId("1");
                String desc = chargesRepository.getStatusDescription("1");
                if(desc != null && !desc.isEmpty()) {
                    newCustomCosting.setStatusDescription(desc);
                }
                newCustomCosting.setDepartment("Airport Operation");
                newCustomCosting.setCashHolder("Ahmad Krez");
                newCustomCosting.setApprovedBy("Ahmad Krez");
                newCustomCosting.setLineNumber(lineNumber);
                log.info("Retrieved line number: {}", lineNumber);
                newCustomCosting.setCreatedOn(new Date());
                newCustomCosting.setCreatedBy(loginUserID);
                customsCostingRepository.save(newCustomCosting);
                // Update PreAlert
//                updateConsignmentPreAlert(newCustomCosting, newCustomCosting.getCostAmount(), Double.valueOf(newCustomCosting.getNoOfShipments()));
            }
        }catch (Exception e) {
            // Log the error with the stack trace for debugging
            log.error("Error occurred while creating customs costing for console: {}", console, e);
        }
    }

    /**
     * Update
     *
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public List<CustomsCosting> updateCustomsCosting(List<CustomsCosting> customsCosting,
                                               String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        List<CustomsCosting> customsCostingList = new ArrayList<>();
        try {
            List<CustomsCosting> createCustomList = new ArrayList<>();
            customsCosting.stream().forEach(cc -> {
                Optional<CustomsCosting> dbCustomsCosting = customsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndLineNumberAndCashNumberAndDeletionIndicator(
                        cc.getCompanyId(), cc.getLanguageId(), cc.getPartnerId(), cc.getCostCenter(), cc.getLineNumber(), cc.getCashNumber(), 0L);
                if(dbCustomsCosting.isPresent()) {
                    CustomsCosting dbCustom = dbCustomsCosting.get();
                    BeanUtils.copyProperties(cc, dbCustom, CommonUtils.getNullPropertyNames(cc));

                    // Set StatusDescription
                    String desc = chargesRepository.getStatusDescription(dbCustom.getStatusId());
                    if(desc != null && !desc.isEmpty()) {
                        dbCustom.setStatusDescription(desc);
                    }
                    dbCustom.setDepartment("Airport Operation");
                    dbCustom.setCashHolder("Ahmad Krez");
                    dbCustom.setApprovedBy("Ahmad Krez");
                    dbCustom.setUpdatedBy(loginUserID);
                    dbCustom.setUpdatedOn(new Date());
                    customsCostingList.add(customsCostingRepository.save(dbCustom));

                    boolean costDescription = dbCustom.getCostDescription() != null && dbCustom.getCostDescription().equalsIgnoreCase("SpecialApprovals");

                    if (costDescription) {
                        // Calculating the new sum of cost_amount
                        Double newAmount = replicaCustomsCostingRepository.costDescriptionAmount(dbCustom.getLanguageId(),
                                dbCustom.getCompanyId(), dbCustom.getInvoiceNumber());

                        // Update the clearanceinvoice table with the new sum
                        customsClearanceInvoiceRepository.updateClearanceInvoiceAmount(
                                newAmount, dbCustom.getLanguageId(), dbCustom.getCompanyId(),
                                dbCustom.getInvoiceNumber()
                        );
                    }
                } else {
                    createCustomList.add(cc);
                }
            });
            customsCostingList.addAll(createCustomsCosting(createCustomList, loginUserID));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customsCostingList;
    }

    /**
     * Delete
     *
     * @param deleteCustomsCosting
     * @param loginUserID
     */
    public void deleteCustomsCosting(List<CustomsCosting> deleteCustomsCosting, String loginUserID) {

        if (deleteCustomsCosting != null || !deleteCustomsCosting.isEmpty()) {

            for (CustomsCosting deleteInput : deleteCustomsCosting){
                CustomsCosting dbCustomsCosting = getCustomsCosting(deleteInput.getCompanyId(),
                        deleteInput.getLanguageId(), deleteInput.getPartnerId(), deleteInput.getCostCenter(),
                        deleteInput.getLineNumber(), deleteInput.getCashNumber());
                if (dbCustomsCosting != null) {
                    dbCustomsCosting.setDeletionIndicator(1L);
                    dbCustomsCosting.setUpdatedBy(loginUserID);
                    dbCustomsCosting.setUpdatedOn(new Date());
                    customsCostingRepository.save(dbCustomsCosting);
                }
            }
        } else {
            throw new EntityNotFoundException("Error in deleting costCenter");
        }
    }


    /**
     *
     * @param customsCostingList
     * @param loginUserID
     * @return
     */
    public List<CustomsCosting> createCustomCostingForCostText(List<CustomsCosting> customsCostingList, String loginUserID) {

        List<CustomsCosting> customsCostings = new ArrayList<>();

        customsCostingList.stream().forEach(customsCosting -> {

//            Optional<CustomsCosting> getCustom = customsCostingRepository.findByCostCenterAndCompanyIdAndLanguageIdAndCashNumberAndCostDescriptionAndDeletionIndicator(
//                    customsCosting.getCostCenter(), customsCosting.getCompanyId(), customsCosting.getLanguageId(), customsCosting.getCashNumber(), customsCosting.getCostDescription(), 0L);

            CustomsCosting costing = customsCostingRepository.getCustomCosting(customsCosting.getCostCenter(), customsCosting.getCostDescription());
            if(costing != null) {
//                CustomsCosting ikey = getCustom.get();
                costing.setCostAmount(customsCosting.getCostAmount());
                costing.setDepartment("Airport Operation");
                costing.setCashHolder("Ahmad Krez");
                costing.setApprovedBy("Ahmad Krez");
                customsCostings.add(customsCostingRepository.save(costing));
            } else {
                CustomsCosting newCustom = new CustomsCosting();
                BeanUtils.copyProperties(customsCosting, newCustom, CommonUtils.getNullPropertyNames(customsCosting));
                Long lineNo = customsCostingRepository.getLineNumber(customsCosting.getCostCenter(), customsCosting.getPartnerId());

                newCustom.setDepartment("Airport Operation");
                newCustom.setCashHolder("Ahmad Krez");
                newCustom.setApprovedBy("Ahmad Krez");
                newCustom.setLineNumber(lineNo);
                newCustom.setInvoiceDate(new Date());
                newCustom.setCreatedBy(loginUserID);
                newCustom.setCreatedOn(new Date());
                customsCostings.add(customsCostingRepository.save(newCustom));
            }
        });
        return customsCostings;
    }

    /**
     * Delete Multiple
     *
     * @param costCenterList
     * @param loginUserID
     */
    public void deleteCustomCostingList(List<CustomsCosting> costCenterList, String loginUserID) {
        if(costCenterList != null && !costCenterList.isEmpty()) {
            for(CustomsCosting costCenter : costCenterList) {
                customsCostingRepository.deleteCustomCosting(costCenter.getCostCenter(), loginUserID);
            }
        }
    }

    /*======================================================REPLICA=====================================================*/


    public List<ReplicaCustomsCosting> getAll() {
        List<ReplicaCustomsCosting> customsCostingList = replicaCustomsCostingRepository.findAll();
        customsCostingList = customsCostingList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return customsCostingList;
    }


    public ReplicaCustomsCosting getReplicaCustomsCosting(String companyId, String languageId, String partnerId, String costCenter, Long lineNumber, Long cashNumber) {
        Optional<ReplicaCustomsCosting> dbCustomsCosting = replicaCustomsCostingRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndCostCenterAndLineNumberAndCashNumberAndDeletionIndicator(
                companyId, languageId, partnerId, costCenter, lineNumber, cashNumber, 0L);
        if (dbCustomsCosting.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId + ", customerId - " + partnerId + ", lineNumber - " + lineNumber + " and costCenter - " + costCenter + " doesn't exists");
        }
        return dbCustomsCosting.get();
    }

    /**
     *
     * @param findCustomsCosting
     * @return
     */
    public List<ReplicaCustomsCosting> findCustomsCosting(FindCustomsCosting findCustomsCosting) {
        ReplicaCustomsCostingSpecification spec = new ReplicaCustomsCostingSpecification(findCustomsCosting);
        List<ReplicaCustomsCosting> results = replicaCustomsCostingRepository.findAll(spec);
        log.info("found CustomsCosting --> {}", results);
        return results;
    }


    /**
     *
     * @param findCustomInvoice
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<CustomCostingInvoice>findCustomCosting(FindCustomInvoice findCustomInvoice) throws ExecutionException, InterruptedException {
        return findCustomsCostingInvoiceAsync(findCustomInvoice).get();
    }

    /**
     *
     * @param findCustomInvoice
     * @return
     */
    private CompletableFuture<List<CustomCostingInvoice>>findCustomsCostingInvoiceAsync(FindCustomInvoice findCustomInvoice) {
        return CompletableFuture.supplyAsync(() -> {
            try{
                return replicaCustomsCostingRepository.getCustomCosting(
                        findCustomInvoice.getCompanyId(),
                        findCustomInvoice.getLanguageId(),
                        findCustomInvoice.getCostCenter(),
                        findCustomInvoice.getPartnerId(),
                        findCustomInvoice.getCashNumber(),
                        findCustomInvoice.getLineNumber(),
                        findCustomInvoice.getSubCustomerId());
            } catch (Exception e) {
                throw new RuntimeException("Failed to Fetch CustomsCosting with Qry", e);
            }
        },executorService);
    }

//    /**
//     * CustomCostingInvoice
//     *
//     * @return
//     */
//    public List<CustomCostingInvoice> findCustomsCostingInvoiceAsync(FindCustomInvoice findCustomInvoice) {
//        return replicaCustomsCostingRepository.getCustomCosting(findCustomInvoice.getCompanyId(),
//                findCustomInvoice.getLanguageId(), findCustomInvoice.getCostCenter(), findCustomInvoice.getPartnerId(),
//                findCustomInvoice.getCashNumber(), findCustomInvoice.getLineNumber());
//    }

    @Transactional
    public List<CustomCostingTotalResult> findCustomCostingTotal(CustomCostingTotal customCostingTotal) {
        log.info("data --> {}", customCostingTotal.getDate());
        List<CustomsCosting> list = customsCostingRepository.getCustomCostingTotal(customCostingTotal.getCashNumber(), customCostingTotal.getDate() ,customCostingTotal.getDepartment(),
                customCostingTotal.getCashHolder(), customCostingTotal.getPartnerId(), customCostingTotal.getCostCenter(),
                customCostingTotal.getNoOfShipments(),customCostingTotal.getRemark());

        Double customDutySum = customsCostingRepository.getCustomDutySum(customCostingTotal.getCashNumber(), customCostingTotal.getDate(), customCostingTotal.getDepartment(),
                customCostingTotal.getCashHolder(), customCostingTotal.getPartnerId(), customCostingTotal.getCostCenter(),
                customCostingTotal.getNoOfShipments(), customCostingTotal.getRemark());

        CustomsCosting customDutyData = customsCostingRepository.getCustomDutyData(customCostingTotal.getCashNumber(), customCostingTotal.getDate(), customCostingTotal.getDepartment(),
                customCostingTotal.getCashHolder(), customCostingTotal.getPartnerId(), customCostingTotal.getCostCenter(),
                customCostingTotal.getNoOfShipments(), customCostingTotal.getRemark());

        log.info("custom duty sum {}", customDutySum);
        log.info("size --> {}", list.size());
        List<CustomCostingTotalResult> customCostingTotalResults = new ArrayList<>();

        CustomCostingTotalResult customCostingTotalResult = null;
        for (CustomsCosting replicaCustomsCosting : list) {

            customCostingTotalResult = new CustomCostingTotalResult();
            log.info("in loop --> ");
            log.info("data1 --> {}", replicaCustomsCosting.getCostDescription());
            customCostingTotalResult.setInvoiceNumber(replicaCustomsCosting.getInvoiceNumber());
            customCostingTotalResult.setInvoiceDate(replicaCustomsCosting.getInvoiceDate());
            customCostingTotalResult.setSupplierName(replicaCustomsCosting.getSupplierName());
            customCostingTotalResult.setCostDescription(replicaCustomsCosting.getCostDescription());
            customCostingTotalResult.setCashNumber(replicaCustomsCosting.getCashNumber());
            customCostingTotalResult.setDate(replicaCustomsCosting.getDate());
            customCostingTotalResult.setDepartment(replicaCustomsCosting.getDepartment());
            customCostingTotalResult.setCashHolder(replicaCustomsCosting.getCashHolder());
            customCostingTotalResult.setPartnerId(replicaCustomsCosting.getPartnerId());
            customCostingTotalResult.setPartnerName(replicaCustomsCosting.getPartnerName());
            customCostingTotalResult.setCostCenter(replicaCustomsCosting.getCostCenter());
            customCostingTotalResult.setNoOfShipments(replicaCustomsCosting.getNoOfShipments());
            customCostingTotalResult.setRemark(replicaCustomsCosting.getRemark());

//            String costDescription = Normalizer.normalize(replicaCustomsCosting.getCostDescription(), Normalizer.Form.NFD)
//                    .replaceAll("[^\\p{ASCII}]", "")
//                    .trim();

            // Normalize the cost description
            String costDescription = customCostingTotalResult.getCostDescription();

            if (costDescription.equalsIgnoreCase("FoodApprovals")) {
                customCostingTotalResult.setFoodApprovals(replicaCustomsCosting.getCostAmount());
            } else if (costDescription.equalsIgnoreCase("Global")) {
                customCostingTotalResult.setGlobal(replicaCustomsCosting.getCostAmount());
            } else if (costDescription.equalsIgnoreCase("Nas-Delivery")) {
                customCostingTotalResult.setNasDeliveryOrder(replicaCustomsCosting.getCostAmount());
            } else if (costDescription.equalsIgnoreCase("Approval")) {
                customCostingTotalResult.setApproval(replicaCustomsCosting.getCostAmount());
            } else if (costDescription.equalsIgnoreCase("Handling&Fork")) {
                customCostingTotalResult.setHandlingForkCharges(replicaCustomsCosting.getCostAmount());
            } else if (costDescription.equalsIgnoreCase("StampChrgs")) {
                customCostingTotalResult.setStamp(replicaCustomsCosting.getCostAmount());
            } else if(costDescription.equalsIgnoreCase("Labours")) {
                customCostingTotalResult.setLabours(replicaCustomsCosting.getCostAmount());
            } else if (costDescription.equalsIgnoreCase("SpecialApprovals")){
                customCostingTotalResult.setSpecialApprovals(replicaCustomsCosting.getCostAmount());
            } else if(costDescription.equalsIgnoreCase("OtherApprovals")){
                customCostingTotalResult.setOtherApprovals(replicaCustomsCosting.getCostAmount());
            } else if(costDescription.equalsIgnoreCase("Others")){
                customCostingTotalResult.setOthers(replicaCustomsCosting.getCostAmount());
            } else if(costDescription.equalsIgnoreCase("OtherCharges")){
                customCostingTotalResult.setOtherCharges(replicaCustomsCosting.getCostAmount());
            }
            customCostingTotalResult.setTotal(replicaCustomsCosting.getCostAmount());
            customCostingTotalResult.setCreatedBy(replicaCustomsCosting.getCreatedBy());
            customCostingTotalResult.setApprovedBy(replicaCustomsCosting.getApprovedBy());
            customCostingTotalResults.add(customCostingTotalResult);
        }

        if(customDutyData != null) {
            CustomCostingTotalResult customCostingTotalResult1 = new CustomCostingTotalResult();
            customCostingTotalResult1.setInvoiceNumber(customDutyData.getInvoiceNumber());
            customCostingTotalResult1.setInvoiceDate(customDutyData.getInvoiceDate());
            customCostingTotalResult1.setSupplierName(customDutyData.getSupplierName());
            customCostingTotalResult1.setCostDescription(customDutyData.getCostDescription());
            customCostingTotalResult1.setCashNumber(customDutyData.getCashNumber());
            customCostingTotalResult1.setDate(customDutyData.getDate());
            customCostingTotalResult1.setDepartment(customDutyData.getDepartment());
            customCostingTotalResult1.setCashHolder(customDutyData.getCashHolder());
            customCostingTotalResult1.setPartnerId(customDutyData.getPartnerId());
            customCostingTotalResult1.setPartnerName(customDutyData.getPartnerName());
            customCostingTotalResult1.setCostCenter(customDutyData.getCostCenter());
            customCostingTotalResult1.setNoOfShipments(customDutyData.getNoOfShipments());
            customCostingTotalResult1.setRemark(customDutyData.getRemark());
            customCostingTotalResult1.setTotal(customDutySum);
            customCostingTotalResult1.setCreatedBy(customDutyData.getCreatedBy());
            customCostingTotalResult1.setApprovedBy(customDutyData.getApprovedBy());
            customCostingTotalResult1.setCustomDuty(customDutySum);

            customCostingTotalResults.add(customCostingTotalResult1);
        }
        return customCostingTotalResults;
    }

    private String normalizeCostDescription(String costDescription) {
        // Normalize to remove accents and diacritics
        String normalized = Normalizer.normalize(costDescription, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .trim()
                .toLowerCase();

        // Replace multiple spaces or spaces around hyphens with a single hyphen
//        normalized = normalized.replaceAll("\\s*-\\s*", "-").replaceAll("\\s+", " ");

        return normalized;
    }

    //======================================================Report Field Update====================================================
    /**
     *
     * @param companyId
     * @param languageId
     * @param partnerMasterAirWayBill
     * @param loginUserID
     */
    public void approveCustomsCosting(String companyId, String languageId, String partnerMasterAirWayBill, String loginUserID) {
        try {
            log.info("Approve Custom costing ---> " + partnerMasterAirWayBill);
            List<String[]> numberOfShipments = replicaPreAlertRepository.shipmentsCount(Collections.singletonList(languageId), Collections.singletonList(companyId),
                                                                                        Collections.singletonList(partnerMasterAirWayBill));
            log.info("Shipments : " + numberOfShipments);
            if(numberOfShipments != null && !numberOfShipments.isEmpty()) {
                for(String[] shipment : numberOfShipments) {
                    Long shipments = shipment[4] != null ? Long.parseLong(shipment[4]) : 0L;
                    if(shipment[2] == null) {
                        throw new BadRequestException("<----- subCustomerId cannot be null ----> ");
                    }
                    ReplicaClearanceCharges clearanceCharges = chargesRepository.getClearanceCharge(shipment[0], shipment[1], shipment[2], shipments);
                    log.info("ClearanceCharges : " + clearanceCharges);
                    if(clearanceCharges == null) {
                        throw new BadRequestException("ClearanceCharge Master Not Maintained for this shipment count ----> " + shipments);
                    }
                    double clearanceCharge;
                    if (clearanceCharges.getClearanceCharges() != null) {
                        clearanceCharge = Double.parseDouble(clearanceCharges.getClearanceCharges()) / shipments;
                        log.info("Predefined ClearanceCharge to be updated ---> " + clearanceCharge);
                    } else {
                        String formula = clearanceCharges.getFormulaField1();
                        String minimumCharge = clearanceCharges.getAddMinCharge();
                        // Parse and calculate the formula
                        clearanceCharge = reportsService.parseAndCalculateFormula(formula, shipments, minimumCharge) / shipments;
                        log.info("Calculated ClearanceCharge to be updated ----> " + clearanceCharge);
                    }
                    customsCostingRepository.UpdateClearanceChargeReportValues(clearanceCharges.getLanguageId(), clearanceCharges.getCompanyId(),
                                                                               shipment[3], clearanceCharge);
                }
            }
            String statusDescription = customsCostingRepository.getStatusDescription("5");
            customsCostingRepository.updatePreAlertReportValues(languageId, companyId, partnerMasterAirWayBill, loginUserID, new Date(), 5L, statusDescription);
            log.info("Approve Custom costing completed ---> " + partnerMasterAirWayBill);
        } catch (Exception e) {
            throw new BadRequestException("Exception While Updating custom costing values " + e.toString());
        }
    }

    /**
     *
     * @param approveCustomCostingInput
     * @param loginUserID
     */
    public void approveCustomsCosting(ApproveCustomCostingInput approveCustomCostingInput, String loginUserID) {
        try {
            log.info("Batch Approve Custom costing ---> " + approveCustomCostingInput);
            List<String[]> numberOfShipments = replicaPreAlertRepository.shipmentsCount(approveCustomCostingInput.getLanguageId(), approveCustomCostingInput.getCompanyId(),
                                                                                        approveCustomCostingInput.getPartnerMasterAirWayBill());
            log.info("Shipments : " + numberOfShipments);
            if(numberOfShipments != null && !numberOfShipments.isEmpty()) {
                for(String[] shipment : numberOfShipments) {
                    Long shipments = shipment[4] != null ? Long.parseLong(shipment[4]) : 0L;
                    if(shipment[2] == null) {
                        throw new BadRequestException("<----- subCustomerId cannot be null ----> ");
                    }
                    ReplicaClearanceCharges clearanceCharges = chargesRepository.getClearanceCharge(shipment[0], shipment[1], shipment[2], shipments);
                    log.info("ClearanceCharges : " + clearanceCharges);
                    if(clearanceCharges == null) {
                        throw new BadRequestException("ClearanceCharge Master Not Maintained for this shipment count ----> " + shipments);
                    }
                    double clearanceCharge;
                    if (clearanceCharges.getClearanceCharges() != null) {
                        clearanceCharge = Double.parseDouble(clearanceCharges.getClearanceCharges()) / shipments;
                        log.info("Predefined ClearanceCharge to be updated ---> " + clearanceCharge);
                    } else {
                        String formula = clearanceCharges.getFormulaField1();
                        String minimumCharge = clearanceCharges.getAddMinCharge();
                        // Parse and calculate the formula
                        clearanceCharge = reportsService.parseAndCalculateFormula(formula, shipments, minimumCharge) / shipments;
                        log.info("Calculated ClearanceCharge to be updated ----> " + clearanceCharge);
                    }
                    customsCostingRepository.UpdateClearanceChargeReportValues(clearanceCharges.getLanguageId(), clearanceCharges.getCompanyId(),
                                                                               shipment[3], clearanceCharge);
                }
            }
            String statusDescription = customsCostingRepository.getStatusDescription("5");
            customsCostingRepository.batchUpdatePreAlertReportValues(approveCustomCostingInput.getLanguageId(), approveCustomCostingInput.getCompanyId(),
                                                                     approveCustomCostingInput.getPartnerMasterAirWayBill(), loginUserID, new Date(),  5L, statusDescription);
            log.info("<----- Batch Approve Custom costing completed ---> ");
        } catch (Exception e) {
            throw new BadRequestException("Exception While Updating custom costing values " + e.toString());
        }
    }

    /**
     * in Finance Approval
     * @param approveCustomCostingInput
     * @param loginUserID
     */
    public void approveCustomsCostingFinanceApproval(ApproveCustomCostingInput approveCustomCostingInput, String loginUserID) {
        try {
            log.info("Batch Approve FinanceApproval Custom costing Initiated ---> " + approveCustomCostingInput);
            List<String[]> numberOfShipments = replicaPreAlertRepository.shipmentsCount(approveCustomCostingInput.getLanguageId(), approveCustomCostingInput.getCompanyId(),
                                                                                        approveCustomCostingInput.getPartnerMasterAirWayBill());
            log.info("No of Shipments : " + numberOfShipments);
            if(numberOfShipments != null && !numberOfShipments.isEmpty()) {
                for(String[] shipment : numberOfShipments) {
                    Long shipments = shipment[4] != null ? Long.parseLong(shipment[4]) : 0L;
                    if(shipment[2] == null) {
                        throw new BadRequestException("<----- subCustomerId cannot be null ----> ");
                    }
                    ReplicaClearanceCharges clearanceCharges = chargesRepository.getClearanceCharge(shipment[0], shipment[1], shipment[2], shipments);
                    log.info("FinanceApproval - ClearanceCharges : " + clearanceCharges);
                    if(clearanceCharges == null) {
                        throw new BadRequestException("ClearanceCharge Master Not Maintained for this shipment count ----> " + shipments);
                    }
                    double clearanceCharge;
                    if (clearanceCharges.getClearanceCharges() != null) {
                        clearanceCharge = Double.parseDouble(clearanceCharges.getClearanceCharges()) / shipments;
                        log.info("FinanceApproval Predefined ClearanceCharge to be updated ---> " + clearanceCharge);
                    } else {
                        String formula = clearanceCharges.getFormulaField1();
                        String minimumCharge = clearanceCharges.getAddMinCharge();
                        // Parse and calculate the formula
                        clearanceCharge = reportsService.parseAndCalculateFormula(formula, shipments, minimumCharge) / shipments;
                        log.info("FinanceApproval Calculated ClearanceCharge to be updated ----> " + clearanceCharge);
                    }
                    customsCostingRepository.UpdateClearanceChargeReportValues(clearanceCharges.getLanguageId(), clearanceCharges.getCompanyId(),
                                                                               shipment[3], clearanceCharge);
                }
            }
            String statusDesc = customsCostingRepository.getStatusDescription("3");
            String statusDescription = "3 - " + statusDesc;
            customsCostingRepository.batchUpdatePreAlertReportValues(approveCustomCostingInput.getLanguageId(), approveCustomCostingInput.getCompanyId(),
                                                                     approveCustomCostingInput.getPartnerMasterAirWayBill(), loginUserID, new Date(),  3L, statusDescription);
            log.info("<----- Batch Approve FinanceApproval Custom costing completed ---> ");
        } catch (Exception e) {
            throw new BadRequestException("Exception While Updating custom costing values " + e.toString());
        }
    }

}