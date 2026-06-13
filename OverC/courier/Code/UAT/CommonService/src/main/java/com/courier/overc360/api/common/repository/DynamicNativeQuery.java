package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DynamicNativeQuery {

    List<Consignment> findConsignment(List<Long> consignmentId, List<String> masterAirwayBill, Date startDate, Date endDate);
    List<ItemDetails> findItemDetails(String piecesId);
    List<PieceDetails> findPiecesDetails(Long consignmentId);
    List<ReferenceImageList> findReferenceImageList(Long consignmentId, String typeId);
    Optional<OriginDetails> findOriginDetails(Long consignmentId);
    Optional<DestinationDetails> findDestinationDetails(Long consignmentId);
    Optional<ReturnDetails> findReturnDetails(Long consignmentId);
}