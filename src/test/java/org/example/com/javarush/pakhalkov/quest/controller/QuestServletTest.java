package org.example.com.javarush.pakhalkov.quest.controller;


import org.example.com.javarush.pakhalkov.quest.entity.Quest;
import org.example.com.javarush.pakhalkov.quest.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class QuestServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private Repository repository;

    private QuestServlet questServlet;

    private List<Quest.Question> questions;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Настраиваем мок репозитория
        questions = new ArrayList<>();
        Quest.Question question = new Quest.Question(1L, "Sample question", new ArrayList<>());
        questions.add(question);

        when(repository.getQuestions()).thenReturn(questions);

        questServlet = new QuestServlet();
        questServlet.init();
    }

    @Test
    public void testDoGet_DisplayQuestion() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/question.jsp")).thenReturn(requestDispatcher);
        when(session.getAttribute("currentQuestionIndex")).thenReturn(0);

        questServlet.doGet(request, response);

        ArgumentCaptor<Quest.Question> questionCaptor = ArgumentCaptor.forClass(Quest.Question.class);
        verify(request).setAttribute(eq("question"), questionCaptor.capture());

        Quest.Question capturedQuestion = questionCaptor.getValue();
        assertNotNull(capturedQuestion);
        assertEquals(1L, capturedQuestion.getId());
        assertEquals("Высадиться на планету?", capturedQuestion.getText());

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_GameOver() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("gameover")).thenReturn("true");
        when(request.getRequestDispatcher("/gameover.jsp")).thenReturn(requestDispatcher);

        questServlet.doGet(request, response);

        verify(request).getRequestDispatcher("/gameover.jsp");

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_RestartGame() throws Exception {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("restart")).thenReturn("true");
        when(request.getContextPath()).thenReturn("");

        questServlet.doGet(request, response);

        verify(session).setAttribute(eq("gamesPlayed"), eq(2));
        verify(session).setAttribute(eq("currentQuestionIndex"), eq(0));
        verify(response).sendRedirect("/quest-servlet");
    }

    @Test
    public void testDoPost_CorrectAnswer() throws Exception {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("answer")).thenReturn("1");
        when(session.getAttribute("currentQuestionIndex")).thenReturn(0);
        when(request.getContextPath()).thenReturn("");

        questServlet.doPost(request, response);

        verify(session).setAttribute(eq("currentQuestionIndex"), eq(1));
        verify(response).sendRedirect("/quest-servlet");
    }

    @Test
    public void testDoPost_GameOver() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("answer")).thenReturn("12");
        when(session.getAttribute("currentQuestionIndex")).thenReturn(0);
        when(request.getContextPath()).thenReturn("");

        questServlet.doPost(request, response);

        verify(response).sendRedirect("/quest-servlet?gameover=true");
    }

}
