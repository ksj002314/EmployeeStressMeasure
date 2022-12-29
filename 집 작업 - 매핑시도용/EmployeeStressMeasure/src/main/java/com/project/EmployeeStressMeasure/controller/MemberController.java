package com.project.EmployeeStressMeasure.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.EmployeeStressMeasure.domain.Member;
import com.project.EmployeeStressMeasure.dto.MemberFormDto;
import com.project.EmployeeStressMeasure.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	
	private final MemberService memberService;

	
	@GetMapping(value = "/login")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/login";
	}
	
	@PostMapping(value ="/login")
	public String memberForm(MemberFormDto memberFormDto) { {
			
		memberService.save(memberFormDto);
		
		return "redirect:/";
		}
	}
	
	
	// 회원 가입
	@PostMapping("/user")
	public String signup(MemberFormDto memberFormDto) {
		memberService.save(memberFormDto);
		return "redirect:/login";
	}
	
	
	
	@GetMapping(value ="/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}
	
	
	
	
	// 에러 처리
	@GetMapping(value="/access-error")
	public String errorPage() {
		return "error/403";
	}
	
}
