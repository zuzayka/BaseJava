<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>

    <c:forEach var="contactEntry" items="${resume.contactType}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
    </c:forEach>

    <c:forEach var="sectionEntry" items="${resume.sectionType}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <c:set var="sectionTitle" value="${sectionEntry.key.title}"/>
        <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
        <h3>${sectionTitle}</h3>
            <c:choose>
                <c:when test="${sectionTitle == 'Личные качества' || sectionTitle == 'Позиция'}">
                    <%=((TextSection) sectionEntry.getValue()).getText()%>
                </c:when>
                <c:when test="${sectionTitle == 'Квалификация' || sectionTitle == 'Достижения'}">
                        <tr>
                            <td style="width: 85%;">
                                <c:forEach var="line" items="<%=((ListSection) section).getList()%>">
                                    <ul>
                                        <li>${line}</li>
                                    </ul>
                                </c:forEach>
                            </td>

                        </tr>
                </c:when>
                <c:when test="${sectionTitle == 'Опыт работы' || sectionTitle == 'Образование'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getList()%>">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${empty organization.name}">
                                        <h4>${organization.webSite}</h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4><a href="${organization.webSite}">${organization.name}</a></h4>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="period" items="${organization.periods}">
                            <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Period"/>
                            <tr>
                                <td><%=HtmlUtil.formatDates(period)%></td>
                                <td>${period.title}<br>${period.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
    </c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>

