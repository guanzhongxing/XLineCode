package com.vertonur.user.registration.action;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.util.TxtFileReader;

public class ShowAgreementAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse arg3)
			throws Exception {

		UserConfig config = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getUserConfig();
		boolean registrationEnabled = config.isRegistrationEnabled();
		if (registrationEnabled) {
			request.setAttribute("registrationEnabled", registrationEnabled);

			URL filePath = servlet.getServletContext().getResource(
					"/templates/common/txt/terms_zh_CN.txt");
			String content = TxtFileReader.readTxtFile(filePath.openStream());
			request.setAttribute("agreementContent", content);
			String fromAdmin = request.getParameter("fromAdmin");
			if ("true".equals(fromAdmin))
				request.setAttribute("fromAdmin", true);
		}

		return mapping.findForward("ShowAgreement");
	}
}
