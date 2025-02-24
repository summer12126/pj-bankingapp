package com.example.finalProject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	
	
	private Long id;
	
	@NotBlank(message = "이름을 입력해주세요.")
	private String username;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;
	
//	@NotBlank(message = "생년월일을 입력해주세요.")
//	private String birth;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "올바른 이메일 주소를 입력해주세요.")
	private String email;
	
	private String agegroup;
	private String gender;
	private String category;
	
//    public MemberEntity toEntity(){
//        return MemberEntity.builder()
//                .id(id)
//                .email(email)
//                .password(password)
//                .build();
//    }

}
