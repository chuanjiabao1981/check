package com.check.v3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.check.v3.domain.Organization;

public interface OrganizationRepository extends CrudRepository<Organization,Long>{
	
	public final static String FIND_WITH_USERS_QUERY = "select o from Organization o " +
													   "left join fetch o.users " +
													   "where  o.id=:id"; 

	public Organization findByName(String name);
	@Query(FIND_WITH_USERS_QUERY)
	public Organization findWithUsers(@Param("id")Long id);
}
