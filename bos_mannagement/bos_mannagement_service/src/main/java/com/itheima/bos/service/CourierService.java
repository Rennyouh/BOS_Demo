package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Courier;

public interface CourierService {

	void save(Courier model);

	Page<Courier> pageQuery(Pageable pageable);

}
