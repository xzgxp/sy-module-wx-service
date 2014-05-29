package module.wxService.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import module.wxService.service.analysis.AnalysisAction;
import module.wxService.service.analysis.AnalysisHelper;
import sy.module.core.dao.Dao;
import sy.module.core.mvc.annotation.ModuleAction;
import sy.module.core.mvc.annotation.ModuleActionParmar;
import sy.module.core.mvc.annotation.ModuleController;

import java.text.SimpleDateFormat;

/**
 * 用户变动分析控制器
 * 
 * @author 石莹 @ caituo
 *
 */
@ModuleController(namespace="module/wxService/controlCenter")
public class ControlCenterUserChangeController {
	private static final Log log = LogFactory.getLog(ControlCenterUserChangeController.class);
	
	/**
	 * 数据加载
	 * @param request
	 * @param response
	 * @param starttime
	 * @param endtime
	 * @return view
	 * @throws SQLException 
	 */
	@ModuleAction(url="userchangedata")
	public Map<String, Object> data(
			HttpServletRequest request, 
			HttpServletResponse response, 
			@ModuleActionParmar(name = "times") String times
	) throws Exception {
		List<Date> list = new ArrayList<Date>();
		for (Object time : new ObjectMapper().readValue(times, List.class)) {
			list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time.toString()));
		}
		//
		final Dao dao = new Dao();
		AnalysisHelper analysisHelper = new AnalysisHelper(list);
		Map<String, Object> data = 
				analysisHelper.addAction(new AnalysisAction() {
					@Override
					public int analysisAction(Date start, Date end) {
						try {
							return (int) dao.queryForLong(
									"SELECT COUNT(log_row_id) AS rs_count "
									+ "FROM module_wxservice_msg_dump "
									+ "WHERE log_type = 'revc' "
									+ "AND analyse_msg_msgtype = 'event' "
									+ "AND analyse_event_type = ? "
									+ "AND log_timestamp >= ? "
									+ "AND log_timestamp < ?", 
									"subscribe", 
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start), 
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end));
						} catch (SQLException e) {
							log.warn("analysis action error", e);
							return 0;
						}
					}
				}, "subscribe_count")
				.addAction(new AnalysisAction() {
					@Override
					public int analysisAction(Date start, Date end) {
						try {
							return (int) dao.queryForLong(
									"SELECT COUNT(log_row_id) AS rs_count "
									+ "FROM module_wxservice_msg_dump "
									+ "WHERE log_type = 'revc' "
									+ "AND analyse_msg_msgtype = 'event' "
									+ "AND analyse_event_type = ? "
									+ "AND log_timestamp >= ? "
									+ "AND log_timestamp < ?", 
									"unsubscribe", 
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start), 
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end));
						} catch (SQLException e) {
							log.warn("analysis action error", e);
							return 0;
						}
					}
				}, "unsubscribe_count")
				.getResultMap();
		// 统计前总数
		data.put("history_subscribe_count", (int) dao.queryForLong(
							"SELECT COUNT(log_row_id) AS rs_count "
							+ "FROM module_wxservice_msg_dump "
							+ "WHERE log_type = 'revc' "
							+ "AND analyse_msg_msgtype = 'event' "
							+ "AND analyse_event_type = ? "
							+ "AND log_timestamp < ?", 
							"subscribe", 
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(analysisHelper.getMinDate()) ));
		data.put("history_unsubscribe_count", (int) dao.queryForLong(
							"SELECT COUNT(log_row_id) AS rs_count "
							+ "FROM module_wxservice_msg_dump "
							+ "WHERE log_type = 'revc' "
							+ "AND analyse_msg_msgtype = 'event' "
							+ "AND analyse_event_type = ? "
							+ "AND log_timestamp < ?", 
							"unsubscribe", 
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(analysisHelper.getMinDate()) ));
		int accumulative_amendments = 0;
		try {
			accumulative_amendments = Integer.parseInt(new Dao().queryForString(
					"select setting_val from module_wxservice_setting where setting_key = ?", "accumulative_amendments"));
		} catch (Exception e){}
		data.put("accumulative_amendments", accumulative_amendments);
		return data;
	}

	/**
	 * 用户变化趋势
	 * @param request
	 * @param response
	 * @return json data
	 */
	@ModuleAction(url="userchange")
	public String index(
			HttpServletRequest request, 
			HttpServletResponse response,
			@ModuleActionParmar(name = "starttime", format="yyyy-MM-dd HH:mm:ss") Date starttime, 
			@ModuleActionParmar(name = "endtime", format="yyyy-MM-dd HH:mm:ss") Date endtime,
			@ModuleActionParmar(name = "accuracy") Integer accuracy,
			@ModuleActionParmar(name = "unit") String unit,
			@ModuleActionParmar(name = "querytype") String querytype
	) {
		if (starttime == null || endtime == null || starttime.after(endtime) 
				|| accuracy == null || accuracy < 0 
				|| ((endtime.getTime() - starttime.getTime()) < accuracy * 1000)) {
			Calendar cal = Calendar.getInstance();
			endtime = cal.getTime();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			starttime = cal.getTime();
			accuracy = 1;
			unit = "date";
			querytype = "subscribe";
		}
		request.setAttribute("starttime", starttime);
		request.setAttribute("endtime", endtime);
		request.setAttribute("accuracy", accuracy);
		request.setAttribute("unit", unit);
		request.setAttribute("querytype", querytype);
		return "freemarker:/module/wxService/view/userchange";
	}
	
	
}
