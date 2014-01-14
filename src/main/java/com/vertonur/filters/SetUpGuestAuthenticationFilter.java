package com.vertonur.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.vertonur.context.SystemContextService;
import com.vertonur.dao.manager.DAOManager;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.User;
import com.vertonur.pojo.security.Group;
import com.vertonur.security.spring.PermissionAuthenticationToken;
import com.vertonur.security.util.PermissionUtils;

public class SetUpGuestAuthenticationFilter extends GenericFilterBean {

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SystemContextService service = SystemContextService.getService();
			GroupService groupService = service
					.getDataManagementService(ServiceEnum.GROUP_SERVICE);
			Group defaultGuestGroup = groupService.getGroupById(service
					.getDefaultGuestGroupId());
			DAOManager daoManager = service.getDaoManager();
			User guest = daoManager.getUserDAO().getUserById(
					service.getGuestId());

			PermissionAuthenticationToken result = PermissionUtils
					.generateGuestPermissionToken(defaultGuestGroup, guest);
			SecurityContextHolder.getContext().setAuthentication(result);
		}

		chain.doFilter(req, res);
	}
}
