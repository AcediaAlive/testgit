package com.example.demo2.security;

import com.alibaba.fastjson.JSON;
import com.example.demo2.service.CustomUserDetailsService;
import com.example.demo2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description EnableWebFluxSecurity权限验证配置
 * @Author JL
 * @Version V1.0
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class WebFluxSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    /**
     * http请求路径权限与过滤链配置
     *
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        log.info("...加载启动springWebFilterChain");
        http
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .securityContextRepository(new JwtSecurityContextRepository(customUserDetailsService, jwtUtil))
                .formLogin()
                .authenticationFailureHandler(getServerAuthenticationFailureHandler())
                .loginPage("/login")
                .and().authorizeExchange()//请求进行授权
                .pathMatchers(HttpMethod.OPTIONS).permitAll()//特殊请求过滤
                .pathMatchers("/api/get-token").permitAll()//登录不需要验证
//                .pathMatchers("/home/test").permitAll()//登录不需要验证
                .anyExchange()//任何请求
                .authenticated()//都需要身份认证
                .and().logout()
                .logoutSuccessHandler(getServerLogoutSuccessHandler()).and()
                .exceptionHandling()
                .accessDeniedHandler(getServerAccessDeniedHandler());
        return http.build();
    }

    private ServerAccessDeniedHandler getServerAccessDeniedHandler() {
        // 无权限访问处理器(可以单独创建类处理)
        return (exchange, denied) -> writeWith(exchange, SecurityResponseBean.accessDeniedFactory("无权限"));
    }

    private ServerLogoutSuccessHandler getServerLogoutSuccessHandler() {
        //退出成功处理器(可以单独创建类处理)
        return (webFilterExchange, authentication) -> writeWith(webFilterExchange.getExchange(), SecurityResponseBean.loginOutFactory("登出"));
    }

    private ServerAuthenticationFailureHandler getServerAuthenticationFailureHandler() {
        return (webFilterExchange, exception) -> { //验证失败处理器(可以单独创建类处理)
            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return writeWith(webFilterExchange.getExchange(), SecurityResponseBean.sysTermErrorFactory("system error"));
        };
    }

    /**
     * 输出响应信息
     *
     * @param exchange
     * @return
     */
    public Mono<Void> writeWith(ServerWebExchange exchange, SecurityResponseBean<Objects> securityResponseBean) {
        ServerHttpResponse response = exchange.getResponse();
        String body = JSON.toJSONString(securityResponseBean);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))));
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}