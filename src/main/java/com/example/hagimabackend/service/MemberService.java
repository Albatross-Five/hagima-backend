package com.example.hagimabackend.service;

import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private UUID createUUID() {
        return UUID.randomUUID();
    }

    public Member register() {
        Member member = Member.builder().uuid(createUUID().toString()).build();
        memberRepository.save(member);
        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUUID(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with uuid : " + uuid));

        return User.builder()
                .username(member.getKakao_id())
                .password("")
                .authorities("ROLE_USER")
                .build();
    }
}
