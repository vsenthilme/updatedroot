package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.menu.AddMenu;
import com.courier.overc360.api.idmaster.primary.model.menu.Menu;
import com.courier.overc360.api.idmaster.primary.model.menu.UpdateMenu;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.MenuRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.menu.FindMenu;
import com.courier.overc360.api.idmaster.replica.model.menu.ReplicaMenu;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaMenuRepository;
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

@Slf4j
@Service
public class MenuService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ReplicaMenuRepository replicaMenuRepository;

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
     * Get Menu
     *
     * @param languageId
     * @param companyId
     * @param menuId
     * @param subMenuId
     * @param authorizationObjectId
     * @return
     */
    public Menu getMenu(String languageId, String companyId, Long menuId, Long subMenuId, Long authorizationObjectId) {
        Optional<Menu> dbMenu =
                menuRepository.findByLanguageIdAndCompanyIdAndMenuIdAndSubMenuIdAndAuthorizationObjectIdAndDeletionIndicator(
                        languageId, companyId, menuId, subMenuId, authorizationObjectId, 0L);
        if (dbMenu.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId +
                    ", menuId - " + menuId + ", subMenuId - " + subMenuId +
                    " and authorizationObjectId - " + authorizationObjectId + " doesn't exists";
            // Error Log
            createMenuLog1(languageId, companyId, menuId, subMenuId, authorizationObjectId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbMenu.get();
    }

    /**
     * Create new Menu
     *
     * @param addMenu
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Menu createMenu(AddMenu addMenu, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addMenu.getCompanyId(), addMenu.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addMenu.getCompanyId()
                        + " and LanguageId - " + addMenu.getLanguageId() + " doesn't exists");
            }

            boolean duplicateMenuPresent = replicaMenuRepository.existsByLanguageIdAndCompanyIdAndMenuIdAndSubMenuIdAndDeletionIndicator(
                    addMenu.getLanguageId(), addMenu.getCompanyId(), addMenu.getMenuId(),
                    addMenu.getSubMenuId(), 0L);
            if (duplicateMenuPresent) {
                throw new BadRequestException("Record is Getting Duplicated with the given values : menuId - " + addMenu.getMenuId());
            }
            log.info("new Menu --> {}", addMenu);
            Menu newMenu = new Menu();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addMenu.getLanguageId(), addMenu.getCompanyId());
            BeanUtils.copyProperties(addMenu, newMenu, CommonUtils.getNullPropertyNames(addMenu));
            if (addMenu.getMenuId() == null || (addMenu.getReferenceField10() != null && addMenu.getReferenceField10().equalsIgnoreCase("true"))) {
                String NUM_RAN_OBJ = "MENU";
                String MENU_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for MENU_ID : " + MENU_ID);
                newMenu.setMenuId(Long.valueOf(MENU_ID));
            }
            if (addMenu.getSubMenuId() == null || (addMenu.getReferenceField9() != null && addMenu.getReferenceField9().equalsIgnoreCase("true"))) {
                String NUM_RAN_OBJ = "SUBMENU";
                String SUB_MENU_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for SUB_MENU_ID : " + SUB_MENU_ID);
                newMenu.setSubMenuId(Long.valueOf(SUB_MENU_ID));
            }
            if (iKeyValuePair != null) {
                newMenu.setLanguageIdAndDescription(iKeyValuePair.getLangDesc());
                newMenu.setCompanyIdAndDescription(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addMenu.getStatusId());
            if (statusDesc != null) {
                newMenu.setStatusDescription(statusDesc);
            }
            newMenu.setAuthorizationObjectId(1L);
            newMenu.setDeletionIndicator(0L);
            newMenu.setCreatedBy(loginUserID);
            newMenu.setCreatedOn(new Date());
            newMenu.setUpdatedBy(loginUserID);
            newMenu.setUpdatedOn(new Date());
            return menuRepository.save(newMenu);
        } catch (Exception e) {
            // Error Log
            createMenuLog2(addMenu, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Create new Menus - Bulk
     *
     * @param addMenuList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public List<Menu> createMenuBulk(List<AddMenu> addMenuList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Menu> createdMenuList = new ArrayList<>();
        for (AddMenu addMenu : addMenuList) {
            Menu newMenu = createMenu(addMenu, loginUserID);
            createdMenuList.add(newMenu);
        }
        return createdMenuList;
    }

    /**
     * Update Menu
     *
     * @param languageId
     * @param companyId
     * @param menuId
     * @param subMenuId
     * @param authorizationObjectId
     * @param loginUserID
     * @param updateMenu
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Menu updateMenu(String languageId, String companyId, Long menuId, Long subMenuId,
                           Long authorizationObjectId, String loginUserID, UpdateMenu updateMenu)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Menu dbMenu = getMenu(languageId, companyId, menuId, subMenuId, authorizationObjectId);
            BeanUtils.copyProperties(updateMenu, dbMenu, CommonUtils.getNullPropertyNames(updateMenu));
            if (updateMenu.getStatusId() != null && !updateMenu.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateMenu.getStatusId());
                if (statusDesc != null) {
                    dbMenu.setStatusDescription(statusDesc);
                }
            }
            dbMenu.setUpdatedBy(loginUserID);
            dbMenu.setUpdatedOn(new Date());
            return menuRepository.save(dbMenu);
        } catch (Exception e) {
            // Error Log
            createMenuLog(languageId, companyId, menuId, subMenuId, authorizationObjectId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Menu
     *
     * @param languageId
     * @param companyId
     * @param menuId
     * @param subMenuId
     * @param authorizationObjectId
     * @param loginUserID
     */
    public void deleteMenu(String languageId, String companyId, Long menuId, Long subMenuId, Long authorizationObjectId, String loginUserID) {
        Menu dbMenu = getMenu(languageId, companyId, menuId, subMenuId, authorizationObjectId);
        if (dbMenu != null) {
            dbMenu.setDeletionIndicator(1L);
            dbMenu.setUpdatedBy(loginUserID);
            dbMenu.setUpdatedOn(new Date());
            menuRepository.save(dbMenu);
        } else {
            // Error Log
            createMenuLog1(languageId, companyId, menuId, subMenuId, authorizationObjectId, "Error in deleting MenuId - " + menuId);
            throw new EntityNotFoundException("Error in deleting MenuId - " + menuId);
        }
    }

    /*--------------------------------------------------REPLICA------------------------------------------------------*/

    /**
     * Get All Menu Details
     *
     * @return
     */
    public List<ReplicaMenu> getAllMenuDetails() {
        List<ReplicaMenu> menuList = replicaMenuRepository.getNonDeletedMenus();
        return menuList;
    }

    /**
     * Get Menu Replica
     *
     * @param languageId
     * @param companyId
     * @param menuId
     * @param subMenuId
     * @param authorizationObjectId
     * @return
     */
    public ReplicaMenu getMenuReplica(String languageId, String companyId, Long menuId, Long subMenuId, Long authorizationObjectId) {
        Optional<ReplicaMenu> dbMenu =
                replicaMenuRepository.findByLanguageIdAndCompanyIdAndMenuIdAndSubMenuIdAndAuthorizationObjectIdAndDeletionIndicator(
                        languageId, companyId, menuId, subMenuId, authorizationObjectId, 0L);
        if (dbMenu.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", menuId - " + menuId + ", subMenuId - " + subMenuId +
                    " and authorizationObjectId - " + authorizationObjectId + " doesn't exists";
            // Error Log
            createMenuLog1(languageId, companyId, menuId, subMenuId, authorizationObjectId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbMenu.get();
    }

    /**
     * Find Menus
     *
     * @param findMenu
     * @return
     */
//    public List<ReplicaMenu> findMenus(FindMenu findMenu) throws ParseException {
//        ReplicaMenuSpecification spec = new ReplicaMenuSpecification(findMenu);
//        List<ReplicaMenu> results = replicaMenuRepository.findAll(spec);
//        log.info("found Menus --> {}", results);
//        return results;
//    }
    public List<ReplicaMenu> findMenus(FindMenu findMenu) throws ParseException {

        log.info("given params for find -- > {}", findMenu);
        List<ReplicaMenu> menuList = replicaMenuRepository.findMenusWithQry(
                findMenu.getLanguageId(), findMenu.getCompanyId(), findMenu.getMenuId(),
                findMenu.getSubMenuId(), findMenu.getStatusId(), findMenu.getAuthorizationObjectId());
//        log.info("found Menus --> {}", menuList);
        return menuList;
    }

    //==============================================Menu_ErrorLog======================================================
    private void createMenuLog(String languageId, String companyId, Long menuId, Long subMenuId,
                               Long authorizationObjectId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(String.valueOf(menuId));
        errorLog.setReferenceField1(String.valueOf(subMenuId));
        errorLog.setReferenceField2(String.valueOf(authorizationObjectId));
        errorLog.setMethod("Exception thrown in updateMenu");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createMenuLog1(String languageId, String companyId, Long menuId, Long subMenuId,
                                Long authorizationObjectId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(String.valueOf(menuId));
        errorLog.setReferenceField1(String.valueOf(subMenuId));
        errorLog.setReferenceField2(String.valueOf(authorizationObjectId));
        errorLog.setMethod("Exception thrown in getMenu");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createMenuLog2(AddMenu addMenu, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addMenu.getLanguageId());
        errorLog.setCompanyId(addMenu.getCompanyId());
        errorLog.setRefDocNumber(String.valueOf(addMenu.getMenuId()));
        errorLog.setReferenceField1(String.valueOf(addMenu.getSubMenuId()));
        errorLog.setMethod("Exception thrown in createMenu");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
