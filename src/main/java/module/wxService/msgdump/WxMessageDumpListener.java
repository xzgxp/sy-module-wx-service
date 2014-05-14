package module.wxService.msgdump;

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
import module.wxService.event.WxAcceptEventAdapter;
import module.wxService.service.AcceptNode;
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
public class WxMessageDumpListener extends WxAcceptEventAdapter {
	private static final Log log = LogFactory.getLog(WxMessageDumpListener.class);
	private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	
	/**
	 * 是否启用了微信消息转储
	 */
	private static Boolean enableWxMsgDump = null;
	
	/**
	 * 是否启用了微信消息转储
	 * @return
	 */
	private boolean isEnableWxMsgDump() {
		if (enableWxMsgDump == null) {
			// 读取配置
			enableWxMsgDump = PropertiesLoader.getInstance().getConfig("module.wxService.msgDump.enable", false);
		}
		return enableWxMsgDump;
	}
	
	
	private static StringBuffer sqlBuffer = new StringBuffer();
	private static List<Object> argesBuffer = new ArrayList<Object>();
	private static int sqlsize = 0;
	
	/**
	 * 追加SQL，延迟执行
	 * @param sql
	 * @param arges
	 */
	private synchronized void appendSql(String sql, Object... arges) {
		sqlsize++;
		sqlBuffer.append(sql);
		sqlBuffer.append(";");
		sqlBuffer.append("\n");
		argesBuffer.addAll(Arrays.asList(arges));
		if (sqlsize >= PropertiesLoader.getInstance().getConfig(
				"module.wxService.msgDump.buffersize", 10)) {
			try {
				new Dao().update(sqlBuffer.toString(), argesBuffer.toArray());
				log.debug("weixin message dump submit to database success.");
				sqlsize = 0;
				sqlBuffer = new StringBuffer();
				argesBuffer.clear();
			} catch (SQLException e) {
				log.warn("weixin message dump submit to database faild.");
			}
		}
	}
	
	

	@Override
	public void onRevoleRevcObject(WxRecvMsg revcMsg) {
		if (!isEnableWxMsgDump()) {
			return;
		}
		if (revcMsg instanceof WxRecvEventMsg) {
			// 事件消息接收
			WxRecvEventMsg msg = (WxRecvEventMsg) revcMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_event_type, "
					+ "analyse_event_key, analyse_location_latitude, analyse_location_longitude, analyse_location_precision "
					+ ") values (?,?,?,?,?,?,?,?,?,?,?,?)", 
					new Date(), "revc", msg.getFromUser(), msg.getToUser(), threadLocal.get(), 
					"event", msg.getMsgId(), msg.getEvent(),
					(msg.getEventKey() != null ? msg.getEventKey() : null), 
					("LOCATION".equals(msg.getEvent()) ? msg.getLatitude() : ""), 
					("LOCATION".equals(msg.getEvent()) ? msg.getLongitude() : ""), 
					("LOCATION".equals(msg.getEvent()) ? msg.getPrecision() : ""));
			
		} else if (revcMsg instanceof WxRecvTextMsg) {
			// 文本消息接收
			WxRecvTextMsg msg = (WxRecvTextMsg) revcMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_text_content "
					+ ") values (?,?,?,?,?,?,?,?)", 
					new Date(), "revc", msg.getFromUser(), msg.getToUser(), threadLocal.get(), 
					"event", msg.getMsgId(), msg.getContent());
			
		} else if (revcMsg instanceof WxRecvGeoMsg) {
			// 位置消息接收
			WxRecvGeoMsg msg = (WxRecvGeoMsg) revcMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_location_latitude, "
					+ "analyse_location_longitude, analyse_location_scale, analyse_location_label "
					+ ") values (?,?,?,?,?,?,?,?,?,?,?)", 
					new Date(), "revc", msg.getFromUser(), msg.getToUser(), threadLocal.get(), 
					"event", msg.getMsgId(), msg.getLatitude(), msg.getLongitude(), 
					msg.getScale(), msg.getLabel());
			
		} else if (revcMsg instanceof WxRecvLinkMsg) {
			// 链接消息接收
			WxRecvLinkMsg msg = (WxRecvLinkMsg) revcMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_link_title, analyse_link_url "
					+ ") values (?,?,?,?,?,?,?,?,?)", 
					new Date(), "revc", msg.getFromUser(), msg.getToUser(), threadLocal.get(), 
					"event", msg.getMsgId(), msg.getTitle(), msg.getUrl());
			
		} else if (revcMsg instanceof WxRecvPicMsg) {
			// 图片消息接收
			WxRecvPicMsg msg = (WxRecvPicMsg) revcMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_pic_url "
					+ ") values (?,?,?,?,?,?,?,?)", 
					new Date(), "revc", msg.getFromUser(), msg.getToUser(), threadLocal.get(), 
					"event", msg.getMsgId(), msg.getPicUrl());
			
		} else if (revcMsg instanceof WxRecvVoiceMsg) {
			// 语音消息接收
			WxRecvVoiceMsg msg = (WxRecvVoiceMsg) revcMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_voice_type, analyse_voice_mediaid, analyse_voice_recognition "
					+ ") values (?,?,?,?,?,?,?,?,?)", 
					new Date(), "revc", msg.getFromUser(), msg.getToUser(), threadLocal.get(), 
					"event", msg.getMsgId(), msg.getMediaId(), msg.getRecognition());
			
		} else if (revcMsg instanceof WxRecvVideoMsg) {
			// 视频消息接收
			WxRecvVideoMsg msg = (WxRecvVideoMsg) revcMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_video_type, analyse_video_mediaid "
					+ ") values (?,?,?,?,?,?,?,?,?)", 
					new Date(), "revc", msg.getFromUser(), msg.getToUser(), threadLocal.get(), 
					"event", msg.getMsgId(), msg.getFormat(), msg.getMediaId());
			
		}
	}

	


	@Override
	public void onComplateAccept(WxRecvMsg msg, WxSendMsg sendMsg,
			AcceptNode accept) {
		if (sendMsg == null || !isEnableWxMsgDump()) {
			return;
		}
		if (sendMsg instanceof WxSendTextMsg) {
			// 文本消息发送
			WxSendTextMsg send = (WxSendTextMsg) sendMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id, analyse_text_content "
					+ ") values (?,?,?,?,?,?,?,?)", 
					new Date(), "send", send.getFromUser(), send.getToUser(), threadLocal.get(), 
					"event", "reply_" + msg.getMsgId(), send.getContent());
		} else if (sendMsg instanceof WxSendNewsMsg) {
			// 图文消息发送
			WxSendNewsMsg send = (WxSendNewsMsg) sendMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id "
					+ ") values (?,?,?,?,?,?,?)", 
					new Date(), "send", send.getFromUser(), send.getToUser(), threadLocal.get(), 
					"event", "reply_" + msg.getMsgId());
		} else if (sendMsg instanceof WxSendMusicMsg) {
			// 音乐消息发送
			WxSendMusicMsg send = (WxSendMusicMsg) sendMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id "
					+ ") values (?,?,?,?,?,?,?)", 
					new Date(), "send", send.getFromUser(), send.getToUser(), threadLocal.get(), 
					"event", "reply_" + msg.getMsgId());
		} else {
			// 其它消息
			WxSendMsg send = sendMsg;
			appendSql(" INSERT INTO module_wxservice_msg_dump ("
					+ "log_timestamp, log_type, wx_fromid, wx_descid, xml_body, "
					+ "analyse_msg_msgtype, analyse_msg_id "
					+ ") values (?,?,?,?,?,?,?)", 
					new Date(), "send", send.getFromUser(), send.getToUser(), threadLocal.get(), 
					"event", "reply_" + msg.getMsgId());
		}
	}



	@Override
	public void onRevcMsg(String xmlmsg, HttpServletRequest request,
			HttpServletResponse response) {
		threadLocal.set(xmlmsg);
	}
	
	
	
	
	

}
