package com.check.v3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.domain.Department;

public interface CheckTemplateRepository extends CrudRepository<CheckTemplate,Long>{
	public final static String FIND_WITH_CHECKPOINTS_QUERY = "select c from CheckTemplate c " +
			   												 "left join fetch c.checkPoints " +
			   												 "where  c.id=:id"; 
	@Query(value=FIND_WITH_CHECKPOINTS_QUERY)
	CheckTemplate findByIdWithCheckPoints(@Param("id")Long id);
	List<CheckTemplate> findAllByDepartment(Department department);
}
