package module.wxService.service.apisupport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MenuService {
	private static final Log log = LogFactory.getLog(MenuService.class);
	
	/**
	 * 提交自定义菜单
	 * @param accessToken
	 * @param jsonBody
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static boolean submit(String accessToken, String jsonBody) throws Exception {
		if (accessToken == null) {
			log.warn("access token not exist.");
			return false;
		}
		if (jsonBody == null) {
			log.warn("json menu body is null.");
			return false;
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
				return false;
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
			return true;
		}
		return false;
	}

}
