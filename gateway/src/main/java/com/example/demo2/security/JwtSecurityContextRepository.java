package com.example.demo2.security;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.example.demo2.service.CustomUserDetailsService;
import com.example.demo2.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description 获取请求头中带过来的token值，解析并验证用户信息
 * @Author JL
 * @Version V1.0
 */
@Slf4j
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.info("加载token:JwtSecurityContextRepository");
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (permitApi(path, antPathMatcher)) {
            return Mono.empty();
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isEmpty(token)) {
            return Mono.empty();
        }

        Claims claims = JwtUtil.parseJWT(token);
        String userId = claims.get("id").toString();
        if (jwtUtil.validateAccessToken(token, userId)) {
            return Mono.empty();
        }
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(claims.get("id", String.class));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(userDetails);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return Mono.just(usernamePasswordAuthenticationToken).map(SecurityContextImpl::new);

    }

    private static boolean permitApi(String path, AntPathMatcher antPathMatcher) {
        return antPathMatcher.match("/api/**", path);
    }
}