package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.exepcions.ResourceNotFoundException;
import com.example.demo.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role saveRole(String name) {
        Role role = new Role(name);

        return roleRepository.save(role);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}
