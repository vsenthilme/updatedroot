package com.courier.overc360.api.idmaster.replica.repository;


import com.courier.overc360.api.idmaster.replica.model.hhtnotification.ReplicaNotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReplicaNotificationMessageRepository extends JpaRepository<ReplicaNotificationMessage, Long>, JpaSpecificationExecutor<ReplicaNotificationMessage> {


}
