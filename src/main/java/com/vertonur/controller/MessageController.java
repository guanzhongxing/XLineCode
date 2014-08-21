package com.vertonur.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/messages")
public class MessageController {
	private final String MESSAGE_PATH = "default/user/message";

	@RequestMapping(params = "password")
	public String showPasswordUpdatedMsg(HttpServletRequest request) {

		request.setAttribute("passwordChanged", "true");
		return MESSAGE_PATH;
	}
}
