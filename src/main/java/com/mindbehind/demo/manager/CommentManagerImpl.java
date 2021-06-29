package com.mindbehind.demo.manager;

import com.mindbehind.demo.exception.MindBehindCommentException;
import com.mindbehind.demo.mapper.CommentMapper;
import com.mindbehind.demo.model.Comment;
import com.mindbehind.demo.model.CommentDto;
import com.mindbehind.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by semihunaldi on 28.06.2021
 */

@Service
@RequiredArgsConstructor
public class CommentManagerImpl implements CommentManager {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getComments() {
        return commentMapper.toDtoList(commentService.fetchAllComments());
    }

    @Override
    public CommentDto postComment(CommentDto commentDto) throws MindBehindCommentException {
        if (Objects.isNull(commentDto)) {
            throw new MindBehindCommentException("comment can not be null");
        }
        if (StringUtils.isBlank(commentDto.getTitle()) || commentDto.getTitle().length() > 10) {
            throw new MindBehindCommentException("comment title is required and it should be not more than 10 characters");
        }
        if (StringUtils.isBlank(commentDto.getBody()) || commentDto.getBody().length() > 50) {
            throw new MindBehindCommentException("comment body is required and it should be not more than 50 characters");
        }
        if (Objects.nonNull(commentDto.getRating()) && (commentDto.getRating() > 5 || commentDto.getRating() < 0)) {
            throw new MindBehindCommentException("comment rating should have an integer value between 0 and 5");
        }
        Comment comment = commentMapper.toComment(commentDto);
        return commentMapper.toDto(commentService.saveComment(comment));
    }
}
