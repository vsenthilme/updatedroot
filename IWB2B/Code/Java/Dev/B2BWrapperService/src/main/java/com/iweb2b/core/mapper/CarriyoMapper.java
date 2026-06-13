//package com.iweb2b.core.mapper;
//
//import com.iweb2b.core.model.carriyo.CarriyoShipmentsTrackingSearchResponse;
//import com.iweb2b.core.model.carriyo.CarriyoStatusEvent;
//import com.iweb2b.core.model.carriyo.CarriyoStatusUpdateRequest;
//import com.iweb2b.core.model.carriyo.CarriyoTrackingInfo;
//import com.iweb2b.core.model.integration.ConsignmentTracking;
//import com.iweb2b.core.model.integration.Event;
//import com.iweb2b.core.model.integration.asyad.ConsignmentWebhook;
//import com.iweb2b.core.util.CollectionUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class CarriyoMapper {
//
//    private static final Set<String> NOT_MAPPED_TYPES = Set.of("on_hold", "release_on_hold", "pickup_completed",
//            "cancel", "handover_courier_partner", "reached_at_hub", "inscan_at_hub", "out_for_pickup",
//            "shipment_clear_successfully");
//
//    public CarriyoShipmentsTrackingSearchResponse toResponse(List<ConsignmentTracking> trackings) {
//
//        CarriyoShipmentsTrackingSearchResponse response = new CarriyoShipmentsTrackingSearchResponse();
//
//        List<CarriyoTrackingInfo> infoList = trackings.stream().map(this::toTrackingInfo).collect(Collectors.toList());
//        response.setInfoList(infoList);
//
//        return response;
//    }
//
//    private CarriyoTrackingInfo toTrackingInfo(ConsignmentTracking consignmentTracking) {
//        CarriyoTrackingInfo trackingInfo = new CarriyoTrackingInfo();
//
//        trackingInfo.setTrackingNumber(consignmentTracking.getReference_number());
//        List<CarriyoStatusEvent> events = CollectionUtils.stream(consignmentTracking.getEvents())
//                .map(this::toStatusEvent).collect(Collectors.toList());
//        trackingInfo.setStatusEvents(events);
//        return trackingInfo;
//    }
//
//    private CarriyoStatusEvent toStatusEvent(Event event) {
//        CarriyoStatusEvent statusEvent = new CarriyoStatusEvent();
//
//        statusEvent.setCarriyoStatusCode(detectCarriyoStatusCode(event.getType()));
//        statusEvent.setCarriyoReasonCode(detectCarriyoReasonCode(event.getFailure_reason()));
//
//        statusEvent.setCarrierStatusCode(event.getType());
//        statusEvent.setCarrierStatusDescription(event.getType());
//        statusEvent.setStatusUpdateDate(event.getEvent_time());
//
//        return statusEvent;
//    }
//
//    private String detectCarriyoStatusCode(String type) {
//        if (type == null || NOT_MAPPED_TYPES.contains(type.toLowerCase())) {
//            return "";
//        } else if (type.equalsIgnoreCase("delivered")) {
//            return "delivered";
//        } else if (type.equalsIgnoreCase("attempted")) {
//            return "failed_delivery_attempt";
//        } else if (type.equalsIgnoreCase("rto")) {
//            return "returned";
//        } else if (type.equalsIgnoreCase("intransit_to_hub")) {
//            return "in_transit";
//        } else if (type.equalsIgnoreCase("accept")) {
//            return "out_for_delivery";
//        }
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown event type: " + type);
//    }
//
//    private String detectCarriyoReasonCode(String failureReason) {
//        if ("DELIVERY ATTEMPTED – CONSIGNEE MOVED TO NEW AND KNOWN ADDRESS".equalsIgnoreCase(failureReason)) {
//            return "address_changed_by_customer";
//        } else if ("Delivery attempted – Consignee not answered".equalsIgnoreCase(failureReason)) {
//            return "customer_not_available";
//        } else if ("Delivery attempted – Consignee phone closed".equalsIgnoreCase(failureReason)) {
//            return "customer_not_contactable";
//        } else if ("DELIVERY ATTEMPTED- BAD ADDRESS".equalsIgnoreCase(failureReason)) {
//            return "incorrect_address_details";
//        } else if ("REFUSED BY CONSIGNEE - INCORRECT CONTENTS".equalsIgnoreCase(failureReason)) {
//            return "incorrect_customer_documentation";
//        } else if ("Delivery attempted - Consignee refused Delivery".equalsIgnoreCase(failureReason)) {
//            return "refused_by_customer";
//        } else if ("DELIVERY ATTEMPTED CONSIGNEE REQUEST FUTURE DELIVERY".equalsIgnoreCase(failureReason)) {
//            return "rescheduled_by_customer";
//        }
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown failure reason: " + failureReason);
//    }
//
//    public CarriyoStatusUpdateRequest toStatusUpdateRequest(ConsignmentWebhook consignmentWebhook) {
//        CarriyoStatusUpdateRequest updateRequest = new CarriyoStatusUpdateRequest();
//
//        updateRequest.setTrackingNumber(consignmentWebhook.getReference_number());
//        updateRequest.setStatusUpdateDate(consignmentWebhook.getEvent_time());
//        updateRequest.setCarriyoStatusCode(detectCarriyoStatusCode(consignmentWebhook.getType()));
//        updateRequest.setCarriyoReasonCode(detectCarriyoReasonCode(consignmentWebhook.getFailure_reason()));
//        updateRequest.setCarrierStatusCode(consignmentWebhook.getType());
//        updateRequest.setCarrierStatusDescription(consignmentWebhook.getType());
//        updateRequest.setStatusLocation(consignmentWebhook.getHub_name());
//        updateRequest.setProofOfDelivery(consignmentWebhook.getPoc_image_list().get(0).getUrl());
//        updateRequest.setDriverName(consignmentWebhook.getWorker_name());
//        updateRequest.setRecipientName(consignmentWebhook.getReceiver_name());
//
//        return updateRequest;
//    }
//}