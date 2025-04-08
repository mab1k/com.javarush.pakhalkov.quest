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

    public List<Quest.Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Quest.Question> questions) {
        this.questions = questions;
    }

    public CSVParserRepository(List<Quest.Question> questions) {
        this.questions = questions;
    }
}
