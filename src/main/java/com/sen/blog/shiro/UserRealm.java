package com.sen.blog.shiro;

import com.sen.blog.entity.User;
import com.sen.blog.exception_handler.IncorrectCaptchaException;
import com.sen.blog.service.UserService;
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

/**
 * @Auther: Sen
 * @Date: 2019/9/22 00:30
 * @Description: shiro自定义域
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    public String getName() {
        return "userRealm";
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        if (user.getUserPermission() == null) {
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(user.getUserPermission());
        return info;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CaptchaAuthenticationToken captchaToken = (CaptchaAuthenticationToken) token;
        // 增加判断验证码逻辑
        String captcha = captchaToken.getKaptcha();
        String exitCode = (String) SecurityUtils.getSubject().getSession().getAttribute("KAPTCHA_SESSION_KEY");
        if (null == captcha || !captcha.equalsIgnoreCase(exitCode )|| "".equals(exitCode)) {
            throw new IncorrectCaptchaException("验证码错误");
        }

        //登录相关逻辑
        String username = token.getPrincipal().toString();
        User user = userService.login(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user
                ,user.getUserPass(), ByteSource.Util.bytes(user.getUserSalt()), getName());
        return info;
    }

    /**
     * 如在运行过程中，主体的权限发生了改变，
     * 那么应该从spring容器中调用realm的清理缓存方法。
     * @param principals
     */
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
