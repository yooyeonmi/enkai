package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.EventUser;
import com.example.demo.repository.EventUserRepository;


@Repository
public class EventUserDao implements BaseDao<EventUser> {
	@Autowired
	EventUserRepository repository;

	@Override
	public List<EventUser> findAll() {
		return repository.findAll();
	}

	@Override
	public EventUser findById(Integer id) throws DataNotFoundException {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(EventUser eventUser) {
		this.repository.save(eventUser);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			EventUser eventUser = this.findById(id);
			this.repository.deleteById(eventUser.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
	
	public List<EventUser> findByEventId(Integer eventId) throws DataNotFoundException{
				List<EventUser> eventuser  = this.repository.findByEventId(eventId);
				if (eventuser == null) {
					throw new DataNotFoundException();
				}
				return eventuser; //eventUsers -> eventUser
			}
	
	public List<EventUser> findByUserId(Integer userId) throws DataNotFoundException{
		List<EventUser> eventuser  = this.repository.findByUserId(userId);
		if (eventuser == null) {
			throw new DataNotFoundException();
		}
		return eventuser; //eventUsers -> eventUser
	}
	
	public EventUser findByEventIdAndUserId(Integer eventId, Integer userId) throws DataNotFoundException {
		EventUser eventuser = this.repository.findByEventIdAndUserId(eventId, userId);
		if (eventuser == null) {
			throw new DataNotFoundException();
		}
		return eventuser; //eventUsers -> eventUser
	}
	
	
}