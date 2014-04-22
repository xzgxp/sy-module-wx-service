package module.wxService.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.wxService.service.WxApiSupport;

public class WxNoticeServlet extends javax.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WxApiSupport wxApiSupport;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			wxApiSupport.notice(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.print("server error.");
			out.flush();
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			wxApiSupport.notice(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.print("server error.");
			out.flush();
			out.close();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		wxApiSupport = new WxApiSupport("wxlzmtest20131006", "support/wx/demo/config/wxReply-spring.xml");
	}
	
}
