package com.itheima.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long>, JpaSpecificationExecutor<Courier> {

	@Modifying
	@Query("update Courier set deltag = 1 where id = ?")
	void upDateDelTagById(long id);

	@Modifying
	@Query("update Courier set deltag = 0 where id = ?")
	void ResByID(long id);

}
