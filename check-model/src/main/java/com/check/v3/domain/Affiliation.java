package com.check.v3.domain;

import java.util.List;

import com.check.v3.domain.Organization;

public interface Affiliation {
	List<Organization> getBelongsToOrganizations();
}
