package com.vertonur.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.admin.form.GroupConfigForm;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.pojo.security.Group;
import com.vertonur.pojo.security.Group.GroupType;

public class GroupUpdateAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		GroupConfigForm castedForm = (GroupConfigForm) form;
		String action = request.getParameter("action");
		if (action != null && "delete".equals(action)) {
			int[] ids = castedForm.getGroupIds();
			for (int id : ids)
				groupService.deleteGroup(id);

			return mapping.findForward("ToGroupList");
		}

		int groupId = castedForm.getGroupId();
		String name = castedForm.getGroupName();
		String description = castedForm.getGroupDesc().trim();
		int parentGroupId = castedForm.getParentGroupId();
		String groupType = castedForm.getGroupType();
		Group parentGroup = groupService.getGroupById(parentGroupId);
		Group group = groupService.getGroupById(groupId);
		if (group == null) {
			group = new Group();
			group.setName(name);
			group.setGroupType(GroupType.valueOf(groupType));
			group.setDescription(description);
			if (parentGroupId == 0) {
				group.setNestedLevel(0);
			} else {
				group.setParent(parentGroup);
				parentGroup.addSubGroup(group);
				group.setNestedLevel(parentGroup.getNestedLevel() + 1);
			}
			groupService.saveGroup(group);
		} else {
			group.setName(name);
			group.setDescription(description);
			group.setGroupType(GroupType.valueOf(groupType));
			Group originalParent = group.getParent();
			if (parentGroupId == 0) {
				if (originalParent != null)
					originalParent.removeSubGroup(group);
				group.setParent(null);
				group.setNestedLevel(0);
			} else {
				if (originalParent == null) {
					parentGroup.addSubGroup(group);
					group.setParent(parentGroup);
					group.setNestedLevel(parentGroup.getNestedLevel() + 1);
				} else if (originalParent != null
						&& originalParent.getId() != parentGroupId) {
					originalParent.removeSubGroup(group);
					parentGroup.addSubGroup(group);
					group.setParent(parentGroup);
					group.setNestedLevel(parentGroup.getNestedLevel() + 1);
				}
			}

			groupService.updateGroup(group);
		}

		return mapping.findForward("ToGroupList");
	}
}
