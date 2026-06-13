package com.courier.overc360.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.courier.overc360.api.model.idmaster.User;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

	public List<User> findAll();


	@Modifying
	@Transactional
	@Query(value = "Update tbllmdinvoiceheader set invoice_status = 1 " +
			"where invoice_no = :invoiceNo and is_deleted = 0", nativeQuery = true)
	void updateInvoice(@Param("invoiceNo") String invoiceNo);

}