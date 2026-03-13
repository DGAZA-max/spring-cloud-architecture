package com.sparta.springcloudarchitecture.repository;

import com.sparta.springcloudarchitecture.members.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
