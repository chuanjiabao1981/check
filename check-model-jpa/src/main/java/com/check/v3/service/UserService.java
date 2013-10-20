package com.check.v3.service;


import com.check.v3.domain.User;

public interface UserService extends LoadInstanceService{
	public User findById(Long id);
	public User findByAccount(String account);
	public User save(User user);
	public void delete(User user);

}
