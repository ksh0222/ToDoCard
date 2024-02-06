package com.sparta.todocard.controller;

import com.sparta.todocard.dto.ToDoCardRequestDto;
import com.sparta.todocard.dto.ToDoCardResponseDto;
import com.sparta.todocard.dto.DeleteResponseDto;
import com.sparta.todocard.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ToDoCardController {
    private final PostService postService;

    // 1. 게시글 생성
    @PostMapping("/api/post")
    public ToDoCardResponseDto createPost(@RequestBody ToDoCardRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }
    // 2. 게시글 전체 목록 조회
    @GetMapping("/api/post")
    public List<ToDoCardResponseDto> getPostList(){
        return postService.getPostList();
    }
    // 3. 선택한 게시글 조회
    @GetMapping("/api/post/{id}")
    public ToDoCardResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
    // 4. 선택한 게시글 수정
    @PutMapping("api/post/{id}")
    public ToDoCardResponseDto update(@PathVariable Long id, @RequestBody ToDoCardRequestDto requestDto, HttpServletRequest request) {
        return postService.update(id, requestDto, request);
    }
    // 5. 선택한 게시글 삭제
    @DeleteMapping("api/post/{id}")
    public DeleteResponseDto delete(@PathVariable Long id, HttpServletRequest request) {
        return postService.delete(id, request);
    }

}
