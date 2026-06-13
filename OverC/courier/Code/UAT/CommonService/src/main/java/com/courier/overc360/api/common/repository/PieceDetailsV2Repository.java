package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.v2.PieceDetailsV2;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PieceDetailsV2Repository extends CassandraRepository<PieceDetailsV2, Long>{
}