package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.ReturnDetails;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ReturnDetailsRepository extends CassandraRepository<ReturnDetails, Long>, DynamicNativeQuery {
}