package com.biz.mocoji.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.mocoji.domain.UserDTO;
import com.biz.mocoji.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/member")
@Controller
@Slf4j
public class MemberController {

	@Autowired
	UserService uService;
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login(
			@RequestParam(value="LOGIN_MSG",required=false, defaultValue = "0")String msg, Model model) {
		
		model.addAttribute("LOGIN_MSG", msg);
		model.addAttribute("BODY", "LOGIN");
		
		return "/login";
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public String login(String BODY, @ModelAttribute UserDTO userDTO, Model model, HttpSession httpSession) {
		
		boolean loginOk = uService.userLoginCheck(userDTO);
		
		if(loginOk == true) {
			httpSession.setMaxInactiveInterval(10*60);
			userDTO = uService.getUser(userDTO.getU_id());
			httpSession.setAttribute("userDTO", userDTO);
		}else {
			httpSession.removeAttribute("userDTO");
			model.addAttribute("LOGIN_MSG", "FAIL");
			return "redirect:/member/login";
		}
				
		model.addAttribute("BODY", BODY);
		return "redirect:/tour/main";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		
		httpSession.setAttribute("userDTO", null);
		httpSession.removeAttribute("userDTO");
		
		return "redirect:/tour/main";
	}
	
	@RequestMapping(value = "/join", method=RequestMethod.GET)
	public String join(Model model) {
		
		UserDTO userDTO = new UserDTO();
		
		model.addAttribute("userDTO", userDTO);
		
		return "/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult bResult, Model model) {
		
		if(bResult.hasErrors()) {
			return "join";
		}else {
			int ret = uService.userJoin(userDTO);
			return "redirect:/tour/main";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/idcheck", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public boolean userIdCheck(@RequestParam(value="u_id", required = false, defaultValue = "0")String u_id) {
		
		boolean idYes = uService.userIdCheck(u_id);
		log.debug("아이디 중복 검사 : " + idYes);
//		model.addAttribute("ID_YES", idYes);
//		model.addAttribute("u_id", u_id);
		
		
		return idYes;
		
	}
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@ResponseBody
	@RequestMapping(value="/pass", method=RequestMethod.GET)
	public String passwordTest(@RequestParam(value="strText",required=false, defaultValue="KOREA")String strText) {
		String cryptTest = passwordEncoder.encode(strText);
		long textLeng = cryptTest.length();
		
		return cryptTest + ":" + textLeng;
	}
	
	
}
