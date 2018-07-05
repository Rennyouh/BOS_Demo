package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardRepository standardRepository;
	
	@Override
	public void save(Standard model) {
		standardRepository.save(model);
	}

	@Override
	public Page<Standard> pageQuery(Pageable pageable) {
		Page<Standard> page = standardRepository.findAll(pageable);
		return page;
	}

	@Override
	public List<Standard> findAll() {
		List<Standard> list = standardRepository.findAll();
		return list;
	}


}
