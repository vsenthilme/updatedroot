package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.zonetypemaster.AddZoneTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.zonetypemaster.ZoneTypeMaster;
import com.courier.overc360.api.idmaster.primary.model.zonetypemaster.UpdateZoneTypeMaster;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ZoneTypeMasterRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.zonetypemaster.FindZoneTypeMaster;
import com.courier.overc360.api.idmaster.replica.model.zonetypemaster.ReplicaZoneTypeMaster;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaZoneTypeMasterRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaZoneTypeMasterSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ZoneTypeMasterService {

    @Autowired
    private ReplicaZoneTypeMasterRepository replicaZoneTypeMasterRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ZoneTypeMasterRepository zoneTypeMasterRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param zoneTypeId
     * @return
     */
    public ZoneTypeMaster getZoneTypeMaster(String companyId, String languageId, String zoneTypeId) {
        Optional<ZoneTypeMaster> dbZoneTypeMaster = zoneTypeMasterRepository.findByCompanyIdAndLanguageIdAndZoneTypeIdAndDeletionIndicator
                (companyId, languageId, zoneTypeId,0L);
        if (dbZoneTypeMaster.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + "and zoneTypeId - " + zoneTypeId + " doesn't exists";
            // Error Log
            createZoneTypeMasterLog1(companyId, languageId, zoneTypeId,errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbZoneTypeMaster.get();
    }

    /**
     * Create
     *
     * @param addZoneTypeMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ZoneTypeMaster createZoneTypeMaster(AddZoneTypeMaster addZoneTypeMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            zoneTypeMasterRepository.findByCompanyIdAndLanguageIdAndZoneTypeIdAndDeletionIndicator
                            (addZoneTypeMaster.getCompanyId(), addZoneTypeMaster.getLanguageId(), addZoneTypeMaster.getZoneTypeId(), 0L)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : zoneTypeId - " + addZoneTypeMaster.getZoneTypeId());
                    });
            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                            (addZoneTypeMaster.getCompanyId(), addZoneTypeMaster.getLanguageId(), 0L)
                    .orElseThrow(() -> new BadRequestException("The given values : CompanyId - " + addZoneTypeMaster.getCompanyId()
                            + " and LanguageId - " + addZoneTypeMaster.getLanguageId() + "  doesn't exists"));

            log.info("new ZoneTypeMaster --> {}", addZoneTypeMaster);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addZoneTypeMaster.getLanguageId(), addZoneTypeMaster.getCompanyId());
            ZoneTypeMaster newZoneTypeMaster = new ZoneTypeMaster();
            BeanUtils.copyProperties(addZoneTypeMaster, newZoneTypeMaster, CommonUtils.getNullPropertyNames(addZoneTypeMaster));
            if (iKeyValuePair != null) {
                newZoneTypeMaster.setLanguageDescription(iKeyValuePair.getLangDesc());
                newZoneTypeMaster.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addZoneTypeMaster.getStatusId());
            if (statusDesc != null) {
                newZoneTypeMaster.setStatusDescription(statusDesc);
            }
            //Save without spacing
//            newZoneTypeMaster.setZoneTypeId(newZoneTypeMaster.getZoneTypeId().replaceAll("\\s+", ""));

            // ZoneTypeId AutoGen
            if ((newZoneTypeMaster.getZoneTypeId() != null &&
                    (newZoneTypeMaster.getReferenceField10() != null && newZoneTypeMaster.getReferenceField10().equalsIgnoreCase("true"))) ||
                    newZoneTypeMaster.getZoneTypeId() == null || newZoneTypeMaster.getZoneTypeId().isBlank()) {
                String ZONETYPEID = numberRangeService.getNextNumberRange("ZONETYPEID");
                log.info("next Value from NumberRange for ROUTE_ID : " + ZONETYPEID);
                newZoneTypeMaster.setZoneTypeId(ZONETYPEID);
            }

            newZoneTypeMaster.setDeletionIndicator(0L);
            newZoneTypeMaster.setCreatedBy(loginUserID);
            newZoneTypeMaster.setUpdatedBy(loginUserID);
            newZoneTypeMaster.setCreatedOn(new Date());
            newZoneTypeMaster.setUpdatedOn(new Date());
            return zoneTypeMasterRepository.save(newZoneTypeMaster);

        } catch (Exception e) {
            // Error Log
            createZoneTypeMasterLog2(addZoneTypeMaster, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param zoneTypeId
     * @param updateZoneTypeMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public ZoneTypeMaster updateZoneTypeMaster(String companyId, String languageId, String zoneTypeId,
                                                   UpdateZoneTypeMaster updateZoneTypeMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ZoneTypeMaster dbZoneTypeMaster = getZoneTypeMaster(companyId, languageId, zoneTypeId);
            BeanUtils.copyProperties(updateZoneTypeMaster, dbZoneTypeMaster, CommonUtils.getNullPropertyNames(updateZoneTypeMaster));

            if (updateZoneTypeMaster.getStatusId() != null && !updateZoneTypeMaster.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateZoneTypeMaster.getStatusId());
                if (statusDesc != null) {
                    dbZoneTypeMaster.setStatusDescription(statusDesc);
                }
            }
            dbZoneTypeMaster.setUpdatedBy(loginUserID);
            dbZoneTypeMaster.setUpdatedOn(new Date());
            return zoneTypeMasterRepository.save(dbZoneTypeMaster);
        } catch (Exception e) {
            // Error Log
            createZoneTypeMasterLog(companyId, languageId, zoneTypeId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * delete
     *
     * @param companyId
     * @param languageId
     * @param zoneTypeId
     * @param loginUserID
     */
    public void deleteZoneTypeMaster(String companyId, String languageId, String zoneTypeId, String loginUserID) {
        ZoneTypeMaster dbZoneTypeMaster = getZoneTypeMaster(companyId, languageId, zoneTypeId);
        if (dbZoneTypeMaster != null) {
            dbZoneTypeMaster.setDeletionIndicator(1L);
            dbZoneTypeMaster.setUpdatedBy(loginUserID);
            dbZoneTypeMaster.setUpdatedOn(new Date());
            zoneTypeMasterRepository.save(dbZoneTypeMaster);
        } else {
            // Error Log
            createZoneTypeMasterLog1(companyId, languageId, zoneTypeId, "Error in deleting ZoneTypeId - " + zoneTypeId);
            throw new EntityNotFoundException("Error in deleting ZoneTypeId - " + zoneTypeId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get all
     *
     * @return
     */
    public List<ReplicaZoneTypeMaster> getAll() {
        List<ReplicaZoneTypeMaster> zoneTypeMasterList = replicaZoneTypeMasterRepository.findAll();
        zoneTypeMasterList = zoneTypeMasterList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return zoneTypeMasterList;
    }

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param zoneTypeId
     * @return
     */
    public ReplicaZoneTypeMaster getReplicaZoneTypeMaster(String companyId, String languageId, String zoneTypeId) {
        Optional<ReplicaZoneTypeMaster> dbZoneTypeMaster = replicaZoneTypeMasterRepository.findByCompanyIdAndLanguageIdAndZoneTypeIdAndDeletionIndicator
                (companyId, languageId, zoneTypeId, 0L);
        if (dbZoneTypeMaster.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and zoneTypeId - " + zoneTypeId + " doesn't exists";
            // Error Log
            createZoneTypeMasterLog1(companyId, languageId, zoneTypeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbZoneTypeMaster.get();
    }

    /**
     * Find
     *
     * @param findZoneTypeMaster
     * @return
     */
    public List<ReplicaZoneTypeMaster> findZoneTypeMaster(FindZoneTypeMaster findZoneTypeMaster) {
        ReplicaZoneTypeMasterSpecification spec = new ReplicaZoneTypeMasterSpecification(findZoneTypeMaster);
        List<ReplicaZoneTypeMaster> results = replicaZoneTypeMasterRepository.findAll(spec);
        log.info("found ZoneTypeMaster --> {}", results);
        return results;
    }

    //=========================================Zone_Type_MasterLog====================================================

    private void createZoneTypeMasterLog(String companyId, String languageId, String zoneTypeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(zoneTypeId);
        errorLog.setMethod("Exception thrown in updateZoneTypeMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createZoneTypeMasterLog1(String companyId, String languageId, String zoneTypeId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(zoneTypeId);
        errorLog.setMethod("Exception thrown in getZoneTypeMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createZoneTypeMasterLog2(AddZoneTypeMaster addZoneTypeMaster, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addZoneTypeMaster.getLanguageId());
        errorLog.setCompanyId(addZoneTypeMaster.getCompanyId());
        errorLog.setRefDocNumber(addZoneTypeMaster.getZoneTypeId());
        errorLog.setMethod("Exception thrown in createTypeTypeMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}
