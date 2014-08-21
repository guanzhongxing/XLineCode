package com.vertonur.user.attm.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;

import com.vertonur.bean.User;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.pojo.AttachmentInfo.AttachmentType;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.user.topic.form.UserTopicForm;
import com.vertonur.util.ForumCommonUtil;

public class DownloadAttachmentAction extends MappingDispatchAction {

	@Deprecated
	public ActionForward initFileDownload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("requireCaptcha", true);
		return mapping.findForward("CaptchaPage");
	}

	@Deprecated
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GlobalConfig globalConfig = SystemConfig.getConfig().getGlobalConfig();
		if (globalConfig.isDownloadCaptchaEnabled()) {
			if (!"true".equals(ForumCommonUtil.checkCaptchaResult(request))) {
				request.setAttribute("wrongCaptcha", true);
				request.setAttribute("requireCaptcha", true);
				return mapping.getInputForward();
			}
		}

		AttachmentService attachmentService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		String attmId = request.getParameter("attmId");
		String downloadUrl = attachmentService.downloadAttachment(Integer
				.valueOf(attmId));

		response.sendRedirect(downloadUrl);

		return null;
	}

	@Deprecated
	public ActionForward uploadEmbeddedImage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserTopicForm castedForm = (UserTopicForm) form;
		FormFile uploadedFile = castedForm.getUpload();
		if (!uploadedFile.getContentType().startsWith("image")) {
			request.setAttribute("isImage", false);
		} else {
			HttpSession session = request.getSession(false);
			UserSession userSession = (UserSession) session
					.getAttribute(Constants.USER_SESSION);
			UserService userService = new UserService();
			User user = userService.getUserById(userSession.getUserId());
			try {
				AttachmentService attachmentService = SystemContextService
						.getService().getDataManagementService(
								ServiceEnum.ATTACHMENT_SERVICE);
				Attachment attm = attachmentService.uploadInfoEmbededImage(
						uploadedFile.getInputStream(),
						uploadedFile.getContentType(),
						uploadedFile.getFileName(),
						new Long(uploadedFile.getFileSize()).longValue(),
						user.getCore());

				request.setAttribute("isImage", true);
				request.setAttribute("attachmentId", attm.getId());
				AttachmentInfo attmInfo = attm.getAttmInfo();
				AttachmentType type = attmInfo.getAttachmentType();
				if (AttachmentType.BCS.equals(type))
					request.setAttribute("url", attmInfo.getDownloadUrl());
				else {
					String contextPath = (String) this.getServlet()
							.getServletContext().getAttribute("contextPath");
					request.setAttribute("url", contextPath
							+ "/do/local/image?id=" + attm.getId());
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.setAttribute("CKEditorFuncNum", castedForm.getCKEditorFuncNum());
		return mapping.findForward("CkEditorCallbackPage");
	}

	public ActionForward showImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		int attmId = Integer.parseInt(request.getParameter("id"));
		downloadLocalFile(attmId, response);
		return null;
	}

	/**
	 * @param attm
	 * @param response
	 */
	public void downloadLocalFile(int attmId, HttpServletResponse response) {
		AttachmentService attachmentService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		Attachment attm = attachmentService.getAttmById(attmId);
		AttachmentInfo info = attm.getAttmInfo();
		response.setContentType(attm.getAttmInfo().getMimeType());
		response.setHeader("Content-Disposition", "attachment; filename="
				+ ForumCommonUtil.toUtf8String(info.getFileName()) + ";");
		response.setContentLength((int) attm.getAttmInfo().getFilesize());

		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(info.getFilePath());
			os = response.getOutputStream();

			int c;
			byte[] b = new byte[4096];
			while ((c = fis.read(b)) != -1) {
				os.write(b, 0, c);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}

			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
