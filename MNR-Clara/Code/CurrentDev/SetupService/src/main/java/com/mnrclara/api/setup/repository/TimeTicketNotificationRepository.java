package com.mnrclara.api.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mnrclara.api.setup.model.notification.TimeTicketNotification;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TimeTicketNotificationRepository extends JpaRepository<TimeTicketNotification, Long> {
	
	public TimeTicketNotification findByTimeKeeperCode(String timeKeeperCode);
	public TimeTicketNotification findByTimeKeeperCodeAndWeekOfYear(String timeKeeperCode, Long weekNumber);

	@Query(value = "select noti_status as notificationStatus from tbltimeticketnotification where" +
			" tk_code = :timeKeeperCode and week_of_yr = :weekOfYear ", nativeQuery = true)
	public Long notificationStatus(@Param("timeKeeperCode") String timeKeeperCode,
								   @Param("weekOfYear") Long weekOfYear);

	@Modifying
	@Query(value = "Update tbltimeticketnotification set noti_status = 1 where tk_code = :timeKeeperCode and \n " +
			" week_of_yr = :weekOfYear ", nativeQuery = true)
	public void updateNotification(@Param("timeKeeperCode") String timeKeeperCode,
								   @Param("weekOfYear") Long weekOfYear);
}