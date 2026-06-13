package com.mnrclara.api.management.repository;

import com.mnrclara.api.management.model.mattergeneral.DocketWiseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface DocketWiseIdRepository extends JpaRepository<DocketWiseId, String> {

    @Query(value = "select TEXT as description from tbldocketwiseid where DOC_WISE_ID = :docketWiseId",nativeQuery = true )
    public String getDescription(@Param(value = "docketWiseId") String docketWiseId);
}
