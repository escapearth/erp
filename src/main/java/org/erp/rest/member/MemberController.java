package org.erp.rest.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.erp.model.common.CommonResult;
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
    private final UserRepository userRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "조직원 추가", notes = "조직원 추가")
    @PostMapping(value = "/add")
    public CommonResult addToMember (@ApiParam(value = "조직원 이메일 ", required = true)
                                     @RequestParam String email,
                                     @ApiParam(value = "조직원 상태 ", required = true)
                                     @RequestParam MemberStatusType memberStatusType,
                                     // TODO 추후 수정
                                     @ApiParam(value = "uid ", required = true)
                                     @RequestParam(value = "uid", defaultValue = "1") String uid,
                                     @ApiParam(value = "비밀번호", required = true)
                                     @RequestParam String password,
                                     @ApiParam(value = "이름", required = true)
                                     @RequestParam String name,
                                     @ApiParam(value = "msrl", required = true)
                                     @RequestParam(value = "msrl", defaultValue = "1") long msrl,
                                     @ApiParam(value = "사용자 이메일", required = true)
                                     @RequestParam String useremail
                                     ) {

        MemberModel member = new MemberModel();
        member.setMemberEmail(email);
        member.setMemberStatus(memberStatusType);

        userRepository.save(UserModel.builder()
                .uid(uid)
                .msrl(msrl)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(useremail)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());


        log.debug("member : > " + member);
        memberService.saveMemberModel(member);
    return responseService.getSuccessResult();
    }
}
