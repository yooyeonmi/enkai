package com.example.demo.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.entity.Chat;
import com.example.demo.entity.User;
import com.example.demo.service.ChatService;
import com.example.demo.service.EventService;
import com.example.demo.service.EventUserService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/admin/chats" })
public class ChatsController {
	@Autowired
	ChatService chatService;
	@Autowired
	EventUserService eventUserService;
	@Autowired
	UserService userService;
	@Autowired
	EventService eventService;
	
	/*
	 * チャット画面
	 */
	@GetMapping(value = "/talk/{eventId}")
	public String talk(@PathVariable Integer eventId, @Valid Chat chat, BindingResult result,
			@AuthenticationPrincipal UserDetails user, 	Model model, RedirectAttributes ra) {
			// 全件取得
			String UserEmail = user.getUsername();
			try {
				User userData = userService.findByEmail(UserEmail);
				/*			Chat chat = chatService.findByEventIdAndUserId(eventId,userData.getId());*/
				/*Chat chat = chatService.findById(chat.getId());*/
					chat.setEvent(eventService.findById(eventId));
					chat.setUser(userService.findById(userData.getId()));
					/*				chatService.save(chat);*/
				model.addAttribute("chat",chat);
			} catch (Exception e) {
				FlashData flash = new FlashData().danger("該当データがありません");
				ra.addFlashAttribute("flash", flash);
			}
			
			return "/admin/chats/talk/{eventId}}";
		} 
	
		/*	@GetMapping(value = "/create/")
			public String register() {
				
			}*/
}
