//package org.erp.rest.member;
//
//import io.swagger.annotations.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.erp.exception.CustomMemberNotFoundException;
//import org.erp.model.common.CommonResult;
//import org.erp.model.common.ListResult;
//import org.erp.model.common.SingleResult;
//import org.erp.model.member.MemberEntity;
//import org.erp.model.member.MemberStatusType;
//import org.erp.repository.MemberRepository;
//import org.erp.service.ResponseService;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@Api(tags = {"2. Member"})
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/member")
//public class MemberController {
//
//    private final ResponseService responseService;
//    private final MemberRepository memberRepository;
//
//        @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "조직원 추가", notes = "조직원 추가")
//    @PostMapping(value = "/add")
//    public SingleResult<MemberEntity> addToMember (@ApiParam(value = "조직원 이메일 ", required = true)
//                                     @RequestParam String email,
//                                     @ApiParam(value = "조직원 상태 ", required = true)
//                                     @RequestParam MemberStatusType memberStatusType
//                                     ) {
//
//        MemberEntity member = MemberEntity.builder()
//                .memberEmail(email)
//                .memberStatus(memberStatusType)
////                .userModel(UserModel.builder().userId("user1").build())
//                .build();
//
//    return responseService.getSingleResult(memberRepository.save(member));
//
//    }
//
//    @ApiOperation(value = "조직원 조회", notes = "조직원 조회")
//    @GetMapping(value = "/listAll")
//    public ListResult<MemberEntity> findAllMember() {
//        return responseService.getListResult(memberRepository.findAll());
//    }
//
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "조직원 단일 조회", notes = "조직원 단일 조회")
//    @GetMapping(value = "/listOne")
//    public SingleResult<MemberEntity> findByMemberId(@ApiParam(value = "언어", defaultValue = "ko")
//                                                    @RequestParam String lang) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String id = authentication.getName();
//
//        return responseService.getSingleResult(memberRepository.findByMemberId(id).orElseThrow(CustomMemberNotFoundException::new));
//    }
//
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "조직원 수정", notes = "조직원 수정")
//    @PutMapping(value = "/modified")
//    public SingleResult<MemberEntity> modifyMember(@ApiParam(value = "memberId", required = true)
//                                                  @RequestParam String memberId,
//                                                  @ApiParam(value = "조직원 이메일", required = true)
//                                                  @RequestParam String memberEmail,
//                                                  @ApiParam(value = "조직원 상태", required = true)
//                                                  @RequestParam MemberStatusType memberStatusType) {
//
//        MemberEntity MemberEntity = MemberEntity.builder()
//                .memberId(memberId)
//                .memberEmail(memberEmail)
//                .memberStatus(memberStatusType)
//                .build();
//
//        return responseService.getSingleResult(memberRepository.save(MemberEntity));
//    }
//
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @ApiOperation(value = "조직원 삭제", notes = "조직원 삭제")
//    @PutMapping(value = "/delete/{memberId}")
//    public CommonResult deleteMember(@ApiParam(value = "memberId", required = true)
//                                     @PathVariable String memberId) {
//        // TODO Soft Delete 로 변경할 것
//        memberRepository.deleteById(memberId);
//
//        return responseService.getSuccessResult();
//    }
//
//}
