package com.streaming.streaming.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // evitar conflito com palavras reservadas
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
}
