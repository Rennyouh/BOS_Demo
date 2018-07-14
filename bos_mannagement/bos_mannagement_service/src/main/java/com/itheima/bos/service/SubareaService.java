package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.SubArea;

public interface SubareaService {

	void save(SubArea subArea);

	Page<SubArea> pageQuery(Pageable pageable);

}
