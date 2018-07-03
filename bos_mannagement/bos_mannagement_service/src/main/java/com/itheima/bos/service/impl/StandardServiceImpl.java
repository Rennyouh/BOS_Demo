package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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


}
