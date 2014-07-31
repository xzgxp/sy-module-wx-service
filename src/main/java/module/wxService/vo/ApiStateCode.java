package module.wxService.vo;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * API状态代码枚举
 * 
 * @author 石莹 @ caituo
 *
 */
public enum ApiStateCode {
	
	code_s1("-1", "系统繁忙"),
	code_0("0", "请求成功"),
	code_40001("40001", "获取access_token时AppSecret错误，或者access_token无效"),
	code_40002("40002", "不合法的凭证类型"),
	code_40003("40003", "不合法的OpenID"),
	code_40004("40004", "不合法的媒体文件类型"),
	code_40005("40005", "不合法的文件类型"),
	code_40006("40006", "不合法的文件大小"),
	code_40007("40007", "不合法的媒体文件id"),
	code_40008("40008", "不合法的消息类型"),
	code_40009("40009", "不合法的图片文件大小"),
	code_40010("40010", "不合法的语音文件大小"),
	code_40011("40011", "不合法的视频文件大小"),
	code_40012("40012", "不合法的缩略图文件大小"),
	code_40013("40013", "不合法的APPID"),
	code_40014("40014", "不合法的access_token"),
	code_40015("40015", "不合法的菜单类型"),
	code_40016("40016", "不合法的按钮个数"),
	code_40017("40017", "不合法的按钮个数"),
	code_40018("40018", "不合法的按钮名字长度"),
	code_40019("40019", "不合法的按钮KEY长度"),
	code_40020("40020", "不合法的按钮URL长度"),
	code_40021("40021", "不合法的菜单版本号"),
	code_40022("40022", "不合法的子菜单级数"),
	code_40023("40023", "不合法的子菜单按钮个数"),
	code_40024("40024", "不合法的子菜单按钮类型"),
	code_40025("40025", "不合法的子菜单按钮名字长度"),
	code_40026("40026", "不合法的子菜单按钮KEY长度"),
	code_40027("40027", "不合法的子菜单按钮URL长度"),
	code_40028("40028", "不合法的自定义菜单使用用户"),
	code_40029("40029", "不合法的oauth_code"),
	code_40030("40030", "不合法的refresh_token"),
	code_40031("40031", "不合法的openid列表"),
	code_40032("40032", "不合法的openid列表长度"),
	code_40033("40033", "不合法的请求字符，不能包含\\uxxxx格式的字符"),
	code_40035("40035", "不合法的参数"),
	code_40038("40038", "不合法的请求格式"),
	code_40039("40039", "不合法的URL长度"),
	code_40050("40050", "不合法的分组id"),
	code_40051("40051", "分组名字不合法"),
	code_41001("41001", "缺少access_token参数"),
	code_41002("41002", "缺少appid参数"),
	code_41003("41003", "缺少refresh_token参数"),
	code_41004("41004", "缺少secret参数"),
	code_41005("41005", "缺少多媒体文件数据"),
	code_41006("41006", "缺少media_id参数"),
	code_41007("41007", "缺少子菜单数据"),
	code_41008("41008", "缺少oauth code"),
	code_41009("41009", "缺少openid"),
	code_42001("42001", "access_token超时"),
	code_42002("42002", "refresh_token超时"),
	code_42003("42003", "oauth_code超时"),
	code_43001("43001", "需要GET请求"),
	code_43002("43002", "需要POST请求"),
	code_43003("43003", "需要HTTPS请求"),
	code_43004("43004", "需要接收者关注"),
	code_43005("43005", "需要好友关系"),
	code_44001("44001", "多媒体文件为空"),
	code_44002("44002", "POST的数据包为空"),
	code_44003("44003", "图文消息内容为空"),
	code_44004("44004", "文本消息内容为空"),
	code_45001("45001", "多媒体文件大小超过限制"),
	code_45002("45002", "消息内容超过限制"),
	code_45003("45003", "标题字段超过限制"),
	code_45004("45004", "描述字段超过限制"),
	code_45005("45005", "链接字段超过限制"),
	code_45006("45006", "图片链接字段超过限制"),
	code_45007("45007", "语音播放时间超过限制"),
	code_45008("45008", "图文消息超过限制"),
	code_45009("45009", "接口调用超过限制"),
	code_45010("45010", "创建菜单个数超过限制"),
	code_45015("45015", "回复时间超过限制"),
	code_45016("45016", "系统分组，不允许修改"),
	code_45017("45017", "分组名字过长"),
	code_45018("45018", "分组数量超过上限"),
	code_46001("46001", "不存在媒体数据"),
	code_46002("46002", "不存在的菜单版本"),
	code_46003("46003", "不存在的菜单数据"),
	code_46004("46004", "不存在的用户"),
	code_47001("47001", "解析JSON/XML内容错误"),
	code_48001("48001", "api功能未授权"),
	code_50001("50001", "用户未授权该api");
	
	
	private String code;
	private String msg;
	
	
	private ApiStateCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 通过JSON字符串解析状态码
	 * @param json
	 * @return 状态码
	 */
	@SuppressWarnings("unchecked")
	public static ApiStateCode resolveStateCode(String json) {
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> obj = null;
		try {
			obj = om.readValue(json, Map.class);
		} catch (Exception e) {
			return null;
		}
		if (obj == null || obj.get("errcode") == null) {
			return null;
		}
		String errcode = obj.get("errcode").toString();
		for (ApiStateCode asc : ApiStateCode.values()) {
			if (asc.code.equals(errcode)) {
				return asc;
			}
		}
		return null;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}	
