package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.v2.ConsignmentV2;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DynamicNativeQueryConsignmentV2 {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public List<ConsignmentV2> findConsignment(List<Long> consignmentIds, List<String> partnerIds,
                                               List<String> companyIds, List<String> languageIds,
                                               List<String> pieceIds, List<String> shipperIds, List<String> statusIds,
                                               List<String> houseAirwayBills, List<String> partnerHouseAirwayBills,
                                               List<String> masterAirwayBills, List<String> partnerMasterAirwayBills,
                                               Date startDate, Date endDate, Long deletionIndicator) {

        StringBuilder cql = new StringBuilder("SELECT * FROM tblconsignmentv2");

        List<Object> params = new ArrayList<>();
        boolean hasConditions = false;

        // Add consignmentIds filter if not null or empty
        if (consignmentIds != null && !consignmentIds.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("consignmentId IN ?");
            params.add(consignmentIds);
            hasConditions = true;
        }

        if (partnerIds != null && !partnerIds.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("partnerId IN ?");
            params.add(partnerIds);
            hasConditions = true;
        }

        if (companyIds != null && !companyIds.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("companyId IN ?");
            params.add(companyIds);
            hasConditions = true;
        }

        if (languageIds != null && !languageIds.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("languageId IN ?");
            params.add(languageIds);
            hasConditions = true;
        }

        if (pieceIds != null && !pieceIds.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("pieceId IN ?");
            params.add(pieceIds);
            hasConditions = true;
        }

        if (shipperIds != null && !shipperIds.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("shipperId IN ?");
            params.add(shipperIds);
            hasConditions = true;
        }

        if (statusIds != null && !statusIds.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("statusId IN ?");
            params.add(statusIds);
            hasConditions = true;
        }

        if (houseAirwayBills != null && !houseAirwayBills.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("houseAirwayBill IN ?");
            params.add(houseAirwayBills);
            hasConditions = true;
        }

        if (partnerHouseAirwayBills != null && !partnerHouseAirwayBills.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("partnerHouseAirwayBill IN ?");
            params.add(partnerHouseAirwayBills);
            hasConditions = true;
        }

        // Add masterAirwayBills filter if not null or empty
        if (masterAirwayBills != null && !masterAirwayBills.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("masterAirwayBill IN ?");
            params.add(masterAirwayBills);
            hasConditions = true;
        }

        if (partnerMasterAirwayBills != null && !partnerMasterAirwayBills.isEmpty()) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("partnerMasterAirwayBill IN ?");
            params.add(partnerMasterAirwayBills);
            hasConditions = true;
        }

        // Add deletionIndicator filter if not null
        if (deletionIndicator != null) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("deletionIndicator = ?");
            params.add(deletionIndicator);
            hasConditions = true;
        }

        // Add date filters if provided
        if (startDate != null) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("createdOn >= ?");
            params.add(startDate);
            hasConditions = true;
        }

        if (endDate != null) {
            cql.append(hasConditions ? " AND " : " WHERE ");
            cql.append("createdOn <= ?");
            params.add(endDate);
        }

        // Append ALLOW FILTERING clause
        cql.append(" ALLOW FILTERING");

        // Construct the SimpleStatement
        SimpleStatement statement = SimpleStatement.builder(cql.toString())
                .addPositionalValues(params.toArray())
                .build();

        // Execute the query with CassandraOperations
        return cassandraTemplate.select(statement, ConsignmentV2.class);
    }
}