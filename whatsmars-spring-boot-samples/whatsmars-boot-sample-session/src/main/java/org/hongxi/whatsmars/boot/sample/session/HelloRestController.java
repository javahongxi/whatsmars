package org.hongxi.whatsmars.boot.sample.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	private static final String SESSION_KEY = "user";

	@GetMapping("/")
	String uid(HttpSession session) {
		if (session.getAttribute(SESSION_KEY) == null) return "not logged in";
		User user = (User) session.getAttribute(SESSION_KEY);
		return session.getId() + "," + JSON.toJSONString(user);
	}

	@GetMapping("/login")
	HttpStatus login(HttpServletRequest request) {
		User user = new User("qwert54321", "hongxi", 1, 28);
		request.getSession(true).setAttribute(SESSION_KEY, user);
		return HttpStatus.OK;
	}

}