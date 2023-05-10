/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created a new interface RoleRepository - dev29
 */
package com.steve.securitysecuritydemo.repository;

import com.steve.securitysecuritydemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Created by: Steve Bang <br/>
 * Created date: 05 - 10 - 2023 <br/>
 * Description:  <br/>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
