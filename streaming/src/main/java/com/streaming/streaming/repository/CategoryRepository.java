package com.streaming.streaming.repository;

import com.streaming.streaming.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNome(String nome);
}
