package com.check.v3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.check.v3.domain.User;

public interface UserRepository extends CrudRepository<User,Long>{
	public static final String FIND_BY_ID_WITH_ORGANIZATION_AND_CHECK_TEMPLATE=
			"select u from User u " +
			"left join fetch u.organizations o " +
		    "left join fetch o.users " +
			"left join fetch o.checkTemplates c " +
		    "left join fetch c.checkPoints "+
			"where u.id = :id";
	public User findByAccount(String account);
	@Query(FIND_BY_ID_WITH_ORGANIZATION_AND_CHECK_TEMPLATE)
	public User findByIdWithOrganizationsAndCheckTemplates(@Param("id")Long id);
}
