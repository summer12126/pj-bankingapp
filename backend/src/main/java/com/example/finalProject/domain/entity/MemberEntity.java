package com.example.finalProject.domain.entity;

import com.example.finalProject.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * model - 회원 정보 데이터를 저장할 회원 엔티티
 * db 설계
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@Table(name="member")
public class MemberEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false) // not null 제약조건
	private String password;

	
//	@Column
//	private String birth;
	
	@Column(nullable = false)
	private String email;
	
	@Column
	private String agegroup;
	
	@Column
	private String gender;
	
	@Column
	private String category;
	
//	@Enumerated(EnumType.STRING)
//	private Role role;
	
	@Builder
	public MemberEntity(Long id, String username, String password,String email, String agegroup, String gender, String category) {
	      this.id = id;
	        this.username = username;
	        this.password = password;
	        this.email = email;
	        this.agegroup = agegroup;
	        this.gender = gender;
	        this.category = category;
	}

}
