package com.check.v3.service.jpa;

import javax.annotation.Resource;


import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.QuickReportState;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.repository.QuickReportResolveRepository;
import com.check.v3.service.QuickReportResolveService;

public class QuickReportResolveServiceImpl implements QuickReportResolveService{

	@Resource
	private QuickReportResolveRepository quickReportResolveRepository;
	@Resource
	private QuickReportRepository quickReportReporsitory;

	@Override
	public QuickReportResolve save(QuickReportResolve quickReportResolve) {
		QuickReport q = quickReportResolve.getQuickReport();
		if (q.getState() == QuickReportState.OPENED){
			q.setState(QuickReportState.CLOSED);
			quickReportReporsitory.save(q);
		}
		return quickReportResolveRepository.save(quickReportResolve);
	}


}
