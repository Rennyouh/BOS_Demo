package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.web.common.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<SubArea> {

	private static final long serialVersionUID = 2677365565097211778L;

	@Autowired
	private SubareaService subareaServiceImpl;

	@Action(value = "subareaAction_save", results = {
			@Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect") })
	public String save() {
		subareaServiceImpl.save(this.getModel());
		return SUCCESS;
	}
	
	@Action(value = "subareaAction_pageQuery")
	public String pageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows);
        Page<SubArea> page = subareaServiceImpl.pageQuery(pageable);

        // 总数据条数
        long totalElements = page.getTotalElements();
        // 当前页面要显示的数据
        List<SubArea> content = page.getContent();

        Map<String, Object> map = new HashMap<>();
        map.put("total", totalElements);
        map.put("rows", content);
        // 把map集合转换成json字符串

        // 指定要忽略的字段
        JsonConfig config = new JsonConfig();

        config.setExcludes(new String[] {"fixedArea"});
        config.setJsonPropertyFilter(new PropertyFilter() {

            @Override
            public boolean apply(Object source, String name, Object value) {
                // 防止循环调用导致的死循环
                if (source instanceof Area && "subareas".equals(name)) {
                    return true;
                }
                return false;
            }
        });

        // JSONObject : 把一个对象或者map集合转换成json字符串
        // JSONArray : 把数组或者list集合转换成json字符串
        String json = JSONObject.fromObject(map, config).toString();

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
