package org.example.projectfinal31.entity;

import java.util.List;

public class Quest {
    private static Long id;

    public Quest() {
    }

    public static Long getId() {
        return id;
    }

    public static void setId(Long id) {
        Quest.id = id;
    }

    public static class Question{
        Long id;
        String text;
        List<Answer> answers;

        public Question(Long id, String text, List<Answer> answers) {
            this.id = id;
            this.text = text;
            this.answers = answers;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<Answer> getAnswers() {
            return answers;
        }

        public void setAnswers(List<Answer> answers) {
            this.answers = answers;
        }
    }


    public static class Answer{
        Long id;
        String text;
        Long questionId;


        public Answer(Long id, Long questionId, String text) {
            this.id = id;
            this.questionId = questionId;
            this.text = text;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
