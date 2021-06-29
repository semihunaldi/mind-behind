package com.mindbehind.demo.service;

import com.mindbehind.demo.model.Comment;

import java.util.List;

/**
 * Created by semihunaldi on 28.06.2021
 */
public interface CommentService {
    List<Comment> fetchAllComments();

    Comment saveComment(Comment comment);
}
