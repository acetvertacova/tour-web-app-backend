package com.tour_web_app.controller;

import com.tour_web_app.aop.annotation.LogEvent;
import com.tour_web_app.aop.enums.EventType;
import com.tour_web_app.dto.CommentDto;
import com.tour_web_app.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{tourId}")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable long tourId){
        List<CommentDto> comments = commentService.getAllByTourId(tourId);

        return ResponseEntity.ok(commentService.getAllByTourId(tourId));
    }

    @PostMapping()
    @LogEvent(eventType = EventType.COMMENT_CREATE)
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.create(commentDto);

        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/{id}")
    @LogEvent(eventType = EventType.COMMENT_UPDATE)
    @PreAuthorize("@commentSecurity.isCommentOwner(#id, authentication.name)")
    public ResponseEntity<CommentDto> update(@RequestBody CommentDto commentDto, @PathVariable long id) {
        CommentDto updatedComment = commentService.update(commentDto, id);

        return ResponseEntity.ok(updatedComment);
    }

    @PreAuthorize("@commentSecurity.isCommentOwner(#id, authentication.name)")
    @DeleteMapping("/{id}")
    @LogEvent(eventType = EventType.COMMENT_DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        commentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
