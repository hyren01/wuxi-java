package com.jn.primiary.beyondsoft.aspect;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String JWT_KEY_ID = "id";
    private static final String JWT_KEY_USERID = "userId";
    private static final String JWT_KEY_USERNAME = "userName";
    private static final String JWT_KEY_USERACCOUNT = "userAccount";

//    @Value("${jwt.secret}")
    private static final String JWT_KEY_SECRET = "scistor*&%$HT*(%#$@";

//    @Value("${jwt.expire}")
//    private static final Long JWT_KEY_EXPIRE = 1800L;

//    public JwtTokenUtil(String secret, Long expire){
//        this.secret = secret;
//        this.expire = expire;
//    }

//    public static void main(String[] args) {
//        CmsUser user = new CmsUser();
//        user.setId("111");
//        user.setName("jack");
//        JwtTokenUtil tokenUtil = new JwtTokenUtil("scistor", 30L);
//        String token = tokenUtil.generateToken(user);
//        System.out.println(token);
//        UserInfo userInfo = tokenUtil.getTokenInfo(token);
//        System.out.println(userInfo.getId());
//        System.out.println(userInfo.getName());
//
//    }

    public static TokenInfo getTokenInfo(String token) {
        TokenInfo tokenInfo = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            String id = claims.get(JWT_KEY_ID).toString();
            String userId = claims.get(JWT_KEY_USERID).toString();
            String userName = claims.get(JWT_KEY_USERNAME).toString();
            String userAccount = claims.get(JWT_KEY_USERACCOUNT).toString();
            tokenInfo = new TokenInfo(id, userId, userName, userAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenInfo;
    }

//    public static Date getCreatedDateFromToken(String token) {
//        Date created;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            created = new Date((Long) claims.get(JWT_KEY_CREATETIME));
//        } catch (Exception e) {
//            created = null;
//        }
//        return created;
//    }

//    public static Date getExpirationDateFromToken(String token) {
//        Date expiration;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            expiration = claims.getExpiration();
//        } catch (Exception e) {
//            expiration = null;
//        }
//        return expiration;
//    }

    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(JWT_KEY_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

//    public static Date generateExpirationDate() {
//        return new Date(System.currentTimeMillis() + JWT_KEY_EXPIRE * 1000);
//    }

//    public static Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }

//    public static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//        return (lastPasswordReset != null && created.before(lastPasswordReset));
//    }

    public static String generateToken(TokenInfo tokenInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_KEY_ID, tokenInfo.getId());
        claims.put(JWT_KEY_USERID, tokenInfo.getUserId());
        claims.put(JWT_KEY_USERNAME, tokenInfo.getUserName());
        claims.put(JWT_KEY_USERACCOUNT, tokenInfo.getUserAccount());
        return generateToken(claims);
    }

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
//                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, JWT_KEY_SECRET)
                .compact();
    }

//    public static Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//        final Date created = getCreatedDateFromToken(token);
//        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
//                && !isTokenExpired(token);
//    }

//    public static String refreshToken(String token) {
//        String refreshedToken;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            claims.put(JWT_KEY_CREATETIME, new Date());
//            refreshedToken = generateToken(claims);
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }

    public static Boolean validateTokenInfo(TokenInfo tokenInfo, CmsUser user) {
        return (
                tokenInfo.getUserAccount().equals(user.getAccount())
                        && tokenInfo.getUserId().equals(user.getId()));
//                        && !isTokenExpired(token));
    }
}
