package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.FixedAreaService;
import com.itheima.bos.web.common.BaseAction;
import com.itheima.crm.domain.Customer;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

	private static final long serialVersionUID = -296656013674551936L;

	@Autowired
	private FixedAreaService fixedAreaServiceImpl;

	@Action(value = "fixedAreaAction_save", results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
	public String save() {
		fixedAreaServiceImpl.save(getModel());

		return SUCCESS;
	}

	@Action(value = "fixedAreaAction_pageQuery")
	public String pageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<FixedArea> pageQuery = fixedAreaServiceImpl.pageQuery(pageable);

		try {
			page2Json(pageQuery, new String[] { "couriers", "subareas" });
		} catch (IOException e) {
			e.printStackTrace();
		}

		return NONE;
	}

	@Action(value = "fixedAreaAction_findUnAssociatedCustomers")
	public String findUnAssociatedCustomers() {
		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:8081/crm/webservice/cs/findCustomersUnAssociated")
				.accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		try {
			list2Json(list, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	@Action(value = "fixedAreaAction_findCustomersAssociated2FixedArea")
	public String findCustomersAssociated2FixedArea() {
		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:8081/crm/webservice/cs/findCustomersAssociated2FixedArea")
				.query("fixedAreaID", getModel().getId()).accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		try {
			list2Json(list, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	private List<Long> customerIds;

	public void setCustomerIds(List<Long> customerIds) {
		this.customerIds = customerIds;
	}

	// 关联客户到指定的定区
	@Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
	public String assignCustomers2FixedArea() throws IOException {

		WebClient.create("http://localhost:8180/crm/webservice/cs/assignCustomers2FixedArea")
				.query("fixedAreaId", getModel().getId()).query("customerIds", customerIds)
				.accept(MediaType.APPLICATION_JSON).put(null);

		return SUCCESS;
	}

}
