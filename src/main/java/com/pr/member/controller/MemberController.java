package com.pr.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pr.member.domain.MemberInfo;
import com.pr.member.dto.SessionMember;
import com.pr.member.repository.MemberRepository;
import com.pr.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@CrossOrigin
public class MemberController {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/memberInfo")
    public SessionMember getMemberInfo() {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("memberInfo");

        // 세션 정보가 존재할 경우 세션 정보 반환
        if (sessionMember != null) {
            System.out.println("@@로그인한 맴버 정보: " + sessionMember);
            return sessionMember;
        }
        return null;
    }

    /**
     * 닉네임 업데이트 API
     * @param sessionMember 클라이언트에서 전송된 세션 정보와 닉네임
     * @return 성공 메시지 또는 오류 메시지
     */
    @PostMapping("/updateNickName")
    public ResponseEntity<?> updateNickName2(@RequestBody SessionMember sessionMember) {
        try {
            // 서비스 호출을 통해 닉네임 업데이트 로직을 처리하도록 위임
            String result = memberService.updateNickName(sessionMember);

            return ResponseEntity.ok(result); // 성공 메시지 반환
        } catch (ResponseStatusException e) {
            // ResponseStatusException 처리
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            // 예기치 않은 오류 처리
            return ResponseEntity.status(500).body("닉네임 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}