package com.tour_web_app.dto.mapper;

import com.tour_web_app.dto.CommentDto;
import com.tour_web_app.entity.Comment;

public class CommentMapper {
    public static CommentDto commentToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .tourId(comment.getTour().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static Comment commentToEntity(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .createdAt(commentDto.getCreatedAt())
                .build();
    }
}
