package com.streaming.streaming;

import com.streaming.streaming.entity.*;
import com.streaming.streaming.repository.*;
import com.streaming.streaming.service.AnalyticsService; // Importe o serviço
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class StreamingApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamingApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserRepository userRepo,
                               CategoryRepository catRepo,
                               VideoRepository videoRepo,
                               WatchHistoryRepository historyRepo,
                               AnalyticsService analyticsService) { // Injete o AnalyticsService
        return args -> {
            // --- INSERÇÃO DE DADOS (Seu código original) ---
            User u1 = userRepo.save(new User(null, "Jorge", "jorge@ex.com"));
            User u2 = userRepo.save(new User(null, "Erick", "erick@ex.com"));
            User u3 = userRepo.save(new User(null, "Ana", "ana@ex.com"));

            Category cAcao = catRepo.save(new Category(null, "Ação"));
            Category cComedia = catRepo.save(new Category(null, "Comédia"));
            Category cSciFi = catRepo.save(new Category(null, "Sci-Fi"));

            Video v1 = videoRepo.save(new Video(null, "Missão Impossível", "Agente em ação", 9.2, 1500L, cAcao));
            Video v2 = videoRepo.save(new Video(null, "Missão Marte", "Exploração espacial", 8.7, 1200L, cSciFi));
            Video v3 = videoRepo.save(new Video(null, "Missão Resgate", "Resgate arriscado", 8.9, 900L, cAcao));
            Video v4 = videoRepo.save(new Video(null, "Rindo Alto", "Comédia pastelão", 7.1, 2000L, cComedia));
            Video v5 = videoRepo.save(new Video(null, "Missão: Segredo", "Ação e suspense", 9.0, 1100L, cAcao));
            Video v6 = videoRepo.save(new Video(null, "Drama espacial", "Sci-Fi dramático", 8.0, 500L, cSciFi));
            Video v7 = videoRepo.save(new Video(null, "Comédia leve", "Risos", 7.5, 700L, cComedia));
            Video v8 = videoRepo.save(new Video(null, "Super Missão", "Ação total", 9.5, 3000L, cAcao));
            Video v9 = videoRepo.save(new Video(null, "Missão X", "Aventura", 8.6, 1300L, cAcao));
            Video v10 = videoRepo.save(new Video(null, "Curta Missão", "Curta", 7.9, 400L, cAcao));

            historyRepo.save(new WatchHistory(null, u1, v1, LocalDateTime.now().minusDays(1)));
            historyRepo.save(new WatchHistory(null, u1, v2, LocalDateTime.now().minusDays(2)));
            historyRepo.save(new WatchHistory(null, u1, v5, LocalDateTime.now().minusHours(5)));
            historyRepo.save(new WatchHistory(null, u1, v8, LocalDateTime.now().minusHours(2)));
            historyRepo.save(new WatchHistory(null, u2, v4, LocalDateTime.now().minusDays(3)));
            historyRepo.save(new WatchHistory(null, u2, v7, LocalDateTime.now().minusDays(1)));
            historyRepo.save(new WatchHistory(null, u2, v8, LocalDateTime.now().minusHours(10)));
            historyRepo.save(new WatchHistory(null, u3, v1, LocalDateTime.now().minusDays(5)));
            historyRepo.save(new WatchHistory(null, u3, v1, LocalDateTime.now().minusDays(4)));
            historyRepo.save(new WatchHistory(null, u3, v9, LocalDateTime.now().minusDays(1)));
            historyRepo.save(new WatchHistory(null, u3, v8, LocalDateTime.now().minusHours(6)));
            historyRepo.save(new WatchHistory(null, u3, v8, LocalDateTime.now().minusHours(2)));
            historyRepo.save(new WatchHistory(null, u3, v8, LocalDateTime.now().minusMinutes(90)));
            // ---------------------------------------------


            // --- EXECUÇÃO DAS CONSULTAS DE TESTE ---
            System.out.println("\n=======================================================");
            System.out.println("  EXECUTANDO CONSULTAS JPA PARA O SISTEMA DE STREAMING");
            System.out.println("=======================================================");

            // 1. Buscar vídeos pelo título com ordenação: "Missão"
            System.out.println("\n1. VÍDEOS COM TÍTULO 'MISSÃO' (ORDENADO POR TÍTULO):");
            List<Video> videosMissao = videoRepo.findByTituloContainingIgnoreCaseOrderByTituloAsc("Missão");
            videosMissao.forEach(v -> System.out.printf("- Título: %s, Avaliação: %.1f\n", v.getTitulo(), v.getAvaliacao()));

            // 2. Todos os vídeos de uma categoria ordenado pelo título: "Ação"
            System.out.println("\n2. VÍDEOS NA CATEGORIA 'AÇÃO' (ORDENADO POR TÍTULO):");
            List<Video> videosAcao = videoRepo.findByCategoriaOrderByTituloAsc(cAcao);
            videosAcao.forEach(v -> System.out.printf("- Título: %s, Views: %d\n", v.getTitulo(), v.getViews()));

            // 3. Os top 10 vídeos mais bem avaliados
            System.out.println("\n3. TOP 10 VÍDEOS MAIS BEM AVALIADOS:");
            List<Video> topAvaliados = videoRepo.findTop10ByOrderByAvaliacaoDesc();
            topAvaliados.forEach(v -> System.out.printf("- Título: %s, Avaliação: %.1f\n", v.getTitulo(), v.getAvaliacao()));

            // 4. Os top 10 vídeos mais assistidos
            System.out.println("\n4. TOP 10 VÍDEOS MAIS ASSISTIDOS (VIEWS):");
            List<Video> topViews = videoRepo.findTop10ByOrderByViewsDesc();
            topViews.forEach(v -> System.out.printf("- Título: %s, Views: %d\n", v.getTitulo(), v.getViews()));

            // 5. O usuário que mais assistiu vídeos (usando o AnalyticsService)
            System.out.println("\n5. USUÁRIO QUE MAIS ASSISTIU VÍDEOS:");
            User topUser = analyticsService.getTopUserByWatchCount();
            if (topUser != null) {
                System.out.printf("- Nome: %s, Email: %s\n", topUser.getNome(), topUser.getEmail());
            } else {
                System.out.println("- Nenhum usuário encontrado no histórico de visualização.");
            }

            System.out.println("\n=======================================================");
            System.out.println("  FIM DAS CONSULTAS JPA");
            System.out.println("=======================================================");
        };
    }
}