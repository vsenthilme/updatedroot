package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.numberange.AddNumberRange;
import com.courier.overc360.api.midmile.primary.model.numberange.NumberRange;
import com.courier.overc360.api.midmile.primary.model.numberange.UpdateNumberRange;
import com.courier.overc360.api.midmile.primary.repository.NumberRangeRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class NumberRangeService {

    @Autowired
    NumberRangeRepository numberRangeRepository;


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
        Optional<NumberRange> numberRange =
                numberRangeRepository.findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
                        languageId, numberRangeCode, numberRangeObject, 0L);

        if (numberRange.isEmpty()) {
            throw new BadRequestException("The given NumberRangeCode - " + numberRangeCode + ", languageId - "
                    + languageId + " and numberRangeObject - " + numberRangeObject + " doesn't exists");
        }

        return numberRange.get();
    }

    /**
     * GetNextNumberRange
     *
     * @param numberRangeObject
     * @return
     */
    public String getNextNumberRange(String numberRangeObject) { //loadtype
        log.info("numberRangeObject : " + numberRangeObject);

        Optional<NumberRange> duplicateNumberRange =
                numberRangeRepository.findByNumberRangeObjectAndDeletionIndicator(numberRangeObject, 0L);
        if (duplicateNumberRange.isEmpty()) {
            throw new BadRequestException("Record not found for given NumberRangeObject - " + numberRangeObject);
        } else {
            NumberRange numberRange = duplicateNumberRange.get();
            log.info("Current record --> " + numberRange);

            String strCurrentValue = numberRange.getNumberRangeCurrent();
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
            log.info("currentValue --> " + currentValue);
            numberRange.setNumberRangeCurrent(String.valueOf(currentValue));
            strCurrentValue = String.valueOf(currentValue);
//        }
            log.info("New value NumberRange --> " + numberRange);
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
            newNumberRange.setDeletionIndicator(0L);
            newNumberRange.setCreatedBy(loginUserID);
            newNumberRange.setCreatedOn(new Date());
            newNumberRange.setUpdatedBy(loginUserID);
            newNumberRange.setUpdatedOn(new Date());
            return numberRangeRepository.save(newNumberRange);
        }
    }

    /**
     * GetNextStringNumberRange
     *
     * @param numberRangeObject
     * @return
     */
    public String getNextStringNumberRange(String numberRangeObject) { // loadtype
        log.info("numberRangeObject : " + numberRangeObject);

        Optional<NumberRange> duplicateNumberRange =
                numberRangeRepository.findByNumberRangeObjectAndDeletionIndicator(numberRangeObject, 0L);
        if (duplicateNumberRange.isEmpty()) {
            throw new BadRequestException("Record not found for given NumberRangeObject - " + numberRangeObject);
        } else {
            NumberRange numberRange = duplicateNumberRange.get();
            log.info("Current record --> " + numberRange);

            String strCurrentValue = numberRange.getNumberRangeCurrent();
            log.info("Current value before increment --> " + strCurrentValue);

            String prefix = strCurrentValue.replaceAll("\\d", ""); // Extract prefix (non-numeric characters)
            String numericPart = strCurrentValue.replaceAll("\\D", ""); // Extract numeric part (numeric characters)

            Long currentValue = Long.valueOf(numericPart); // Convert numeric part to Long
            currentValue++; // Increment the numeric part

            // Format the incremented number back with leading zeros
            String incrementedNumber = String.format("%0" + numericPart.length() + "d", currentValue);

            // Concatenate the prefix and the incremented numeric part
            String newNumberRange = prefix + incrementedNumber;

            log.info("New NumberRange value after increment --> " + newNumberRange);

            numberRange.setNumberRangeCurrent(newNumberRange);
            numberRangeRepository.save(numberRange);
            log.info("New value has been updated in NumberRangeCurrent column");

            return newNumberRange;
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
            numberRangeRepository.save(numberRange);
        } else {
            throw new BadRequestException("Error in deleting NumberRange Id - " + numberRangeCode);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * @param findNumberRange
     * @return
     * @throws ParseException
     */
//    public List<ReplicaNumberRange> findNumberRange(FindNumberRange findNumberRange) throws ParseException {

//        if (findNumberRange.getStartCreatedOn() != null && findNumberRange.getStartCreatedOn() != null) {
//            Date date = DateUtils.convertStringToYYYYMMDD(findNumberRange.getStartCreatedOn());
//            Date date1 = DateUtils.convertStringToYYYYMMDD(findNumberRange.getEndCreatedOn());
//
//            Date[] dates = DateUtils.addTimeToDatesForSearch(date, date1);
//            findNumberRange.setFromDate(dates[0]);
//            findNumberRange.setToDate(dates[1]);
//        }
//        ReplicaNumberRangeSpecification spec = new ReplicaNumberRangeSpecification(findNumberRange);
//        List<ReplicaNumberRange> results = replicaNumberRangeRepository.findAll(spec);
//        log.info("found NumberRangeCodes --> " + results);
//        return results;
//
//    }

    /**
     * Get ReplicaNumberRange
     *
     * @param languageId
     * @param numberRangeCode
     * @param numberRangeObject
     * @return
     */
//    public ReplicaNumberRange replicaGetNumberRange(String languageId, Long numberRangeCode, String numberRangeObject) {
//        Optional<ReplicaNumberRange> numberRange =
//                replicaNumberRangeRepository.findByLanguageIdAndNumberRangeCodeAndNumberRangeObjectAndDeletionIndicator(
//                        languageId, numberRangeCode, numberRangeObject, 0L);
//
//        if (numberRange.isEmpty()) {
//            throw new BadRequestException("The given NumberRangeCode - " + numberRangeCode + ", languageId - "
//                    + languageId + " and numberRangeObject - " + numberRangeObject + " doesn't exists");
//        }
//
//        return numberRange.get();
//    }


    /**
     * Get All Number Ranges
     *
     * @return
     */
//    public List<ReplicaNumberRange> getNumberRanges() {
//        List<ReplicaNumberRange> numberRangeList = replicaNumberRangeRepository.findAll();
//        numberRangeList = numberRangeList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
//        return numberRangeList;
//    }

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
