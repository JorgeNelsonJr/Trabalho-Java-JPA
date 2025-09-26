package com.streaming.streaming.repository;

import com.streaming.streaming.entity.WatchHistory;
import com.streaming.streaming.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    // Retorna lista [User, Long(count)] ordenado por contagem decrescente.
    @Query("SELECT wh.usuario, COUNT(wh) as cnt FROM WatchHistory wh GROUP BY wh.usuario ORDER BY cnt DESC")
    List<Object[]> findUserWatchCounts(Pageable pageable);

    // opcional: contar quantas vezes um usuário assistiu (se quiser)
    @Query("SELECT COUNT(wh) FROM WatchHistory wh WHERE wh.usuario.id = :userId")
    Long countByUserId(Long userId);

}
