package module.wxService.exception;

import java.io.IOException;

/**
 * 微信消息受理异常
 * 
 * @author 石莹 @ caituo
 *
 */
public class WxMessageAcceptException extends IOException {

	private static final long serialVersionUID = 4498981187813032066L;

	public WxMessageAcceptException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WxMessageAcceptException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WxMessageAcceptException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WxMessageAcceptException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
