package com.courier.overc360.api.midmile.replica.repository;


import com.courier.overc360.api.midmile.primary.model.notificationmessage.ReadMessages;
import com.courier.overc360.api.midmile.replica.model.notificationmessage.ReplicaReadMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReplicaReadMessagesRepository extends JpaRepository<ReplicaReadMessages, Long>, JpaSpecificationExecutor<ReplicaReadMessages> {
}
