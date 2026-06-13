package com.mnrclara.api.setup.repository;

import com.mnrclara.api.setup.model.timetickettracting.TimeTicketTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface TimeTicketTrackingRepository extends JpaRepository<TimeTicketTracking, Long> {

    List<TimeTicketTracking> findByProcessedStatus(Long status);

    @Modifying
    @Query(value = "update tbltimetracking set PROCESSED_STATUS = 1 where TICKET_ID = :ticketId ", nativeQuery = true)
    public void updateNotification(@Param("ticketId") Long ticketId);
}
