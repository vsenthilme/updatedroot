package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.menu.Menu;
import com.courier.overc360.api.idmaster.primary.model.module.AddModule;
import com.courier.overc360.api.idmaster.primary.model.module.Module;
import com.courier.overc360.api.idmaster.primary.model.module.UpdateModule;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.MenuRepository;
import com.courier.overc360.api.idmaster.primary.repository.ModuleRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.module.FindModule;
import com.courier.overc360.api.idmaster.replica.model.module.ReplicaModule;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaModuleRepository;
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
public class ModuleService {

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ReplicaModuleRepository replicaModuleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------*/

    /**
     * Get module
     *
     * @param menuId
     * @param moduleId
     * @param companyId
     * @param languageId
     * @param subMenuId
     * @return
     */
    public Module getModule(Long menuId, String moduleId, String companyId, String languageId, Long subMenuId) {
        Optional<Module> dbModule = moduleRepository.findByCompanyIdAndAndLanguageIdAndModuleIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                companyId, languageId, moduleId, menuId, subMenuId, 0L);
        if (dbModule.isEmpty()) {
            String errMsg = "The given values :  languageId - " + languageId + ", companyId - " + companyId +
                    ", moduleId - " + moduleId + ", menuId - " + menuId + " and submenuId - " + subMenuId + " doesn't exists";
            // Error Log
            createModuleLog(languageId, companyId, moduleId, menuId, subMenuId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbModule.get();

    }

    /**
     * Create module
     *
     * @param addModuleList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    @Transactional
    public List<Module> createModule(List<AddModule> addModuleList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<Module> newModuleList = new ArrayList<>();

            for (AddModule addModule : addModuleList) {
                boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                        addModule.getCompanyId(), addModule.getLanguageId(), 0L);

                boolean duplicateModulesPresent = replicaModuleRepository.existsByCompanyIdAndAndLanguageIdAndModuleIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                        addModule.getCompanyId(), addModule.getLanguageId(), addModule.getModuleId(),
                        addModule.getMenuId(), addModule.getSubMenuId(), 0L);

                if (!dbCompanyPresent) {
                    throw new BadRequestException("CompanyId - " + addModule.getCompanyId() + " and LanguageId - " + addModule.getLanguageId() + " doesn't exists");
                } else if (duplicateModulesPresent) {
                    throw new EntityNotFoundException("Record is Getting Duplicated with given values : moduleId - " + addModule.getModuleId());
                } else {
                    Optional<Menu> dbMenu = menuRepository.
                            findByCompanyIdAndAndLanguageIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                                    addModule.getCompanyId(), addModule.getLanguageId(), addModule.getMenuId(),
                                    addModule.getSubMenuId(), 0L);

                    if (dbMenu.isEmpty()) {
                        throw new IllegalAccessException("MenuId - " + addModule.getMenuId() + " and SubMenuId - "
                                + addModule.getSubMenuId() + " doesn't exists");
                    } else {
                        log.info("new Module --> {}", addModule);
                        Module module = new Module();
                        IKeyValuePair iKeyValuePair = replicaModuleRepository.getDescription(addModule.getLanguageId(), addModule.getCompanyId(),
                                addModule.getMenuId(), addModule.getSubMenuId());
                        BeanUtils.copyProperties(addModule, module, CommonUtils.getNullPropertyNames(addModule));
                        if ((addModule.getModuleId() != null &&
                                (addModule.getReferenceField10() != null && addModule.getReferenceField10().equalsIgnoreCase("true"))) ||
                                addModule.getModuleId() == null || addModule.getModuleId().isBlank()) {
                            String NUM_RAN_OBJ = "MODULE";
                            String MODULE_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                            log.info("next Value from NumberRange for MODULE_ID : " + MODULE_ID);
                            addModule.setModuleId(MODULE_ID);
                        }
                        if (iKeyValuePair != null) {
                            module.setLanguageIdAndDescription(iKeyValuePair.getLangDesc());
                            module.setCompanyIdAndDescription(iKeyValuePair.getCompanyDesc());
                            module.setMenuName(iKeyValuePair.getMenuDesc());
                            module.setSubMenuName(iKeyValuePair.getSubMenuDesc());
                        }
                        String statusDesc = replicaStatusRepository.getStatusDescription(addModule.getStatusId());
                        if (statusDesc != null) {
                            module.setStatusDescription(statusDesc);
                        }
                        module.setDeletionIndicator(0L);
                        module.setCreatedBy(loginUserID);
                        module.setCreatedOn(new Date());
                        module.setUpdatedBy(loginUserID);
                        module.setUpdatedOn(new Date());
                        newModuleList.add(moduleRepository.save(module));
                    }
                }
            }
            return newModuleList;
        } catch (Exception e) {
            // Error Log
            createModuleLog2(addModuleList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    /**
//     * @param languageId
//     * @param companyId
//     * @param menuId
//     * @param subMenuId
//     * @param moduleId
//     * @param loginUserID
//     * @param updateModule
//     * @return
//     * @throws IllegalAccessException
//     * @throws InvocationTargetException
//     */
//    @Transactional
//    public Module updateModule(String languageId, String companyId, Long menuId, Long subMenuId, String moduleId, String loginUserID, UpdateModule updateModule)
//            throws IllegalAccessException, InvocationTargetException {
//        try {
//            Module dbModule = getModule(menuId, moduleId, companyId, languageId, subMenuId);
//            BeanUtils.copyProperties(updateModule, dbModule, CommonUtils.getNullPropertyNames(updateModule));
//            dbModule.setUpdatedBy(loginUserID);
//            dbModule.setUpdatedOn(new Date());
//            return moduleRepository.save(dbModule);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * @param moduleId
     * @param companyId
     * @param languageId
     * @param loginUserID
     * @param updateModuleId
     * @return
     */
    @Transactional
    public List<Module> updateModule(String moduleId, String companyId, String languageId, String loginUserID,
                                     List<UpdateModule> updateModuleId) throws IOException, CsvException {

        try {
            List<Module> moduleIdList = new ArrayList<>();

            for (UpdateModule newUpdateModuleId : updateModuleId) {
                Optional<Module> dbModuleId = moduleRepository.findByCompanyIdAndAndLanguageIdAndModuleIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                        companyId, languageId, moduleId, newUpdateModuleId.getMenuId(), newUpdateModuleId.getSubMenuId(), 0L);

                if (dbModuleId != null && !dbModuleId.isEmpty()) {
                    Module dbModule = dbModuleId.get();
                    if (newUpdateModuleId.getDeletionIndicator() == 1) {
                        moduleRepository.delete(dbModule);
                    } else {
                        BeanUtils.copyProperties(newUpdateModuleId, dbModule, CommonUtils.getNullPropertyNames(newUpdateModuleId));
                        if (newUpdateModuleId.getStatusId() != null && !newUpdateModuleId.getStatusId().isEmpty()) {
                            String statusDesc = replicaStatusRepository.getStatusDescription(newUpdateModuleId.getStatusId());
                            if (statusDesc != null) {
                                dbModule.setStatusDescription(statusDesc);
                            }
                        }
                        dbModule.setUpdatedBy(loginUserID);
                        dbModule.setUpdatedOn(new Date());
                        moduleIdList.add(moduleRepository.save(dbModule));
                    }
                } else {

                    if (newUpdateModuleId.getDeletionIndicator() != 1) {

                        Module newModuleId = new Module();
                        log.info("newModuleId : " + newUpdateModuleId);

                        BeanUtils.copyProperties(newUpdateModuleId, newModuleId, CommonUtils.getNullPropertyNames(newUpdateModuleId));

                        if (newUpdateModuleId.getStatusId() != null && !newUpdateModuleId.getStatusId().isEmpty()) {
                            String statusDesc = replicaStatusRepository.getStatusDescription(newUpdateModuleId.getStatusId());
                            if (statusDesc != null) {
                                newModuleId.setStatusDescription(statusDesc);
                            }
                        }

                        newModuleId.setModuleId(moduleId);
                        newModuleId.setCompanyId(companyId);
                        newModuleId.setLanguageId(languageId);

                        newModuleId.setDeletionIndicator(0L);
                        newModuleId.setCreatedBy(loginUserID);
                        newModuleId.setUpdatedBy(loginUserID);
                        newModuleId.setCreatedOn(new Date());
                        newModuleId.setUpdatedOn(new Date());
                        moduleIdList.add(moduleRepository.save(newModuleId));
                    }
                }
            }
            return moduleIdList;
        } catch (Exception e) {
            // Error Log
            createModuleLog3(languageId, companyId, moduleId, updateModuleId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete module
     *
     * @param moduleId
     * @param companyId
     * @param languageId
     * @param loginUserID
     */
    public void deleteModule(String moduleId, String companyId, String languageId, String loginUserID) {
        List<Module> dbModule = moduleRepository.findByCompanyIdAndModuleIdAndLanguageIdAndDeletionIndicator(
                companyId, moduleId, languageId, 0L);
        if (dbModule != null) {
            for (Module module : dbModule) {
                if (module != null) {
                    module.setDeletionIndicator(1L);
                    module.setUpdatedBy(loginUserID);
                    module.setUpdatedOn(new Date());
                    moduleRepository.save(module);
                } else {
                    throw new BadRequestException("Error in deleting moduleId - " + moduleId);
                }
            }
        }
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    /**
     * Get all module
     *
     * @return
     */
    public List<ReplicaModule> getAllModule() {
        List<ReplicaModule> moduleList = replicaModuleRepository.findAll();
        moduleList = moduleList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return moduleList;
    }

    /**
     * Get ReplicaModule
     *
     * @param moduleId
     * @return
     */
    public ReplicaModule getReplicaModule(Long menuId, String moduleId, String companyId, String languageId, Long subMenuId) {
        Optional<ReplicaModule> dbModule = replicaModuleRepository.findByCompanyIdAndAndLanguageIdAndModuleIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                companyId, languageId, moduleId, menuId, subMenuId, 0L);
        if (dbModule.isEmpty()) {
            String errMsg = "The given values :  languageId - " + languageId + ", companyId - " + companyId +
                    ", moduleId - " + moduleId + ", menuId - " + menuId + " and submenuId - " + subMenuId + " doesn't exists";
            // Error Log
            createModuleLog(languageId, companyId, moduleId, menuId, subMenuId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbModule.get();

    }

    /**
     * Get ReplicaModuleList
     *
     * @param moduleId
     * @return
     */
    public List<ReplicaModule> getReplicaModuleList(String moduleId, String companyId, String languageId) {
        List<ReplicaModule> dbModuleList = replicaModuleRepository.findByCompanyIdAndAndLanguageIdAndModuleIdAndDeletionIndicator(
                companyId, languageId, moduleId, 0L);
        if (dbModuleList.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId +
                    " and moduleId - " + moduleId + " doesn't exists";
            // Error Log
            createModuleLog1(languageId, companyId, moduleId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbModuleList;

    }

    /**
     * Find modules
     *
     * @param findModule
     * @return
     * @throws ParseException
     */
//    public List<ReplicaModule> findModules(FindModule findModule) throws ParseException {
//
//        ReplicaModuleSpecification spec = new ReplicaModuleSpecification(findModule);
//        List<ReplicaModule> results = replicaModuleRepository.findAll(spec);
//        log.info("found Modules --> {}", results);
//        return results;
//    }
    public List<ReplicaModule> findModules(FindModule findModule) throws ParseException {

        log.info("given params for find -- > {}", findModule);
        List<ReplicaModule> results = replicaModuleRepository.findModulesWithQry(findModule.getLanguageId(), findModule.getCompanyId(),
                findModule.getMenuId(), findModule.getSubMenuId(), findModule.getStatusId(), findModule.getModuleId());
//        log.info("found Modules --> {}", results);
        return results;

    }

    //==============================================Module_ErrorLog====================================================
    private void createModuleLog(String languageId, String companyId, String moduleId, Long menuId,
                                 Long subMenuId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(moduleId);
        errorLog.setReferenceField1(String.valueOf(menuId));
        errorLog.setReferenceField2(String.valueOf(subMenuId));
        errorLog.setMethod("Exception thrown in getModule");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createModuleLog1(String languageId, String companyId, String moduleId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(moduleId);
        errorLog.setMethod("Exception thrown in getModuleList");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createModuleLog2(List<AddModule> addModuleList, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddModule addModule : addModuleList) {

            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addModule.getLanguageId());
            errorLog.setCompanyId(addModule.getCompanyId());
            errorLog.setRefDocNumber(addModule.getModuleId());
            errorLog.setReferenceField1(String.valueOf(addModule.getMenuId()));
            errorLog.setReferenceField2(String.valueOf(addModule.getSubMenuId()));
            errorLog.setMethod("Exception thrown in createModule");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createModuleLog3(String languageId, String companyId, String moduleId, List<UpdateModule> updateModuleList,
                                  String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateModule updateModule : updateModuleList) {

            ErrorLog errorLog = new ErrorLog();
            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(languageId);
            errorLog.setCompanyId(companyId);
            errorLog.setRefDocNumber(moduleId);
            errorLog.setReferenceField1(String.valueOf(updateModule.getMenuId()));
            errorLog.setReferenceField2(String.valueOf(updateModule.getSubMenuId()));
            errorLog.setMethod("Exception thrown in updateModule");
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

}
