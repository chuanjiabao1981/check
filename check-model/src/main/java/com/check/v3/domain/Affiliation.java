package com.check.v3.domain;

import java.util.Set;

import com.check.v3.domain.Organization;

public interface Affiliation {
	Set<Organization> getBelongsToOrganizations();
}
