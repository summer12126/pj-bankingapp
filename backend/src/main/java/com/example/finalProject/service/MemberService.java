package com.example.finalProject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.example.finalProject.domain.Role;
import com.example.finalProject.domain.entity.MemberEntity;
import com.example.finalProject.domain.repository.MemberRepository;
import com.example.finalProject.dto.MemberDTO;
import com.example.finalProject.dto.MemberResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@AllArgsConstructor
@Getter
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	// 회원가입을 처리하는 메소드
	@Transactional
	public Long join(MemberDTO memberdto) {
		MemberEntity user = MemberEntity.builder()
                .username(memberdto.getUsername())
                .password(passwordEncoder.encode(memberdto.getPassword())) // 비밀번호 암호화
                .email(memberdto.getEmail())
                .agegroup(memberdto.getAgegroup())
                .gender(memberdto.getGender())
                .category(memberdto.getCategory())
                .build();
//		SiteUser user = new SiteUser();
//		user.setUsername(memberdto.getUsername());
//		user.setPassword(passwordEncoder.encode(memberdto.getPassword()));
//		user.setBirth(memberdto.getBirth());
//		user.setEmail(memberdto.getEmail());
	
		
		return memberRepository.save(user).getId(); // 생성된 user의 id 반환
	}
	
	// 상세 정보를 조회하는 메소드
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
//		Optional<MemberEntity> member = memberRepository.findByEmail(email);
		
		MemberEntity member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
	
		
		return toUserDetails(member);
		
//		if(member == null) {
//			throw new UsernameNotFoundException(email);
//		}
//		
//		return User.builder()
//				.username(new MemberDTO().getEmail())
//				.password(new MemberDTO().getPassword())
//				.roles(Role.MEMBER.getValue())
//				.build();
		
//		MemberEntity memberEntity = member.orElse(null);
//		
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		
//		authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
//		
//		return new User(memberEntity.getEmail(), memberEntity.getPassword(),authorities);
	}
	
	// 사용자 정보를 제공하는 역할
	private UserDetails toUserDetails(MemberEntity member) {
		return User.builder()
		.username(member.getUsername())
		.password(member.getPassword())
		.authorities(new SimpleGrantedAuthority(Role.MEMBER.getValue()))
		.build();
	}
	
	// 회원 목록 조회
	public List<MemberResponseDTO> findMembers(){
		List<MemberEntity> all = memberRepository.findAll();
		List<MemberResponseDTO> members = new ArrayList<>();
		
		for(MemberEntity member : all) {
			MemberResponseDTO build = MemberResponseDTO.builder()
					.member(member)
					.build();
			members.add(build);
		}
		
		return members;
	}
	
	// 회원가입 시 유효성 체크
//	@Transactional(readOnly = true)
	public Map<String, String> validateHandling(Errors errors){
		Map<String, String> validatorResult = new HashMap<>();
		
		// 유효성 검사에 실패한 필드 목록을 받음
		// 유효성 검사에 실패한 필드들은 Map 자료구조를 통해 키값과 에러 메시지를 응답함
		// key : valid_{dto 필드명} , Message : dto에서 작성한 message 값
		// 유효성 검사에 실패한 필드 목록을 받아 미리 정의된 메시지를 가져와 Map에 넣어줌
		for(FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		
		return validatorResult;
	}
	
	// 이메일 중복 체크
	@Transactional(readOnly = true)
	public void checkEmailDuplication(MemberDTO dto) {
		boolean emailDuplicate = memberRepository.existsByEmail(dto.getEmail());
		if(emailDuplicate) {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		}
	}

}
