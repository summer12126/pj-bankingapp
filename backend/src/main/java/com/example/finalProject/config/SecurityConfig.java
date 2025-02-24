package com.example.finalProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.example.finalProject.service.MemberService;
import com.example.finalProject.service.MemberService5;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	private MemberService memberService;
		
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	        http
//	            .csrf().disable()
//	            .authorizeRequests()
//	                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()  // 로그인, 회원가입 페이지는 접근 허용
//	                .anyRequest().authenticated()
//	            .and()
//	            .formLogin()
//	                .loginPage("/login")           // 커스텀 로그인 페이지
//	                .defaultSuccessUrl("/")        // 로그인 성공 시 리다이렉트할 경로
//	                .permitAll()
//	            .and()
//	            .logout()
//	                .logoutSuccessUrl("/login")    // 로그아웃 성공 시 리다이렉트할 경로
//	                .permitAll();
//	        return http.build();
//	    }
	   
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http
//			.csrf().disable()
//			.headers()
//			.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
//		return http.build();
//	}
	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http.
//			authorizeHttpRequests((authorizeHttpRequests) ->
//			authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//			.csrf((csrf) -> csrf
//					.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
//			.headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
//					XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));
//		return http.build();
//	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	
	
//	  @Bean
//	    public WebSecurityCustomizer webSecurityCustomizer() {
//	        return (web) -> web.ignoring().requestMatchers("/예외처리하고 싶은 url", "/예외처리하고 싶은 url");
//	    }
	    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스들이 보안필터를 거치지 않게끔
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/font/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()                               // cors 방지
                .csrf().disable()                           // csrf 방지
                .headers().frameOptions().disable();        // x frame 방어 해제

        http.authorizeRequests()
                // 페이지 권한 설정
        .requestMatchers("/members/new").permitAll() // 누구나 접근 가능 
        .requestMatchers("/members/**").hasRole("USER") // USER 권한이 있어야 접근 가능
        // .requestMatchers("/dashboard/**").hasRole("USER") // USER 권한이 있어야 접근 가능         
        .requestMatchers("/dashboard/**").permitAll()
         
        .anyRequest().permitAll();
//                .anyRequest().permitAll();
        


        http.formLogin()
		        .loginPage("/login")
		        .loginProcessingUrl("/login")
		        .defaultSuccessUrl("/login/result")
		        .usernameParameter("email")
                .passwordParameter("password")
		        .failureUrl("/login?error=true");
                    
//                .and()
//                    .logout()
//                    .logoutSuccessUrl("/logout/result")
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID");

        // status code 핸들링
        http.exceptionHandling().accessDeniedPage("/denied");

        return http.build();
    }

    
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
    	auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
	
}
