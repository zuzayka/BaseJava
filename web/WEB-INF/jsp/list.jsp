<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.UUID" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Phone</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.PHONE)}</td>
<%--                <td>${resume.getContact(ContactType.PHONE)}</td>--%>
<%--                <td><%=ContactType.PHONE.toHtml(resume.getContact(ContactType.PHONE))%></td>--%>
<%--                <td><%=ContactType.PHONE.toHtml(resume.getContact(ContactType.PHONE))%></td>--%>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a> </td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a> </td>
            </tr>
        </c:forEach>
    </table>
    <h3>Новое резюме:</h3>
    <a href="resume?uuid=uuid2&action=add"><img src="img/add.png"></a>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
<%--    <link rel="stylesheet" href="css/style.css">--%>
<%--    <title>Список всех резюме</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<jsp:include page="fragments/header.jsp"/>--%>
<%--<section>--%>
<%--    <table border="1" cellpadding="8" cellspacing="0">--%>
<%--        <tr>--%>
<%--            <th>Имя</th>--%>
<%--            <th>Phone</th>--%>
<%--        </tr>--%>


<%--        <%--%>
<%--            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {--%>
<%--        %>--%>

<%--        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>--%>
<%--        <c:forEach items="${resumes}" var="resume">--%>
<%--            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>--%>
<%--            <tr>--%>
<%--                <td><a href="resume?uuid=${resume.uuid}">${resume.fullName}</a>--%>
<%--                </td>--%>
<%--                <td>${resume.getContact(ContactType.PHONE)}--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>


<%--        <tr>--%>
<%--            <td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%></a>--%>
<%--            </td>--%>
<%--            <td><%=resume.getContact(ContactType.PHONE)%>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--            }--%>
<%--        %>--%>


<%--    </table>--%>
<%--</section>--%>
<%--<jsp:include page="fragments/footer.jsp"/>--%>
<%--</body>--%>
<%--</html>--%>