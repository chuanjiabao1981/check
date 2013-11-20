package com.check.v3.security.util;

import java.util.Collection;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.check.v3.domain.Department;
import com.check.v3.domain.User;



public class SecurityTools {
	private static final Logger logger = LoggerFactory.getLogger(SecurityTools.class);

	public static String getEncryptPassword(String password)
	{
		RealmSecurityManager realmSecurityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		if (realmSecurityManager == null){
			logger.warn("RealmSecurityManager is not found!");
			return "";
		}
		Collection<Realm> realms = realmSecurityManager.getRealms();
		if (realms == null || realms.size() == 0){
			logger.warn("Realms is null!");
			return "";
		}
		AuthorizingRealm realm = (AuthorizingRealm) realms.iterator().next();
		PasswordMatcher	 passwordMatcher = (PasswordMatcher) realm.getCredentialsMatcher();
		return passwordMatcher.getPasswordService().encryptPassword(password);
				
	}
	public static Department getCurrentDepartment()
	{
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return user.getDepartment();
	}
}
