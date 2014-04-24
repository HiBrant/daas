package data.as.a.service.controller.ui;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.as.a.service.access.entity.jpa.sys.UserEntity;
import data.as.a.service.management.UserService;

@Controller
@RequestMapping("/ui")
public class LogonController {

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView regist(@RequestParam String username,
			@RequestParam String email, @RequestParam String password,
			HttpSession session) {

		UserService service = new UserService();
		UserEntity user = service.register(username, password, email);
		ModelAndView mv = null;
		if (user == null) {
			mv = new ModelAndView("login");
			mv.addObject("errorMsg",
					"Register: User has already existed with the same username or email!");
		} else {
			mv = new ModelAndView("index");
			session.setAttribute("userId", user.get_id());
			session.setAttribute("username", user.getUsername());
		}
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam String username,
			@RequestParam String password, HttpSession session) {

		UserService service = new UserService();
		UserEntity user = service.login(username, password);
		ModelAndView mv = null;
		if (user == null) {
			mv = new ModelAndView("login");
			mv.addObject("errorMsg",
					"Login: Wrong password or unavailable username!");
		} else {
			mv = new ModelAndView("index");
			session.setAttribute("userId", user.get_id());
			session.setAttribute("username", user.getUsername());
		}
		return mv;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("app");
		session.removeAttribute("userId");
		session.removeAttribute("username");
		return "login";
	}
}
