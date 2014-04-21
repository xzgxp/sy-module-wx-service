package module.wxService.accept;

import java.io.IOException;
import java.io.InputStream;

import module.wxService.exception.WxMessageAcceptException;
import module.wxService.model.WxMessage;
import module.wxService.service.WxMessageResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class SimpleWxMessageNotice implements WxMessageNotice {
	private static final Log log = LogFactory.getLog(SimpleWxMessageNotice.class);

	@Override
	public byte[] accept(InputStream xmlstream)
			throws WxMessageAcceptException {
		try {
			WxMessage message = WxMessageResolver.getInstance().resolveMessage(xmlstream);
			
			System.out.println(new ObjectMapper().writeValueAsString(message));
		} catch (IOException e) {
			log.warn("resolver wx message faild.", e);
			return null;
		}
		
		
		return null;
	}

}
