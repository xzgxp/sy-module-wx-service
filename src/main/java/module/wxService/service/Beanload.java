package module.wxService.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;


/**
 * Bean加载器
 * 
 * @author 石莹 @ caituo
 *
 */
public class Beanload implements BeanFactoryAware {
	private static final Log log = LogFactory.getLog(Beanload.class);
	
	public static final Beanload reflex = new Beanload(); 
	public static final Beanload spring = new Beanload();

	private static BeanFactory beanFactory;

	// private static ApplicationContext context;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getBean(String beanName) {
		if (beanFactory == null) {
			log.error("BeanFactory为Null，请检查Spring配置（参考ReadMe 3.3.1）。");
		}
		if (null != beanFactory) {
			T t = (T) beanFactory.getBean(beanName);
			if (t == null) {
				log.warn("获取BeanFactory成功，但是指定的Bean["+beanName+"]获取失败。");
			}
			return t;
		}
		return null;
	}
	
	public static Beanload valueOf(String str) {
		if ("reflex".equals(str)) {
			return Beanload.reflex;
		} else if ("spring".equals(str)) {
			return Beanload.spring;
		} else {
			return null;
		}
	}
	
	/**
	 * 加载一个Bean
	 * @param name
	 * @return bean
	 */
	public Object loadBean(String name) {
		try {
	        if (this == Beanload.reflex) {
				Class<?> groovyClass = Class.forName(name); 
		        Object obj = groovyClass.newInstance();  
        		log.debug("通过Java反射获取 Bean["+name+"]：" + obj);
		        return obj;
	        } else {
	        	Object obj = getBean(name);
        		log.debug("通过Spring容器获取 Bean["+name+"]：" + obj);
	        	return obj;
	        }
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 加载消息受理器
	 * @param accept
	 * @return wxMsgAccept
	 */
	public WxMsgAccept loadWxMsgAccept(String accept) {
		try {
	        return (WxMsgAccept) loadBean(accept);
		} catch (Exception e) {
			return null;
		}
	}

}
