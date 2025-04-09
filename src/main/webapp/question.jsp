<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Question</title>
  <style>
    body {
      font-family: 'Roboto', sans-serif;
    }
    p {
      margin-left: 40px;
      text-indent: 25px;
    }
  </style>
  <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
</head>
<body style="background-image: url('${pageContext.request.contextPath}/img/space.jpg');
        background-size: 1920px 1200px;
        background-repeat: no-repeat;
        background-position: center center;">
<div style="margin-top: 150px;">
  <h2 style="color: white; text-align: center">${question.text}</h2>

  <form method="POST" action="${pageContext.request.contextPath}/quest-servlet" style="color: white; text-align: center">
    <c:forEach var="answer" items="${question.answers}">
      <div>
        <input type="radio" id="${answer.text}" name="answer" value="${answer.id}" required>
        <label for="${answer.text}">${answer.text}</label>
      </div>
    </c:forEach>
    <br>
    <button type="submit">Ответить</button>
  </form>
</div>

<div style="margin-top: 400px;">
  <h4 style="color: white">Ваше имя: ${userName}</h4>
  <h4 style="color: white">Количество ваших игр за сессию: ${gamesPlayed}</h4>
</div>
</body>
</html>
