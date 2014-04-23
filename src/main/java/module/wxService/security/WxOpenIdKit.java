package module.wxService.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sy.module.core.config.PropertiesLoader;
import sy.module.core.util.encry.AESUtils;
import sy.module.core.util.encry.Base64Util;

public class WxOpenIdKit {
	private static final Log log = LogFactory.getLog(WxOpenIdKit.class);
	private static final String dateFormat = "yyyyMMddHHmmssSSS";
	
	/**
	 * 加载私钥
	 * @return 私钥字符串
	 */
	private static String loadPrivateKey() {
		return PropertiesLoader.getInstance().getConfig(
				"module.wxService.security.privateKey", "default_private_key");
	}
	
	/**
	 * 验证openid签名
	 * @param openid
	 * @param sign
	 * @return 验证状态
	 */
	public static boolean checkOpenIdSign(String openid, String sign) {
		try {
			String checkModel = PropertiesLoader.getInstance().getConfig("module.wxService.security.checkModel", "normal");
			// 模式：不检查
			if (checkModel.equals("none")) {
				// 简单返回true
				return true;
			}
			String openid_base64 = AESUtils.decryptStr(sign, loadPrivateKey());
			String timeAndOpenid = Base64Util.decodeAsString(openid_base64);
			String timeStr = timeAndOpenid.substring(0, dateFormat.length());
			String openidStr = timeAndOpenid.substring(dateFormat.length(), timeAndOpenid.length());
			// 模式：常规
			if (checkModel.equals("normal")) {
				return openid.equals(openidStr);
			}
			// 模式：严格
			Calendar now = Calendar.getInstance();
			Calendar singCatalina = Calendar.getInstance();
			singCatalina.setTime(new SimpleDateFormat(dateFormat).parse(timeStr));
			singCatalina.add(
					PropertiesLoader.getInstance().getConfig("module.wxService.security.strict.time.field", 12), 		// Calendar.MINUTE
					PropertiesLoader.getInstance().getConfig("module.wxService.security.strict.time.amount", 30));		// 
			if (now.before(singCatalina)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			log.warn("check open id sign faild.", e);
			return false;
		}
	}
	
	/**
	 * 获得openid签名
	 * @param openid
	 * @return 签名字符串
	 */
	public static String getOpenidSign(String openid) {
		// 
		String time = new SimpleDateFormat(dateFormat).format(new Date());
		// base 64
		String openid_base64 = Base64Util.encodeForString(time + openid);
		// aes
		String openid_aes = AESUtils.encryptStr(openid_base64, loadPrivateKey());
		// md5
		return openid_aes;
	}
	

}
