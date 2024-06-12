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
			List<Event> event = eventService.findAll();
			model.addAttribute("event",event);
			return "/admin/events/list";
	}
	/*	
		@GetMapping(value = {"events/view/{id}"})
		public String view(Model model) {
			// 全件取得
			List<Event> events = eventService.findAll();
			model.addAttribute("events", events);
			return "/admin/events/view";
		}*/
	/*
	 * イベント詳細
	 */
	/*	@GetMapping(value = {"events/view/{id}"})
		public String view(@PathVariable Integer id, Model model, RedirectAttributes ra) {
			// 全件取得
			Event event = new Event();
			try {
				event = eventService.findById(id);
			} catch (Exception e) {
				FlashData flash = new FlashData().danger("該当データがありません");
				ra.addFlashAttribute("flash", flash);
				return "redirect:/admin/events";
			}
			model.addAttribute("event", event);
			return "/admin/events/view";
		} */
	
	
	
}

