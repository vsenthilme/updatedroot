package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.v2.ConsignmentV2;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConsignmentV2Repository extends CassandraRepository<ConsignmentV2, Long> {

    @AllowFiltering
    List<ConsignmentV2> findByConsignmentIdAndMasterAirwayBillAndDeletionIndicator(Long consignmentId, String masterAirwayBill, Long deletionIndicator);

    @AllowFiltering
    List<ConsignmentV2> findByCreatedOnBetween(Date startDate, Date endDate);

}