package org.example.com.javarush.pakhalkov.quest.service;

import org.example.com.javarush.pakhalkov.quest.entity.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestServiceTest {

    private QuestService questService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        questService = new QuestService();
    }


    @Test
    public void testParseAndStoreQuestions_EmptyCSV() throws IOException {

        Path tempFile = Files.createTempFile("test-empty-csv", ".csv");

        Files.write(tempFile, "IdQuestion,TextQuestion,TextAnswer\n".getBytes(StandardCharsets.UTF_8));

        questService.parseAndStoreQuestions(tempFile.toString());

        List<Quest.Question> questions = questService.getQuestions();

        assertNotNull(questions);
        assertEquals(0, questions.size());

        Files.delete(tempFile);
    }


    @Test
    public void testParseAndStoreQuestionAnswer_ValidCSV() throws IOException {
        Path tempFile = Files.createTempFile("test-valid-csv", ".csv");

        String csvContent = "IdQuestion,TextQuestion,TextAnswer\n" +
                "1,What is your name?,John";
        Files.write(tempFile, csvContent.getBytes(StandardCharsets.UTF_8));

        questService.parseAndStoreQuestions(tempFile.toString());

        List<Quest.Question> questions = questService.getQuestions();

        assertNotNull(questions);
        assertEquals(1, questions.size());
        Quest.Question question = questions.get(0);
        assertEquals("What is your name?", question.getText());
        assertEquals(1, question.getAnswers().size());
        assertEquals("John", question.getAnswers().get(0).getText());

        Files.delete(tempFile);
    }

    @Test
    public void testParseAndStoreQuestionAnswers_ValidCSV() throws IOException {
        Path tempFile = Files.createTempFile("test-valid-csv", ".csv");

        String csvContent = "IdQuestion,TextQuestion,TextAnswer\n" +
                "1,What is your name?,\"John,Jane\"";
        Files.write(tempFile, csvContent.getBytes(StandardCharsets.UTF_8));

        questService.parseAndStoreQuestions(tempFile.toString());

        List<Quest.Question> questions = questService.getQuestions();

        assertNotNull(questions);
        assertEquals(1, questions.size());
        Quest.Question question = questions.get(0);
        assertEquals("What is your name?", question.getText());
        assertEquals(2, question.getAnswers().size());

        Files.delete(tempFile);
    }

    @Test
    public void testParseAndStoreQuestions_InvalidFileFormat() throws IOException {
        Path tempFile = Files.createTempFile("test-invalid-file-format", ".txt");

        String invalidContent = "Это не CSV файл, а просто текст!";
        Files.write(tempFile, invalidContent.getBytes(StandardCharsets.UTF_8));

        Exception exception = assertThrows(Exception.class, () -> questService.parseAndStoreQuestions(tempFile.toString()));

        assertTrue(exception instanceof IOException || exception instanceof IllegalArgumentException,
                "Unexpected exception type: " + exception.getClass());

        Files.delete(tempFile);
    }

    @Test
    public void testParseAndStoreQuestions_InvalidColumns() throws IOException {
        Path tempFile = Files.createTempFile("test-invalidColumns-file-format", ".txt");
        String csvContent = "1,2,3\n" +
                "1,What is your name?,\"John,Jane\"";
        Files.write(tempFile, csvContent.getBytes(StandardCharsets.UTF_8));

        Exception exception = assertThrows(Exception.class, () -> questService.parseAndStoreQuestions(tempFile.toString()));

        assertTrue(exception instanceof IOException || exception instanceof IllegalArgumentException,
                "Unexpected exception type: " + exception.getClass());

        Files.delete(tempFile);

    }


}
