package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.route.AddRoute;
import com.courier.overc360.api.idmaster.primary.model.route.Route;
import com.courier.overc360.api.idmaster.primary.model.route.UpdateRoute;
import com.courier.overc360.api.idmaster.primary.model.serviceType.ServiceType;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.RouteRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.route.FindRoute;
import com.courier.overc360.api.idmaster.replica.model.route.ReplicaRoute;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRouteRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaRouteSpecification;
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
public class RouteService {

    @Autowired
    private ReplicaRouteRepository replicaRouteRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private RouteRepository routeRepository;

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
     * get
     *
     * @param companyId
     * @param languageId
     * @param routeId
     * @return
     */
    public Route getRoute(String companyId, String languageId, String routeId) {
        Optional<Route> dbRoute = routeRepository.findByCompanyIdAndLanguageIdAndRouteIdAndDeletionIndicator
                (companyId, languageId, routeId,0l);
        if (dbRoute.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + ", routeId - " + routeId +" doesn't exists";
            // Error Log
            createRouteLog1(companyId, languageId, routeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbRoute.get();
    }

    /**
     * Create
     *
     * @param addRoute
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Route createRoute(AddRoute addRoute, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
//            Optional<Route> duplicateRoute= routeRepository.findByCompanyIdAndLanguageIdAndRouteIdAndLegIdAndDeletionIndicator(
//                    addRoute.getCompanyId(), addRoute.getLanguageId(), addRoute.getRouteId(),addRoute.getLegId(), 0L);
//            if (duplicateRoute.isPresent()) {
//                throw new BadRequestException("Record is getting Duplicated with the given values : routeId - " + addRoute.getRouteId());
//            }

            routeRepository.findByCompanyIdAndLanguageIdAndRouteIdAndDeletionIndicator
                            (addRoute.getCompanyId(), addRoute.getLanguageId(), addRoute.getRouteId(), 0L)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : routeId - " + addRoute.getRouteId());
                    });

            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                    (addRoute.getCompanyId(), addRoute.getLanguageId(), 0L);
            if (dbCompany.isEmpty()) {
                throw new BadRequestException("The given values : CompanyId - " + addRoute.getCompanyId()
                        + " and LanguageId - " + addRoute.getLanguageId() + "  doesn't exists");
            }
            log.info("new Route --> {}", addRoute);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addRoute.getLanguageId(), addRoute.getCompanyId());
            Route newRoute = new Route();
            BeanUtils.copyProperties(addRoute, newRoute, CommonUtils.getNullPropertyNames(addRoute));
            if ((addRoute.getRouteId() != null &&
                    (addRoute.getReferenceField10() != null && addRoute.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addRoute.getRouteId() == null || addRoute.getRouteId().isBlank()) {
                String ROUTE_ID = numberRangeService.getNextNumberRange("ROUTEID");
                log.info("next Value from NumberRange for ROUTE_ID : " + ROUTE_ID);
                newRoute.setRouteId(ROUTE_ID);
            }
            if ((addRoute.getLegId() != null &&
                    (addRoute.getReferenceField10() != null && addRoute.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addRoute.getLegId() == null || addRoute.getLegId().isBlank()) {
                String LEG_ID = numberRangeService.getNextNumberRange("LEGID");
                log.info("next Value from NumberRange for LEG_ID : " + LEG_ID);
                newRoute.setLegId(LEG_ID);
            }
            if (iKeyValuePair != null) {
                newRoute.setLanguageDescription(iKeyValuePair.getLangDesc());
                newRoute.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addRoute.getStatusId());
            if (statusDesc != null) {
                newRoute.setStatusDescription(statusDesc);
            }
            newRoute.setDeletionIndicator(0L);
            newRoute.setCreatedBy(loginUserID);
            newRoute.setUpdatedBy(loginUserID);
            newRoute.setCreatedOn(new Date());
            newRoute.setUpdatedOn(new Date());
            return routeRepository.save(newRoute);

        } catch (Exception e) {
            // Error Log
            createRouteLog2(addRoute, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param routeId
     * @param updateRoute
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Route updateRoute(String companyId, String languageId, String routeId,
                            UpdateRoute updateRoute, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Route dbRoute = getRoute(companyId, languageId, routeId);
            BeanUtils.copyProperties(updateRoute, dbRoute, CommonUtils.getNullPropertyNames(updateRoute));

            if (updateRoute.getStatusId() != null && !updateRoute.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateRoute.getStatusId());
                if (statusDesc != null) {
                    dbRoute.setStatusDescription(statusDesc);
                }
            }
            dbRoute.setUpdatedBy(loginUserID);
            dbRoute.setUpdatedOn(new Date());
            return routeRepository.save(dbRoute);
        } catch (Exception e) {
            // Error Log
            createRouteLog(companyId, languageId, routeId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param routeId
     * @param loginUserID
     */
    public void deleteRoute(String companyId, String languageId, String routeId, String loginUserID) {
        Route dbRoute = getRoute(companyId, languageId, routeId);
        if (dbRoute != null) {
            dbRoute.setDeletionIndicator(1L);
            dbRoute.setUpdatedBy(loginUserID);
            dbRoute.setUpdatedOn(new Date());
            routeRepository.save(dbRoute);
        } else {
            // Error Log
            createRouteLog1(companyId, languageId, routeId, "Error in deleting RouteId - " + routeId);
            throw new EntityNotFoundException("Error in deleting RouteId - " + routeId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All
     *
     * @return
     */
    public List<ReplicaRoute> getAll() {
        List<ReplicaRoute> routeList = replicaRouteRepository.findAll();
        routeList = routeList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return routeList;
    }

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param routeId
     * @return
     */
    public ReplicaRoute getReplicaRoute(String companyId, String languageId, String routeId) {
        Optional<ReplicaRoute> dbRoute = replicaRouteRepository.findByCompanyIdAndLanguageIdAndRouteIdAndDeletionIndicator
                (companyId, languageId, routeId,0l);
        if (dbRoute.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + ", routeId - " + routeId + " doesn't exists";
            // Error Log
            createRouteLog1(companyId, languageId, routeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbRoute.get();
    }

    /**
     * Find
     *
     * @param findRoute
     * @return
     */
    public List<ReplicaRoute> findRoute(FindRoute findRoute) {
        ReplicaRouteSpecification spec = new ReplicaRouteSpecification(findRoute);
        List<ReplicaRoute> results = replicaRouteRepository.findAll(spec);
        log.info("found Route --> {}", results);
        return results;
    }

    //=========================================Route_ErrorLog====================================================
    private void createRouteLog(String companyId, String languageId, String routeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(routeId);
        errorLog.setMethod("Exception thrown in updateRoute");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createRouteLog1(String companyId, String languageId, String routeId,  String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(routeId);
        errorLog.setMethod("Exception thrown in getRoute");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createRouteLog2(AddRoute addRoute, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addRoute.getLanguageId());
        errorLog.setCompanyId(addRoute.getCompanyId());
        errorLog.setRefDocNumber(addRoute.getRouteId());
        errorLog.setReferenceField1(addRoute.getLegId());
        errorLog.setMethod("Exception thrown in createRoute");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}
