package com.vertonur.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.common.OperactionCheckAction;
import com.vertonur.util.PermissionUtils;

public class AdminIndexAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("servicePath", "admin/statistic");

		HttpSession session = request.getSession();
		session.setAttribute("enableOperateGroup", PermissionUtils
				.checkOperateGroupPermission(getServlet().getServletContext()));
		session.setAttribute("enableOperateUser", PermissionUtils
				.checkOperateUserPermission(getServlet().getServletContext()));
		session.setAttribute("enableOperateForumzone", PermissionUtils
				.checkOperateForumzonePermission(getServlet()
						.getServletContext()));
		session.setAttribute("enableOperateForum", PermissionUtils
				.checkOperateForumPermission(getServlet().getServletContext()));
		session.setAttribute("enableConfigRuntimeParameter", PermissionUtils
				.checkConfigRuntimeParameterPermission(getServlet()
						.getServletContext()));
		session.setAttribute("enableOperateRanking",
				PermissionUtils.checkOperateRankingPermission(getServlet()
						.getServletContext()));
		session.setAttribute("enableConfigPoints", PermissionUtils
				.checkConfigPointsPermission(getServlet().getServletContext()));
		session.setAttribute("enableConfigBackendPermission", PermissionUtils
				.checkConfigPointsPermission(getServlet().getServletContext()));
		return mapping.findForward("AdminIndex");
	}
}
