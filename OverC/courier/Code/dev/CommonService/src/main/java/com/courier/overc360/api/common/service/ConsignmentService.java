package com.courier.overc360.api.common.service;

import com.courier.overc360.api.common.controller.exception.BadRequestException;
import com.courier.overc360.api.common.model.consignment.*;
import com.courier.overc360.api.common.model.consignment.v2.*;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class ConsignmentService {
    @Autowired
    private ItemDetailsV2Repository itemDetailsV2Repository;
    @Autowired
    private PieceDetailsV2Repository pieceDetailsV2Repository;
    @Autowired
    private ImageV2Repository imageV2Repository;
    @Autowired
    private ConsignmentV2Repository consignmentV2Repository;
    @Autowired
    private ConsignmentRepository consignmentRepository;
    @Autowired
    private DynamicNativeQueryConsignmentV2 consignmentV2Repo;

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
     * @param consignmentList
     * @param loginUserId
     * @param masterAirwayBill
     */
    public void createConsignment(List<AddConsignment> consignmentList, String loginUserId, String masterAirwayBill) {
        try {
            log.info("raw data - cassandra save Ititiated:---> " + consignmentList.size() + "," + masterAirwayBill);
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
            log.info("raw data saved in cassandra dB successfully ");
        } catch (Exception e) {
            throw new BadRequestException("Exception while cassandra raw save : " + e);
        }
    }

    /**
     * create consignment after processing
     * @param consignmentList
     * @param masterAirwayBill
     */
    public void postConsignment(List<AddConsignmentV2> consignmentList, String masterAirwayBill) {

        log.info("cassandra processed save Initiated:---> " + consignmentList.size() + "," + masterAirwayBill);

        try {
            if (consignmentList != null && !consignmentList.isEmpty()) {

                List<CompletableFuture<Void>> futures = new ArrayList<>();
                // Create a thread pool with a fixed number of threads
                ExecutorService executor = Executors.newFixedThreadPool(2);
                // Batch processing using streams and CompletableFuture
                consignmentList.forEach(consignment -> {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        if (consignment != null) {
                            ConsignmentV2 newConsignment = new ConsignmentV2();
                            BeanUtils.copyProperties(consignment, newConsignment, CommonUtils.getNullPropertyNames(consignment));
                            if (consignment.getOriginDetails() != null) {
                                OriginDetails originDetails = consignment.getOriginDetails();
                                newConsignment.setOriginAddressHubCode(originDetails.getAddressHubCode());
                                newConsignment.setOriginAccountId(originDetails.getAccountId());
                                newConsignment.setOriginEmail(originDetails.getEmail());
                                newConsignment.setOriginCompanyName(originDetails.getCompanyName());
                                newConsignment.setOriginName(originDetails.getName());
                                newConsignment.setOriginPhone(originDetails.getPhone());
                                newConsignment.setOriginAlternatePhone(originDetails.getAlternatePhone());
                                newConsignment.setOriginAddressLine1(originDetails.getAddressLine1());
                                newConsignment.setOriginAddressLine2(originDetails.getAddressLine2());
                                newConsignment.setOriginPinCode(originDetails.getPinCode());
                                newConsignment.setOriginDistrict(originDetails.getDistrict());
                                newConsignment.setOriginCity(originDetails.getCity());
                                newConsignment.setOriginState(originDetails.getState());
                                newConsignment.setOriginCountry(originDetails.getCountry());
                                newConsignment.setOriginLatitude(originDetails.getLatitude());
                                newConsignment.setOriginLongitude(originDetails.getLongitude());
                            }
                            if (consignment.getDestinationDetails() != null) {
                                DestinationDetails destinationDetails = consignment.getDestinationDetails();
                                newConsignment.setDestinationAddressHubCode(destinationDetails.getAddressHubCode());
                                newConsignment.setDestinationAccountId(destinationDetails.getAccountId());
                                newConsignment.setDestinationEmail(destinationDetails.getEmail());
                                newConsignment.setDestinationCompanyName(destinationDetails.getCompanyName());
                                newConsignment.setDestinationName(destinationDetails.getName());
                                newConsignment.setDestinationPhone(destinationDetails.getPhone());
                                newConsignment.setDestinationAlternatePhone(destinationDetails.getAlternatePhone());
                                newConsignment.setDestinationAddressLine1(destinationDetails.getAddressLine1());
                                newConsignment.setDestinationAddressLine2(destinationDetails.getAddressLine2());
                                newConsignment.setDestinationPinCode(destinationDetails.getPinCode());
                                newConsignment.setDestinationDistrict(destinationDetails.getDistrict());
                                newConsignment.setDestinationCity(destinationDetails.getCity());
                                newConsignment.setDestinationState(destinationDetails.getState());
                                newConsignment.setDestinationCountry(destinationDetails.getCountry());
                                newConsignment.setDestinationLatitude(destinationDetails.getLatitude());
                                newConsignment.setDestinationLongitude(destinationDetails.getLongitude());
                            }
                            if (consignment.getReturnDetails() != null) {
                                ReturnDetails returnDetails = consignment.getReturnDetails();
                                newConsignment.setReturnAddressHubCode(returnDetails.getAddressHubCode());
                                newConsignment.setReturnAccountId(returnDetails.getAccountId());
                                newConsignment.setReturnEmail(returnDetails.getEmail());
                                newConsignment.setReturnCompanyName(returnDetails.getCompanyName());
                                newConsignment.setReturnName(returnDetails.getName());
                                newConsignment.setReturnPhone(returnDetails.getPhone());
                                newConsignment.setReturnAlternatePhone(returnDetails.getAlternatePhone());
                                newConsignment.setReturnAddressLine1(returnDetails.getAddressLine1());
                                newConsignment.setReturnAddressLine2(returnDetails.getAddressLine2());
                                newConsignment.setReturnPinCode(returnDetails.getPinCode());
                                newConsignment.setReturnDistrict(returnDetails.getDistrict());
                                newConsignment.setReturnCity(returnDetails.getCity());
                                newConsignment.setReturnState(returnDetails.getState());
                                newConsignment.setReturnCountry(returnDetails.getCountry());
                                newConsignment.setReturnLatitude(returnDetails.getLatitude());
                                newConsignment.setReturnLongitude(returnDetails.getLongitude());
                            }
                            ConsignmentV2 createdConsignment = consignmentV2Repository.save(newConsignment);

                            if (consignment.getReferenceImageList() != null && !consignment.getReferenceImageList().isEmpty()) {
                                consignment.getReferenceImageList().stream().forEach(referenceImageList -> {
                                    ImageListV2 imageList = new ImageListV2();
                                    BeanUtils.copyProperties(consignment, imageList, CommonUtils.getNullPropertyNames(consignment));
                                    BeanUtils.copyProperties(referenceImageList, imageList, CommonUtils.getNullPropertyNames(referenceImageList));
                                    imageList.setConsignmentId(createdConsignment.getConsignmentId());
                                    imageList.setTypeId("consignment");
                                    imageV2Repository.save(imageList);
                                });
                            }
                            if (consignment.getPieceDetails() != null && !consignment.getPieceDetails().isEmpty()) {
                                consignment.getPieceDetails().stream().forEach(pieceDetails -> {
                                    PieceDetailsV2 newPieceDetails = new PieceDetailsV2();
                                    BeanUtils.copyProperties(consignment, newPieceDetails, CommonUtils.getNullPropertyNames(consignment));
                                    BeanUtils.copyProperties(pieceDetails, newPieceDetails, CommonUtils.getNullPropertyNames(pieceDetails));
                                    newPieceDetails.setConsignmentId(createdConsignment.getConsignmentId());
                                    pieceDetailsV2Repository.save(newPieceDetails);
                                    if (pieceDetails.getReferenceImageList() != null && !pieceDetails.getReferenceImageList().isEmpty()) {
                                        consignment.getReferenceImageList().stream().forEach(referenceImageList -> {
                                            ImageListV2 imageList = new ImageListV2();
                                            BeanUtils.copyProperties(consignment, imageList, CommonUtils.getNullPropertyNames(consignment));
                                            BeanUtils.copyProperties(referenceImageList, imageList, CommonUtils.getNullPropertyNames(referenceImageList));
                                            imageList.setConsignmentId(createdConsignment.getConsignmentId());
                                            imageList.setPieceDetailsId(newPieceDetails.getPieceDetailsId());
                                            imageList.setTypeId("pieces");
                                            imageV2Repository.save(imageList);
                                        });
                                    }
                                    if (pieceDetails.getItemDetails() != null && !pieceDetails.getItemDetails().isEmpty()) {
                                        pieceDetails.getItemDetails().stream().forEach(itemDetails -> {
                                            if (itemDetails.getReferenceImageList() != null && !itemDetails.getReferenceImageList().isEmpty()) {
                                                consignment.getReferenceImageList().stream().forEach(referenceImageList -> {
                                                    ImageListV2 imageList = new ImageListV2();
                                                    BeanUtils.copyProperties(consignment, imageList, CommonUtils.getNullPropertyNames(consignment));
                                                    BeanUtils.copyProperties(referenceImageList, imageList, CommonUtils.getNullPropertyNames(referenceImageList));
                                                    imageList.setConsignmentId(createdConsignment.getConsignmentId());
                                                    imageList.setPieceDetailsId(newPieceDetails.getPieceDetailsId());
                                                    imageList.setItemDetailsId(itemDetails.getItemDetailsId());
                                                    imageList.setTypeId("item");
                                                    imageV2Repository.save(imageList);
                                                });
                                            }
                                            ItemDetailsV2 newItemDetails = new ItemDetailsV2();
                                            BeanUtils.copyProperties(consignment, newItemDetails, CommonUtils.getNullPropertyNames(consignment));
                                            BeanUtils.copyProperties(itemDetails, newItemDetails, CommonUtils.getNullPropertyNames(itemDetails));
                                            newItemDetails.setPieceDetailsId(newPieceDetails.getPieceDetailsId());
                                            itemDetailsV2Repository.save(newItemDetails);
                                        });
                                    }
                                });
                            }
                        }
                    }, executor);
                    futures.add(future);
                });
                // Wait for all the futures to complete
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                // Shut down the executor service
                executor.shutdown();
            }
        } catch (Exception e) {
            log.error("Exception while saving cassandra" + e.toString());
        }
        log.info("processed data saved in cassandra dB successfully - " + masterAirwayBill);
    }

    /**
     * @param findConsignment
     * @return
     * @throws Exception
     */
    public List<ConsignmentV2> findConsignmentV2(FindConsignment findConsignment) throws Exception {

        try {
            Date[] dates;
            if (findConsignment.getStartDate() != null && findConsignment.getEndDate() != null) {
                dates = DateUtils.addTimeToDatesForSearch(findConsignment.getStartDate(), findConsignment.getEndDate());
            } else {
                dates = DateUtils.addTimeToDatesForSearch(new Date(), new Date());
            }
            findConsignment.setStartDate(dates[0]);
            findConsignment.setEndDate(dates[1]);
            log.info("find cassandra Inout: " + findConsignment);

            List<ConsignmentV2> consignmentV2List = consignmentV2Repo.findConsignment(
                    findConsignment.getConsignmentId(),
                    findConsignment.getPartnerId(),
                    findConsignment.getCompanyId(),
                    findConsignment.getLanguageId(),
                    findConsignment.getPieceId(),
                    findConsignment.getShipperId(),
                    findConsignment.getStatusId(),
                    findConsignment.getHouseAirwayBill(),
                    findConsignment.getPartnerHouseAirwayBill(),
                    findConsignment.getMasterAirwayBill(),
                    findConsignment.getPartnerMasterAirwayBill(),
                    findConsignment.getStartDate(),
                    findConsignment.getEndDate(), 0L);
            log.info("find cassandra Executed: " + consignmentV2List.size());
            return consignmentV2List;
        } catch (Exception e) {
            throw new BadRequestException("Exception during find : " + e);
        }
    }

    /**
     * @param findConsignment
     * @return
     * @throws Exception
     */
    public List<ConsignmentV2> findConsignmentV3(FindConsignment findConsignment) throws Exception {

        try {
            Date[] dates;
            if (findConsignment.getStartDate() != null && findConsignment.getEndDate() != null) {
                dates = DateUtils.addTimeToDatesForSearch(findConsignment.getStartDate(), findConsignment.getEndDate());
            } else {
                dates = DateUtils.addTimeToDatesForSearch(new Date(), new Date());
            }
            findConsignment.setStartDate(dates[0]);
            findConsignment.setEndDate(dates[1]);
            log.info("find cassandra Inout: " + findConsignment);

            List<ConsignmentV2> consignmentV2List = consignmentV2Repository.findByCreatedOnBetween(
                    findConsignment.getStartDate(), findConsignment.getEndDate());
            log.info("find cassandra Executed: " + consignmentV2List.size());
            return consignmentV2List;
        } catch (Exception e) {
            throw new BadRequestException("Exception during find : " + e);
        }
    }

    /**
     * @param findConsignment
     * @return
     */
    public List<AddConsignment> findConsignment(FindConsignment findConsignment) throws ParseException {

        try {
            if ((findConsignment.getConsignmentId() == null || findConsignment.getConsignmentId().isEmpty()) &&
                    (findConsignment.getMasterAirwayBill() == null || findConsignment.getMasterAirwayBill().isEmpty()) &&
                    (findConsignment.getStartDate() == null && findConsignment.getEndDate() == null)) {
                throw new BadRequestException("Input parameter should be specified..!");
            }

            if (findConsignment.getStartDate() != null && findConsignment.getEndDate() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearch(findConsignment.getStartDate(), findConsignment.getEndDate());
                findConsignment.setStartDate(dates[0]);
                findConsignment.setEndDate(dates[1]);
            }
            log.info("find cassandra Inout: " + findConsignment);

            List<AddConsignment> dbAddConsignmentList = new ArrayList<>();
            List<Consignment> consignmentList = null;

            if (findConsignment.getStartDate() == null && findConsignment.getEndDate() == null) {
                consignmentList = consignmentRepository.findConsignment(findConsignment.getConsignmentId(), findConsignment.getMasterAirwayBill(), findConsignment.getStartDate(), findConsignment.getEndDate());
            }

            if (findConsignment.getStartDate() != null && findConsignment.getEndDate() != null) {
                if ((findConsignment.getMasterAirwayBill() == null || findConsignment.getMasterAirwayBill().isEmpty()) &&
                        (findConsignment.getConsignmentId() == null || findConsignment.getConsignmentId().isEmpty())) {
                    consignmentList = consignmentRepository.findByCreatedOnBetween(findConsignment.getStartDate(), findConsignment.getEndDate());
                }
                if ((findConsignment.getMasterAirwayBill() == null || findConsignment.getMasterAirwayBill().isEmpty()) &&
                        (findConsignment.getConsignmentId() != null && !findConsignment.getConsignmentId().isEmpty())) {
                    consignmentList = consignmentRepository.findByConsignmentIdInAndCreatedOnBetween(findConsignment.getConsignmentId(), findConsignment.getStartDate(), findConsignment.getEndDate());
                }
                if ((findConsignment.getMasterAirwayBill() != null && !findConsignment.getMasterAirwayBill().isEmpty()) &&
                        (findConsignment.getConsignmentId() == null || findConsignment.getConsignmentId().isEmpty())) {
                    consignmentList = consignmentRepository.findByMasterAirwayBillInAndCreatedOnBetween(findConsignment.getMasterAirwayBill(), findConsignment.getStartDate(), findConsignment.getEndDate());
                }
                if (findConsignment.getMasterAirwayBill() != null && !findConsignment.getMasterAirwayBill().isEmpty() &&
                        findConsignment.getConsignmentId() != null && !findConsignment.getConsignmentId().isEmpty()) {
                    consignmentList = consignmentRepository.findByConsignmentIdInAndMasterAirwayBillInAndCreatedOnBetween(
                            findConsignment.getConsignmentId(), findConsignment.getMasterAirwayBill(),
                            findConsignment.getStartDate(), findConsignment.getEndDate());
                }
            }
            log.info("Consignment List : " + consignmentList.size());
            if (consignmentList != null && !consignmentList.isEmpty()) {
                for (Consignment consignment : consignmentList) {
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
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }
}