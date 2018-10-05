package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class Feedback {

    private Integer id;
    private String question_date;
    private String question_time;
    private String answer_date;
    private String answer_time;
    private String question;
    private String answer;
    private int status;

    public Feedback(String question_date, String question_time, String question, int status) {
        this.question_date = question_date;
        this.question_time = question_time;
        this.question = question;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getAnswer_date() {
        return answer_date;
    }

    public String getAnswer_time() {
        return answer_time;
    }

    public String getAnswer() {
        return answer;
    }

    public int getStatus() {
        return status;
    }
}
