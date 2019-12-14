package org.erp.rest.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.erp.model.common.CommonResult;
import org.erp.model.common.SingleResult;
import org.erp.model.member.MemberModel;
import org.erp.model.member.MemberStatusType;
import org.erp.model.user.UserModel;
import org.erp.repository.MemberRepository;
import org.erp.repository.UserRepository;
import org.erp.service.MemberService;
import org.erp.service.ResponseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@Api(tags = {"1. Member"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final ResponseService responseService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "조직원 추가", notes = "조직원 추가")
    @PostMapping(value = "/add")
    public SingleResult<MemberModel> addToMember (@ApiParam(value = "조직원 이메일 ", required = true)
                                     @RequestParam String email,
                                     @ApiParam(value = "조직원 상태 ", required = true)
                                     @RequestParam MemberStatusType memberStatusType,
                                                  @ApiParam(value = "msrl ", required = true)
                                                      @RequestParam(value = "msrl", defaultValue = "1") long msrl,
                                                  @ApiParam(value = "memberId", required = true)
                                                      @RequestParam(value = "memberId", defaultValue = "1") long memberId
                                     ) {

        MemberModel member = MemberModel.builder()
                .memberEmail(email)
                .memberStatus(memberStatusType)
                .memberId(memberId)
                // TODO 리팩토링필요 UserModel 연관관계
                .userModel(UserModel.builder().msrl(msrl).email("emailtest").name("nametest").uid("uidtest").build())
                .build();

    return responseService.getSingleResult(memberRepository.save(member));
    }
}
