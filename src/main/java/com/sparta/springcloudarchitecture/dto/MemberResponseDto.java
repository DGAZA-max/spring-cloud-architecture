package com.sparta.springcloudarchitecture.dto;

import com.sparta.springcloudarchitecture.members.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String mbti;
    private String profileImageUrl;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
        this.mbti = member.getMbti();
        this.profileImageUrl = member.getProfileImageUrl();
    }
}
