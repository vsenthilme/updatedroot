package com.courier.overc360.api.common.repository;

import com.courier.overc360.api.common.model.consignment.ReferenceImageList;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ReferenceImageRepository extends CassandraRepository<ReferenceImageList, Long>, DynamicNativeQuery {
}