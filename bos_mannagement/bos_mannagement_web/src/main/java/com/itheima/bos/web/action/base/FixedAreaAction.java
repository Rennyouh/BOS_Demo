package com.itheima.bos.web.action.base;

import java.io.IOException;

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

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

	private static final long serialVersionUID = -296656013674551936L;

	@Autowired
	private FixedAreaService fixedAreaServiceImpl;
	
	@Action(value="fixedAreaAction_save", results= {@Result(name="success", location="/pages/base/fixed_area.html", type="redirect")})
	public String save() {
		fixedAreaServiceImpl.save(getModel());
		
		return SUCCESS;
	}
	
	@Action(value="fixedAreaAction_pageQuery")
	public String pageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<FixedArea> pageQuery = fixedAreaServiceImpl.pageQuery(pageable);
		
		try {
			page2Json(pageQuery, new String[] {"couriers", "subareas"});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return NONE;
	}
}
