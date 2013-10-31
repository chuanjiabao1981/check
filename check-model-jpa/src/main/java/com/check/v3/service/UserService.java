package com.check.v3.service;


import com.check.v3.domain.User;
import com.check.v3.service.exception.UserAccountDuplicateException;

public interface UserService extends LoadInstanceService{
	public User findById(Long id);
	public User findByAccount(String account);
	public User save(User user) throws UserAccountDuplicateException;
	public void delete(User user);

}
