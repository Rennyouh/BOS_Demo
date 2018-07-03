package com.itheima.bos.web.action.base;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

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

	@Action(value="standardAction_save", results= {@Result(name="SUCCESS", location="/pages/base/standard.html", type="redirect")})
	public String save() {
		standardService.save(model);
		return SUCCESS;
	}
}
