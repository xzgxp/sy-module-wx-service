package module.wxService.msgdump;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sy.module.core.config.PropertiesLoader;
import sy.module.core.dao.Dao;
import sy.module.core.mvc.ModuleCoreFilter;
import module.wxService.event.WxAcceptEventAdapter;
import module.wxService.service.AcceptNode;
import module.wxService.service.apisupport.MediaTransport;
import module.wxService.vo.recv.WxRecvEventMsg;
import module.wxService.vo.recv.WxRecvGeoMsg;
import module.wxService.vo.recv.WxRecvLinkMsg;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.recv.WxRecvPicMsg;
import module.wxService.vo.recv.WxRecvTextMsg;
import module.wxService.vo.recv.WxRecvVideoMsg;
import module.wxService.vo.recv.WxRecvVoiceMsg;
import module.wxService.vo.send.WxSendMsg;
import module.wxService.vo.send.WxSendMusicMsg;
import module.wxService.vo.send.WxSendNewsMsg;
import module.wxService.vo.send.WxSendTextMsg;

/**
 * 微信消息监听
 * 
 * @author 石莹 @ caituo
 *
 */
public class WxMessageMediaDownloadListener extends WxAcceptEventAdapter {
	private static final Log log = LogFactory.getLog(WxMessageMediaDownloadListener.class);
	
	/**
	 * 是否启用
	 */
	private static Boolean enable = null;
	private static String appid = null;
	private static String secret = null;
	private static File savePath = null;
	
	/**
	 * 是否启用
	 * @return 启用状态
	 */
	private boolean isEnable() {
		if (enable == null) {
			// 读取配置
			enable = PropertiesLoader.getInstance().getConfig("module.wxService.msgMediaDownload.enable", false);
			if (enable) {
				appid = PropertiesLoader.getInstance().getConfig("module.wxService.appid");
				secret = PropertiesLoader.getInstance().getConfig("module.wxService.secret");
				savePath = new File(
						ModuleCoreFilter.getRequestContext().servletContext.getRealPath(
							PropertiesLoader.getInstance().getConfig(
									"module.wxService.msgMediaDownload.savepath", 
									"module\\wxService\\wx_media"
							).replace("\\", File.separator).replace("/", File.separator)
						)
				);
			}
		}
		return enable;
	}
	

	@Override
	public void onRevoleRevcObject(WxRecvMsg revcMsg) {
		if (!isEnable()) {
			return;
		}
		try {
			if (!savePath.exists()) {
				savePath.mkdirs();
			}
			if (revcMsg instanceof WxRecvPicMsg) {
				if (appid != null && !appid.equals("") 
						&& secret != null && !secret.equals("")) {
					MediaTransport.download(appid, secret, ((WxRecvPicMsg) revcMsg).getMediaId(), savePath);
				} else {
					MediaTransport.download( ((WxRecvPicMsg) revcMsg).getPicUrl(), savePath);
				}
				
			} else if (revcMsg instanceof WxRecvVoiceMsg) {
				if (appid != null && !appid.equals("") 
						&& secret != null && !secret.equals("")) {
					MediaTransport.download(appid, secret, ((WxRecvVoiceMsg) revcMsg).getMediaId(), savePath);
				} else {
					log.warn("message media download is enable, but appid or secret is not config.");
				}
				
			} else if (revcMsg instanceof WxRecvVideoMsg) {
				if (appid != null && !appid.equals("") 
						&& secret != null && !secret.equals("")) {
					MediaTransport.download(appid, secret, ((WxRecvVideoMsg) revcMsg).getMediaId(), savePath);
					MediaTransport.download(appid, secret, ((WxRecvVideoMsg) revcMsg).getThumbMediaId(), savePath);
				} else {
					log.warn("message media download is enable, but appid or secret is not config.");
				}
				
			}
		} catch (Exception e) {
			log.warn("download media faild.", e);
		}
	}

	

	
	
	

}
