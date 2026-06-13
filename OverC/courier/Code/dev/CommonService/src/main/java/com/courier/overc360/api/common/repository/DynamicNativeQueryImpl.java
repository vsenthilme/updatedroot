package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.*;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DynamicNativeQueryImpl implements DynamicNativeQuery {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public List<Consignment> findConsignment(List<Long> consignmentId, List<String> masterAirwayBill, Date startDate, Date endDate) {
        String cql = "SELECT * FROM tblconsignment WHERE ";
        if (consignmentId != null && !consignmentId.isEmpty()) {
            cql = cql + "consignmentid in ? ";
        }
        if ((consignmentId == null || consignmentId.isEmpty()) && (masterAirwayBill != null && !masterAirwayBill.isEmpty())) {
            cql = cql + "masterairwaybill in ? ";
        }
        if (consignmentId != null && !consignmentId.isEmpty() && masterAirwayBill != null && !masterAirwayBill.isEmpty()) {
            cql = cql + "AND masterairwaybill in ? ";
        }
        if ((consignmentId != null && !consignmentId.isEmpty() &&
                masterAirwayBill != null && !masterAirwayBill.isEmpty() &&
                startDate != null && endDate != null) ||
                ((consignmentId == null || consignmentId.isEmpty()) &&
                        masterAirwayBill != null && !masterAirwayBill.isEmpty() &&
                        startDate != null && endDate != null)) {
            cql = cql + "AND createdon between ? AND ? ";
        }
        if ((consignmentId == null || consignmentId.isEmpty()) &&
                (masterAirwayBill == null || masterAirwayBill.isEmpty()) &&
                startDate != null && endDate != null) {
            cql = cql + "createdon between ? AND ? ";
        }
        cql = cql + "ALLOW FILTERING";
        SimpleStatement statement = null;
        if (consignmentId != null && !consignmentId.isEmpty()) {
            statement = SimpleStatement.builder(cql)
                    .addPositionalValue(consignmentId).build();
        }
        if ((consignmentId == null || consignmentId.isEmpty()) && (masterAirwayBill != null && !masterAirwayBill.isEmpty())) {
            statement = SimpleStatement.builder(cql)
                    .addPositionalValue(masterAirwayBill)
                    .build();
        }
        if (consignmentId != null && !consignmentId.isEmpty() && masterAirwayBill != null && !masterAirwayBill.isEmpty()) {
            statement = SimpleStatement.builder(cql)
                    .addPositionalValue(consignmentId)
                    .addPositionalValue(masterAirwayBill)
                    .build();
        }
        if (consignmentId != null && !consignmentId.isEmpty() &&
                masterAirwayBill != null && !masterAirwayBill.isEmpty() &&
                startDate != null && endDate != null) {
            statement = SimpleStatement.builder(cql)
                    .addPositionalValue(consignmentId)
                    .addPositionalValue(masterAirwayBill)
                    .addPositionalValue(startDate)
                    .addPositionalValue(endDate)
                    .build();
        }
        if ((consignmentId == null || consignmentId.isEmpty()) &&
                masterAirwayBill != null && !masterAirwayBill.isEmpty() &&
                startDate != null && endDate != null) {
            statement = SimpleStatement.builder(cql)
                    .addPositionalValue(masterAirwayBill)
                    .addPositionalValue(startDate)
                    .addPositionalValue(endDate)
                    .build();
        }
        if ((consignmentId == null || consignmentId.isEmpty()) &&
                (masterAirwayBill == null || masterAirwayBill.isEmpty()) &&
                startDate != null && endDate != null) {
            statement = SimpleStatement.builder(cql)
                    .addPositionalValue(startDate)
                    .addPositionalValue(endDate)
                    .build();
        }
        return cassandraTemplate.select(statement, Consignment.class);
    }

    @Override
    public List<ItemDetails> findItemDetails(String piecesId) {
        String cql = "SELECT * FROM tblitemdetails WHERE pieceid = ? ALLOW FILTERING";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .addPositionalValue(piecesId)
                .build();
        return cassandraTemplate.select(statement, ItemDetails.class);
    }

    @Override
    public List<PieceDetails> findPiecesDetails(Long consignmentId) {
        String cql = "SELECT * FROM tblpiecesdetails WHERE consignmentid = ? ALLOW FILTERING";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .addPositionalValue(consignmentId)
                .build();
        return cassandraTemplate.select(statement, PieceDetails.class);
    }

    @Override
    public List<ReferenceImageList> findReferenceImageList(Long consignmentId, String typeId) {
        String cql = "SELECT * FROM tblimagereference WHERE consignmentid = ? AND typeid = ? ALLOW FILTERING";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .addPositionalValue(consignmentId)
                .addPositionalValue(typeId)
                .build();
        return cassandraTemplate.select(statement, ReferenceImageList.class);
    }

    @Override
    public Optional<OriginDetails> findOriginDetails(Long consignmentId) {
        String cql = "SELECT * FROM tblorigindetails WHERE consignmentid = ? ALLOW FILTERING";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .addPositionalValue(consignmentId)
                .build();
        return Optional.ofNullable(cassandraTemplate.selectOne(statement, OriginDetails.class));
    }

    @Override
    public Optional<DestinationDetails> findDestinationDetails(Long consignmentId) {
        String cql = "SELECT * FROM tbldestinationdetails WHERE consignmentid = ? ALLOW FILTERING";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .addPositionalValue(consignmentId)
                .build();
        return Optional.ofNullable(cassandraTemplate.selectOne(statement, DestinationDetails.class));
    }

    @Override
    public Optional<ReturnDetails> findReturnDetails(Long consignmentId) {
        String cql = "SELECT * FROM tblreturndetails WHERE consignmentid = ? ALLOW FILTERING";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .addPositionalValue(consignmentId)
                .build();
        return Optional.ofNullable(cassandraTemplate.selectOne(statement, ReturnDetails.class));
    }
}