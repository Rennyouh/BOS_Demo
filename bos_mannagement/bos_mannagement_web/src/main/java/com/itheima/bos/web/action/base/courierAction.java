package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class courierAction extends ActionSupport implements ModelDriven<Courier> {

	private static final long serialVersionUID = -7007191727249932790L;

	private Courier model = new Courier();
	@Override
	public Courier getModel() {
		return model;
	}
	
	@Autowired
	private CourierService courierService;

	@Action(value="courierAction_save", results= {@Result(name="SUCCESS", location="/pages/base/courier.html", type="redirect")})
	public String save() {
		courierService.save(model);
		return SUCCESS;
	}
	
	private int page;
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value="courierAction_pageQuery")
	public String pageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Courier> page = courierService.pageQuery(pageable);
		
		long total = page.getTotalElements();
		List<Courier> rows = page.getContent();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", rows);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
		
		String json = JSONObject.fromObject(map, jsonConfig).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return NONE;
	}
	
}
