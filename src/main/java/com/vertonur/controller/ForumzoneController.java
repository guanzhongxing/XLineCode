package com.vertonur.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.bean.Admin;
import com.vertonur.bean.Forumzone;
import com.vertonur.constants.Constants;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.dms.exception.DeptModerationListNotEmptyException;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;

@Controller
public class ForumzoneController {

	@RequestMapping(value = { "/forumzones" }, method = RequestMethod.GET)
	public String getForumzoneList(
			@RequestParam(required = false) boolean created,
			@RequestParam(required = false) boolean updated,
			@RequestParam(required = false) boolean deleted,
			HttpServletRequest request) {

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);
		request.setAttribute("created", created);
		request.setAttribute("updated", updated);
		request.setAttribute("deleted", deleted);
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
		}
		return "default/admin/forumzone_form";
	}

	@RequestMapping(value = { "/forumzones/form" }, method = RequestMethod.POST)
	public String createForumzone(@Valid Forumzone forumzone,
			HttpServletRequest request) {

		forumzone.setCreatedTime(new Date());
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService service = new UserService();
		Admin author = (Admin) service.getUserById(userSession.getUserId());
		forumzone.setCreator(author);

		ForumzoneService forumzoneService = new ForumzoneService();
		if (forumzoneService.saveForumzone(forumzone) != null)
			return "redirect:/forumzones?created=true";
		else {
			request.setAttribute("failed", true);
			return "default/admin/forumzone_form";
		}
	}

	@RequestMapping(value = { "/forumzones/{forumzoneId}" }, method = RequestMethod.PUT)
	public String updateForumzone(@Valid Forumzone forumzone,
			@PathVariable int forumzoneId, HttpServletRequest request) {

		ForumzoneService forumzoneService = new ForumzoneService();
		Forumzone savedForumzone = forumzoneService
				.getForumzoneById(forumzoneId);
		savedForumzone.setName(forumzone.getName());
		savedForumzone.setDescription(forumzone.getDescription());
		savedForumzone.setModerated(forumzone.isModerated());
		savedForumzone.setPriority(forumzone.getPriority());

		try {
			forumzoneService.updateForumzone(savedForumzone);
		} catch (DeptModerationListNotEmptyException
				| CategoryModerationListNotEmptyException e) {
			request.setAttribute("msgToBeModerated", true);
			return "default/admin/forumzone_form";
		}

		return "redirect:/forumzones?updated=true";
	}

	@RequestMapping(value = { "/forumzones" }, method = RequestMethod.DELETE)
	public String deleteForumzone(@RequestParam int[] forumzoneIds,
			HttpServletRequest request) {

		ForumzoneService forumzoneService = new ForumzoneService();
		for (int id : forumzoneIds) {
			Forumzone forumzone = forumzoneService.getForumzoneById(id);
			forumzone.setDeprecated(true);
			try {
				forumzoneService.updateForumzone(forumzone);
			} catch (DeptModerationListNotEmptyException
					| CategoryModerationListNotEmptyException e) {
				request.setAttribute("msgToBeModerated", true);
				return "default/admin/forumzone_form";
			}
		}

		return "redirect:/forumzones?deleted=true";
	}
}
