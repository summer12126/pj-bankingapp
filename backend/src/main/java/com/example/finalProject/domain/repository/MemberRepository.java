package com.example.finalProject.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalProject.domain.entity.MemberEntity;


@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	
	// email의 존재 여부 확인
	boolean existsByEmail(String email);
	
	Optional<MemberEntity> findByEmail(String email);

}
