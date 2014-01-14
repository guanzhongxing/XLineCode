package com.vertonur.common;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.UserService;
import com.vertonur.pojo.User;

//check if a user specified name exist in the database through,this action is 
//used in registration and user management functions
public class CheckUserNameExistence extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String userName = request.getParameter("userName");
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		List<User> gotUsers = userService.getUsersByName_EM(userName);
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter writer = response.getWriter();
		if (!gotUsers.isEmpty()) {
			writer.write("true");
		} else
			writer.write("false");
		return null;
	}
}
