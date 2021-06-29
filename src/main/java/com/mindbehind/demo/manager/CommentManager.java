package com.mindbehind.demo.manager;

import com.mindbehind.demo.exception.MindBehindCommentException;
import com.mindbehind.demo.model.CommentDto;

import java.util.List;

/**
 * Created by semihunaldi on 28.06.2021
 */
public interface CommentManager {
    List<CommentDto> getComments();

    CommentDto postComment(CommentDto commentDto) throws MindBehindCommentException;
}
