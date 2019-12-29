package com.biz.mocoji.domain;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

	private String u_id;
	
	@Size(min = 8, max = 20, message="비밀번호는 8자리 이상 20자리 이하여야 합니다.")
	private String u_password;
	private String re_password;
	
}
