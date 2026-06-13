package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.v2.ImageListV2;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageV2Repository extends CassandraRepository<ImageListV2, Long> {
}