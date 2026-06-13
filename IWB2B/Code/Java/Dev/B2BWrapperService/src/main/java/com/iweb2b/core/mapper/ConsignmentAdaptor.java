//package com.iweb2b.core.mapper;
//
//import com.iweb2b.core.model.carriyo.CarriyoItemDto;
//import com.iweb2b.core.model.carriyo.CarriyoParcelDto;
//import com.iweb2b.core.model.carriyo.CarriyoPickupDto;
//import com.iweb2b.core.model.carriyo.CarriyoShipmentRequest;
//import com.iweb2b.core.model.integration.asyad.Consignment;
//import com.iweb2b.core.model.integration.asyad.DestinationDetails;
//import com.iweb2b.core.model.integration.asyad.OriginDetails;
//import com.iweb2b.core.model.integration.asyad.PieceDetails;
//import com.iweb2b.core.model.integration.asyad.PieceItemDetails;
//import com.iweb2b.core.util.CollectionUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class ConsignmentAdaptor {
//
//    @Value("${carriyo.default-service-type-id}")
//    private String defaultServiceTypeId;
//
//    @Value("${carriyo.default-customer-code}")
//    private String defaultCustomerCode;
//
//    public Consignment adapt(CarriyoShipmentRequest request) {
//
//        Consignment consignment = new Consignment();
//
//        consignment.setConsignmentType(request.getEntityType());
//        consignment.setTenant(request.getTenant());
//        consignment.setMerchant(request.getMerchant());
//
//        consignment.setCustomerCode(defaultCustomerCode);
//        if (request.getReferences() != null) {
//            consignment.setCustomerReferenceNumber(request.getReferences().getPartnerOrderReference());
//            consignment.setPartnerShipmentReference(request.getReferences().getPartnerShipmentReference());
//        }
//        if (request.getPayment() != null) {
//            consignment.setCodCollectionMode(request.getPayment().getPaymentMode());
//            consignment.setPendingAmount(request.getPayment().getPendingAmount());
//            consignment.setCodAmount(String.valueOf(request.getPayment().getTotalAmount()));
//            consignment.setCurrency(request.getPayment().getCurrency());
//        }
//
//        consignment.setCollectionScheduler(request.getCollection());
//        consignment.setDeliveryScheduler(request.getDelivery());
//
//        consignment.setOriginDetails(adapt(request.getPickup()));
//
//        consignment.setDestinationDetails(adaptDestinationDetails(request.getDropoff()));
//        consignment.setPieceItemDetails(adaptItems(request.getItems()));
//        consignment.setPiecesDetails(adaptParcels(request.getParcels()));
//
//        if (request.getCarrierCustomAttributes() != null) {
//            consignment.setServiceTypeId(defaultServiceTypeId);
//            consignment.setAccountNumber(request.getCarrierCustomAttributes().getCarrierAccountNumber());
//        }
//
//        return consignment;
//    }
//
//    private OriginDetails adapt(CarriyoPickupDto pickup) {
//        if (pickup == null) {
//            return null;
//        }
//        OriginDetails originDetails = new OriginDetails();
//
//        originDetails.setName(pickup.getContactName());
//        originDetails.setPhone(pickup.getContactPhone());
//        originDetails.setEmail(pickup.getContactEmail());
//        originDetails.setAddressLine1(pickup.getAddress1());
//        originDetails.setCity(pickup.getCity());
//
//        originDetails.setState(pickup.getState());
//        originDetails.setCountry(pickup.getCountry());
//        originDetails.setPostcode(pickup.getPostcode());
//        originDetails.setCoords(pickup.getCoords());
//        originDetails.setWhat3words(pickup.getWhat3words());
//        originDetails.setPoBox(pickup.getPoBox());
//        originDetails.setType(pickup.getType());
//        originDetails.setNotes(pickup.getNotes());
//
//        return originDetails;
//    }
//
//    private DestinationDetails adaptDestinationDetails(CarriyoPickupDto dropoff) {
//
//        if (dropoff == null) {
//            return null;
//        }
//
//        DestinationDetails destinationDetails = new DestinationDetails();
//
//        destinationDetails.setName(dropoff.getContactName());
//        destinationDetails.setPhone(dropoff.getContactPhone());
//        destinationDetails.setEmail(dropoff.getContactEmail());
//        destinationDetails.setAddressLine1(dropoff.getAddress1());
//        destinationDetails.setAddressLine2(dropoff.getAddress2());
//        destinationDetails.setCity(dropoff.getCity());
//        destinationDetails.setState(dropoff.getState());
//        destinationDetails.setCountry(dropoff.getCountry());
//        destinationDetails.setPostcode(dropoff.getPostcode());
//        destinationDetails.setCoords(dropoff.getCoords());
//        destinationDetails.setNotes(dropoff.getNotes());
//        return destinationDetails;
//    }
//
//    private List<PieceItemDetails> adaptItems(List<CarriyoItemDto> items) {
//        if (items == null) {
//            return null;
//        }
//        return CollectionUtils.stream(items).map(this::adaptItem).collect(Collectors.toList());
//
//    }
//
//    private PieceItemDetails adaptItem(CarriyoItemDto item) {
//
//        PieceItemDetails pieceDetails = new PieceItemDetails();
//
//        pieceDetails.setDescription(item.getDescription());
//        pieceDetails.setQuantity(item.getQuantity());
//        if (item.getPrice() != null) {
//            pieceDetails.setDeclaredValue(item.getPrice().getAmount());
//            pieceDetails.setCurrency(item.getPrice().getCurrency());
//        }
//        if (item.getWeight() != null) {
//            pieceDetails.setWeight(item.getWeight().getValue());
//            pieceDetails.setWeightUnit(item.getWeight().getUnit());
//            pieceDetails.setSku(item.getWeight().getSku());
//        }
//        return pieceDetails;
//    }
//
//    private Set<PieceDetails> adaptParcels(List<CarriyoParcelDto> parcels) {
//        if (parcels == null) {
//            return null;
//        }
//        return CollectionUtils.stream(parcels).map(this::adaptParcel).collect(Collectors.toSet());
//    }
//
//    private PieceDetails adaptParcel(CarriyoParcelDto parcel) {
//        PieceDetails pieceDetails = new PieceDetails();
//        pieceDetails.setPartnerParcelReference(parcel.getPartnerParcelReference());
//
//        if (parcel.getWeight() != null) {
//            pieceDetails.setWeight(parcel.getWeight().getValue());
//            pieceDetails.setWeightUnit(parcel.getWeight().getUnit());
//        }
//        if (parcel.getDimension() != null) {
//            pieceDetails.setWidth(parcel.getDimension().getWidth());
//            pieceDetails.setHeight(parcel.getDimension().getHeight());
//            pieceDetails.setDimensionUnit(parcel.getDimension().getUnit());
//            pieceDetails.setDimensionDepth(parcel.getDimension().getDepth());
//        }
//        return pieceDetails;
//    }
//}
//
//
//