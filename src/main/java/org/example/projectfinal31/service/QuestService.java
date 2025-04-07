package org.example.projectfinal31.service;

import org.example.projectfinal31.entity.Quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestService {

    public List<Quest.Question> createQuestionsFromParsedData(Map<Integer, Map<String, Integer>> parsedData) {
        List<Quest.Question> questions = new ArrayList<>();

        for (Map.Entry<Integer, Map<String, Integer>> entry : parsedData.entrySet()) {
            Long questionId = Long.valueOf(entry.getKey());
            String textQuestion = "Question for ID: " + questionId; // Здесь вы можете заменить на реальный текст вопроса, если он у вас есть

            List<Quest.Answer> answers = new ArrayList<>();
            for (Map.Entry<String, Integer> answerEntry : entry.getValue().entrySet()) {
                Long answerId = Long.valueOf(answerEntry.getValue());
                String answerText = answerEntry.getKey();
                answers.add(new Quest.Answer(answerId, questionId, answerText));
            }

            Quest.Question question = new Quest.Question(questionId, textQuestion, answers);
            questions.add(question);
        }

        return questions;
    }
}
