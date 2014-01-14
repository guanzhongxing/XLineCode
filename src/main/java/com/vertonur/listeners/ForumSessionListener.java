package com.vertonur.listeners;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;

public class ForumSessionListener implements HttpSessionListener {
	private static Logger logger = LoggerFactory
			.getLogger(ForumSessionListener.class.getCanonicalName());

	public void sessionCreated(HttpSessionEvent e) {
		HttpSession session = e.getSession();
		ServletContext context = session.getServletContext();
		session.setMaxInactiveInterval((Integer) context
				.getAttribute(Constants.SESSION_TIMING_KEY));

		if (logger.isTraceEnabled())
			logger.trace("A new http session created, total session num :"
					+ SystemContextService.getService().getState()
							.getOnlineUserNum() + "\n");
	}

	public void sessionDestroyed(HttpSessionEvent e) {
		if (logger.isTraceEnabled())
			logger.trace("Another http session timeout, total session left :"
					+ SystemContextService.getService().getState()
							.getOnlineUserNum() + "\n");
	}
}
