package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.primary.model.maps.GeoInfo;
import com.courier.overc360.api.midmile.primary.model.pickup.PickerAssignment;
import com.courier.overc360.api.midmile.primary.repository.PickupEntityRepository;
import com.courier.overc360.api.midmile.replica.model.pickup.AppUser;
import com.courier.overc360.api.midmile.replica.repository.ReplicaDeliveryRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPickupEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PickupReportService {

    @Autowired
    private PickupEntityRepository pickupEntityRepository;

    @Autowired
    private ReplicaPickupEntityRepository replicaPickupEntityRepository;

    @Autowired
    private ReplicaDeliveryRepository replicaDeliveryRepository;

    @Autowired
    private GeoInfoService geoInfoService;




    //--------------------------------------------------------PICKUP_REPORTS--------------------------------------------


    /**
     * Find PickerAssignment
     *
     * @return
     */
    public List<PickerAssignment> findPickerAssignment() {

        List<PickerAssignment> pickerAssignments = new ArrayList<>();
        try {
            List<AppUser> userId = replicaPickupEntityRepository.getAppUser();
            log.info("Users Id List{}", userId);
            userId.stream().forEach(user -> {
                PickerAssignment newPicker = new PickerAssignment();
                if(user.getAppUserId() != null) {
                    //UserId
                    newPicker.setUserId(user.getAppUserId());

                    newPicker.setMobileNumber(user.getMobileNumber() != null ? user.getMobileNumber() : null);

                    // PickerAssigned
                    Long pickerAgnCount = replicaPickupEntityRepository.pickerAssignedCount(user.getAppUserId());
                    newPicker.setNoOfShipmentsAssignment(pickerAgnCount != null ? pickerAgnCount : 0L);
                    log.info("PickerAssigned Count {}", pickerAgnCount);

                    // Picker Delivery
                    Long pickerDelivery = replicaPickupEntityRepository.pickupDeliveryCount(user.getAppUserId(), "18");
                    newPicker.setNoOfShipmentsDelivered(pickerDelivery != null ? pickerDelivery : 0L);
                    log.info("PickerDelivered Count {}", pickerDelivery);

                    // PendingShipment
                    Long pendingShipment = (pickerAgnCount != null ? pickerAgnCount : 0L) - (pickerDelivery != null ? pickerDelivery : 0L);
                    newPicker.setNoOfShipmentsPending(pendingShipment);
                    log.info("Pending Shipments {}", pendingShipment);

                    if(user.getLongitude() != null && user.getLatitude() != null && user.getAddress() != null) {
                        newPicker.setAddress(user.getAddress());
                        newPicker.setLongitude(user.getLongitude());
                        newPicker.setLatitude(user.getLatitude());
                    }

                    // Address
                    if (user.getAddress() != null && user.getLongitude() == null && user.getLatitude() == null) {
                        GeoInfo rs = geoInfoService.geoCode(user.getAddress());
                        newPicker.setAddress(rs.getAddress());
                        newPicker.setLongitude(rs.getLongitude());
                        newPicker.setLatitude(rs.getLatitude());
                        pickupEntityRepository.updateAppUser(rs.getLatitude(), rs.getLongitude(), user.getAddress(), user.getCompanyId(),
                                user.getLanguageId(), user.getAppUserId());
                    }

                    // LAT & LONG
                    if (user.getLongitude() != null && user.getLatitude()!= null && user.getAddress() == null) {
                        GeoInfo rs = geoInfoService.reverseGeocode(user.getLatitude(), user.getLongitude());
                        newPicker.setAddress(rs.getAddress());
                        newPicker.setLongitude(rs.getLongitude());
                        newPicker.setLatitude(rs.getLatitude());
                        pickupEntityRepository.updateAppUser(user.getLatitude(), user.getLongitude(), rs.getAddress(), user.getCompanyId(),
                                user.getLanguageId(), user.getAppUserId());
                    }

                    // ETA
                    String eta = replicaPickupEntityRepository.getEta(user.getAppUserId());
                    if (eta != null) {
                        newPicker.setLastShipmentsETA(eta);
                    }
                } else {
                    log.info("UserId is Null");
                }
                pickerAssignments.add(newPicker);
            });

        } catch (Exception e) {
            log.info("Picker Assignment Failed Error ");
            throw new RuntimeException("Picker Assignment Failed "+ e);
        }
        return pickerAssignments;
    }


    // Delivery Assignment
    /**
     * Find DeliveryAssignment
     *
     * @return
     */
    public List<PickerAssignment> findDeliveryAssignmentCount() {

        List<PickerAssignment> deliveryAssignments = new ArrayList<>();
        try {
            List<AppUser> userId = replicaPickupEntityRepository.getAppUser();
            log.info("Users Id List{}", userId);
            userId.stream().forEach(user -> {
                PickerAssignment newPicker = new PickerAssignment();
                if(user.getAppUserId() != null) {
                    //UserId
                    newPicker.setUserId(user.getAppUserId());

                    // Mobile Number
                    newPicker.setMobileNumber(user.getMobileNumber() != null ? user.getMobileNumber() : null);

                    // DeliveryAssigned
                    Long pickerAgnCount = replicaDeliveryRepository.pickerAssignedCount(user.getAppUserId());
                    newPicker.setNoOfShipmentsAssignment(pickerAgnCount != null ? pickerAgnCount : 0L);
                    log.info("DeliveryAssigned Count {}", pickerAgnCount);

                    // Picker Delivery
                    Long riderDelivery = replicaDeliveryRepository.pickupDeliveryCount(user.getAppUserId(), "18");
                    newPicker.setNoOfShipmentsDelivered(riderDelivery != null ? riderDelivery : 0L);
                    log.info("Delivered Count {}", riderDelivery);

                    // PendingShipment
                    Long pendingShipment = (pickerAgnCount != null ? pickerAgnCount : 0L) - (riderDelivery != null ? riderDelivery : 0L);
                    newPicker.setNoOfShipmentsPending(pendingShipment);
                    log.info("Pending Shipments: {}", pendingShipment);

                    if(user.getLongitude() != null && user.getLatitude() != null && user.getAddress() != null) {
                        newPicker.setAddress(user.getAddress());
                        newPicker.setLongitude(user.getLongitude());
                        newPicker.setLatitude(user.getLatitude());
                    }

                    // Address
                    if (user.getAddress() != null && user.getLongitude() == null && user.getLatitude() == null) {
                        GeoInfo rs = geoInfoService.geoCode(user.getAddress());
                        newPicker.setAddress(rs.getAddress());
                        newPicker.setLongitude(rs.getLongitude());
                        newPicker.setLatitude(rs.getLatitude());
                        pickupEntityRepository.updateAppUser(rs.getLatitude(), rs.getLongitude(), user.getAddress(), user.getCompanyId(),
                                user.getLanguageId(), user.getAppUserId());
                    }

                    // LAT & LONG
                    if (user.getLongitude() != null && user.getLatitude()!= null && user.getAddress() == null) {
                        GeoInfo rs = geoInfoService.reverseGeocode(user.getLatitude(), user.getLongitude());
                        newPicker.setAddress(rs.getAddress());
                        newPicker.setLongitude(rs.getLongitude());
                        newPicker.setLatitude(rs.getLatitude());
                        pickupEntityRepository.updateAppUser(user.getLatitude(), user.getLongitude(), rs.getAddress(), user.getCompanyId(),
                                user.getLanguageId(), user.getAppUserId());
                    }

                    String eta = replicaDeliveryRepository.getEta(user.getAppUserId());
                    if (eta != null) {
                        newPicker.setLastShipmentsETA(eta);
                    }
                    deliveryAssignments.add(newPicker);
                } else {
                    log.info("App User Id is Null");
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Picker Assignment Failed "+ e);
        }
        return deliveryAssignments;
    }


}
