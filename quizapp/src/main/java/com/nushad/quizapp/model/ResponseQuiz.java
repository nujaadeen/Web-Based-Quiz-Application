package com.nushad.quizapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseQuiz {
    private Integer id;
    private String response;
}
