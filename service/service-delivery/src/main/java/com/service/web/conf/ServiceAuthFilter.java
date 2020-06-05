package com.service.web.conf;

import com.google.common.collect.Lists;
import com.service.common.constant.SysConstants;
import com.service.common.security.AuthObject;
import com.service.common.security.PasswordAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 服务认证
 *
 * @author xxx
 * @since 2020-06-04.
 */
public class ServiceAuthFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private List<String> ignorePaths;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private RedisTemplate<Object, Object> redisTemplate;

    public ServiceAuthFilter(TokenService tokenService, String... ignorePaths) {
        this.tokenService = tokenService;
        this.ignorePaths = Lists.newArrayList(ignorePaths);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(ignorePaths.stream().anyMatch(path -> antPathMatcher.match(path, request.getServletPath()))){
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isEmpty(cookies)) {
            throw new AccessDeniedException("您的账号还未登录，请先登录！");
        }

        Optional<Cookie> optionalCookie = Stream.of(cookies).filter(cookie -> Objects
                .equals(SysConstants.TOKEN_NAME, cookie.getName()) && !ObjectUtils.isEmpty(cookie.getValue()))
                                                .findFirst();
        if (!optionalCookie.isPresent()) {
            throw new AccessDeniedException("您的账号还未登录，请先登录！");
        }

        Cookie authCookie = optionalCookie.get();
        String cookieToken = authCookie.getValue();
        Token token = tokenService.verifyToken(cookieToken);
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        if (!Boolean.TRUE.equals(hashOperations.hasKey("user", token.getExtendedInformation()))) {
            throw new AccessDeniedException("您的账号已超时，请重新登录！");
        }
        AuthObject authObject = (AuthObject) redisTemplate.opsForHash().get("user", token.getExtendedInformation());
        PasswordAuthToken passwordAuthToken = new PasswordAuthToken(authObject, null);
        SecurityContextHolder.getContext().setAuthentication(passwordAuthToken);

        filterChain.doFilter(request,response);
    }
}
