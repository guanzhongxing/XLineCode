/**
 * 
 */
package com.vertonur.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.upload.FormFile;
import org.jsoup.Jsoup;

import com.YinXiangMa.YinXiangMa;
import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.vertonur.bean.Forum;
import com.vertonur.bean.Info;
import com.vertonur.bean.Response;
import com.vertonur.bean.Topic;
import com.vertonur.bean.User;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.RuntimeConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.pojo.AttachmentInfo.AttachmentType;
import com.vertonur.pojo.Category;
import com.vertonur.pojo.Comment;
import com.vertonur.pojo.config.AttachmentConfig;

/**
 * @author Vertonur This class is used to keep common code in a place
 */
public class ForumCommonUtil {

	// TODO: move bcs and yinxiangma config to xml
	//XLineCode的demo的百度云存储空间
	public static String HOST = "bcs.duapp.com";
	public static String ACCESS_KEY = "6lHNnwyPTevGeNOLOzsOQ8GR";
	public static String SECRET_KEY = "IUnniM8ibA0iUCz2273Q6vAwj6OsUkBs ";
	public static String BUCKET = "xlinecode-demo";

	public static enum PageType {
		HOME_PAGE, INFO_PAGE
	}

	public static String checkCaptchaResult(HttpServletRequest request)
			throws IOException {

		return YinXiangMa.Check_Answer("你的印象码的key",
				request.getParameter("YinXiangMa_challenge"),
				request.getParameter("YXM_level"),
				request.getParameter("YXM_input_result"));
	}

	public static void setPageSeo(HttpServletRequest request, String title,
			String desc, PageType pageType) {
		GlobalConfig globalConfig = SystemConfig.getConfig().getGlobalConfig();
		if (pageType.equals(PageType.INFO_PAGE)) {
			title += " -- " + globalConfig.getForumPageTitle();
			desc = Jsoup.parse(desc).text();
			desc = StringUtils.substring(desc, 0,
					globalConfig.getMetaDescLength())
					+ "...";
		} else {
			title = globalConfig.getForumPageTitle();
			desc = globalConfig.getForumDescription();
		}
		request.setAttribute("title", title);
		request.setAttribute("description", desc);
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((c >= 0) && (c <= 255))
				sb.append(c);
			else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}

				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append('%').append(Integer.toHexString(k).toUpperCase());
				}
			}
		}

		return sb.toString();
	}

//	public static Attachment uploadAttchment(FormFile uploadedFile,
//			String attmComment, ServletContext context, User theCurrentUser,
//			Info info) throws Exception {
//
//		AttachmentConfig attachmentConfig = SystemContextService
//				.getService()
//				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
//				.getAttachmentConfig();
//		int maxAttmSize = (int) attachmentConfig.getMaxSize();
//		if (uploadedFile != null && uploadedFile.getFileSize() > 0
//				&& uploadedFile.getFileSize() <= maxAttmSize) {
//			String mimeType = uploadedFile.getContentType();
//			String mainTpye = mimeType.split("/")[0];
//
//			Calendar uploadDate = Calendar.getInstance();
//			String realFileName = uploadedFile.getFileName() + "_";
//
//			String tmp = mainTpye + "/" + uploadDate.get(Calendar.YEAR) + "/"
//					+ uploadDate.get(Calendar.MONTH) + "/"
//					+ uploadDate.get(Calendar.DAY_OF_MONTH);
//			String phisicalPath = context.getRealPath(SystemConfig.getConfig()
//					.getRuntimeConfig().getUploadRootFolder()
//					+ "/" + tmp);
//			File diskFile = new File(phisicalPath);
//			diskFile.mkdirs();
//
//			phisicalPath = phisicalPath + File.separator + realFileName;
//			File file = new File(phisicalPath);
//			while (file.exists()) {
//				int second = Calendar.SECOND;
//				int milisecond = Calendar.MILLISECOND;
//				phisicalPath = phisicalPath + second + milisecond;
//				realFileName = realFileName + second + milisecond;
//				file = new File(phisicalPath);
//			}
//			OutputStream outputStream = new FileOutputStream(phisicalPath);
//			InputStream inputStream = uploadedFile.getInputStream();
//			byte[] buffer = new byte[32768];
//			int n = 0;
//			Date startTime = new Date();
//			while ((n = inputStream.read(buffer)) != -1) {
//				outputStream.write(buffer, 0, n);
//			}
//
//			Date endTime = new Date();
//			long elapsedTime = endTime.getTime() - startTime.getTime();
//			inputStream.close();
//			outputStream.flush();
//			outputStream.close();
//
//			AttachmentInfo attmInfo = new AttachmentInfo();
//			attmInfo.setAttachmentType(AttachmentType.LOCAL);
//			attmInfo.setMimeType(mimeType);
//			attmInfo.setFilesize(uploadedFile.getFileSize());
//			attmInfo.setRealFilename(realFileName);
//			attmInfo.setPhysicalFilename(tmp + "/" + realFileName);
//			attmInfo.setUploadTime(uploadDate.getTime());
//			attmInfo.setComment(attmComment);
//			attmInfo.setUploadTimeInMillis(elapsedTime);
//			if (mainTpye.equals("image")) {
//				attmInfo.setHasThumb(true);
//				String extension = uploadedFile.getFileName().split("\\.")[1];
//				UploadedImageHandler.upload(extension,
//						uploadedFile.getInputStream(), phisicalPath
//								+ "",
//						attachmentConfig.getThumbWidth(),
//						attachmentConfig.getThumbHeight());
//			}
//			uploadedFile.destroy();
//
//			Attachment attm = new Attachment();
//			attm.setUploader(theCurrentUser.getCore());
//			attm.setAttmHolder(info.getCore());
//			attmInfo.setAttm(attm);
//			AttachmentService attachmentService = SystemContextService
//					.getService().getDataManagementService(
//							ServiceEnum.ATTACHMENT_SERVICE);
//			attachmentService.saveAttachmentInfo(attmInfo);
//
//			attm.setAttmInfo(attmInfo);
//			return attm;
//		}
//
//		return null;
//	}

	/**
	 * 将文件上传到百度云存储
	 * 
	 * @param uploadedFile
	 * @param attmComment
	 * @param context
	 * @param theCurrentUser
	 * @param info
	 * @return
	 * @throws Exception
	 */
//	public static Attachment uploadBcsAttchment(FormFile uploadedFile,
//			String attmComment, ServletContext context, User theCurrentUser)
//			throws Exception {
//
//		AttachmentConfig attachmentConfig = SystemContextService
//				.getService()
//				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
//				.getAttachmentConfig();
//		int maxAttmSize = (int) attachmentConfig.getMaxSize();
//		if (uploadedFile != null && uploadedFile.getFileSize() > 0
//				&& uploadedFile.getFileSize() <= maxAttmSize) {
//			String mimeType = uploadedFile.getContentType();
//			String mainTpye = mimeType.split("/")[0];
//
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//			Date uploadDate = new Date();
//			String realFileName = RandomStringUtils.randomAlphabetic(6) + "_"
//					+ uploadedFile.getFileName();
//
//			String tmp = mainTpye + "/" + format.format(uploadDate);
//			String phisicalPath = SystemConfig.getConfig().getRuntimeConfig()
//					.getUploadRootFolder()
//					+ "/" + tmp;
//
//			phisicalPath = phisicalPath + "/" + realFileName;
//			Date startTime = new Date();
//			String downloadableUrl = uploadBcsObject(uploadedFile, phisicalPath);
//			Date endTime = new Date();
//			long elapsedTime = endTime.getTime() - startTime.getTime();
//
//			AttachmentInfo attmInfo = new AttachmentInfo();
//			attmInfo.setAttachmentType(AttachmentType.BCS);
//			attmInfo.setMimeType(mimeType);
//			attmInfo.setFilesize(uploadedFile.getFileSize());
//			attmInfo.setRealFilename(realFileName);
//			attmInfo.setPhysicalFilename(phisicalPath);
//			attmInfo.setDownloadUrl(downloadableUrl);
//			attmInfo.setUploadTime(uploadDate);
//			attmInfo.setComment(attmComment);
//			attmInfo.setUploadTimeInMillis(elapsedTime);
//			uploadedFile.destroy();
//
//			Attachment attm = new Attachment();
//			attm.setUploader(theCurrentUser.getCore());
//			AttachmentService attachmentService = SystemContextService
//					.getService().getDataManagementService(
//							ServiceEnum.ATTACHMENT_SERVICE);
//			attm.setAttmInfo(attmInfo);
//
//			return attm;
//		}
//
//		return null;
//	}

//	public static String uploadBcsObject(FormFile uploadedFile,
//			String phisicalPath) throws FileNotFoundException, IOException {
//		BCSCredentials credentials = new BCSCredentials(ACCESS_KEY, SECRET_KEY);
//		BaiduBCS baiduBCS = new BaiduBCS(credentials, HOST);
//		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
//		ObjectMetadata objectMetadata = new ObjectMetadata();
//		objectMetadata.setContentType(uploadedFile.getContentType());
//		objectMetadata.setContentLength(uploadedFile.getFileSize());
//
//		PutObjectRequest request = new PutObjectRequest(BUCKET, phisicalPath,
//				uploadedFile.getInputStream(), objectMetadata);
//		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
//		response.getResult();
//
//		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(
//				HttpMethodName.GET, BUCKET, phisicalPath);
//		String downloadableUrl = baiduBCS.generateUrl(generateUrlRequest);
//
//		return downloadableUrl;
//	}

	public static int strToIntTransitionBuffer(String value) {
		if (value != null && !value.equals(""))
			return new Integer(value);
		else
			return 0;
	}

	public static int caculateTotalPageSize(int paginationSize,
			int paginationOffset) {
		int totalPages;
		if (paginationSize % paginationOffset != 0)
			totalPages = paginationSize / paginationOffset + 1;
		else
			totalPages = paginationSize / paginationOffset;

		return totalPages;
	}

	public static boolean deleteAvatar(ServletContext context, String avatarName) {
		String avatarPath = context.getRealPath(File.separator
				+ SystemConfig.getConfig().getRuntimeConfig()
						.getUploadRootFolder() + File.separator + "avatar"
				+ avatarName);
		File avatar = new File(avatarPath);
		return avatar.delete();
	}

	public static void deleteAttachment(ServletContext context,
			AttachmentInfo attachmentInfo) {
		RuntimeConfig config = SystemConfig.getConfig().getRuntimeConfig();
		String attachmentPath = context.getRealPath(File.separator
				+ config.getUploadRootFolder() + File.separator
				+ attachmentInfo.getPhysicalFilename());
		File attachment = new File(attachmentPath);
		attachment.delete();

		String mainTpye = attachmentInfo.getMimeType().split("/")[0];
		if (mainTpye.equals("image")) {
			String thumbPath = context.getRealPath(File.separator
					+ config.getUploadRootFolder() + File.separator
					+ attachmentInfo.getPhysicalFilename()
					+ "");
			File thumb = new File(thumbPath);
			thumb.delete();
		}
	}

	public static void deleteBcsAttachment(AttachmentInfo attachmentInfo) {
		BCSCredentials credentials = new BCSCredentials(ACCESS_KEY, SECRET_KEY);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, HOST);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8

		baiduBCS.deleteObject(BUCKET, attachmentInfo.getPhysicalFilename());
		AttachmentService attachmentService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		attachmentService.deleteAttachment(attachmentInfo.getAttm());
	}

	public static void deleteBcsObject(String filePath) {
		BCSCredentials credentials = new BCSCredentials(ACCESS_KEY, SECRET_KEY);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, HOST);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8

		baiduBCS.deleteObject(BUCKET, filePath);
	}

	public static List<Topic> convertInfosToTopics(
			List<? extends com.vertonur.pojo.AbstractInfo> infos) {
		List<Topic> topics = new ArrayList<Topic>();
		int size = infos.size();
		for (int i = 0; i < size; i++) {
			Topic topic = new Topic((com.vertonur.pojo.Info) infos.get(i));
			topics.add(topic);
		}
		return topics;
	}

	public static List<Response> convertCommentsToResponses(
			List<? extends com.vertonur.pojo.AbstractInfo> comments) {
		List<Response> responses = new ArrayList<Response>();
		int size = comments.size();
		for (int i = 0; i < size; i++) {
			Response response = new Response((Comment) comments.get(i));
			responses.add(response);
		}
		return responses;
	}

	public static List<User> convertCoreUsersToUsers(
			List<com.vertonur.pojo.User> coreUsers) {
		List<User> users = new ArrayList<User>();
		for (com.vertonur.pojo.User user : coreUsers)
			users.add(new User(user));

		return users;
	}

	public static List<Forum> convertCategoriesToForums(
			List<Category> categories) {
		int size = categories.size();
		List<Forum> forums = new ArrayList<Forum>();
		for (int i = 0; i < size; i++) {
			Forum forum = new Forum(categories.get(i));
			forums.add(forum);
		}
		return forums;
	}
}
