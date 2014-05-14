DROP TABLE IF EXISTS `module_wxservice_msg_dump`;

CREATE TABLE `module_wxservice_msg_dump` (
  `log_row_id` bigint(200) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `log_timestamp` datetime NOT NULL COMMENT '记录时间戳',
  `log_type` varchar(200) NOT NULL COMMENT 'recv/send/call,记录类型',
  `wx_fromid` varchar(200) DEFAULT NULL COMMENT '消息来源ID，微信OpenID，或公众帐号原始ID',
  `wx_descid` varchar(200) DEFAULT NULL COMMENT '消息目标ID，微信OpenID，或公众帐号原始ID',
  `xml_body` varchar(2000) DEFAULT NULL COMMENT '传输的xml内容体',
  `analyse_msg_msgtype` varchar(200) DEFAULT NULL COMMENT '消息类型',
  `analyse_msg_id` varchar(200) DEFAULT NULL COMMENT '消息ID',
  `analyse_text_content` varchar(2000) DEFAULT NULL COMMENT '解析的文本内容',
  `analyse_event_type` varchar(200) DEFAULT NULL COMMENT '事件类型',
  `analyse_event_key` varchar(200) DEFAULT NULL COMMENT '事件Key',
  `analyse_location_latitude` varchar(200) DEFAULT NULL COMMENT '地址位置，经度',
  `analyse_location_longitude` varchar(200) DEFAULT NULL COMMENT '地址位置，纬度',
  `analyse_location_precision` varchar(200) DEFAULT NULL COMMENT '地址位置，精度',
  `analyse_location_scale` varchar(200) DEFAULT NULL COMMENT '地址位置，缩放',
  `analyse_location_label` varchar(200) DEFAULT NULL COMMENT '地址位置，地理位置标签',
  PRIMARY KEY (`log_row_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='微信交互日志表';