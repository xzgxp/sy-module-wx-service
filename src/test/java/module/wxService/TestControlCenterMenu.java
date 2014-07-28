package module.wxService;

import module.controlCenter.ControlCenterMenu;
import sy.module.core.mvc.annotation.ModuleFollowContainerInit;

@ModuleFollowContainerInit
public class TestControlCenterMenu {
	
	public TestControlCenterMenu() {
		new ControlCenterMenu("微信服务组件", "")
			.appendSubMenu(new ControlCenterMenu("用户变化", "wxService/controlCenter/userchange.do"))
			.appendSubMenu(new ControlCenterMenu("设置", "wxService/controlCenter/setting.do"))
			.appenToRoot();
	}

}
