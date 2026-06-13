package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.serviceType.AddServiceType;
import com.courier.overc360.api.idmaster.primary.model.serviceType.ServiceType;
import com.courier.overc360.api.idmaster.primary.model.serviceType.UpdateServiceType;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ServiceTypeRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.serviceType.FindServiceType;
import com.courier.overc360.api.idmaster.replica.model.serviceType.ReplicaServiceType;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaServiceTypeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaServiceTypeSpecification;
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

@Slf4j
@Service
public class ServiceTypeService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaServiceTypeRepository replicaServiceTypeRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Service Type
     *
     * @param companyId
     * @return
     */
    public ServiceType getServiceType(String companyId, String languageId, String serviceTypeId) {
        Optional<ServiceType> dbServiceType = serviceTypeRepository.findByCompanyIdAndLanguageIdAndServiceTypeIdAndDeletionIndicator(
                companyId, languageId, serviceTypeId, 0L);
        if (dbServiceType.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and serviceTypeId - " + serviceTypeId + " doesn't exists";
            // Error Log
            createServiceTypeLog1(languageId, companyId, serviceTypeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbServiceType.get();
    }

    /**
     * Create Service Type
     *
     * @param addServiceType
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ServiceType createServiceType(AddServiceType addServiceType, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<ServiceType> duplicateServiceType = serviceTypeRepository.findByCompanyIdAndLanguageIdAndServiceTypeIdAndDeletionIndicator(
                    addServiceType.getCompanyId(), addServiceType.getLanguageId(), addServiceType.getServiceTypeId(), 0L);

            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addServiceType.getCompanyId(), addServiceType.getLanguageId(), 0L);

            if (dbCompany.isEmpty()) {
                throw new BadRequestException("The given values : CompanyId - " + addServiceType.getCompanyId()
                        + " and LanguageId - " + addServiceType.getLanguageId() + "  doesn't exists");
            } else if (duplicateServiceType.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : serviceTypeId - " + addServiceType.getServiceTypeId());
            } else {
                log.info("new ServiceType --> {}", addServiceType);
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addServiceType.getLanguageId(), addServiceType.getCompanyId());
                ServiceType newServiceType = new ServiceType();
                BeanUtils.copyProperties(addServiceType, newServiceType, CommonUtils.getNullPropertyNames(addServiceType));
                if ((addServiceType.getServiceTypeId() != null &&
                        (addServiceType.getReferenceField10() != null && addServiceType.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addServiceType.getServiceTypeId() == null || addServiceType.getServiceTypeId().isBlank()) {
                    String NUM_RAN_OBJ = "SERVICETYPE";
                    String SERVICE_TYPE_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for SERVICE_TYPE_ID : " + SERVICE_TYPE_ID);
                    newServiceType.setServiceTypeId(SERVICE_TYPE_ID);
                }
                if (iKeyValuePair != null) {
                    newServiceType.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newServiceType.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addServiceType.getStatusId());
                if (statusDesc != null) {
                    newServiceType.setStatusDescription(statusDesc);
                }
                newServiceType.setDeletionIndicator(0L);
                newServiceType.setCreatedBy(loginUserID);
                newServiceType.setUpdatedBy(loginUserID);
                newServiceType.setCreatedOn(new Date());
                newServiceType.setUpdatedOn(new Date());
                return serviceTypeRepository.save(newServiceType);
            }
        } catch (Exception e) {
            // Error Log
            createServiceTypeLog2(addServiceType, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Service Type
     *
     * @param companyId
     * @param languageId
     * @param serviceTypeId
     * @param loginUserID
     * @param updateServiceType
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ServiceType updateServiceType(String companyId, String languageId, String serviceTypeId, String loginUserID,
                                         UpdateServiceType updateServiceType)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ServiceType dbServicetype = getServiceType(companyId, languageId, serviceTypeId);
            BeanUtils.copyProperties(updateServiceType, dbServicetype, CommonUtils.getNullPropertyNames(updateServiceType));
            if (updateServiceType.getStatusId() != null && !updateServiceType.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateServiceType.getStatusId());
                if (statusDesc != null) {
                    dbServicetype.setStatusDescription(statusDesc);
                }
            }
            dbServicetype.setUpdatedBy(loginUserID);
            dbServicetype.setUpdatedOn(new Date());
            return serviceTypeRepository.save(dbServicetype);
        } catch (Exception e) {
            // Error Log
            createServiceTypeLog(languageId, companyId, serviceTypeId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete ServiceType
     *
     * @param loginUserID
     * @param languageId
     * @param serviceTypeId
     * @param companyId
     */
    public void deleteServiceType(String companyId, String languageId, String serviceTypeId, String loginUserID) {
        ServiceType dbServicetype = getServiceType(companyId, languageId, serviceTypeId);
        if (dbServicetype != null) {
            dbServicetype.setDeletionIndicator(1L);
            dbServicetype.setUpdatedBy(loginUserID);
            dbServicetype.setUpdatedOn(new Date());
            serviceTypeRepository.save(dbServicetype);
        } else {
            // Error Log
            createServiceTypeLog1(languageId, companyId, serviceTypeId, "Error in deleting ServiceTypeId - " + serviceTypeId);
            throw new EntityNotFoundException("Error in deleting ServiceTypeId - " + serviceTypeId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All Service Type Details
     *
     * @return
     */
    public List<ReplicaServiceType> getAllServiceTypes() {
        List<ReplicaServiceType> servicetypeList = replicaServiceTypeRepository.findAll();
        servicetypeList = servicetypeList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return servicetypeList;
    }

    /**
     * Get Service Type
     *
     * @param companyId
     * @return
     */
    public ReplicaServiceType getReplicaServiceType(String companyId, String languageId, String serviceTypeId) {
        Optional<ReplicaServiceType> dbServiceType = replicaServiceTypeRepository.findByCompanyIdAndLanguageIdAndServiceTypeIdAndDeletionIndicator(
                companyId, languageId, serviceTypeId, 0L);
        if (dbServiceType.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and serviceTypeId - " + serviceTypeId + " doesn't exists";
            // Error Log
            createServiceTypeLog1(languageId, companyId, serviceTypeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbServiceType.get();
    }

    /**
     * Find ServiceType
     *
     * @param findServiceType
     * @return
     */
    public List<ReplicaServiceType> findServiceTypes(FindServiceType findServiceType) {

        ReplicaServiceTypeSpecification spec = new ReplicaServiceTypeSpecification(findServiceType);
        List<ReplicaServiceType> results = replicaServiceTypeRepository.findAll(spec);
        log.info("found ServiceTypes --> {}", results);
        return results;
    }

    //=========================================ServiceType_ErrorLog====================================================
    private void createServiceTypeLog(String languageId, String companyId, String serviceTypeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(serviceTypeId);
        errorLog.setMethod("Exception thrown in updateServiceType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createServiceTypeLog1(String languageId, String companyId, String serviceTypeId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(serviceTypeId);
        errorLog.setMethod("Exception thrown in getServiceType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createServiceTypeLog2(AddServiceType addServiceType, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addServiceType.getLanguageId());
        errorLog.setCompanyId(addServiceType.getCompanyId());
        errorLog.setRefDocNumber(addServiceType.getServiceTypeId());
        errorLog.setMethod("Exception thrown in createServiceType");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
