package com.mindbehind.demo.service;

import com.mindbehind.demo.config.MindBehindProperties;
import com.mindbehind.demo.model.Comment;
import com.mindbehind.demo.model.CommentBody;
import com.mindbehind.demo.model.CommentId;
import com.mindbehind.demo.model.CommentPostId;
import com.mindbehind.demo.model.CommentRating;
import com.mindbehind.demo.model.CommentTitle;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by semihunaldi on 29.06.2021
 */

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private final MindBehindProperties mindBehindProperties = prepareMindBehindProperties();

    @Mock
    private MongoTemplate mongoTemplate;

    private CommentService commentService;

    @BeforeEach
    public void setup() {
        commentService = new CommentServiceImpl(restTemplate, mindBehindProperties, mongoTemplate);
    }

    @Test
    public void test_fetchAllComments() {
        final Comment comment = prepareDummyComment();
        when(restTemplate.getForEntity(eq("http://locahost/test"), eq(Comment[].class))).thenReturn(ResponseEntity.ok(Arrays.array(comment)));
        final List<Comment> comments = commentService.fetchAllComments();
        Assertions.assertFalse(comments.isEmpty());
        Assertions.assertEquals(comments.get(0).getBody().getValue(), comment.getBody().getValue());
        Assertions.assertEquals(comments.get(0).getId().getValue(), comment.getId().getValue());
        Assertions.assertEquals(comments.get(0).getTitle().getValue(), comment.getTitle().getValue());
        Assertions.assertEquals(comments.get(0).getPostId().getValue(), comment.getPostId().getValue());
        Assertions.assertEquals(comments.get(0).getRating().getValue(), comment.getRating().getValue());
    }

    @Test
    public void test_fetchAllComments_empty() {
        when(restTemplate.getForEntity(eq("http://locahost/test"), eq(Comment[].class))).thenReturn(ResponseEntity.ok(Arrays.array()));
        final List<Comment> comments = commentService.fetchAllComments();
        Assertions.assertTrue(comments.isEmpty());
    }

    @Test
    public void test_fetchAllComments_http_status_Error() {
        when(restTemplate.getForEntity(eq("http://locahost/test"), eq(Comment[].class))).thenReturn(ResponseEntity.badRequest().build());
        final List<Comment> comments = commentService.fetchAllComments();
        Assertions.assertTrue(comments.isEmpty());
    }

    @Test
    public void test_fetchAllComments_null() {
        when(restTemplate.getForEntity(eq("http://locahost/test"), eq(Comment[].class))).thenReturn(ResponseEntity.ok(null));
        final List<Comment> comments = commentService.fetchAllComments();
        Assertions.assertTrue(comments.isEmpty());
    }

    @Test
    public void test_saveComment() {
        when(mongoTemplate.save(any(), eq("comments"))).thenReturn(prepareDummyComment());
        final Comment comment = commentService.saveComment(prepareDummyComment());
        Assertions.assertNotNull(comment);
    }

    private MindBehindProperties prepareMindBehindProperties() {
        final MindBehindProperties mindBehindProperties = new MindBehindProperties();
        mindBehindProperties.setCommentsServiceURL("http://locahost/test");
        return mindBehindProperties;
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

}
