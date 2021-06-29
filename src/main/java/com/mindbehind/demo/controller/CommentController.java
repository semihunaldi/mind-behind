package com.mindbehind.demo.controller;

import com.mindbehind.demo.manager.CommentManager;
import com.mindbehind.demo.model.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created by semihunaldi on 28.06.2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/comments")
public class CommentController {

    private final CommentManager commentManager;

    @GetMapping(path = "/getComments")
    public ResponseEntity<List<CommentDto>> getComments() {
        return ResponseEntity.ok(commentManager.getComments());
    }

    @PostMapping(path = "/comment")
    public ResponseEntity<CommentDto> postComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentManager.postComment(commentDto));
    }

    @GetMapping(path = "/test")
    public ResponseEntity<CommentDto> test() {
        CommentDto commentDto = new CommentDto((long) new Random().nextInt(100), "test", 1L, "title", 1);
        return ResponseEntity.ok(commentManager.postComment(commentDto));
    }

    @GetMapping(path = "/test2")
    public ResponseEntity<CommentDto> test2() {
        CommentDto commentDto = new CommentDto((long) new Random().nextInt(100), "test", 1L, "title", 100);
        return ResponseEntity.ok(commentManager.postComment(commentDto));
    }
}
