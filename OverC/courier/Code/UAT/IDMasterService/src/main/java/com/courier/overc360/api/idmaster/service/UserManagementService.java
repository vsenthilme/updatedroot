package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.user.AddUserManagement;
import com.courier.overc360.api.idmaster.primary.model.user.UpdateUserManagement;
import com.courier.overc360.api.idmaster.primary.model.user.UserManagement;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.UserManagementRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.primary.util.PasswordEncoder;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.user.FindUserManagement;
import com.courier.overc360.api.idmaster.replica.model.user.ReplicaUserManagement;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRoleAccessRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaUserManagementRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaUserTypeRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaUserManagementSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserManagementService {

    @Autowired
    private ReplicaUserTypeRepository replicaUserTypeRepository;

    @Autowired
    private ReplicaRoleAccessRepository replicaRoleAccessRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Autowired
    private ReplicaUserManagementRepository replicaUserManagementRepository;

    private PasswordEncoder passwordEncoder = new PasswordEncoder();

    /**
     * Get UserManagement
     *
     * @param languageId
     * @param companyId
     * @param userId
     * @return
     */
    public UserManagement getUserManagement(String languageId, String companyId, String userId) {
        Optional<UserManagement> dbUserManagement = userManagementRepository.
                findByLanguageIdAndCompanyIdAndUserIdAndDeletionIndicator(languageId, companyId, userId, 0L);
        if (dbUserManagement.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and userId - " + userId + " doesn't exists");
        }
        return dbUserManagement.get();
    }

    /**
     * Validate User
     *
     * @param userId
     * @param loginPassword
     * @param version
     * @return
     */
    public UserManagement validateUser(String userId, String loginPassword, String version) {
        List<UserManagement> userManagementList =
                userManagementRepository.findByUserIdAndDeletionIndicator(userId, 0L);
        if (userManagementList.isEmpty()) {
            throw new BadRequestException("Invalid Username : " + userId);
        }

        boolean isSuccess = false;
        for (UserManagement userManagement : userManagementList) {
            isSuccess = passwordEncoder.matches(loginPassword, userManagement.getPassword());
            log.info("version : " + version);
           if(isSuccess) {
               return userManagement;
           }
        }
        if (!isSuccess) {
            throw new BadRequestException("Password is wrong. Please enter correct password.");
        }
        return null;
    }

    /**
     * Create UserManagement
     *
     * @param addUserManagement
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public UserManagement createUserManagement(AddUserManagement addUserManagement, String loginUserID)
            throws IllegalAccessException, InvocationTargetException {

        Optional<UserManagement> duplicateCheck = userManagementRepository.findByLanguageIdAndCompanyIdAndUserIdAndDeletionIndicator(
                addUserManagement.getLanguageId(), addUserManagement.getCompanyId(), addUserManagement.getUserId(), 0L);

        Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                addUserManagement.getCompanyId(), addUserManagement.getLanguageId(), 0L);
        if (dbCompany.isEmpty()) {
            throw new BadRequestException("CompanyId - " + addUserManagement.getCompanyId() + ", LanguageId - " + addUserManagement.getLanguageId() + " doesn't exists");
        } else if (duplicateCheck.isPresent()) {
            throw new BadRequestException("Record is getting Duplicated with given values : userId - " + addUserManagement.getUserId());
        } else {
            log.info("new User --> {}", addUserManagement);
            UserManagement userManagement = new UserManagement();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addUserManagement.getLanguageId(), addUserManagement.getCompanyId());
            BeanUtils.copyProperties(addUserManagement, userManagement, CommonUtils.getNullPropertyNames(addUserManagement));

            if (iKeyValuePair != null) {
                userManagement.setLanguageIdAndDescription(iKeyValuePair.getLangDesc());
                userManagement.setCompanyIdAndDescription(iKeyValuePair.getCompanyDesc());
            }
            if (addUserManagement.getUserRoleId() != null) {
                IKeyValuePair iKeyValuePair1 = replicaRoleAccessRepository.getRoleDesc(
                        addUserManagement.getLanguageId(), addUserManagement.getCompanyId(), addUserManagement.getUserRoleId());
                if (iKeyValuePair1 != null) {
                    userManagement.setUserRoleIdAndDescription(iKeyValuePair1.getUserRoleDesc());
                }
            }
            if (addUserManagement.getUserTypeId() != null) {
                IKeyValuePair iKeyValuePair2 = replicaUserTypeRepository.getUserTypeDesc(
                        addUserManagement.getLanguageId(), addUserManagement.getCompanyId(), addUserManagement.getUserTypeId());
                if (iKeyValuePair2 != null) {
                    userManagement.setUserTypeIdAndDescription(iKeyValuePair2.getUserTypeDesc());
                }
            }

            // Password encryption
            try {
                String encodedPwd = passwordEncoder.encodePassword(addUserManagement.getPassword());
                userManagement.setPassword(encodedPwd);
                userManagement.setUserId(addUserManagement.getUserId().toUpperCase());
                userManagement.setCreatedBy(loginUserID);
                userManagement.setCreatedOn(new Date());
                userManagement.setUpdatedBy(loginUserID);
                userManagement.setUpdatedOn(new Date());
                userManagement.setDeletionIndicator(0L);
                return userManagementRepository.save(userManagement);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


            /**
             * Update UserManagement
             *
             * @param userId
             * @param companyId
             * @param languageId
             * @param updateUserManagement
             * @param loginUserID
             * @return
             * @throws IllegalAccessException
             * @throws InvocationTargetException
             */
            @Transactional
            public UserManagement updateUserManagement (String userId, String companyId, String
            languageId, UpdateUserManagement updateUserManagement, String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
                try {
                    UserManagement dbUserManagement = getUserManagement(languageId, companyId, userId);

                    if (updateUserManagement.getUserRoleId() != null) {
                        IKeyValuePair iKeyValuePair = replicaRoleAccessRepository.getRoleDesc(languageId, companyId, updateUserManagement.getUserRoleId());
                        if (iKeyValuePair != null) {
                            dbUserManagement.setUserRoleIdAndDescription(iKeyValuePair.getUserRoleDesc());
                        }
                    }
                    if (updateUserManagement.getUserTypeId() != null) {
                        IKeyValuePair iKeyValuePair1 = replicaUserTypeRepository.getUserTypeDesc(languageId, companyId, updateUserManagement.getUserTypeId());
                        if (iKeyValuePair1 != null) {
                            dbUserManagement.setUserTypeIdAndDescription(iKeyValuePair1.getUserTypeDesc());
                        }
                    }
                    BeanUtils.copyProperties(updateUserManagement, dbUserManagement, CommonUtils.getNullPropertyNames(updateUserManagement));

                    if (updateUserManagement.getPassword() != null) {
                        // Password encryption
                        String encodedPwd = passwordEncoder.encodePassword(updateUserManagement.getPassword());
                        dbUserManagement.setPassword(encodedPwd);
                    }
                    dbUserManagement.setUpdatedBy(loginUserID);
                    dbUserManagement.setUpdatedOn(new Date());
                    return userManagementRepository.save(dbUserManagement);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            /**
             * Delete UserManagement
             *
             * @param userId
             * @param languageId
             * @param companyId
             * @param loginUserID
             */
            public void deleteUserManagement (String userId, String languageId, String companyId, String loginUserID){
                UserManagement dbUserManagement = getUserManagement(languageId, companyId, userId);
                if (dbUserManagement != null) {
                    dbUserManagement.setUpdatedBy(loginUserID);
                    dbUserManagement.setUpdatedOn(new Date());
                    dbUserManagement.setDeletionIndicator(1L);
                    userManagementRepository.save(dbUserManagement);
                } else {
                    throw new EntityNotFoundException("Error in deleting userId: " + userId);
                }
            }

            //===============================================Replica==================================================

            /**
             * Get all UserManagement details
             *
             * @return
             */
            public List<ReplicaUserManagement> replicaGetUserManagements () {
                List<ReplicaUserManagement> userManagementList = replicaUserManagementRepository.findAll();
                userManagementList = userManagementList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
                return userManagementList;

            }

            /**
             * Get UserManagement
             *
             * @param languageId
             * @param companyId
             * @param userId
             * @return
             */
            public ReplicaUserManagement replicaGetUserManagement (String languageId, String companyId, String userId){
                Optional<ReplicaUserManagement> dbUserManagement = replicaUserManagementRepository.
                        findByLanguageIdAndCompanyIdAndUserIdAndDeletionIndicator(languageId, companyId, userId, 0L);
                if (dbUserManagement.isEmpty()) {
                    throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId +
                            " and userId - " + userId + " doesn't exists");
                }
                return dbUserManagement.get();
            }

            /**
             * Find UserManagement
             *
             * @param findUserManagement
             * @return
             * @throws ParseException
             */
            public List<ReplicaUserManagement> findUserManagement (FindUserManagement findUserManagement) throws
            ParseException {

                ReplicaUserManagementSpecification spec = new ReplicaUserManagementSpecification(findUserManagement);
                List<ReplicaUserManagement> results = replicaUserManagementRepository.findAll(spec);
                log.info("found Users --> {}", results);
                return results;
            }

        }
