package com.gdbc.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdbc.domain.ActiveUser;
import com.gdbc.domain.SysPermission;
import com.gdbc.domain.SysUser;
import com.gdbc.service.SysService;

public class CustomRealm extends AuthorizingRealm {

	// 注入service
	@Autowired
	private SysService sysService;

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName("customerRealm");
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// 从token中 获取用户身份信息
		String userCode = (String) token.getPrincipal();
		// 拿username从数据库中查询
		SysUser sysUser = null;
		try {
			sysUser = sysService.findSysUserByUserCode(userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果查询不到返回null
		if (sysUser == null) {//
			return null;
		}
		// 获取从数据库查询出来的用户密码
		String password = sysUser.getPassword();// 这里使用静态数据模拟。。
		// 盐
		String salt = sysUser.getSalt();
		// activeUser就是用户身份信息
		ActiveUser activeUser = new ActiveUser();

		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());

		// 根据用户id取出菜单
		List<SysPermission> menus = null;
		try {
			// 通过service取出菜单
			menus = sysService.findMenuListByUserId(sysUser.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将用户菜单 设置到activeUser
		activeUser.setMenus(menus);
		// 将activeUser设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				activeUser, password, ByteSource.Util.bytes(salt),
				this.getName());

		return simpleAuthenticationInfo;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		// 从 principals获取主身份信息
		// 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

		// 根据身份信息获取权限信息
		// 从数据库获取到权限数据
		List<SysPermission> permissionList = null;
		try {
			permissionList = sysService.findPermissionListByUserId(activeUser
					.getUserid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 单独定一个集合对象
		List<String> permissions = new ArrayList<String>();
		if (permissionList != null) {
			for (SysPermission sysPermission : permissionList) {
				// 将数据库中的权限标签 符放入集合
				permissions.add(sysPermission.getPercode());
			}
		}
		// 将权限信息封闭为AuthorizationInfo
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;

	}

	// 清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject()
				.getPrincipals();
		super.clearCache(principals);
	}

}
