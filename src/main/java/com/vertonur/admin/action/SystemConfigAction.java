package com.vertonur.admin.action;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.admin.form.SystemConfigForm;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.context.SystemContextService;
import com.vertonur.dao.api.ConfigDAO;
import com.vertonur.dao.manager.DAOManager;
import com.vertonur.dms.ModerationService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.CommentConfig;
import com.vertonur.pojo.config.EmailConfig;
import com.vertonur.pojo.config.InfoConfig;
import com.vertonur.pojo.config.SystemContextConfig;
import com.vertonur.pojo.config.UserConfig;

public class SystemConfigAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		SystemConfigForm catedForm = (SystemConfigForm) form;
		String action = request.getParameter("action");
		if (action != null && action.equals("sendTestMail")) {
			sendTestMail(catedForm);
			return null;
		} else {
			int topicsPerPage = catedForm.getTopicsPerPage();
			int rspsPerPage = catedForm.getRspsPerPage();
			int usersPerPage = catedForm.getUsersPerPage();
			int hotTopicDef = catedForm.getHotTopicDef();
			int recentTopicPageNum = catedForm.getRecentTopicPageNum();
			long newCmtInterval = catedForm.getNewRspInterval();
			long newInfoInterval = catedForm.getNewTopicInterval();

			RuntimeParameterService service = SystemContextService.getService()
					.getDataManagementService(
							ServiceEnum.RUNTIME_PARAMETER_SERVICE);
			InfoConfig infoConfig = service.getInfoConfig();
			infoConfig.setInfoPgnOffset(topicsPerPage);
			infoConfig.setHottestInfoGeCmts(hotTopicDef);
			infoConfig.setRecentInfoPgnOffset(recentTopicPageNum);
			infoConfig.setNewInfoInterval(newInfoInterval);
			service.updateInfoConfig(infoConfig);

			CommentConfig commentConfig = service.getCommentConfig();
			commentConfig.setCmtPgnOffset(rspsPerPage);
			commentConfig.setNewCmtInterval(newCmtInterval);
			service.updateCommentConfig(commentConfig);

			boolean registrationEnabled = catedForm.isRegistrationEnabled();
			int avatarHeight = catedForm.getAvatarHeight();
			int avatarWidth = catedForm.getAvatarWidth();
			int avatarSize = catedForm.getAvatarSize();
			UserConfig userConfig = service.getUserConfig();
			userConfig.setUsrPgnOffset(usersPerPage);
			userConfig.setRequireNewUserAuthEmail(catedForm
					.isRequireAuthEmail());
			userConfig
					.setNotifyAuthorOnNewCmt(catedForm.isNotifyUserOnNewRsp());
			userConfig.setRegistrationEnabled(registrationEnabled);
			userConfig.setAvatarHeight(avatarHeight);
			userConfig.setAvatarWidth(avatarWidth);
			userConfig.setAvatarSize(avatarSize);
			service.updateUserConfig(userConfig);

			SystemContextConfig systemContextConfig = service
					.getSystemContextConfig();
			int sessionTimeout = catedForm.getSessionTimeout();
			systemContextConfig.setSessionTiming(sessionTimeout);
			systemContextConfig.setLoginSessionTiming(catedForm
					.getLoginSessionTimeout());
			service.updateSystemContextConfig(systemContextConfig);

			int mdrDigestionNum = catedForm.getMdrDigestionNum();
			SystemContextService systemContextService = SystemContextService
					.getService();
			ModerationService moderationService = systemContextService
					.getDataManagementService(ServiceEnum.MODERATION_SERVICE);
			moderationService.setDigestionNum(mdrDigestionNum);
			int assignPendingLogInterval = catedForm
					.getAssignPendingLogInterval();
			moderationService
					.setAssignPendingLogInterval(assignPendingLogInterval);

			processGlobalConfig(catedForm);
			processEmailConfig(catedForm);
			return mapping.findForward("ToSystemConfig");
		}
	}

	private void sendTestMail(SystemConfigForm form) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(form.getSmtpHost());
		email.addTo(form.getAddress(), form.getAddress());
		email.setFrom(form.getSender(), form.getSmtpUsername());
		boolean requireSSL = form.isRequireSSL();
		if (requireSSL)
			email.setSSL(requireSSL);

		if (form.isRequireAuth())
			email.setAuthentication(form.getSmtpUsername(), form.getSmtpPwd());
		email.setSubject("Forum test mail");
		email.setMsg("This is a test mail that send by admin to see if the config setting of a"
				+ " forum is correct,if mail is received, it's said that mail "
				+ "service in this forum is function well");
		email.send();
	}

	private void processGlobalConfig(SystemConfigForm form) throws IOException,
			URISyntaxException {
		String homepageLink = form.getHomepageLink();
		String forumName = form.getForumName();
		String pageTitle = form.getForumPageTitle();
		String forumDesc = form.getForumDesc();
		boolean downloadCaptchaEnabled = form.isDownloadCaptchaEnabled();
		boolean loginCaptchaEnabled = form.isLoginCaptchaEnabled();
		boolean registrationCaptchaEnabled = form.isRegistrationCaptchaEnabled();

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		config.setHomepageLink(homepageLink);
		config.setForumName(forumName);
		config.setForumPageTitle(pageTitle);
		config.setForumDescription(forumDesc);
		config.setDownloadCaptchaEnabled(downloadCaptchaEnabled);
		config.setLoginCaptchaEnabled(loginCaptchaEnabled);
		config.setRegistrationCaptchaEnabled(registrationCaptchaEnabled);

		SystemContextService systemContextService = SystemContextService
				.getService();
		DAOManager daoManager = systemContextService.getDaoManager();
		ConfigDAO<GlobalConfig, Integer> dao = daoManager
				.getExtendedConfigDAO(GlobalConfig.class);
		dao.updateConfig(config);
	}

	private void processEmailConfig(SystemConfigForm form) throws IOException,
			URISyntaxException {
		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		EmailConfig config = service.getEmailConfig();
		config.setSender(form.getSender());
		config.setSmtpHost(form.getSmtpHost());
		config.setSmtpPort(form.getSmtpPort());
		config.setRequireAuth(form.isRequireAuth());
		config.setRequireSSL(form.isRequireSSL());
		config.setSmtpUsername(form.getSmtpUsername());
		config.setSmtpPwd(form.getSmtpPwd());
		config.setEmailFormat(form.getEmailFormat());

		service.updateEmailConfig(config);
	}
}
