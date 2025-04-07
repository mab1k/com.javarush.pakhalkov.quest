package org.example.projectfinal31.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.projectfinal31.entity.Quest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVParserRepository {
    private List<Quest.Question> questions;

    public void parseAndStoreQuestions(String csvPath) {
        try {
            Reader reader = new InputStreamReader(Files.newInputStream(Paths.get(csvPath)), StandardCharsets.UTF_8);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withTrim());

            List<Quest.Question> questionList = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                Long questionId = Long.parseLong(record.get("IdQuestion"));
                String textQuestion = record.get("TextQuestion");
                String[] answers = record.get("TextAnswer").split(",");

                List<Quest.Answer> answerList = new ArrayList<>();
                for (int i = 0; i < answers.length; i++) {
                    Long answerId = questionId * 10 + (i + 1);
                    answerList.add(new Quest.Answer(answerId, questionId, answers[i].trim()));
                }

                Quest.Question question = new Quest.Question(questionId, textQuestion, answerList);
                questionList.add(question);
            }

            csvParser.close();

            this.questions = questionList;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Quest.Question> getQuestions() {
        return questions;
    }
}
