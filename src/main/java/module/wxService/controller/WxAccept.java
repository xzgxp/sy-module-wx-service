package module.wxService.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sy.module.core.config.PropertiesLoader;
import sy.module.core.mvc.annotation.ModuleAction;
import sy.module.core.mvc.annotation.ModuleController;
import module.wxService.accept.SimpleWxMessageNotice;
import module.wxService.service.WxMessageVerification;

/**
 * 微信通知入口
 * 
 * @author 石莹 @ caituo
 *
 */
@ModuleController(namespace="module/wxService")
public class WxAccept {
	private static final Log log = LogFactory.getLog(WxAccept.class);
	
	@Resource
	private SimpleWxMessageNotice simpleWxMessageNotice;
	
	/**
	 * 微信接入入口
	 * 
	 * 完成行为判断，来源有效性判断
	 * 
	 * @param rquest
	 * @param response
	 * @return
	 */
	@ModuleAction(url="accept")
	public String accept(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 判断请求行为
			String echostr = request.getParameter("echostr");
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			// 读取token
			String token = PropertiesLoader.getInstance().getConfig("module.wxservice.token");
			if (token == null || token.equals("")) {
				log.warn("微信token读取失败，请在configs/config.properties中配置module.wxservice.token属性，并与微信公众平台端设置一致");
				return "";
			}
			
			if (null != echostr) {
				// API 接入验证
				log.debug("开始进行微信API接入验证，signature="+signature + ", timestamp=" + timestamp + ", nonce=" + nonce);
				if(WxMessageVerification.access(token, signature, timestamp, nonce)) {
					log.info("微信API接入验证成功。");
					return echostr;
			    } else {
					log.warn("微信API接入验证失败。");
			    	return "";
			    }
			} else {
				// 消息合法性验证
				if(!WxMessageVerification.access(token, signature, timestamp, nonce)) {
					log.warn("微信消息来源不合法。");
					return "";
				}
				// xml流转储
				ByteArrayOutputStream xmlstream = new ByteArrayOutputStream();
				InputStream is = request.getInputStream();
				int b = -1;
				while ((b = is.read()) != -1) {
					xmlstream.write(b);
				}
				log.debug(">>>>>>>>>> receive weixin message >>>>>>>>>> \n" + new String(xmlstream.toByteArray(), "UTF-8"));
				// 受理消息
				byte[] results = simpleWxMessageNotice.accept(
						new ByteArrayInputStream(xmlstream.toByteArray()));
				if (results == null) {
					log.warn("受理服务返回结果为空。");
					return "";
				}
				// 返回消息
				log.debug("<<<<<<<<<< send weixin message <<<<<<<<<< \n" + new String(results, "UTF-8"));
				return new String(results, "UTF-8");
			}
			
		} catch (Exception e) {
			log.warn("微信消息受理异常。", e);
			return "";
		}
	}
	
}
