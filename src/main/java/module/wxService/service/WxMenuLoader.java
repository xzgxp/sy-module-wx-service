package module.wxService.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WxMenuLoader {
	private static final Log log = LogFactory.getLog(WxMenuLoader.class);

	private String jsonFilePath = null;
	
	public WxMenuLoader(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}
	
	/**
	 * 请求访问令牌
	 * @param appid
	 * @param secret
	 * @return token
	 */
	private String requestWxAccessToken(String appid, String secret) {
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
	
	/**
	 * 提交微信菜单
	 * @param appid
	 * @param secret
	 */
	@SuppressWarnings("deprecation")
	public void submitWxMenu(String appid, String secret) throws Exception {
		String accessToken = requestWxAccessToken(appid, secret);
		if (accessToken == null) {
			log.warn("access token not exist.");
			return;
		}
		String jsonBody = loadMenuContent();
		if (jsonBody == null) {
			log.warn("json menu body is null.");
			return;
		}
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken);
		String resBody = null;
		try {
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			postMethod.setRequestBody(jsonBody);
			httpClient.executeMethod(postMethod);
			if (postMethod.getStatusCode() != 200) {
				log.warn("unknown http code : " + postMethod.getStatusCode());
				return;
			}
			resBody = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.warn("submit microMsg menu error.", e);
		} finally {
			try {
				postMethod.releaseConnection();
			} catch (Exception e){}
		}
		//
		if (resBody != null) {
			Matcher matcher = Pattern.compile("\\\"errmsg\\\":\\\"([^\\\"]*)\\\"").matcher(resBody);
			String code = matcher.find() ? matcher.group(1) : null;
			if (code == null) {
				throw new Exception("not found microMsg status code.");
			}
			if (!code.equals("ok")) {
				throw new Exception("unknonw microMsg state code : " + code);
			}
			log.info("submit microMsg menu success.");
		}
	}
	
	/**
	 * 加载菜单内容
	 * @return json
	 */
	public String loadMenuContent() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					WxMenuLoader.class.getClassLoader().getResourceAsStream(jsonFilePath), "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			return buf.toString();
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
