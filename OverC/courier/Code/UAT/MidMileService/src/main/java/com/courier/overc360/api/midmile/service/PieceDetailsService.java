package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentCommonValues;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import com.courier.overc360.api.midmile.primary.model.itemdetails.AddItemDetails;
import com.courier.overc360.api.midmile.primary.model.itemdetails.ItemDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.AddPieceDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.PieceDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.UpdatePieceDetails;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.midmile.primary.repository.ImageReferenceRepository;
import com.courier.overc360.api.midmile.primary.repository.PieceDetailsRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.dto.LabelFormInput;
import com.courier.overc360.api.midmile.replica.model.dto.LabelFormOutput;
import com.courier.overc360.api.midmile.replica.model.piecedetails.FindPieceDetails;
import com.courier.overc360.api.midmile.replica.model.piecedetails.ReplicaPieceDetails;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPieceDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaPieceDetailsSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PieceDetailsService {

    @Autowired
    private PieceDetailsRepository pieceDetailsRepository;

    @Autowired
    private ReplicaPieceDetailsRepository replicaPieceDetailsRepository;

    @Autowired
    private ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    ItemDetailsService itemDetailsService;

    @Autowired
    ImageReferenceService imageReferenceService;

    @Autowired
    ImageReferenceRepository imageReferenceRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    ConsignmentStatusService consignmentStatusService;

    @Autowired
    ConsignmentService consignmentService;
    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @return
     */
    public PieceDetails getPieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                        String houseAirwayBill, String pieceId) {

        Optional<PieceDetails> dbPieceDetails = pieceDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator
                (languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, 0L);

        if (dbPieceDetails.isEmpty()) {
            // Error Log
//            createPieceDetailsLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, "The given values : languageId - " + languageId +
//                    ", companyId - " + companyId + " , partnerId - " + partnerId + ", MasterAirwayBill: " + masterAirwayBill +
//                    ", HouseAirwayBill: " + houseAirwayBill + " and PieceId: " + pieceId + "  doesn't exists");
            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + ", PartnerId: "
                    + partnerId + ", MasterAirwayBill: " + masterAirwayBill + ", HouseAirwayBill: " + houseAirwayBill + " and PieceId: " + pieceId + "  doesn't exists");
        }
        return dbPieceDetails.get();
    }

    /**
     * Create
     *
     * @param addPieceDetails
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public PieceDetails createPieceDetails(AddPieceDetails addPieceDetails, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {

            Optional<PieceDetails> duplicatePieceDetails = pieceDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator
                    (addPieceDetails.getLanguageId(), addPieceDetails.getCompanyId(), addPieceDetails.getPartnerId(),
                            addPieceDetails.getMasterAirwayBill(), addPieceDetails.getHouseAirwayBill(), addPieceDetails.getPieceId(), 0l);

            if (duplicatePieceDetails.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : pieceId - " + addPieceDetails.getPieceId());
            } else {
                log.info("new PieceDetails --> " + addPieceDetails);
//                IKeyValuePair iKeyValuePair = replicaPieceDetailsRepository.getDescription(
//                        addPieceDetails.getLanguageId(), addPieceDetails.getCompanyId());

                PieceDetails newPieceDetails = new PieceDetails();
                BeanUtils.copyProperties(addPieceDetails, newPieceDetails, CommonUtils.getNullPropertyNames(addPieceDetails));
                if (addPieceDetails.getPieceId() == null || addPieceDetails.getPieceId().isBlank()) {
                    String NUM_RAN_OBJ = "PIECEDETAILS";
                    String PIECE_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for PIECE_ID : " + PIECE_ID);
                    newPieceDetails.setPieceId(PIECE_ID);
                }
//                if (iKeyValuePair != null) {
//                    newPieceDetails.setLanguageDescription(iKeyValuePair.getLangDesc());
//                    newPieceDetails.setCompanyName(iKeyValuePair.getCompanyDesc());
//                }

                newPieceDetails.setDeletionIndicator(0L);
                newPieceDetails.setCreatedBy(loginUserID);
                newPieceDetails.setCreatedOn(new Date());
                newPieceDetails.setUpdatedBy(loginUserID);
                newPieceDetails.setUpdatedOn(new Date());
                return pieceDetailsRepository.save(newPieceDetails);
            }
        } catch (Exception e) {
            // Error Log
//            createPieceDetailsLog2(addPieceDetails, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Update
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param updatePieceDetails
     * @param loginUserID
     * @return
     */
    public PieceDetails updatePieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                           String pieceId, UpdatePieceDetails updatePieceDetails, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            PieceDetails dbPieceDetails = getPieceDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId);
            BeanUtils.copyProperties(updatePieceDetails, dbPieceDetails, CommonUtils.getNullPropertyNames(updatePieceDetails));
            dbPieceDetails.setUpdatedBy(loginUserID);
            dbPieceDetails.setUpdatedOn(new Date());
            return pieceDetailsRepository.save(dbPieceDetails);
        } catch (Exception e) {
            // Error Log
            createPieceDetailsLog(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Create
     *
     * @param addPieceDetailsList
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<PieceDetails> createPieceDetailsList(ConsignmentCommonValues commonValues, List<AddPieceDetails> addPieceDetailsList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<PieceDetails> pieceDetailsList = new ArrayList<>();
        try {
            Long pieceCounter = 1L;
            Double totalWeight = 0.0;
            if (addPieceDetailsList != null && !addPieceDetailsList.isEmpty()) {
                for (AddPieceDetails addPieceDetails : addPieceDetailsList) {

                    List<AddItemDetails> itemDetailsList = addPieceDetails.getItemDetails();
                    int itemCount = itemDetailsList != null ? itemDetailsList.size() : 0; // Count the item details

//                    Optional<PieceDetails> duplicatePieceDetails =
//                            pieceDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
//                                    commonValues.getLanguageId(), commonValues.getCompanyId(), commonValues.getPartnerId(), commonValues.getMasterAirwayBill(),
//                                    commonValues.getHouseAirwayBill(), addPieceDetails.getPieceId(), 0L);

//                    if (duplicatePieceDetails.isPresent()) {
//                        log.info("Record is getting Duplicated with the given values : pieceId - " + addPieceDetails.getPieceId());
//                    } else {

                    String PIECE_ID = commonValues.getHouseAirwayBill() + String.format("%03d", pieceCounter++);
                    PieceDetails newPieceDetails = new PieceDetails();
                    BeanUtils.copyProperties(commonValues, newPieceDetails, CommonUtils.getNullPropertyNames(commonValues));
                    BeanUtils.copyProperties(addPieceDetails, newPieceDetails, CommonUtils.getNullPropertyNames(addPieceDetails));

                    //Add all piece's weight
                    Double pieceWeight = addPieceDetails.getWeight();
                    if (pieceWeight != null) {
                        totalWeight = totalWeight + pieceWeight;
                    }
                    newPieceDetails.setPieceId(PIECE_ID);
                    //HawbPieceStatus
                    newPieceDetails.setPieceTypeId(commonValues.getHawbTypeId());
                    newPieceDetails.setPieceType(commonValues.getHawbType());
                    newPieceDetails.setPieceTypeDescription(commonValues.getHawbTypeDescription());
                    newPieceDetails.setPieceTimeStamp(new Date());
                    newPieceDetails.setTags(String.valueOf(itemCount));
                    if (commonValues.getHsCode() != null) {
                        newPieceDetails.setHsCode(commonValues.getHsCode());
                    }
                    newPieceDetails.setDeletionIndicator(0L);
                    newPieceDetails.setCreatedOn(new Date());
                    newPieceDetails.setUpdatedOn(new Date());

                    //ReferenceImage Create
                    // CON_IMAGE
                    Set<ImageReference> imageReferenceSet = Optional.ofNullable(addPieceDetails.getReferenceImageList())
                            .orElse(Collections.emptySet()).stream()
                            .map(imageReference -> {
                                String downloadDocument = commonService.downLoadDocument(imageReference.getReferenceImageUrl(), "document", "image");
                                if (downloadDocument != null) {
                                    return imageReferenceService.createImageReference(
                                            commonValues.getLanguageId(), commonValues.getCompanyId(), commonValues.getPartnerId(), commonValues.getPartnerName(),
                                            commonValues.getHouseAirwayBill(), commonValues.getMasterAirwayBill(), commonValues.getPartnerHouseAirwayBill(),
                                            commonValues.getPartnerMasterAirwayBill(), PIECE_ID, null, imageReference.getReferenceImageUrl(),
                                            "P_ID", downloadDocument, commonValues.getCreatedBy());
                                }
                                return null;
                            }).filter(Objects::nonNull).collect(Collectors.toSet());
                    newPieceDetails.setReferenceImageList(imageReferenceSet);

                    //ItemDetails Create
                    List<ItemDetails> itemDetails = itemDetailsService.createItemDetailsList(PIECE_ID, commonValues, addPieceDetails.getItemDetails());

                    Double totalItemVolume = itemDetails.stream()
                            .map(ItemDetails::getVolume)
                            .filter(n -> n != null)
                            .mapToDouble(a -> a)
                            .sum();
                    newPieceDetails.setVolume(totalItemVolume);

                    // Calculate the total declared value
                    Double pieceValue = 0.0;
                    Double consignmentLocalValue = 0.0;
                    Double addIata = 0.0;
                    Double addInsurance = 0.0;
                    Double customsValue = 0.0;
                    Double calculatedTotalDuty = 0.0;
                    String currency = null;
                    for (ItemDetails item : itemDetails) {
                        if (item.getDeclaredValue() != null && item.getConsignmentValueLocal() != null && item.getAddIata() != null &&
                                item.getAddInsurance() != null && item.getCustomsValue() != null && item.getCalculatedTotalDuty() != null) {
                            Double declaredValue = item.getDeclaredValue();
                            Double conLocalValue = item.getConsignmentValueLocal();
                            Double iataAdd = item.getAddIata();
                            Double insuranceAdd = item.getAddInsurance();
                            Double costomValue = item.getCustomsValue();
                            Double totalDuty = item.getCalculatedTotalDuty();

                            pieceValue += declaredValue;
                            consignmentLocalValue += conLocalValue;
                            addIata += iataAdd;
                            addInsurance += insuranceAdd;
                            customsValue += costomValue;
                            calculatedTotalDuty += totalDuty;
                            currency = item.getCurrency();
                        }
                    }
                    newPieceDetails.setPieceCurrency(currency);
                    newPieceDetails.setPieceValue(pieceValue);
                    newPieceDetails.setConsignmentValueLocal(consignmentLocalValue);
                    newPieceDetails.setAddIata(addIata);
                    newPieceDetails.setAddInsurance(addInsurance);
                    newPieceDetails.setCustomsValue(customsValue);
                    newPieceDetails.setCalculatedTotalDuty(calculatedTotalDuty);
                    newPieceDetails.setItemDetails(itemDetails);
                    //Save PieceDetails
//                        PieceDetails savePieceDetails = pieceDetailsRepository.save(newPieceDetails);

                    // Save ConsignmentStatus
                    consignmentStatusService.insertConsignmentStatusRecord(newPieceDetails.getLanguageId(), newPieceDetails.getLanguageDescription(),
                            newPieceDetails.getCompanyId(), newPieceDetails.getCompanyName(), newPieceDetails.getPieceId(), newPieceDetails.getMasterAirwayBill(),
                            newPieceDetails.getHouseAirwayBill(), newPieceDetails.getPieceType(), newPieceDetails.getPieceTypeId(), newPieceDetails.getPieceTypeDescription(),
                            newPieceDetails.getPieceTimeStamp(), newPieceDetails.getPieceType(), newPieceDetails.getPieceTypeId(), newPieceDetails.getPieceTypeDescription(),
                            newPieceDetails.getPieceTimeStamp(), newPieceDetails.getCreatedBy(), newPieceDetails.getPartnerHouseAirwayBill(), newPieceDetails.getPartnerMasterAirwayBill(),
                            null, commonValues.getHubCode(), commonValues.getHubName());

                    pieceDetailsList.add(newPieceDetails);
                }
//                }
            } else {

                String PIECE_ID = commonValues.getHouseAirwayBill() + String.format("%03d", pieceCounter++);
                PieceDetails newPieceDetails = new PieceDetails();

                newPieceDetails.setPieceId(PIECE_ID);
                BeanUtils.copyProperties(commonValues, newPieceDetails);
                newPieceDetails.setTags("1");
                if (commonValues.getHsCode() != null) {
                    newPieceDetails.setHsCode(commonValues.getHsCode());
                }
                if (commonValues.getVolume() != null) {
                    newPieceDetails.setVolume(commonValues.getVolume());
                } else {
                    //volume calculation
                    if ((commonValues.getLength() != null) && (commonValues.getWidth() != null) && (commonValues.getHeight() != null)) {
                        Double itemVolumeCalculation = commonValues.getLength() * commonValues.getWidth() * commonValues.getHeight();
                        newPieceDetails.setVolume(itemVolumeCalculation);
                    }
                }

                newPieceDetails.setPieceTypeId(commonValues.getHawbTypeId());
                newPieceDetails.setPieceType(commonValues.getHawbType());
                newPieceDetails.setPieceTypeDescription(commonValues.getHawbTypeDescription());
                newPieceDetails.setPieceTimeStamp(new Date());
                newPieceDetails.setDeletionIndicator(0L);
                newPieceDetails.setCreatedOn(new Date());
                newPieceDetails.setUpdatedBy(null);
                newPieceDetails.setUpdatedOn(null);

                //ItemDetails Create
                List<ItemDetails> itemDetails = itemDetailsService.createItemDetailsList(PIECE_ID, commonValues, null);

                newPieceDetails.setItemDetails(itemDetails);
                //Save PieceDetails
                PieceDetails savePieceDetails = pieceDetailsRepository.save(newPieceDetails);

                // Save ConsignmentStatus
                consignmentStatusService.insertConsignmentStatusRecord(savePieceDetails.getLanguageId(), savePieceDetails.getLanguageDescription(),
                        savePieceDetails.getCompanyId(), savePieceDetails.getCompanyName(), savePieceDetails.getPieceId(), savePieceDetails.getMasterAirwayBill(),
                        savePieceDetails.getHouseAirwayBill(), savePieceDetails.getPieceType(), savePieceDetails.getPieceTypeId(), savePieceDetails.getPieceTypeDescription(),
                        savePieceDetails.getPieceTimeStamp(), savePieceDetails.getPieceType(), savePieceDetails.getPieceTypeId(), savePieceDetails.getPieceTypeDescription(),
                        savePieceDetails.getPieceTimeStamp(), commonValues.getCreatedBy(), savePieceDetails.getPartnerHouseAirwayBill(), savePieceDetails.getPartnerMasterAirwayBill(),
                        null, commonValues.getHubCode(), commonValues.getHubName());
            }

        } catch (Exception e) {
            for (AddPieceDetails addPieceDetails : addPieceDetailsList) {
                // Error Log
//                createPieceDetailsLog2(addPieceDetails, e.toString());
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
        return pieceDetailsList;
    }


    /**
     * @param getPieceDetails
     * @param updatePieceDetails
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public List<PieceDetails> updatePieceDetails(List<PieceDetails> getPieceDetails, List<PieceDetails> updatePieceDetails, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<PieceDetails> pieceDetailsList = new ArrayList<>();

        try {
            for (PieceDetails pieceDetails : updatePieceDetails) {
                for (PieceDetails dbPiece : getPieceDetails) {
                    if (Objects.equals(pieceDetails.getPieceId(), dbPiece.getPieceId())) {

                        BeanUtils.copyProperties(pieceDetails, dbPiece, CommonUtils.getNullPropertyNames(pieceDetails));

                        //Update ReferenceImage
                        Set<ImageReference> referenceImageLists = new HashSet<>();
                        if (pieceDetails.getReferenceImageList() != null && !pieceDetails.getReferenceImageList().isEmpty()) {
                            for (ImageReference image : pieceDetails.getReferenceImageList()) {

                                String downloadDocument = commonService.downLoadDocument(image.getReferenceImageUrl(), "document", "image");
                                ImageReference imageReferenceRecord = imageReferenceRepository.findByImageRefIdAndDeletionIndicator(image.getImageRefId(), 0L);
                                if (imageReferenceRecord == null) {
                                    log.info("ImageReference doesn't exist" + image.getImageRefId());
                                }
                                imageReferenceRecord.setReferenceImageUrl(image.getReferenceImageUrl());
                                imageReferenceRecord.setReferenceField2(downloadDocument);
                                imageReferenceRecord.setDeletionIndicator(0L);
                                imageReferenceRecord.setUpdatedBy(loginUserID);
                                imageReferenceRecord.setUpdatedOn(new Date());
                                ImageReference imageRef = imageReferenceRepository.save(imageReferenceRecord);
                                referenceImageLists.add(imageRef);
                            }
                        }
                        dbPiece.setReferenceImageList(referenceImageLists);

                        // UpdateItemDetails
                        if (pieceDetails.getItemDetails() != null && !pieceDetails.getItemDetails().isEmpty()) {
                            List<ItemDetails> dbItemDetails = itemDetailsService.updateItemDetails(dbPiece.getItemDetails(), pieceDetails.getItemDetails(), loginUserID);
                            dbPiece.setItemDetails(dbItemDetails);
                        }

                        dbPiece.setDeletionIndicator(0L);
                        dbPiece.setUpdatedBy(loginUserID);
                        dbPiece.setUpdatedOn(new Date());
                        pieceDetailsList.add(pieceDetailsRepository.save(dbPiece));
//                        pieceDetailsList.add(dbPiece);
                    }
                }
            }
        } catch (Exception e) {
            // Error Log
//            createPieceDetailsLog(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, "pieceDetails.getpi", e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return pieceDetailsList;
    }

    /**
     * Delete
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param loginUserID
     */
    public void deletePieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                   String pieceId, String loginUserID) {
        PieceDetails dbPieceDetails = getPieceDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId);
        if (dbPieceDetails != null) {
            dbPieceDetails.setDeletionIndicator(1L);
            dbPieceDetails.setUpdatedBy(loginUserID);
            dbPieceDetails.setUpdatedOn(new Date());

            //Multiple ImageDelete
            imageReferenceRepository.updateImageTable(companyId, languageId, partnerId, houseAirwayBill, masterAirwayBill, pieceId, loginUserID);

            //Delete ItemDetails
            itemDetailsService.deleteItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, loginUserID);
            pieceDetailsRepository.save(dbPieceDetails);

        } else {
            // Error Log
            createPieceDetailsLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, "Error in deleting PieceId - " + pieceId);
            throw new BadRequestException("Error in deleting PartnerId - " + partnerId);
        }
    }

    /**
     * Delete
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param loginUserID
     */
    public void deletePieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String loginUserID) {
        List<PieceDetails> pieceDetails = pieceDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, 0L);

        if (pieceDetails != null && !pieceDetails.isEmpty()) {
            for (PieceDetails dbPieceDetails : pieceDetails) {
                if (dbPieceDetails != null) {
                    dbPieceDetails.setDeletionIndicator(1L);
                    dbPieceDetails.setUpdatedBy(loginUserID);
                    dbPieceDetails.setUpdatedOn(new Date());

                    //MultipleItem Delete
                    itemDetailsService.deleteItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, loginUserID);

                    //MultipleImage Delete
                    imageReferenceRepository.updateImageTable(companyId, languageId, partnerId, houseAirwayBill, masterAirwayBill, loginUserID);

                    pieceDetailsRepository.save(dbPieceDetails);

                } else {
                    // Error Log
                    createPieceDetailsLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, dbPieceDetails.getPieceId(), "Error in deleting PieceId - " + dbPieceDetails.getPieceId());
                    throw new BadRequestException("Error in deleting PartnerId - " + partnerId);
                }
            }
        } else {
            log.info("PieceDetails Doesn't exists");
        }
    }

    /**
     * @param labelFormInput
     * @return
     */
    public List<LabelFormOutput> getLabelFormOutput(LabelFormInput labelFormInput) {

        log.info("labelFormInput : {}", labelFormInput);
        List<LabelFormOutput> labelFormOutputList = replicaPieceDetailsRepository.getLabelPdfOutput(
                labelFormInput.getLanguageId(),
                labelFormInput.getCompanyId(),
                labelFormInput.getPieceId(),
                labelFormInput.getHouseAirwayBill(),
                new Date());
        log.info("labelForm output: {}", labelFormOutputList.size());
        return labelFormOutputList;
    }

    /**
     * for PreAlertManifest
     *
     * @param languageId
     * @param companyId
     * @param consignmentId
     * @return
     */
    public List<ReplicaPieceDetails> getReplicaPieceDetailsForPreAlertManifest(String languageId, String companyId, Long consignmentId) {

        List<ReplicaPieceDetails> dbPieceDetails = replicaPieceDetailsRepository.getAllPieceDetails(languageId, companyId, consignmentId);

        if (dbPieceDetails == null || dbPieceDetails.isEmpty()) {
            createPieceDetailsLog(languageId, companyId, String.valueOf(consignmentId), "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + " and PieceId: " + consignmentId + "  doesn't exists");
            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + " and consignmentId: " + consignmentId + "  doesn't exists");
        }

        return dbPieceDetails;
    }

    /*=================================================REPLICA=============================================================*/

    /**
     * Get all
     *
     * @return
     */
    public List<ReplicaPieceDetails> getAllPieceDetails() {
        List<ReplicaPieceDetails> pieceDetailsList = replicaPieceDetailsRepository.findAll();
        pieceDetailsList = pieceDetailsList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return pieceDetailsList;
    }

    /**
     * Get
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @return
     */
    public ReplicaPieceDetails getReplicaPieceDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                                      String houseAirwayBill, String pieceId) {

        Optional<ReplicaPieceDetails> dbPieceDetails = replicaPieceDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator
                (languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, 0l);

        if (dbPieceDetails.isEmpty()) {
            createPieceDetailsLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + " , partnerId - " + partnerId + ", MasterAirwayBill: " + masterAirwayBill +
                    ", HouseAirwayBill: " + houseAirwayBill + " and PieceId: " + pieceId + "  doesn't exists");
            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + ", PartnerId: "
                    + partnerId + ", MasterAirwayBill: " + masterAirwayBill + ", HouseAirwayBill: " + houseAirwayBill + " and PieceId: " + pieceId + "  doesn't exists");
        }
        return dbPieceDetails.get();
    }

    /**
     * Find
     *
     * @param findPieceDetails
     * @return
     */
    public List<ReplicaPieceDetails> findPieceDetails(FindPieceDetails findPieceDetails) throws ParseException {
        ReplicaPieceDetailsSpecification spec = new ReplicaPieceDetailsSpecification(findPieceDetails);
        List<ReplicaPieceDetails> results = replicaPieceDetailsRepository.findAll(spec);
        log.info("found Piecedetails--> " + results);
        return results;
    }

    // Upload Update Piece_Details
    public List<PieceDetails> updatePieceDetailsList(List<PieceDetails> pieceDetails, String loginUserID) {
        List<PieceDetails> pieceDetailsList = new ArrayList<>();
        pieceDetails.forEach(piece -> {
            if (piece.getPieceId() != null) {
                Optional<PieceDetails> dbPiece = Optional.ofNullable(pieceDetailsRepository.findByPieceIdAndDeletionIndicator(
                        piece.getPieceId(), 0L));
                if (dbPiece.isPresent()) {
                    PieceDetails pd = dbPiece.get();
                    BeanUtils.copyProperties(piece, pd, CommonUtils.getNullPropertyNames(piece));
                    pd.setUpdatedBy(loginUserID);
                    pd.setUpdatedOn(new Date());
                    pieceDetailsList.add(pieceDetailsRepository.save(pd));
                } else {
                    throw new BadRequestException("Given Values Doesn't exist ------- PieceId " + dbPiece.get().getPieceId());
                }
            } else {
                throw new BadRequestException("PieceId is mandatory ");
            }
        });
        return pieceDetailsList;
    }

    //========================================PieceDetails_ErrorLog=================================================
    private void createPieceDetailsLog(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                       String houseAirwayBill, String pieceId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(pieceId);
        errorLog.setReferenceField1(partnerId);
        errorLog.setReferenceField2(masterAirwayBill);
        errorLog.setReferenceField3(houseAirwayBill);
        errorLog.setMethod("Exception thrown in updatePieceDetails");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createPieceDetailsLog1(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                        String houseAirwayBill, String pieceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(pieceId);
        errorLog.setReferenceField1(partnerId);
        errorLog.setReferenceField2(masterAirwayBill);
        errorLog.setReferenceField3(houseAirwayBill);
        errorLog.setMethod("Exception thrown in getPieceDetails");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createPieceDetailsLog(String languageId, String companyId, String pieceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(pieceId);
        errorLog.setMethod("Exception thrown in getPieceDetails");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createPieceDetailsLog2(AddPieceDetails addPieceDetails, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addPieceDetails.getLanguageId());
        errorLog.setCompanyId(addPieceDetails.getCompanyId());
        errorLog.setRefDocNumber(addPieceDetails.getPieceId());
        errorLog.setReferenceField1(addPieceDetails.getPartnerId());
        errorLog.setReferenceField2(addPieceDetails.getMasterAirwayBill());
        errorLog.setReferenceField3(addPieceDetails.getHouseAirwayBill());
        errorLog.setMethod("Exception thrown in createPieceDetails");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }


}


















