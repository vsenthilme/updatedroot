package com.iweb2b.api.portal.repository;

import com.iweb2b.api.portal.model.consignment.dto.shopini.IShopiniWebhook;
import com.iweb2b.api.portal.model.consignment.dto.shopini.ShopiniWebhook;
import com.iweb2b.api.portal.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ShopiniWebhookRepository extends JpaRepository<ShopiniWebhook, Long>, StreamableJpaSpecificationRepository<ShopiniWebhook> {
    @Query(value = "select\n" +
            "TRACKING_NO trackingNo,\n" +
            "REFERENCE_NUMBER referenceNumber,\n" +
            "SHIPMENT_STATUS shipmentStatus,\n" +
            "ACTION_DATE actionDate,\n" +
            "ITEM_ACTION_NAME itemActionName,\n" +
            "LMD_SYNCED_STATUS lmdStatus\n" +
            "from tblshopiniwebhook\n" +
            "where\n" +
            "(COALESCE(:trackingNo, null) IS NULL OR (TRACKING_NO IN (:trackingNo))) and \n" +
            "(COALESCE(:referenceNumber, null) IS NULL OR (REFERENCE_NUMBER IN (:referenceNumber))) and \n" +
            "(COALESCE(:shipmentStatus, null) IS NULL OR (SHIPMENT_STATUS IN (:shipmentStatus))) and \n" +
            "(COALESCE(:itemActionName, null) IS NULL OR (ITEM_ACTION_NAME IN (:itemActionName))) and \n" +
            "(COALESCE(:lmdStatus, null) IS NULL OR (LMD_SYNCED_STATUS IN (:lmdStatus))) and \n" +
            "(COALESCE(CONVERT(VARCHAR(255), :fromDate), null) IS NULL OR (action_date between COALESCE(CONVERT(VARCHAR(255), :fromDate), null) and COALESCE(CONVERT(VARCHAR(255), :toDate), null)))", nativeQuery = true)
    public List<IShopiniWebhook> findShopiniWebhook(@Param("trackingNo") List<String> trackingNo,
                                                    @Param("referenceNumber") List<String> referenceNumber,
                                                    @Param("shipmentStatus") List<String> shipmentStatus,
                                                    @Param("itemActionName") List<String> itemActionName,
                                                    @Param("lmdStatus") List<Long> lmdStatus,
                                                    @Param("fromDate") Date fromDate,
                                                    @Param("toDate") Date toDate);

}

