package module.wxService.service;

import java.io.InputStream;
import java.util.Iterator;

import module.wxService.event.WxAcceptEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WxControlConfigLoader {
	private static final Log log = LogFactory.getLog(WxControlConfigLoader.class);
	private static final String xmlConfigMsgAcceptClassPath = "module.wxService.service.XmlConfigMsgAccept";
	
	private String configPath = null;
	
	public WxControlConfigLoader(String configPath) {
		this.configPath = configPath;
	}
	
	private Element loadSimpleAccept(String type) {
		try {
			Document doc = loadDocument();
			Element root = doc.getRootElement();
			Element reply = root.element("reply");
			return reply.element(type);
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 加载基于XML配置的消息受理器
	 * @return 消息受理器
	 */
	private WxMsgAccept loadXmlConfigMsgAccept() {
		try {
			Class<WxMsgAccept> cls = (Class<WxMsgAccept>) Class.forName(xmlConfigMsgAcceptClassPath);
			return cls.newInstance();
		} catch (Exception e) {
			log.warn("XML config msg accept load faild!", e);
			return null;
		}
	}
	
	/**
	 * 获取BeanLoad方式
	 * @return BeanLoad方式实体
	 */
	public Beanload loadBeanload() {
		try {
			Document doc = loadDocument();
			Element root = doc.getRootElement();
			String beanloadtype = root.attributeValue("beanload");
			if (beanloadtype == null || beanloadtype.equals("")) {
				beanloadtype = Beanload.reflex.toString();
			}
			Beanload bl = Beanload.valueOf(beanloadtype);
			if (bl != null) {
				return bl;
			} else {
				return Beanload.reflex;
			}
		} catch (Exception e) {
			log.warn("获取BeanLoad方式异常。", e);
			return Beanload.reflex;
		}
	}
	
	/**
	 * 加载事件配置
	 * @return
	 */
	public WxAcceptEvent loadAcceptEvent() {
		Beanload beanload = loadBeanload();
		try {
			Document doc = loadDocument();
			Element root = doc.getRootElement();
			Element reply = root.element("reply");
			String eventCls = reply.attributeValue("event");
			if (eventCls == null || eventCls.equals("")) {
				return null;
			}
			WxAcceptEvent evn = (WxAcceptEvent) beanload.loadBean(eventCls);
			return evn;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 获得默认的受理文件
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadDefaultAccept() {
		Beanload beanload = loadBeanload();
		Element acceptElement = loadSimpleAccept("default");
		if (acceptElement != null) {
			AcceptNode an = new AcceptNode();
			an.setSubElement(acceptElement);
			if (acceptElement.attributeValue("accept") == null 
					|| acceptElement.attributeValue("accept").equals("")) {
				an.setReflectInter(loadXmlConfigMsgAccept());
			} else {
				an.setReflectInter(beanload.loadWxMsgAccept(acceptElement.attributeValue("accept")));
			}
			return an;
		} else {
			log.warn("default accept not config, please check.");
			return null;
		}
	}
	
	/**
	 * 获得地理位置的受理文件
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadGeoAccept() {
		Beanload beanload = loadBeanload();
		Element acceptElement = loadSimpleAccept("geo");
		if (acceptElement != null) {
			AcceptNode an = new AcceptNode();
			an.setSubElement(acceptElement);
			if (acceptElement.attributeValue("accept") == null 
					|| acceptElement.attributeValue("accept").equals("")) {
				an.setReflectInter(loadXmlConfigMsgAccept());
			} else {
				an.setReflectInter(beanload.loadWxMsgAccept(acceptElement.attributeValue("accept")));
			}
			return an;
		} else {
			return loadDefaultAccept();
		}
	}
	
	/**
	 * 获得链接的受理文件
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadLinkAccept() {
		Beanload beanload = loadBeanload();
		Element acceptElement = loadSimpleAccept("link");
		if (acceptElement != null) {
			AcceptNode an = new AcceptNode();
			an.setSubElement(acceptElement);
			if (acceptElement.attributeValue("accept") == null 
					|| acceptElement.attributeValue("accept").equals("")) {
				an.setReflectInter(loadXmlConfigMsgAccept());
			} else {
				an.setReflectInter(beanload.loadWxMsgAccept(acceptElement.attributeValue("accept")));
			}
			return an;
		} else {
			return loadDefaultAccept();
		}
	}
	
	/**
	 * 获得图片的受理文件
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadPicAccept() {
		Beanload beanload = loadBeanload();
		Element acceptElement = loadSimpleAccept("pic");
		if (acceptElement != null) {
			AcceptNode an = new AcceptNode();
			an.setSubElement(acceptElement);
			if (acceptElement.attributeValue("accept") == null 
					|| acceptElement.attributeValue("accept").equals("")) {
				an.setReflectInter(loadXmlConfigMsgAccept());
			} else {
				an.setReflectInter(beanload.loadWxMsgAccept(acceptElement.attributeValue("accept")));
			}
			return an;
		} else {
			return loadDefaultAccept();
		}
	}
	
	/**
	 * 加载符合规则的文本受理文件
	 * @param content 内容
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadTextAcceptByMatch(String content) {
		try {
			Document doc = loadDocument();
			Element root = doc.getRootElement();
			Element reply = root.element("reply");
			Iterator eventIter = reply.elementIterator("text");
			while (eventIter.hasNext()) {
				Element event = (Element) eventIter.next();
				String match = event.attributeValue("match");
				if (match == null || match.equals("")) {
					match = ".*";
				}
				if (content.matches(match)) {
					Beanload beanload = loadBeanload();
					AcceptNode an = new AcceptNode();
					an.setSubElement(event);
					if (event.attributeValue("accept") == null 
							|| event.attributeValue("accept").equals("")) {
						an.setReflectInter(loadXmlConfigMsgAccept());
					} else {
						an.setReflectInter(beanload.loadWxMsgAccept(event.attributeValue("accept")));
					}
					return an;
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return loadDefaultAccept();
	}
	
	/**
	 * 加载指定事件Key的受理文件
	 * @param key
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadEventAcceptByKey(String key) {
		try {
			if (key == null) {
				return null;
			}
			if (key.indexOf("~") == -1) {
				key = key + "~";
			}
			//
			Document doc = loadDocument();
			Element root = doc.getRootElement();
			Element reply = root.element("reply");
			Iterator eventIter = reply.elementIterator("event");
			while (eventIter.hasNext()) {
				Element event = (Element) eventIter.next();
				String keyVal = event.attributeValue("key");
				if (key.equalsIgnoreCase(keyVal)) {
					Beanload beanload = loadBeanload();
					AcceptNode an = new AcceptNode();
					an.setSubElement(event);
					if (event.attributeValue("accept") == null 
							|| event.attributeValue("accept").equals("")) {
						an.setReflectInter(loadXmlConfigMsgAccept());
					} else {
						an.setReflectInter(beanload.loadWxMsgAccept(event.attributeValue("accept")));
					}
					return an;
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}


	/**
	 * 视频消息受理文件
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadVideoAccept() {
		Beanload beanload = loadBeanload();
		Element acceptElement = loadSimpleAccept("video");
		if (acceptElement != null) {
			AcceptNode an = new AcceptNode();
			an.setSubElement(acceptElement);
			if (acceptElement.attributeValue("accept") == null 
					|| acceptElement.attributeValue("accept").equals("")) {
				an.setReflectInter(loadXmlConfigMsgAccept());
			} else {
				an.setReflectInter(beanload.loadWxMsgAccept(acceptElement.attributeValue("accept")));
			}
			return an;
		} else {
			return loadDefaultAccept();
		}
	}


	/**
	 * 音频消息受理文件
	 * @return 受理文件描述对象
	 */
	public AcceptNode loadVoiceAccept() {
		Beanload beanload = loadBeanload();
		Element acceptElement = loadSimpleAccept("voice");
		if (acceptElement != null) {
			AcceptNode an = new AcceptNode();
			an.setSubElement(acceptElement);
			if (acceptElement.attributeValue("accept") == null 
					|| acceptElement.attributeValue("accept").equals("")) {
				an.setReflectInter(loadXmlConfigMsgAccept());
			} else {
				an.setReflectInter(beanload.loadWxMsgAccept(acceptElement.attributeValue("accept")));
			}
			return an;
		} else {
			return loadDefaultAccept();
		}
	}

	/**
	 * 加载文件
	 * @return XML文档
	 * @throws DocumentException
	 */
	private Document loadDocument() throws DocumentException {
          Document document = null;
          InputStream configIs = null;
          try {
        	  configIs = WxControlConfigLoader.class.getClassLoader()
        			  .getResourceAsStream(configPath);
        	  if (configIs != null) {
	        	  SAXReader reader = new SAXReader();
	        	  document = reader.read(configIs);
        	  } else {
        		  log.warn("配置文件["+configPath+"]读取失败，忽略消息处理。");
        	  }
          } catch (Exception e) {
        	  log.warn("load document error.", e);
          } finally {
        	  try {
        		  configIs.close();
        	  } catch (Exception e){}
          }
          return document;
	}
	
	
}
