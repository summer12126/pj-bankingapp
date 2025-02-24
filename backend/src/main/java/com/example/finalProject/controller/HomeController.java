package com.example.finalProject.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.finalProject.dto.MemberDTO;
import com.example.finalProject.dto.MemberResponseDTO;
import com.example.finalProject.service.MemberService;
import com.example.finalProject.validate.CheckEmailValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	
	private final MemberService memberService;
	private final CheckEmailValidator checkEmailValidator;
	

	// 메인페이지
	@GetMapping("/")
	public String Home() {
		return "index";
	}
	
	@GetMapping("/loginMain")
	public String loginMain() {
		return "members/loginMain"; //view
	}
	
	// 로그인페이지
	@GetMapping("/login")
	public String login() {
		return "members/login";
	}
	
	// 로그인 결과 페이지
	@GetMapping("/login/result")
    public String dispLoginResult(Model model, HttpSession session) {
        // 인증된 사용자 ID 가져오기
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userId);

        // 세션에 userId 저장
        session.setAttribute("userId2", userId);

        // 모델에 userId 추가 (화면에서 직접 사용할 경우)
        model.addAttribute("userId", userId);
//        return "members/loginSuccess";
        return "redirect:/";
    }

	
	@GetMapping("/logout/result")
	public String dispLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		System.out.println("logout");
		
		return "redirect:/";
	}
	
//    @GetMapping(value = "/login/error")
//    public String loginError(Model model){
//        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
//        return "home";
//    }
	
	
    // 회원가입 페이지
	@GetMapping("/join")
	public String createMemberForm(Model model, MemberDTO memberdto) {
		// addAttribute ; 모델에 값을 지정
	    model.addAttribute("formData", memberdto); // Member 객체 저장
	    return "members/join"; // view
	}
	
	// 커스텀 유효성 검증을 위해 추가
	@InitBinder // 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkEmailValidator);
	}

	// 회원가입 처리
	@PostMapping("/join/result")
	public String createMember(@Valid MemberDTO memberdto, Errors errors, Model model) {
		
		model.addAttribute("formData",memberdto);
		
		if(errors.hasErrors()) { // 유효성 검사에 실패한 필드가 있는지 확인
			// memberdto를 담아줘서 회원가입 실패 시, 회원가입 페이지에서 입력했던 정보들을 그대로 유지하기 위해 입력받았던 데이터를 그대로 할당해줌
			model.addAttribute("memberdto", memberdto); 
			
			// 유효성 검사에 실패한 필드가 있다면, Service 계층으로 Errors 객체를 전달해 비지니스 로직을 구현하고 모델에 담음
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			
			return "members/join";
		}
		
		memberService.join(memberdto);
		return "members/login";
	}
	
	@GetMapping("/members")
	public String members(Model model) {
		List<MemberResponseDTO> members = memberService.findMembers();
		model.addAttribute("members",members);
		
		return "/members/memberList";
	}
	
	@GetMapping("/category/store")
	public String store() {
		return "/category/store";
	}
	
	@GetMapping("/category/cafe")
	public String cafe() {
		return "/category/cafe";
	}
	
	@GetMapping("/category/gas")
	public String gas() {
		return "/category/gas";
	}
	
	@GetMapping("/category/movie")
	public String movie() {
		return "/category/movie";
	}
	
	@GetMapping("/category/mart")
	public String mart() {
		return "/category/mart";
	}
	
	@GetMapping("/category/search")
	public String search() {
		return "/category/search";
	}

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            log.info("Dashboard controller called");
            return "dashboard/dashboard";  
        } catch (Exception e) {
            log.error("Error in dashboard controller", e);
            return "error";
        }
    }

}

