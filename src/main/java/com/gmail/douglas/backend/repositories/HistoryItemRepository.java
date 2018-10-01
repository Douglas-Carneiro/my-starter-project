package com.gmail.douglas.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.douglas.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
