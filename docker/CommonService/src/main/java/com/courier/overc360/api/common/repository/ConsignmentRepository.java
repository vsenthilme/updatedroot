package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.Consignment;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConsignmentRepository extends CassandraRepository<Consignment, Long>, DynamicNativeQuery {

    @AllowFiltering
    List<Consignment> findByConsignmentIdAndMasterAirwayBillAndDeletionIndicator(Long consignmentId, String masterAirwayBill, Long deletionIndicator);

    @AllowFiltering
    List<Consignment> findByConsignmentIdAndDeletionIndicator(Long consignmentId, Long deletionIndicator);

    @AllowFiltering
    List<Consignment> findByMasterAirwayBillAndDeletionIndicator(String masterAirwayBill, Long deletionIndicator);

    @AllowFiltering
    List<Consignment> findByCreatedOnBetween(Date startDate, Date endDate);

    @AllowFiltering
    List<Consignment> findByConsignmentIdInAndCreatedOnBetween(List<Long> consignmentId, Date startDate, Date endDate);

    @AllowFiltering
    List<Consignment> findByMasterAirwayBillInAndCreatedOnBetween(List<String> masterAirwayBill, Date startDate, Date endDate);

    @AllowFiltering
    List<Consignment> findByConsignmentIdInAndMasterAirwayBillInAndCreatedOnBetween(List<Long> consignmentId, List<String> masterAirwayBill, Date startDate, Date endDate);
}