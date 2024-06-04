package com.example.demo.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.entity.EventUser;
import com.example.demo.service.BaseService;
import com.example.demo.service.EventService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({"/admin/eventusers"})
public class EventUsersController {
	@Autowired
	BaseService<EventUser> eventUserService;
	@Autowired
	EventService eventService;

	/*
	 * イベント一覧表示
	 */
	 
	/*	@GetMapping(path = {"/",""})
		public String list(Model model) {
			//	全件取得
				List<Event> events = eventService.findAll();
				model.addAttribute("events",events);
				return "/admin/events/list";
		}//admin
	*/	
	
	 /*
	  *  イベント詳細
	  */
		/*	 
			@GetMapping(value = {"/events/view/{id}"})
			public String view(Model model) {
				// 全件取得
				List<Event> events = eventService.findAll();
				model.addAttribute("orderDetails", events);
				return "/admin/events/view";
			}*/
	 
	/*
	 * イベント詳細
	 */
	 @GetMapping(value = {"/create/{id}"})
	public String register(@Valid EventUser eventUser, BindingResult result, Model model,
			RedirectAttributes ra) {	
		 FlashData flash;
		 try {
			 if (result.hasErrors()) {
				 return "admin/eventusers/create";
			 }
			 //新規登録
			 eventUserService.save(eventUser);
			 flash = new FlashData().success("新規登録しました");
		 }catch (Exception e) {
			 flash = new FlashData().danger("処理中にエラーが発生しました");
		 }
		 ra.addFlashAttribute("flash",flash);
		 return "redirect:/admin/eventusers";
	 }
	 

		
	 
}