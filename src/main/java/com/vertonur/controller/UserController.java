package com.vertonur.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.bean.User;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;

@Controller
public class UserController {

	@RequestMapping(value = "/users/form", method = RequestMethod.GET)
	public String showHomePage(HttpServletRequest request) {

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		request.setAttribute("loginCaptchaEnabled",
				config.isLoginCaptchaEnabled());

		return "default/user/user/user_login";
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public String showUserProfile(@PathVariable int userId,
			@RequestParam(required = false) boolean mine,
			HttpServletRequest request) {

		UserService userService = new UserService();
		User user = userService.getUserById(userId);
		request.setAttribute("displayedUser", user);

		if (!mine) {
			InfoService infoService = new InfoService();
			long topicNum = infoService.getTopicNumByCreator(user);
			request.setAttribute("topicNum", topicNum);

			long rsNum = infoService.getResponseNumByUser(user);
			request.setAttribute("rsNum", rsNum);
		}

		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		UserConfig config = service.getUserConfig();
		request.setAttribute("avatarHeight", config.getAvatarHeight());
		request.setAttribute("avatarWidth", config.getAvatarWidth());

		if (mine)
			return "default/user/user/user_form";
		else
			return "default/user/user/user_profile";
	}
}
