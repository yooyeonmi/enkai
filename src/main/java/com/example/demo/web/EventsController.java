package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Event;
import com.example.demo.service.BaseService;

@Controller
@RequestMapping({"/enkai/"})
public class EventsController {
	@Autowired
	BaseService<Event> eventService;

	/*
	 * イベント一覧表示
	 */
	@GetMapping(path = {"/",""})
	public String list(Model model) {
		//	全件取得
			List<Event> events = eventService.findAll();
			model.addAttribute("events",events);
			return "/events/list";
	}//admin
	
	/*
	 * イベント詳細
	 */
	@GetMapping(value = {"events/view/{id}"})
	public String view(Model model) {
		// 全件取得
		List<Event> events = eventService.findAll();
		model.addAttribute("events", events);
		return "/events/view";
	}
	
	/*	@GetMapping(value = {"events/view/{id}"})
		public String getEvent(@PathInteger int id,Model model) {
			List<EventUser>eventUsers = eventUserService.findAll();
			model.addAttribute("eventUsers",events.getEventUsers());
			return "/events/view"
			
			model.addAttribute("eventUser",events.getEventUsers());
			return "/events/view";
		}*/
	
	/*	@GetMapping(value ={ "/events/view/{id}"})
		public String getEvent(@PathVariable int id, Model model){
			List<Event> event = eventService.findAll();
			model.addAttribute("EventUserCount",event.getEventUserCount());
			return "/events/view";
			
		}
		*/
	
}

