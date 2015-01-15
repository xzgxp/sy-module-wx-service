package module.wxService.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WxJsSdkKit {
	
	/**
	 * 生成签名
	 * @param noncestr
	 * @param jsapi_ticket
	 * @param timestamp
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String generationSign(
			String noncestr, String jsapi_ticket, String timestamp, String url) throws Exception {
		String string1 = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		// SHA1
		String sha1 = toSha1(string1);
		return sha1;
	}
	
	
	private static String toSha1(String str) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
		messageDigest.update(str.getBytes("UTF-8"));
		String sha1 = getFormattedText(messageDigest.digest());
		return sha1;
	}
	
	
	private static String getFormattedText(byte[] bytes) {
		char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) { 			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

}
