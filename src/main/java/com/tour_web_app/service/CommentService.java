package com.tour_web_app.service;

import com.tour_web_app.dto.CommentDto;
import com.tour_web_app.dto.mapper.CommentMapper;
import com.tour_web_app.entity.Comment;
import com.tour_web_app.exception.NotFoundException;
import com.tour_web_app.repository.CommentRepository;
import com.tour_web_app.repository.TourRepository;
import com.tour_web_app.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    public List<CommentDto> getAllByTourId(Long tourId) {
        List<Comment> comments = commentRepository.findByTourId(tourId);
        return comments.stream()
                .map(CommentMapper::commentToDto)
                .toList();
    }

    public CommentDto create(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .user(userRepository.findByUsername(commentDto.getUsername()).orElseThrow(() -> new ValidationException("User not found")))
                .tour(tourRepository.findById(commentDto.getTourId())
                        .orElseThrow(() -> new NotFoundException("Tour not found")))
                .build();
        commentRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    public CommentDto update(CommentDto commentDto, Long id){
        Comment commentToUpdate = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour not found"));

        commentToUpdate.setContent(commentDto.getContent());
        commentRepository.save(commentToUpdate);
        return CommentMapper.commentToDto(commentToUpdate);
    }

    public void deleteById(Long commentId){
        commentRepository.deleteById(commentId);
    }
}
