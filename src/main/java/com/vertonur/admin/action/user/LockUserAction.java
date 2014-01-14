package com.vertonur.admin.action.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.admin.form.UserAdminForm;
import com.vertonur.bean.User;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.service.UserService;

public class LockUserAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserAdminForm loginForm=(UserAdminForm)form;
		int[] ids = loginForm.getUserIds();
		UserService service=new UserService();
		for(int id:ids){
			User user = service.getUserById(id);
			if(user.isLocked())
				service.unLockUser(user);
			else	service.lockUser(user);
		}
		
		return mapping.findForward("AdminUserList");
	}
}
