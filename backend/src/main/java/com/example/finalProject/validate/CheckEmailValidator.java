package com.example.finalProject.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.example.finalProject.domain.repository.MemberRepository;
import com.example.finalProject.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<MemberDTO> {
	
	private final MemberRepository memberRepository;
	
	@Override
	protected void doValidate(MemberDTO dto, Errors errors) {
		if(memberRepository.existsByEmail(dto.getEmail())) {
			errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일입니다.");
		}
	}
	
	

}
