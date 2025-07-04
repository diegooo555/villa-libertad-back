package com.example.demo.controllers;

import com.example.demo.entities.Role;
import com.example.demo.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN')")
@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/new-role")
    public ResponseEntity<Role> createNewRole(String roleName) {
        Role newRole = roleService.saveRole(roleName);
        return ResponseEntity.ok(newRole);
    }
}
