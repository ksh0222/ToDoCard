package com.sparta.todocard.service;

import com.sparta.todocard.dto.CommentResponseDto;
import com.sparta.todocard.dto.DeleteResponseDto;
import com.sparta.todocard.dto.ToDoCardRequestDto;
import com.sparta.todocard.dto.ToDoCardResponseDto;
import com.sparta.todocard.entity.Comment;
import com.sparta.todocard.entity.ToDoCard;
import com.sparta.todocard.entity.User;
import com.sparta.todocard.jwt.JwtUtil;
import com.sparta.todocard.repository.CommentRepository;
import com.sparta.todocard.repository.PostRepository;
import com.sparta.todocard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    // 1. 게시글 생성
    @Transactional
    public ToDoCardResponseDto createPost(ToDoCardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        // 토큰이 있는 경우에만 글 작성 가능
        if (token != null) {
            // 토큰 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("로그인 해주세요")
            );
            ToDoCard post = new ToDoCard(requestDto, user.getUsername());
            post.setUser(user);
            // user.add(post);
            postRepository.save(post);
            return new ToDoCardResponseDto(post);
        }else {
            return null;
        }
    }
    // 2. 게시글 목록 조회
    @Transactional
    public List<ToDoCardResponseDto> getPostList() {
        List<ToDoCard> posts = postRepository.findAllByOrderByModifiedAtDesc();
        if(Collections.isEmpty(posts)) return null;
        List<ToDoCardResponseDto> results = new ArrayList<>();

        for(ToDoCard post : posts) {
            List<Comment> comments = commentRepository.findAllByTodocard(post);
            ToDoCardResponseDto todocardResponseDto = new ToDoCardResponseDto(post);
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            for (Comment comment : comments) {
                commentResponseDtos.add(new CommentResponseDto(comment));
            }
            todocardResponseDto.setCommentList(commentResponseDtos);
            results.add(todocardResponseDto);
        }
        return results;
    }

    // 3. 게시글 조회
    @Transactional
    public ToDoCardResponseDto getPost(Long id) {
        ToDoCard post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        ToDoCardResponseDto postDto = new ToDoCardResponseDto(post);

        List<Comment> comments = commentRepository.findAllByTodocard(post);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponseDtos.add(new CommentResponseDto(comment));
        }
        postDto.setCommentList(commentResponseDtos);
        return postDto;
    }

    //4. 게시글 수정
    @Transactional
    public ToDoCardResponseDto update(Long id, ToDoCardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // 토큰 검증
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다");
            }

            ToDoCard post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
            );
            post.update(requestDto);
            postRepository.save(post);
            return new ToDoCardResponseDto(post);
        }else {
            return null;
        }
    }
    //5. 게시글 삭제
    @Transactional
    public DeleteResponseDto delete(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // 토큰 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다");
            }

            ToDoCard post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("로그인 해주세요")
            );
            postRepository.deleteById(id);
            String response = "삭제완료!";
            return new DeleteResponseDto(response);
        }
        return null;
    }
}