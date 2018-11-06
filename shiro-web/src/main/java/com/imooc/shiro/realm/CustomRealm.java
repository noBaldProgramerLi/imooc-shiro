package com.imooc.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {
    Map<String,String> users = new HashMap<String, String>();
    {
        users.put("Mark","123456");
        super.setName("CustomRealm");
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = getRolesByUserName(username);
        Set<String> permissions = getPermissionsByUserName(username) ;

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String username) {
        Set<String> permissions = new HashSet<String>();
        permissions.add("user:select");
        return permissions;
    }

    private Set<String> getRolesByUserName(String username) {
        System.out.println("从数据库中获取授权数据");
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        roles.add("root");
        return roles;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();

        String password = getPasswordByUserName(username);
        if (password == null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("Mark","e10adc3949ba59abbe56e057f20f883e","CustomRealm");
        return simpleAuthenticationInfo;
    }

    private String getPasswordByUserName(String username) {
       return  users.get(username);
    }
}
