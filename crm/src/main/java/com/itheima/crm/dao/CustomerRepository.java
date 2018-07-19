package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByFixedAreaIdIsNull();

	List<Customer> findByFixedAreaId(String fixedAreaId);

	@Modifying
	@Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
	void unbindFixedAreaById(String fixedAreaId);

	@Modifying
	@Query("update Customer set fixedAreaId = ? where id = ?")
	void bindCustomer2FixedArea(String fixedAreaId, Long id);

}
