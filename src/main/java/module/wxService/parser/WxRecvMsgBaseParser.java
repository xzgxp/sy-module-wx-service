package module.wxService.parser;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import module.wxService.vo.recv.WxRecvMsg;

public abstract class WxRecvMsgBaseParser implements WxRecvMsgParser {
	private static final Log log = LogFactory.getLog(WxRecvMsgBaseParser.class);

	@Override
	public WxRecvMsg parser(Document doc) throws IOException, DocumentException {
		Element root = doc.getRootElement();
		String toUserName = getElementText(root, "ToUserName");
		String fromUserName = getElementText(root, "FromUserName");
		String createTime = getElementText(root, "CreateTime");
		String msgType = getElementText(root, "MsgType");
		String msgId = getElementText(root, "MsgId");
		
		return parser(root,new WxRecvMsg(toUserName,fromUserName,createTime,msgType,msgId));
	}
	
	protected abstract WxRecvMsg parser(Element root,WxRecvMsg msg) throws IOException, DocumentException;
	
	protected String getElementText(Element elem,String xpath) throws IOException, DocumentException {
		Node node = elem.selectSingleNode(xpath+"/text()");
		if(null == node) {
			return "";
		}
		return node.getText();
	}

}
