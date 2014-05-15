package module.wxService.parser;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.recv.WxRecvVideoMsg;

public class WxRecvVideoMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvVideoMsg parser(Element root, WxRecvMsg msg)  throws IOException, DocumentException {
		return new WxRecvVideoMsg(msg, getElementText(root, "MediaId"), getElementText(root, "Format"), getElementText(root, "ThumbMediaId"));
	}

	
}
