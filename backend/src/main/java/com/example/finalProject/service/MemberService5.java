package com.example.finalProject.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.example.finalProject.domain.entity.MemberEntity;
import com.example.finalProject.domain.repository.MemberRepository;
import com.example.finalProject.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

/**
 * 회원 정보 데이터 저장할 회원 엔티티 
 */


public interface MemberService5 {
	
	Long join(MemberDTO memberdto);
	
	Map<String, String> validateHandling(Errors errors);

}
