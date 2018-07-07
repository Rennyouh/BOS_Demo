package com.itheima.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

public interface CourierService {

	void save(Courier model);

	Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

	void batchDel(String ids);

	void batchRes(String ids);

}
