package module.wxService.service.apisupport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.codehaus.jackson.map.ObjectMapper;

import sy.module.core.util.encry.Base64Util;
import sy.module.core.util.encry.MD5Util;

/**
 * 多媒体上传下载接口支持
 * 
 * @author 石莹 @ caituo
 *
 */
public class MediaTransport {
	
	/**
	 * 文件媒体ID
	 * 
	 * @param appid
	 * @param secret
	 * @param file
	 * @param type
	 * @return 上传后的MediaId
	 * @throws Exception 
	 */
	public static String upload(String appid, String secret, File file, MediaType type) throws Exception {
		//
		String accessToken = AccessTokenService.requestWxAccessToken(appid, secret);
		if (accessToken == null || accessToken.equals("")) {
			throw new Exception("request access token faild.");
		}
		//
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type="+type.toString();
		//
		HttpClient http = new HttpClient();
		PostMethod post = new PostMethod(url);
		try {
			// 设置连接参数
			FilePart fp = new FilePart("media", file);
            Part[] parts = { fp };
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, post.getParams());
            post.setRequestEntity(mre);
            http.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置连接时间
            // fire
            http.executeMethod(post);
			if (post.getStatusCode() == 200) {
				String rs = new String(post.getResponseBody(), "UTF-8");
				@SuppressWarnings("rawtypes")
				Map map = new ObjectMapper().readValue(rs, Map.class);
				if (map.get("errcode") != null) {
					throw new Exception("media upload faild. " + rs);
				} else {
					return (String) map.get("media_id");
				}
			} else {
				throw new Exception("faild http status code : " + post.getStatusCode());
			}
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) http.getHttpConnectionManager()).shutdown();
		}
	}
	
	/**
	 * 下载媒体文件
	 * @param url
	 * @param savePath
	 * @return 下载后的文件
	 * @throws Exception
	 */
	public static File download(
			String url, File savePath
	) throws Exception {
		//
		HttpClient http = new HttpClient();
		GetMethod get = new GetMethod(url);
		try {
			http.executeMethod(get);
			if (get.getStatusCode() == 200) {
				//
				if (!savePath.exists()) {
					savePath.getParentFile().mkdirs();
				}
				if (!savePath.isDirectory()) {
					throw new Exception("savePath must be directory");
				}
				// 读取文件信息
				String filename = null;
				Header info = get.getResponseHeader("Content-disposition");
				if (info != null) {
					Pattern pattern = Pattern.compile("filename=\"(.*?)\"");
					Matcher matcher = pattern.matcher(info.getValue());
					if (matcher.find()) {
						filename = matcher.group(1);
					}
				}
				if (filename == null) {
					// 尝试读取后缀名
					Header typeInfo = get.getResponseHeader("Content-Type");
					String typename = "unknown";
					if (typeInfo != null && typeInfo.getValue().lastIndexOf("/") != -1) {
						typename = typeInfo.getValue().substring(
								typeInfo.getValue().lastIndexOf("/")+1, typeInfo.getValue().length());
					}
					// 创建文件名
					filename = MD5Util.toMD5(Base64Util.encodeForString(url)) + "_"
								+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) 
								+ "." + typename;
				}
				//
				File desc = new File(savePath, filename);
				//
				if (desc.exists()) {
					desc.delete();
					desc.getParentFile().mkdirs();
					desc.createNewFile();
				}
				//
				BufferedInputStream bis = new BufferedInputStream(get.getResponseBodyAsStream());
				FileOutputStream fos = new FileOutputStream(desc);
				try {
					byte[] bytes = new byte[1024 * 100];
					int readSize = -1;
					while ((readSize = (bis.read(bytes))) != -1) {
						fos.write(bytes, 0, readSize);
					}
				} finally {
					fos.flush();
					fos.close();
				}
				return desc;
			} else {
				throw new Exception("http status code error : " + get.getStatusCode());
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) http.getHttpConnectionManager()).shutdown();
		}
	}
	
	/**
	 * 下载媒体文件
	 * @param appid
	 * @param secret
	 * @param mediaId
	 * @param savePath
	 * @return 下载的文件
	 * @throws Exception
	 */
	public static File download(
			String appid, String secret, String mediaId, File savePath
	) throws Exception {
		//
		String accessToken = AccessTokenService.requestWxAccessToken(appid, secret);
		if (accessToken == null || accessToken.equals("")) {
			throw new Exception("request access token faild.");
		}
		//
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+accessToken+"&media_id="+mediaId;
		//
		return download(url, savePath);
	}

}
