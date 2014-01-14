package com.vertonur.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.admin.form.ModerationForm;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.pojo.ModerationLog.LogType;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.service.InfoService;

public class ModerateMsgAction extends OperactionCheckAction {

	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ModerationForm moderationForm = (ModerationForm) form;
		int[] msgIds = moderationForm.getMsgIds();
		int[] logIds = moderationForm.getLogIds();
		String[] logTypes = moderationForm.getLogTypes();
		InfoService service = new InfoService();

		for (int i = 0; i < msgIds.length; i++) {
			int msgId = msgIds[i];
			String updateOpt = request.getParameter("updateOpt_" + msgId);
			ModerationStatus option = ModerationStatus.valueOf(updateOpt);
			if (!ModerationStatus.DEFERRED.equals(option)) {
				int logId = logIds[i];
				String logTypeStr = logTypes[i];
				LogType logType = LogType.valueOf(logTypeStr);
				if (ModerationStatus.APPROVED.equals(option)) {
					if (LogType.INFO.equals(logType))
						service.approveTopic(msgId, logId);
					else if (LogType.CMT.equals(logType))
						service.approveRsp(msgId, logId);
				} else {
					if (LogType.INFO.equals(logType))
						service.rejectTopic(msgId, logId);
					else if (LogType.CMT.equals(logType))
						service.rejectRsp(msgId, logId);
				}
			}
		}

		return mapping.findForward("ModerationListAction");
	}
}
