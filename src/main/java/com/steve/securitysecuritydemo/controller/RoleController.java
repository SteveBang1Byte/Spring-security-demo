/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.controller;

import com.steve.securitysecuritydemo.entity.Role;
import com.steve.securitysecuritydemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createRole(role));
    }
}
