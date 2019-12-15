package org.erp.service;

import lombok.RequiredArgsConstructor;
import org.erp.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


}
