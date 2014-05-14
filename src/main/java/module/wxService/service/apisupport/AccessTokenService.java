package module.wxService.service.apisupport;

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

	
	/**
	 * 请求访问令牌
	 * @param appid
	 * @param secret
	 * @return token
	 */
	public static String requestWxAccessToken(String appid, String secret) {
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
		try {
			httpClient.executeMethod(get);
			if (get.getStatusCode() == 200) {
				String body = get.getResponseBodyAsString();
				Matcher matcher = Pattern.compile("\\\"access_token\\\":\\\"([^\\\"]*)\\\"").matcher(body);
				String access_token = matcher.find() ? matcher.group(1) : null;
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
	
	
}
