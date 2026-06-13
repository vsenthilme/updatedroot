package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.ItemDetails;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemDetailsRepository extends CassandraRepository<ItemDetails, Long>, DynamicNativeQuery {
}