package com.junyang.security.role.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.junyang.common.Constants;
import com.junyang.common.model.page.Page;
import com.junyang.common.model.tree.TreeNode;
import com.junyang.common.util.JsonUtil;
import com.junyang.common.util.StringUtil;
import com.junyang.security.menu.service.MenuService;
import com.junyang.security.role.model.PersonRole;
import com.junyang.security.role.model.Role;
import com.junyang.security.role.service.RoleService;
import com.junyang.security.role.vo.QueryRoleVo;

@Controller
@RequestMapping(value="security/role/")
public class RoleAction {
    private static final String ROLE_LIST_VIEW="security/role/roleList";
    private static final String ROLE_EDIT_VIEW="security/role/roleEdit";
    private static final String ROLE_LIST_ACTION="security/role/roleList.do";
    private static final String REDIRECT_ROLE_LIST_ACTION="redirect:roleList.do";
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
	/**
	 * 人员列表页面
	 * @return
	 */
	@RequestMapping(value="roleList")
	private ModelAndView roleList(@ModelAttribute("queryRoleVo") QueryRoleVo queryRoleVo,@ModelAttribute("page") Page page,HttpServletRequest request){
		if(!Constants.TURN_PAGE.equals(request.getParameter(Constants.TURN_PAGE))){
			page.setPageNo(1);
		}
		page.initTurnPageUrl(ROLE_LIST_ACTION);
		List<Role> roleList = roleService.findRolePage(page,queryRoleVo);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("roleList", roleList);
		map.put("page", page);
		map.put("queryRoleVo", queryRoleVo);
		return new ModelAndView(ROLE_LIST_VIEW,map);
	}
	@RequestMapping(value="roleRemove")
	@ResponseBody
	private String roleRemove(HttpServletRequest request){
		String[] ids = request.getParameterValues("rId");
		if(ids!=null && ids.length>0){
			roleService.removeRoles(ids);
		}
		return "success";
	}
	@RequestMapping(value="roleEdit")
	private ModelAndView roleEdit(HttpServletRequest request){
		String id = request.getParameter("id");
		Role role = new Role();
		if(!StringUtil.isEmpty(id)){
			role = roleService.getRoleById(id);
		}
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("role", role);
		return new ModelAndView(ROLE_EDIT_VIEW,map);
	}
	@RequestMapping(value="roleSave")
	private ModelAndView roleSave(@ModelAttribute("role")Role role){
		roleService.saveOrUpdateRole(role);
		return new ModelAndView(REDIRECT_ROLE_LIST_ACTION);
	}
	@RequestMapping(value="getRoleMenuListJson")
	private void getRoleMenuListJson(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		List<TreeNode> records = menuService.findTreeNodesByRoleId(roleId);
		try {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JsonUtil.Object2Json(records));  //换成这个就好了
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="getPersonRoleJson")
	private void getPersonRoleJson(HttpServletRequest request,HttpServletResponse response){
		String personId = request.getParameter("personId");
		Set<Role> hasRoleList = roleService.findPersonRoleByPersonId(personId);
		List<Role> nohasRoleList = roleService.findPersonNoRoleByPersonId(personId);
		String roles = JsonUtil.Object2Json(hasRoleList);
		String noroles = JsonUtil.Object2Json(nohasRoleList);
		StringBuffer  result = new StringBuffer();
		result.append("{");
		result.append("\"roles\":").append(roles).append(",");
		result.append("\"noroles\":").append(noroles);
		result.append("}");
		try {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(result.toString());  //换成这个就好了
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="addPersonRole")
	private void addPersonRole(HttpServletRequest request,HttpServletResponse response){
		String personId = request.getParameter("personId");
		String roleIdsStr = request.getParameter("roleIdsStr");
		if(roleIdsStr!=null){
			String[] roleIds = roleIdsStr.split(",");
			if(roleIds!=null && roleIds.length>0){
				List<PersonRole> list = new ArrayList<PersonRole>();
				for (int j = 0; j < roleIds.length; j++) {
					PersonRole  personRole = new PersonRole();
					personRole.setpId(personId);
					personRole.setrId(roleIds[j]);
					list.add(personRole);
				}
				roleService.savePersonRoles(list);
			}
		}
		try {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print("success");  
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="removePersonRoles")
	private void removePersonRoles(HttpServletRequest request,HttpServletResponse response){
		String personId = request.getParameter("personId");
		String roleIdsStr = request.getParameter("roleIdsStr");
		if(roleIdsStr!=null){
			String[] roleIds = roleIdsStr.split(",");
			roleService.removePersonRoles(personId, roleIds);
		}
		try {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print("success");  //换成这个就好了
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}