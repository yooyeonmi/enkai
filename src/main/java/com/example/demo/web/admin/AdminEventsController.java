package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.entity.Event;
import com.example.demo.service.BaseService;
import com.example.demo.service.EventUserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({"/admin/events"})
public class AdminEventsController {
	@Autowired
	BaseService<Event> eventService;
	@Autowired
	EventUserService eventUserService;
	
	 
	 /*
	  *   イベント一覧表示
	  */
	@GetMapping(path = {"/",""})
	public String list(Model model) {
		//	全件取得
			List<Event> events = eventService.findAll();
			model.addAttribute("events",events);
			return "/admin/events/list";
	}//admin
	
	private boolean isParticipating = false;
	
	//@PostMapping(value = )
	
	/*
	 *   イベント詳細
	 */
	 
	@GetMapping(value = {"/view/{id}"})
	public String view(Model model) {
		// 全件取得
		List<Event> events = eventService.findAll();
		model.addAttribute("events", events);
		return "/admin/events/view";
	}

	/*	@GetMapping(value = {"view/{id}"})
		public String viewEvent(@PathVariable int id, Model model, Principal principal) {
			Event evnet = eventService.findById(id);
			User user = userService.findByEmail(principal.getName());
			boolean isParticipant = eventService.isParticipant("event", event);
			model.addAttribute("isParticipant",isParticipant);
			return "/admin/event/view";
		}*/
	
	/*
	 * マイイベント表示
	 */
	
	@GetMapping(value = "/mylist")
	public String mylist(Model model) {
		//	全件取得
			List<Event> events = eventService.findAll();
			model.addAttribute("events",events);
			return "/admin/events/mylist";
	}

	
	/*
	 * 新規作成画面表示
	 */
	@GetMapping(value = "/create")
	public String form(Event event, Model model) { //id
		model.addAttribute("event", event);
		return "admin/events/create";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/create")
	public String register(@Valid Event event, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/events/create";
			}
			eventService.save(event);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events";
	}
	
	 /*
	  *  編集画面表示
	  */
	 
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			// 存在確認
			Event events = eventService.findById(id);
			model.addAttribute("events",events);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/events";
		}
		return "admin/events/edit";
	}
	
	 /*
	  *  更新
	  */
	 
	@PostMapping(value = "/edit/{id}")
	public String update(@PathVariable Integer id, @Valid Event event, BindingResult result, Model model,
			RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/events/edit";
			}
			eventService.findById(id);
			// 更新
			eventService.save(event);
			flash = new FlashData().success("更新しました");
		} catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events/mylist";
	}
	
	/*
	 * 削除
	 */
	/*@GetMapping("/delete/{id}")
		public String delete(@PathVariable Integer id, Event event, RedirectAttributes ra) {
			FlashData flash;
			try {
				List<Event> event = eventUserService.findByEventId(id);
	    		if (eventUsers.isEmpty()) {
	        			eventService.findById(id);
	        			eventService.deleteById(id);
	        			flash = new FlashData().success("イベントの削除が完了しました");
	        			
	    		} else {
	        			flash = new FlashData().danger("イベント参加者が登録されているイベントは削除できません");
	    		}
			} catch (DataNotFoundException e) {
	    		flash = new FlashData().danger("該当データがありません");
	    		ra.addFlashAttribute("flash", flash);
			} catch (Exception e) {
				flash = new FlashData().danger("処理中にエラーが発生しました");
				}
			ra.addFlashAttribute("flash",flash);
			return "redirect:/admin/events";
		}*/
}
