package com.vertonur.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import com.vertonur.context.SystemContextService;

public class RollbackTransactionAccessDeniedHandler extends
		AccessDeniedHandlerImpl {

	private String backendFullAuthToken;

	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		SystemContextService service = SystemContextService.getService();
		try {
			if (service.isTransactionSupported() == true
					&& service.isTransactionAlive()) {
				// log.debug("Trying to rollback database transaction after exception");
				service.rollbackTransaction();
			}

			request.setAttribute("accessDenied", true);
			String uri = request.getRequestURI();
			if (uri.contains(backendFullAuthToken))
				request.setAttribute("fullAuthRequired", true);
		} catch (Throwable rbEx) {
			// log.error("Could not rollback transaction after exception!",
			// rbEx);
		}

		super.handle(request, response, accessDeniedException);
	}

	public void setBackendFullAuthToken(String backendFullAuthToken) {
		this.backendFullAuthToken = backendFullAuthToken;
	}
}
