package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.model.imagereference.AddImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.UpdateImageReference;
import com.courier.overc360.api.midmile.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.midmile.primary.repository.ImageReferenceRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.imagereference.ReplicaImageReference;
import com.courier.overc360.api.midmile.replica.repository.ReplicaImageReferenceRepository;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImageReferenceService {

    @Autowired
    private ImageReferenceRepository imageReferenceRepository;

    @Autowired
    private ReplicaImageReferenceRepository replicaImageReferenceRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get ImageReference
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param imageRefId
     * @return
     */
    public ImageReference getImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                            String houseAirwayBill, String pieceId, String pieceItemId, String imageRefId) {

        Optional<ImageReference> dbImageReference = imageReferenceRepository.
                findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndImageRefIdAndDeletionIndicator(
                        languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, 0L);
        if (dbImageReference.isEmpty()) {
            String errorMessage = "The given values : languageId - " + languageId
                    + ", companyId - " + companyId + ", partnerId - " + partnerId + ", masterAirwayBill - " + masterAirwayBill
                    + ", houseAirwayBill - " + houseAirwayBill + ", pieceId - " + pieceId + ", pieceItemId - " + pieceItemId
                    + " and ImageRefId - " + imageRefId + " and given values doesn't exists";
            createImageReferenceLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, errorMessage);
            throw new BadRequestException(errorMessage);
        }
        return dbImageReference.get();
    }

    /**
     * Create ImageReference
     *
     * @param addImageReference
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ImageReference createImageReference(AddImageReference addImageReference, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<ImageReference> duplicateImageReference = imageReferenceRepository.
                    findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndImageRefIdAndDeletionIndicator(
                            addImageReference.getLanguageId(), addImageReference.getCompanyId(), addImageReference.getPartnerId(),
                            addImageReference.getMasterAirwayBill(), addImageReference.getHouseAirwayBill(), addImageReference.getPieceId(), addImageReference.getPieceItemId(),
                            addImageReference.getImageRefId(), 0L);

            if (duplicateImageReference.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : imageRefId - " + addImageReference.getImageRefId());
            } else {
                log.info("new ImageReference --> " + addImageReference);
                IKeyValuePair iKeyValuePair = imageReferenceRepository.getDescription(addImageReference.getLanguageId(), addImageReference.getCompanyId());
                ImageReference newImageReference = new ImageReference();
                BeanUtils.copyProperties(addImageReference, newImageReference, CommonUtils.getNullPropertyNames(addImageReference));
                if (addImageReference.getImageRefId() == null || addImageReference.getImageRefId().isBlank()) {
                    String NUM_RAN_OBJ = "IMAGEREFERENCE";
                    String IMAGE_REF_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for IMAGE_REF_ID : " + IMAGE_REF_ID);
                    newImageReference.setImageRefId(IMAGE_REF_ID);
                }
                if (iKeyValuePair != null) {
                    newImageReference.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newImageReference.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                newImageReference.setDeletionIndicator(0L);
                newImageReference.setCreatedBy(loginUserID);
                newImageReference.setCreatedOn(new Date());
                newImageReference.setUpdatedBy(loginUserID);
                newImageReference.setUpdatedOn(new Date());
                return imageReferenceRepository.save(newImageReference);
            }
        } catch (Exception e) {
            createImageReferenceLog2(addImageReference, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerName
     * @param houseAirwayBill
     * @param masterAirwayBillNo
     * @param partnerHouseAirwayBill
     * @param partnerMasterAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param url
     * @param tableName
     * @param downloadUrl
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ImageReference createImageReference(String languageId, String companyId, String partnerId, String partnerName, String houseAirwayBill,
                                               String masterAirwayBillNo, String partnerHouseAirwayBill, String partnerMasterAirwayBill,
                                               String pieceId, String pieceItemId, String url, String tableName, String downloadUrl, String loginUserID) {
        try {
            ImageReference newImageReference = new ImageReference();
            String NUM_RAN_OBJ = "IMAGEREFERENCE";
            String IMAGE_REF_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
            log.info("next Value from NumberRange for IMAGE_REF_ID : " + IMAGE_REF_ID);
            newImageReference.setLanguageId(languageId);
            newImageReference.setCompanyId(companyId);
            newImageReference.setPartnerId(partnerId);
            newImageReference.setPartnerName(partnerName);
            newImageReference.setHouseAirwayBill(houseAirwayBill);
            newImageReference.setMasterAirwayBill(masterAirwayBillNo);
            newImageReference.setPartnerHouseAirwayBill(partnerHouseAirwayBill);
            newImageReference.setPartnerMasterAirwayBill(partnerMasterAirwayBill);
            newImageReference.setPieceId(pieceId);
            newImageReference.setPieceItemId(pieceItemId);
            newImageReference.setReferenceImageUrl(url);
            newImageReference.setImageRefId(IMAGE_REF_ID);
            newImageReference.setReferenceField1(tableName);
            newImageReference.setReferenceField2(downloadUrl);
            newImageReference.setDeletionIndicator(0L);
            newImageReference.setCreatedBy(loginUserID);
            newImageReference.setCreatedOn(new Date());
            newImageReference.setUpdatedBy(null);
            newImageReference.setUpdatedOn(null);
            return imageReferenceRepository.save(newImageReference);
        } catch (Exception e) {
//            createImageReferenceLog2(addImageReference, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update ImageReference
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param imageRefId
     * @param updateImageReference
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public ImageReference updateImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill,
                                               String pieceId, String pieceItemId, String imageRefId, UpdateImageReference updateImageReference, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ImageReference dbImageReference = getImageReference(languageId, companyId, partnerId, masterAirwayBill,
                    houseAirwayBill, pieceId, pieceItemId, imageRefId);

            BeanUtils.copyProperties(updateImageReference, dbImageReference, CommonUtils.getNullPropertyNames(updateImageReference));
            dbImageReference.setUpdatedBy(loginUserID);
            dbImageReference.setUpdatedOn(new Date());
            return imageReferenceRepository.save(dbImageReference);
        } catch (Exception e) {
            createImageReferenceLog(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete ImageReference
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param imageRefId
     * @param loginUserID
     */
    public void deleteImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                     String houseAirwayBill, String pieceId, String pieceItemId, String imageRefId, String loginUserID) {

        ImageReference dbImageReference = getImageReference(languageId, companyId, partnerId, masterAirwayBill,
                houseAirwayBill, pieceId, pieceItemId, imageRefId);
        if (dbImageReference != null) {
            dbImageReference.setDeletionIndicator(1L);
            dbImageReference.setUpdatedBy(loginUserID);
            dbImageReference.setUpdatedOn(new Date());
            imageReferenceRepository.save(dbImageReference);
        } else {
            createImageReferenceLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId,
                    pieceItemId, imageRefId, "Error in deleting imageRefId - " + imageRefId);
            throw new BadRequestException("Error in deleting imageRefId - " + imageRefId);
        }
    }

    /**
     * Delete ImageReference
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param loginUserID
     */
    public void deleteImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                     String houseAirwayBill, String loginUserID) {

        List<ImageReference> dbImageReference = imageReferenceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, 0L);
        try {
            if (dbImageReference != null) {
                for (ImageReference dbImage : dbImageReference) {
                    if (dbImage.getReferenceField1().equalsIgnoreCase("CON_ID")) {
                        dbImage.setDeletionIndicator(1L);
                        dbImage.setUpdatedBy(loginUserID);
                        dbImage.setUpdatedOn(new Date());
                        imageReferenceRepository.save(dbImage);
                    }
                }
            } else {
                log.info("Consignment Image Delete Doesn't exist");
            }
        } catch (Exception e) {
            for (ImageReference dbImage : dbImageReference) {
                createImageReferenceLog1(dbImage.getLanguageId(), dbImage.getCompanyId(), dbImage.getPartnerId(), dbImage.getMasterAirwayBill(),
                        dbImage.getHouseAirwayBill(), dbImage.getPieceId(), dbImage.getPieceItemId(), dbImage.getImageRefId(), " Error in ImageReference Delete" + e.getMessage());
//            throw new BadRequestException("Error in deleting imageRefId - " + imageRefId);
            }
        }
    }

    /**
     * Delete ImageReference
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param loginUserID
     */
    public void deleteImageReference(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                     String houseAirwayBill, String pieceId, String pieceItemId, String loginUserID) {

        List<ImageReference> imageReferenceList = imageReferenceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndDeletionIndicator(
                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, 0L);
        try {
            if (imageReferenceList != null) {
                for (ImageReference imageReference : imageReferenceList) {
                    imageReference.setDeletionIndicator(1L);
                    imageReference.setUpdatedBy(loginUserID);
                    imageReference.setUpdatedOn(new Date());
                    imageReferenceRepository.save(imageReference);
                }
            }
        } catch (Exception e) {
            for (ImageReference imageReference : imageReferenceList) {
                createImageReferenceLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId,
                        pieceItemId, imageReference.getImageRefId(), "Error in deleting imageRefId - " + imageReference.getImageRefId());
//                 BadRequestException("Error in deleting imageRefId - " + imageReference.getImageRefId());
            }
        }
    }

    /**
     * @param imageRefId
     * @param loginUserID
     */
    public void deleteImageReference(String imageRefId, String loginUserID) {

        ImageReference imageReference = imageReferenceRepository.findByImageRefIdAndDeletionIndicator(imageRefId, 0L);
        if (imageReference != null) {
            imageReference.setDeletionIndicator(1L);
            imageReference.setUpdatedBy(loginUserID);
            imageReference.setUpdatedOn(new Date());
            imageReferenceRepository.save(imageReference);
        } else {
            createImageReferenceLog1(imageRefId, "Error in deleting imageRefId - " + imageRefId);
            throw new BadRequestException("Error in deleting imageRefId - " + imageRefId);
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get all ImageReference
     *
     * @return
     */
    public List<ReplicaImageReference> getAllImageReferenceIds() {
        List<ReplicaImageReference> imageReferenceList = replicaImageReferenceRepository.findAll();
        imageReferenceList = imageReferenceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return imageReferenceList;
    }

    /**
     * Get ImageReference
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param imageRefId
     * @return
     */
    public ReplicaImageReference getReplicaImageReference(String languageId, String companyId, String
            partnerId, String masterAirwayBill,
                                                          String houseAirwayBill, String pieceId, String pieceItemId, String imageRefId) {

        Optional<ReplicaImageReference> dbImageReference = replicaImageReferenceRepository.
                findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndImageRefIdAndDeletionIndicator(
                        languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, 0L);
        if (dbImageReference.isEmpty()) {
            String errorMessage = "The given values : languageId - " + languageId
                    + ", companyId - " + companyId + ", partnerId - " + partnerId + ", masterAirwayBill - " + masterAirwayBill
                    + ", houseAirwayBill - " + houseAirwayBill + ", pieceId - " + pieceId + ", pieceItemId - " + pieceItemId
                    + " and ImageRefId - " + imageRefId + " and given values doesn't exists";
            createImageReferenceLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, errorMessage);
            throw new BadRequestException(errorMessage);
        }
        return dbImageReference.get();
    }

//    /**
//     * Find ImageReference
//     *
//     * @param findImageReference
//     * @return
//     * @throws ParseException
//     */
//    public List<ReplicaImageReference> findImageReference(FindImageReference findImageReference) throws ParseException {
//
//        ReplicaImageReferenceSpecification spec = new ReplicaImageReferenceSpecification(findImageReference);
//        List<ReplicaImageReference> results = replicaImageReferenceRepository.findAll(spec);
//        log.info("found ImageReferences --> " + results);
//        return results;
//    }

    //=============================================ImageReference_ErrorLog====================================================
    private void createImageReferenceLog(String languageId, String companyId, String partnerId, String
            masterAirwayBill, String houseAirwayBill,
                                         String pieceId, String pieceItemId, String imageRefId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(imageRefId);
        errorLog.setMethod("Exception thrown in updateImageReference");
        errorLog.setReferenceField1(partnerId);
        errorLog.setReferenceField2(masterAirwayBill);
        errorLog.setReferenceField3(houseAirwayBill);
        errorLog.setReferenceField4(pieceId);
        errorLog.setReferenceField5(pieceItemId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }


    //ImageReferenceLog
    private void createImageReferenceLog1(String imageRefId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
//        errorLog.setLanguageId(languageId);
//        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(imageRefId);
        errorLog.setMethod("Exception thrown in getImageReference");
//        errorLog.setReferenceField1(partnerId);
//        errorLog.setReferenceField2(masterAirwayBill);
//        errorLog.setReferenceField3(houseAirwayBill);
//        errorLog.setReferenceField4(pieceId);
//        errorLog.setReferenceField5(pieceItemId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createImageReferenceLog1(String languageId, String companyId, String partnerId, String
            masterAirwayBill, String houseAirwayBill,
                                          String pieceId, String pieceItemId, String imageRefId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(imageRefId);
        errorLog.setMethod("Exception thrown in getImageReference");
        errorLog.setReferenceField1(partnerId);
        errorLog.setReferenceField2(masterAirwayBill);
        errorLog.setReferenceField3(houseAirwayBill);
        errorLog.setReferenceField4(pieceId);
        errorLog.setReferenceField5(pieceItemId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createImageReferenceLog2(AddImageReference addImageReference, String error) throws
            IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addImageReference.getLanguageId());
        errorLog.setCompanyId(addImageReference.getCompanyId());
        errorLog.setRefDocNumber(addImageReference.getImageRefId());
        errorLog.setMethod("Exception thrown in createImageReference");
        errorLog.setReferenceField1(addImageReference.getPartnerId());
        errorLog.setReferenceField2(addImageReference.getMasterAirwayBill());
        errorLog.setReferenceField3(addImageReference.getHouseAirwayBill());
        errorLog.setReferenceField4(addImageReference.getPieceId());
        errorLog.setReferenceField5(addImageReference.getPieceItemId());
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}

