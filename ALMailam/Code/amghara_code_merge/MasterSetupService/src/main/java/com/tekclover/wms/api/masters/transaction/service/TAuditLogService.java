package com.tekclover.wms.api.masters.transaction.service;

import com.tekclover.wms.api.masters.transaction.model.auditlog.TAuditLog;
import com.tekclover.wms.api.masters.transaction.model.auditlog.SearchAuditLog;
import com.tekclover.wms.api.masters.transaction.repository.TAuditLogRepository;
import com.tekclover.wms.api.masters.transaction.repository.specification.AuditLogSpecification;
import com.tekclover.wms.api.masters.transaction.util.CommonUtils;
import com.tekclover.wms.api.masters.transaction.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class TAuditLogService {
	
	@Autowired
	private TAuditLogRepository TAuditLogRepository;
	
	public List<TAuditLog> getAuditLogs () {
		return TAuditLogRepository.findAll();
	}

	/**
	 *
	 * @param auditLogNumber
	 * @return
	 */
	public TAuditLog getAuditLog (String auditLogNumber) {
		return TAuditLogRepository.findByAuditLogNumber(auditLogNumber);
	}

	public Long getAuditLogNumber () {
		Long auditLogNumber;
		if(TAuditLogRepository.getAuditLogNumber() == null) {
			auditLogNumber = 1L;
		} else {
			auditLogNumber = Long.valueOf(TAuditLogRepository.getAuditLogNumber());
		}
		return auditLogNumber;
	}
	/**
	 * createAuditLog
	 * @param newTAuditLog
	 * @param loginUserID 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TAuditLog createAuditLog (TAuditLog newTAuditLog, String loginUserID)
			throws IllegalAccessException, InvocationTargetException {
		
		TAuditLog dbTAuditLog = new TAuditLog();
		BeanUtils.copyProperties(newTAuditLog, dbTAuditLog, CommonUtils.getNullPropertyNames(newTAuditLog));
		dbTAuditLog.setAuditLogNumber(System.currentTimeMillis());
		dbTAuditLog.setAuditFileNumber(String.valueOf(dbTAuditLog.getAuditLogNumber()));
		dbTAuditLog.setCreatedBy(loginUserID);
		dbTAuditLog.setUpdatedBy(loginUserID);
		dbTAuditLog.setCreatedOn(new Date());
		dbTAuditLog.setUpdatedOn(new Date());
		log.info("dbAuditLog----------> : " + dbTAuditLog);
		return TAuditLogRepository.save(dbTAuditLog);
	}
	
	/**
	 * deleteAuditLog
	 * @param auditLogNumber
	 */
	public void deleteAuditLog (String auditLogNumber, String loginUserID ) {
		TAuditLog auditlog = getAuditLog(auditLogNumber);
		if ( auditlog != null) {
			auditlog.setDeletionIndicator(1L);
			auditlog.setUpdatedBy(loginUserID);
			TAuditLogRepository.save(auditlog);
//			auditLogRepository.delete(auditlog);
		} else {
			throw new EntityNotFoundException("Error in deleting Id: " + auditLogNumber);
		}
	}

	/**
	 *
	 * @param searchAuditLog
	 * @return
	 * @throws ParseException
	 */
	public Stream<TAuditLog> findAuditLog(SearchAuditLog searchAuditLog) throws ParseException {
		if (searchAuditLog.getStartCreatedOn() != null && searchAuditLog.getEndCreatedOn() != null) {
			Date[] dates = DateUtils.addTimeToDatesForSearch(searchAuditLog.getStartCreatedOn(), searchAuditLog.getEndCreatedOn());
			searchAuditLog.setStartCreatedOn(dates[0]);
			searchAuditLog.setEndCreatedOn(dates[1]);
		}
		AuditLogSpecification spec = new AuditLogSpecification(searchAuditLog);
		Stream<TAuditLog> results = TAuditLogRepository.stream(spec, TAuditLog.class);
//		log.info("results: " + results);
		return results;
	}
}