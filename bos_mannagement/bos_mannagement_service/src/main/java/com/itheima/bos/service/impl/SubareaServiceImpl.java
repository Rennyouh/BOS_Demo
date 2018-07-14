package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.SubareaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.SubareaService;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {

	@Autowired
	private SubareaRepository subareaRepository;
	
	@Override
	public void save(SubArea subArea) {
		subareaRepository.save(subArea);
	}

	@Override
	public Page<SubArea> pageQuery(Pageable pageable) {
		Page<SubArea> page = subareaRepository.findAll(pageable);
		return page;
	}

}
