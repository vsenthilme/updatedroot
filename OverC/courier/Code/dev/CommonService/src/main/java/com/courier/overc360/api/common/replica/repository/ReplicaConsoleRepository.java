package com.courier.overc360.api.common.replica.repository;

import com.courier.overc360.api.common.replica.console.ReplicaConsole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface ReplicaConsoleRepository extends JpaRepository<ReplicaConsole, String>,
        JpaSpecificationExecutor<ReplicaConsole> {


    @Query(value = "select ccr_id \n" +
            "from tblconsole where console_id = :consoleId and is_deleted = 0 ",nativeQuery = true)
    String getCCRNumber(@Param("consoleId") String consoleId);

}