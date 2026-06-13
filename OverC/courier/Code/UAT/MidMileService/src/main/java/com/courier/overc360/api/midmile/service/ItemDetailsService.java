package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentCommonValues;
import com.courier.overc360.api.midmile.primary.model.consignment.FindConsignment;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.model.imagereference.AddImageReference;
import com.courier.overc360.api.midmile.primary.model.imagereference.ImageReference;
import com.courier.overc360.api.midmile.primary.model.itemdetails.AddItemDetails;
import com.courier.overc360.api.midmile.primary.model.itemdetails.ItemDetails;
import com.courier.overc360.api.midmile.primary.model.itemdetails.UpdateItemDetails;
import com.courier.overc360.api.midmile.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.midmile.primary.repository.ImageReferenceRepository;
import com.courier.overc360.api.midmile.primary.repository.ItemDetailsRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.dto.FindPreAlertManifest;
import com.courier.overc360.api.midmile.replica.model.dto.PreAlertManifestImpl;
import com.courier.overc360.api.midmile.replica.model.itemdetails.ReplicaItemDetails;
import com.courier.overc360.api.midmile.replica.repository.ReplicaBondedManifestRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCcrRepository;
//import com.courier.overc360.api.midmile.replica.repository.ReplicaItemDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaItemDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaItemDetailsSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemDetailsService {

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ImageReferenceService imageReferenceService;

    @Autowired
    ImageReferenceRepository imageReferenceRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    ReplicaBondedManifestRepository replicaBondedManifestRepository;

    @Autowired
    ReplicaCcrRepository replicaCcrRepository;

    @Autowired
    ReplicaItemDetailsRepository replicaItemDetailsRepository;

    //Decimal Format
    DecimalFormat decimalFormat = new DecimalFormat("#.###");
    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get ItemDetails
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @return
     */
    public ItemDetails getItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                      String houseAirwayBill, String pieceId, String pieceItemId) {
        Optional<ItemDetails> dbItemDetails = itemDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndDeletionIndicator(
                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, 0L);
        if (dbItemDetails.isEmpty()) {
            // Error Log
            createItemDetailsLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId,
                    "The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + ", PartnerId: " + partnerId +
                            ", MasterAirwayBill: " + masterAirwayBill + ",HouseAirwayBill: " + houseAirwayBill +
                            ", PieceId: " + pieceId + " and PieceItemId: " + pieceItemId + " and doesn't exists");

            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + ", PartnerId: "
                    + partnerId + ", MasterAirwayBill: " + masterAirwayBill + ", HouseAirwayBill: " + houseAirwayBill + ", PieceId: " + pieceId +
                    " and PieceItemId: " + pieceItemId + " and doesn't exists");
        }
        return dbItemDetails.get();
    }

    /**
     * @param getItemDetails
     * @param updateItemDetails
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<ItemDetails> updateItemDetails(List<ItemDetails> getItemDetails, List<ItemDetails> updateItemDetails, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<ItemDetails> itemDetailsList = new ArrayList<>();
            for (ItemDetails itemDetails : updateItemDetails) {
                for (ItemDetails dbItem : getItemDetails) {
                    if (Objects.equals(itemDetails.getPieceItemId(), dbItem.getPieceItemId())) {
                        BeanUtils.copyProperties(itemDetails, dbItem, CommonUtils.getNullPropertyNames(itemDetails));
                        dbItem.setUpdatedBy(loginUserID);
                        dbItem.setUpdatedOn(new Date());
                        itemDetailsList.add(itemDetailsRepository.save(dbItem));
//                        itemDetailsList.add(dbItem);
                    }
                }
            }
            return itemDetailsList;
        } catch (Exception e) {
            // Error Log
//            createItemDetailsLog(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update ItemDetails
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param loginUserID
     * @param updateItemDetails
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public ItemDetails updateItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                         String houseAirwayBill, String pieceId, String pieceItemId,
                                         String loginUserID, UpdateItemDetails updateItemDetails)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ItemDetails dbItemDetails = getItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId);
            BeanUtils.copyProperties(updateItemDetails, dbItemDetails, CommonUtils.getNullPropertyNames(updateItemDetails));
            dbItemDetails.setUpdatedBy(loginUserID);
            dbItemDetails.setUpdatedOn(new Date());
            return itemDetailsRepository.save(dbItemDetails);
        } catch (Exception e) {
            // Error Log
            createItemDetailsLog(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete ItemDetails
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
    public void deleteItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                  String houseAirwayBill, String pieceId, String pieceItemId, String loginUserID) {
        ItemDetails dbItemDetails = getItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId);
        if (dbItemDetails != null) {
            dbItemDetails.setDeletionIndicator(1L);
            dbItemDetails.setUpdatedBy(loginUserID);
            dbItemDetails.setUpdatedOn(new Date());
            // MultipleImage
            imageReferenceService.deleteImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, loginUserID);
            itemDetailsRepository.save(dbItemDetails);
        } else {
            // Error Log
            createItemDetailsLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, "Error in deleting PieceItemId - " + pieceItemId);
            throw new BadRequestException("Error in deleting PieceItemId - " + pieceItemId);
        }
    }


    /**
     * Delete ItemDetails
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param loginUserID
     */
    public void deleteItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                  String houseAirwayBill, String pieceId, String loginUserID) {
        List<ItemDetails> dbItemDetails = itemDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, 0L);
        if (dbItemDetails != null) {
            for (ItemDetails itemDetails : dbItemDetails) {
                itemDetails.setDeletionIndicator(1L);
                itemDetails.setUpdatedBy(loginUserID);
                itemDetails.setUpdatedOn(new Date());

                //MultipleImage
                imageReferenceRepository.updateImageTable(itemDetails.getCompanyId(), itemDetails.getLanguageId(), itemDetails.getPartnerId(),
                        itemDetails.getHouseAirwayBill(), itemDetails.getMasterAirwayBill(), itemDetails.getPieceId(), loginUserID);
                itemDetailsRepository.save(itemDetails);
            }
        } else {
            // Error Log
            createItemDetailsLog3(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, "Error in deleting ItemId ");
            throw new BadRequestException("Error in deleting PieceId - " + pieceId);
        }
    }


    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param loginUserID
     */
    public void deleteItemDetails(String languageId, String companyId, String partnerId, String masterAirwayBill, String houseAirwayBill, String loginUserID) {

        List<ItemDetails> itemDetails = itemDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, 0L);

        if (itemDetails != null && !itemDetails.isEmpty()) {
            for (ItemDetails dbItemDetails : itemDetails) {
                if (dbItemDetails != null) {
                    dbItemDetails.setDeletionIndicator(1L);
                    dbItemDetails.setUpdatedBy(loginUserID);
                    dbItemDetails.setUpdatedOn(new Date());
                    //MultipleImage
                    imageReferenceRepository.updateImageTable(companyId, languageId, partnerId, houseAirwayBill, masterAirwayBill, loginUserID);
                    itemDetailsRepository.save(dbItemDetails);
                }
            }
        } else {
            for (ItemDetails details : itemDetails) {
                createItemDetailsLog1(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill,
                        details.getPieceId(), details.getPieceItemId(), "Error deleting ItemId ");
            }
        }


    }

    /**
     * Get All ItemDetails
     *
     * @return
     */
    public List<ReplicaItemDetails> getAllItemDetails() {
        List<ReplicaItemDetails> itemDetailsList = replicaItemDetailsRepository.findAll();
        itemDetailsList = itemDetailsList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return itemDetailsList;
    }

    //=============================================ItemDetails_ErrorLog=======================================================
    private void createItemDetailsLog(String languageId, String companyId, String partnerId, String
            masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId,
                                      String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(pieceItemId);
        errorLog.setReferenceField1(partnerId);
        errorLog.setReferenceField2(masterAirwayBill);
        errorLog.setReferenceField3(houseAirwayBill);
        errorLog.setReferenceField4(pieceId);
        errorLog.setMethod("Exception thrown in updateItemDetails");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @param error
     */
    private void createItemDetailsLog1(String languageId, String companyId, String partnerId, String
            masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(pieceItemId);
        errorLog.setReferenceField1(partnerId);
        errorLog.setReferenceField2(masterAirwayBill);
        errorLog.setReferenceField3(houseAirwayBill);
        errorLog.setReferenceField4(pieceId);
        errorLog.setMethod("Exception thrown in getItemDetails");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

//    private void createItemDetailsLog2(AddItemDetails addItemDetails, String error) throws IOException, CsvException {
//
//        List<ErrorLog> errorLogList = new ArrayList<>();
//        ErrorLog errorLog = new ErrorLog();
//        errorLog.setLogDate(new Date());
//        errorLog.setLanguageId(addItemDetails.getLanguageId());
//        errorLog.setCompanyId(addItemDetails.getCompanyId());
//        errorLog.setRefDocNumber(addItemDetails.getPieceItemId());
//        errorLog.setReferenceField1(addItemDetails.getPieceId());
//        errorLog.setReferenceField2(addItemDetails.getPartnerId());
//        errorLog.setReferenceField3(addItemDetails.getMasterAirwayBill());
//        errorLog.setReferenceField4(addItemDetails.getHouseAirwayBill());
//        errorLog.setMethod("Exception thrown in createItemDetails");
//        errorLog.setErrorMessage(error);
//        errorLog.setCreatedBy("Admin");
//        errorLogRepository.save(errorLog);
//        errorLogList.add(errorLog);
//        errorLogService.writeLog(errorLogList);
//    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param error
     */
    private void createItemDetailsLog3(String languageId, String companyId, String partnerId, String
            masterAirwayBill, String houseAirwayBill, String pieceId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(pieceId);
        errorLog.setReferenceField1(partnerId);
        errorLog.setReferenceField2(masterAirwayBill);
        errorLog.setReferenceField3(houseAirwayBill);
        errorLog.setReferenceField4(pieceId);
        errorLog.setMethod("Exception thrown in getItemDetails");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }


    /**
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<ItemDetails> createItemDetailsList(String pieceId, ConsignmentCommonValues commonValues, List<AddItemDetails> addItemDetailsList)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<ItemDetails> itemDetailsList = new ArrayList<>();
        try {
            Long itemDetails = 1L;
            Double totalLength = 0.0;
            Double totalWeight = 0.0;
            Double totalHeight = 0.0;
            Double totalVolume = 0.0;
            if (addItemDetailsList != null && !addItemDetailsList.isEmpty()) {
                for (AddItemDetails addItemDetails : addItemDetailsList) {
                    String PIECE_ITEM_ID = pieceId + String.format("%03d", itemDetails++);
                    ItemDetails newItemDetails = new ItemDetails();

                    BeanUtils.copyProperties(commonValues, newItemDetails, CommonUtils.getNullPropertyNames(commonValues));
                    //Add all item's length
                    Double itemLength = 0.0;
                    if (addItemDetails.getLength() != null) {
                        itemLength = addItemDetails.getLength();
                        totalLength = totalLength + itemLength;
                    }

                    //Add all item's weight
                    Double itemWeight = 0.0;
                    if (addItemDetails.getWeight() != null) {
                        itemWeight = addItemDetails.getWeight();
                        totalWeight = totalWeight + itemWeight;
                    }

                    //Add all item's height
                    Double itemHeight = 0.0;
                    if (addItemDetails.getHeight() != null) {
                        itemHeight = addItemDetails.getHeight();
                        totalHeight = totalHeight + itemHeight;
                    }

                    //Add all item's volume
                    Double itemVolume = 0.0;
                    if (addItemDetails.getVolume() != null) {
                        itemVolume = addItemDetails.getVolume();
                        totalVolume = totalVolume + itemVolume;
                    }

                    // Get Iatakd
                    replicaCcrRepository.getIataCurrencyValue(commonValues.getCompanyId(), commonValues.getLanguageId(),
                                    addItemDetails.getCurrency(), commonValues.getCountry())
                            .ifPresent(ikey -> {
                                addItemDetails.setExchangeRate(ikey.getCurrencyValue() != null ? Double.valueOf(ikey.getCurrencyValue()) : null);
                                addItemDetails.setIata(ikey.getIataKd() != null ? ikey.getIataKd() : null);
                            });

                    //DeclaredValue calculation
                    Double totalValue = 0.0;
                    if (addItemDetails.getUnitValue() != null && addItemDetails.getQuantity() != null) {
                        Double unitValue = addItemDetails.getUnitValue();
                        Double quantity = addItemDetails.getQuantity();
                        totalValue = unitValue * quantity;
                    }

                    addItemDetails.setDeclaredValue(totalValue);

                    //Hardcode CustomsCurrency
                    if (addItemDetails.getCustomsInsurance() == null) {
                        addItemDetails.setCustomsInsurance(1D);
                    }
                    //HardCode Duty
                    if (addItemDetails.getDuty() == null) {
                        addItemDetails.setDuty("5");
                    }

                    BeanUtils.copyProperties(addItemDetails, newItemDetails, CommonUtils.getNullPropertyNames(addItemDetails));

                    Double declaredValue = 0.0;
                    Double exchangeRate = 0.0;
                    Double consignmentValue = 0.0;

                    //Consignment Value
                    if (newItemDetails.getExchangeRate() != null) {
                        declaredValue = totalValue;
                        exchangeRate = newItemDetails.getExchangeRate();
                        consignmentValue = declaredValue * exchangeRate;
                    }

                    String formatConsignmentValue = decimalFormat.format(consignmentValue);
                    newItemDetails.setConsignmentValueLocal(Double.valueOf(formatConsignmentValue));
                    if (newItemDetails.getIata() != null) {
                        Double iata = newItemDetails.getIata();
                        newItemDetails.setAddIata(iata + consignmentValue);
                    }
                    if (newItemDetails.getAddIata() != null && newItemDetails.getCustomsInsurance() != null) {
                        Double addIata = newItemDetails.getAddIata();

                        Double addInsure = addIata * 0.01;
                        //Decimal Format
                        String formatInsurance = decimalFormat.format(addInsure);
                        newItemDetails.setAddInsurance(Double.valueOf(formatInsurance));

                        if (newItemDetails.getAddInsurance() != null) {
                            Double addInsurance = newItemDetails.getAddInsurance();
                            newItemDetails.setCustomsValue(addIata + addInsurance);

                            if (newItemDetails.getDuty() != null) {
                                Double duty = Double.valueOf(newItemDetails.getDuty());
                                Double customsValue = newItemDetails.getCustomsValue();

                                Double totalDuty = customsValue + (customsValue * 0.05);
                                //Decimal Format
                                String formatTotalDuty = decimalFormat.format(totalDuty);
                                newItemDetails.setCalculatedTotalDuty(Double.valueOf(formatTotalDuty));
                            }
                        }
                    }
                    //volume calculation
                    if ((newItemDetails.getLength() != null) &&
                            (newItemDetails.getWidth() != null) &&
                            (newItemDetails.getHeight() != null)) {
                        Double itemVolumeCalculation = newItemDetails.getLength() * newItemDetails.getWidth() * newItemDetails.getHeight();
                        newItemDetails.setVolume(itemVolumeCalculation);
                    }

                    newItemDetails.setConsignmentValue(consignmentValue);
                    newItemDetails.setPieceItemId(PIECE_ITEM_ID);
                    newItemDetails.setPieceId(pieceId);
                    if (addItemDetails.getHsCode() == null && commonValues.getHsCode() != null) {
                        newItemDetails.setHsCode(commonValues.getHsCode());
                    }

                    // CON_IMAGE
                    Set<ImageReference> imageReferenceSet = Optional.ofNullable(addItemDetails.getReferenceImageList())
                            .orElse(Collections.emptySet()).stream()
                            .map(imageReference -> {
                                String downloadDocument = commonService.downLoadDocument(imageReference.getReferenceImageUrl(), "document", "image");
                                if (downloadDocument != null) {
                                    return imageReferenceService.createImageReference(
                                            commonValues.getLanguageId(), commonValues.getCompanyId(), commonValues.getPartnerId(),
                                            commonValues.getPartnerName(), commonValues.getHouseAirwayBill(), commonValues.getMasterAirwayBill(),
                                            commonValues.getPartnerHouseAirwayBill(), commonValues.getPartnerMasterAirwayBill(), pieceId,
                                            PIECE_ITEM_ID, imageReference.getReferenceImageUrl(), "PI_ID", downloadDocument, commonValues.getCreatedBy());
                                }
                                return null;
                            }).filter(Objects::nonNull).collect(Collectors.toSet());

                    newItemDetails.setReferenceImageList(imageReferenceSet);
                    newItemDetails.setDeletionIndicator(0L);
                    newItemDetails.setCreatedOn(new Date());
                    newItemDetails.setUpdatedBy(null);
                    newItemDetails.setUpdatedOn(null);
//                        ItemDetails savedItemDetails = itemDetailsRepository.save(newItemDetails);
                    itemDetailsList.add(newItemDetails);
                }
//                }
            } else {
                String PIECE_ITEM_ID = pieceId + String.format("%03d", itemDetails++);
                ItemDetails newItemDetails = new ItemDetails();
                newItemDetails.setPieceItemId(PIECE_ITEM_ID);
                BeanUtils.copyProperties(commonValues, newItemDetails, CommonUtils.getNullPropertyNames(commonValues));
                newItemDetails.setPieceId(pieceId);
                if (commonValues.getVolume() != null) {
                    newItemDetails.setVolume(commonValues.getVolume());
                } else {
                    //volume calculation
                    if ((commonValues.getLength() != null) && (commonValues.getWidth() != null) && (commonValues.getHeight() != null)) {
                        Double itemVolumeCalculation = commonValues.getLength() * commonValues.getWidth() * commonValues.getHeight();
                        newItemDetails.setVolume(itemVolumeCalculation);
                    }
                }
                newItemDetails.setDeletionIndicator(0L);
                newItemDetails.setCreatedOn(new Date());
                newItemDetails.setUpdatedBy(null);
                newItemDetails.setUpdatedOn(null);

//                ItemDetails savedItemDetails = itemDetailsRepository.save(newItemDetails);
                itemDetailsList.add(newItemDetails);
            }
            itemDetailsRepository.updatePiece(commonValues.getCompanyId(), commonValues.getLanguageId(), pieceId, commonValues.getHouseAirwayBill(),
                    commonValues.getMasterAirwayBill(), String.valueOf(totalLength),
                    String.valueOf(totalHeight), String.valueOf(totalWeight), String.valueOf(totalVolume));
        } catch (Exception e) {
            // Error Log
            for (AddItemDetails itemDetails : addItemDetailsList) {
//                createItemDetailsLog2(itemDetails, e.toString());
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return itemDetailsList;
    }


    /**
     * Get Item Details
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param masterAirwayBill
     * @param houseAirwayBill
     * @param pieceId
     * @param pieceItemId
     * @return
     */
    public ReplicaItemDetails replicaGetItemDetails(String languageId, String companyId, String partnerId, String
            masterAirwayBill, String houseAirwayBill, String pieceId, String pieceItemId) {
        Optional<ReplicaItemDetails> dbItemDetails = replicaItemDetailsRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndPieceIdAndPieceItemIdAndDeletionIndicator(
                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, 0L);
        if (dbItemDetails.isEmpty()) {
            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + ", PartnerId: "
                    + partnerId + ", MasterAirwayBill: " + masterAirwayBill + ", HouseAirwayBill: " + houseAirwayBill + ", PieceId: " + pieceId + "and PieceItemId: " + pieceItemId + " doesn't exists");
        }
        return dbItemDetails.get();
    }

    /**
     * for PreAlertManifest
     *
     * @param languageId
     * @param companyId
     * @param pieceId
     * @return
     */
    public List<ReplicaItemDetails> replicaGetItemDetails(String languageId, String companyId, String pieceId) {
        List<ReplicaItemDetails> dbItemDetails = replicaItemDetailsRepository.findByLanguageIdAndCompanyIdAndPieceIdAndDeletionIndicator(
                languageId, companyId, pieceId, 0L);
        if (dbItemDetails.isEmpty()) {
            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + ", PieceId: " + pieceId + " doesn't exists");
        }
        return dbItemDetails;
    }

    /**
     * Find ItemDetails
     *
     * @param findItemDetails
     * @return
     */
    public List<ReplicaItemDetails> findItemDetails(FindConsignment findItemDetails) {

        ReplicaItemDetailsSpecification spec = new ReplicaItemDetailsSpecification(findItemDetails);
        List<ReplicaItemDetails> results = replicaItemDetailsRepository.findAll(spec);
        log.info("found Cities --> " + results);
        return results;
    }

    /**
     * @param findPreAlertManifest
     * @return
     */
    public List<PreAlertManifestImpl> findPreAlertManifest(FindPreAlertManifest findPreAlertManifest) {
        if (findPreAlertManifest.getConsignmentId() != null && findPreAlertManifest.getConsignmentId().isEmpty()) {
            findPreAlertManifest.setConsignmentId(null);
        }
        if (findPreAlertManifest.getLanguageId() != null && findPreAlertManifest.getLanguageId().isEmpty()) {
            findPreAlertManifest.setLanguageId(null);
        }
        if (findPreAlertManifest.getCompanyId() != null && findPreAlertManifest.getCompanyId().isEmpty()) {
            findPreAlertManifest.setCompanyId(null);
        }
        if (findPreAlertManifest.getPartnerId() != null && findPreAlertManifest.getPartnerId().isEmpty()) {
            findPreAlertManifest.setPartnerId(null);
        }
        if (findPreAlertManifest.getStatusId() != null && findPreAlertManifest.getStatusId().isEmpty()) {
            findPreAlertManifest.setStatusId(null);
        }
        if (findPreAlertManifest.getConsoleIndicator() != null && findPreAlertManifest.getConsoleIndicator().isEmpty()) {
            findPreAlertManifest.setConsoleIndicator(null);
        }
        if (findPreAlertManifest.getManifestIndicator() != null && findPreAlertManifest.getManifestIndicator().isEmpty()) {
            findPreAlertManifest.setManifestIndicator(null);
        }
        log.info("PreAlert Manifest Input : " + findPreAlertManifest);
        List<PreAlertManifestImpl> results = null;
        if (findPreAlertManifest.getManifestIndicator() == null && findPreAlertManifest.getConsoleIndicator() == null) {
            results = replicaItemDetailsRepository.getPreAlertManifest(
                    findPreAlertManifest.getConsignmentId(),
                    findPreAlertManifest.getLanguageId(),
                    findPreAlertManifest.getCompanyId(),
                    findPreAlertManifest.getPartnerId(),
                    findPreAlertManifest.getStatusId());
            log.info("PreAlert Manifest result: " + results.size());
            return results;
        }

        if (findPreAlertManifest.getManifestIndicator() == null && findPreAlertManifest.getConsoleIndicator() != null && !findPreAlertManifest.getConsoleIndicator().isEmpty()) {
            findPreAlertManifest.setManifestIndicator(Collections.singletonList(0L));
        }
        if (findPreAlertManifest.getConsoleIndicator() == null && findPreAlertManifest.getManifestIndicator() != null && !findPreAlertManifest.getManifestIndicator().isEmpty()) {
            findPreAlertManifest.setConsoleIndicator(Collections.singletonList(0L));
        }

        log.info("PreAlert Manifest Input : " + findPreAlertManifest);
        if (findPreAlertManifest.getManifestIndicator() != null && findPreAlertManifest.getConsoleIndicator() != null) {
            results = replicaItemDetailsRepository.getPreAlertManifest(
                    findPreAlertManifest.getConsignmentId(),
                    findPreAlertManifest.getLanguageId(),
                    findPreAlertManifest.getCompanyId(),
                    findPreAlertManifest.getPartnerId(),
                    findPreAlertManifest.getStatusId(),
                    findPreAlertManifest.getConsoleIndicator(),
                    findPreAlertManifest.getManifestIndicator());
            log.info("PreAlert Manifest Output : " + results.size());
            return results;
        }
        return null;
    }


}

