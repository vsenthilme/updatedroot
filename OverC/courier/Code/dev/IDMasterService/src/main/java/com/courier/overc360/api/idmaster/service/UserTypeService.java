package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.usertype.AddUserType;
import com.courier.overc360.api.idmaster.primary.model.usertype.UpdateUserType;
import com.courier.overc360.api.idmaster.primary.model.usertype.UserType;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.UserTypeRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.usertype.FindUserType;
import com.courier.overc360.api.idmaster.replica.model.usertype.ReplicaUserType;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaUserTypeRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaUserTypeSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserTypeService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private ReplicaUserTypeRepository replicaUserTypeRepository;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get UserType
     *
     * @param userTypeId
     * @param companyId
     * @param languageId
     * @return
     */
    public UserType getUserType(Long userTypeId, String companyId, String languageId) {
        Optional<UserType> dbUserType = userTypeRepository.findByCompanyIdAndUserTypeIdAndLanguageIdAndDeletionIndicator(
                companyId, userTypeId, languageId, 0L);
        if (dbUserType.isEmpty()) {
            throw new BadRequestException("The given values : " + "companyId - " + companyId + ",languageId - " + languageId +
                    "and userTypeId - " + userTypeId + " doesn't exist.");
        }
        return dbUserType.get();
    }

    /**
     * Create UserType
     *
     * @param addUserType
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    public UserType createUserType(AddUserType addUserType, String loginUserID)
            throws IllegalAccessException, InvocationTargetException {

        Optional<UserType> duplicateUsertype = userTypeRepository.findByCompanyIdAndUserTypeIdAndLanguageIdAndDeletionIndicator(addUserType.getCompanyId(), addUserType.getUserTypeId(), addUserType.getLanguageId(), 0L);
        Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                addUserType.getCompanyId(), addUserType.getLanguageId(), 0L);
        if (dbCompany.isEmpty()) {
            throw new BadRequestException("CompanyId - " + addUserType.getCompanyId() + " and LanguageId - " + addUserType.getLanguageId() + " doesn't exists");
        } else if (duplicateUsertype.isPresent()) {
            throw new EntityNotFoundException("Record is Getting Duplicated with given values : userTypeId - " + addUserType.getUserTypeId());
        } else {
            log.info("new UserType --> {}", addUserType);
            UserType dbUserType = new UserType();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addUserType.getLanguageId(), addUserType.getCompanyId());
            BeanUtils.copyProperties(addUserType, dbUserType, CommonUtils.getNullPropertyNames(addUserType));
            if (iKeyValuePair != null) {
                dbUserType.setLanguageIdAndDescription(iKeyValuePair.getLangDesc());
                dbUserType.setCompanyIdAndDescription(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addUserType.getStatusId());
            if (statusDesc != null) {
                dbUserType.setStatusDescription(statusDesc);
            }
            dbUserType.setDeletionIndicator(0L);
            dbUserType.setCreatedBy(loginUserID);
            dbUserType.setUpdatedBy(loginUserID);
            dbUserType.setCreatedOn(new Date());
            dbUserType.setUpdatedOn(new Date());
            return userTypeRepository.save(dbUserType);
        }
    }

    /**
     * Update UserType
     *
     * @param userTypeId
     * @param companyId
     * @param languageId
     * @param loginUserID
     * @param updateUserType
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    public UserType updateUserType(Long userTypeId, String companyId, String languageId, String loginUserID,
                                   UpdateUserType updateUserType)
            throws IllegalAccessException, InvocationTargetException, ParseException {
        UserType dbUserType = getUserType(userTypeId, companyId, languageId);
        BeanUtils.copyProperties(updateUserType, dbUserType, CommonUtils.getNullPropertyNames(updateUserType));
        if (updateUserType.getStatusId() != null && !updateUserType.getStatusId().isEmpty()) {
            String statusDesc = replicaStatusRepository.getStatusDescription(updateUserType.getStatusId());
            if (statusDesc != null) {
                dbUserType.setStatusDescription(statusDesc);
            }
        }
        dbUserType.setUpdatedBy(loginUserID);
        dbUserType.setUpdatedOn(new Date());
        return userTypeRepository.save(dbUserType);
    }

    /**
     * Delete UserType
     *
     * @param userTypeId
     * @param companyId
     * @param languageId
     * @param loginUserID
     */
    public void deleteUserType(Long userTypeId, String companyId, String languageId, String loginUserID) {
        UserType dbUserType = getUserType(userTypeId, companyId, languageId);
        if (dbUserType != null) {
            dbUserType.setDeletionIndicator(1L);
            dbUserType.setUpdatedBy(loginUserID);
            dbUserType.setUpdatedOn(new Date());
            userTypeRepository.save(dbUserType);
        } else {
            throw new EntityNotFoundException("Error in deleting Id: " + userTypeId);
        }
    }

    //===============================================Replica==================================================

    /**
     * get all UserTypeIds
     *
     * @return
     */
    public List<ReplicaUserType> getAllUserTypeIds() {
        List<ReplicaUserType> userTypeList = replicaUserTypeRepository.findAll();
        userTypeList = userTypeList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return userTypeList;
    }

    /**
     * Get UserType
     *
     * @param userTypeId
     * @param companyId
     * @param languageId
     * @return
     */
    public ReplicaUserType getReplicaUserType(Long userTypeId, String companyId, String languageId) {
        Optional<ReplicaUserType> dbUserType = replicaUserTypeRepository.findByCompanyIdAndUserTypeIdAndLanguageIdAndDeletionIndicator(
                companyId, userTypeId, languageId, 0L);
        if (dbUserType.isEmpty()) {
            throw new BadRequestException("The given values : " + "companyId - " + companyId + ",languageId - " + languageId +
                    "and userTypeId - " + userTypeId + " doesn't exist.");
        }
        return dbUserType.get();
    }

    //Find UserType
    public List<ReplicaUserType> findUserType(FindUserType findUserType) throws ParseException {

        ReplicaUserTypeSpecification spec = new ReplicaUserTypeSpecification(findUserType);
        List<ReplicaUserType> results = replicaUserTypeRepository.findAll(spec);
        log.info("found UserTypes --> " + results);
        return results;
    }
}
