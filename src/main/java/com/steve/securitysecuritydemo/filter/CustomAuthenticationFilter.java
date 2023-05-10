/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.securitysecuritydemo.dto.JWTResponse;
import com.steve.securitysecuritydemo.utility.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Get username
        String username = request.getParameter("username");
        // Get password
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // Việc authenticaion giao cho authenticationManager xử lý.
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
//        Algorithm algorithm = Algorithm.HMAC256("secret-steve-jwt".getBytes());
//        String accessToken = JWT
//                .create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))//10 minutes
//                .withIssuer(getHost(request.getRequestURL().toString())) // Để biết client nào đã tạo ra. nếu mà không khớp thì sẽ thất bại.
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//
//        String refreshToken = JWT
//                .create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))//60 minutes
//                .withIssuer(getHost(request.getRequestURL().toString()))// Set issuer for URL created
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//        response.setHeader("access-token",accessToken);
//        response.setHeader("refresh-token",refreshToken);
        String accessToken = jwtUtil.generateAccessToken(
                user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                getHost(request.getRequestURL().toString()));
        String refreshToken = jwtUtil.generateRefreshToken(
                user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                getHost(request.getRequestURL().toString()));
        response.setHeader("access-token", accessToken);
        response.setHeader("refresh-token", refreshToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), new JWTResponse(accessToken, refreshToken));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);

    }

    private String getHost(String url) {
        return URI.create(url).getHost();
    }
}
