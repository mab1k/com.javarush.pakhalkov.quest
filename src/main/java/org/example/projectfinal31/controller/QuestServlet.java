package org.example.projectfinal31.controller;

import org.example.projectfinal31.entity.Quest;
import org.example.projectfinal31.entity.User;
import org.example.projectfinal31.repository.CSVParserRepository;
import org.example.projectfinal31.service.QuestService;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@WebServlet(name = "questServlet", value = "/quest-servlet")
public class QuestServlet extends HttpServlet {
    private CSVParserRepository repository;
    private QuestService service;
    private List<Quest.Question> questions;
    private String userName;
    private User user;
    private String filePath;

    @Override
    public void init() {
        service = new QuestService();
        Jsonb jsonb = JsonbBuilder.create();
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.json");

        assert configStream != null;
        Config config = jsonb.fromJson(new InputStreamReader(configStream), Config.class);

        String csvFileName = config.nameDataBase;

        filePath = getClass().getClassLoader().getResource(csvFileName).getPath();
        filePath = filePath.replaceFirst("^/", "");

        service.parseAndStoreQuestions(filePath);

        repository = new CSVParserRepository(service.getQuestions());
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
            request.setAttribute("userName", userName);
            request.getRequestDispatcher("/wingame.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();

        
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

