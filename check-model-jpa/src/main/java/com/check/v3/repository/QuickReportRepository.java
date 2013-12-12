package com.check.v3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;

public interface QuickReportRepository extends CrudRepository<QuickReport,Long>{
	public final static String FIND_WITH_MEDIA = "select q from QuickReport q " +
												 "left join fetch q.images " +
												 "where q.id=:id";
	
	public final static String FIND_WITH_MEDIA_AND_RESOLVE = "select q from QuickReport q " +
															 "left join fetch q.images "    +
															 "left join fetch q.resolves r "  +
															 "left join fetch r.images " +
															 "where q.id=:id";

	public final static String FIND_ALL_BY_ORGANIZATION_WITH_MEDIA = "select q from QuickReport q " +
																	 "left join fetch q.images " +
																	 "where q.organization.id = :organizationId";
	@Query(FIND_ALL_BY_ORGANIZATION_WITH_MEDIA)
	Page<QuickReport> findAllByOrganizationWithMedia(@Param("organizationId") Long organizationId,Pageable pageable);
	Page<QuickReport> findByOrganization(Organization organization,Pageable pageable);
	@Query(FIND_WITH_MEDIA)
	QuickReport findByIdWithMedia(@Param("id")Long id);
	@Query(FIND_WITH_MEDIA_AND_RESOLVE)
	QuickReport findByIdWithMediaAndResolve(@Param("id")Long id);

}
