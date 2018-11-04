package com.imooc.shiro.realm;

import com.imooc.bean.User;
import com.imooc.dao.UserDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.*;

/**
 * 自定义Realm
 */
public class JdbcRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;

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
        List<String> list = userDao.queryRolesByUserName(username);
        Set<String> roles = new HashSet<String>(list);
        return roles;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();

        String password = getPasswordByUserName(username);
        if (password == null){
            return null;
        }
        //SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,"CustomRealm");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,"JdbcRealm");
        return simpleAuthenticationInfo;
    }

    private String getPasswordByUserName(String username) {
        User user = userDao.getUserNyUserName(username);
        if (user != null){
            return user.getPassword();
        }
       return  null;
    }
}
