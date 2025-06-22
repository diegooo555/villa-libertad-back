package com.example.demo.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.entities.Role;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleService roleService;
    
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }
    
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    public User saveUser(UserDto userDto) {
        User user = new User(userDto);
        Role role = roleService.findRoleByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public Set<Role> findRolesByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        return (user != null) ? user.getRoles()
                : Set.of(roleService.findRoleByName("ROLE_VISITOR"));
    }
    
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}