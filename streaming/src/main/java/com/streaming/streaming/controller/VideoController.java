package com.streaming.streaming.controller;

import com.streaming.streaming.entity.Category;
import com.streaming.streaming.entity.User;
import com.streaming.streaming.entity.Video;
import com.streaming.streaming.repository.CategoryRepository;
import com.streaming.streaming.repository.VideoRepository;
import com.streaming.streaming.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VideoController {

    private final VideoRepository videoRepo;
    private final CategoryRepository categoryRepo;
    private final AnalyticsService analyticsService;

    public VideoController(VideoRepository videoRepo, CategoryRepository categoryRepo, AnalyticsService analyticsService) {
        this.videoRepo = videoRepo;
        this.categoryRepo = categoryRepo;
        this.analyticsService = analyticsService;
    }

    // 1) Buscar vídeos pelo título com ordenação. Ex: /api/videos/search?titulo=Missão
    @GetMapping("/videos/search")
    public List<Video> searchByTitle(@RequestParam("titulo") String titulo) {
        return videoRepo.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
    }

    // 2) Todos os vídeos de uma categoria ordenado pelo título. Ex: /api/videos/category?nome=Ação
    @GetMapping("/videos/category")
    public ResponseEntity<?> videosByCategory(@RequestParam("nome") String categoryName) {
        Category cat = categoryRepo.findByNome(categoryName);
        if (cat == null) return ResponseEntity.notFound().build();
        List<Video> videos = videoRepo.findByCategoriaOrderByTituloAsc(cat);
        return ResponseEntity.ok(videos);
    }

    // 3) Top 10 mais bem avaliados: /api/videos/top-rated
    @GetMapping("/videos/top-rated")
    public List<Video> topRated() {
        return videoRepo.findTop10ByOrderByAvaliacaoDesc();
    }

    // 4) Top 10 mais assistidos: /api/videos/top-viewed
    @GetMapping("/videos/top-viewed")
    public List<Video> topViewed() {
        return videoRepo.findTop10ByOrderByViewsDesc();
    }

    // 5) Usuário que mais assistiu vídeos: /api/users/top-watcher
    @GetMapping("/users/top-watcher")
    public ResponseEntity<?> topUser() {
        User top = analyticsService.getTopUserByWatchCount();
        if (top == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(top);
    }
}
