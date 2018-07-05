package com.itheima.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

public interface StandardService {

	void save(Standard model);

	Page<Standard> pageQuery(Pageable pageable);

	List<Standard> findAll();

}
