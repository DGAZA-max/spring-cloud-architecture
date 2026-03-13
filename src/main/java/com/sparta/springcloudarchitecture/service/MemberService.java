package com.sparta.springcloudarchitecture.service;

import com.sparta.springcloudarchitecture.dto.MemberRequestDto;
import com.sparta.springcloudarchitecture.dto.MemberResponseDto;
import com.sparta.springcloudarchitecture.members.Member;
import com.sparta.springcloudarchitecture.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto saveMember(MemberRequestDto requestDto) {
        Member member = memberRepository.save(new Member(
                requestDto.getName(),
                requestDto.getAge(),
                requestDto.getMbti()
        ));
        return new MemberResponseDto(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 팀원이 없습니다."));
        return new MemberResponseDto(member);
    }


    @Transactional
    public void updateImageKey(Long memberId, String key) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));

        member.updateProfileImage(key);
    }

    @Transactional(readOnly = true)
    public String getDownloadImageKey(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));

        String key = member.getProfileImageUrl();
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("등록된 프로필 이미지가 없습니다.");
        }

        return key;
    }
}
