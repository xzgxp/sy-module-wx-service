package module.wxService.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import module.wxService.service.WxApiSupport;
import sy.module.core.config.PropertiesLoader;
import sy.module.core.mvc.annotation.ModuleAction;
import sy.module.core.mvc.annotation.ModuleController;

@ModuleController(namespace="module/wxService")
public class WxAccept {
	private static final Log log = LogFactory.getLog(WxAccept.class);
	
	private WxApiSupport wxApiSupport;

	@ModuleAction(url="notice")
	public void notice(HttpServletRequest request, HttpServletResponse response) {
		if (wxApiSupport == null) {
			String token = PropertiesLoader.getInstance().getConfig("module.wxService.token");
			String configPath = PropertiesLoader.getInstance().getConfig("module.wxService.configPath");
			String appid = PropertiesLoader.getInstance().getConfig("module.wxService.appid");
			String secret = PropertiesLoader.getInstance().getConfig("module.wxService.secret");
			String wxMenuJsonPath = PropertiesLoader.getInstance().getConfig("module.wxService.wxMenuJsonPath");
			if (token == null || token.equals("") || configPath == null || configPath.equals("")) {
				log.warn("配置读取失败，请在配置文件中检查配置项 [token, configPath]");
				return;
			}
			if (appid != null && !appid.equals("") 
					&& secret != null && !secret.equals("")
					&& wxMenuJsonPath != null && !wxMenuJsonPath.equals("")) {
				log.info("使用高级支持初始化。token=" + token + ", configPath=" + configPath 
						+ ", appid=" + appid + ", secret=" + secret + ", wxMenuJsonPath=" + wxMenuJsonPath);
				wxApiSupport = new WxApiSupport(token, appid, secret, configPath, wxMenuJsonPath);
			} else {
				log.info("使用基础支持初始化。token=" + token + ", configPath=" + configPath);
				wxApiSupport = new WxApiSupport(token, configPath);
			}
		}
		try {
			wxApiSupport.notice(request, response);
		} catch (Exception e) {
			log.warn("微信消息受理异常。", e);
		}
	}
	
}
