package com.vertonur.user.message.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.InfoService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.constants.Constants;
import com.vertonur.pojo.PrivateMessage;
import com.vertonur.pojo.User;
import com.vertonur.user.message.form.PrivateMsgForm;

public class CreatePrivateMessageAction extends OperactionCheckAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return super.execute(mapping, form, request, response);
	}

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrivateMsgForm pmForm = (PrivateMsgForm) form;
		String subject = pmForm.getSubject();
		String content = pmForm.getContent();
		String userId = pmForm.getUserId();
		boolean attachSig = pmForm.isAttachSig();

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.findForward("DisplayPrivateMsgSentbox");
		}

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User theCurrentUser = userService.getUserById(userSession.getUserId());

		PrivateMessage pm = new PrivateMessage();
		pm.setSubject(subject);
		pm.setContent(content);
		pm.setAuthor(theCurrentUser);
		pm.setAttachSig(attachSig);
		
		User receiver = userService.getUserById(Integer.parseInt(userId));
		pm.setReceiver(receiver);
		pm.setCreatedTime(new Date());
		InfoService infoService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.INFO_SERVICE);
		infoService.savePrivateMsg(pm);

		resetToken(request);

		return mapping.findForward("DisplayPrivateMsgSentbox");
	}
}