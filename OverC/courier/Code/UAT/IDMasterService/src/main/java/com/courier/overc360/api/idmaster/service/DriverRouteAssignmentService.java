package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment.AddDriverRouteAssignment;
import com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment.DriverRouteAssignment;
import com.courier.overc360.api.idmaster.primary.model.driverRouteAssignment.UpdateDriverRouteAssignment;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.DriverRouteAssignmentRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.driverrouteassignment.FindDriverRouteAssignment;
import com.courier.overc360.api.idmaster.replica.model.driverrouteassignment.ReplicaDriverRouteAssignment;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaDriverRouteAssignmentRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaDriverRouteAssignmentSpecification;
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
public class DriverRouteAssignmentService {

    @Autowired
    private DriverRouteAssignmentRepository driverRouteAssignmentRepository;

    @Autowired
    private ReplicaDriverRouteAssignmentRepository replicaDriverRouteAssignmentRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

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
    public DriverRouteAssignment getDriverRouteAssignment(String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode) {
        Optional<DriverRouteAssignment> dbDriverRouteAssignment = driverRouteAssignmentRepository.findByCompanyIdAndLanguageIdAndCourierIdAndRouteIdAndVehicleRegNumberAndAssignedHubCodeAndDeletionIndicator(
                companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode, 0L);
        if (dbDriverRouteAssignment.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    ", courierId - " + courierId + ", routeId - " + routeId + " vehicleRegNumber - " + vehicleRegNumber + " and assignedHubCode - " + assignedHubCode + " doesn't exists";
            // Error Log
            createDriverRouteAssignmentLog1(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbDriverRouteAssignment.get();
    }

    /**
     * Create DriverRouteAssignment
     *
     * @param addDriverRouteAssignment
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public DriverRouteAssignment createDriverRouteAssignment(AddDriverRouteAssignment addDriverRouteAssignment, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addDriverRouteAssignment.getCompanyId(), addDriverRouteAssignment.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addDriverRouteAssignment.getCompanyId()
                        + " and LanguageId - " + addDriverRouteAssignment.getLanguageId() + " doesn't exists");
            }

            boolean duplicateDriverRouteAssignment = replicaDriverRouteAssignmentRepository.existsByCompanyIdAndLanguageIdAndCourierIdAndRouteIdAndVehicleRegNumberAndAssignedHubCodeAndDeletionIndicator(
                    addDriverRouteAssignment.getCompanyId(), addDriverRouteAssignment.getLanguageId(), addDriverRouteAssignment.getCourierId(), addDriverRouteAssignment.getRouteId(), addDriverRouteAssignment.getVehicleRegNumber(), addDriverRouteAssignment.getAssignedHubCode(), 0L);
            if (duplicateDriverRouteAssignment) {
                throw new BadRequestException("Record is getting Duplicated with the given values :  courierId - " + addDriverRouteAssignment.getCourierId());
            }

            log.info("new DriverRouteAssignment --> {}", addDriverRouteAssignment);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addDriverRouteAssignment.getLanguageId(), addDriverRouteAssignment.getCompanyId());
            DriverRouteAssignment newDriverRouteAssignment = new DriverRouteAssignment();
            BeanUtils.copyProperties(addDriverRouteAssignment, newDriverRouteAssignment, CommonUtils.getNullPropertyNames(addDriverRouteAssignment));
            if (iKeyValuePair != null) {
                newDriverRouteAssignment.setLanguageDescription(iKeyValuePair.getLangDesc());
                newDriverRouteAssignment.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addDriverRouteAssignment.getStatusId());
            if (statusDesc != null) {
                newDriverRouteAssignment.setStatusDescription(statusDesc);
            }
            //Save without spacing
            newDriverRouteAssignment.setCourierId(newDriverRouteAssignment.getCourierId().replaceAll("\\s+",""));

            newDriverRouteAssignment.setDeletionIndicator(0L);
            newDriverRouteAssignment.setCreatedBy(loginUserID);
            newDriverRouteAssignment.setCreatedOn(new Date());
            newDriverRouteAssignment.setUpdatedBy(loginUserID);
            newDriverRouteAssignment.setUpdatedOn(new Date());
            return driverRouteAssignmentRepository.save(newDriverRouteAssignment);

        } catch (Exception e) {
            // Error Log
            createDriverRouteAssignmentLog2(addDriverRouteAssignment, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update DriverRouteAssignment
     *
     * @param companyId
     * @param languageId
     * @param courierId
     * @param routeId
     * @param vehicleRegNumber
     * @param assignedHubCode
     * @param loginUserID
     * @param updateDriverRouteAssignment
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public DriverRouteAssignment updateDriverRouteAssignment(String companyId, String languageId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, String loginUserID,
                                                             UpdateDriverRouteAssignment updateDriverRouteAssignment)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            DriverRouteAssignment dbDriverRouteAssignment = getDriverRouteAssignment(companyId, languageId, courierId, routeId, vehicleRegNumber, assignedHubCode);
            BeanUtils.copyProperties(updateDriverRouteAssignment, dbDriverRouteAssignment, CommonUtils.getNullPropertyNames(updateDriverRouteAssignment));
            if (updateDriverRouteAssignment.getStatusId() != null && !updateDriverRouteAssignment.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateDriverRouteAssignment.getStatusId());
                if (statusDesc != null) {
                    dbDriverRouteAssignment.setStatusDescription(statusDesc);
                }
            }
            dbDriverRouteAssignment.setUpdatedBy(loginUserID);
            dbDriverRouteAssignment.setUpdatedOn(new Date());
            return driverRouteAssignmentRepository.save(dbDriverRouteAssignment);
        } catch (Exception e) {
            // Error Log
            createDriverRouteAssignmentLog(languageId, companyId, courierId, routeId, vehicleRegNumber, assignedHubCode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete DriverRouteAssignment
     *
     * @param languageId
     * @param companyId
     * @param courierId
     * @param routeId
     * @param vehicleRegNumber
     * @param assignedHubCode
     * @param loginUserID
     */
    public void deleteDriverRouteAssignment(String languageId, String companyId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, String loginUserID) {
        DriverRouteAssignment dbDriverRouteAssignment = getDriverRouteAssignment(languageId, companyId, courierId, routeId, vehicleRegNumber, assignedHubCode);
        if (dbDriverRouteAssignment != null) {
            dbDriverRouteAssignment.setDeletionIndicator(1L);
            dbDriverRouteAssignment.setUpdatedBy(loginUserID);
            dbDriverRouteAssignment.setUpdatedOn(new Date());
            driverRouteAssignmentRepository.save(dbDriverRouteAssignment);
        } else {
            createDriverRouteAssignmentLog1(languageId, companyId, courierId, routeId, vehicleRegNumber, assignedHubCode, "Error in deleting courierId - " + courierId);
            throw new BadRequestException("Error in deleting courierId - " + courierId);
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get all DriverRouteAssignment Details
     *
     * @return
     */
    public List<ReplicaDriverRouteAssignment> getAllDriverRouteAssignments() {
        List<ReplicaDriverRouteAssignment> driverRouteAssignmentList = replicaDriverRouteAssignmentRepository.findAll();
        driverRouteAssignmentList = driverRouteAssignmentList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return driverRouteAssignmentList;
    }

    /**
     * Get DriverRouteAssignment
     *
     * @param languageId
     * @param companyId
     * @param courierId
     * @param routeId
     * @param vehicleRegNumber
     * @param assignedHubCode
     * @return
     */
    public ReplicaDriverRouteAssignment getReplicaDriverRouteAssignment(String languageId, String companyId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode) {

        Optional<ReplicaDriverRouteAssignment> dbDriverRouteAssignment = replicaDriverRouteAssignmentRepository.findByCompanyIdAndLanguageIdAndCourierIdAndRouteIdAndVehicleRegNumberAndAssignedHubCodeAndDeletionIndicator
                (languageId, companyId, courierId, routeId, vehicleRegNumber, assignedHubCode, 0L);

        if (dbDriverRouteAssignment.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + ", courierId - " + courierId + ", routeId - " + routeId +
                    " vehicleRegNumber - " + vehicleRegNumber + " and assignedHubCode - " + assignedHubCode + " doesn't exists";
            // Error Log
            createDriverRouteAssignmentLog1(languageId, companyId, courierId, routeId, vehicleRegNumber, assignedHubCode, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbDriverRouteAssignment.get();
    }

    /**
     * Find DriverRouteAssignment
     *
     * @param findDriverRouteAssignment
     * @return
     */
    public List<ReplicaDriverRouteAssignment> findDriverRouteAssignments(FindDriverRouteAssignment findDriverRouteAssignment) {

        ReplicaDriverRouteAssignmentSpecification spec = new ReplicaDriverRouteAssignmentSpecification(findDriverRouteAssignment);
        List<ReplicaDriverRouteAssignment> results = replicaDriverRouteAssignmentRepository.findAll(spec);
        log.info("found DriverRouteAssignments --> {}", results);
        return results;
    }

    //========================================DriverRouteAssignment_ErrorLog=================================================
    private void createDriverRouteAssignmentLog(String languageId, String companyId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(courierId);
        errorLog.setReferenceField1(routeId);
        errorLog.setReferenceField2(vehicleRegNumber);
        errorLog.setReferenceField3(assignedHubCode);
        errorLog.setMethod("Exception thrown in updateDriverRouteAssignment");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createDriverRouteAssignmentLog1(String languageId, String companyId, String courierId, String routeId, String vehicleRegNumber, String assignedHubCode, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(courierId);
        errorLog.setReferenceField1(routeId);
        errorLog.setReferenceField2(vehicleRegNumber);
        errorLog.setReferenceField3(assignedHubCode);
        errorLog.setMethod("Exception thrown in getDriverRouteAssignment");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createDriverRouteAssignmentLog2(AddDriverRouteAssignment addDriverRouteAssignment, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addDriverRouteAssignment.getLanguageId());
        errorLog.setCompanyId(addDriverRouteAssignment.getCompanyId());
        errorLog.setRefDocNumber(addDriverRouteAssignment.getCourierId());
        errorLog.setReferenceField1(addDriverRouteAssignment.getRouteId());
        errorLog.setReferenceField2(addDriverRouteAssignment.getVehicleRegNumber());
        errorLog.setReferenceField3(addDriverRouteAssignment.getAssignedHubCode());
        errorLog.setMethod("Exception thrown in createDriverRouteAssignment");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
