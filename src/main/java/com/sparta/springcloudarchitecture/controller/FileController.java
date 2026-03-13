package com.sparta.springcloudarchitecture.controller;

import com.sparta.springcloudarchitecture.dto.FileDownloadUrlResponse;
import com.sparta.springcloudarchitecture.dto.FileUploadResponse;
import com.sparta.springcloudarchitecture.service.MemberService;
import com.sparta.springcloudarchitecture.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class FileController {

    private final S3Service s3Service;
    private final MemberService memberService;

    // 프로필 이미지 업로드
    @PostMapping("/{id}/profile-image")
    public ResponseEntity<FileUploadResponse> updateImage(
            @PathVariable("id") Long memberId,
            @RequestParam("file") MultipartFile file) {

        // 업로드 파일 s3 key 값
        String key = s3Service.upload(file);

        memberService.updateImageKey(memberId, key);
        return ResponseEntity.ok(new FileUploadResponse(key));
    }

    @GetMapping("/{id}/profile-image")
    public ResponseEntity<FileDownloadUrlResponse> getDownloadImage(
            @PathVariable("id") Long memberId) {

        String key = memberService.getDownloadImageKey(memberId);

        URL presignedUrl = s3Service.getDownloadUrl(key);
        return ResponseEntity.ok(new FileDownloadUrlResponse(presignedUrl.toString()));
    }
}