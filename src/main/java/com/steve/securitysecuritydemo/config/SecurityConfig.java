/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.config;

import com.steve.securitysecuritydemo.constant.Constant;
import com.steve.securitysecuritydemo.filter.CustomAccessDeniedHandler;
import com.steve.securitysecuritydemo.filter.CustomAuthenticationFilter;
import com.steve.securitysecuritydemo.filter.CustomAuthorizationFilter;
import com.steve.securitysecuritydemo.utility.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// Để annotation này để dùng @PreAuthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Tắt tính năng bảo vệ CSRF mặc định của spring boot. CRSF là gì? xem tại: https://topdev.vn/blog/csrf-la-gi/
        http.csrf().disable();
        // Cấu hình phiên làm việc cho mỗi request, cấu hình như thế này thì mỗi phiên làm việc sẽ không lưu lại trên server
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), jwtUtil);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority(Constant.USER_ROLE);
//        http.authorizeRequests().antMatchers("/api/roles/**").hasAnyAuthority(Constant.ADMIN_ROLE);
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
