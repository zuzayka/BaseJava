<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <c:set var="sectionTitle" value="${type.title}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <h3><a>${sectionTitle}</a></h3>
            <c:choose>
                <c:when test="${sectionTitle == 'Личные качества'}">
                    <input type="text" name="${type}" size="120" value="<%=((TextSection) section).getText()%>">
                </c:when>
                <c:when test="${sectionTitle == 'Позиция'}">
                    <textarea name="${type}" cols="75" rows="2"><%=((TextSection) section).getText()%></textarea>
                </c:when>
                <c:when test="${sectionTitle == 'Квалификация' || sectionTitle == 'Достижения'}">
                    <textarea name="${type}" cols="75" rows="5"><%=String.join("\n",
                            ((ListSection) section).getList())%></textarea>

                </c:when>
                <c:when test="${sectionTitle == 'Опыт работы' || sectionTitle == 'Образование'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getList()%>" varStatus="counter">
<%--                        <p>${organization.name}</p>--%>
                        <dl>
                            <dt>Название учреждения</dt>
                            <dd><input type="text" name="${type}" size="100" value="${organization.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учреждения</dt>
                            <dd><input type="text" name="${type}url" size="100" value="${organization.webSite}"></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="position" items="${organization.periods}">
                                <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Period"/>
                                 <dl>
                                     <dt>Начальная дата:</dt>
                                     <dd>
                                         <input type="text" name="${type}${counter.index}startDate" size=10
                                         value="<%=DateUtil.format(position.getStartDate())%>" placeholder="MM/yyyy">
                                     </dd>
                                 </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(position.getEndDate())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}title" size=75
                                               value="${position.title}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd>
                                        <textarea name="${type}${counter.index}description" rows=2
                                               cols=75>${position.description}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>

        <button type="submit">Сохранить</button>
    </form>
    <button onclick="window.history.back()">Отменить</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
