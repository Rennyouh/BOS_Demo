package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.crm.domain.Customer;

public interface CustomerService {

	@GET
	@Path("/customer")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Customer> findAll();

	// 未关联到定区的客户
	@GET
	@Path("/findCustomersUnAssociated")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Customer> findCustomersUnAssociated();

	// 已经关联到当前定区的客户
	@GET
	@Path("/findCustomersAssociated2FixedArea")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId);

	// 关联或取消关联客户
	@GET
	@Path("/findCustomersAssociated2FixedArea")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void assignCustomers2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId,
			@QueryParam("customerIds") List<Long> customerIds);

}
