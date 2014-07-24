package com.vertonur.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;

@Controller
@RequestMapping("/user/form")
public class UserController {

	@RequestMapping(method = RequestMethod.GET)
	public String showHomePage(Map<String, Object> model,
			HttpServletRequest request) {

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		request.setAttribute("loginCaptchaEnabled",
				config.isLoginCaptchaEnabled());

		return "default/user/user/user_login";
	}
}
