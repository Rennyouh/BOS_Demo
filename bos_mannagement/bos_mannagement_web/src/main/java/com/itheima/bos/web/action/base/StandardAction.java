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

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	private static final long serialVersionUID = 2817217081505713715L;
	
	private Standard model = new Standard();
	@Override
	public Standard getModel() {
		return model;
	}
	
	@Autowired
	private StandardService standardService;

	@Action(value="standardAction_save", results= {@Result(name="success", location="/pages/base/standard.html", type="redirect")})
	public String save() {
		standardService.save(model);
		return SUCCESS;
	}
	
//	属性驱动
	private int page;
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value="standardAction_pageQuery")
	public String pageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Standard> page = standardService.pageQuery(pageable);
		
		long total = page.getTotalElements();
		List<Standard> rows = page.getContent();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", rows);
		
		String json = JSONObject.fromObject(map).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return NONE;
		
	}
	
	@Action(value="standardAction_findAll")
	public String findAll() {
		List<Standard> standards = standardService.findAll();
		
		String json = JSONArray.fromObject(standards).toString();
		
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
