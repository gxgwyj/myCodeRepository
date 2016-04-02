package com.junyang.security.person.service;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.junyang.common.model.page.Page;
import com.junyang.security.menu.model.Menu;
import com.junyang.security.person.model.Person;
import com.junyang.security.person.vo.PersonVo;
import com.junyang.security.person.vo.QueryPersonVo;
import com.junyang.security.role.model.Role;


/**
 * 人员服务类
 * @author Administrator
 *
 */
public interface PersonService {
	/**
	 * 分页查询用户信息
	 * @param page
	 * @param queryPersonVo
	 * @return
	 */
	public List<PersonVo> findPersonVoPage(Page page, QueryPersonVo queryPersonVo);
	/**
	 * 根据用户ID获得用户信息
	 * @param id
	 * @return
	 */
	public PersonVo getPersonVoById(String id);
	/**
	 * 保存用户信息
	 * @param person
	 */
	public void saveOrupdatePerson(Person person);
	/**
	 * 批量删除用户信息
	 * @param ids
	 */
	public void removePersons(String[] ids);
	/**
	 * 根据用户ID获得用户角色
	 */
	public Set<Role> getPersonRoles(String personId);
	/**
	 * 根据用户ID获取用户所拥有的权限（菜单）
	 * @param personId
	 * @return
	 */
	public Set<Menu> getPersonPermissions(String personId);
	/**
	 * 根据条件获得人员信息
	 * @param queryPersonVo
	 * @return
	 */
	List<PersonVo> getPersonVo(QueryPersonVo queryPersonVo);
      
}
