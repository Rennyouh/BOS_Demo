package com.itheima.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> findAll() {
		List<Customer> customers = customerRepository.findAll();
		return customers;
	}

	@Override
	public List<Customer> findCustomersUnAssociated() {

		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findCustomersAssociated2FixedArea(String fixedAreaId) {

		return customerRepository.findByFixedAreaId(fixedAreaId);
	}

	@Override
	public void assignCustomers2FixedArea(String fixedAreaId, List<Long> customerIds) {
		if (StringUtils.isNotEmpty(fixedAreaId)) {
            // 把关联到指定定区的客户,全部解绑
            customerRepository.unbindFixedAreaById(fixedAreaId);
            // 重新绑定
            for (Long id : customerIds) {
                customerRepository.bindCustomer2FixedArea(fixedAreaId, id);
            }
        }
	}

}
