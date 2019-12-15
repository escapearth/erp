package org.erp.rest.member;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.erp.exception.CustomMemberNotFoundException;
import org.erp.model.common.ListResult;
import org.erp.model.common.SingleResult;
import org.erp.model.member.MemberModel;
import org.erp.model.member.MemberStatusType;
import org.erp.model.user.UserModel;
import org.erp.repository.MemberRepository;
import org.erp.service.ResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"2. Member"})
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
                                     @RequestParam MemberStatusType memberStatusType
                                     ) {

        MemberModel member = MemberModel.builder()
                .memberEmail(email)
                .memberStatus(memberStatusType)
//                .userModel(UserModel.builder().userId("user1").build())
                .build();


    return responseService.getSingleResult(memberRepository.save(member));

    }

    @ApiOperation(value = "조직원 조회", notes = "조직원 조회")
    @GetMapping(value = "/listAll")
    public ListResult<MemberModel> findAllMember() {
        log.debug("");
        return responseService.getListResult(memberRepository.findAll());
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @GetMapping(value = "/listOne")
    @ApiOperation(value = "조직원 단일 조회", notes = "조직원 단일 조회")
    public SingleResult<MemberModel> findByMemberId(@ApiParam(value = "언어", defaultValue = "ko")
                                                    @RequestParam String lang,
                                                    @RequestParam String memberId) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String id = authentication.getName();

        return responseService.getSingleResult(memberRepository.findByMemberId(memberId).orElseThrow(CustomMemberNotFoundException::new));
    }
}
