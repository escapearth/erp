package org.erp.service;

import lombok.RequiredArgsConstructor;
import org.erp.model.member.MemberModel;
import org.erp.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Find a model
     *
     * @param id
     * @return model
     */
    @Transactional(readOnly = true)
    public MemberModel findMemberModel(@NotBlank @Size(min = 36, max = 36) Long id) {
        return memberRepository.findById(id).get();
    }


    @Transactional
    public MemberModel saveMemberModel(@NotNull MemberModel model) {
        return memberRepository.save(model);
    }
}
