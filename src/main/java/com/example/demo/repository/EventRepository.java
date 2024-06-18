package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer>{
		List<Event> findByCategoryId(int categoryId);
		List<Event> findByUserId(int id);
		List<Event> findByIdAndUserId(int id, int userId);
}
