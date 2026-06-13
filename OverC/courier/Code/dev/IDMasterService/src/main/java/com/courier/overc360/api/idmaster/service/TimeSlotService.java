package com.courier.overc360.api.idmaster.service;


import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.timeslot.AddTimeSlot;
import com.courier.overc360.api.idmaster.primary.model.timeslot.TimeSlot;
import com.courier.overc360.api.idmaster.primary.repository.TimeSlotRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.timeslot.FindTimeSlot;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaTimeSlotRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaTimeslotSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TimeSlotService {

     @Autowired
     private TimeSlotRepository timeSlotRepository;

     @Autowired
     private ReplicaTimeSlotRepository replicaTimeSlotRepository;

     @Autowired
    ReplicaCompanyRepository replicaCompanyRepository;

    /**
     *
     * @param addTimeSlot
     * @param loginUserID
     * @return
     */

    @Transactional
    public List<TimeSlot> createTimeslot(List<AddTimeSlot> addTimeSlot, String loginUserID) {

        List<TimeSlot> timeSlots = new ArrayList<>();
        try {
            addTimeSlot.stream().forEach(timeSlot -> {

                boolean duplicate = timeSlotRepository.existsByLanguageIdAndCompanyIdAndTimeSlotIdAndDeletionIndicator(
                        timeSlot.getLanguageId(), timeSlot.getCompanyId(), timeSlot.getTimeSlotId(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }

                // Create new Timeslot and related entities
                TimeSlot newTimeslot = new TimeSlot();
                BeanUtils.copyProperties(timeSlot, newTimeslot, CommonUtils.getNullPropertyNames(timeSlot));

                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newTimeslot.getLanguageId(), newTimeslot.getCompanyId());
                if (iKeyValuePair != null) {
                    newTimeslot.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newTimeslot.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                newTimeslot.setCreatedBy(loginUserID);
                newTimeslot.setCreatedOn(new Date());
                newTimeslot.setUpdatedBy(loginUserID);
                newTimeslot.setUpdatedOn(new Date());
                TimeSlot timeSlotCreate = timeSlotRepository.save(newTimeslot);
                timeSlots.add(timeSlotCreate);
            });
        }  catch (Exception e) {
            throw new BadRequestException("Npr Create Error " + e);
        }
        return timeSlots;



    }


    /**
     *
     * @param updateTimeslot
     * @param loginUserID
     * @return
     */
    public List<TimeSlot> updateTimeslot(List<AddTimeSlot> updateTimeslot, String loginUserID) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        for (AddTimeSlot updateTime : updateTimeslot) {
            Optional<TimeSlot> dbTimeslot = timeSlotRepository.findByLanguageIdAndCompanyIdAndTimeSlotIdAndDeletionIndicator(
                    updateTime.getLanguageId(), updateTime.getCompanyId(), updateTime.getTimeSlotId(), 0L);

            if (dbTimeslot.isPresent()) {
                TimeSlot newTimeslot = dbTimeslot.get();
                BeanUtils.copyProperties(updateTime, newTimeslot, CommonUtils.getNullPropertyNames(updateTime));
                newTimeslot.setUpdatedOn(new Date());
                newTimeslot.setUpdatedBy(loginUserID);
                TimeSlot timeSlot = timeSlotRepository.save(newTimeslot);
                timeSlots.add(timeSlot);
            }
        }
        return timeSlots;
    }

    /**
     * 
     * @param deleteTimeslotList
     * @param loginUserID
     */
    public void deleteTimeslotList(List<TimeSlot> deleteTimeslotList, String loginUserID) {

        if (deleteTimeslotList != null && !deleteTimeslotList.isEmpty()) {
            log.info("given values to delete Timeslot --->  {}", deleteTimeslotList);

            deleteTimeslotList.parallelStream().forEach(deleteInput -> {
                TimeSlot dbTimeslot = getTimeSlot(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getTimeSlotId());
                dbTimeslot.setDeletionIndicator(1L);
                dbTimeslot.setUpdatedBy(loginUserID);
                dbTimeslot.setUpdatedOn(new Date());
                timeSlotRepository.save(dbTimeslot);
            });
        }
    }


    /**
     *
     * @param languageId
     * @param companyId
     * @param timeSlotId
     * @return
     */
    private TimeSlot getTimeSlot(String languageId, String companyId, String timeSlotId) {
        Optional<TimeSlot> dbTimeslot = timeSlotRepository.findByLanguageIdAndCompanyIdAndTimeSlotIdAndDeletionIndicator(
                languageId, companyId, timeSlotId, 0L);
        if (dbTimeslot.isEmpty()) {
            throw new BadRequestException("Timeslot with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", timeSlotId: " + timeSlotId +  " doesn't exists");
        }
        return dbTimeslot.get();
    }

    public List<ReplicaTimeSlot> getAllTimeslot() {
        List<ReplicaTimeSlot> timeSlotList = replicaTimeSlotRepository.findAll();

        timeSlotList = timeSlotList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return timeSlotList;
    }

    /**
     *
     * @param languageId
     * @param companyId
     * @param timeslotId
     * @return
     */
    public ReplicaTimeSlot getReplicaTimeslot(String languageId, String companyId, String timeslotId) {
        Optional<ReplicaTimeSlot> dbTimeslot = replicaTimeSlotRepository.findByLanguageIdAndCompanyIdAndTimeSlotIdAndDeletionIndicator(
                languageId, companyId, timeslotId, 0L);

        if (dbTimeslot.isEmpty()) {
            throw new BadRequestException("Timeslot with given values : languageId: " + languageId + ", companyId: " + companyId + ", timeSlotId: " + timeslotId +  " doesn't exists");
        }
        return dbTimeslot.get();
    }

    /**
     *
     * @param findTimeSlot
     * @return
     */
    public List<ReplicaTimeSlot> findTimeSlot(FindTimeSlot findTimeSlot) {
        ReplicaTimeslotSpecification spec = new ReplicaTimeslotSpecification(findTimeSlot);
        List<ReplicaTimeSlot> results = replicaTimeSlotRepository.findAll(spec);
        return results;
    }
}
