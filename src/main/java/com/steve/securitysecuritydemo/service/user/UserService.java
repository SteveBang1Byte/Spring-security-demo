/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created a new interface UserService - dev29
 */
package com.steve.securitysecuritydemo.service.user;

import com.steve.securitysecuritydemo.entity.Role;
import com.steve.securitysecuritydemo.entity.User;

import java.util.List;

/**
 * Created by: Steve Bang <br/>
 * Created date: 05 - 10 - 2023 <br/>
 * Description:  <br/>
 */
public interface UserService {
    User createUser(User user);

    Role createRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUserByUsername(String username);

    List<User> getUsers();
}
