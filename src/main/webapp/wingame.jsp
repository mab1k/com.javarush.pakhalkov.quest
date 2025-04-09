<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Win Game</title>
</head>
<body style="background-image: url('${pageContext.request.contextPath}/img/space.jpg');
        background-size: 1920px 1200px;
        background-repeat: no-repeat;
        background-position: center center;">
<div style="margin-top: 150px; text-align: center">
  <h1 style="color: green" onclick="">Вы выиграли!</h1>
  <p style="color: white">Спасибо за игру!</p>
  <p style="color: white">Ваше имя: ${userName}</p>
  <p style="color: white">Количество ваших игр за сессию: ${gamesPlayed}</p>
  <a style="color: white" href="${pageContext.request.contextPath}/quest-servlet?restart=true">Начать заново</a>
</div>
</body>
</html>
