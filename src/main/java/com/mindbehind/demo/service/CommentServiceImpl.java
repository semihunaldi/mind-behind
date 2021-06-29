package com.mindbehind.demo.service;

import com.mindbehind.demo.config.MindBehindProperties;
import com.mindbehind.demo.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by semihunaldi on 28.06.2021
 */

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final RestTemplate restTemplate;

    private final MindBehindProperties mindBehindProperties;

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Comment> fetchAllComments() {
        final ResponseEntity<Comment[]> commentListResponseEntity = restTemplate.getForEntity(mindBehindProperties.getCommentsServiceURL(), Comment[].class);
        if (commentListResponseEntity.getStatusCode() != HttpStatus.OK || Objects.isNull(commentListResponseEntity.getBody())) {
            return new ArrayList<>(0);
        }
        return Arrays.asList(commentListResponseEntity.getBody());
    }

    @Override
    public Comment saveComment(Comment comment) {
        return mongoTemplate.save(comment, "comments");
    }
}
