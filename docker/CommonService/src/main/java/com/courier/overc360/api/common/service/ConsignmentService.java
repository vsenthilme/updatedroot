package com.courier.overc360.api.common.service;

import com.courier.overc360.api.common.controller.exception.BadRequestException;
import com.courier.overc360.api.common.model.consignment.*;
import com.courier.overc360.api.common.repository.*;
import com.courier.overc360.api.common.util.CommonUtils;
import com.courier.overc360.api.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ConsignmentService {
    @Autowired
    private ConsignmentRepository consignmentRepository;

    @Autowired
    DestinationRepository destinationDetailsRepository;

    @Autowired
    OriginRepository originDetailsRepository;

    @Autowired
    ReturnDetailsRepository returnDetailsRepository;

    @Autowired
    PieceDetailsRepository pieceDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @Autowired
    ReferenceImageRepository referenceImageRepository;

    /**
     *
     * @param consignmentList
     * @param loginUserId
     * @param masterAirwayBill
     */
    public void createConsignment(List<AddConsignment> consignmentList, String loginUserId, String masterAirwayBill) {
        log.info("cassandra save Ititiated:---> " + consignmentList.size() + "," + masterAirwayBill);
        if(consignmentList != null && !consignmentList.isEmpty()) {
            for (AddConsignment consignment : consignmentList) {
                if (consignment != null) {
                    Consignment newConsignment = new Consignment();
                    BeanUtils.copyProperties(consignment, newConsignment, CommonUtils.getNullPropertyNames(consignment));
                    newConsignment.setDeletionIndicator(0L);
                    newConsignment.setCreatedBy(loginUserId);
                    newConsignment.setMasterAirwayBill(masterAirwayBill);
                    newConsignment.setCreatedOn(new Date());
                    newConsignment.setConsignmentId(System.currentTimeMillis());
                    Consignment createdConsignment = consignmentRepository.save(newConsignment);
                    if (consignment.getOriginDetails() != null) {
                        OriginDetails originDetails = consignment.getOriginDetails();
                        OriginDetailsCompositeKey originDetailsCompositeKey = new OriginDetailsCompositeKey();
                        originDetailsCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                        originDetailsCompositeKey.setOriginId(1L);
                        originDetails.setKey(originDetailsCompositeKey);
                        originDetailsRepository.save(originDetails);
                    }
                    if (consignment.getDestinationDetails() != null) {
                        DestinationDetails destinationDetails = consignment.getDestinationDetails();
                        DestinationDetailsCompositeKey destinationDetailsCompositeKey = new DestinationDetailsCompositeKey();
                        destinationDetailsCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                        destinationDetailsCompositeKey.setDestinationDetailId(1L);
                        destinationDetails.setKey(destinationDetailsCompositeKey);
                        destinationDetailsRepository.save(destinationDetails);
                    }
                    if (consignment.getReturnDetails() != null) {
                        ReturnDetails returnDetails = consignment.getReturnDetails();
                        ReturnDetailsCompositeKey returnDetailsCompositeKey = new ReturnDetailsCompositeKey();
                        returnDetailsCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                        returnDetailsCompositeKey.setReturnId(1L);
                        returnDetails.setKey(returnDetailsCompositeKey);
                        returnDetailsRepository.save(returnDetails);
                    }
                    if (consignment.getReferenceImageList() != null && !consignment.getReferenceImageList().isEmpty()) {
                        Long imgId = 0L;
                        for (ReferenceImageList referenceImageList : consignment.getReferenceImageList()) {
                            imgId++;
                            BeanUtils.copyProperties(consignment, referenceImageList, CommonUtils.getNullPropertyNames(consignment));
                            ReferenceImageListCompositeKey referenceImageListCompositeKey = new ReferenceImageListCompositeKey();
                            referenceImageListCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                            referenceImageListCompositeKey.setImageRefId(imgId);
                            referenceImageListCompositeKey.setTypeId("consignment");
                            referenceImageList.setKey(referenceImageListCompositeKey);
                            referenceImageRepository.save(referenceImageList);
                        }
                    }
                    if (consignment.getPieceDetails() != null && !consignment.getPieceDetails().isEmpty()) {
                        Long pieceId = 0L;
                        for (AddPieceDetails pieceDetails : consignment.getPieceDetails()) {
                            pieceId++;
                            PieceDetails newPieceDetails = new PieceDetails();
                            BeanUtils.copyProperties(consignment, newPieceDetails, CommonUtils.getNullPropertyNames(consignment));
                            BeanUtils.copyProperties(pieceDetails, newPieceDetails, CommonUtils.getNullPropertyNames(pieceDetails));
                            newPieceDetails.setDeletionIndicator(0L);
                            newPieceDetails.setCreatedBy(loginUserId);
                            newPieceDetails.setMasterAirwayBill(masterAirwayBill);
                            newPieceDetails.setCreatedOn(new Date());
                            PieceDetailsCompositeKey pieceDetailsCompositeKey = new PieceDetailsCompositeKey();
                            pieceDetailsCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                            pieceDetailsCompositeKey.setPieceId(pieceId);
                            newPieceDetails.setKey(pieceDetailsCompositeKey);
                            pieceDetailsRepository.save(newPieceDetails);
                            if (pieceDetails.getReferenceImageList() != null && !pieceDetails.getReferenceImageList().isEmpty()) {
                                Long imgId = 0L;
                                for (ReferenceImageList referenceImageList : consignment.getReferenceImageList()) {
                                    imgId++;
                                    BeanUtils.copyProperties(consignment, referenceImageList, CommonUtils.getNullPropertyNames(consignment));
                                    ReferenceImageListCompositeKey referenceImageListCompositeKey = new ReferenceImageListCompositeKey();
                                    referenceImageListCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                                    referenceImageListCompositeKey.setImageRefId(imgId);
                                    referenceImageListCompositeKey.setTypeId("pieces");
                                    referenceImageList.setKey(referenceImageListCompositeKey);
                                    referenceImageRepository.save(referenceImageList);
                                }
                            }
                            if (pieceDetails.getItemDetails() != null && !pieceDetails.getItemDetails().isEmpty()) {
                                Long itemId = 0L;
                                for (AddItemDetails itemDetails : pieceDetails.getItemDetails()) {
                                    if (itemDetails.getReferenceImageList() != null && !itemDetails.getReferenceImageList().isEmpty()) {
                                        Long imgId = 0L;
                                        for (ReferenceImageList referenceImageList : consignment.getReferenceImageList()) {
                                            imgId++;
                                            BeanUtils.copyProperties(consignment, referenceImageList, CommonUtils.getNullPropertyNames(consignment));
                                            ReferenceImageListCompositeKey referenceImageListCompositeKey = new ReferenceImageListCompositeKey();
                                            referenceImageListCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                                            referenceImageListCompositeKey.setImageRefId(imgId);
                                            referenceImageListCompositeKey.setTypeId("item");
                                            referenceImageList.setKey(referenceImageListCompositeKey);
                                            referenceImageRepository.save(referenceImageList);
                                        }
                                    }
                                    itemId++;
                                    ItemDetails newItemDetails = new ItemDetails();
                                    BeanUtils.copyProperties(consignment, newItemDetails, CommonUtils.getNullPropertyNames(consignment));
                                    BeanUtils.copyProperties(itemDetails, newItemDetails, CommonUtils.getNullPropertyNames(itemDetails));
                                    ItemDetailsCompositeKey itemDetailsCompositeKey = new ItemDetailsCompositeKey();
                                    itemDetailsCompositeKey.setConsignmentId(createdConsignment.getConsignmentId());
                                    itemDetailsCompositeKey.setItemDetailId(itemId);
                                    newItemDetails.setKey(itemDetailsCompositeKey);
                                    newItemDetails.setPieceId(String.valueOf(pieceId));
                                    newItemDetails.setPieceItemId(String.valueOf(itemId));
                                    newItemDetails.setMasterAirwayBill(masterAirwayBill);
                                    newItemDetails.setCreatedBy(loginUserId);
                                    newItemDetails.setCreatedOn(new Date());
                                    newItemDetails.setDeletionIndicator(0L);
                                    itemDetailsRepository.save(newItemDetails);
                                }
                            }
                        }
                    }
                }
            }
        }
        log.info("data saved in cassandra dB successfully ");
    }
    /**
     *
     * @param findConsignment
     * @return
     */
    public List<AddConsignment> findConsignment(FindConsignment findConsignment) throws ParseException {

        if((findConsignment.getConsignmentId() == null || findConsignment.getConsignmentId().isEmpty()) &&
                (findConsignment.getMasterAirwayBill() == null || findConsignment.getMasterAirwayBill().isEmpty()) &&
                (findConsignment.getStartDate() == null && findConsignment.getEndDate() == null)) {
            throw new BadRequestException("Input parameter should be specified..!");
        }

        if(findConsignment.getStartDate() != null && findConsignment.getEndDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(findConsignment.getStartDate(), findConsignment.getEndDate());
            findConsignment.setStartDate(dates[0]);
            findConsignment.setEndDate(dates[1]);
        }
        log.info("find cassandra Inout: " + findConsignment);

        List<AddConsignment> dbAddConsignmentList = new ArrayList<>();
        List<Consignment> consignmentList = null;

        if(findConsignment.getStartDate() == null && findConsignment.getEndDate() == null) {
            consignmentList = consignmentRepository.findConsignment(findConsignment.getConsignmentId(), findConsignment.getMasterAirwayBill(), findConsignment.getStartDate(), findConsignment.getEndDate());
        }

        if(findConsignment.getStartDate() != null && findConsignment.getEndDate() != null) {
            if((findConsignment.getMasterAirwayBill() == null || findConsignment.getMasterAirwayBill().isEmpty()) &&
                    (findConsignment.getConsignmentId() == null || findConsignment.getConsignmentId().isEmpty())) {
                consignmentList = consignmentRepository.findByCreatedOnBetween(findConsignment.getStartDate(), findConsignment.getEndDate());
            }
            if((findConsignment.getMasterAirwayBill() == null || findConsignment.getMasterAirwayBill().isEmpty()) &&
                    (findConsignment.getConsignmentId() != null && !findConsignment.getConsignmentId().isEmpty())) {
                consignmentList = consignmentRepository.findByConsignmentIdInAndCreatedOnBetween(findConsignment.getConsignmentId(), findConsignment.getStartDate(), findConsignment.getEndDate());
            }
            if((findConsignment.getMasterAirwayBill() != null && !findConsignment.getMasterAirwayBill().isEmpty()) &&
                    (findConsignment.getConsignmentId() == null || findConsignment.getConsignmentId().isEmpty())) {
                consignmentList = consignmentRepository.findByMasterAirwayBillInAndCreatedOnBetween(findConsignment.getMasterAirwayBill(), findConsignment.getStartDate(), findConsignment.getEndDate());
            }
            if(findConsignment.getMasterAirwayBill() != null && !findConsignment.getMasterAirwayBill().isEmpty() &&
                    findConsignment.getConsignmentId() != null && !findConsignment.getConsignmentId().isEmpty()) {
                consignmentList = consignmentRepository.findByConsignmentIdInAndMasterAirwayBillInAndCreatedOnBetween(
                        findConsignment.getConsignmentId(), findConsignment.getMasterAirwayBill(),
                        findConsignment.getStartDate(), findConsignment.getEndDate());
            }
        }
        log.info("Consignment List : " + consignmentList.size());
        if(consignmentList != null && !consignmentList.isEmpty()) {
            for(Consignment consignment : consignmentList) {
                AddConsignment dbAddConsignment = new AddConsignment();
                BeanUtils.copyProperties(consignment, dbAddConsignment, CommonUtils.getNullPropertyNames(consignment));
                Optional<ReturnDetails> returnDetails = returnDetailsRepository.findReturnDetails(consignment.getConsignmentId());
                Optional<OriginDetails> originDetails = originDetailsRepository.findOriginDetails(consignment.getConsignmentId());
                Optional<DestinationDetails> destinationDetails = destinationDetailsRepository.findDestinationDetails(consignment.getConsignmentId());
                List<ReferenceImageList> consignmentReferenceImageLists = referenceImageRepository.findReferenceImageList(consignment.getConsignmentId(), "consignment");
                List<PieceDetails> pieceDetails = pieceDetailsRepository.findPiecesDetails(consignment.getConsignmentId());
                List<AddPieceDetails> addPieceDetails = new ArrayList<>();
                if (pieceDetails != null && !pieceDetails.isEmpty()) {
                    for (PieceDetails piece : pieceDetails) {
                        AddPieceDetails addPieceDetail = new AddPieceDetails();
                        BeanUtils.copyProperties(piece, addPieceDetail, CommonUtils.getNullPropertyNames(piece));
                        List<ReferenceImageList> pieceReferenceImageLists = referenceImageRepository.findReferenceImageList(consignment.getConsignmentId(), "pieces");
                        addPieceDetail.setReferenceImageList(pieceReferenceImageLists);
                        List<ItemDetails> itemDetails = itemDetailsRepository.findItemDetails(String.valueOf(piece.getKey().getPieceId()));
                        List<AddItemDetails> addItemDetails = new ArrayList<>();
                        if (itemDetails != null && !itemDetails.isEmpty()) {
                            for (ItemDetails itemDetail : itemDetails) {
                                AddItemDetails addItemDetail = new AddItemDetails();
                                BeanUtils.copyProperties(itemDetail, addItemDetail, CommonUtils.getNullPropertyNames(itemDetail));
                                List<ReferenceImageList> itemReferenceImageLists = referenceImageRepository.findReferenceImageList(consignment.getConsignmentId(), "item");
                                addItemDetail.setReferenceImageList(itemReferenceImageLists);
                                addItemDetails.add(addItemDetail);
                            }
                        }
                        addPieceDetail.setItemDetails(addItemDetails);
                        addPieceDetails.add(addPieceDetail);
                    }
                }
                dbAddConsignment.setOriginDetails(originDetails.isPresent() ? originDetails.get() : null);
                dbAddConsignment.setDestinationDetails(destinationDetails.isPresent() ? destinationDetails.get() : null);
                dbAddConsignment.setReturnDetails(returnDetails.isPresent() ? returnDetails.get() : null);
                dbAddConsignment.setPieceDetails(addPieceDetails);
                dbAddConsignment.setReferenceImageList(consignmentReferenceImageLists);
                dbAddConsignmentList.add(dbAddConsignment);
            }
        }

        return dbAddConsignmentList;


    }
}