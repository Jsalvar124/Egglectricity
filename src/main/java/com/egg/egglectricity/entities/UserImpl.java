package com.egg.egglectricity.entities;
import com.egg.egglectricity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getters, Setters, Equals&HashCode, RequiredArgsConstructor, toString.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserImpl { // we use user impl to avoid the same name of the spring User class used for spring security.
    public UserImpl(String email, String name, String lastName, String password, Role role) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="user_id", nullable = false)
    private String uuid; // save as String instead of UUID, in order to avoid unreadable 16Binary data on the database.
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(name="last_name", nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
