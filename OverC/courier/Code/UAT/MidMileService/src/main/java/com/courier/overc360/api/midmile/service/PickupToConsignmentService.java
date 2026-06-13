package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.RetailPrice;
import com.courier.overc360.api.midmile.primary.model.consignment.*;
import com.courier.overc360.api.midmile.primary.model.finance.Rate;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupFinance;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupPriceList;
import com.courier.overc360.api.midmile.primary.model.piecedetails.AddPieceDetails;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.repository.RateRepository;
import com.courier.overc360.api.midmile.primary.repository.RetailPriceRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class PickupToConsignmentService {

    @Autowired
    ConsignmentService consignmentService;

    @Autowired
    ConsignmentEntityRepository consignmentEntityRepository;

//    @Autowired
//    EmailService emailService;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    RetailPriceRepository retailPriceRepository;


    /**
     * @param pickupEntities
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    public List<ConsignmentEntity> createConsignmentEntity(List<PickupEntity> pickupEntities, String loginUserID)
            throws Exception {

        List<AddConsignment> consignmentEntityList = new ArrayList<>();
        try {
            pickupEntities.parallelStream().forEach(pickup -> {

                AddConsignment newCons = new AddConsignment();
                BeanUtils.copyProperties(pickup, newCons, CommonUtils.getNullPropertyNames(pickup));

                if (pickup.getDestinationDetails() != null) {
                    DestinationDetails newDest = new DestinationDetails();
                    BeanUtils.copyProperties(pickup.getDestinationDetails(), newDest);
                    newCons.setDestinationDetails(newDest);
                }
                if (pickup.getPickupDetails() != null) {
                    OriginDetails newOrigin = new OriginDetails();
                    BeanUtils.copyProperties(pickup.getPickupDetails(), newOrigin);
                    newCons.setOriginDetails(newOrigin);
                }
                long pieceCount = Long.parseLong(pickup.getPieceCount() != null ? pickup.getPieceCount() : "0");

                List<AddPieceDetails> pieceDetailsList = new ArrayList<>();

                for (int i = 0; i < pieceCount; i++) {
                    AddPieceDetails newPiece = new AddPieceDetails();
                    newPiece.setCreatedOn(new Date());
                    newPiece.setCreatedBy(loginUserID);
                    pieceDetailsList.add(newPiece);
                }
                newCons.setHawbTypeId(pickup.getStatusId());
                newCons.setPieceDetails(pieceDetailsList);
                consignmentEntityList.add(newCons);
            });
            return consignmentService.createConsignmentEntity(consignmentEntityList, loginUserID);
        } catch (Exception e) {
            throw new RuntimeException("PickUp to Consignment Create Failed" + e.getMessage());
        }
    }


    // Consignment_Upload_Update
    public List<ConsignmentEntity> updateConsignment(List<ConsignmentDto> consignmentDtoList, String loginUserID) {
        List<ConsignmentEntity> consignmentEntityList = new ArrayList<>();
        consignmentDtoList.stream().forEach(con -> {
            log.info("Consignment_Update Input ----------->" + con);
            Optional<ConsignmentEntity> consignment = consignmentEntityRepository.findByHouseAirwayBillAndDeletionIndicator(con.getHouseAirwayBill(), 0L);
            if (consignment.isPresent()) {
                ConsignmentEntity uc = consignment.get();
                BeanUtils.copyProperties(con, uc, CommonUtils.getNullPropertyNames(con));
                uc.setUpdatedBy(loginUserID);
                uc.setUpdatedOn(new Date());
                consignmentEntityRepository.save(uc);
                consignmentEntityList.add(uc);
            } else {
                throw new BadRequestException("Given Values Doesn't exist HouseAirwayBill - " + con.getHouseAirwayBill());
            }
        });
        log.info("Update Successfully ------------" + consignmentDtoList);
        // Send email notification
        String fromEmail = "no-reply@overc360.com";
        String toEmail = "yogesh.m@tekclover.com";
        String subject = "Consignment Update Notification";
        String message = "The consignment update was successful for the provided records.";

//        emailService.sendEmail(fromEmail, toEmail, subject, message);
        return consignmentEntityList;
    }


    // Update Finance
    public UpdateFinance updateFinances(UpdateFinance updateFinance, String loginUserID) {

        int size = updateFinance.getHouseAirwayBill().size();
        double ceilingValue;
        double chargeableWeight;
        double frightCharge;
        double codCharge;
        double fulfilmentCharge;
        double rtoCharge;
        double asrCharge;
        double movementCharge;
        double truckCharge;
        double outboundClearance;

        if (updateFinance.getCeilingValue() != null) {
            ceilingValue = updateFinance.getCeilingValue() / size;
        } else {
            ceilingValue = 0.0;
        }
        if (updateFinance.getChargeableWeight() != null) {
            chargeableWeight = updateFinance.getChargeableWeight() / size;
        } else {
            chargeableWeight = 0.0;
        }
        if (updateFinance.getFrightCharge() != null) {
            frightCharge = updateFinance.getFrightCharge() / size;
        } else {
            frightCharge = 0.0;
        }
        if (updateFinance.getCodCharge() != null) {
            codCharge = updateFinance.getCodCharge() / size;
        } else {
            codCharge = 0.0;
        }
        if (updateFinance.getFulfilmentCharge() != null) {
            fulfilmentCharge = updateFinance.getFulfilmentCharge() / size;
        } else {
            fulfilmentCharge = 0.0;
        }
        if (updateFinance.getRtoCharge() != null) {
            rtoCharge = updateFinance.getRtoCharge() / size;
        } else {
            rtoCharge = 0.0;
        }
        if (updateFinance.getAsrCharge() != null) {
            asrCharge = updateFinance.getAsrCharge() / size;
        } else {
            asrCharge = 0.0;
        }
        if (updateFinance.getMovementCharge() != null) {
            movementCharge = updateFinance.getMovementCharge() / size;
        } else {
            movementCharge = 0.0;
        }
        if (updateFinance.getTruckCharge() != null) {
            truckCharge = updateFinance.getTruckCharge() / size;
        } else {
            truckCharge = 0.0;
        }
        if (updateFinance.getOutboundClearance() != null) {
            outboundClearance = updateFinance.getOutboundClearance() / size;
        } else {
            outboundClearance = 0.0;
        }
        // Update Consignment
        updateFinance.getHouseAirwayBill().forEach(houseAirwayBill -> {
            consignmentEntityRepository.updateFinance(houseAirwayBill, ceilingValue, chargeableWeight, frightCharge, codCharge,
                    fulfilmentCharge, rtoCharge, asrCharge, movementCharge, truckCharge, outboundClearance);
        });
        updateFinance.getHouseAirwayBill().forEach(house -> {
            Optional<ConsignmentEntity> cons = consignmentEntityRepository.findByHouseAirwayBillAndDeletionIndicator(house, 0L);
            if (cons.isPresent()) {
                ConsignmentEntity finance = cons.get();
                double totalLmd = finance.getFrightCharge() + finance.getCodCharge() + finance.getFulfilmentCharge() + finance.getRtoCharge() +
                        finance.getAsrCharge() + finance.getMovementCharge() + finance.getTruckCharge();
                finance.setTotalLmdCharges(totalLmd);
                consignmentEntityRepository.save(finance);
            }
        });

        return updateFinance;
    }


    // Pickup_finance
    public List<PickupPriceList> pickupPriceListList(List<PickupFinance> pickupFinances) {

        List<PickupPriceList> pickupPriceLists = new ArrayList<>();
        pickupFinances.forEach(pf -> {
            if (pf.getPartnerType().equalsIgnoreCase("Customer") || pf.getPartnerType().equalsIgnoreCase("retail")) {
                List<Rate> rateList = rateRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(pf.getCompanyId(), pf.getLanguageId(), pf.getCustomerId(), 0L);
                rateList.forEach(rate -> {
                    double ceilingValue = rate.getCeilingValue() != null ? rate.getCeilingValue() : 0.0;
                    double from = rate.getRangeFrom() != null ? rate.getRangeFrom() : 0.0;
                    double to = rate.getRangeTo() != null ? rate.getRangeTo() : 0.0;
                    double chargeWeight = calculateAdjustedChargeWeight(pf.getWeight(), ceilingValue);
                    if (rate.getRateParameterId().equalsIgnoreCase("4")) {
                        if (from <= chargeWeight && to >= chargeWeight) {
                            PickupPriceList newPickup = new PickupPriceList();
                            newPickup.setPartnerType(pf.getPartnerType());
                            newPickup.setCustomerId(pf.getCustomerId());
                            newPickup.setWeight(pf.getWeight());
                            newPickup.setChargeableWeight(chargeWeight);
                            newPickup.setCeilingValue(ceilingValue);
                            newPickup.setFrightCharge(rate.getRate());
                            pickupPriceLists.add(newPickup);
                        }
                    }
                    if (rate.getRateParameterId().equalsIgnoreCase("2")) {
                        PickupPriceList newPickup = new PickupPriceList();
                        newPickup.setPartnerType(pf.getPartnerType());
                        newPickup.setCustomerId(pf.getCustomerId());
                        newPickup.setWeight(pf.getWeight());
                        newPickup.setChargeableWeight(chargeWeight);
                        newPickup.setCeilingValue(ceilingValue);
                        newPickup.setFrightCharge(rate.getRate());
                        pickupPriceLists.add(newPickup);
                    }
                });
            }
            if (pf.getPartnerType().equalsIgnoreCase("OneTime")) {
                List<RetailPrice> retailPrices = retailPriceRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndDeletionIndicator(pf.getCompanyId(), pf.getLanguageId(), pf.getCustomerId(), 0L);
                retailPrices.forEach(rate -> {

                    double ceilingValue = rate.getCeilingValue() != null ? rate.getCeilingValue() : 0.0;
                    double from = rate.getRangeFrom() != null ? Double.parseDouble(rate.getRangeFrom()) : 0.0;
                    double to = rate.getRangeTo() != null ? Double.parseDouble(rate.getRangeTo()) : 0.0;
                    double chargeWeight = calculateAdjustedChargeWeight(pf.getWeight(), ceilingValue);
                    if (rate.getRateParameterId().equalsIgnoreCase("4")) {
                        if (from <= chargeWeight && to >= chargeWeight) {
                            PickupPriceList newPickup = new PickupPriceList();
                            newPickup.setChargeableWeight(chargeWeight);
                            newPickup.setCeilingValue(ceilingValue);
                            newPickup.setFrightCharge(Double.valueOf(rate.getRate()));
                            pickupPriceLists.add(newPickup);
                        }
                    }
                    if (rate.getRateParameterId().equalsIgnoreCase("2")) {
                        PickupPriceList newPickup = new PickupPriceList();
                        newPickup.setChargeableWeight(chargeWeight);
                        newPickup.setCeilingValue(ceilingValue);
                        newPickup.setFrightCharge(Double.valueOf(rate.getRate()));
                        pickupPriceLists.add(newPickup);
                    }
                });
            }
        });
        return pickupPriceLists;
    }

    // GetRoundOfValue
    public double calculateAdjustedChargeWeight(double totalPieceWeight, double ceilingValue) {
        double chargeWeight = totalPieceWeight + ceilingValue;
        if (totalPieceWeight == (int) totalPieceWeight) {
            return totalPieceWeight;
        }
        // Round to the nearest 0.5 increment
        return Math.round(chargeWeight * 2) / 2.0;
    }

}
