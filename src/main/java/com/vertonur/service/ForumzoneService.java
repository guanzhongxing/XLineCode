package com.vertonur.service;

import java.util.ArrayList;
import java.util.List;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.DepartmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.dms.exception.DeptModerationListNotEmptyException;
import com.vertonur.bean.Forumzone;
import com.vertonur.pojo.Department;

public class ForumzoneService {

	private DepartmentService service;

	public ForumzoneService() {
		service = SystemContextService.getService().getDataManagementService(
				ServiceEnum.DEPARTMENT_SERVICE);
	}

	public Forumzone getForumzoneById(int id) {
		return new Forumzone(service.getDepartmentById(id));
	}

	public Forumzone getForumzoneById(int id, boolean useCache) {
		return new Forumzone(service.getDepartmentById(id, useCache));
	}

	public boolean deleteForumzone(Forumzone forumzone) {
		return service.deleteDepartment(forumzone.getCore());
	}

	public void updateForumzone(Forumzone forumzone)
			throws DeptModerationListNotEmptyException,
			CategoryModerationListNotEmptyException {
		service.updateDepartment(forumzone.getCore());
	}

	public List<Forumzone> getForumzones() {
		List<Department> departments = service.getDepartments();
		List<Forumzone> forumzones = new ArrayList<Forumzone>();
		int size = departments.size();
		for (int i = 0; i < size; i++) {
			Forumzone forumzone = new Forumzone(departments.get(i));
			forumzones.add(forumzone);
		}
		return forumzones;
	}

	public Integer saveForumzone(Forumzone forumzone) {
		return service.saveDepartment(forumzone.getCore());
	}
}
