package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaService;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {

	private static final long serialVersionUID = -4887817007319630888L;

	private Area model = new Area();
	@Override
	public Area getModel() {
		return model;
	}
	
	@Autowired
	private AreaService areaService;
	
	private File file;
	public void setFile(File file) {
		this.file = file;
	}

	@Action(value="areaAction_importXSL", results= {@Result(name="success", location="/pages/base/area.html", type="redirect")})
	public String importXSL() {
		try {

            // 用于存放Area的集合
            // 如果一条一条数据的插入数据库性能太低,所以使用一个集合,一次性进行插入
            List<Area> list = new ArrayList<>();

            // 加载文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));

            // 读取第一个工作簿的内容
            HSSFSheet sheet = workbook.getSheetAt(0);
            // 遍历所有的行
            for (Row row : sheet) {
                // 跳过第一行
                if (row.getRowNum() == 0) {
                    continue;
                }

                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                // 截掉省市区的最后一个字符
                province = province.substring(0, province.length() - 1);
                city = city.substring(0, city.length() - 1);
                district = district.substring(0, district.length() - 1);
                // 生成城市编码
                String citycode =
                        PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
                // 生成简码
                String[] headByString = PinYin4jUtils
                        .getHeadByString(province + city + district);
                String shortcode =
                        PinYin4jUtils.stringArrayToString(headByString);

                Area area = new Area();
                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setCitycode(citycode);
                area.setShortcode(shortcode);

                list.add(area);

            }

            areaService.save(list);
            // 释放资源
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
		
		return SUCCESS;
	}

}
