package com.egg.egglectricity.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getters, Setters, Equals&HashCode, RequiredArgsConstructor, toString.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factories")
public class Factory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="factory_id")
    private Long id; // best practice, numerical id as long.
    @Column(nullable = false)
    private String name;
}
