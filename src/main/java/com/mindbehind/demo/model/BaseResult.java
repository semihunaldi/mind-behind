package com.mindbehind.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by semihunaldi on 29.06.2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult {

    private int status;

    private String errorDescription;
}
