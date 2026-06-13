package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.consignment.DestinationDetails;
import com.courier.overc360.api.midmile.primary.model.console.Console;
import com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice.AddCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice.CustomsClearanceInvoice;
import com.courier.overc360.api.midmile.primary.model.customsclearanceinvoice.UpdateCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.repository.ConsoleRepository;
import com.courier.overc360.api.midmile.primary.repository.CustomsClearanceInvoiceRepository;
import com.courier.overc360.api.midmile.primary.repository.DestinationDetailsRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice.FindCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice.ReplicaCustomsClearanceInvoice;
import com.courier.overc360.api.midmile.replica.repository.ReplicaConsignmentStatusRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaConsoleRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCustomsClearanceInvoiceRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCustomsCostingRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaCustomsClearanceInvoiceSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomsClearanceInvoiceService {

    @Autowired
    private CustomsClearanceInvoiceRepository customsClearanceInvoiceRepository;

    @Autowired
    private ReplicaCustomsClearanceInvoiceRepository replicaCustomsClearanceInvoiceRepository;

    @Autowired
    private ConsoleRepository consoleRepository;

    @Autowired
    private ReplicaConsoleRepository replicaConsoleRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaConsignmentStatusRepository replicaConsignmentStatusRepository;

    @Autowired
    private ConsignmentService consignmentService;

    @Autowired
    private DestinationDetailsRepository destinationDetailsRepository;

    @Autowired
    private ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    private ReplicaCustomsCostingRepository replicaCustomsCostingRepository;

    /*-----------------------------------------PRIMARY-----------------------------------------------*/

    /**
     * Get CustomsClearanceInvoice
     *
     * @param languageId
     * @param companyId
     * @param partnerHouseAirwayBill
     * @param houseAirwayBill
     * @param invoiceNo
     * @return
     */
    public CustomsClearanceInvoice getCustomsClearanceInvoice(String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo) {

        Optional<CustomsClearanceInvoice> dbCustomsClearanceInvoice = customsClearanceInvoiceRepository.findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo, 0L);

        if (dbCustomsClearanceInvoice.isEmpty()) {
            String errMsg ="The given values - languageId: " + languageId + ", companyId: " + companyId + ", partnerHouseAirwayBill: " + partnerHouseAirwayBill + ", houseAirwayBill: " + houseAirwayBill + ", invoiceNo: " + invoiceNo + " doesn't exist.";

            // Error log
//            createCustomsClearanceInvoiceLog1(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCustomsClearanceInvoice.get();
    }


    /**
     * Create CustomsClearanceInvoice
     *
     * @param addCustomsClearanceInvoice
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public CustomsClearanceInvoice createCustomsClearanceInvoice(AddCustomsClearanceInvoice addCustomsClearanceInvoice, String loginUserID) throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        try {
            Optional<CustomsClearanceInvoice> dbCustomsClearanceInvoice = customsClearanceInvoiceRepository.findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(addCustomsClearanceInvoice.getLanguageId(), addCustomsClearanceInvoice.getCompanyId(), addCustomsClearanceInvoice.getPartnerHouseAirwayBill(), addCustomsClearanceInvoice.getHouseAirwayBill(), addCustomsClearanceInvoice.getInvoiceNo(), addCustomsClearanceInvoice.getDeletionIndicator());

            boolean duplicateCustomsClearanceInvoicePresent = replicaCustomsClearanceInvoiceRepository.existsByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(addCustomsClearanceInvoice.getLanguageId(), addCustomsClearanceInvoice.getCompanyId(), addCustomsClearanceInvoice.getPartnerHouseAirwayBill(), addCustomsClearanceInvoice.getHouseAirwayBill(), addCustomsClearanceInvoice.getInvoiceNo(), addCustomsClearanceInvoice.getDeletionIndicator());

            if (dbCustomsClearanceInvoice.isEmpty()) {
                throw new BadRequestException("LanguageId: " + addCustomsClearanceInvoice.getLanguageId() + ", CompanyId: " + addCustomsClearanceInvoice.getCompanyId() + ", PartnerHouseAirwayBill: " + addCustomsClearanceInvoice.getPartnerHouseAirwayBill() + ", HouseAirwayBill: " + addCustomsClearanceInvoice.getHouseAirwayBill() + ", InvoiceNo: " + addCustomsClearanceInvoice.getInvoiceNo() + " doesn't exist");
            } else if (duplicateCustomsClearanceInvoicePresent) {
                throw new BadRequestException("Record is getting duplicated with the given values.");
            } else {
                log.info("new CustomsClearanceInvoice --> " + addCustomsClearanceInvoice);

                IKeyValuePair iKeyValuePair = replicaCustomsClearanceInvoiceRepository.getDescription(addCustomsClearanceInvoice.getLanguageId(), addCustomsClearanceInvoice.getCompanyId());

                CustomsClearanceInvoice newCustomsClearanceInvoice = new CustomsClearanceInvoice();
                BeanUtils.copyProperties(addCustomsClearanceInvoice, newCustomsClearanceInvoice, CommonUtils.getNullPropertyNames(addCustomsClearanceInvoice));

                if (addCustomsClearanceInvoice.getInvoiceNo() != null) {

                    String NUM_RAN_OBJ ="CUSTOMS_CLEARANCE_INVOICE";
                    String INVOICE_NO = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for INVOICE_NO : " + INVOICE_NO);
                    newCustomsClearanceInvoice.setInvoiceNo(INVOICE_NO);
                }
                if (iKeyValuePair != null) {
                    newCustomsClearanceInvoice.setLanguageId(iKeyValuePair.getLangDesc());
                    newCustomsClearanceInvoice.setCompanyId(iKeyValuePair.getCompanyDesc());
                }

//                String statusDesc = replicaConsignmentStatusRepository.getStatusDescription(addCustomsClearanceInvoice.getStatusId());
//                if (statusDesc != null) {
//                    newCustomsClearanceInvoice.setStatusId(statusDesc);
//                }

                newCustomsClearanceInvoice.setDeletionIndicator(0L);
                newCustomsClearanceInvoice.setCreatedOn(new Date());
                newCustomsClearanceInvoice.setCreatedBy(loginUserID);
                newCustomsClearanceInvoice.setUpdatedBy(loginUserID);
                newCustomsClearanceInvoice.setUpdatedOn(new Date());
                return customsClearanceInvoiceRepository.save(newCustomsClearanceInvoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Create CustomsClearanceInvoice while updating hawbTypeId = 7 and Incoterm = DDU
     *
     * @param updateConsole
     * @param loginUserID
     */
    public void createCustomsClearance(Console updateConsole, String loginUserID) {

        CustomsClearanceInvoice clearanceInvoice = new CustomsClearanceInvoice();

        BeanUtils.copyProperties(updateConsole, clearanceInvoice, CommonUtils.getNullPropertyNames(updateConsole));

        boolean duplicateCustomsClearanceInvoicePresent = replicaCustomsClearanceInvoiceRepository.existsByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(clearanceInvoice.getLanguageId(), clearanceInvoice.getCompanyId(), clearanceInvoice.getPartnerHouseAirwayBill(), clearanceInvoice.getHouseAirwayBill(), clearanceInvoice.getInvoiceNo(), clearanceInvoice.getDeletionIndicator());

        if (duplicateCustomsClearanceInvoicePresent) {
            log.info("Record is getting Duplicated with the given values");
        } else {

            String INVOICE_NO = numberRangeService.getNextStringNumberRange("DDU_INVOICE");
            log.info("next Value from NumberRange for INVOICE_NO : " + INVOICE_NO);
            clearanceInvoice.setInvoiceNo(INVOICE_NO);

            Optional<IKeyValuePair> consignmentId = consignmentEntityRepository.consignmentId(clearanceInvoice.getPartnerHouseAirwayBill(), clearanceInvoice.getLanguageId(), clearanceInvoice.getCompanyId());

            if (consignmentId.isPresent()) {
                IKeyValuePair consignmentDetails = consignmentId.get();

                // Get DestinationDetails from Consignment
                if (consignmentDetails.getConsignmentId() != null) {
                    Optional<DestinationDetails> destinationDetails =
                            destinationDetailsRepository.findByDestinationDetailId(consignmentDetails.getConsignmentId());


                    if (destinationDetails.isPresent()) {
                        DestinationDetails dest = destinationDetails.get();
                        clearanceInvoice.setDestinationName(dest.getName());
                        clearanceInvoice.setDestinationAddress(updateConsole.getAddDestinationDetails());
                    }
                }

                // Get PaymentType from Consignment
                if (consignmentDetails.getPaymentType() != null) {
                    clearanceInvoice.setPaymentType(consignmentDetails.getPaymentType());
                }

                // Get SpecialApprovalCharge from Consignment
//                if (consignmentDetails.getSpecialApprovalCharge() != null) {
//                    clearanceInvoice.setSpecialApprovalValue(consignmentDetails.getSpecialApprovalCharge());
//                } else {
//                    clearanceInvoice.setSpecialApprovalValue(0.000);
//                }
            }

            Double costAmounts = replicaCustomsCostingRepository.costDescriptionAmount(clearanceInvoice.getLanguageId(),
                    clearanceInvoice.getCompanyId(), clearanceInvoice.getPartnerHouseAirwayBill());

            if (costAmounts != null) {
                clearanceInvoice.setSpecialApprovalValue(costAmounts);
            } else {
                clearanceInvoice.setSpecialApprovalValue(0.000);
            }


            String clearanceCharges = replicaCustomsCostingRepository.clearanceCharges(clearanceInvoice.getLanguageId(),
                    clearanceInvoice.getCompanyId());

            if (clearanceCharges != null && !clearanceCharges.isEmpty() && !clearanceCharges.equalsIgnoreCase("string")) {
                clearanceInvoice.setClearanceFee(Double.valueOf(clearanceCharges));
            } else {
                clearanceInvoice.setClearanceFee(5.000);
            }

            if (updateConsole.getTotalDuty() != null) {
                clearanceInvoice.setCustomsDuty(updateConsole.getTotalDuty());
            } else if (updateConsole.getTotalDuty() == null) {
                if (updateConsole.getCalculatedTotalDuty() != null) {
                    clearanceInvoice.setCustomsDuty(updateConsole.getCalculatedTotalDuty());
                } else {
                    clearanceInvoice.setCustomsDuty(0.000);
                }
            }

            Double totalFee = clearanceInvoice.getSpecialApprovalValue() + clearanceInvoice.getCustomsDuty() + clearanceInvoice.getClearanceFee();

            clearanceInvoice.setTotalFee(totalFee);

            clearanceInvoice.setDeletionIndicator(0L);
            clearanceInvoice.setCreatedOn(new Date());
            clearanceInvoice.setCreatedBy(loginUserID);
            clearanceInvoice.setUpdatedBy(loginUserID);
            clearanceInvoice.setUpdatedOn(new Date());
            customsClearanceInvoiceRepository.save(clearanceInvoice);
        }

    }

    /**
     *
     * Update CustomsClearanceInvoice
     *
     * @param languageId
     * @param companyId
     * @param partnerHouseAirwayBill
     * @param houseAirwayBill
     * @param invoiceNo
     * @param loginUserID
     * @param updateCustomsClearanceInvoice
     * @return
     */
    public CustomsClearanceInvoice updateCustomsClearanceInvoice(String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo, String loginUserID, UpdateCustomsClearanceInvoice updateCustomsClearanceInvoice) throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        try {
            CustomsClearanceInvoice dbCustomsClearanceInvoice = getCustomsClearanceInvoice(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo);

            BeanUtils.copyProperties(updateCustomsClearanceInvoice, dbCustomsClearanceInvoice, CommonUtils.getNullPropertyNames(updateCustomsClearanceInvoice));

            dbCustomsClearanceInvoice.setUpdatedBy(loginUserID);
            dbCustomsClearanceInvoice.setUpdatedOn(new Date());
            return customsClearanceInvoiceRepository.save(dbCustomsClearanceInvoice);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * Delete CustomsClearanceInvoice
     *
     * @param languageId
     * @param companyId
     * @param partnerHouseAirwayBill
     * @param houseAirwayBill
     * @param invoiceNo
     * @param loginUserID
     */
    public void deleteCustomsClearanceInvoice(String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo, String loginUserID) {

        CustomsClearanceInvoice dbCustomsClearanceInvoice = getCustomsClearanceInvoice(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo);

        if (dbCustomsClearanceInvoice != null) {
            dbCustomsClearanceInvoice.setUpdatedOn(new Date());
            dbCustomsClearanceInvoice.setUpdatedBy(loginUserID);
            dbCustomsClearanceInvoice.setDeletionIndicator(1L);
            customsClearanceInvoiceRepository.save(dbCustomsClearanceInvoice);
        } else {
            throw new BadRequestException("Error in Deleting InvoiceNo: " + invoiceNo);
        }
    }

    /*============================================REPLICA======================================*/

    /**
     * Get All CustomsClearanceInvoice
     *
     * @return
     */
    public List<ReplicaCustomsClearanceInvoice> getAllCustomsClearanceInvoice() {
        List<ReplicaCustomsClearanceInvoice> customsClearanceInvoiceList = replicaCustomsClearanceInvoiceRepository.findAll();

        customsClearanceInvoiceList = customsClearanceInvoiceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return customsClearanceInvoiceList;
    }

    /**
     * Get ReplicaCustomsClearanceInvoice
     *
     * @param languageId
     * @param companyId
     * @param partnerHouseAirwayBill
     * @param houseAirwayBill
     * @param invoiceNo
     * @return
     */
    public ReplicaCustomsClearanceInvoice replicaGetCustomsClearanceInvoice(String languageId, String companyId, String partnerHouseAirwayBill, String houseAirwayBill, String invoiceNo) {
        Optional<ReplicaCustomsClearanceInvoice> dbCustomsClearanceInvoice = replicaCustomsClearanceInvoiceRepository.findByLanguageIdAndCompanyIdAndPartnerHouseAirwayBillAndHouseAirwayBillAndInvoiceNoAndDeletionIndicator(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo, 0L);

        if (dbCustomsClearanceInvoice.isEmpty()) {
            String errMsg ="The given values - languageId: " + languageId + ", companyId: " + companyId + ", partnerHouseAirwayBill: " + partnerHouseAirwayBill + ", houseAirwayBill: " + houseAirwayBill + ", invoiceNo: " + invoiceNo + " doesn't exist.";

            throw new RuntimeException(errMsg);
        }
        return dbCustomsClearanceInvoice.get();
    }

    /**
     *
     * Find CustomsClearanceInvoice
     *
     * @param findCustomsClearanceInvoice
     * @return
     */
    public List<ReplicaCustomsClearanceInvoice> findCustomsClearanceInvoice(FindCustomsClearanceInvoice findCustomsClearanceInvoice) {

        ReplicaCustomsClearanceInvoiceSpecification spec = new ReplicaCustomsClearanceInvoiceSpecification(findCustomsClearanceInvoice);
        List<ReplicaCustomsClearanceInvoice> results = replicaCustomsClearanceInvoiceRepository.findAll(spec);
        log.info("Found CustomsClearanceInvoices ----> " + results);
        return results;
    }

}
