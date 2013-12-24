package com.check.v3.domain.util;

import java.util.Comparator;

import com.check.v3.domain.BaseEntity;


public class BaseEntityComparer implements Comparator<BaseEntity>{

	
	@Override
	public int compare(BaseEntity o1, BaseEntity o2) {
		if (o2 == null || o2.getId() == null){
			return -1;
		}

		if (o1 == null || o1.getId() == null){
			return 1;
		}
		return Long.compare(o1.getId(), o2.getId());
	}

}
