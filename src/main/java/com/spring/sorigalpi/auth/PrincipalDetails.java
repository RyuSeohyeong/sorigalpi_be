package com.spring.sorigalpi.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.sorigalpi.entity.Member;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

	private final Member member;

	@Override
	public String getPassword() {
		return member.getPwd();
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() { // 계정이 만료되었는지 여부
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정이 잠겨있는지 여부
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 자격 증명이 만료되었는지 여부
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정이 활성화되어있는지 여부
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 사용자의 권한 목록을 반환
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		member.getRolesList().forEach(r -> {
			authorities.add(() -> r);
		});

		return authorities;
	}
}
