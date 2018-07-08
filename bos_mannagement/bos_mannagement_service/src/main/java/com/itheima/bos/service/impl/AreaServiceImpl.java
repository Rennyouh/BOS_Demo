package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaService;

@Service
@Transactional
public class AreaServiceImpl implements AreaService{

	@Autowired
	private AreaRepository areaRepository;
	
	@Override
	public void save(List<Area> list) {
		areaRepository.save(list);
	}

	@Override
	public Page<Area> pageQuery(Pageable pageable) {
		Page<Area> page = areaRepository.findAll(pageable);
		return page;
	}

	
}
