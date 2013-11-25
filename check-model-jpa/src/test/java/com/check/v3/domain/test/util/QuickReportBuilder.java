package com.check.v3.domain.test.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.User;
import com.check.v3.service.QuickReportService;

@Service("quickReportBuilderServiceTest")
public class QuickReportBuilder {
	@Resource
	QuickReportService quickReportService;

	
	public QuickReport build(User user,Organization org,String desc)
	{
		QuickReport quickReport = new QuickReport();
		quickReport.setSubmitter(user);
		quickReport.setDepartment(user.getDepartment());
		quickReport.setOrganization(org);
		quickReport.setDescription(desc);
		return quickReport;
	}
	
	public QuickReport create(User user,Organization org,String desc)
	{
		return quickReportService.save(build(user,org,desc));
	}

}
