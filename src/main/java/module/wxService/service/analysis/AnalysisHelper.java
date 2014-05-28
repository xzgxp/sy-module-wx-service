package module.wxService.service.analysis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析服务
 * 
 * @author 石莹 @ caituo
 *
 */
public class AnalysisHelper {
	
	private List<Date> columns = null;
	private Map<String, Object> data = null;
	
	public AnalysisHelper(
			Date starttime, 
			Date endtime, 
			Integer accuracy) {
		columns = calculatedTimeInterval(starttime, endtime, accuracy);
		data = new HashMap<String, Object>();
		data.put("columns", columns);
		this.addColumnsFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public Map<String, Object> getResultMap() {
		return data;
	}
	
	
	private AnalysisHelper addColumnsFormat(String format) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < columns.size(); i++) {
			list.add(new SimpleDateFormat(format).format(columns.get(i)));
		}
		data.put("columns_format_string", format);
		data.put("columns_format", list);
		return this;
	}
	
	
	/**
	 * 添加统计
	 * @param action
	 * @param name
	 * @return this
	 */
	public AnalysisHelper addAction(AnalysisAction action, String name) {
		List<Integer> list = new ArrayList<Integer>();
		int count = 0;
		for (int i = 1; i < columns.size(); i++) {
			Date start = (Date) columns.get(i - 1);
			Date end = (Date) columns.get(i);
			int rs = action.analysisAction(start, end);
			list.add(rs);
			count += rs;
		}
		data.put(name, list);
		data.put(name + "_count", count);
		
		return this;
	}
	
	/**
	 * 计算时间区间
	 * @param starttime
	 * @param endtime
	 * @param accuracy
	 * @return 时间区间
	 */
	private List<Date> calculatedTimeInterval(
			Date starttime, 
			Date endtime, 
			Integer accuracy) {
		// 计算时间区间
		List<Date> columns = new ArrayList<Date>();
		Date curtime = starttime;
		while (curtime.getTime() <= endtime.getTime()) {
			columns.add(curtime);
			curtime = new Date(curtime.getTime() + accuracy * 1000);
		}
		return columns;
	}

}
