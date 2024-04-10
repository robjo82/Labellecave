package com.labellecave.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "last_name")
    private String lastname;

    @Column(nullable = false, name = "first_name")
    private String firstname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    @Column()
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "email", email,
                "isAdmin", isAdmin,
                "firstname", firstname,
                "lastname", lastname,
                "address", address,
                "phone", phone
        );
    }
}
