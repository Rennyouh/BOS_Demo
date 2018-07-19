package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea fixedArea);

	Page<FixedArea> pageQuery(Pageable pageable);

}
