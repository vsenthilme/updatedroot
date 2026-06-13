package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.fueltracking.AddFuelTracking;
import com.courier.overc360.api.midmile.primary.model.fueltracking.FuelTracking;
import com.courier.overc360.api.midmile.primary.model.fueltracking.UpdateFuelTracking;
import com.courier.overc360.api.midmile.primary.repository.FuelTrackingRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.fueltracking.FindFuelTracking;
import com.courier.overc360.api.midmile.replica.model.fueltracking.ReplicaFuelTracking;
import com.courier.overc360.api.midmile.replica.repository.ReplicaFuelTrackingRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaFuelTrackingSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FuelTrackingService {
    @Autowired
    private FuelTrackingRepository fuelTrackingRepository;
    @Autowired
    private ReplicaFuelTrackingRepository replicaFuelTrackingRepository;

    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param vehicleRegNumber
     * @return
     */

    public FuelTracking getFuelTracking(String companyId, String languageId, String vehicleRegNumber) {
        Optional<FuelTracking> dbFuelTracking = fuelTrackingRepository.findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator(
                companyId, languageId, vehicleRegNumber, 0L);
        if (dbFuelTracking.isEmpty()) {
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " VehicleRegNumber - " + vehicleRegNumber + " doesn't exists");
        }
        return dbFuelTracking.get();
    }


    /**
     * Create
     *
     * @param addFuelTracking
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public FuelTracking createFuelTracking(AddFuelTracking addFuelTracking, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            replicaFuelTrackingRepository.findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator(
                            addFuelTracking.getCompanyId(), addFuelTracking.getLanguageId(), addFuelTracking.getVehicleRegNumber(), 0l)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : VehicleRegNumber - " + addFuelTracking.getVehicleRegNumber());
                    });
//            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
//                            addFuelTracking.getCompanyId(), addFuelTracking.getLanguageId(), 0l)
//                    .orElseThrow(() -> new BadRequestException("The given values : CompanyId - " + addFuelTracking.getCompanyId()
//                            + " and LanguageId - " + addFuelTracking.getLanguageId() + "  doesn't exists"));
            log.info("new FuelTracking --> " + addFuelTracking);
            FuelTracking newFuelTracking = new FuelTracking();
            BeanUtils.copyProperties(addFuelTracking, newFuelTracking, CommonUtils.getNullPropertyNames(addFuelTracking));
            //Save without spacing

            newFuelTracking.setVehicleRegNumber(newFuelTracking.getVehicleRegNumber().replaceAll("\\s", "\\"));

            newFuelTracking.setDeletionIndicator(0l);
            newFuelTracking.setCreatedBy(loginUserID);
            newFuelTracking.setCreatedOn(new Date());
            newFuelTracking.setUpdatedBy(loginUserID);
            newFuelTracking.setUpdatedOn(new Date());
            return fuelTrackingRepository.save(newFuelTracking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param vehicleRegNumber
     * @param updateFuelTracking
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public FuelTracking updateFuelTracking(String companyId, String languageId, String vehicleRegNumber,
                                           UpdateFuelTracking updateFuelTracking, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            FuelTracking dbFuelTracking = getFuelTracking(companyId, languageId, vehicleRegNumber);
            BeanUtils.copyProperties(updateFuelTracking, dbFuelTracking, CommonUtils.getNullPropertyNames(updateFuelTracking));
            dbFuelTracking.setUpdatedOn(new Date());
            dbFuelTracking.setUpdatedBy(loginUserID);
            return fuelTrackingRepository.save(dbFuelTracking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param vehicleRegNumber
     * @param loginUserID
     */


    public void deleteFuelTracking(String companyId, String languageId, String vehicleRegNumber, String loginUserID) {

        FuelTracking dbFuelTracking = getFuelTracking(companyId, languageId, vehicleRegNumber);
        if (dbFuelTracking != null) {
            dbFuelTracking.setDeletionIndicator(1L);
            dbFuelTracking.setUpdatedBy(loginUserID);
            dbFuelTracking.setUpdatedOn(new Date());
            fuelTrackingRepository.save(dbFuelTracking);
        } else {
            throw new EntityNotFoundException("Error in deleting VehicleRegNumber - " + vehicleRegNumber);
        }
    }

    /*======================================================REPLICA=====================================================*/


    public List<ReplicaFuelTracking> getAll() {
        List<ReplicaFuelTracking> fuelTrackingList = replicaFuelTrackingRepository.findAll();
        fuelTrackingList = fuelTrackingList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return fuelTrackingList;
    }


    public ReplicaFuelTracking getReplicaFuelTracking(String companyId, String languageId, String vehicleRegNumber) {
        Optional<ReplicaFuelTracking> dbFuelTracking = replicaFuelTrackingRepository.findByCompanyIdAndLanguageIdAndVehicleRegNumberAndDeletionIndicator(companyId, languageId, vehicleRegNumber, 0l);
        if (dbFuelTracking.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId + " and VehicleRegNumber - " + vehicleRegNumber + " doesn't exists");
        }
        return dbFuelTracking.get();
    }

    public List<ReplicaFuelTracking> findFuelTracking(FindFuelTracking findFuelTracking) {
        ReplicaFuelTrackingSpecification spec = new ReplicaFuelTrackingSpecification(findFuelTracking);
        List<ReplicaFuelTracking> results = replicaFuelTrackingRepository.findAll(spec);
        log.info("found FuelTracking --> {}", results);
        return results;
    }


}

