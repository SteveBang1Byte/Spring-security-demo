/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleJWTException(JWTVerificationException jwtVerificationException) {
        return jwtVerificationException.getMessage();
    }
}
