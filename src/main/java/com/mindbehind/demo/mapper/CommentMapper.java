package com.mindbehind.demo.mapper;

import com.mindbehind.demo.model.Comment;
import com.mindbehind.demo.model.CommentBody;
import com.mindbehind.demo.model.CommentDto;
import com.mindbehind.demo.model.CommentId;
import com.mindbehind.demo.model.CommentPostId;
import com.mindbehind.demo.model.CommentRating;
import com.mindbehind.demo.model.CommentTitle;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;

/**
 * Created by semihunaldi on 29.06.2021
 */

@Mapper
public interface CommentMapper {

    List<Comment> toCommentList(List<CommentDto> commentDto);

    List<CommentDto> toDtoList(List<Comment> comment);

    Comment toComment(CommentDto commentDto);

    CommentDto toDto(Comment comment);

    default CommentId mapCommentId(Long value) {
        return new CommentId(value);
    }

    default CommentBody mapCommentBody(String value) {
        return new CommentBody(value);
    }

    default CommentPostId mapCommentPostId(Long value) {
        return new CommentPostId(value);
    }

    default CommentTitle mapCommentTitle(String value) {
        return new CommentTitle(value);
    }

    default CommentRating mapCommentRating(Integer value) {
        return new CommentRating(value);
    }

    default Long mapCommentId(CommentId commentId) {
        return Objects.nonNull(commentId) ? commentId.getValue() : null;
    }

    default String mapCommentBody(CommentBody commentBody) {
        return Objects.nonNull(commentBody) ? commentBody.getValue() : null;
    }

    default Long mapCommentPostId(CommentPostId commentPostId) {
        return Objects.nonNull(commentPostId) ? commentPostId.getValue() : null;
    }

    default String mapCommentTitle(CommentTitle commentTitle) {
        return Objects.nonNull(commentTitle) ? commentTitle.getValue() : null;
    }

    default Integer mapCommentRating(CommentRating commentRating) {
        return Objects.nonNull(commentRating) ? commentRating.getValue() : null;
    }
}
