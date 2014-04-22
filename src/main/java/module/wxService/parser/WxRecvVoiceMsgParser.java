package module.wxService.parser;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.recv.WxRecvVoiceMsg;

public class WxRecvVoiceMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvVoiceMsg parser(Element root, WxRecvMsg msg)  throws IOException, DocumentException {
		return new WxRecvVoiceMsg(msg, getElementText(root, "MediaId"), getElementText(root, "Format"), getElementText(root, "Recognition"));
	}

	
}
