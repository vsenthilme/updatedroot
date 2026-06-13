package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.roleaccess.AddRoleAccess;
import com.courier.overc360.api.idmaster.primary.model.roleaccess.RoleAccess;
import com.courier.overc360.api.idmaster.primary.model.roleaccess.UpdateRoleAccess;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.MenuRepository;
import com.courier.overc360.api.idmaster.primary.repository.ModuleRepository;
import com.courier.overc360.api.idmaster.primary.repository.RoleAccessRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.roleaccess.FindRoleAccess;
import com.courier.overc360.api.idmaster.replica.model.roleaccess.ReplicaRoleAccess;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaMenuRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaModuleRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRoleAccessRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleAccessService {

    @Autowired
    private ReplicaMenuRepository replicaMenuRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaModuleRepository replicaModuleRepository;

    @Autowired
    private RoleAccessRepository roleAccessRepository;

    @Autowired
    private ReplicaRoleAccessRepository replicaRoleAccessRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------*/

    /**
     * Get RoleAccess
     *
     * @return
     */
    public RoleAccess getRoleAccess(String companyId, String languageId, Long roleId, Long menuId, Long subMenuId) {
        Optional<RoleAccess> dbRoleAccess = roleAccessRepository.findByCompanyIdAndAndLanguageIdAndRoleIdAndMenuIdAndSubMenuIdAndDeletionIndicator
                (companyId, languageId, roleId, menuId, subMenuId, 0L);
        if (dbRoleAccess.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + " languageId - " + languageId +
                    " roleId - " + roleId + " menuId - " + menuId + " submenuId - " + subMenuId + " doesn't exists";
            createRoleAccessLog1(languageId, companyId, roleId, menuId, subMenuId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbRoleAccess.get();
    }

    /**
     * Create new RoleAccess
     *
     * @param addRoleAccessList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    @Transactional
    public List<RoleAccess> createRoleAccess(List<AddRoleAccess> addRoleAccessList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Long roleId = roleAccessRepository.getRoleId();

            if (roleId == null) {
                roleId = 1L;
            }
            List<RoleAccess> newRoleAccessList = new ArrayList<>();

            for (AddRoleAccess addRoleAccess : addRoleAccessList) {

                boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                        addRoleAccess.getCompanyId(), addRoleAccess.getLanguageId(), 0L);
                if (!dbCompanyPresent) {
                    throw new BadRequestException("CompanyId - " + addRoleAccess.getCompanyId()
                            + " and LanguageId - " + addRoleAccess.getLanguageId() + " doesn't exists");
                }

                boolean duplicateRoleAccess = replicaRoleAccessRepository.existsByLanguageIdAndCompanyIdAndRoleIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                        addRoleAccess.getLanguageId(), addRoleAccess.getCompanyId(), addRoleAccess.getRoleId(),
                        addRoleAccess.getMenuId(), addRoleAccess.getSubMenuId(), 0L);
                if (duplicateRoleAccess) {
                    throw new EntityNotFoundException("Record is Getting Duplicated with roleId - " + addRoleAccess.getRoleId());
                }
                boolean dbMenu = replicaMenuRepository.existsByLanguageIdAndCompanyIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                        addRoleAccess.getLanguageId(), addRoleAccess.getCompanyId(), addRoleAccess.getMenuId(),
                        addRoleAccess.getSubMenuId(), 0L);

                if (!dbMenu) {
                    throw new IllegalAccessException("MenuId: " + addRoleAccess.getMenuId() + " and SubMenuId: "
                            + addRoleAccess.getSubMenuId() + " doesn't exists");
                } else {
                    RoleAccess roleAccess = new RoleAccess();
                    IKeyValuePair iKeyValuePair = replicaModuleRepository.getDescription(addRoleAccess.getLanguageId(),
                            addRoleAccess.getCompanyId(), addRoleAccess.getMenuId(), addRoleAccess.getSubMenuId());
                    BeanUtils.copyProperties(addRoleAccess, roleAccess, CommonUtils.getNullPropertyNames(addRoleAccess));
                    if (iKeyValuePair != null) {
                        roleAccess.setLanguageIdAndDescription(iKeyValuePair.getLangDesc());
                        roleAccess.setCompanyIdAndDescription(iKeyValuePair.getCompanyDesc());
                        roleAccess.setMenuName(iKeyValuePair.getMenuDesc());
                        roleAccess.setSubMenuName(iKeyValuePair.getSubMenuDesc());
                    }
                    String statusDesc = replicaStatusRepository.getStatusDescription(addRoleAccess.getStatusId());
                    if (statusDesc != null) {
                        roleAccess.setStatusDescription(statusDesc);
                    }
                    roleAccess.setStatusId("1"); // ACTIVE
                    roleAccess.setRoleId(roleId);
                    roleAccess.setDeletionIndicator(0L);
                    roleAccess.setCreatedBy(loginUserID);
                    roleAccess.setCreatedOn(new Date());
                    roleAccess.setUpdatedBy(loginUserID);
                    roleAccess.setUpdatedOn(new Date());

                    // Insert Record
                    RoleAccess createdRoleAccess = roleAccessRepository.save(roleAccess);
                    log.info("new RoleAccess created --> {}", createdRoleAccess);
                    newRoleAccessList.add(createdRoleAccess);
                }

            }
            return newRoleAccessList;
        } catch (Exception e) {
            // Error Log
            createRoleAccessLog3(addRoleAccessList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update RoleAccess
     *
     * @param languageId
     * @param companyId
     * @param menuId
     * @param subMenuId
     * @param roleId
     * @param loginUserID
     * @param updateRoleAccess
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public RoleAccess updateRoleAccess(String companyId, String languageId, Long roleId, Long menuId,
                                       Long subMenuId, String loginUserID, UpdateRoleAccess updateRoleAccess)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            RoleAccess dbRoleAccess = getRoleAccess(companyId, languageId, roleId, menuId, subMenuId);
            BeanUtils.copyProperties(updateRoleAccess, dbRoleAccess, CommonUtils.getNullPropertyNames(updateRoleAccess));
            if (updateRoleAccess.getStatusId() != null && !updateRoleAccess.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateRoleAccess.getStatusId());
                if (statusDesc != null) {
                    dbRoleAccess.setStatusDescription(statusDesc);
                }
            }
            dbRoleAccess.setUpdatedBy(loginUserID);
            dbRoleAccess.setUpdatedOn(new Date());
            return roleAccessRepository.save(dbRoleAccess);
        } catch (Exception e) {
            // Error Log
            createRoleAccessLog(languageId, companyId, roleId, menuId, subMenuId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<RoleAccess> updateRoleAccess(String companyId, String languageId, Long roleId, String loginUserID, List<AddRoleAccess> updateRoleAccess)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<RoleAccess> roleAccessList = new ArrayList<>();
        try {
            List<RoleAccess> roleAccesses = roleAccessRepository.findByCompanyIdAndRoleIdAndLanguageIdAndDeletionIndicator(
                    companyId, roleId, languageId, 0L);

            // Delete
            roleAccesses.forEach(role -> {
            roleAccessRepository.delete(role);
            });
            // Insert
            updateRoleAccess.forEach(addRole -> {
                RoleAccess newRoleAccess = new RoleAccess();
                BeanUtils.copyProperties(addRole, newRoleAccess, CommonUtils.getNullPropertyNames(addRole));
                newRoleAccess.setRoleId(roleId);
                newRoleAccess.setCreatedBy(loginUserID);
                newRoleAccess.setCreatedOn(new Date());
                if (newRoleAccess.getStatusId() != null && !newRoleAccess.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(newRoleAccess.getStatusId());
                    if (statusDesc != null) {
                        newRoleAccess.setStatusDescription(statusDesc);
                    }
                }
                roleAccessList.add(roleAccessRepository.save(newRoleAccess));
            });
            return roleAccessList;
        } catch (Exception e) {
            // Error Log
//            createRoleAccessLog(languageId, companyId, roleId, menuId, subMenuId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Delete RoleAccess
     *
     * @param languageId
     * @param companyId
     * @param roleId
     * @param loginUserID
     */
    public void deleteRoleAccess(String languageId, String companyId, Long roleId, String loginUserID) {
        List<RoleAccess> dbRoleAccess = roleAccessRepository.findByCompanyIdAndRoleIdAndLanguageIdAndDeletionIndicator(
                companyId, roleId, languageId, 0L);
        if (dbRoleAccess != null) {
            for (RoleAccess roleAccess : dbRoleAccess) {
                if (roleAccess != null) {
                    roleAccess.setDeletionIndicator(1L);
                    roleAccess.setUpdatedBy(loginUserID);
                    roleAccess.setUpdatedOn(new Date());
                    roleAccessRepository.save(roleAccess);
                } else {
                    String errMsg = "Error in deleting roleId: " + roleId;
                    // Error Log
                    createRoleAccessLog2(languageId, companyId, roleId, errMsg);
                    throw new BadRequestException(errMsg);
                }
            }
        }
    }
    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    /**
     * Get all RoleAccess Details
     *
     * @return
     */
    public List<ReplicaRoleAccess> getAllRoleAccesses() {
        List<ReplicaRoleAccess> roleAccessList = replicaRoleAccessRepository.findAll();
        roleAccessList = roleAccessList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return roleAccessList;
    }

    /**
     * Get RoleAccess
     *
     * @param companyId
     * @param languageId
     * @param roleId
     * @param menuId
     * @param subMenuId
     * @return
     */
    public ReplicaRoleAccess getReplicaRoleAccess(String companyId, String languageId, Long roleId, Long menuId, Long subMenuId) {
        Optional<ReplicaRoleAccess> dbRoleAccess = replicaRoleAccessRepository.findByLanguageIdAndCompanyIdAndRoleIdAndMenuIdAndSubMenuIdAndDeletionIndicator
                (languageId, companyId, roleId, menuId, subMenuId, 0L);
        if (dbRoleAccess.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + " languageId - " + languageId +
                    " roleId - " + roleId + " menuId - " + menuId + " submenuId - " + subMenuId + " doesn't exists";
            createRoleAccessLog1(languageId, companyId, roleId, menuId, subMenuId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbRoleAccess.get();
    }

    /**
     * @param companyId
     * @param languageId
     * @param roleId
     * @return
     */
    public List<ReplicaRoleAccess> getReplicaRoleAccessList(String companyId, String languageId, Long roleId) {
        List<ReplicaRoleAccess> dbRoleAccessList = replicaRoleAccessRepository.findByLanguageIdAndCompanyIdAndRoleIdAndDeletionIndicator(
                languageId, companyId, roleId, 0L);
        if (dbRoleAccessList.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + " languageId - " + languageId +
                    " and roleId - " + roleId + " doesn't exists";
            // Error Log
            createRoleAccessLog2(languageId, companyId, roleId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbRoleAccessList;
    }

    /**
     * Find RoleAccesses
     *
     * @param findRoleAccess
     * @return
     * @throws ParseException
     */
//    public List<ReplicaRoleAccess> findRoleAccess(FindRoleAccess findRoleAccess) throws ParseException {
//
//        ReplicaRoleAccessSpecification spec = new ReplicaRoleAccessSpecification(findRoleAccess);
//        List<ReplicaRoleAccess> results = replicaRoleAccessRepository.findAll(spec);
//        log.info("results --> {}", results);
//        return results;
//    }
    public List<ReplicaRoleAccess> findRoleAccess(FindRoleAccess findRoleAccess) throws ParseException {

        log.info("given params for find -- > {}", findRoleAccess);
        List<ReplicaRoleAccess> roleAccessList = replicaRoleAccessRepository.findRoleAccessesWithQry(
                findRoleAccess.getLanguageId(), findRoleAccess.getCompanyId(), findRoleAccess.getMenuId(),
                findRoleAccess.getSubMenuId(), findRoleAccess.getStatusId(), findRoleAccess.getRoleId());
//        log.info("found roleAccessList --> {}", roleAccessList);
        return roleAccessList;
    }

    //==========================================RoleAccess_ErrorLog====================================================
    private void createRoleAccessLog(String languageId, String companyId, Long roleId, Long menuId, Long subMenuId,
                                     String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(String.valueOf(roleId));
        errorLog.setMethod("Exception thrown in updateRoleAccess");
        errorLog.setReferenceField1(String.valueOf(menuId));
        errorLog.setReferenceField2(String.valueOf(subMenuId));
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createRoleAccessLog1(String languageId, String companyId, Long roleId, Long menuId, Long subMenuId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(String.valueOf(roleId));
        errorLog.setMethod("Exception thrown in getRoleAccess");
        errorLog.setReferenceField1(String.valueOf(menuId));
        errorLog.setReferenceField2(String.valueOf(subMenuId));
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createRoleAccessLog2(String languageId, String companyId, Long roleId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(String.valueOf(roleId));
        errorLog.setMethod("Exception thrown in getRoleAccessList");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createRoleAccessLog3(List<AddRoleAccess> addRoleAccessList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddRoleAccess addRoleAccess : addRoleAccessList) {

            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addRoleAccess.getLanguageId());
            errorLog.setCompanyId(addRoleAccess.getCompanyId());
            errorLog.setRefDocNumber(String.valueOf(addRoleAccess.getRoleId()));
            errorLog.setMethod("Exception thrown in updateRoleAccess");
            errorLog.setReferenceField1(String.valueOf(addRoleAccess.getMenuId()));
            errorLog.setReferenceField2(String.valueOf(addRoleAccess.getSubMenuId()));
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

}



