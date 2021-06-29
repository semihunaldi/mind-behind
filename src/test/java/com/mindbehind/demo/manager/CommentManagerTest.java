package com.mindbehind.demo.manager;

import com.mindbehind.demo.exception.MindBehindCommentException;
import com.mindbehind.demo.mapper.CommentMapper;
import com.mindbehind.demo.model.Comment;
import com.mindbehind.demo.model.CommentBody;
import com.mindbehind.demo.model.CommentDto;
import com.mindbehind.demo.model.CommentId;
import com.mindbehind.demo.model.CommentPostId;
import com.mindbehind.demo.model.CommentRating;
import com.mindbehind.demo.model.CommentTitle;
import com.mindbehind.demo.service.CommentService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by semihunaldi on 29.06.2021
 */

@ExtendWith(MockitoExtension.class)
public class CommentManagerTest {

    @Mock
    private CommentService commentService;

    private CommentManager commentManager;

    @BeforeEach
    void setup() {
        commentManager = new CommentManagerImpl(commentService, Mappers.getMapper(CommentMapper.class));
    }

    @Test
    public void test_getComments() {
        final Comment comment = prepareDummyComment();
        when(commentService.fetchAllComments()).thenReturn(Lists.newArrayList(comment));
        final List<CommentDto> comments = commentManager.getComments();
        Assertions.assertNotNull(comments);
        Assertions.assertEquals(1, comments.size());
        final CommentDto resultDto = comments.get(0);
        Assertions.assertEquals(comment.getBody().getValue(), resultDto.getBody());
        Assertions.assertEquals(comment.getId().getValue(), resultDto.getId());
        Assertions.assertEquals(comment.getTitle().getValue(), resultDto.getTitle());
        Assertions.assertEquals(comment.getPostId().getValue(), resultDto.getPostId());
        Assertions.assertEquals(comment.getRating().getValue(), resultDto.getRating());
    }

    @Test
    public void test_postComment() {
        final Comment comment = prepareDummyComment();
        when(commentService.saveComment(any())).thenReturn(comment);
        final CommentDto resultDto = commentManager.postComment(prepareDummyCommentDto());
        Assertions.assertEquals(comment.getBody().getValue(), resultDto.getBody());
        Assertions.assertEquals(comment.getId().getValue(), resultDto.getId());
        Assertions.assertEquals(comment.getTitle().getValue(), resultDto.getTitle());
        Assertions.assertEquals(comment.getPostId().getValue(), resultDto.getPostId());
        Assertions.assertEquals(comment.getRating().getValue(), resultDto.getRating());
    }

    @Test
    public void test_postComment_comment_null() {
        Assertions.assertThrows(MindBehindCommentException.class, () -> commentManager.postComment(null));
    }

    @Test
    public void test_postComment_title_null() {
        final CommentDto comment = prepareDummyCommentDto();
        comment.setTitle(null);
        Assertions.assertThrows(MindBehindCommentException.class, () -> commentManager.postComment(comment));
    }

    @Test
    public void test_postComment_title_long() {
        final CommentDto comment = prepareDummyCommentDto();
        comment.setTitle("1234567890_");
        Assertions.assertThrows(MindBehindCommentException.class, () -> commentManager.postComment(comment));
    }

    @Test
    public void test_postComment_body_null() {
        final CommentDto comment = prepareDummyCommentDto();
        comment.setBody(null);
        Assertions.assertThrows(MindBehindCommentException.class, () -> commentManager.postComment(comment));
    }

    @Test
    public void test_postComment_body_long() {
        final CommentDto comment = prepareDummyCommentDto();
        comment.setBody("12345678901234567890123456789012345678901234567890_");
        Assertions.assertThrows(MindBehindCommentException.class, () -> commentManager.postComment(comment));
    }

    @Test
    public void test_postComment_rating_out_of_range() {
        final CommentDto comment = prepareDummyCommentDto();
        comment.setRating(6);
        Assertions.assertThrows(MindBehindCommentException.class, () -> commentManager.postComment(comment));
    }

    @Test
    public void test_postComment_rating_out_of_range2() {
        final CommentDto comment = prepareDummyCommentDto();
        comment.setRating(-1);
        Assertions.assertThrows(MindBehindCommentException.class, () -> commentManager.postComment(comment));
    }

    private Comment prepareDummyComment() {
        final Comment comment = new Comment();
        comment.setBody(new CommentBody("body"));
        comment.setId(new CommentId(1L));
        comment.setTitle(new CommentTitle("title"));
        comment.setPostId(new CommentPostId(1L));
        comment.setRating(new CommentRating(1));
        return comment;
    }

    private CommentDto prepareDummyCommentDto() {
        final CommentDto commentDto = new CommentDto();
        commentDto.setBody("body");
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setRating(1);
        commentDto.setTitle("title");
        return commentDto;
    }
}
