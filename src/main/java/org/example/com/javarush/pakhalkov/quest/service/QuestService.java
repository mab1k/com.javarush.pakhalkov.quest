package org.example.com.javarush.pakhalkov.quest.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.com.javarush.pakhalkov.quest.entity.Quest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QuestService {

    private List<Quest.Question> questions;

    public List<Quest.Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Quest.Question> questions) {
        this.questions = questions;
    }

    public void parseAndStoreQuestions(String csvPath) throws IOException {
        try (Reader reader = new InputStreamReader(Files.newInputStream(Paths.get(csvPath)), StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withTrim())) {

            if (csvParser.getHeaderNames() == null || !csvParser.getHeaderNames().containsAll(List.of("IdQuestion", "TextQuestion", "TextAnswer"))) {
                throw new IllegalArgumentException("Invalid CSV format: Missing required headers.");
            }

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

            this.questions = questionList;

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
