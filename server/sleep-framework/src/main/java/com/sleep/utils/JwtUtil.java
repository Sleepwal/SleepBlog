package com.sleep.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 *
 * @author manson
 * @date 2023/05/14
 */
public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 24 * 60 * 60 * 1000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "sl";

    /**
     * 得到uuid
     *
     * @return {@link String}
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 创建jwt
     *
     * @param subject token中要存放的数据（json格式）
     * @return {@link String}
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 创建jwt
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return {@link String}
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 得到jwt建设者
     *
     * @param subject   秘钥明文
     * @param ttlMillis 过期时间
     * @param uuid      uuid
     * @return {@link JwtBuilder}
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("SleepWalker")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 创建token
     *
     * @param id        id
     * @param subject   主题
     * @param ttlMillis ttl米尔斯
     * @return {@link String}
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    public static void main(String[] args) throws Exception {
        String jwt = createJWT("1");
        System.out.println(jwt);
        Claims claims = parseJWT(jwt);
        System.out.println(claims);

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwN2IxZGRkMDU5YTI0NzgzODQ0YzgxZmFjZTQxOTY2YyIsInN1YiI6IjUiLCJpc3MiOiJzbGVlcCIsImlhdCI6MTY4OTk0OTM5NiwiZXhwIjoxNjkwMDM1Nzk2fQ.cmkPDFTtU3fskaDTFU-IlxlySN6b_8wwksAotENikXI";
        Claims claims1 = parseJWT(token);
        System.out.println(claims1);
    }

    /**
     * 生成加密后的秘钥 secretKey
     * AES加密
     *
     * @return {@link SecretKey}
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析jwt
     *
     * @param jwt jwt
     * @return {@link Claims}
     * @throws Exception 异常
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}