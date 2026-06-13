package com.mnrclara.api.management.repository;

import com.mnrclara.api.management.model.mattergeneral.FavouritesMatterGenAcc;
import com.mnrclara.api.management.model.mattergeneral.FavouritesMatterGenImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface FavouritesMatterGenAccRepository extends JpaRepository<FavouritesMatterGenAcc, Long>,
        JpaSpecificationExecutor<FavouritesMatterGenAcc> {


    //---------------find matter favourite------------------------------------------------------------------------------------
    @Query(value = "select \n"
            + "mg.class_id as classId,\n"
            + "mg.class_text as classDescription,\n"
            + "mg.Matter_no as matterNumber,\n"
            + "mg.Matter_text as matterDescription,\n"
            + "mg.client_id as clientId,\n"
            + "mg.client_name as clientName,\n"
            + "mg.lang_id as languageId,\n"
            + "mg.status_id as statusId,\n"
            + "mg.status_text as statusDesc,\n"
            + "DATE_FORMAT(mg.view_date, '%m-%d-%Y %T.%f') as viewedDate,\n"
            + "mg.ctd_by as createdBy\n"
            + "from tblfavouritesmattergenaccid mg \n"
            + "WHERE \n"
            + "mg.fav = :favourites and \n"
            + "(COALESCE(:matterNumber) IS NULL OR (mg.matter_no IN (:matterNumber))) and \n"
            + "(COALESCE(:classId) IS NULL OR (mg.class_id IN (:classId))) and \n"
            + "(COALESCE(:clientId) IS NULL OR (mg.client_id IN (:clientId))) and \n"
            + "(COALESCE(:statusId) IS NULL OR (mg.status_id IN (:statusId))) and \n"
            + "(COALESCE(:createdBy) IS NULL OR (mg.ctd_by IN (:createdBy))) and \n"
            + "(COALESCE(:fromDate) IS NULL OR (mg.view_date BETWEEN :fromDate AND :toDate)) and \n"
            + "mg.is_deleted=0 order by view_date desc limit :number", nativeQuery = true)
    public List<FavouritesMatterGenImpl> getFavouriteList(@Param("matterNumber") List<String> matterNumber,
                                                          @Param("clientId") List<String> clientId,
                                                          @Param("classId") List<Long> classId,
                                                          @Param("number") Long number,
                                                          @Param("statusId") List<Long> statusId,
                                                          @Param("favourites") Boolean favourites,
                                                          @Param("createdBy") List<String> createdBy,
                                                          @Param("fromDate") Date fromDate,
                                                          @Param("toDate") Date toDate);

    @Query(value = "select \n"
            + "mg.class_id as classId,\n"
            + "mg.class_text as classDescription,\n"
            + "mg.Matter_no as matterNumber,\n"
            + "mg.Matter_text as matterDescription,\n"
            + "mg.client_id as clientId,\n"
            + "mg.client_name as clientName,\n"
            + "mg.lang_id as languageId,\n"
            + "mg.status_id as statusId,\n"
            + "mg.status_text as statusDesc,\n"
            + "DATE_FORMAT(mg.view_date, '%m-%d-%Y %T.%f') as viewedDate,\n"
            + "mg.ctd_by as createdBy\n"
            + "from tblfavouritesmattergenaccid mg \n"
            + "WHERE \n"
            + "(COALESCE(:matterNumber) IS NULL OR (mg.matter_no IN (:matterNumber))) and \n"
            + "(COALESCE(:classId) IS NULL OR (mg.class_id IN (:classId))) and \n"
            + "(COALESCE(:clientId) IS NULL OR (mg.client_id IN (:clientId))) and \n"
            + "(COALESCE(:statusId) IS NULL OR (mg.status_id IN (:statusId))) and \n"
            + "(COALESCE(:createdBy) IS NULL OR (mg.ctd_by IN (:createdBy))) and \n"
            + "(COALESCE(:fromDate) IS NULL OR (mg.view_date BETWEEN :fromDate AND :toDate)) and \n"
            + "mg.is_deleted=0 order by view_date desc limit :number", nativeQuery = true)
    public List<FavouritesMatterGenImpl> getRecentList(@Param("matterNumber") List<String> matterNumber,
                                                       @Param("clientId") List<String> clientId,
                                                       @Param("classId") List<Long> classId,
                                                       @Param("number") Long number,
                                                       @Param("statusId") List<Long> statusId,
                                                       @Param("createdBy") List<String> createdBy,
                                                       @Param("fromDate") Date fromDate,
                                                       @Param("toDate") Date toDate);

    FavouritesMatterGenAcc findTopByLanguageIdAndClassIdAndClientIdAndMatterNumberAndCreatedByAndDeletionIndicatorOrderByViewedDateDesc(
            String languageId, Long classId, String clientId, String matterNumber, String createdBy, Long deletionIndicator);

    List<FavouritesMatterGenAcc> findAllByLanguageIdAndClassIdAndClientIdAndMatterNumberAndCreatedByAndDeletionIndicator(
            String languageId, Long classId, String clientId, String matterNumber, String createdBy, Long deletionIndicator);
}