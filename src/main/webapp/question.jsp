<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Question</title>
</head>
<body>
<h2>${question.text}</h2>

<form method="POST" action="${pageContext.request.contextPath}/quest-servlet">
  <c:forEach var="answer" items="${question.answers}">
    <div>
      <input type="radio" id="${answer.text}" name="answer" value="${answer.id}" required>
      <label for="${answer.text}">${answer.text}</label>
    </div>
  </c:forEach>
  <br>
  <button type="submit">Ответить</button>
</form>

<br>
<br>
<p>Ваше имя: ${userName}</p>
<p>Количество ваших игр за сессию: ${gamesPlayed}</p>
</body>
</html>
