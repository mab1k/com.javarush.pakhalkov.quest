<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
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
<br>
<br>
<br>
<br>
<br>
<br>
<h1 style="color: white; text-align: center" ><%= "Пролог" %>
</h1>
<br>
<p style="color: white; margin-right: 50px;">Вы – командир звездного корабля "Орион", ведущего свою миссию через неизведанные просторы галактики. Несколько лет ваш экипаж искал следы древних цивилизаций, стремясь разгадать тайны Вселенной. Но всё это время поиски не приносили результатов... до сегодняшнего дня.

    Корабль получает слабый сигнал с далекой и неизученной планеты Зеталон V. На экране появляются изображения руин, скрытых под покрывалом густых облаков, и вы понимаете, что эта находка может стать величайшим открытием в истории человечества. Под слоями пыли и времени могут лежать секреты древней цивилизации, её технологии или даже сокровища.

    Однако планета окутана легендами. Говорят, что те, кто осмелится ступить на её поверхность, столкнутся с опасными загадками. Каждый выбор определит вашу судьбу: шаг влево – и вы станете героем, шаг вправо – и потеряете всё. Вы осознаёте риск, но желание узнать правду сильнее страха.

    Ваш экипаж напряжённо смотрит на вас, ожидая приказа. Включить ли двигатели для посадки? Решение за вами, капитан.</p>
<br/>

<div class="registration-container" style="text-align: center">
    <form method="get" action="${pageContext.request.contextPath}/quest-servlet">
        <label for="name"></label>
        <input type="text" id="name" name="name" class="input-field" required><br>
        <input type="submit" value="Присоедениться">
    </form>
</div>
</body>
</html>