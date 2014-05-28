package module.wxService.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sy.module.core.mvc.annotation.ModuleAction;
import sy.module.core.mvc.annotation.ModuleController;

@ModuleController(namespace="module/wxService/controlCenter")
public class ControlCenterIndexController {

	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return view
	 */
	@ModuleAction(url="index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "freemarker:/module/wxService/view/index";
	}
	
	
}
