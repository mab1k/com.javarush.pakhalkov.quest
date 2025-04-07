<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Game Over</title>
</head>
<body>
<h1>Вы проиграли</h1>
<p>Спасибо за игру!</p>
<p>Ваше имя: ${userName}</p>
<p>Количество ваших игр за сессию: ${gamesPlayed}</p>
<a href="${pageContext.request.contextPath}/quest-servlet?restart=true">Начать заново</a>
</body>
</html>
