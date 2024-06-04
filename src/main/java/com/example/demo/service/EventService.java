package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.BaseDao;
import com.example.demo.dao.EventDao;
import com.example.demo.entity.Event;

@Service
public class EventService implements BaseService<Event> {
	@Autowired
	private BaseDao<Event> dao;
	@Autowired
	private EventDao eventDao;
	/*	@Autowired
		private EventDao eventUserDao;*/

	@Override
	public List<Event> findAll() {
		return dao.findAll();
	}

	@Override
	public Event findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(Event event) {
		dao.save(event);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}	
	
	public List<Event> findByCategoryId(Integer categoryId) throws DataNotFoundException {
		return eventDao.findByCategoryId(categoryId); //
	}
	/*	
		public List<Event> findByEventId(Integer eventId) throws DataNotFoundException {
			return eventDao.findByEventId(eventId); //
		}*/
/*	
		public List<Event> findByEventUserId(Integer eventUserId) throws DataNotFoundException {
			return eventDao.findByEventUserId(eventUserId); //
		}
	*/
	
}
