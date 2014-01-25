package com.vertonur.user.attm.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
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
import com.vertonur.bean.config.RuntimeConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo.AttachmentType;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.user.topic.form.UserTopicForm;
import com.vertonur.util.ForumCommonUtil;

public class DownloadAttachmentAction extends MappingDispatchAction {
	public ActionForward initFileDownload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("requireCaptcha", true);
		return mapping.findForward("CaptchaPage");
	}

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
				RuntimeConfig config = SystemConfig.getConfig()
						.getRuntimeConfig();
				Attachment attm = attachmentService.uploadInfoEmbededImage(AttachmentType.BCS,
						uploadedFile.getInputStream(),
						uploadedFile.getContentType(),
						config.getUploadRootFolder(),
						uploadedFile.getFileName(),
						new Long(uploadedFile.getFileSize()).longValue(),
						user.getCore());

				request.setAttribute("isImage", true);
				request.setAttribute("attachmentId", attm.getId());
				request.setAttribute("url", attm.getAttmInfo().getDownloadUrl());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.setAttribute("CKEditorFuncNum", castedForm.getCKEditorFuncNum());
		return mapping.findForward("CkEditorCallbackPage");
	}

	/**
	 * 转换为使用BCS的形式
	 * 
	 * @param attm
	 * @param response
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void downloadLocalFile(Attachment attm, HttpServletResponse response) {
		response.setContentType(attm.getAttmInfo().getMimeType());
		response.setHeader(
				"Content-Disposition",
				"attachment; filename="
						+ ForumCommonUtil.toUtf8String(attm.getAttmInfo()
								.getRealFilename()) + ";");
		response.setContentLength((int) attm.getAttmInfo().getFilesize());

		FileInputStream fis = null;
		OutputStream os = null;
		try {
			String filename = SystemConfig.getConfig().getRuntimeConfig()
					.getUploadRootFolder()
					+ "/" + attm.getAttmInfo().getPhysicalFilename();
			ServletContext context = servlet.getServletContext();
			filename = context.getRealPath(filename);
			fis = new FileInputStream(filename);
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
