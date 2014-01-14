package com.vertonur.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.common.OperactionCheckAction;
import com.vertonur.context.SystemContextService;
import com.vertonur.context.SystemContextService.SystemState;

public class SystemStatisticAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SystemContextService service = SystemContextService.getService();
		SystemState state = service.getState();

		request.setAttribute("rspNum", state.getCommentNum());
		request.setAttribute("rspsPerDay", state.getCommentPerDay());

		request.setAttribute("topicNum", state.getInfoNum());
		request.setAttribute("topicsPerDay", state.getInfoPerDay());

		request.setAttribute("userNum", state.getUserNum());
		request.setAttribute("usersPerDay", state.getUserPerDay());

		SystemContextService contextService = SystemContextService.getService();
		request.setAttribute("userSessions", contextService.getLoginSessions()
				.values());
		return mapping.findForward("SystemStatistic");
	}
}
