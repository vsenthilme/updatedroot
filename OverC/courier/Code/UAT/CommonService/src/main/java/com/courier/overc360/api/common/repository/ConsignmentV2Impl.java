package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.v2.ConsignmentV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ConsignmentV2Impl {

    @Autowired
    private CassandraOperations cassandraTemplate;

    public List<ConsignmentV2> findConsignment(List<Long> consignmentIds, List<String> partnerIds,
                                               List<String> companyIds, List<String> languageIds,
                                               List<String> pieceIds, List<String> shipperIds, List<String> statusIds,
                                               List<String> houseAirwayBills, List<String> partnerHouseAirwayBills,
                                               List<String> masterAirwayBills, List<String> partnerMasterAirwayBills,
                                               Date startDate, Date endDate, Long deletionIndicator) {
        Query query = Query.empty();

        // Add filters dynamically based on the input parameters
        if (consignmentIds != null && !consignmentIds.isEmpty()) {
            query = query.and(Criteria.where("consignmentId").in(consignmentIds));
        }

        if (partnerIds != null && !partnerIds.isEmpty()) {
            query = query.and(Criteria.where("partnerId").in(partnerIds));
        }

        if (companyIds != null && !companyIds.isEmpty()) {
            query = query.and(Criteria.where("companyId").in(companyIds));
        }

        if (languageIds != null && !languageIds.isEmpty()) {
            query = query.and(Criteria.where("languageId").in(languageIds));
        }

        if (pieceIds != null && !pieceIds.isEmpty()) {
            query = query.and(Criteria.where("pieceId").in(pieceIds));
        }

        if (shipperIds != null && !shipperIds.isEmpty()) {
            query = query.and(Criteria.where("shipperId").in(shipperIds));
        }

        if (statusIds != null && !statusIds.isEmpty()) {
            query = query.and(Criteria.where("statusId").in(statusIds));
        }

        if (houseAirwayBills != null && !houseAirwayBills.isEmpty()) {
            query = query.and(Criteria.where("houseAirwayBill").in(houseAirwayBills));
        }

        if (partnerHouseAirwayBills != null && !partnerHouseAirwayBills.isEmpty()) {
            query = query.and(Criteria.where("partnerHouseAirwayBill").in(partnerHouseAirwayBills));
        }

        if (partnerMasterAirwayBills != null && !partnerMasterAirwayBills.isEmpty()) {
            query = query.and(Criteria.where("partnerMasterAirwayBill").in(partnerMasterAirwayBills));
        }

        if (masterAirwayBills != null && !masterAirwayBills.isEmpty()) {
            query = query.and(Criteria.where("masterAirwayBill").in(masterAirwayBills));
        }

        if (deletionIndicator != null) {
            query = query.and(Criteria.where("deletionIndicator").is(deletionIndicator));
        }
        // Filter by date range (between startDate and endDate)
        if (startDate != null) {
            query = query.and(Criteria.where("createdOn").gte(startDate)); // Start date
        }

        if (endDate != null) {
            query = query.and(Criteria.where("createdOn").lte(endDate)); // End date
        }

        // Execute the query using CassandraOperations
        return cassandraTemplate.select(query, ConsignmentV2.class);
    }
}