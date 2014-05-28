package module.wxService.service.apisupport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 访问令牌支持
 * 
 * @author 石莹 @ caituo
 *
 */
public class AccessTokenService {
	private static final Log log = LogFactory.getLog(AccessTokenService.class);
	
	private static final Map<String, TokenCache> cache = new HashMap<String, TokenCache>();
	
	/**
	 * 获取Token缓存
	 * @return cache data
	 */
	public Map<String, TokenCache> getTokenCache() {
		return cache;
	}
	
	/**
	 * 请求访问令牌
	 * @param appid
	 * @param secret
	 * @return token
	 */
	public synchronized static String requestWxAccessToken(String appid, String secret) {
		// 检查cache
		TokenCache tc = cache.get(appid);
		if (tc != null 
				&& tc.getAppid().equals(appid) 
				&& tc.getSecret().equals(secret) 
				&& tc.isExpires()) {
			return tc.access_token;
		}
		log.info("reload access token");
		//
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
		try {
			httpClient.executeMethod(get);
			if (get.getStatusCode() == 200) {
				String body = get.getResponseBodyAsString();
				Matcher matcher = Pattern.compile("\\\"access_token\\\":\\\"([^\\\"]*)\\\"").matcher(body);
				String access_token = matcher.find() ? matcher.group(1) : null;
				// 写入时间
				Matcher expires_matcher = Pattern.compile("\\\"expires_in\\\":\\\"([^\\\"]*)\\\"").matcher(body);
				int expires_in = Integer.parseInt(expires_matcher.find() ? expires_matcher.group(1) : "0");
				// 写入缓存
				TokenCache tcs = new TokenCache();
				tcs.setAppid(appid);
				tcs.setSecret(secret);
				tcs.setCreate_time(new Date());
				tcs.setExpires_in(expires_in);
				tcs.setAccess_token(access_token);
				cache.put(appid, tcs);
				//
				return access_token;
			} else {
				log.warn("error state code : " + get.getStatusCode());
				return null;
			}
		} catch (Exception e) {
			log.warn("request microMsg access token error.", e);
			return null;
		} finally {
			get.releaseConnection();
		}
	}
	
	private static class TokenCache {
		private String appid;
		private String secret;
		private String access_token;
		private Date create_time;
		private int expires_in;
		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getSecret() {
			return secret;
		}
		public void setSecret(String secret) {
			this.secret = secret;
		}
		@SuppressWarnings("unused")
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		@SuppressWarnings("unused")
		public Date getCreate_time() {
			return create_time;
		}
		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}
		@SuppressWarnings("unused")
		public int getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
		public boolean isExpires() {
			return new Date().getTime() >= (create_time.getTime() + (expires_in-10) * 1000);
		}
	}
}
