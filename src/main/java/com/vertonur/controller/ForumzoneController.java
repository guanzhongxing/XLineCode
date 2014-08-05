package com.vertonur.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.bean.Forumzone;
import com.vertonur.constants.Constants;
import com.vertonur.service.ForumzoneService;

@Controller
public class ForumzoneController {

	@RequestMapping(value = { "/forumzones" }, method = RequestMethod.GET)
	public String getForumzoneList(HttpServletRequest request) {

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);
		return "default/admin/forumzone_list";
	}

	@RequestMapping(value = { "/forumzones/form" }, method = RequestMethod.GET)
	public String showForumzoneForm(
			@RequestParam(defaultValue = "0") int forumzoneId,
			HttpServletRequest request) {

		if (forumzoneId != 0) {
			ForumzoneService forumzoneService = new ForumzoneService();
			Forumzone forumzone = forumzoneService
					.getForumzoneById(forumzoneId);
			request.setAttribute("forumzone", forumzone);
			request.setAttribute("servicePath", "/admin/forumzone/edit");
		} else
			request.setAttribute("servicePath", "/admin/forumzone/create");
		return "default/admin/forumzone_form";
	}
}
