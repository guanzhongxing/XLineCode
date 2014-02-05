/**
 * 
 */
package com.vertonur.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import com.YinXiangMa.YinXiangMa;
import com.vertonur.bean.Forum;
import com.vertonur.bean.Response;
import com.vertonur.bean.Topic;
import com.vertonur.bean.User;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.pojo.Category;
import com.vertonur.pojo.Comment;

/**
 * @author Vertonur This class is used to keep common code in a place
 */
public class ForumCommonUtil {

	// TODO: move bcs and yinxiangma config to xml
	// XLineCode的demo的百度云存储空间
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
