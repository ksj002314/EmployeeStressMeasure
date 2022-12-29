package com.project.EmployeeStressMeasure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.project.EmployeeStressMeasure.service.MemberService;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	private final MemberService memberService;
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		// static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		
		   http
	          .authorizeRequests() // 6
	            .antMatchers("/login", "/signup", "/user").permitAll() // 누구나 접근 허용
	            .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
	            .antMatchers("/measure/**").hasRole("ADMIN") // ADMIN만 접근 가능
	            .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
	        .and() 
	          .formLogin() // 7
	            .loginPage("/login") // 로그인 페이지 링크
	            .defaultSuccessUrl("/employees") // 로그인 성공 후 리다이렉트 주소
	        .and()
	          .logout() // 8
	            .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
		    .invalidateHttpSession(true) // 세션 날리기
		    .and()
		    .exceptionHandling().accessDeniedPage("/access-error");
	    ;
	  }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

}


/*
authorizeRequests() : 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미합니다.

permitAll() : 모든 사용자가 인증(로그인)없이 해당 경로를 접근할 수 있도록 설정합니다.

hasRole("ADMIN") : 해당 권한을 가진 사용자만 경로에 접근할 수 있습니다.

authenticationEntryPoint(new CustomAuthenticationEntryPoint()) : 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록합니다.
*/