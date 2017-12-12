package com.example.zlata.quiz.entity;

/**
 * Created by Админ on 12.12.2017.
 */

public class Answer {

    public Long id;
    public String wrongAnswers[];
    public String correctAnswer;
    public String imageUrl;

    //   ArrayList<Answer> answers = ServerAnswers.get

    public Long getId() {
        return id;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWrongAnswers(String[] wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
