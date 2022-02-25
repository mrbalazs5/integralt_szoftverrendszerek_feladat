package hu.unimiskolc.iit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@GetMapping("/")
	public ModelAndView showIndexPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index.html");
		return mav;
	}
	

}
