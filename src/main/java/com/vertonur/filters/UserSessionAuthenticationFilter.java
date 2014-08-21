package com.vertonur.filters;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.User;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;

public class UserSessionAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private boolean postOnly = true;

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}

		String registrationMark = request.getParameter("registrationMark");
		GlobalConfig globalConfig = SystemConfig.getConfig().getGlobalConfig();
		if (registrationMark == null && globalConfig.isLoginCaptchaEnabled()) {
			// 验证码
			try {
				if (!"true".equals(ForumCommonUtil.checkCaptchaResult(request))) {
					request.setAttribute("wrongCaptcha", true);
					throw new AuthenticationServiceException(
							"Wrong captcha is inputted");
				}
			} catch (IOException e) {
				String msg = "Exception occurs when logging in with "
						+ SystemContextService.class.getName();
				logger.error(msg, e);

				request.setAttribute(e.getMessage(), true);
				throw new AuthenticationServiceException(msg, e);
			}
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		if (username == null || "".equals(username)) {
			request.setAttribute("invalidUsrId", true);
			throw new AuthenticationServiceException("User Id is required");
		}
		if (password == null || "".equals(password)) {
			request.setAttribute("invalidPwd", true);
			throw new AuthenticationServiceException("Password is required");
		}

		username = username.trim();
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User user = userService.getUserByEmail(username);
		if (user == null) {
			String msg = "Exception occurs when logging in with "
					+ SystemContextService.class.getName();

			request.setAttribute("userNotFound", true);
			throw new AuthenticationServiceException(msg);
		}
		username = String.valueOf(user.getId());
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		Authentication authentication = null;
		SavedRequestAwareAuthenticationSuccessHandler handler = (SavedRequestAwareAuthenticationSuccessHandler) getSuccessHandler();

		try {
			if (userSession.isGuest() == true) {
				authentication = SystemContextService.getService().login(
						userSession, authRequest);
				session.setMaxInactiveInterval(new Long(userSession
						.getValidPeriod()).intValue());
				if (registrationMark != null) {
					handler.setDefaultTargetUrl("/users/register/" + username);
				} else {
					String fromUrl = request.getParameter("fromUrl");
					if (fromUrl != null && !"".equals(fromUrl))
						handler.setDefaultTargetUrl(fromUrl);
				}
			} else
				authentication = userSession.getAuthentication();

		} catch (LoginException e) {
			String msg = "Exception occurs when logging in with "
					+ SystemContextService.class.getName();
			logger.error(msg, e);

			request.setAttribute(e.getMessage(), true);
			throw new AuthenticationServiceException(msg, e);
		}

		return authentication;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}
