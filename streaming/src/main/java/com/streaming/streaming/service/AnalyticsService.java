package com.streaming.streaming.service;

import com.streaming.streaming.entity.User;
import com.streaming.streaming.repository.WatchHistoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final WatchHistoryRepository historyRepo;

    public AnalyticsService(WatchHistoryRepository historyRepo) {
        this.historyRepo = historyRepo;
    }

    // Retorna o usuário que mais assistiu (ou null se não houver)
    public User getTopUserByWatchCount() {
        List<Object[]> results = historyRepo.findUserWatchCounts(PageRequest.of(0,1));
        if (results == null || results.isEmpty()) return null;
        Object[] row = results.get(0);
        return (User) row[0];
    }
}
