package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;

@Controller
@RequestMapping("/admin") //	/admin 以外に変えたらエラー
public class AdminController {
	@Autowired
	EventService eventService;

	
	 /*
	  *   イベント一覧表示
	  */
	@GetMapping(path = {"/",""})
	public String list(Model model) {
		//	全件取得
			List<Event> events = eventService.findAll();
			model.addAttribute("events",events);
			return "/admin/events/list";
	}
	
	
}

