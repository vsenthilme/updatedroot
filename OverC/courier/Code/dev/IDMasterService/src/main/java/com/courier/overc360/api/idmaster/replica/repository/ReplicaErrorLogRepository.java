package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.errorlog.ReplicaErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReplicaErrorLogRepository extends JpaRepository<ReplicaErrorLog, String>,
        JpaSpecificationExecutor<ReplicaErrorLog> {

}
