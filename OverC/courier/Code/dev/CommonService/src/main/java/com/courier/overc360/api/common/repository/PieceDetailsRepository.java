package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.PieceDetails;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PieceDetailsRepository extends CassandraRepository<PieceDetails, Long>, DynamicNativeQuery {
}