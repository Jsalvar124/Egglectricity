package com.egg.egglectricity.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    public Article(Integer number, String name, String description, Factory factory) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.factory = factory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id")
    private Long id;
    private Integer number; // Article number -> based on atomic integer.
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "factory_id", nullable = false)
    private Factory factory;
}


