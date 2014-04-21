package module.wxService.accept;

import java.io.InputStream;

import module.wxService.exception.WxMessageAcceptException;

/**
 * 微信消息受理接口
 * 
 * @author 石莹 @ caituo
 *
 */
public interface WxMessageNotice {
	
	/**
	 * 微信消息受理入口
	 * 
	 * @param xmlstream xml流
	 * @return 结果流
	 * @throws WxMessageAcceptException 若处理异常，抛出异常
	 */
	public byte[] accept(
			InputStream xmlstream
	) throws WxMessageAcceptException ;

}
