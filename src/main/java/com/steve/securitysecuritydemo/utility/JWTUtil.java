/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@Component
@Slf4j
public class JWTUtil {
    @Value("${security.jwt.expired-access}")
    private long timeExpiredAccessToken;
    @Value("${security.jwt.expired-refresh}")
    private long timeExpiredRefreshToken;
    @Value("${security.jwt.secret-key}")
    private String secretKey;


    public String generateAccessToken(String username, List<String> roleNames, String host) {
        return JWT
                .create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + timeExpiredAccessToken))//60 minutes
                .withIssuer(host)
                .withClaim("roles", roleNames)
                .sign(Algorithm.HMAC256(secretKey.getBytes()));
    }

    public String generateRefreshToken(String username, List<String> roleNames, String host) {
        return JWT
                .create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + timeExpiredRefreshToken))//60 minutes
                .withIssuer(host)
                .withClaim("roles", roleNames)
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey.getBytes());
    }

    public UsernamePasswordAuthenticationToken readAccessToken(String accessToken) throws Exception {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            DecodedJWT decodedJWT = verifier.verify(accessToken);

            String username = decodedJWT.getSubject();
            String[] roleNames = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (roleNames != null && roleNames.length > 0) {
                for (String roleName : roleNames) {
                    authorities.add(new SimpleGrantedAuthority(roleName));
                }
            }
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (JWTVerificationException jwtVerificationException) {
            log.error(jwtVerificationException.getMessage());
            throw new JWTVerificationException(jwtVerificationException.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
