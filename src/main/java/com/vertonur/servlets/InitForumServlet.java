package com.vertonur.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dao.api.ConfigDAO;
import com.vertonur.dao.manager.DAOManager;
import com.vertonur.dms.constant.ServiceEnum;

//This class is used to set up initial status of a forum during starting up
public class InitForumServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		ServletContext context = getServletContext();
		String contextPath = context.getContextPath();
		context.setAttribute("contextPath", contextPath);
		context.setAttribute("resourcesHost", context.getInitParameter("resourcesHost"));

		WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		SystemContextService systemContextService = (SystemContextService) appContext
				.getBean("systemContextService");
		DAOManager manager = systemContextService.getDaoManager();

		manager.beginTransaction();
		ConfigDAO<GlobalConfig, Integer> globalConfigDao = manager
				.getExtendedConfigDAO(GlobalConfig.class);
		GlobalConfig globalConfig = globalConfigDao.getConfig();
		if (globalConfig == null) {
			globalConfig = (GlobalConfig) appContext.getBean("globalConfig");
			globalConfigDao.saveConfig(globalConfig);
		}
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setGlobalConfig(globalConfig);
		SystemConfig.setConfig(systemConfig);

		int sessionTiming = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getSystemContextConfig().getSessionTiming();
		context.setAttribute(Constants.SESSION_TIMING_KEY, sessionTiming);
		manager.commitTransaction();

		context.setAttribute("globalConfig", globalConfig);
	}

	public void destroy() {
	}
}
