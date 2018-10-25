package com.sfy.shiro;

import com.sfy.domain.Role;
import com.sfy.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 *  在Shiro权限认证之后, 认证过的用户对于特定权限的页面或者功能仍然不具备访问权限,
 *  此时就需要针对不同的用户进行相应的授权操作. Shiro的授权操作是在权限认证的基础之上完成的
 *
 * @author: shifengyuan
 * @date: 2017/8/4 8:57
 */

public class ShiroRealm extends AuthorizingRealm {


    // 登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 简单默认一个用户,实际项目应User user = userService.getByAccount(token.getUsername());
        User user = new User("sfy", "csdncsdn");

        if (user == null) {
            throw new AuthorizationException();
        }

        SimpleAuthenticationInfo info = null;
        if (user.getUserName().equals(token.getUsername())) {
            info = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
        }
        return info;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 根据用户配置用户与权限
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String name = (String) getAvailablePrincipal(principals);
        List<String> roles = new ArrayList<String>();

        // 简单默认一个用户与角色，实际项目应User user = userService.getByAccount(name);
        User user = new User("sfy", "csdncsdn");
        Role role = new Role("administrator");
        user.setRole(role);

        if (user.getUserName().equals(name)) {
            if (user.getRole() != null) {
                roles.add(user.getRole().getName());
            }
        } else {
            throw new AuthorizationException();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 增加角色
        info.addRoles(roles);
        return info;
    }

}
