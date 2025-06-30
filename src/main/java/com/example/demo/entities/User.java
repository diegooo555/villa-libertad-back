package com.example.demo.entities;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.demo.dtos.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String city;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    @JsonIgnore
    private Set<Role> roles;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public User(UserDto userDto) {
        email = userDto.getEmail();
        name = userDto.getName();
        phone = userDto.getPhone();
        city = userDto.getCity();
    }
}