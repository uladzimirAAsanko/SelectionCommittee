<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post" name="registration">
    <input type="hidden" name="command" value="registration">
    <br>
    E-mail:
    <br>
    <input type="email" name="email" value="">
    <br>
    Login:
    <br>
    <input type="text" name="login" value="">
    <br>
    Password:
    <br>
    <input type="password" name="password" value="">
    <br>
    Confirm your password:
    <br>
    <input type="password" name="passwordConfirm" value="">
    <br>
    First name:
    <br>
    <input type="text" name="firstName" value="">
    <br>
    Last name:
    <br>
    <input type="text" name="lastName" value="">
    <br>
    Fathers name:
    <br>
    <input type="text" name="fathersName" value="">
    <br> <br> <br>
    <p><select size="2"  name="role">
        <option disabled>Выберите героя</option>
        <option value="1">Администратор</option>
        <option selected value="0">Абитуриент</option>
    </select></p>
    <input type="submit" value="Registration">

    <br>
</form>
</body>