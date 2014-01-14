package com.vertonur.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.CommentConfig;
import com.vertonur.pojo.config.EmailConfig;
import com.vertonur.pojo.config.InfoConfig;
import com.vertonur.pojo.config.ModerationConfig;
import com.vertonur.pojo.config.SystemContextConfig;
import com.vertonur.pojo.config.UserConfig;

public class GetSystemConfigAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);
		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		InfoConfig infoConfig = service.getInfoConfig();
		request.setAttribute("topicsPerPage", infoConfig.getInfoPgnOffset());
		request.setAttribute("newTopicInterval",
				infoConfig.getNewInfoInterval());
		request.setAttribute("hotTopicDef", infoConfig.getHottestInfoGeCmts());
		request.setAttribute("recentTopicPageNum",
				infoConfig.getRecentInfoPgnOffset());

		CommentConfig commentConfig = service.getCommentConfig();
		request.setAttribute("rspsPerPage", commentConfig.getCmtPgnOffset());
		request.setAttribute("newRspInterval",
				commentConfig.getNewCmtInterval());
		UserConfig userConfig = service.getUserConfig();
		request.setAttribute("usersPerPage", userConfig.getUsrPgnOffset());
		request.setAttribute("registrationEnabled",
				userConfig.isRegistrationEnabled());
		request.setAttribute("avatarSize", userConfig.getAvatarSize());
		request.setAttribute("avatarHeight", userConfig.getAvatarHeight());
		request.setAttribute("avatarWidth", userConfig.getAvatarWidth());
		request.setAttribute("notifyUserOnNewRsp",
				userConfig.isNotifyAuthorOnNewCmt());
		request.setAttribute("requireAuthEmail",
				userConfig.isRequireNewUserAuthEmail());

		SystemContextConfig systemContextConfig = service
				.getSystemContextConfig();
		int sessionTiming = systemContextConfig.getSessionTiming();
		long loginSessionTiming = systemContextConfig.getLoginSessionTiming();
		request.setAttribute("sessionTiming", sessionTiming);
		request.setAttribute("loginSessionTimeout", loginSessionTiming);

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		EmailConfig emailConfig = service.getEmailConfig();
		request.setAttribute("globalConfig", config);
		request.setAttribute("emailConfig", emailConfig);

		ModerationConfig moderationConfig = service.getModerationConfig();
		request.setAttribute("mdrDigestionNum",
				moderationConfig.getDigestionNum());
		request.setAttribute("assignPendingLogInterval",
				moderationConfig.getAssignPendingLogInterval());

		return mapping.findForward("ToSystemConfigPage");
	}
}
