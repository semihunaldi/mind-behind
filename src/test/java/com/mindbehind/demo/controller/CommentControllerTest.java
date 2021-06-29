package com.mindbehind.demo.controller;

import com.mindbehind.demo.MindBehindBaseTest;
import com.mindbehind.demo.exception.MindBehindCommentException;
import com.mindbehind.demo.manager.CommentManager;
import com.mindbehind.demo.model.CommentDto;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by semihunaldi on 29.06.2021
 */

@WebMvcTest(CommentController.class)
public class CommentControllerTest extends MindBehindBaseTest {

    @MockBean
    private CommentManager commentManager;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_getComments_empty() throws Exception {
        when(commentManager.getComments()).thenReturn(Lists.newArrayList());
        this.mockMvc.perform(get("/api/comments/getComments"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().string("[]"));
    }

    @Test
    public void test_getComments() throws Exception {
        when(commentManager.getComments()).thenReturn(Lists.newArrayList(prepareDummyComment()));
        this.mockMvc.perform(get("/api/comments/getComments"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$[0].body").value("body"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].postId").value(1L))
                .andExpect(jsonPath("$[0].rating").value(1))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

    @Test
    public void test_comment() throws Exception {
        when(commentManager.postComment(any())).thenReturn(prepareDummyComment());
        this.mockMvc.perform(post("/api/comments/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prepareDummyComment())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.body").value("body"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.rating").value(1))
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    public void test_comment_error() throws Exception {
        when(commentManager.postComment(any())).thenThrow(new MindBehindCommentException("test error message"));
        this.mockMvc.perform(post("/api/comments/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prepareDummyComment())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorDescription").value("test error message"));
    }

    private CommentDto prepareDummyComment() {
        final CommentDto commentDto = new CommentDto();
        commentDto.setBody("body");
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setRating(1);
        commentDto.setTitle("title");
        return commentDto;
    }
}
