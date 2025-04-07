package org.example.projectfinal31.controller;

import org.example.projectfinal31.entity.Quest;
import org.example.projectfinal31.entity.User;
import org.example.projectfinal31.repository.CSVParserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "questServlet", value = "/quest-servlet")
public class QuestServlet extends HttpServlet {
    private CSVParserRepository repository;
    private List<Quest.Question> questions;
    private String userName;
    private User user;
    private String filePath;

    @Override
    public void init() {
        repository = new CSVParserRepository();
        filePath = getClass().getClassLoader().getResource("db.csv").getPath();
        filePath = filePath.replaceFirst("^/", "");
        repository.parseAndStoreQuestions(filePath);
        questions = repository.getQuestions();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();

        if (userName == null) {
            userName = request.getParameter("name");
        }

        if (user == null){
            user = new User(userName);
        }

        Integer gamesPlayed = (Integer) session.getAttribute("gamesPlayed");
        if (gamesPlayed == null) {
            gamesPlayed = 0;
        }

        if ("true".equals(request.getParameter("restart"))) {
            System.out.println(2);
            session.setAttribute("gamesPlayed", gamesPlayed + 1);
            session.setAttribute("currentQuestionIndex", 0);
            response.sendRedirect(contextPath + "/quest-servlet");
            return;
        }

        if ("true".equals(request.getParameter("gameover"))) {
            request.setAttribute("gamesPlayed", gamesPlayed);
            System.out.println(userName);
            request.setAttribute("userName", userName);
            request.getRequestDispatcher("/gameover.jsp").forward(request, response);
            return;
        }

        Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
        if (currentQuestionIndex == null) {
            currentQuestionIndex = 0;
            session.setAttribute("currentQuestionIndex", currentQuestionIndex);
        }

        if (currentQuestionIndex < questions.size()) {
            Quest.Question currentQuestion = questions.get(currentQuestionIndex);

            request.setAttribute("question", currentQuestion);
            request.setAttribute("gamesPlayed", gamesPlayed);
            request.setAttribute("userName", userName);
            request.getRequestDispatcher("/question.jsp").forward(request, response);
        } else  {
            request.setAttribute("gamesPlayed", gamesPlayed);
            System.out.println(userName);
            request.setAttribute("userName", userName);
            request.getRequestDispatcher("/wingame.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();

        // Получаем индекс текущего вопроса
        Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
        if (currentQuestionIndex == null) {
            currentQuestionIndex = 0;
        }

        int userAnswer = Integer.parseInt(request.getParameter("answer"));

        if (currentQuestionIndex < questions.size() && userAnswer != 12 && userAnswer != 22 && userAnswer != 32 && userAnswer != 42) {
            currentQuestionIndex++;
        } else {
            response.sendRedirect(contextPath + "/quest-servlet?gameover=true");
            return;
        }

        session.setAttribute("currentQuestionIndex", currentQuestionIndex);

        response.sendRedirect(contextPath + "/quest-servlet");
    }
}

