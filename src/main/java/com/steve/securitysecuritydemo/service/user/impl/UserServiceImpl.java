/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.service.user.impl;

import com.steve.securitysecuritydemo.entity.Role;
import com.steve.securitysecuritydemo.entity.User;
import com.steve.securitysecuritydemo.repository.RoleRepository;
import com.steve.securitysecuritydemo.repository.UserRepository;
import com.steve.securitysecuritydemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User createUser(User user) {
        log.info("Save user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role createRole(Role role) {
        log.info("Save role");
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Add role to user");
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Role> roleOptional = roleRepository.findByName(roleName);
        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();
            user.getRoles().add(role);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("Get by user {}", username);
        return userRepository.findByUsername(username).get();
    }

    @Override
    public List<User> getUsers() {
        log.info("Get all user");
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Get Authority
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User not found in database with username: " + username);
    }
}
