package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.zonemaster.AddZoneMaster;
import com.courier.overc360.api.idmaster.primary.model.zonemaster.UpdateZoneMaster;
import com.courier.overc360.api.idmaster.primary.model.zonemaster.ZoneMaster;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ZoneMasterRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.zonemaster.FindZoneMaster;
import com.courier.overc360.api.idmaster.replica.model.zonemaster.ReplicaZoneMaster;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaZoneMasterRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaZoneTypeMasterRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaZoneMasterSpecification;
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
public class ZoneMasterService {

    @Autowired
    private ReplicaZoneMasterRepository replicaZoneMasterRepository;

    @Autowired
    private ReplicaZoneTypeMasterRepository replicaZoneTypeMasterRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ZoneMasterRepository zoneMasterRepository;

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
     * @param zoneId
     * @param zoneType
     * @return
     */
    public ZoneMaster getZoneMaster(String companyId, String languageId, String zoneId, String zoneType, String hubCode) {
        Optional<ZoneMaster> dbzoneMaster = zoneMasterRepository.findByCompanyIdAndLanguageIdAndZoneIdAndZoneTypeAndHubCodeAndDeletionIndicator
                (companyId, languageId, zoneId, zoneType, hubCode, 0L);
        if (dbzoneMaster.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + ", zoneId - " + zoneId + " and zoneType - " + zoneType + " doesn't exists";
            // Error Log
            createZoneMasterLog1(companyId, languageId, zoneId, zoneType, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbzoneMaster.get();
    }

    /**
     * Create
     *
     * @param addZoneMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ZoneMaster createZoneMaster(AddZoneMaster addZoneMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            zoneMasterRepository.findByCompanyIdAndLanguageIdAndZoneIdAndZoneTypeAndHubCodeAndDeletionIndicator
                            (addZoneMaster.getCompanyId(), addZoneMaster.getLanguageId(), addZoneMaster.getZoneId(), addZoneMaster.getZoneType(),
                                    addZoneMaster.getHubCode(), 0L)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : zoneId - " + addZoneMaster.getZoneId());
                    });
            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                            (addZoneMaster.getCompanyId(), addZoneMaster.getLanguageId(), 0L)
                    .orElseThrow(() -> new BadRequestException("The given values : CompanyId - " + addZoneMaster.getCompanyId()
                            + " and LanguageId - " + addZoneMaster.getLanguageId() + "  doesn't exists"));

            log.info("new ZoneMaster --> {}", addZoneMaster);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addZoneMaster.getLanguageId(), addZoneMaster.getCompanyId());
            ZoneMaster newZoneMaster = new ZoneMaster();
            BeanUtils.copyProperties(addZoneMaster, newZoneMaster, CommonUtils.getNullPropertyNames(addZoneMaster));
            if (iKeyValuePair != null) {
                newZoneMaster.setLanguageDescription(iKeyValuePair.getLangDesc());
                newZoneMaster.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addZoneMaster.getStatusId());
            if (statusDesc != null) {
                newZoneMaster.setStatusDescription(statusDesc);
            }

            // ZoneId auto-gen
            String zoneId = numberRangeService.getNextNumberRange("ZONEID");
            newZoneMaster.setZoneId(zoneId);

            //Set ZoneType Text from zone type master
            String zoneText = replicaZoneTypeMasterRepository.getZoneTypeText(addZoneMaster.getZoneType());
            newZoneMaster.setZoneTypeText(zoneText);

            newZoneMaster.setDeletionIndicator(0L);
            newZoneMaster.setCreatedBy(loginUserID);
            newZoneMaster.setUpdatedBy(loginUserID);
            newZoneMaster.setCreatedOn(new Date());
            newZoneMaster.setUpdatedOn(new Date());
            return zoneMasterRepository.save(newZoneMaster);

        } catch (Exception e) {
            // Error Log
            createZoneMasterLog2(addZoneMaster, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param zoneId
     * @param zoneType
     * @param updateZoneMaster
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ZoneMaster updateZoneMaster(String companyId, String languageId, String zoneId, String zoneType, String hubCode,
                                       UpdateZoneMaster updateZoneMaster, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ZoneMaster dbZoneMaster = getZoneMaster(companyId, languageId, zoneId, zoneType, hubCode);
            BeanUtils.copyProperties(updateZoneMaster, dbZoneMaster, CommonUtils.getNullPropertyNames(updateZoneMaster));

            if (updateZoneMaster.getStatusId() != null && !updateZoneMaster.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateZoneMaster.getStatusId());
                if (statusDesc != null) {
                    dbZoneMaster.setStatusDescription(statusDesc);
                }
            }
            dbZoneMaster.setUpdatedBy(loginUserID);
            dbZoneMaster.setUpdatedOn(new Date());
            return zoneMasterRepository.save(dbZoneMaster);
        } catch (Exception e) {
            // Error Log
            createZoneMasterLog(companyId, languageId, zoneId, zoneType, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * delete
     *
     * @param companyId
     * @param languageId
     * @param zoneId
     * @param zoneType
     * @param loginUserID
     */
    public void deleteZoneMaster(String companyId, String languageId, String zoneId, String  zoneType, String hubCode, String loginUserID){
        ZoneMaster dbzoneMaster = getZoneMaster(companyId,languageId,zoneId, zoneType, hubCode);
        if(dbzoneMaster !=null){
            dbzoneMaster.setDeletionIndicator(1L);
            dbzoneMaster.setUpdatedBy(loginUserID);
            dbzoneMaster.setUpdatedOn(new Date());
            zoneMasterRepository.save(dbzoneMaster);
        } else {
            // Error Log
            createZoneMasterLog1(companyId, languageId, zoneId, zoneType,"Error in deleting ZoneId - " + zoneId);
            throw new EntityNotFoundException("Error in deleting ZoneId - " + zoneId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get all
     * @return
     */
    public List<ReplicaZoneMaster> getAll(){
        List<ReplicaZoneMaster> zoneMasterList = replicaZoneMasterRepository.findAll();
        zoneMasterList = zoneMasterList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return zoneMasterList;
    }

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param zoneId
     * @param zoneType
     * @return
     */
    public ReplicaZoneMaster getReplicaZoneMaster(String companyId, String languageId, String zoneId, String zoneType, String hubCode) {
        Optional<ReplicaZoneMaster> dbzoneMaster = replicaZoneMasterRepository.findByCompanyIdAndLanguageIdAndZoneIdAndZoneTypeAndHubCodeAndDeletionIndicator
                (companyId, languageId, zoneId, zoneType, hubCode,0L);
        if (dbzoneMaster.isEmpty()) {
            String errMsg ="The given values : companyId - " + companyId + ", languageId - " + languageId + ", zoneId - " + zoneId + " and zoneType - " + zoneType + " doesn't exists";
            // Error Log
            createZoneMasterLog1(companyId, languageId, zoneId, zoneType,errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbzoneMaster.get();
    }

    /**
     * Find
     *
     * @param findZoneMaster
     * @return
     */
    public List<ReplicaZoneMaster> findZoneMaster(FindZoneMaster findZoneMaster){
        ReplicaZoneMasterSpecification spec = new ReplicaZoneMasterSpecification(findZoneMaster);
        List<ReplicaZoneMaster> results = replicaZoneMasterRepository.findAll(spec);
        log.info("found ZoneMaster --> {}", results);
        return results;
    }

    //=========================================Zone_MasterLog====================================================

    private void createZoneMasterLog(String companyId, String languageId, String zoneId, String zoneType, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(zoneId);
        errorLog.setReferenceField1(zoneType);
        errorLog.setMethod("Exception thrown in updateZoneMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createZoneMasterLog1(String companyId, String languageId, String zoneId, String zoneType,String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(zoneId);
        errorLog.setReferenceField1(zoneType);
        errorLog.setMethod("Exception thrown in getZoneMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createZoneMasterLog2(AddZoneMaster addZoneMaster, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addZoneMaster.getLanguageId());
        errorLog.setCompanyId(addZoneMaster.getCompanyId());
        errorLog.setRefDocNumber(addZoneMaster.getZoneId());
        errorLog.setReferenceField1(addZoneMaster.getZoneType());
        errorLog.setMethod("Exception thrown in createZoneMaster");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}




