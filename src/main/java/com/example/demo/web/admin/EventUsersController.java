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
@RequestMapping({ "/admin/eventusers" })
public class EventUsersController {
	@Autowired
	EventUserService eventUserService;
	@Autowired
	EventService eventService;
	@Autowired
	UserService userService;

	/*
	 * イベントユーザ新規登録
	 */
	@GetMapping(value = "/create/{eventId}")
		public String register(@PathVariable Integer eventId, @Valid EventUser eventUser,
				BindingResult result,@AuthenticationPrincipal UserDetails user, Model model,
				RedirectAttributes ra) {
			// 全件取得
			
			try {
				String UserEmail = user.getUsername();
				User userData = userService.findByEmail(UserEmail);
				Event event = eventService.findById(eventId);
				Integer MaxParticipant = event.getMaxParticipant();
				List<EventUser> eventP = eventUserService.findByEventId(eventId);
				if (eventP.size() < MaxParticipant) {
					eventUser.setEvent(eventService.findById(eventId));
					eventUser.setUser(userService.findById(userData.getId()));
					eventUserService.save(eventUser);
					model.addAttribute("eventUser", eventUser);
				} else {
					FlashData flash = new FlashData().danger("イベント参加者数が最大数です");
					ra.addFlashAttribute("flash", flash);
					return "redirect:/admin/events/view/{eventId}";
					}
			} catch (Exception e) {
				FlashData flash = new FlashData().danger("該当データがありません");
				ra.addFlashAttribute("flash", flash);
				return "redirect:/admin/events";
			}
			return "redirect:/admin/events/view/{eventId}";
		}


	/*
	 * 削除
	 */
	@GetMapping("/delete/{eventId}")
	public String delete(@PathVariable Integer eventId,
			@AuthenticationPrincipal UserDetails user, RedirectAttributes ra) {
		FlashData flash;
		String UserEmail = user.getUsername();
		try {
			User userData = userService.findByEmail(UserEmail);
			EventUser eventUser = eventUserService.findByEventIdAndUserId(eventId, userData.getId());
			if (eventUser == null) {
				flash = new FlashData().danger("辞退できません");
			} else {
				//eventUserService.findById(eventId);
				eventUserService.deleteById(eventUser.getId());
				flash = new FlashData().success("イベントユーザの辞退が完了しました");
			}
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/events/view/{eventId}";
	}
}