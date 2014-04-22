package module.wxService.parser;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import module.wxService.vo.recv.WxRecvEventMsg;
import module.wxService.vo.recv.WxRecvMsg;

public class WxRecvEventMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvEventMsg parser(Element root, WxRecvMsg msg)  throws IOException, DocumentException {
		String event = getElementText(root, "Event");
		String eventKey = getElementText(root, "EventKey");
		String latitude = getElementText(root, "Latitude");
		String longitude = getElementText(root, "Longitude");
		String precision = getElementText(root, "Precision");
		return new WxRecvEventMsg(msg, event, eventKey, latitude, longitude, precision);
	}

}
