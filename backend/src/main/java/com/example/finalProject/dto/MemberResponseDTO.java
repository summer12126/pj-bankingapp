package com.example.finalProject.dto;

import com.example.finalProject.domain.entity.MemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDTO {
	
	private String email;
	private String username;
	
	@Builder
	public MemberResponseDTO(MemberEntity member) {
		this.email = member.getEmail();
		this.username = member.getUsername();
	}

}
