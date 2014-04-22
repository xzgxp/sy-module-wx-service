package module.wxService.parser;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import module.wxService.vo.recv.WxRecvMsg;

public interface WxRecvMsgParser {
	WxRecvMsg parser(Document doc) throws IOException, DocumentException;
}
