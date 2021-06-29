package com.mindbehind.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by semihunaldi on 28.06.2021
 */

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private CommentId id;

    private CommentBody body;

    private CommentPostId postId;

    private CommentTitle title;

    private CommentRating rating;
}
