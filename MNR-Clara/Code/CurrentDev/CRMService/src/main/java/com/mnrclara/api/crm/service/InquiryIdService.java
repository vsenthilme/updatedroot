package com.mnrclara.api.crm.service;

import com.mnrclara.api.crm.controller.exception.BadRequestException;
import com.mnrclara.api.crm.model.InquiryId.AddInquiryId;
import com.mnrclara.api.crm.model.InquiryId.FindInquiryId;
import com.mnrclara.api.crm.model.InquiryId.InquiryId;
import com.mnrclara.api.crm.repository.InquiryIdRepository;
import com.mnrclara.api.crm.repository.specification.InquiryIdSpecification;
import com.mnrclara.api.crm.util.CommonUtils;
import com.mnrclara.api.crm.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InquiryIdService {

    @Autowired
    InquiryIdRepository inquiryIdRepository;

    /**
     * getLanguageIds
     *
     * @return
     */
    public List<InquiryId> getInquiryIdAll() {
        List<InquiryId> dbInquiryId = inquiryIdRepository.findAll();
        dbInquiryId = dbInquiryId.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return dbInquiryId;
    }

    /**
     *
     * @param inquiryId
     * @return
     */
    public List<InquiryId> getInquiryId(Long inquiryId) {
        List<InquiryId> dbInquiryId = inquiryIdRepository.findByInquiryIdAndDeletionIndicator(inquiryId, 0L);
        if (dbInquiryId == null || dbInquiryId.isEmpty()) {
            throw new BadRequestException("InquiryId with ID: " + inquiryId + " doesn't exist");
        }
        return dbInquiryId;
    }

    /**
     * @param inquiryIds
     * @param loginUserID
     * @return
     */
    public List<InquiryId> createInquiryId(List<AddInquiryId> inquiryIds, String loginUserID) {
        List<InquiryId> savedInquiryIds = new ArrayList<>();
        for (AddInquiryId inquiryId : inquiryIds) {
            InquiryId dbInquiryId = new InquiryId();
            log.info("newInquiryId: " + inquiryId);
            BeanUtils.copyProperties(inquiryId, dbInquiryId, CommonUtils.getNullPropertyNames(inquiryId));
            dbInquiryId.setUploadedBy(loginUserID);
            dbInquiryId.setReUploadedBy(loginUserID);
            dbInquiryId.setUploadedOn(new Date());
            dbInquiryId.setReUploadedOn(new Date());
            savedInquiryIds.add(inquiryIdRepository.save(dbInquiryId));
        }
        return savedInquiryIds;
    }

    /**
     * @param loginUserID
     * @param updateInquiryIdList
     * @return
     */
    public List<InquiryId> updateInquiryId(String loginUserID, List<AddInquiryId> updateInquiryIdList) {
        List<InquiryId> updatedInquiryList = new ArrayList<>();
        List<AddInquiryId> createInquiryList = new ArrayList<>();
        for (AddInquiryId inquiryId : updateInquiryIdList) {
            InquiryId dbInquiryId = inquiryIdRepository.findByIdAndDeletionIndicator(inquiryId.getId(), 0L);
            if (dbInquiryId != null) {
                BeanUtils.copyProperties(inquiryId, dbInquiryId, CommonUtils.getNullPropertyNames(inquiryId));
                dbInquiryId.setReUploadedBy(loginUserID);
                dbInquiryId.setDeletionIndicator(0L);
                dbInquiryId.setReUploadedOn(new Date());
                inquiryIdRepository.save(dbInquiryId);
                updatedInquiryList.add(dbInquiryId);
            } else {
                createInquiryList.add(inquiryId);
            }
        }
        if (createInquiryList != null && !createInquiryList.isEmpty()) {
            List<InquiryId> createdInquiryList = createInquiryId(createInquiryList, loginUserID);
        }
        return updatedInquiryList;
    }

    /**
     * @param id
     * @param inquiryId
     * @param loginUserID
     */
    public void deleteInquiryId(Long id, Long inquiryId, String loginUserID) {
        InquiryId request = inquiryIdRepository.findByIdAndInquiryIdAndDeletionIndicator(id, inquiryId, 0L);
        if (request == null) {
            throw new EntityNotFoundException("Inquiry with ID: " + id + "InquiryId " + inquiryId + " doesn't exist");
        }
        request.setDeletionIndicator(1L);
        request.setUploadedBy(loginUserID);
        request.setUploadedOn(new Date());
        inquiryIdRepository.save(request);
    }

    /**
     * @param findInquiryId
     * @return
     * @throws ParseException
     */
    public List<InquiryId> findInquiryId(FindInquiryId findInquiryId) throws ParseException {
        if (findInquiryId.getSStartDate() != null && findInquiryId.getSEndDate() != null) {
            Date stringStartDate = DateUtils.convertStringDateToDate(findInquiryId.getSStartDate());
            Date stringEndDate = DateUtils.convertStringDateToDate(findInquiryId.getSEndDate());
            Date[] dates = DateUtils.addTimeToDatesForSearch(stringStartDate, stringEndDate);
            findInquiryId.setStartDate(dates[0]);
            findInquiryId.setEndDate(dates[1]);
        }
        InquiryIdSpecification spec = new InquiryIdSpecification(findInquiryId);
        List<InquiryId> results = inquiryIdRepository.findAll(spec);
        log.info("results: " + results);
        return results;
    }
}