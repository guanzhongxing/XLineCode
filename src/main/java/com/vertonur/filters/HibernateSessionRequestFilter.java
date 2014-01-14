/**
 * 
 */
package com.vertonur.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.StaleObjectStateException;

import com.vertonur.context.SystemContextService;
import com.vertonur.security.exception.InsufficientPermissionException;

/**
 * @author Vertonur
 * 
 */
public class HibernateSessionRequestFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		SystemContextService service = SystemContextService.getService();
		try {
			if (service.isTransactionSupported() == true)
				service.beginTransaction();

			// Call the next filter (continue request processing)
			chain.doFilter(request, response);

			// Commit and cleanup
			// log.debug("Committing the database transaction");
			if (service.isTransactionSupported() == true
					&& service.isTransactionAlive())
				service.commitTransaction();

		} catch (StaleObjectStateException staleEx) {
			// log.error("This interceptor does not implement optimistic concurrency control!");
			// log.error("Your application will not work until vertonur add compensation actions!");
			// Rollback, close everything, possibly compensate for any permanent
			// changes
			// during the conversation, and finally restart business
			// conversation. Maybe
			// give the user of the application a chance to merge some of his
			// work with
			// fresh data... what vertonur do here depends on vertonurr
			// applications
			// design.
			throw staleEx;
		} catch (Throwable ex) {
			// Rollback only
			ex.printStackTrace();
			try {
				if (service.isTransactionSupported() == true
						&& service.isTransactionAlive()) {
					// log.debug("Trying to rollback database transaction after exception");
					service.rollbackTransaction();
				}
			} catch (Throwable rbEx) {
				// log.error("Could not rollback transaction after exception!",
				// rbEx);
			}

			if (ex.getCause() instanceof InsufficientPermissionException) {
				request.setAttribute("insufficientPermission", true);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/templates/default/user/message.jsp");
				dispatcher.forward(request, response);
			} else
				throw new ServletException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {

	}
}
