package cn.itcast.e_anno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("userAction")  // 加入IOC容器
public class UserAction {

	@Resource(name = "userService")
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

	public String execute() {
		userService.save();
		return null;
	}
}








