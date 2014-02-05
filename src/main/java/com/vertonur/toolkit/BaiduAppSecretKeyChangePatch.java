package com.vertonur.toolkit;

import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;

/**
 * 该类用于在百度云存储的SecretKey被用户重置后对BCS类型的AttachmentInfo的downloadUrl数据根据新的SecretKey重新生成公有链接
 * 。注：重置后的SecretKey新在系统中重现配置以对该类其作用。
 * 
 * @author Vertonur
 * 
 */
public class BaiduAppSecretKeyChangePatch {
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

		AttachmentService attachmentervice = service
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		List<Attachment> attms = attachmentervice.getBcsAttms();
		for (Attachment attm : attms) {
			AttachmentInfo attmInfo = attm.getAttmInfo();
			String filePath = attmInfo.getFilePath();
			BCSCredentials credentials = new BCSCredentials(
					ForumCommonUtil.ACCESS_KEY, ForumCommonUtil.SECRET_KEY);
			BaiduBCS baiduBCS = new BaiduBCS(credentials, ForumCommonUtil.HOST);
			baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
			GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(
					HttpMethodName.GET, ForumCommonUtil.BUCKET, filePath);
			String downloadableUrl = baiduBCS.generateUrl(generateUrlRequest);
			attmInfo.setDownloadUrl(downloadableUrl);
		}
		service.commitTransaction();
		System.exit(0);
	}
}
