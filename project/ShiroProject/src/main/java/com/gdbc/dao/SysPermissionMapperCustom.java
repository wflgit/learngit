package com.gdbc.dao;

import java.util.List;

import com.gdbc.domain.SysPermission;



/**
 * 
 * <p>Title: SysPermissionMapperCustom</p>
 * <p>Description: 权限mapper</p>
 */
public interface SysPermissionMapperCustom {
	
	//根据用户id查询菜单
	public List<SysPermission> findMenuListByUserId(String userid)throws Exception;
	//根据用户id查询权限url
	public List<SysPermission> findPermissionListByUserId(String userid)throws Exception;

}
