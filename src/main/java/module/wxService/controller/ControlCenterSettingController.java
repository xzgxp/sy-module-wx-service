package module.wxService.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sy.module.core.dao.Dao;
import sy.module.core.mvc.annotation.ModuleAction;
import sy.module.core.mvc.annotation.ModuleActionParmar;
import sy.module.core.mvc.annotation.ModuleController;

@ModuleController(namespace="module/wxService/controlCenter")
public class ControlCenterSettingController {

	/**
	 * 执行设置
	 * @param accumulative_amendments
	 * @return 状态
	 * @throws SQLException
	 */
	@ModuleAction(url="dosetting")
	public String dosetting(
			@ModuleActionParmar(name = "accumulative_amendments") Integer accumulative_amendments
			) throws SQLException {
		if (accumulative_amendments != null) {
			new Dao().update("delete from module_wxservice_setting where setting_key = ?; "
					+ "insert into module_wxservice_setting (setting_key, setting_val) values (?,?)", 
					"accumulative_amendments", "accumulative_amendments", accumulative_amendments);
		}
		return "success";
	}

	/**
	 * 加载设置
	 * @param request
	 * @param response
	 * @return view
	 * @throws SQLException 
	 */
	@ModuleAction(url="setting")
	public String setting(
			HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// 提交
		int accumulative_amendments = 0;
		try {
			accumulative_amendments = Integer.parseInt(new Dao().queryForString(
					"select setting_val from module_wxservice_setting where setting_key = ?", "accumulative_amendments"));
		} catch (Exception e){}
		request.setAttribute("accumulative_amendments", accumulative_amendments);
		return "freemarker:/module/wxService/view/setting"; 
	}

}
