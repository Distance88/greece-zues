package com.zhang.common.base.config;

import com.alibaba.fastjson.JSON;
import com.zhang.common.base.util.JwtTokenUtils;
import com.zhang.common.base.util.RedisUtil;
import com.zhang.common.core.exception.ErrorCode;
import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.template.RestBusinessTemplate;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Yaohang Zhang
 * @ClassName WebSecurityConfig
 * @description TODO
 * @date 2021-09-08 13:57
 */
@Slf4j
@Configuration
public class WebSecurityConfig{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    private static final long DOUBLE_EXPIRE_TIME = 2 * EXPIRE_TIME;

    @Component
    public class AuthFilter implements GlobalFilter, Ordered {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

            if(StringUtils.contains(exchange.getRequest().getPath().toString(),"/user/login")){
                return chain.filter(exchange);
            }
            if(StringUtils.contains(exchange.getRequest().getPath().toString(),"/hades")){
                return chain.filter(exchange);
            }
            CommonRestResult commonRestResult = new CommonRestResult();

            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            String requestURI = serverHttpRequest.getURI().toString();

            String accessToken = serverHttpRequest.getHeaders().getFirst("Authorization");
            String refreshToken = serverHttpRequest.getHeaders().getFirst("refreshToken");

            if (StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(refreshToken)) {
                String userOpenId = jwtTokenUtils.getUserOpenIdFromToken(accessToken);
                if(redisUtil.hasKey(userOpenId)){
                    String redisToken = redisUtil.get(userOpenId).toString();
                    if(!StringUtils.equals(redisToken,accessToken)){
                        if(!jwtTokenUtils.isExpired(refreshToken)){
                            commonRestResult.setCode("999994");
                            commonRestResult.setMessage("您已在其他地方登录");
                            return responseResult(serverHttpResponse,commonRestResult);
                        }
                    }
                }
                if(!jwtTokenUtils.validateToken(accessToken)){
                    if(jwtTokenUtils.validateToken(refreshToken)){
                        Claims claims = jwtTokenUtils.getClaimsFromToken(refreshToken);
                        String newAccessToken = jwtTokenUtils.createToken(claims,EXPIRE_TIME);
                        String newRefreshToken = jwtTokenUtils.createToken(claims,DOUBLE_EXPIRE_TIME);
                        serverHttpResponse.getHeaders().set("Authorization",newAccessToken);
                        serverHttpResponse.getHeaders().set("refreshToken",newRefreshToken);
                        return responseResult(serverHttpResponse,RestBusinessTemplate.execute(()->null));
                    }
                }else {
                    if(jwtTokenUtils.validateToken(refreshToken)){
                        log.debug("set Authentication to security context for '{}', uri: {}", "", requestURI);
                        return chain.filter(exchange);
                    }
                }
            } else {
                log.debug("no valid JWT token found, uri: {}", requestURI);
                commonRestResult.setCode(ErrorCode.TOKEN_EXCEPTION.getCode());
                commonRestResult.setMessage(ErrorCode.TOKEN_EXCEPTION.getDescription());
                return responseResult(serverHttpResponse,commonRestResult);
            }
            return null;
        }

        @Override
        public int getOrder() {
            return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER-1;
        }

        //对请求失败结果进行封装
        private Mono<Void> responseResult(ServerHttpResponse response, CommonRestResult object) {
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setStatusCode(HttpStatus.OK);
            DataBufferFactory bufferFactory = response.bufferFactory();
            byte[] bytes = JSON.toJSONString(object).getBytes();
            DataBuffer buffer = bufferFactory.wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        }

    }
}
