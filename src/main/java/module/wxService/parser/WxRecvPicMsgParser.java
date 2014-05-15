package module.wxService.parser;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.recv.WxRecvPicMsg;

public class WxRecvPicMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvPicMsg parser(Element root, WxRecvMsg msg)  throws IOException, DocumentException {
		return new WxRecvPicMsg(msg, getElementText(root, "PicUrl"), getElementText(root, "MediaId"));
	}

}
