package com.vertonur.spring.service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.security.spring.PermissionUser;
import com.vertonur.security.spring.RememberMePermissionAuthenticationToken;
import com.vertonur.session.UserSession;

public class InfoTokenBasedRememberMeServices extends
		TokenBasedRememberMeServices {

	/**
	 * Reserved for Spring DI usage
	 */
	@Deprecated
	public InfoTokenBasedRememberMeServices() {
	}

	public InfoTokenBasedRememberMeServices(String key,
			UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	protected Authentication createSuccessfulAuthentication(
			HttpServletRequest request, UserDetails user) {

		PermissionUser permissionUser = (PermissionUser) user;
		RememberMePermissionAuthenticationToken auth = new RememberMePermissionAuthenticationToken(
				getKey(), Integer.parseInt(user.getUsername()),
				user.getAuthorities(), permissionUser.getPermissions());
		auth.setDetails(getAuthenticationDetailsSource().buildDetails(request));

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		try {
			SystemContextService.getService().autoLogin(userSession,
					Integer.parseInt(user.getUsername()), user.getPassword());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return auth;
	}
}
