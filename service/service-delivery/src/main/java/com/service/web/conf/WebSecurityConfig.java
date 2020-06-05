package com.service.web.conf;

import com.service.biz.user.IUserBizService;
import com.service.common.security.PasswordAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.session.SessionManagementFilter;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 安全认证配置
 *
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 系统绕过验证的请求配置
     */
    @Value("${system.anonymous.path}")
    private String[] anonymous;

    @Autowired
    private IUserBizService userBizService;

    @Autowired
    private DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/service/**")
            .permitAll()
            .anyRequest()
            .permitAll()
            .and()
            .exceptionHandling().authenticationEntryPoint(defaultAuthenticationEntryPoint)
            .and()
            .addFilterBefore(new ServiceAuthFilter(tokenService(), anonymous), SessionManagementFilter.class)
            .headers().cacheControl().disable()
            .and()
            .formLogin()
            .and()
            .httpBasic();
        http.authenticationProvider(new PasswordAuthProvider(userBizService));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public TokenService tokenService() throws NoSuchAlgorithmException {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
        tokenService.setServerInteger(2);
        tokenService.setServerSecret("xxx");
        SecureRandom random = new SecureRandom();
        tokenService.setSecureRandom(random);
        return tokenService;
    }

}
