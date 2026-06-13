package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.vehicle.AddVehicle;
import com.courier.overc360.api.idmaster.primary.model.vehicle.UpdateVehicle;
import com.courier.overc360.api.idmaster.primary.model.vehicle.Vehicle;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.VehicleRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.vehicle.FindVehicle;
import com.courier.overc360.api.idmaster.replica.model.vehicle.ReplicaVehicle;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaVehicleRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaVehicleSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaVehicleRepository replicaVehicleRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogRepository errorLogRepository;
    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Vehicle
     *
     * @param companyId
     * @param languageId
     * @param vehicleRegNumber
     * @return
     */
    public Vehicle getVehicle(String companyId, String languageId, String vehicleRegNumber) {
        Optional<Vehicle> dbVehicle = vehicleRepository.findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator(
                companyId, languageId, vehicleRegNumber, 0L);
        if (dbVehicle.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and vehicleRegNumber - " + vehicleRegNumber + " doesn't exists";
            // Error Log
            createVehicleLog1(languageId, companyId, vehicleRegNumber, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbVehicle.get();
    }

    /**
     * Create Vehicle
     *
     * @param addVehicle
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Vehicle createVehicle(AddVehicle addVehicle, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addVehicle.getCompanyId(), addVehicle.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addVehicle.getCompanyId()
                        + " and LanguageId - " + addVehicle.getLanguageId() + " doesn't exists");
            }

            boolean duplicateVehicle = replicaVehicleRepository.existsByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator(
                    addVehicle.getCompanyId(), addVehicle.getLanguageId(), addVehicle.getVehicleRegNumber(), 0L);
            if (duplicateVehicle) {
                throw new BadRequestException("Record is getting Duplicated with the given values : vehicleRegNumber - " + addVehicle.getVehicleRegNumber());
            }

            log.info("new Vehicle --> {}", addVehicle);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addVehicle.getLanguageId(), addVehicle.getCompanyId());
            Vehicle newVehicle = new Vehicle();
            BeanUtils.copyProperties(addVehicle, newVehicle, CommonUtils.getNullPropertyNames(addVehicle));
            if (iKeyValuePair != null) {
                newVehicle.setLanguageDescription(iKeyValuePair.getLangDesc());
                newVehicle.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addVehicle.getStatusId());
            if (statusDesc != null) {
                newVehicle.setStatusDescription(statusDesc);
            }
            //Save without spacing
            newVehicle.setVehicleRegNumber(newVehicle.getVehicleRegNumber().replaceAll("\\s+",""));

            newVehicle.setDeletionIndicator(0L);
            newVehicle.setCreatedBy(loginUserID);
            newVehicle.setCreatedOn(new Date());
            newVehicle.setUpdatedBy(loginUserID);
            newVehicle.setUpdatedOn(new Date());
            return vehicleRepository.save(newVehicle);

        } catch (Exception e) {
            // Error Log
            createVehicleLog2(addVehicle, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Vehicle
     *
     * @param companyId
     * @param languageId
     * @param vehicleRegNumber
     * @param loginUserID
     * @param updateVehicle
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Vehicle updateVehicle(String companyId, String languageId, String vehicleRegNumber, String loginUserID,
                                 UpdateVehicle updateVehicle)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Vehicle dbVehicle = getVehicle(companyId, languageId, vehicleRegNumber);
            BeanUtils.copyProperties(updateVehicle, dbVehicle, CommonUtils.getNullPropertyNames(updateVehicle));
            if (updateVehicle.getStatusId() != null && !updateVehicle.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateVehicle.getStatusId());
                if (statusDesc != null) {
                    dbVehicle.setStatusDescription(statusDesc);
                }
            }
            dbVehicle.setUpdatedBy(loginUserID);
            dbVehicle.setUpdatedOn(new Date());
            return vehicleRepository.save(dbVehicle);
        } catch (Exception e) {
            // Error Log
            createVehicleLog(languageId, companyId, vehicleRegNumber, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Vehicle
     *
     * @param languageId
     * @param companyId
     * @param vehicleRegNumber
     * @param loginUserID
     */
    public void deleteVehicle(String languageId, String companyId, String vehicleRegNumber, String loginUserID) {
        Vehicle dbVehicle = getVehicle(languageId, companyId, vehicleRegNumber);
        if (dbVehicle != null) {
            dbVehicle.setDeletionIndicator(1L);
            dbVehicle.setUpdatedBy(loginUserID);
            dbVehicle.setUpdatedOn(new Date());
            vehicleRepository.save(dbVehicle);
        } else {
            createVehicleLog1(languageId, companyId, vehicleRegNumber, "Error in deleting vehicleRegNumber - " + vehicleRegNumber);
            throw new BadRequestException("Error in deleting vehicleRegNumber - " + vehicleRegNumber);
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get all Vehicle Details
     *
     * @return
     */
    public List<ReplicaVehicle> getAllVehicles() {
        List<ReplicaVehicle> vehicleList = replicaVehicleRepository.findAll();
        vehicleList = vehicleList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return vehicleList;
    }

    /**
     * Get Vehicle
     *
     * @param languageId
     * @param companyId
     * @param vehicleRegNumber
     * @return
     */
    public ReplicaVehicle getReplicaVehicle(String languageId, String companyId, String vehicleRegNumber) {

        Optional<ReplicaVehicle> dbVehicle = replicaVehicleRepository.findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator
                (languageId, companyId, vehicleRegNumber, 0L);

        if (dbVehicle.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and vehicleRegNumber - " + vehicleRegNumber + " doesn't exists";
            // Error Log
            createVehicleLog1(languageId, companyId, vehicleRegNumber, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbVehicle.get();
    }

    /**
     * Find Vehicle
     *
     * @param findVehicle
     * @return
     */
    public List<ReplicaVehicle> findVehicles(FindVehicle findVehicle) {

        ReplicaVehicleSpecification spec = new ReplicaVehicleSpecification(findVehicle);
        List<ReplicaVehicle> results = replicaVehicleRepository.findAll(spec);
        log.info("found Vehicles --> {}", results);
        return results;
    }

    //========================================Vehicle_ErrorLog=================================================
    private void createVehicleLog(String languageId, String companyId, String vehicleRegNumber, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(vehicleRegNumber);
        errorLog.setMethod("Exception thrown in updateVehicle");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createVehicleLog1(String languageId, String companyId, String vehicleRegNumber, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(vehicleRegNumber);
        errorLog.setMethod("Exception thrown in getVehicle");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createVehicleLog2(AddVehicle addVehicle, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addVehicle.getLanguageId());
        errorLog.setCompanyId(addVehicle.getCompanyId());
        errorLog.setRefDocNumber(addVehicle.getVehicleRegNumber());
        errorLog.setMethod("Exception thrown in createVehicle");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}
