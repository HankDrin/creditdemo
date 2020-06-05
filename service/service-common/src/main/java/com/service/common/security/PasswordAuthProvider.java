package com.service.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码输入模式认证
 *
 * @author
 * @since 2020-06-04.
 */
@Slf4j
public class PasswordAuthProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private UserDetailsChecker userDetailsChecker = new DefaultPreAuthenticationChecks();

    public PasswordAuthProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PasswordAuthToken passwordAuthToken = (PasswordAuthToken) authentication;
        String userNo = passwordAuthToken.getPrincipal().toString();
        String password = passwordAuthToken.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(userNo);
        if (user == null) {
            throw new BadCredentialsException("无法获取用户信息");
        }
        userDetailsChecker.check(user);

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bcryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码校验不通过");
        }

        return new PasswordAuthToken(user, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthToken.class.isAssignableFrom(authentication);
    }

    private static class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                log.debug("User account is locked");

                throw new LockedException("用户账户已锁定");
            }

            if (!user.isEnabled()) {
                log.debug("User account is disabled");

                throw new DisabledException("用户账户已禁用");
            }

            if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");

                throw new AccountExpiredException("用户账户已过期");
            }
        }
    }
}
