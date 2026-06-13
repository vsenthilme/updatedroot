package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.v2.ItemDetailsV2;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDetailsV2Repository extends CassandraRepository<ItemDetailsV2, Long> {
}