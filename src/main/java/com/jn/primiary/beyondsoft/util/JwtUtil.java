package com.jn.primiary.beyondsoft.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.Map;


public class JwtUtil {
   /* private Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long ttl=3600000*24;
    public String createJwt(String id , String name, Map<String,Object> map){
        long now = System.currentTimeMillis();
        long exp = now+ttl;

        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(name)
                .setIssuedAt(new Date())
                .signWith(key);

        for (Map.Entry<String,Object> entry:map.entrySet()) {
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));
        String token = jwtBuilder.compact();
        return token;
    }

    public Claims parseJwt(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            //System.out.println(claims.getId());
            //System.out.println(claims.getSubject());
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }*/
}
