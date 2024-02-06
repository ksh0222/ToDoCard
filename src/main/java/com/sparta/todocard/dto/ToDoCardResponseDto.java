package com.sparta.todocard.dto;

import com.sparta.todocard.entity.Comment;
import com.sparta.todocard.entity.ToDoCard;

import java.time.LocalDateTime;
import java.util.List;

public class ToDoCardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;

    public ToDoCardResponseDto (ToDoCard todocard) {
        this.id = todocard.getId();
        this.title = todocard.getTitle();
        this.content = todocard.getContent();
        this.username = todocard.getUsername();
        this.createdAt = todocard.getCreatedAt();
        this.modifiedAt = todocard.getModifiedAt();
    }
    public ToDoCardResponseDto(ToDoCard todocard, Comment comment) {
        this.id = todocard.getId();
        this.title = todocard.getTitle();
        this.content = todocard.getContent();
        this.username = todocard.getUsername();
        this.createdAt = todocard.getCreatedAt();
        this.modifiedAt = todocard.getModifiedAt();
    }

    public void setCommentList(List<CommentResponseDto> commentResponseDtos) {
    }
}
