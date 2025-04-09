package org.example.com.javarush.pakhalkov.quest.repository;

import org.example.com.javarush.pakhalkov.quest.entity.Quest;
import org.example.com.javarush.pakhalkov.quest.service.QuestService;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.*;
import java.util.List;

public class Repository {
    private List<Quest.Question> questions;
    private String filePath;
    private QuestService  questService;

    public List<Quest.Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Quest.Question> questions) {
        this.questions = questions;
    }

    public Repository(String nameFileConfig) throws IOException {
        questService = new QuestService();
        Jsonb jsonb = JsonbBuilder.create();
        InputStream configStream = getClass().getClassLoader().getResourceAsStream(nameFileConfig);

        assert configStream != null;
        Config config = jsonb.fromJson(new InputStreamReader(configStream), Config.class);

        String csvFileName = config.nameDataBase;

        filePath = getClass().getClassLoader().getResource(csvFileName).getPath();
        filePath = filePath.replaceFirst("^/", "");

        questService.parseAndStoreQuestions(filePath);

        this.questions = questService.getQuestions();
    }
}
