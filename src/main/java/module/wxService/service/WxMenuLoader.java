package module.wxService.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import module.wxService.service.apisupport.AccessTokenService;
import module.wxService.service.apisupport.MenuService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sy.module.core.mvc.ModuleCoreFilter;
import sy.module.core.util.string.StringTemplateKit;

/**
 * 自定义菜单加载器
 * 
 * @author 石莹 @ caituo
 *
 */
public class WxMenuLoader {
	private static final Log log = LogFactory.getLog(WxMenuLoader.class);

	private String jsonFilePath = null;
	
	public WxMenuLoader(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}
	
	/**
	 * 提交微信菜单
	 * @param appid
	 * @param secret
	 */
	public void submitWxMenu(String appid, String secret, Map<String, Object> context) throws Exception {
		String accessToken = AccessTokenService.requestWxAccessToken(appid, secret);
		MenuService.submit(accessToken, loadMenuContent(context));
	}
	
	/**
	 * 加载菜单内容
	 * @return json
	 */
	public String loadMenuContent(Map<String, Object> context) {
		BufferedReader br = null;
		try {
			URL url = WxMenuLoader.class.getClassLoader().getResource(jsonFilePath);
			File jsonfile = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(jsonfile), "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			return new StringTemplateKit().mergerString(buf.toString(), context);
		} catch (Exception e) {
			log.warn("load menu content error.", e);
			return null;
		} finally {
			try {
				br.close();
			} catch (Exception e) {}
		}
	}
	
}
