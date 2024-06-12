package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;



@Repository
public class EventDao implements BaseDao<Event> {
	@Autowired
	EventRepository repository;

	@Override
	public List<Event> findAll() {
		return repository.findAll();
	}

	@Override
	public Event findById(Integer id) throws DataNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Event event) {
		this.repository.save(event);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Event event = this.findById(id);
			this.repository.deleteById(event.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
	
	/*	public Event findByMax(Integer maxParticipant) throws DataNotFoundException {
			Event event = this.repository.findByMax(maxParticipant);
			if (event == null) {
				throw new DataNotFoundException();
			}
			return event; //
		}*/
	
	public List<Event> findByCategoryId(Integer categoryId) throws DataNotFoundException{
		List<Event> events  = this.repository.findByCategoryId(categoryId);
		if (events == null) {
			throw new DataNotFoundException();
		}
		return events;
	}
	public List<Event> findByUserId(Integer userId) throws DataNotFoundException{
		List<Event> events  = this.repository.findByUserId(userId);
		if (events == null) {
			throw new DataNotFoundException();
		}
		return events;
	}
	/*public Event findByUserId(Integer eventId, Integer userId) throws DataNotFoundException{
		EventUser eventuser = this.repository.findByUserId(eventId, userId);
		if (eventuser == null) {
			throw new DataNotFoundException();
		}
		return events; //eventUsers -> eventUser
	}*/
}

