package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EventUser;

public interface EventUserRepository extends JpaRepository<EventUser, Integer> {
		List<EventUser> findByEventId(int eventId); 
		public EventUser findByEventIdAndUserId(int eventId, int userId); 
		List<EventUser> findByUserId(int id); 
		
}
