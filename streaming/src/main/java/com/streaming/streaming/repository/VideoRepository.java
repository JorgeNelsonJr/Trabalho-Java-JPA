package com.streaming.streaming.repository;

import com.streaming.streaming.entity.Video;
import com.streaming.streaming.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    // Buscar vídeos pelo título (contendo) com ordenação
    List<Video> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    // Todos os vídeos de uma categoria ordenado pelo título
    List<Video> findByCategoriaOrderByTituloAsc(Category categoria);

    // Top 10 vídeos mais bem avaliados
    List<Video> findTop10ByOrderByAvaliacaoDesc();

    // Top 10 vídeos mais assistidos (por views)
    List<Video> findTop10ByOrderByViewsDesc();

    // Alternativa usando Pageable (flexível)
    List<Video> findAllByOrderByAvaliacaoDesc(Pageable pageable);
}
