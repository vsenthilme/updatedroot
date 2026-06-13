package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.language.AddLanguage;
import com.courier.overc360.api.idmaster.primary.model.language.Language;
import com.courier.overc360.api.idmaster.primary.model.language.UpdateLanguage;
import com.courier.overc360.api.idmaster.primary.repository.LanguageRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.language.FindLanguage;
import com.courier.overc360.api.idmaster.replica.model.language.ReplicaLanguage;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaLanguageRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaLanguageSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ReplicaLanguageRepository replicaLanguageRepository;


    /*======================================================PRIMARY=============================================================*/

    /**
     * Get Language
     *
     * @param languageId
     * @return
     */
    public Language getLanguage(String languageId) {
        Optional<Language> dbLanguageId =
                languageRepository.findByLanguageIdAndDeletionIndicator(languageId, 0L);
        if (dbLanguageId.isEmpty()) {
            throw new BadRequestException("LanguageId - " + languageId + " doesn't exists");
        }
        return dbLanguageId.get();
    }

    /**
     * Create Language
     *
     * @param addLanguage
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Language createLanguage(AddLanguage addLanguage, String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        try {
            Optional<Language> duplicateLanguage = languageRepository.findByLanguageIdAndDeletionIndicator(
                    addLanguage.getLanguageId(), 0L);
            if (duplicateLanguage.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with given values : languageId - " + addLanguage.getLanguageId());
            } else {
                Language dbLanguage = new Language();
                log.info("new Language --> " + addLanguage);
                BeanUtils.copyProperties(addLanguage, dbLanguage, CommonUtils.getNullPropertyNames(addLanguage));
                dbLanguage.setDeletionIndicator(0L);
                dbLanguage.setCreatedBy(loginUserID);
                dbLanguage.setCreatedOn(new Date());
                dbLanguage.setUpdatedBy(loginUserID);
                dbLanguage.setUpdatedOn(new Date());
                return languageRepository.save(dbLanguage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Update Language Description in all Masters Tables using Stored Procedure
    private void updateLangDescSP(String languageId, UpdateLanguage updateLanguage, Language dbLanguage) {

        if (updateLanguage.getLanguageDescription() != null) {
            if (updateLanguage.getLanguageDescription().isBlank()) {
                throw new BadRequestException("Language Description cannot be blank");
            }
            boolean isLangDescChanged = !dbLanguage.getLanguageDescription().equalsIgnoreCase(updateLanguage.getLanguageDescription());
            if (isLangDescChanged) {
                String newLangDesc = updateLanguage.getLanguageDescription();
                log.info("new Language Description --> {}", newLangDesc);
                String oldLanguageDesc = dbLanguage.getLanguageDescription();
                try {
                    // Update Language Desc in all Masters Tables
                    languageRepository.updateLanguageDescProc(languageId, oldLanguageDesc, newLangDesc);
                    log.info("new language Description - {} updated in all Masters Tables", newLangDesc);
                } catch (Exception e) {
                    log.error("Failed to update new language Description in all Masters Tables : " + e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Update Language
     *
     * @param languageId
     * @param updateLanguage
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Language updateLanguage(String languageId, UpdateLanguage updateLanguage, String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        try {
            Language dbLanguage = getLanguage(languageId);
            BeanUtils.copyProperties(updateLanguage, dbLanguage, CommonUtils.getNullPropertyNames(updateLanguage));
            dbLanguage.setUpdatedBy(loginUserID);
            dbLanguage.setUpdatedOn(new Date());
            return languageRepository.save(dbLanguage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Language
     *
     * @param languageId
     * @param loginUserID
     */
    public void deleteLanguage(String languageId, String loginUserID) {

        Language dbLanguage = getLanguage(languageId);

        Long languageCount = languageRepository.getLanguageCount(languageId);
        if (languageCount != null) {
            if (languageCount > 0) {
                log.info("languageCount --> {}", languageCount);
                throw new BadRequestException("Records present in associated tables with languageId - " + languageId);
            }
        }

        if (dbLanguage != null) {
            dbLanguage.setDeletionIndicator(1L);
            dbLanguage.setUpdatedBy(loginUserID);
            dbLanguage.setUpdatedOn(new Date());
            languageRepository.save(dbLanguage);
        } else {
            throw new BadRequestException("Error in deleting LanguageId - " + languageId);
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get All Language Details
     *
     * @return
     */
    public List<ReplicaLanguage> getAllLanguageIds() {
        List<ReplicaLanguage> languageList = replicaLanguageRepository.findAll();
        languageList = languageList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return languageList;
    }

    /**
     * Get Language
     *
     * @param languageId
     * @return
     */
    public ReplicaLanguage getReplicaLanguage(String languageId) {
        Optional<ReplicaLanguage> dbLanguage =
                replicaLanguageRepository.findByLanguageIdAndDeletionIndicator(languageId, 0L);
        if (dbLanguage.isEmpty()) {
            throw new BadRequestException("LanguageId - " + languageId + " doesn't exists");
        }
        return dbLanguage.get();
    }


    /**
     * Find Language
     *
     * @param findLanguage
     * @return
     * @throws ParseException
     */
    public List<ReplicaLanguage> findLanguage(FindLanguage findLanguage) throws ParseException {

        ReplicaLanguageSpecification spec = new ReplicaLanguageSpecification(findLanguage);
        List<ReplicaLanguage> results = replicaLanguageRepository.findAll(spec);
        log.info("found Languages --> {}", results);
        return results;
    }

}
