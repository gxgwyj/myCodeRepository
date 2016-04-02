package com.junyang.security.organization.service;

import java.util.List;

import com.junyang.common.model.page.Page;
import com.junyang.security.organization.model.Organization;
import com.junyang.security.organization.vo.OrganizationVo;
import com.junyang.security.organization.vo.QueryOrganizationVo;

public interface OrganizationService {
	public void saveOrupdateOrg(Organization organization);
	public void changeOrganization (Organization organization);
	public List<Organization> findOrganizationList();
	public void removeOrganizations(String[] ids);
	public Organization getOrganization(String id);
	public OrganizationVo getOrganizationVo(String id);
	public List<OrganizationVo> findOrganizationPage(Page page,QueryOrganizationVo queryOrganizationVo);
}