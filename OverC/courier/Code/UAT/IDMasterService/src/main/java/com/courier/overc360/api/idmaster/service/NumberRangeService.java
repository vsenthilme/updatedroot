package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.numberange.AddNumberRange;
import com.courier.overc360.api.idmaster.primary.model.numberange.NumberRange;
import com.courier.overc360.api.idmaster.primary.model.numberange.UpdateNumberRange;
import com.courier.overc360.api.idmaster.primary.repository.NumberRangeRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.numberrange.FindNumberRange;
import com.courier.overc360.api.idmaster.replica.model.numberrange.ReplicaNumberRange;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaNumberRangeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaNumberRangeSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NumberRangeService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    NumberRangeRepository numberRangeRepository;

    @Autowired
    ReplicaNumberRangeRepository replicaNumberRangeRepository;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get NumberRange
     *
     * @param languageId
     * @param numberRangeCode
     * @param numberRangeObject
     * @return
     */
    public NumberRange getNumberRange(String languageId, Long numberRangeCode, String numberRangeObject) {
        Optional<NumberRange> dbNumberRange =
                numberRangeRepository.findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
                        languageId, numberRangeCode, numberRangeObject, 0L);
        if (dbNumberRange.isEmpty()) {
            throw new BadRequestException("The given NumberRangeCode - " + numberRangeCode + ", languageId - "
                    + languageId + " and numberRangeObject - " + numberRangeObject + " doesn't exists");
        }
        return dbNumberRange.get();
    }

    /**
     * GetNextNumberRange
     *
     * @param numberRangeObject
     * @return
     */
    public String getNextNumberRange(String numberRangeObject) {
//        log.info("numberRangeObject : " + numberRangeObject);

        Optional<NumberRange> dbNumberRange = numberRangeRepository.findByNumberRangeObjectAndDeletionIndicator(numberRangeObject, 0L);
        if (dbNumberRange.isEmpty()) {
            throw new BadRequestException("Record not found for given NumberRangeObject - " + numberRangeObject);
        } else {
            NumberRange numberRange = dbNumberRange.get();
//            log.info("Current record --> " + numberRange);

            String strCurrentValue = numberRange.getNumberRangeCurrent();
            log.info("current Value --> {}", strCurrentValue);
            Long currentValue = 0L;
//        if (strCurrentValue.startsWith("AL")) { // Increment logic for AuditLog Insert
//            strCurrentValue = strCurrentValue.substring(2); // AL1000002
//            currentValue = Long.valueOf(strCurrentValue);
//            currentValue++;
//            strCurrentValue = "AL" + String.valueOf(currentValue);
//            numberRange.setNumberRangeCurrent(strCurrentValue);
//            log.info("currentValue of AL: " + currentValue);
//        } else {
            currentValue = Long.valueOf(strCurrentValue);
            currentValue++;
            numberRange.setNumberRangeCurrent(String.valueOf(currentValue));
            log.info("new Value --> {}", currentValue);
            strCurrentValue = String.valueOf(currentValue);
//        }
//            log.info("New value NumberRange --> " + numberRange);
            numberRangeRepository.save(numberRange);
            log.info("New value has been updated in NumberRangeCurrent column");
            return strCurrentValue;
        }
    }

    /**
     * @param addNumberRange
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public NumberRange createNumberRange(AddNumberRange addNumberRange, String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        Optional<NumberRange> duplicateNumRange = numberRangeRepository.findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
                addNumberRange.getLanguageId(), addNumberRange.getNumberRangeCode(), addNumberRange.getNumberRangeObject(), 0L);
        if (duplicateNumRange.isPresent()) {
            throw new BadRequestException("Record is getting Duplicated with the given values");
        } else {
            NumberRange newNumberRange = new NumberRange();
            BeanUtils.copyProperties(addNumberRange, newNumberRange, CommonUtils.getNullPropertyNames(addNumberRange));
            String statusDesc = replicaStatusRepository.getStatusDescription(addNumberRange.getNumberRangeStatus());
            if (statusDesc != null) {
                newNumberRange.setStatusDescription(statusDesc);
            }
            newNumberRange.setDeletionIndicator(0L);
            newNumberRange.setCreatedBy(loginUserID);
            newNumberRange.setCreatedOn(new Date());
            newNumberRange.setUpdatedBy(loginUserID);
            newNumberRange.setUpdatedOn(new Date());
            return numberRangeRepository.save(newNumberRange);
        }
    }

    /**
     * Update NumberRange
     *
     * @param numberRangeCode
     * @param languageId
     * @param numberRangeObject
     * @param loginUserID
     * @param updateNumberRange
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public NumberRange updateNumberRange(Long numberRangeCode, String languageId, String numberRangeObject,
                                         String loginUserID, UpdateNumberRange updateNumberRange)
            throws IllegalAccessException, InvocationTargetException {
        NumberRange dbNumberRange = getNumberRange(languageId, numberRangeCode, numberRangeObject);
        BeanUtils.copyProperties(updateNumberRange, dbNumberRange, CommonUtils.getNullPropertyNames(updateNumberRange));
        if (updateNumberRange.getNumberRangeStatus() != null && !updateNumberRange.getNumberRangeStatus().isEmpty()) {
            String statusDesc = replicaStatusRepository.getStatusDescription(updateNumberRange.getNumberRangeStatus());
            if (statusDesc != null) {
                dbNumberRange.setStatusDescription(statusDesc);
            }
        }
        dbNumberRange.setUpdatedBy(loginUserID);
        dbNumberRange.setUpdatedOn(new Date());
        return numberRangeRepository.save(dbNumberRange);
    }

    /**
     * Delete NumberRange
     *
     * @param numberRangeCode
     * @param languageId
     * @param numberRangeObject
     * @param loginUserID
     */
    public void deleteNumberRange(Long numberRangeCode, String languageId, String numberRangeObject, String loginUserID) {
        NumberRange numberRange = getNumberRange(languageId, numberRangeCode, numberRangeObject);
        if (numberRange != null) {
            numberRange.setDeletionIndicator(1L);
            numberRange.setUpdatedBy(loginUserID);
            numberRange.setUpdatedOn(new Date());
            numberRangeRepository.save(numberRange);
        } else {
            throw new BadRequestException("Error in deleting NumberRangeId - " + numberRangeCode);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * @param findNumberRange
     * @return
     * @throws ParseException
     */
    public List<ReplicaNumberRange> findNumberRange(FindNumberRange findNumberRange) throws ParseException {

//        if (findNumberRange.getStartCreatedOn() != null && findNumberRange.getStartCreatedOn() != null) {
//            Date date = DateUtils.convertStringToYYYYMMDD(findNumberRange.getStartCreatedOn());
//            Date date1 = DateUtils.convertStringToYYYYMMDD(findNumberRange.getEndCreatedOn());
//
//            Date[] dates = DateUtils.addTimeToDatesForSearch(date, date1);
//            findNumberRange.setFromDate(dates[0]);
//            findNumberRange.setToDate(dates[1]);
//        }
        ReplicaNumberRangeSpecification spec = new ReplicaNumberRangeSpecification(findNumberRange);
        List<ReplicaNumberRange> results = replicaNumberRangeRepository.findAll(spec);
        log.info("found NumberRangeCodes --> " + results);
        return results;

    }

    /**
     * Get ReplicaNumberRange
     *
     * @param languageId
     * @param numberRangeCode
     * @param numberRangeObject
     * @return
     */
    public ReplicaNumberRange replicaGetNumberRange(String languageId, Long numberRangeCode, String numberRangeObject) {
        Optional<ReplicaNumberRange> dbNumberRange =
                replicaNumberRangeRepository.findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
                        languageId, numberRangeCode, numberRangeObject, 0L);
        if (dbNumberRange.isEmpty()) {
            throw new BadRequestException("The given NumberRangeCode - " + numberRangeCode + ", languageId - "
                    + languageId + " and numberRangeObject - " + numberRangeObject + " doesn't exists");
        }
        return dbNumberRange.get();
    }


    /**
     * Get All Number Ranges
     *
     * @return
     */
    public List<ReplicaNumberRange> getNumberRanges() {
        List<ReplicaNumberRange> numberRangeList = replicaNumberRangeRepository.findAll();
        numberRangeList = numberRangeList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return numberRangeList;
    }

//    /**
//     * Get Next NumberRangeCode
//     *
//     * @param numberRangeCode
//     * @param numberRangeObject
//     * @param languageId
//     * @return
//     */
//    public String getNextNumberRange(Long numberRangeCode, String numberRangeObject, String languageId) {
//        log.info("numberRangeCode : " + numberRangeCode);
//        Optional<NumberRange> numberRangeOpt = numberRangeRepository.findByNumberRangeCodeAndNumberRangeObjectAndLanguageId(
//                numberRangeCode, numberRangeObject, languageId);
//        if (numberRangeOpt.isEmpty()) {
//            throw new BadRequestException("Record not found for given NumberRangeCode - " + numberRangeCode);
//        }
////        NumberRange currentNumberRange = numberRangeOpt.get();
//
//        Optional<NumberRange> optNumberRange =
//                numberRangeRepository.findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
//                        languageId, numberRangeCode, numberRangeObject, 0L);
//
//        NumberRange numberRange = optNumberRange.get();
//        log.info("Current record --> " + numberRange);
//
//        String strCurrentValue = numberRange.getNumberRangeCurrent();
//        Long currentValue = 0L;
////        if (strCurrentValue.startsWith("AL")) { // Increment logic for AuditLog Insert
////            strCurrentValue = strCurrentValue.substring(2); // AL1000002
////            currentValue = Long.valueOf(strCurrentValue);
////            currentValue++;
////            strCurrentValue = "AL" + String.valueOf(currentValue);
////            numberRange.setNumberRangeCurrent(strCurrentValue);
////            log.info("currentValue of AL: " + currentValue);
////        } else {
//        currentValue = Long.valueOf(strCurrentValue);
//        currentValue++;
//        log.info("currentValue --> " + currentValue);
//        numberRange.setNumberRangeCurrent(String.valueOf(currentValue));
//        strCurrentValue = String.valueOf(currentValue);
////        }
//        log.info("New value NumberRange --> " + numberRange);
//        numberRangeRepository.save(numberRange);
//        log.info("New value has been updated in NumberRangeCurrent column");
//        return strCurrentValue;
//    }


}
