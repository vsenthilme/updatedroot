package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.serviceprovider.AddServiceProvider;
import com.courier.overc360.api.idmaster.primary.model.serviceprovider.ServiceProvider;
import com.courier.overc360.api.idmaster.primary.model.serviceprovider.UpdateServiceProvider;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ServiceProviderRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.serviceprovider.FindServiceProvider;
import com.courier.overc360.api.idmaster.replica.model.serviceprovider.ReplicaServiceProvider;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaServiceProviderRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaServiceProviderSpecification;
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
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ReplicaServiceProviderRepository replicaServiceProviderRepository;

    @Autowired
    private NumberRangeService numberRangeService;

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

    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param serviceProvidersId
     * @return
     */
    public ServiceProvider getServiceProvider(String companyId, String languageId, String serviceProvidersId) {
        Optional<ServiceProvider> dbServiceProvider = serviceProviderRepository.findByCompanyIdAndLanguageIdAndServiceProvidersIdAndDeletionIndicator
                (companyId, languageId, serviceProvidersId, 0l);
        if (dbServiceProvider.isEmpty()) {
            //Error Log
            createServiceProviderLog1(languageId, companyId, serviceProvidersId, " ServiceProvidersId - " + serviceProvidersId + " and given values doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " serviceProvidersId - " + serviceProvidersId + " doesn't exists");
        }
        return dbServiceProvider.get();
    }

    /**
     * Create
     *
     * @param addServiceProvider
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ServiceProvider createServiceProvider(AddServiceProvider addServiceProvider, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            serviceProviderRepository.findByCompanyIdAndLanguageIdAndServiceProvidersIdAndDeletionIndicator
                            (addServiceProvider.getCompanyId(), addServiceProvider.getLanguageId(), addServiceProvider.getServiceProvidersId(), 0L)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : serviceProvidersId - " + addServiceProvider.getServiceProvidersId());
                    });
            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                            (addServiceProvider.getCompanyId(), addServiceProvider.getLanguageId(), 0L)
                    .orElseThrow(() -> new BadRequestException("The given values : CompanyId - " + addServiceProvider.getCompanyId()
                            + " and LanguageId - " + addServiceProvider.getLanguageId() + "  doesn't exists"));

            log.info("new ServiceProvider --> " + addServiceProvider);
            ServiceProvider newServiceProvider = new ServiceProvider();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addServiceProvider.getLanguageId(), addServiceProvider.getCompanyId());
            BeanUtils.copyProperties(addServiceProvider, newServiceProvider, CommonUtils.getNullPropertyNames(addServiceProvider));
            if (iKeyValuePair != null) {
                newServiceProvider.setCompanyName(iKeyValuePair.getCompanyDesc());
                newServiceProvider.setLanguageDescription(iKeyValuePair.getLangDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addServiceProvider.getStatusId());
            if (statusDesc != null) {
                newServiceProvider.setStatusDescription(statusDesc);
            }
            //Save without spacing
            newServiceProvider.setServiceProvidersId(newServiceProvider.getServiceProvidersId().replaceAll("\\s+",""));

            newServiceProvider.setDeletionIndicator(0l);
            newServiceProvider.setCreatedBy(loginUserID);
            newServiceProvider.setCreatedOn(new Date());
            newServiceProvider.setUpdatedBy(loginUserID);
            newServiceProvider.setUpdatedOn(new Date());
            return serviceProviderRepository.save(newServiceProvider);
        } catch (Exception e) {
            // Error Log
            createServiceProviderLog2(addServiceProvider, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param serviceProvidersId
     * @param updateServiceProvider
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public ServiceProvider updateServiceProvider(String companyId, String languageId, String serviceProvidersId,
                                                 UpdateServiceProvider updateServiceProvider, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ServiceProvider dbServiceProvider = getServiceProvider(companyId, languageId, serviceProvidersId);
            BeanUtils.copyProperties(updateServiceProvider, dbServiceProvider, CommonUtils.getNullPropertyNames(updateServiceProvider));

            if (updateServiceProvider.getStatusId() != null && !updateServiceProvider.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateServiceProvider.getStatusId());
                if (statusDesc != null) {
                    dbServiceProvider.setStatusDescription(statusDesc);
                }
            }
            dbServiceProvider.setUpdatedBy(loginUserID);
            dbServiceProvider.setUpdatedOn(new Date());
            return serviceProviderRepository.save(dbServiceProvider);
        } catch (Exception e) {
            // Error Log
            createServiceProviderLog(companyId, languageId, serviceProvidersId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param serviceProvidersId
     * @param loginUserID
     */
    public void deleteServiceProvider(String companyId, String languageId, String serviceProvidersId, String loginUserID){
        ServiceProvider dbServiceProvider = getServiceProvider(companyId,languageId,serviceProvidersId);
        if(dbServiceProvider !=null){
            dbServiceProvider.setDeletionIndicator(1L);
            dbServiceProvider.setUpdatedBy(loginUserID);
            dbServiceProvider.setUpdatedOn(new Date());
            serviceProviderRepository.save(dbServiceProvider);
        } else {
            // Error Log
            createServiceProviderLog1(companyId, languageId, serviceProvidersId, "Error in deleting ServiceProvidersId - " + serviceProvidersId);
            throw new EntityNotFoundException("Error in deleting ServiceProvidersId - " + serviceProvidersId);
        }
    }
    /*======================================================REPLICA=====================================================*/


    public List<ReplicaServiceProvider> getAll(){
        List<ReplicaServiceProvider> serviceProviderLists = replicaServiceProviderRepository.findAll();
        serviceProviderLists = serviceProviderLists.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return serviceProviderLists;
    }


    public ReplicaServiceProvider getReplicaServiceProvider(String companyId, String languageId, String serviceProvidersId) {
        Optional<ReplicaServiceProvider> dbServiceProvider = replicaServiceProviderRepository.findByCompanyIdAndLanguageIdAndServiceProvidersIdAndDeletionIndicator
                (companyId, languageId, serviceProvidersId, 0L);
        if (dbServiceProvider.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and serviceProvidersId - " + serviceProvidersId + " doesn't exists";
            // Error Log
            createServiceProviderLog1(companyId, languageId, serviceProvidersId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbServiceProvider.get();
    }


    public List<ReplicaServiceProvider> findServiceProvider(FindServiceProvider findServiceProvider){
        ReplicaServiceProviderSpecification spec = new ReplicaServiceProviderSpecification(findServiceProvider);
        List<ReplicaServiceProvider> results = replicaServiceProviderRepository.findAll(spec);
        log.info("found ServiceProvider --> {}", results);
        return results;
    }

    //=========================================Service_ProviderLog====================================================

    private void createServiceProviderLog(String companyId, String languageId, String serviceProvidersId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(serviceProvidersId);
        errorLog.setMethod("Exception thrown in updateServiceProvider");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createServiceProviderLog1(String companyId, String languageId, String serviceProvidersId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(serviceProvidersId);
        errorLog.setMethod("Exception thrown in getServiceProvider");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createServiceProviderLog2(AddServiceProvider addServiceProvider, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addServiceProvider.getLanguageId());
        errorLog.setCompanyId(addServiceProvider.getCompanyId());
        errorLog.setRefDocNumber(addServiceProvider.getServiceProvidersId());
        errorLog.setMethod("Exception thrown in createServiceProvider");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}






