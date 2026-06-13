package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.b2b.Consignment;
import com.courier.overc360.api.midmile.primary.model.b2b.Destination_Details;
import com.courier.overc360.api.midmile.primary.model.b2b.Origin_Details;
import com.courier.overc360.api.midmile.primary.model.b2b.Pieces_Details;
import com.courier.overc360.api.midmile.primary.model.consignment.*;
import com.courier.overc360.api.midmile.primary.model.itemdetails.ItemDetails;
import com.courier.overc360.api.midmile.primary.model.piecedetails.PieceDetails;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.consignment.ReplicaConsignmentEntity;
import com.courier.overc360.api.midmile.replica.repository.ReplicaConsignmentEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class B2BService {

    @Autowired
    ConsignmentService consignmentService;

    @Autowired
    NumberRangeService numberRangeService;

    @Autowired
    ReplicaConsignmentEntityRepository repConRepo;

    @Autowired
    ConsignmentEntityRepository consignmentEntityRepository;

    public synchronized String generateUniqueHouseAirwayBill(String numRanObj) {
        return numberRangeService.getNextNumberRange(numRanObj);
    }


    // Create Consignment_From_B2B
    public List<Consignment> createConsignmentFromB2b(List<Consignment> addConsignmentList, String loginUserID) throws Exception {

        List<ConsignmentEntity> consignmentList = new ArrayList<>();
        String masterAirwayBill = numberRangeService.getNextNumberRange("MAWB");
        addConsignmentList.stream().forEach(con -> {
            // New Object
            ConsignmentEntity newConEntity = new ConsignmentEntity();
            BeanUtils.copyProperties(con, newConEntity, CommonUtils.getNullPropertyNames(con));
            IKeyValuePair iKey = repConRepo.getDescription("IWE");

            // Duplicate_Check
            ReplicaConsignmentEntity consignment = repConRepo.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerHouseAirwayBillAndDeletionIndicator(                                                   // DUPLICATE CHECK
                    iKey.getLangId(), "IWE", con.getCustomer_code(), con.getCustomer_reference_number(), 0L);
            if (consignment != null) {
                throw new BadRequestException("Given Values Getting Duplicate CustomerReferenceNumber " + consignment.getPartnerHouseAirwayBill());
            }
            // Get Product_Text
            String productName = repConRepo.getCustomerNm(con.getCustomer_code());
            // SetHouseAirwayBill
            String houseAirwayBill = Optional.ofNullable(generateUniqueHouseAirwayBill(productName))
                    .orElseThrow(() -> new BadRequestException("Number Range Object " + productName + " Doesn't exist in NumberRangeTable"));

            //HAWB_TYPE
                consignmentEntityRepository.statusEventText("IWE", "EN", "1")
                        .ifPresent(statusText -> {
                            newConEntity.setHawbType("STATUS");
                            newConEntity.setHawbTypeId("1");
                            newConEntity.setHawbTypeDescription(statusText);
                            newConEntity.setHawbTimeStamp(new Date());
                        });

            newConEntity.setCustomerCode(con.getCustomer_code());
            newConEntity.setPartnerId(con.getCustomer_code());
            newConEntity.setCustomerReferenceNumber(con.getCustomer_reference_number());
            newConEntity.setPartnerHouseAirwayBill(con.getCustomer_reference_number());
            newConEntity.setServiceTypeId(con.getService_type_id());
            newConEntity.setConsignmentType(con.getConsignment_type());
            newConEntity.setLoadType(con.getLoad_type());
            newConEntity.setDescription(con.getDescription());
            newConEntity.setCodFavorOf(con.getCod_favor_of());
            newConEntity.setMasterAirwayBill(masterAirwayBill);
            newConEntity.setDeclaredValue(con.getDeclared_value());
            newConEntity.setCurrency(con.getCurrency());
            newConEntity.setStatusDescription(con.getStatus_description());
            newConEntity.setIncoTerms(con.getInco_terms());
            newConEntity.setLanguageId(iKey.getLangId());
            newConEntity.setLanguageDescription(iKey.getLangDesc());
            newConEntity.setCompanyName(iKey.getCompanyDesc());
            newConEntity.setHouseAirwayBill(houseAirwayBill);
            newConEntity.setCustomerReferenceNumber(houseAirwayBill);
            newConEntity.setCreatedOn(new Date());
            newConEntity.setCreatedBy(loginUserID);

            // Origin
            if (con.getOrigin_details() != null) {
                newConEntity.setOriginDetails(mapOriginDetails(con.getOrigin_details(), loginUserID));
            }
            // Destination
            if (con.getDestination_details() != null) {
                newConEntity.setDestinationDetails(mapDestinationDetails(con.getDestination_details(), loginUserID));
            }
            // Return
            ReturnDetails newReturn = new ReturnDetails();
            newReturn.setCreatedBy(loginUserID);
            newReturn.setCreatedOn(new Date());
            newConEntity.setReturnDetails(newReturn);

            ConsignmentCommonValues commonValues = new ConsignmentCommonValues();
            BeanUtils.copyProperties(newConEntity, commonValues);
            // Piece_Details
            if (con.getPieces_detail() != null) {
                List<PieceDetails> pieceDetails = mapPieceDetails(con.getPieces_detail(), commonValues);
                Double totalPieceVolume = pieceDetails.stream().map(PieceDetails::getVolume).filter(n -> n != null).mapToDouble(a -> a).sum();
                Double totalPieceWeight = pieceDetails.stream().map(PieceDetails::getWeight).filter(n -> n != null).mapToDouble(a -> a).sum();
                Double totalPieceLength = pieceDetails.stream().map(PieceDetails::getLength).filter(n -> n != null).mapToDouble(a -> a).sum();
                Double totalPieceWidth = pieceDetails.stream().map(PieceDetails::getWidth).filter(n -> n != null).mapToDouble(a -> a).sum();
                Double totalPieceHeight = pieceDetails.stream().map(PieceDetails::getHeight).filter(n -> n != null).mapToDouble(a -> a).sum();
                String firstWeightUnit = pieceDetails.stream().map(PieceDetails::getWeight_unit).filter(Objects::nonNull).findFirst().orElse("");

                newConEntity.setPieceDetails(pieceDetails);
                newConEntity.setVolume(totalPieceVolume);
                newConEntity.setWeight(totalPieceWeight);
                newConEntity.setLength(totalPieceLength);
                newConEntity.setWidth(totalPieceWidth);
                newConEntity.setHeight(totalPieceHeight);
                newConEntity.setWeightUnit(firstWeightUnit);
                newConEntity.setActualWeight(totalPieceWeight);
                newConEntity.setPieceDetails(pieceDetails);
            }
            consignmentEntityRepository.save(newConEntity);
            consignmentList.add(newConEntity);
        });
        return createConsignmentFrom(consignmentList);
    }


    // Map to Piece
    private List<PieceDetails> mapPieceDetails(Set<Pieces_Details> piecesDetails, ConsignmentCommonValues commonValues) {
        long pieceCounter = 1L;
        List<PieceDetails> pieceDetailsList = new ArrayList<>();
        for(Pieces_Details piece : piecesDetails) {
            PieceDetails detail = new PieceDetails();
            List<ItemDetails> itemDetailsList = new ArrayList<>();
            BeanUtils.copyProperties(piece, detail, CommonUtils.getNullPropertyNames(piece));
            BeanUtils.copyProperties(commonValues, detail, CommonUtils.getNullPropertyNames(commonValues));
            String PIECE_ID = commonValues.getHouseAirwayBill() + String.format("%03d", pieceCounter++);
            detail.setDeclaredValue(piece.getDeclared_value());
            detail.setDimensionUnit(piece.getDimension_unit());
            detail.setVolumeUnit(piece.getVolume_unit());
            detail.setPieceProductCode(piece.getPiece_product_code());
            detail.setPieceId(PIECE_ID);
//            detail.setChargbleWeight(piece.getChargeable_weight());
            detail.setCreatedOn(new Date());
            detail.setHsCode(piece.getHsn_code());
            // Set ItemDetails
            ItemDetails newItem = new ItemDetails();
            BeanUtils.copyProperties(commonValues, newItem, CommonUtils.getNullPropertyNames(commonValues));

            newItem.setCreatedOn(new Date());
            itemDetailsList.add(newItem);
            detail.setItemDetails(itemDetailsList);
            pieceDetailsList.add(detail);
        }
        return pieceDetailsList;
    }


    // Response B2B
    public List<Consignment> createConsignmentFrom(List<ConsignmentEntity> consignmentList) throws Exception {

        List<Consignment> consignments = new ArrayList<>();
        consignmentList.stream().forEach(con -> {
            Consignment response = new Consignment();

            BeanUtils.copyProperties(con, response);
            // Map values from input to response
            response.setCustomer_code(con.getCustomerCode());
            response.setCustomer_reference_number(con.getCustomerReferenceNumber());
            response.setService_type_id(con.getServiceTypeId());
            response.setReference_number(con.getHouseAirwayBill());
            response.setConsignment_type(con.getConsignmentType());
            response.setLoad_type(con.getLoadType());
            response.setDescription(con.getDescription());
            response.setCod_favor_of(con.getCodFavorOf());
            response.setStatus_description(con.getHawbTypeDescription());

            response.setDeclared_value(con.getDeclaredValue());
            response.setCurrency(con.getCurrency());
            response.setStatus_description(con.getStatusDescription());
            response.setInco_terms(con.getIncoTerms());

            // Origin
            if (con.getOriginDetails() != null) {
                Origin_Details newOrigin = new Origin_Details();
                BeanUtils.copyProperties(con.getOriginDetails(), newOrigin);
                newOrigin.setAddress_line_1(con.getOriginDetails().getAddressLine1());
                newOrigin.setAddress_line_2(con.getOriginDetails().getAddressLine2());
                response.setOrigin_details(newOrigin);
            }
            // Destination
            if (con.getDestinationDetails() != null) {
                Destination_Details newDest = new Destination_Details();
                BeanUtils.copyProperties(con.getDestinationDetails(), newDest);
                newDest.setAddress_line_1(con.getDestinationDetails().getAddressLine1());
                newDest.setAddress_line_2(con.getDestinationDetails().getAddressLine2());
                response.setDestination_details(newDest);
            }

            if (con.getPieceDetails() != null) {
                Set<Pieces_Details> newPiece = new HashSet<>();
                con.getPieceDetails().stream().forEach(piece -> {
                    Pieces_Details detail = new Pieces_Details();
                    BeanUtils.copyProperties(piece, detail);
                    detail.setDeclared_value(piece.getDeclaredValue());
                    detail.setDimension_unit(piece.getDimensionUnit());
                    detail.setVolume_unit(piece.getVolumeUnit());
                    detail.setPiece_product_code(piece.getPieceProductCode());
                    detail.setHsn_code(piece.getHsCode());
                    newPiece.add(detail);
                });
                response.setPieces_detail(newPiece);
            }
            consignments.add(response);
        });
        return consignments;
    }

    // Map to Origin
    private OriginDetails mapOriginDetails(Origin_Details originDetails, String loginUserID) {
        OriginDetails origin = new OriginDetails();
        BeanUtils.copyProperties(originDetails, origin, CommonUtils.getNullPropertyNames(originDetails));
        origin.setAddressLine1(originDetails.getAddress_line_1());
        origin.setAddressLine2(originDetails.getAddress_line_2());
        origin.setCreatedOn(new Date());
        origin.setCreatedBy(loginUserID);
        return origin;
    }

    // Map to Destination
    private DestinationDetails mapDestinationDetails(Destination_Details destinationDetails, String loginUserID) {
        DestinationDetails destination = new DestinationDetails();
        BeanUtils.copyProperties(destinationDetails, destination, CommonUtils.getNullPropertyNames(destinationDetails));
        destination.setAddressLine1(destinationDetails.getAddress_line_1());
        destination.setAddressLine2(destinationDetails.getAddress_line_2());
        destination.setCreatedBy(loginUserID);
        destination.setCreatedOn(new Date());
        return destination;
    }



}
