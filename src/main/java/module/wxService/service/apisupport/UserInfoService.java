package module.wxService.service.apisupport;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import sy.module.core.config.PropertiesLoader;

/**
 * 用户信息服务
 * 
 * @author 石莹 @ caituo
 *
 */
public class UserInfoService {
	private static final Log log = LogFactory.getLog(UserInfoService.class);
	

	/**
	 * 用户是否已经关注
	 * @param openid
	 * @return 关注状态
	 */
	public static boolean isSubscribe(String openid) {
		Map<String, Object> obj = pullUserInfo(openid);
		return obj != null 
				&& obj.get("subscribe") != null 
				&& obj.get("subscribe").toString().equals("1");
	}

	/**
	 * 拉取用户信息
	 * @param openid
	 * @return 用户信息
	 */
	public static Map<String, Object> pullUserInfo(String openid) {
		String appid = PropertiesLoader.getInstance().getConfig("module.wxService.appid");
		String secret = PropertiesLoader.getInstance().getConfig("module.wxService.secret");
		//
		String token = "";
		try {
			token = AccessTokenService.requestWxAccessToken(appid, secret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ token + "&openid=" + openid + "&lang=zh_CN";
		//
		HttpClient http = new HttpClient();
		GetMethod get = new GetMethod(url);
		try {
			http.executeMethod(get);
			String res = new String(get.getResponseBody(), "UTF-8");
			log.debug("pull user info， body : ["+res+"]");
			// 分析
			@SuppressWarnings("unchecked")
			Map<String, Object> info = new ObjectMapper().readValue(res, Map.class);
			if (info != null 
					&& info.get("openid") != null) {
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return null;
	}

}
