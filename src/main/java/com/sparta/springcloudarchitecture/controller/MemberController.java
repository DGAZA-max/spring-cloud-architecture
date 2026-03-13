package com.sparta.springcloudarchitecture.controller;

import com.sparta.springcloudarchitecture.dto.MemberRequestDto;
import com.sparta.springcloudarchitecture.dto.MemberResponseDto;
import com.sparta.springcloudarchitecture.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/api/members")
    public ResponseEntity<MemberResponseDto> saveMember(@RequestBody MemberRequestDto requestDto){
        MemberResponseDto responseDto = memberService.saveMember(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/api/members/{id}")
    public ResponseEntity<MemberResponseDto> getMember(
            @PathVariable Long id
    ){
        MemberResponseDto responseDto = memberService.getMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
