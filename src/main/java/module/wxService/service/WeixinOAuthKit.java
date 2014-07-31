package module.wxService.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import sy.module.core.config.PropertiesLoader;
import sy.module.core.mvc.ModuleCoreFilter;
import sy.module.core.mvc.annotation.ModuleAction;
import sy.module.core.mvc.annotation.ModuleController;
import sy.module.core.util.encry.Base64Util;

@ModuleController(namespace="module/wxService")
public class WeixinOAuthKit {
	private static final Log log = LogFactory.getLog(WeixinOAuthKit.class);
	
	/**
	 * 获取临时访问令牌
	 * @param code
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAccessToken(String code) {
		//
		try {
			String appid = PropertiesLoader.getInstance().getConfig("module.wxService.appid");
			String secret = PropertiesLoader.getInstance().getConfig("module.wxService.secret");
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+
					"&secret="+secret+
					"&code="+code+
					"&grant_type=authorization_code";
			//
			HttpClient http = new HttpClient();
			GetMethod get = new GetMethod(url);
			http.executeMethod(get);
			String res = new String(get.getResponseBody(), "UTF-8");
			// 分析
			Map<String, Object> token = new ObjectMapper().readValue(res, Map.class);
			if (token.get("access_token") != null) {
				return token;
			}
		} catch (Exception e) {
			log.warn("获取临时令牌失败。", e);
		}
		return null;
	}
	
	
	/**
	 * 通过Code，拉取Openid
	 * @param code
	 * @return openid
	 */
	public static String pullOpenId(String code) {
		return (String) getAccessToken(code).get("openid");
	}
	
	
	/**
	 * 拉取用户信息
	 * @param token
	 * @return Map 
	 */
	public static Map<String, Object> pullUserInfo(Map<String, Object> token) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token.get("access_token")+
				"&openid="+token.get("openid")+
				"&lang=zh_CN";
		//
		HttpClient http = new HttpClient();
		GetMethod get = new GetMethod(url);
		try {
			http.executeMethod(get);
			String res = new String(get.getResponseBody(), "UTF-8");
			log.debug("pull user info， body : ["+res+"]");
			// 解析
			@SuppressWarnings("unchecked")
			Map<String, Object> info = new ObjectMapper().readValue(res, Map.class);
			if (info.get("openid") != null) {
				return info;
			}
		} catch (Exception e) {
			log.warn("拉取用户信息失败。", e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) http.getHttpConnectionManager()).shutdown();
		}
		return null;
	}
	
	
	/**
	 * 微信鉴权请求
	 * @param request
	 * @param response
	 * @return 跳转地址
	 */
	@ModuleAction(url="weixinAdvancedOAuth")
	public static String weixinAdvancedOAuth(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		if (code == null || code.equals("")) {
			log.warn("微信鉴权认证跳转成功，但是返回code为空。");
			return null;
		}
		if (state == null || state.equals("")) {
			log.warn("微信鉴权认证跳转成功，但是返回state为空。");
			return null;
		}
		String relativelyUrl = Base64Util.decodeAsString(state);
		// 获取access_token
		Map<String, Object> accessToken = getAccessToken(code);
		if (accessToken != null) {
			Map<String, Object> infos = pullUserInfo(accessToken);
			if (infos != null) {
				for (String key : infos.keySet()) {
					request.getSession().setAttribute("module-wx-service_oauth_"+key, infos.get(key));
				}
			}
		}
		return "redirect:" + ModuleCoreFilter.getRequestContext().basePath + relativelyUrl;
	}
	
	/**
	 * 获得基础鉴权URL地址
	 * @param relativelyUrl
	 * @param state
	 * @return URL地址
	 * @throws UnsupportedEncodingException
	 */
	public static String loadWeixinBaseOAuthUrl(
			String relativelyUrl, String state
			) throws UnsupportedEncodingException {
		return loadWeixinOAuthUrl(relativelyUrl, state, "snsapi_base");
	}
	
	/**
	 * 获得鉴权URL地址
	 * @param relativelyUrl
	 * @param state
	 * @param scope snsapi_base 或 snsapi_userinfo
	 * @return URL地址
	 * @throws UnsupportedEncodingException 
	 */
	public static String loadWeixinOAuthUrl(
			String relativelyUrl, String state, String scope
			) throws UnsupportedEncodingException {
		String appid = PropertiesLoader.getInstance().getConfig("module.wxService.appid");
		String secret = PropertiesLoader.getInstance().getConfig("module.wxService.secret");
		//
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="
						+URLEncoder.encode(ModuleCoreFilter.getRequestContext().basePath+relativelyUrl, "UTF-8")
						+"&response_type=code&scope="+scope+"&state="+secret+"#wechat_redirect";
	}
	
	
	/**
	 * 获得鉴权URL地址
	 * @param relativelyUrl
	 * @return URL地址
	 */
	public static String loadWeixinAdvancedOAuthUrl(String relativelyUrl) {
		try {
			String appid = PropertiesLoader.getInstance().getConfig("module.wxService.appid");
			String redirect_uri = ModuleCoreFilter.getRequestContext().basePath + "module/wxService/weixinAdvancedOAuth.do";
			String response_type = "code";
			String scope = "snsapi_userinfo";
			String state = Base64Util.encodeForString(relativelyUrl);
			return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+
					"&redirect_uri="+redirect_uri+
					"&response_type="+response_type+
					"&scope="+scope+
					"&state="+state+
					"#wechat_redirect";
			
		} catch (Exception e) {
			log.warn("exception.", e);
			return null;
		}
	}

}
