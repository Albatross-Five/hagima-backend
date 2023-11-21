package com.example.hagimabackend.service;

import com.example.hagimabackend.entity.CustomUserDetails;
import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public Member register() {
        Member member = new Member();
        memberRepository.save(member);
        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUUID(UUID.fromString(uuid))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with uuid : " + uuid));
        return CustomUserDetails.builder()
                .username(member.getUuid().toString())
                .password("")
                .member(member)
                .build();
    }

    public Member getMemberByHeader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getMember();
    }
}
