package com.mnrclara.api.management.service;

import com.mnrclara.api.management.controller.exception.BadRequestException;
import com.mnrclara.api.management.model.mattergeneral.FavouritesMatterGenAcc;
import com.mnrclara.api.management.model.mattergeneral.FavouritesMatterGenImpl;
import com.mnrclara.api.management.model.mattergeneral.SearchFavouritesMatterGenAcc;
import com.mnrclara.api.management.repository.FavouritesMatterGenAccRepository;
import com.mnrclara.api.management.util.CommonUtils;
import com.mnrclara.api.management.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FavouritesMatterGenAccService {

    @Autowired
    private FavouritesMatterGenAccRepository favouritesMatterGenAccRepository;

    /**
     * @param newFavouritesMatterGenAcc
     * @return
     */
    public FavouritesMatterGenAcc createFavouritesMatterGenAcc(FavouritesMatterGenAcc newFavouritesMatterGenAcc) {
        FavouritesMatterGenAcc favouritesMatterGenAcc = favouritesMatterGenAccRepository.findTopByLanguageIdAndClassIdAndClientIdAndMatterNumberAndCreatedByAndDeletionIndicatorOrderByViewedDateDesc(
                newFavouritesMatterGenAcc.getLanguageId(),
                newFavouritesMatterGenAcc.getClassId(),
                newFavouritesMatterGenAcc.getClientId(),
                newFavouritesMatterGenAcc.getMatterNumber(),
                newFavouritesMatterGenAcc.getCreatedBy(),
                0L);
        log.info("favouritesMatterGenAcc : " + favouritesMatterGenAcc);
        if (favouritesMatterGenAcc != null) {
            favouritesMatterGenAcc.setFavourites(newFavouritesMatterGenAcc.isFavourites());
            favouritesMatterGenAcc.setViewedDate(new Date());
            favouritesMatterGenAcc.setUpdatedOn(new Date());
            favouritesMatterGenAcc.setUpdatedBy(newFavouritesMatterGenAcc.getCreatedBy());
            return favouritesMatterGenAccRepository.save(favouritesMatterGenAcc);
        }
        FavouritesMatterGenAcc newFavourites = new FavouritesMatterGenAcc();
        BeanUtils.copyProperties(newFavouritesMatterGenAcc, newFavourites, CommonUtils.getNullPropertyNames(newFavouritesMatterGenAcc));
        newFavourites.setViewedDate(new Date());
        newFavourites.setCreatedOn(new Date());
        newFavourites.setDeletionIndicator(0L);
        log.info("favouritesMatterGenAcc New Created: " + newFavourites);
        return favouritesMatterGenAccRepository.save(newFavourites);
    }

    /**
     * @param searchFavouritesMatterGenAcc
     * @return
     * @throws ParseException
     */
    public List<FavouritesMatterGenImpl> findFavouritesMatterGenAcc(SearchFavouritesMatterGenAcc searchFavouritesMatterGenAcc) throws ParseException {
        if (searchFavouritesMatterGenAcc.getFromDate() != null && searchFavouritesMatterGenAcc.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchFavouritesMatterGenAcc.getFromDate(), searchFavouritesMatterGenAcc.getToDate());
            searchFavouritesMatterGenAcc.setFromDate(dates[0]);
            searchFavouritesMatterGenAcc.setToDate(dates[1]);
        }
        if (searchFavouritesMatterGenAcc.getMatterNumber() == null || searchFavouritesMatterGenAcc.getMatterNumber().isEmpty()) {
            searchFavouritesMatterGenAcc.setMatterNumber(null);
        }
        if (searchFavouritesMatterGenAcc.getCreatedBy() == null || searchFavouritesMatterGenAcc.getCreatedBy().isEmpty()) {
            searchFavouritesMatterGenAcc.setCreatedBy(null);
        }
        if (searchFavouritesMatterGenAcc.getClientId() == null || searchFavouritesMatterGenAcc.getClientId().isEmpty()) {
            searchFavouritesMatterGenAcc.setClientId(null);
        }
        if (searchFavouritesMatterGenAcc.getClassId() == null || searchFavouritesMatterGenAcc.getClassId().isEmpty()) {
            searchFavouritesMatterGenAcc.setClassId(null);
        }
        if (searchFavouritesMatterGenAcc.getStatusId() == null || searchFavouritesMatterGenAcc.getStatusId().isEmpty()) {
            searchFavouritesMatterGenAcc.setStatusId(null);
        }
        if (searchFavouritesMatterGenAcc.getFavourites() == null) {
            searchFavouritesMatterGenAcc.setFavourites(false);
        }
        if (searchFavouritesMatterGenAcc.getNumber() == null || searchFavouritesMatterGenAcc.getNumber() <= 0) {
            throw new BadRequestException("Number should be greater than 0");
        }
        log.info("favouritesMatterGenAcc Search Input: " + searchFavouritesMatterGenAcc);
        List<FavouritesMatterGenImpl> searchResults = null;
        if(searchFavouritesMatterGenAcc.getFavourites()) {
            searchResults = favouritesMatterGenAccRepository.getFavouriteList(
                    searchFavouritesMatterGenAcc.getMatterNumber(),
                    searchFavouritesMatterGenAcc.getClientId(),
                    searchFavouritesMatterGenAcc.getClassId(),
                    searchFavouritesMatterGenAcc.getNumber(),
                    searchFavouritesMatterGenAcc.getStatusId(),
                    searchFavouritesMatterGenAcc.getFavourites(),
                    searchFavouritesMatterGenAcc.getCreatedBy(),
                    searchFavouritesMatterGenAcc.getFromDate(),
                    searchFavouritesMatterGenAcc.getToDate());
        }
        if(!searchFavouritesMatterGenAcc.getFavourites()) {
            searchResults = favouritesMatterGenAccRepository.getRecentList(
                    searchFavouritesMatterGenAcc.getMatterNumber(),
                    searchFavouritesMatterGenAcc.getClientId(),
                    searchFavouritesMatterGenAcc.getClassId(),
                    searchFavouritesMatterGenAcc.getNumber(),
                    searchFavouritesMatterGenAcc.getStatusId(),
                    searchFavouritesMatterGenAcc.getCreatedBy(),
                    searchFavouritesMatterGenAcc.getFromDate(),
                    searchFavouritesMatterGenAcc.getToDate());
        }

        log.info("results: " + searchResults.size());
        return searchResults;
    }

    /**
     * delete
     *
     * @param favouritesMatterGenAcc
     */
    public void deleteFavouritesMatterGenAcc(FavouritesMatterGenAcc favouritesMatterGenAcc) {
        List<FavouritesMatterGenAcc> dbFavouritesMatterGenAccList = favouritesMatterGenAccRepository.findAllByLanguageIdAndClassIdAndClientIdAndMatterNumberAndCreatedByAndDeletionIndicator(
                favouritesMatterGenAcc.getLanguageId(),
                favouritesMatterGenAcc.getClassId(),
                favouritesMatterGenAcc.getClientId(),
                favouritesMatterGenAcc.getMatterNumber(),
                favouritesMatterGenAcc.getCreatedBy(),
                0L);
        log.info("favouritesMatterGenAcc to delete: " + dbFavouritesMatterGenAccList);
        if (dbFavouritesMatterGenAccList != null) {
            for (FavouritesMatterGenAcc dbFavouritesMatterGenAcc : dbFavouritesMatterGenAccList) {
                dbFavouritesMatterGenAcc.setDeletionIndicator(1L);
                dbFavouritesMatterGenAcc.setUpdatedOn(new Date());
                dbFavouritesMatterGenAcc.setUpdatedBy(favouritesMatterGenAcc.getCreatedBy());
                favouritesMatterGenAccRepository.save(dbFavouritesMatterGenAcc);
            }
        } else {
            throw new EntityNotFoundException("Error in deleting Id: " + favouritesMatterGenAcc.getMatterNumber());
        }
    }
}