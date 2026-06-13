package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.maps.MobileTracking;
import com.courier.overc360.api.midmile.primary.model.reports.DeliveryCountResponse;
import com.courier.overc360.api.midmile.primary.model.reports.DeliveryMobileApp;
import com.courier.overc360.api.midmile.primary.model.reports.MobileDashboardCount;
import com.courier.overc360.api.midmile.primary.model.reports.MobileDashboardResponse;
import com.courier.overc360.api.midmile.replica.repository.ReplicaDeliveryRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPickupEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ReportsService {


    @Autowired
    private ReplicaPickupEntityRepository replicaPickupEntityRepository;

    @Autowired
    private ReplicaDeliveryRepository replicaDeliveryRepository;


    /**
     * @param mobileDashboardCount
     * @return
     */
    public MobileDashboardResponse getMobileDashboardCount(MobileDashboardCount mobileDashboardCount) {

        MobileDashboardResponse mobileDashboardResponse;
            Long assignedPickup = replicaPickupEntityRepository.getPickupAssignedCount(mobileDashboardCount.getCourierId(),mobileDashboardCount.getLanguageId(),mobileDashboardCount.getCompanyId(),mobileDashboardCount.getPartnerId(),
                    mobileDashboardCount.getHouseAirwayBill(),mobileDashboardCount.getPickupId());
            Long inProgressPickup = replicaPickupEntityRepository.getPickupInprogressCount(mobileDashboardCount.getCourierId(),mobileDashboardCount.getLanguageId(),
                    mobileDashboardCount.getCompanyId(),mobileDashboardCount.getPartnerId(),mobileDashboardCount.getHouseAirwayBill(),mobileDashboardCount.getPickupId());
        Long assignedDelivery = replicaDeliveryRepository.getDeliveryAssignedCount(mobileDashboardCount.getPieceId(),mobileDashboardCount.getLanguageId(),mobileDashboardCount.getCompanyId(),
                mobileDashboardCount.getDeliveryId(),mobileDashboardCount.getHouseAirwayBill(),mobileDashboardCount.getCourierId());
        Long inProgressDelivery = replicaDeliveryRepository.getDeliveryInprogressCount(mobileDashboardCount.getPieceId(),mobileDashboardCount.getLanguageId(),
                mobileDashboardCount.getCompanyId(),mobileDashboardCount.getDeliveryId(),mobileDashboardCount.getHouseAirwayBill(),mobileDashboardCount.getCourierId());

        mobileDashboardResponse = new MobileDashboardResponse();
        Long pickupTotal = assignedPickup+inProgressPickup;
        log.info("total pickup value -- {}",pickupTotal);
        Long deliveryTotal = assignedDelivery+inProgressDelivery;
        log.info("total delivery value -- {}",deliveryTotal);
        mobileDashboardResponse.setPickupTotal(pickupTotal);
        mobileDashboardResponse.setPickupAssignedCount(assignedPickup);
        mobileDashboardResponse.setPickupInProgressCount(inProgressPickup);
        mobileDashboardResponse.setDeliveryTotal(deliveryTotal);
        mobileDashboardResponse.setDeliveryAssignedCount(assignedDelivery);
        mobileDashboardResponse.setDeliveryInProgressCount(inProgressDelivery);
        return mobileDashboardResponse;
    }

//
//    /**
//     * Pickup Assigned List
//     *
//     * @param deliveryMobileApp
//     * @return
//     */
//    public DeliveryCountResponse getDeliveryAppCount(DeliveryMobileApp deliveryMobileApp) {
//        DeliveryCountResponse deliveryCountResponse = null;
//
//            Long assigned = replicaDeliveryRepository.getDeliveryAssignedCount(deliveryMobileApp.getPieceId(),deliveryMobileApp.getLanguageId(),deliveryMobileApp.getCompanyId(),
//                    deliveryMobileApp.getDeliveryId(),deliveryMobileApp.getHouseAirwayBill(),deliveryMobileApp.getCourierId());
//            Long inProgress = replicaDeliveryRepository.getDeliveryInprogressCount(deliveryMobileApp.getPieceId(),deliveryMobileApp.getLanguageId(),
//                    deliveryMobileApp.getCompanyId(),deliveryMobileApp.getDeliveryId(),deliveryMobileApp.getHouseAirwayBill(),deliveryMobileApp.getCourierId());
//            deliveryCountResponse = new DeliveryCountResponse();
//            deliveryCountResponse.setDeliveryAssignedCount(assigned);
//            deliveryCountResponse.setDeliveryInProgressCount(inProgress);
//
//            return deliveryCountResponse;
//    }


    /**
     * MobileTracking List
     *
     * @param mobileTrackingList
     * @return
     */
    public List<MobileTracking> findMobileTracking(List<MobileTracking> mobileTrackingList) {

        List<MobileTracking> mobileTrackings = new ArrayList<>();
        try {
            mobileTrackingList.stream().forEach(mobileTracking -> {
                MobileTracking newTrack = new MobileTracking();
                newTrack.setUserId(mobileTracking.getUserId());
                newTrack.setLatitude(mobileTracking.getLatitude());
                newTrack.setLongitude(mobileTracking.getLongitude());
                newTrack.setFromDate(mobileTracking.getFromDate());
                newTrack.setToDate(mobileTracking.getToDate());
                newTrack.setMobileNo(mobileTracking.getMobileNo());
                mobileTrackings.add(newTrack);
            });
        }catch (Exception e) {
            throw new RuntimeException("Mobile Tracking Failed " + e.getMessage());
        }
        return mobileTrackings;
    }


}
