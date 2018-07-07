package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

	private static final long serialVersionUID = -7007191727249932790L;

	private Courier model = new Courier();
	@Override
	public Courier getModel() {
		return model;
	}
	
	@Autowired
	private CourierService courierService;

	@Action(value="courierAction_save", results= {@Result(name="success", location="/pages/base/courier.html", type="redirect")})
	public String save() {
		courierService.save(model);
		return SUCCESS;
	}
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@Action(value="courierAction_batchDel", results= {@Result(name="success", location="/pages/base/courier.html", type="redirect")})
	public String batchDel() {
		courierService.batchDel(ids);
		return SUCCESS;
	}
	
	@Action(value="courierAction_batchRes", results= {@Result(name="success", location="/pages/base/courier.html", type="redirect")})
	public String batchRes() {
		courierService.batchRes(ids);
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
		// 动态的构造查询条件
		Specification<Courier> specification = new Specification<Courier>() {

			// 在这个方法中构造查询条件
            // root : 根对象,一般可以直接理解为泛型对象.以本例来说,root可以简单的理解为就是Courier对象
            // cb : 用来构造查询条件的对象
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				ArrayList<Predicate> list = new ArrayList<>();
				
				String courierNum = model.getCourierNum();
				Standard standard = model.getStandard();
				String company = model.getCompany();
				String type = model.getType();
				
				if (StringUtils.isNotEmpty(courierNum)) {
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
					list.add(p1);
				}
				
				if (StringUtils.isNotEmpty(company)) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+ company +"%");
					list.add(p2);
				}
				
				if (StringUtils.isNotEmpty(type)) {
					Predicate p3 = cb.equal(root.get("type").as(String.class), type);
					list.add(p3);
				}
				
				if (standard != null) {
					String name = standard.getName();
					if (StringUtils.isNotEmpty(name)) {
						Join<Object, Object> join = root.join("standard");
						Predicate p4 = cb.equal(join.get("name").as(String.class), name);
						list.add(p4);
					}
				}
				
				if (list.size() == 0) {
					return null;
				}
				
				Predicate[] arr = new Predicate[list.size()];
				list.toArray(arr);
				
				return cb.and(arr);
			}
		};
		
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Courier> page = courierService.pageQuery(specification, pageable);
		
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
