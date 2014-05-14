
if (exists (SELECT * FROM dbo.sysobjects 
	where id = object_id(N'module_wxservice_msg_dump') and OBJECTPROPERTY(id, N'IsUserTable') = 1)) 
DROP TABLE module_wxservice_msg_dump;

CREATE TABLE module_wxservice_msg_dump (
  log_row_id int NOT NULL identity(1, 1) , /* 记录ID */
  log_timestamp datetime NOT NULL , /* 记录时间戳 */
  log_type varchar(200) NOT NULL , /* recv/send/call,记录类型 */
  wx_fromid varchar(200) DEFAULT NULL , /* 消息来源ID，微信OpenID，或公众帐号原始ID */
  wx_descid varchar(200) DEFAULT NULL , /* 消息目标ID，微信OpenID，或公众帐号原始ID */
  xml_body varchar(2000) DEFAULT NULL , /* 传输的xml内容体 */
  analyse_msg_msgtype varchar(200) DEFAULT NULL , /* 消息类型 */
  analyse_msg_id varchar(200) DEFAULT NULL , /* 消息ID */
  analyse_text_content varchar(2000) DEFAULT NULL , /* 解析的文本内容 */
  analyse_event_type varchar(200) DEFAULT NULL , /* 事件类型 */
  analyse_event_key varchar(200) DEFAULT NULL , /* 事件Key */
  analyse_location_latitude varchar(200) DEFAULT NULL , /* 地址位置，经度 */
  analyse_location_longitude varchar(200) DEFAULT NULL , /* 地址位置，纬度 */
  analyse_location_precision varchar(200) DEFAULT NULL , /* 地址位置，精度 */
  analyse_location_scale varchar(200) DEFAULT NULL , /* 地址位置，缩放 */
  analyse_location_label varchar(200) DEFAULT NULL , /* 地址位置，地理位置标签 */
  analyse_link_title varchar(200) DEFAULT NULL , /* 链接，标题 */
  analyse_link_url varchar(200) DEFAULT NULL , /* 链接，地址 */
  analyse_pic_url varchar(200) DEFAULT NULL , /* 图片，图片地址 */
  analyse_voice_mediaid varchar(200) DEFAULT NULL , /* 语音，媒体ID */
  analyse_voice_recognition varchar(200) DEFAULT NULL , /* 语音，识别结果 */
  analyse_video_type varchar(200) DEFAULT NULL , /* 视频，类型 */
  analyse_video_mediaid varchar(200) DEFAULT NULL , /* 视频，媒体id */
  PRIMARY KEY (log_row_id)
); /* 微信交互日志表 */ 





