/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.filter;

import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("error","Error");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{ \"error\": \"Access denied\" }");
    }
}