/**
 * 
 */
package com.vertonur.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;

import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.session.UserSession;

/**
 * @author Vertonur
 * 
 */
public class UserSessionFilter extends GenericFilterBean {
	private static Logger logger = LoggerFactory
			.getLogger(UserSessionFilter.class.getCanonicalName());

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String uri = httpServletRequest.getRequestURI();
		if (!uri.contains("user/filter_logout")) {
			HttpSession session = httpServletRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute(Constants.USER_SESSION);
			if (userSession == null)
				generateNewSession(session, request);
			else {
				userSession.activateSession();
				if (userSession.isInvalid() == true) {
					SecurityContext context = (SecurityContext) session
							.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
					context.setAuthentication(null);
					logger.trace("UserSession is not null but is invalid,name:["
							+ userSession.getUsername()
							+ "],id:["
							+ userSession.getUserId()
							+ "],remove spring session data.\n");
					userSession = generateNewSession(session, request);
				}
			}
		}

		chain.doFilter(request, response);
	}

	private UserSession generateNewSession(HttpSession session,
			ServletRequest request) {
		SystemContextService service = SystemContextService.getService();
		UserSession newSession = service.getNewUserSession(request.getLocale()
				.toString(), request.getRemoteAddr());
		session.setAttribute(Constants.USER_SESSION, newSession);

		logger.trace("New UserSession generated.\n");
		return newSession;
	}
}
