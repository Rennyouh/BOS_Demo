package com.itheima.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;

public interface AreaService {

	void save(List<Area> list);

	Page<Area> pageQuery(Pageable pageable);

	List<Area> findAll();

	List<Area> findByQ(String q);

}
