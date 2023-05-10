/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created a new interface UserRepository - dev29
 */
package com.steve.securitysecuritydemo.repository;

import com.steve.securitysecuritydemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Created by: Steve Bang <br/>
 * Created date: 05 - 10 - 2023 <br/>
 * Description:  <br/>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
