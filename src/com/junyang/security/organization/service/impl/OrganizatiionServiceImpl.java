package com.junyang.security.organization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junyang.common.model.page.Page;
import com.junyang.common.util.StringUtil;
import com.junyang.security.organization.dao.OrganizationMapper;
import com.junyang.security.organization.model.Organization;
import com.junyang.security.organization.service.OrganizationService;
import com.junyang.security.organization.vo.OrganizationVo;
import com.junyang.security.organization.vo.QueryOrganizationVo;
@Service
public class OrganizatiionServiceImpl implements OrganizationService {

	@Autowired
    private OrganizationMapper organizationMapper;
	
	@Override
	public void saveOrupdateOrg(Organization organization) {
		if(StringUtil.isEmpty(organization.getoId())){
			organizationMapper.insert(organization);
		}else{
			organizationMapper.updateByPrimaryKeySelective(organization);
		}
	}
	@Override
	public void changeOrganization(Organization organization) {
		organizationMapper.updateByPrimaryKey(organization);
		
	}
	@Override
	public List<Organization> findOrganizationList() {
		return organizationMapper.selectOrganizations(); 
	}
	@Override
	public void removeOrganizations(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				organizationMapper.deleteByPrimaryKey(id);
			}
		}
	}
	@Override
	public Organization getOrganization(String id) {
		return organizationMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<OrganizationVo> findOrganizationPage(Page page,QueryOrganizationVo queryOrganizationVo) {
		List<OrganizationVo> records = organizationMapper.selectOrganizationPage(page,queryOrganizationVo);
		return records;
	}
	@Override
	public OrganizationVo getOrganizationVo(String id) {
		return organizationMapper.selectVoByPrimaryKey(id);
	}

}
