package com.springboot.member.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member){
        verifyExistEmail(member.getEmail());
        return memberRepository.save(member);
    }
    public Member findMember(long memberId){
        return findVarifiedMember(memberId);
    }
    public Page<Member> findMembers(int page, int size){
        return memberRepository.findAll(PageRequest.of(page,size, Sort.Direction.DESC,"memberId"));
    }
    public void deleteMember(long memberId){
        Member findmember =findVarifiedMember(memberId);
        findmember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        memberRepository.save(findmember);
    }
    private void verifyExistEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
    private Member findVarifiedMember(long memberId){
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
