package com.check.v3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.check.v3.domain.QuickReportResolve;

public interface QuickReportResolveRepository extends CrudRepository<QuickReportResolve,Long>{

	public final static String FIND_WITH_MEDIA = "select q from QuickReportResolve q " +
			 									 "left join fetch q.images " +
			 									 "where q.id=:id";
	@Query(FIND_WITH_MEDIA)
	QuickReportResolve findByIdWithMedia(@Param("id")Long id);
}
