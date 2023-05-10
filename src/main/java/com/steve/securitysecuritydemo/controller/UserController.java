/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.controller;

import com.steve.securitysecuritydemo.entity.User;
import com.steve.securitysecuritydemo.service.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Dành cho user có quyền Admin,Supper_Admin, Manager
    @GetMapping
    @PreAuthorize("hasAnyAuthority('Admin','Supper_Admin','Manager')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
    // Dành cho user có quyền Admin
    @PostMapping
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<User> createUser(User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createUser(user));
    }
    // Dành cho user có quyền Supper_Admin
    @PostMapping("add-role")
    @PreAuthorize("hasAnyAuthority('Supper_Admin')")
    public ResponseEntity<User> addRoleToUser(RoleToUserForm roleToUserForm) {
        this.userService.addRoleToUser(roleToUserForm.username, roleToUserForm.roleName);
        return ResponseEntity.ok(this.userService.getUserByUsername(roleToUserForm.username));
    }

    // Dành cho user có quyền Supper_Admin hoặc nếu user là chính nó thì sẽ có quyền vào
    @DeleteMapping("{username}")
    @PreAuthorize("hasAnyAuthority('Supper_Admin') or principal == #username")
    public ResponseEntity<String> delete(@PathVariable String username) {
        return ResponseEntity.ok("Delete user " + username);
    }


    @Data
    class RoleToUserForm {
        private String username;
        private String roleName;
    }
}
