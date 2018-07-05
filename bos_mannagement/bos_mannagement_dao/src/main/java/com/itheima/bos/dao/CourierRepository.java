package com.itheima.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {

}
