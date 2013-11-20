package com.check.v3.service;


import java.util.List;

import com.check.v3.domain.User;
import com.check.v3.service.exception.UserAccountDuplicateException;

public interface UserService extends InstanceLoaderService{
	public User 		findById(Long id);
	public User 		findByAccount(String account);
	public List<User>	findAllByDepartmentId(Long departmentId);
	public User 		save(User user) throws UserAccountDuplicateException;
	public User 		createDepartmentAdmin(User user,Long department_id);
	public void delete(User user);

}
