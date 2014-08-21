package com.vertonur.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
import com.vertonur.util.ForumCommonUtil;

@Controller
@RequestMapping(value = "/attachments")
public class AttachmentController {

	@RequestMapping(value = "/{attachmentId}")
	public String downloadAttachment(@PathVariable int attachmentId,
			HttpServletRequest request) throws IOException {

		GlobalConfig globalConfig = SystemConfig.getConfig().getGlobalConfig();
		if (globalConfig.isDownloadCaptchaEnabled())
			if (!"true".equals(ForumCommonUtil.checkCaptchaResult(request))) {
				request.setAttribute("wrongCaptcha", true);
				request.setAttribute("requireCaptcha", true);
				return "default/user/message";
			}

		AttachmentService attachmentService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		String downloadUrl = attachmentService.downloadAttachment(attachmentId);

		return "redirect:" + downloadUrl;
	}

	@RequestMapping(value = "/{attachmentId}/captcha")
	public String showDownloadAttachmentCaptcha(@PathVariable int attachmentId,
			HttpServletRequest request) throws IOException {

		request.setAttribute("attachmentId", attachmentId);
		request.setAttribute("requireCaptcha", true);
		return "default/user/message";
	}

	@RequestMapping(value = "/image")
	public String uploadEmbeddedImage(@RequestParam MultipartFile upload,
			@RequestParam String CKEditorFuncNum, HttpServletRequest request)
			throws IOException {

		if (!upload.getContentType().startsWith("image")) {
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
						upload.getInputStream(), upload.getContentType(),
						upload.getOriginalFilename(),
						new Long(upload.getSize()).longValue(), user.getCore());

				request.setAttribute("isImage", true);
				request.setAttribute("attachmentId", attm.getId());
				AttachmentInfo attmInfo = attm.getAttmInfo();
				AttachmentType type = attmInfo.getAttachmentType();
				if (AttachmentType.BCS.equals(type))
					request.setAttribute("url", attmInfo.getDownloadUrl());
				else {
					String contextPath = (String) request.getServletContext()
							.getAttribute("contextPath");
					request.setAttribute("url", contextPath
							+ "/do/local/image?id=" + attm.getId());
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.setAttribute("CKEditorFuncNum", CKEditorFuncNum);
		return "default/common/ckEditor_callback";
	}
}
