package com.streaming.streaming.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private Double avaliacao;
    private Long views = 0L;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category categoria;
}
