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
public class CommentDto {

    private Long id;

    private String body;

    private Long postId;

    private String title;

    private Integer rating;
}
