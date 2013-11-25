package com.check.v3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;

public interface QuickReportRepository extends CrudRepository<QuickReport,Long>{
	Page<QuickReport> findByOrganization(Organization organization,Pageable pageable);
}
