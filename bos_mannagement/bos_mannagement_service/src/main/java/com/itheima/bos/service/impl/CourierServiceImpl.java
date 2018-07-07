package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierRepository courierRepository;
	
	@Override
	public void save(Courier model) {
		courierRepository.save(model);
	}

	@Override
	public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
		Page<Courier> couriers = courierRepository.findAll(specification, pageable);
		return couriers;
	}

	@Override
	public void batchDel(String ids) {
		String[] arr = ids.split(",");
		for (String id : arr) {
			courierRepository.upDateDelTagById(Long.parseLong(id));
		}
	}

	@Override
	public void batchRes(String ids) {
		String[] arr = ids.split(",");
		for (String id : arr) {
			courierRepository.ResByID(Long.parseLong(id));
		}
		
	}

}
