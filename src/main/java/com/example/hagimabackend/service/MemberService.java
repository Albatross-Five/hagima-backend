package com.example.hagimabackend.service;

import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private UUID createUUID() {
        return UUID.randomUUID();
    }

    public Member register() {
        Member member = Member.builder().uuid(createUUID().toString()).build();
        memberRepository.save(member);
        return member;
    }
}
