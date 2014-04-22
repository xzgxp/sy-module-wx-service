package module.wxService.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import module.wxService.WeiXin;
import module.wxService.vo.recv.WxRecvEventMsg;
import module.wxService.vo.recv.WxRecvGeoMsg;
import module.wxService.vo.recv.WxRecvLinkMsg;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.recv.WxRecvPicMsg;
import module.wxService.vo.recv.WxRecvTextMsg;
import module.wxService.vo.recv.WxRecvVideoMsg;
import module.wxService.vo.recv.WxRecvVoiceMsg;
import module.wxService.vo.send.WxSendMsg;


/**
 * 消息受理支持
 * 
 * @author 石莹 @ caituo
 *
 */
public class WxApiSupport {
	private static final Log log = LogFactory.getLog(WxApiSupport.class);

	private WxControlConfigLoader wxControlConfigLoader = null;
	private WxMenuLoader wxMenuLoader = null;
	
	private String wx_token = null;
	private String wx_appid = null;
	private String wx_secret = null;
	
	/**
	 * 初始化一个微信接口支持
	 * @param wx_token
	 * @param configPath
	 */
	public WxApiSupport(String wx_token, String configPath) {
		this.wx_token = wx_token;
		wxControlConfigLoader = new WxControlConfigLoader(configPath);
	}
	
	/**
	 * 初始化一个微信接口支持
	 * @param wx_token
	 * @param wx_appid
	 * @param wx_secret
	 * @param configPath
	 * @param wxMenuJsonPath
	 */
	public WxApiSupport(
			String wx_token, String wx_appid, 
			String wx_secret, String configPath, String wxMenuJsonPath) {
		this.wx_token = wx_token;
		wxControlConfigLoader = new WxControlConfigLoader(configPath);
		if (wx_appid != null && wx_secret != null && wxMenuJsonPath != null) {
			wxMenuLoader = new WxMenuLoader(wxMenuJsonPath);
			this.wx_appid = wx_appid;
			this.wx_secret = wx_secret;
		}
	}
	
	
	/**
	 * 微信API通信接口
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void notice(
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		// 判断请求行为
		String echostr = request.getParameter("echostr");
		if (null != echostr) {
			// API 接入验证
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			if(WeiXin.access(wx_token, signature, timestamp, nonce)) {
				log.info("微信API接入验证成功。");
				PrintWriter out = response.getWriter();
				out.write(echostr);
				out.flush();
				out.close();
				// 三秒钟后，自动提交菜单
				if (wxMenuLoader != null) {
					log.info("系统已经配置了自定义菜单，3秒钟后自动提交自定义菜单。");
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(3000);
								wxMenuLoader.submitWxMenu(wx_appid, wx_secret);
								log.warn("微信自定义菜单提交成功。");
							} catch (Exception e) {
								log.warn("微信自定义菜单提交失败。", e);
							}
						}
					}).start();
				}
				return;
		    } else {
				log.warn("微信API接入验证失败。");
		    	return;
		    }
		} else {
			// 流转储
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int b = -1;
			while ((b = request.getInputStream().read()) != -1) {
				baos.write(b);
			}
			// log.debug("recv input stream：\n" + new String(baos.toByteArray(), "UTF-8") + "\n");
			ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
			// 接收消息
			WxRecvMsg msg = WeiXin.recv(in);
			WxSendMsg sendMsg = WeiXin.builderSendByRecv(msg);
			// 受理文件
			AcceptNode accept = null;
			// 微信消息分发
			if(msg instanceof WxRecvEventMsg) {
			    WxRecvEventMsg m = (WxRecvEventMsg) msg;
			    String event = m.getEvent();
			    accept = wxControlConfigLoader.loadEventAcceptByKey(event + "~" + m.getEventKey());
			    
			} else if (msg instanceof WxRecvTextMsg) {
			    accept = wxControlConfigLoader.loadTextAcceptByMatch(((WxRecvTextMsg) msg).getContent());
			    
			} else if (msg instanceof WxRecvPicMsg) {
				accept = wxControlConfigLoader.loadPicAccept();
				
			} else if (msg instanceof WxRecvLinkMsg) {
				accept = wxControlConfigLoader.loadLinkAccept();
				
			} else if (msg instanceof WxRecvGeoMsg) {
				accept = wxControlConfigLoader.loadGeoAccept();
				
			} else if (msg instanceof WxRecvVideoMsg) {
				accept = wxControlConfigLoader.loadVideoAccept();
				
			} else if (msg instanceof WxRecvVoiceMsg) {
				accept = wxControlConfigLoader.loadVoiceAccept();
				
			} else {
				log.warn("未知的消息类型：" + msg);
			}
		    // 交给受理文件
		    try {
		    	if (accept == null) {
		    		log.warn("消息处理器加载异常，请检查配置文件路径，以及消息配置是否正确。");
		    		return;
		    	}
		        if (accept.getReflectInter() == null) {
		        	log.warn("消息处理器加载异常，请检查Bean或Classpath是否存在。");
		        	return;
		        }
		        sendMsg = accept.getReflectInter().accept(msg, sendMsg, request, accept.getSubElement());
		        if (sendMsg != null) {
			        // 发送回微信
			        WeiXin.send(sendMsg, response.getOutputStream());
		        }
		    } catch (Exception e) {
		    	log.warn("消息处理器类["+accept+"]加载异常["+e.getMessage()+"]，忽略消息处理。", e);
		    }
		}
		return;
	}
	
	
	
	
	
	
}








