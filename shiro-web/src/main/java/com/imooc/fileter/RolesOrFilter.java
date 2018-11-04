package com.imooc.fileter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RolesOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, Object o) throws Exception {

        //1.获取主体对象
        Subject subject = getSubject(servletRequest,servletResponse);
        //2.获取shiro配置中的角色权限列表
        String[] roles = (String[])o;
        //3.若角色列表为空，则说明无限制
        if (roles == null || roles.length == 0){
            return true;
        }

        //否则有限制，则判断当前角色是否有对应权限
        for (String role : roles){
            if (subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
