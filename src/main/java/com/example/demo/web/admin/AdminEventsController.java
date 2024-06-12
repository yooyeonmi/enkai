package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Event;
import com.example.demo.entity.EventUser;
import com.example.demo.entity.User;
import com.example.demo.service.EventService;
import com.example.demo.service.EventUserService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({"/admin/events"})
public class AdminEventsController {
	/*@Autowired
	BaseService<Event> eventService;*/
	@Autowired
	EventUserService eventUserService;
	@Autowired
	UserService userService;
	@Autowired
	EventService eventService;
	
	
	/*
	 * イベント表示詳細
	 */
	@GetMapping(value = "/view/{id}")
		public String view(@PathVariable Integer id, @AuthenticationPrincipal UserDetails user, Model model, RedirectAttributes ra) {
			// 全件取得
			String UserEmail = user.getUsername();
			try {
				User userData = userService.findByEmail(UserEmail);
				Event event = eventService.findById(id);
				EventUser eventUser = eventUserService.findByEventIdAndUserId(event.getId(),userData.getId());
				model.addAttribute("eventUser",eventUser); //view に送る			
				model.addAttribute("event",event); 
			} catch (Exception e) {
				FlashData flash = new FlashData().danger("該当データがありません");
				ra.addFlashAttribute("flash", flash);
				/*return "redirect:/admin/events";*/
			}
			
			return "/admin/events/view";
		} 
	
	/*
	 * マイイベント表示
	 */
	
	@GetMapping(value = "/mylist")
	public String mylist(Model model, @AuthenticationPrincipal UserDetails user,RedirectAttributes ra) {
		FlashData flash;
		//	全件取得
			String UserEmail = user.getUsername();
			try {
				User userData = userService.findByEmail(UserEmail); 
				List<Event> event = eventService.findByUserId(userData.getId()); 
				model.addAttribute("event",event); //view に送る
				
			} catch(DataNotFoundException e) {
	    		flash = new FlashData().danger("該当データがありません");
	    		ra.addFlashAttribute("flash", flash);
			}
			
			
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
	@PostMapping(value = "/create{id}")
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
			Event event = eventService.findById(id);
			model.addAttribute("event",event);
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
	@GetMapping("/delete/{id}")
		public String delete(@PathVariable Integer id, Event event, RedirectAttributes ra) {
			FlashData flash;
			try {
				List<EventUser> eventUser = eventUserService.findByEventId(event.getId());
	    		if (eventUser.isEmpty()) {
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
			return "redirect:/admin/events/mylist";
		}
}
