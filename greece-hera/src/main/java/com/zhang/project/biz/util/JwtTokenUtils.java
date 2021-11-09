package com.zhang.project.biz.util;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String AUTHORITIES = "authorities";
    /**
     * 密钥
     */
    private static final String SECRET = "zhangyaohang";

    /**
     * 创建token令牌
     * @param claims
     * @return
     */
    public  String createToken (Map<String, Object> claims,Long expireTime) {
        Date expirationDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts.builder()
                .setClaims(claims)
                .setId(IdUtil.simpleUUID())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }



    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    private static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            username = claims.get("username").toString();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从令牌中获取openId
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUserOpenIdFromToken(String token) {
        String userOpenId;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            userOpenId = claims.get("userOpenId").toString();
        } catch (Exception e) {
            userOpenId = null;
        }
        return userOpenId;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
    /**
     * 验证令牌
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("无效的JWT签名");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.error("过期的JWT令牌");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.error("不支持的JWT令牌");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 获取请求token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(token)) {
            return token;
        }
        return null;
    }

}

