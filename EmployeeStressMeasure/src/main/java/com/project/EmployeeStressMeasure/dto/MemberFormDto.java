package com.project.EmployeeStressMeasure.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.project.EmployeeStressMeasure.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberFormDto {

	@NotBlank(message ="이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message = "아이디는 필수 입력 값입니다.")
	private String userid;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
	private String password;
	
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String address;
	
	private String auth;
	
	@Builder
	public MemberFormDto( String name, String userid, String password, String address, String auth) {

		this.name = name;
		this.userid = userid;
		this.password = password;
		this.address = address;
		this.auth = auth;
	}
	
	public Member toEntity() {
		return Member.builder()
				.userid(userid)
				.name(name)
				.password(password)
				.address(address)
				.auth(auth)
				.build();
	}
}
