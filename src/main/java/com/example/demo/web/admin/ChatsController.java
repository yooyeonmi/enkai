/*package com.example.demo.web.admin;

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

import com.example.demo.common.FlashData;
import com.example.demo.entity.Chat;
import com.example.demo.entity.Event;
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

	
	 * チャット表示
	 
	
	@GetMapping(value = "/talk/{eventId}")
	public String talk(@PathVariable Integer eventId, @AuthenticationPrincipal UserDetails user, 
			 Model model, RedirectAttributes ra) {
		//	全件取得
	
		try {
			String UserEmail = user.getUsername();
			User userData = userService.findByEmail(UserEmail);
			Event event = eventService.findById(eventId);
			Chat chatUser = chatService.findByEventIdAndUserId(eventId,userData.getId()); //chatにイベントとユーザのデータを入れた
			List<Chat> chat = chatService.findAll();
			model.addAttribute("chatUsert",chatUser);
			model.addAttribute("event",event);
			model.addAttribute("chat",chat);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
		}
		return "/admin/chats/talk";
	}
	
	
	
	 * チャット登録
	 
	
	@PostMapping(value = "/create")
	public String register(@Valid Chat chat, Event event,@AuthenticationPrincipal UserDetails user, 
			 BindingResult result,  Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/chats/talk";
			}
			String UserEmail = user.getUsername();
			User userData = userService.findByEmail(UserEmail);
			Chat chatUser = chatService.findByEventIdAndUserId(event.getId(),userData.getId());
			chat.setUser(userService.findById(userData.getId()));
			chatService.save(chat);
			chatService.save(chatUser);
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/chats";
		}
		return "redirect:/admin/chats/talk";
	}
	
}
*/