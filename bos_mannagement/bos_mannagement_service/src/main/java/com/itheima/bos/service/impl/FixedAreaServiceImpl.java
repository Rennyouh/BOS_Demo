package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.FixedAreaRepository;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Override
	public void save(FixedArea fixedArea) {

		fixedAreaRepository.save(fixedArea);
		
	}

	@Override
	public Page<FixedArea> pageQuery(Pageable pageable) {
		Page<FixedArea> pages = fixedAreaRepository.findAll(pageable);
		return pages;
	}

}
