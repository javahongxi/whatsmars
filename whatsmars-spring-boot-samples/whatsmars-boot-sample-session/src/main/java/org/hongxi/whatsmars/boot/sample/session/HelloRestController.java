package org.hongxi.whatsmars.boot.sample.session;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	@GetMapping("/")
	String uid(HttpSession session) {
		return session.getId();
	}

}