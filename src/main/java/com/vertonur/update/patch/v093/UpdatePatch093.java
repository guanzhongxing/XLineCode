package com.vertonur.update.patch.v093;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.pojo.AttachmentInfo.AttachmentType;
import com.vertonur.pojo.User;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.session.UserSession;

public class UpdatePatch093 {
	private static SystemContextService service;

	public static void init() throws Exception {
		new ClassPathXmlApplicationContext(
				"com/vertonur/spring/info-web-beans.xml");
		service = SystemContextService.getService();
	}

	public static void main(String[] args) throws Exception {
		init();
		service.beginTransaction();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				"1", "2296867");
		UserSession session = service.getNewUserSession(
				Locale.CHINESE.toString(), "127.0.0.1");
		Authentication result = service.login(session, token);
		SecurityContextHolder.getContext().setAuthentication(result);

		UserService userService = service
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		long num = userService.getUserNum();
		UserConfig userConfig = service.getDataManagementService(
				ServiceEnum.RUNTIME_PARAMETER_SERVICE).getUserConfig();
		userConfig.setUsrPgnOffset(new Long(num).intValue());
		List<User> users = userService.getUsers(0);
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			AttachmentInfo info = new AttachmentInfo();
			info.setDownloadCount(0);
			info.setFilesize(4345);
			info.setMimeType("image/jpeg");
			info.setHasThumb(false);
			info.setPhysicalFilename("upload/avatar/20131027/defaultAvatar.jpeg");
			info.setRealFilename("defaultAvatar.jpeg");
			info.setUploadTime(new Date());
			info.setUploadTimeInMillis(0);
			info.setAttachmentType(AttachmentType.BCS);
			info.setDownloadUrl("http://bcs.duapp.com/xlinecode%2Fupload%2Favatar%2F20131027%2FdefaultAvatar.jpeg?sign=MBO%3ACkBc7E3REQ14wsHBkOKiFQWF%3Am3Ngv0zSmLQ9GpDaNqb5x%2BFvh00%3D");
			info.setUploadConfirmed(true);

			Attachment attachment = new Attachment();
			attachment.setAttmInfo(info);
			attachment.setUploader(user);
			user.setAvatar(attachment);
			userService.updateUser(user);

		}
		service.commitTransaction();
	}
}
